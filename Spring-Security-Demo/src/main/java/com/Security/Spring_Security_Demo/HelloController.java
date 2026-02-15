package com.Security.Spring_Security_Demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtills jwtUtills;

    @GetMapping("hello")
   // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String sayHello(){
        return "Avinash ,normal ....!!";
    }

    @GetMapping("/admin/hello")
    public String sayAdminHello(){
        return "Avinash, Admin !!";
    }

    @GetMapping("/user/hello")
    public String sayUserHello(){
        return "Avinash,User !!!!";
    }

    @PostMapping("/signin")
    public String login(@RequestBody LoginRequest loginRequest){
        Authentication authentication= null;

       try {
           authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                   loginRequest.getUsername(),
                   loginRequest.getPassword()));

       }catch (AuthenticationException e){
           e.printStackTrace();
       }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        assert authentication != null;
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        assert userDetails != null;
        return jwtUtills.genrateJwtTokenFromUserName(userDetails.getUsername());
    }
}
