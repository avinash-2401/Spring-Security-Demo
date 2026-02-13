package com.Security.Spring_Security_Demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("hello")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
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
}
