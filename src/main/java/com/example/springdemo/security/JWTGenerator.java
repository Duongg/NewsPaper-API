package com.example.springdemo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springdemo.entity.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTGenerator extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    public JWTGenerator(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            try {
                Account account = new ObjectMapper().readValue(request.getInputStream(), Account.class);
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
            } catch (IOException ee) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        authResult.getAuthorities().forEach(authority -> System.out.println(authority.getAuthority()));
        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
                .withClaim("JWTAuthoritiesKey", authResult.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_000))
                .sign(Algorithm.HMAC512("JWTSecretKey".getBytes()));

        response.setHeader("Authorization", "Bearer " + token);
        response.getWriter().write("Bearer " + token);

    }

}
