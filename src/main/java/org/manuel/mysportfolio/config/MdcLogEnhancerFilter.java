package org.manuel.mysportfolio.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class MdcLogEnhancerFilter implements Filter {

  private final UserIdProvider userIdProvider;

  @Override
  public void init(final FilterConfig filterConfig) {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    MDC.put("userId", userIdProvider.getUserId().orElse(null));
    chain.doFilter(request, response);
    MDC.remove("userId");
  }

  @Override
  public void destroy() {
  }
}
