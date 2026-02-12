package com.Security.Spring_Security_Demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("hello")
    public String sayHello(){
        return "Avinash";
    }

}
