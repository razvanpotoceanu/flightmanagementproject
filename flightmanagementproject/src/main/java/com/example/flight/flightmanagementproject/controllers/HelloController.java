package com.example.flight.flightmanagementproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController; // Schimbat

@RestController // Folosim RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() { // Nu mai e nevoie de @ResponseBody
        return "Die Anwendung funktioniert!";
    }
}