package org.jvi.demo.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

// @Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class RequestEventLoggingFilter extends OncePerRequestFilter {

  @Autowired
  private ObjectMapper mapper;

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response, final FilterChain filterChain)
      throws ServletException, IOException {

    log.info("Event occurred.");

    try {
      filterChain.doFilter(request, response);
    } catch (final Exception e) {
      logRequestInfos(request);
      throw e;
    }

  }

  private void logRequestInfos(final HttpServletRequest request) {
    final Map<String, String> headers = Collections.list(request.getHeaderNames()).stream()
        .collect(Collectors.toMap(h -> h, request::getHeader));

    final StringBuilder builder = new StringBuilder();
    builder.append("REQUEST DATA :\n");
    builder.append(request.getMethod() + " " + getFullURL(request) + "\n");
    headers.forEach((k, v) -> {
      builder.append(k + ": " + v + "\n");
    });
    log.info(builder.toString());
    // try (BufferedReader reader = request.getReader()) {
    // if (null != reader) {
    // final String jsonString = IOUtils.toString(reader);
    // log.info("Body={}", jsonString);
    // }
    // }
  }

  private String getFullURL(final HttpServletRequest request) {
    final StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
    final String queryString = request.getQueryString();

    if (queryString == null) {
      return requestURL.toString();
    } else {
      return requestURL.append('?').append(queryString).toString();
    }
  }

}
