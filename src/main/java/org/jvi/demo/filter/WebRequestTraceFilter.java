package org.jvi.demo.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebRequestTraceFilter extends OncePerRequestFilter implements Ordered {

  private boolean dumpRequests = false;

  // Not LOWEST_PRECEDENCE, but near the end, so it has a good chance of catching all
  // enriched headers, but users can add stuff after this if they want to
  private int order = Ordered.LOWEST_PRECEDENCE - 10;

  private ErrorAttributes errorAttributes;

  @Override
  public int getOrder() {
    return this.order;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response, final FilterChain filterChain)
      throws ServletException, IOException {
    final long startTime = System.nanoTime();
    final Map<String, Object> trace = getTrace(request);
    logTrace(request, trace);
    int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    try {
      filterChain.doFilter(request, response);
      status = response.getStatus();
    } finally {
      // addTimeTaken(trace, startTime);
      enhanceTrace(trace, status == response.getStatus() ? response
          : new CustomStatusResponseWrapper(response, status));
      logTrace(request, trace);
      log.info("Statut = {}", status);
    }
  }

  protected Map<String, Object> getTrace(final HttpServletRequest request) {
    final HttpSession session = request.getSession(false);
    final Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
    final Principal userPrincipal = request.getUserPrincipal();
    final Map<String, Object> trace = new LinkedHashMap<String, Object>();
    final Map<String, Object> headers = new LinkedHashMap<String, Object>();
    trace.put("method", request.getMethod());
    trace.put("path", request.getRequestURI());
    trace.put("headers", headers);
    return trace;
  }

  private Map<String, Object> getRequestHeaders(final HttpServletRequest request) {
    final Map<String, Object> headers = new LinkedHashMap<String, Object>();
    final Enumeration<String> names = request.getHeaderNames();
    while (names.hasMoreElements()) {
      final String name = names.nextElement();
      headers.put(name, getHeaderValue(request, name));
    }
    postProcessRequestHeaders(headers);
    return headers;
  }

  private Object getHeaderValue(final HttpServletRequest request, final String name) {
    final List<String> value = Collections.list(request.getHeaders(name));
    if (value.size() == 1) {
      return value.get(0);
    }
    if (value.isEmpty()) {
      return "";
    }
    return value;
  }

  protected void postProcessRequestHeaders(final Map<String, Object> headers) {}

  @SuppressWarnings("unchecked")
  protected void enhanceTrace(final Map<String, Object> trace, final HttpServletResponse response) {
    final Map<String, Object> headers = (Map<String, Object>) trace.get("headers");
    headers.put("response", getResponseHeaders(response));
  }

  private Map<String, String> getResponseHeaders(final HttpServletResponse response) {
    final Map<String, String> headers = new LinkedHashMap<String, String>();
    for (final String header : response.getHeaderNames()) {
      final String value = response.getHeader(header);
      headers.put(header, value);
    }
    headers.put("status", "" + response.getStatus());
    return headers;
  }

  private void logTrace(final HttpServletRequest request, final Map<String, Object> trace) {
    if (log.isInfoEnabled()) {
      log.info("Processing request " + request.getMethod() + " " + request.getRequestURI());
      if (this.dumpRequests) {
        log.info("Headers: " + trace.get("headers"));
      }
    }
  }

  public void setErrorAttributes(final ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  private static final class CustomStatusResponseWrapper extends HttpServletResponseWrapper {

    private final int status;

    private CustomStatusResponseWrapper(final HttpServletResponse response, final int status) {
      super(response);
      this.status = status;
    }

    @Override
    public int getStatus() {
      return this.status;
    }

  }

}
