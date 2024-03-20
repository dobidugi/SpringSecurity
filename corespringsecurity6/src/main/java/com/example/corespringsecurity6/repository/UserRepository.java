package com.example.corespringsecurity6.repository;

import com.example.corespringsecurity6.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {

  Account findByUsername(String username);
}
