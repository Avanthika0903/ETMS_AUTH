package com.etms.worldline.controllers;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class UserController {
    @GetMapping("/dashboard")
    public String sampleadmin(){
        return "Hello admin!";
    }
}
