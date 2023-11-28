package com.etms.worldline.controllers;

import com.etms.worldline.Repository.RoleRepository;
import com.etms.worldline.Service.UserService;
import com.etms.worldline.model.ERole;
import com.etms.worldline.model.Role;
import com.etms.worldline.model.User;
import com.etms.worldline.payload.request.SignupRequest;
import com.etms.worldline.payload.response.JwtResponse;
import com.etms.worldline.payload.response.MessageResponse;
import com.etms.worldline.security.JwtTokenProvider;
import com.etms.worldline.security.UserPrincipal;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        if(userService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.ok(new MessageResponse("User is already there!"));
        }
        User reguser = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),signupRequest.getLast_name(),signupRequest.getCollege_location(),signupRequest.getDob(),signupRequest.getCollege_name(),signupRequest.getGender());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            System.out.println("This is executed");
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role manRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(manRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        reguser.setRoles(roles);
        userService.save(reguser);
        return ResponseEntity.ok(new MessageResponse("User is successfully registered"));
    }
    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody User user){
        if(userService.existsByUsername(user.getUsername())){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt=jwtTokenProvider.generateToken(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
    return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUserId(),userDetails.getUsername(),userDetails.getEmail(),true,roles,"User credentials is correct"));}
        return ResponseEntity.ok(new JwtResponse("Username or password is wrong",false));
    }
}
