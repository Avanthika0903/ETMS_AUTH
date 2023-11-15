package com.etms.worldline.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AdminController {
    @GetMapping("/profile")
    public String sample(){
        return "Hello User!";
    }

}
