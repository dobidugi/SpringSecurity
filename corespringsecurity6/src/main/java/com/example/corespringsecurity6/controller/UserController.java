package com.example.corespringsecurity6.controller;

import com.example.corespringsecurity6.domain.AccountDto;
import com.example.corespringsecurity6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("/mypage")
  public String mypage() {
    return "user/mypage";
  }

  @GetMapping("/users")
  public String createUser() {
    return "user/login/register";
  }

  @PostMapping("/users")
  public String register(AccountDto accountDto) {
    System.out.println(userService);
    System.out.println(passwordEncoder);
    accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
    userService.createUser(accountDto.toAccount());
    return "redirect:/";

  }
}
