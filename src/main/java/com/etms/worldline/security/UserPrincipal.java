package com.etms.worldline.security;

import com.etms.worldline.model.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.lang.Long;

@ComponentScan
public class UserPrincipal implements UserDetails {

    private Long user_id;
    private String username;
    private String last_name;
    private String gender;
    private Date dob;
    private String email;
    private String college_name;
    private String college_location;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    public UserPrincipal(Long userId, String firstName, String lastName, String gender,
                         Date dob, String email, String collegeName, String collegeLocation,
                         String password,Collection<? extends GrantedAuthority> authorities) {
        this.user_id = userId;
        this.username = firstName;
        this.last_name = lastName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.college_name = collegeName;
        this.college_location = collegeLocation;
        this.password = password;
        this.authorities=authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserPrincipal(
                user.getUser_id(),
                user.getUsername(),
                user.getLast_name(),
                user.getGender(),
                user.getDob(),
                user.getEmail(),
                user.getCollege_name(),
                user.getCollege_location(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Implement other UserDetails methods...

    public Long getUserId() {
        return user_id;
    }

    public String getLastName() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getCollegeName() {
        return college_name;
    }

    public String getCollegeLocation() {
        return college_location;
    }

    // Implement other getters...

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserPrincipal user = (UserPrincipal) o;
        return Objects.equals(user_id, user.user_id);
    }

}