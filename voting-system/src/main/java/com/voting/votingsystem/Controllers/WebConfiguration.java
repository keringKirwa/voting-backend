package com.voting.votingsystem.Controllers;

import com.voting.votingsystem.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebConfiguration {
    /**
     * note that  the @Autowired annotation in this case will be used to inject  this instance from the bean Container .
     */
    private static final String[] WHITE_LIST_URLS = {"/auth/login", "/mpesa"};
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private AuthFilter JWTAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable(); //prevent attackers from performing cross site request forgery and executing malicious scripts .

        http.authorizeHttpRequests().antMatchers(WHITE_LIST_URLS).permitAll().anyRequest().authenticated().and().authenticationProvider(authenticationProvider).addFilterBefore(JWTAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//stop the  use of sessions in soring boot .
        return http.build();
    }
}
