package com.example.springdemo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JWTVerifier extends BasicAuthenticationFilter {

    public static String USERNAME = "";

    public JWTVerifier(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader != null){
            String token = tokenHeader.replace("Bearer ", "");

            String username = getDecodedJWT(token).getSubject();

            USERNAME = username;

            if(username != null){
                Collection<GrantedAuthority> authorities = Arrays.stream(getDecodedJWT(token).getClaim("JWTAuthoritiesKey").asString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        }
        return null;
    }

    public static DecodedJWT getDecodedJWT(String token) {
        return JWT.require(Algorithm.HMAC512("JWTSecretKey".getBytes()))
                .build()
                .verify(token);
    }
}
