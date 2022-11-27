package com.voting.votingsystem.Controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@EnableWebSecurity
public class SpringConfig {
    private static final String[] WHITE_LIST_URLS = {
            "/voting-seven.vercel.app",
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeHttpRequests().antMatchers(WHITE_LIST_URLS)
                .permitAll();
        http.headers().frameOptions().disable();
        return http.build();
    }
}