package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/user")
    public String user(){ return "user";}

    @GetMapping("/notice")
    public String notice(){ return "notice";}

    @GetMapping("/admin/pay")
    public String admin(){ return "admin";}

    @GetMapping("/admin/sys")
    public String sys(){ return "sys";}
}
