package com.example.corespringsecurity6.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;
import static org.springframework.security.authorization.AuthorizationManagers.allOf;
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

  @Bean
  public UserDetailsService users() {
    UserDetails user = User.builder()
            .username("user")
            .password("{noop}1234")
            .roles("USER")
            .build();

    UserDetails admin = User.builder()
            .username("admin")
            .password("{noop}1234")
            .roles("ADMIN")
            .build();

    UserDetails sys = User.builder()
            .username("sys")
            .password("{noop}1234")
            .roles("SYS")
            .build();

    return new InMemoryUserDetailsManager(user, admin, sys);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((authz) ->
                    authz
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/mypage").hasRole("USER")
                            .requestMatchers("/message").hasRole("MANAGER")
                            .requestMatchers("/config").hasAnyRole("ADMIN","MANAGER","USER")
                            .anyRequest()
                            .authenticated())
            .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
            .sessionManagement(session ->
                            session
//                                .invalidSessionUrl("/invalid") // 세션 유효 하지 않을때 이동할 URL
                                    .maximumSessions(1) // 최대 허용 가능 세션, -1 무제한
//                                .expiredUrl("/expired") // 토근 만료 되었을때 이동할 URL
                                    .maxSessionsPreventsLogin(false) // true 동시 로그인 차단, false 기존 세션 만료 기존 세션 만료
            );
    return http.build();
  }
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) ->
            web
                    .ignoring()
                    .requestMatchers(
                            PathRequest.toStaticResources().atCommonLocations()
                    );
  }
}