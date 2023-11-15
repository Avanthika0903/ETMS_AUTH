package com.etms.worldline.controllers;

import com.etms.worldline.Service.UserService;
import com.etms.worldline.model.User;
import com.etms.worldline.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@Component
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user){
        if(userService.existsByUsername(user.getUsername())){
            return "User is already there!";
        }
        userService.save(user);
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtTokenProvider.generateToken(authentication);
        return jwt;
    }
    @PostMapping("/login")
    public String authUser(@RequestBody User user){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt=jwtTokenProvider.generateToken(authentication);
    return jwt;
    }
}
