package com.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello";
    }
}
