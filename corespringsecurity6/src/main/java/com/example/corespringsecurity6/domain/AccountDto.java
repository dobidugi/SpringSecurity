package com.example.corespringsecurity6.domain;

import lombok.Data;

@Data
public class AccountDto {
  private Long Id;
  private String username;
  private String password;
  private String email;
  private String age;
  private String role;


  public Account toAccount() {
    return Account.builder().email(email).username(username).password(password).age(age).role(role).build();
  }
}
