package com.example.corespringsecurity6.security.config;

import com.example.corespringsecurity6.security.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

//  private CustomUserDetailsService userDetailsService;
  private final AuthenticationProvider authenticationProvider;
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
//  public UserDetailsService users() {
//    UserDetails user = User.builder()
//            .username("user")
//            .password(passwordEncoder.passwordEncoder().encode("1234"))
//            .roles("USER")
//            .build();
//
//    UserDetails manager = User.builder()
//            .username("manager")
//            .password(passwordEncoder.passwordEncoder().encode("1234"))
//            .roles("MANAGER")
//            .build();
//
//    UserDetails admin = User.builder()
//            .username("admin")
//            .password(passwordEncoder().encode("1234"))
//            .roles("ADMIN")
//            .build();
//
//
//    return new InMemoryUserDetailsManager(user, admin, manager);
//  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((authz) ->
                    authz
                            .requestMatchers("/","/users","/logout").permitAll()
                            .requestMatchers("/mypage").hasRole("USER")
                            .requestMatchers("/message").hasAnyRole("MANAGER", "ADMIN")
                            .requestMatchers("/config").hasRole("ADMIN")
                            .anyRequest()
                            .authenticated()

            )
            .formLogin(formLogin ->
                    formLogin.loginPage("/login")
                            .loginProcessingUrl("/login_proc")
                            .defaultSuccessUrl("/")
                            .failureHandler(customAuthenticationFailureHandler)
                            .permitAll()
            )
            .sessionManagement(session ->
                            session
//                                .invalidSessionUrl("/invalid") // 세션 유효 하지 않을때 이동할 URL
                                    .maximumSessions(1) // 최대 허용 가능 세션, -1 무제한
//                                .expiredUrl("/expired") // 토근 만료 되었을때 이동할 URL
                                    .maxSessionsPreventsLogin(false) // true 동시 로그인 차단, false 기존 세션 만료 기존 세션 만료
            )
            .authenticationProvider(authenticationProvider);
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