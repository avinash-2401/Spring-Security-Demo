package com.Security.Spring_Security_Demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){

        http.authorizeHttpRequests(authorizeRequest->
                        authorizeRequest.requestMatchers("/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                                .anyRequest().authenticated());
                                http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user1= User.withUsername("user1")
                //.password("{noop}user@123")
                .password(passwordEncoder().encode("user@123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("user2")
                //.password("{noop}user@234")
                .password(passwordEncoder().encode("user@234"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                //.password("{noop}admin@123")
                .password(passwordEncoder().encode("admin@123"))
                .roles("ADMIN")
                .build();

         //return new InMemoryUserDetailsManager(user1,user2,admin);
        JdbcUserDetailsManager userDetailsManager=new JdbcUserDetailsManager(dataSource);

        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        userDetailsManager.createUser(admin);
        return userDetailsManager;



    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
