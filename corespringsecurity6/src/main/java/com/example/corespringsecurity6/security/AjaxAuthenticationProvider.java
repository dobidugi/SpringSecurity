package com.example.corespringsecurity6.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AjaxAuthenticationProvider implements AuthenticationProvider {

  private final CustomUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = (String)authentication.getCredentials();

    AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username);

    if(!passwordEncoder.matches(password,accountContext.getAccount().getPassword())) {
      throw new BadCredentialsException("BadCredentialsException");
    }

    return new AjaxAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return AjaxAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
