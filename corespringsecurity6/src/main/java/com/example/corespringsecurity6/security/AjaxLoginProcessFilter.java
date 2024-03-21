package com.example.corespringsecurity6.security;

import com.example.corespringsecurity6.domain.AccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

public class AjaxLoginProcessFilter extends AbstractAuthenticationProcessingFilter {

  private ObjectMapper objectMapper = new ObjectMapper();

  public AjaxLoginProcessFilter() {
    super(new AntPathRequestMatcher("/api/login"));
  }
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    if(!isAjax(request)) {
      throw new IllegalStateException("Authentication is Not Supported");
    }
    AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);
    if(StringUtils.isEmpty(accountDto.getUsername()) || StringUtils.isEmpty(accountDto.getPassword())) {
      throw new IllegalStateException("Username or Password Is empty");
    }

    AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());

    return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
  }



  private boolean isAjax(HttpServletRequest request) {
   return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }
}
