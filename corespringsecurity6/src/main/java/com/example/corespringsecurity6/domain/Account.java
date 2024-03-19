package com.example.corespringsecurity6.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
public class Account {

  @Id
  @GeneratedValue
  private Long Id;
  private String username;
  private String password;
  private String email;
  private String age;
  private String role;

  @Builder
  public Account(String username, String password, String email, String age, String role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.age = age;
    this.role = role;
  }

  public Account() {

  }
}
