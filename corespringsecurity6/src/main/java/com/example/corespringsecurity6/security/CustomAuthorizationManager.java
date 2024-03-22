package com.example.corespringsecurity6.security;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager {
  private UrlFilterMetaDataSource metaDataSource = new UrlFilterMetaDataSource();

  @Override
  public AuthorizationDecision check(Supplier authentication, Object object) {
    Collection<ConfigAttribute> attributes = metaDataSource.getAttributes(object);


    return new AuthorizationDecision(false);
  }

  @Override
  public void verify(Supplier authentication, Object object) {
    AuthorizationManager.super.verify(authentication, object);
  }
}
