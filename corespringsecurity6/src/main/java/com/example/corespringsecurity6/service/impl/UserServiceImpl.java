package com.example.corespringsecurity6.service.impl;

import com.example.corespringsecurity6.domain.Account;
import com.example.corespringsecurity6.repository.UserRepository;
import com.example.corespringsecurity6.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Transactional
  @Override
  public void createUser(Account account) {
    userRepository.save(account);

  }

  @Override
  public Account findByUsername(String username) {
    return null;
  }
}
