package com.Security.Spring_Security_Demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFillterToken extends OncePerRequestFilter {

    @Autowired
    private JwtUtills jwtUtills;

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("authenticationFillterToken!!!!!!");

        try{

            String jwt= Parssjwt(request);
            if(jwt != null && jwtUtills.validateToken(jwt));
            String username = jwtUtills.getUsernameFromToken(jwt);
       // UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken=
                    new UsernamePasswordAuthenticationToken(username,null, List.of());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }catch (Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request,response);

    }
    private String Parssjwt(HttpServletRequest request){
        String jwt=jwtUtills.getJwtFromHeader(request);
        return jwt;

    }
}
