package com.infokey.infokey.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * For testing connection from docker
 * Not used
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String HelloWorld() {
        return "hello world";
    }
}
