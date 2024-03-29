package com.example.corespringsecurity6.security;

import com.example.corespringsecurity6.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class AccountContext extends User {
  private Account account;
  public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
    super(account.getUsername(), account.getPassword(), authorities);
    this.account = account;
  }

  public Account getAccount() {
    return account;
  }
}
