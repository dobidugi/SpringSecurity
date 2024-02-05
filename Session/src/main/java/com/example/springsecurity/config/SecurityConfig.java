package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
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
}
