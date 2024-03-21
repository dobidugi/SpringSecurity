package com.example.corespringsecurity6.security;

import com.example.corespringsecurity6.domain.Account;
import com.example.corespringsecurity6.repository.UserRepository;
import com.example.corespringsecurity6.security.AccountContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = userRepository.findByUsername(username);

    if(account == null) {
      throw new UsernameNotFoundException("UsernameNotFoundException");
    }


    List<GrantedAuthority> roles = new ArrayList<>();
    roles.add(new SimpleGrantedAuthority(account.getRole()));
    AccountContext accountContext = new AccountContext(account, roles);
    System.out.println(account.getRole());
    return accountContext;
  }
}
