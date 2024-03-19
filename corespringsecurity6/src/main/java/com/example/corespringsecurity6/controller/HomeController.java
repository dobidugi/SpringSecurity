package com.example.corespringsecurity6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping(value="/")
  public String home() throws Exception {
    return "home";
  }

  public String login() throws Exception {
    return "login";
  }
}
