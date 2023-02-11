package com.voting.votingsystem;

import com.voting.votingsystem.utils.JWTInterpreter;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * if you are using token-based authentication, each request made to the system will contain a token that
 * identifies the user who made the request. In this case, the "current user" will be the user who is
 * identified by the token in the current request.
 */

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    /**
     * this  is   the filter that the application is  will be using , this   is a JWT FILTER , and therefore the app will know that the user to login  can be found  in the token .
     * Again note that the SessionPolicy STATELESS indicates  that the app should not use the session base authentication policy.
     */

    private final JWTInterpreter jwtInterpreter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("BearerKKDEV ")) {
            filterChain.doFilter(request, response);
            return; //in this case here , the return here will stop calling the other methods in the filter chain .
        }

        jwt = authHeader.split(" ")[1];

        userEmail = jwtInterpreter.extractUserName(jwt); //the  user email was used as the  username.

        if (SecurityContextHolder.getContext().getAuthentication() != null){
            System.out.println(":::::: THE CONTEXT WAS NOT NULL .... REQUEST STOPPED ...");
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(userEmail);
            if (jwtInterpreter.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                /**
                 * this one here updates more auth details , that is , more details for the authenticated request .
                 * the authToken will have granted authorities , and the user  and a principal object that has the user details . the getDetails could give us  the session id .
                 */

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}