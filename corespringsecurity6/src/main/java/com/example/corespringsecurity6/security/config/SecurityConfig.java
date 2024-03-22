package com.example.corespringsecurity6.security.config;

import com.example.corespringsecurity6.security.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

  private final AjaxAuthenticationProvider ajaxAuthenticationProvider;
  private final AjaxAuthenticationOnSuccessHandler ajaxAuthenticationOnSuccessHandler;
  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  @Bean
  public AuthenticationManager authenticationManager(){
    return new ProviderManager(ajaxAuthenticationProvider);
  }





//  @Bean
//  public AuthorizationFilter authorizationFilter () {
//    AuthorizationFilter authorizationFilter = new AuthorizationFilter((AuthorizationManager<HttpServletRequest>) authenticationManager());
//
//  }


  @Bean
  public AuthorizationManager authorizationManager() {
    CustomAuthorizationManager customAuthorizationManager = new CustomAuthorizationManager();
    return customAuthorizationManager;
  }




  @Bean
  public AjaxAuthenticationOnSuccessHandler authenticationOnSuccessHandler() {
    return new AjaxAuthenticationOnSuccessHandler();
  }

  @Bean
  public AjaxLoginProcessFilter ajaxLoginProcessFilter(AuthenticationManager authenticationManager) {
    AjaxLoginProcessFilter filter = new AjaxLoginProcessFilter();
    filter.setAuthenticationManager(authenticationManager);
    filter.setAuthenticationSuccessHandler(ajaxAuthenticationOnSuccessHandler);

    return filter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authz) ->
                    authz
                            .anyRequest().access(authorizationManager())
//                            .requestMatchers("/","/users","/logout","/api/login").permitAll()
//                            .requestMatchers("/mypage").hasRole("USER")
//                            .requestMatchers("/message").hasAnyRole("MANAGER", "ADMIN")
//                            .requestMatchers("/config").hasRole("ADMIN")
//                            .anyRequest()
//                            .authenticated()

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
//            .authenticationProvider(authenticationProvider)
//            .authorizeHttpRequests( )
            .addFilterBefore(ajaxLoginProcessFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

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