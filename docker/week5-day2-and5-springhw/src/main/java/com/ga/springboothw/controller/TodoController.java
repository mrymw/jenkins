package com.ga.springboothw.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    @GetMapping("/hello")
    public String hello(){
        System.out.println("Hello World");
        return "Hello World!!!";
    }
}
