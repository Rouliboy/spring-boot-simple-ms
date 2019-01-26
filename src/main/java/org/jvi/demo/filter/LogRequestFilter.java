package org.jvi.demo.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;

// @Component
@Slf4j
public class LogRequestFilter extends OncePerRequestFilter implements Ordered {

  // put filter at the end of all other filters to make sure we are processing after all others
  private int order = Ordered.LOWEST_PRECEDENCE - 600;
  private ErrorAttributes errorAttributes;

  @Override
  public int getOrder() {
    return order;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response, final FilterChain filterChain)
      throws ServletException, IOException {
    final ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

    log.info("**** Entering LogRequestFilter");
    int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

    // pass through filter chain to do the actual request handling

    try {
      filterChain.doFilter(wrappedRequest, response);
    } catch (final Exception e) {
      status = response.getStatus();
      log.info("**** status={}", status);
      throw e;
    }
    status = response.getStatus();
    // only log request if there was an error
    if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
      final Map<String, Object> trace = getTrace(wrappedRequest, status);

      // body can only be read after the actual request handling was done!
      getBody(wrappedRequest, trace);
      logTrace(wrappedRequest, trace);
    }
  }

  private void getBody(final ContentCachingRequestWrapper request,
      final Map<String, Object> trace) {
    // wrap request to make sure we can read the body of the request (otherwise it will be consumed
    // by the actual
    // request handler)
    final ContentCachingRequestWrapper wrapper =
        WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
    if (wrapper != null) {
      final byte[] buf = wrapper.getContentAsByteArray();
      if (buf.length > 0) {
        String payload;
        try {
          payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
        } catch (final UnsupportedEncodingException ex) {
          payload = "[unknown]";
        }

        trace.put("body", payload);
      }
    }
  }

  private void logTrace(final HttpServletRequest request, final Map<String, Object> trace) {
    final Object method = trace.get("method");
    final Object path = trace.get("path");
    final Object statusCode = trace.get("statusCode");

    log.info(String.format("%s %s produced an error status code '%s'. Trace: '%s'", method, path,
        statusCode, trace));
  }

  protected Map<String, Object> getTrace(final HttpServletRequest request, final int status) {
    final Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

    final Principal principal = request.getUserPrincipal();

    final Map<String, Object> trace = new LinkedHashMap<String, Object>();
    trace.put("method", request.getMethod());
    trace.put("path", request.getRequestURI());
    trace.put("principal", principal.getName());
    trace.put("query", request.getQueryString());
    trace.put("statusCode", status);

    final ServletWebRequest servletWebRequest = new ServletWebRequest(request);

    if (exception != null && this.errorAttributes != null) {
      trace.put("error", this.errorAttributes.getErrorAttributes(servletWebRequest, true));
    }

    return trace;
  }

}
