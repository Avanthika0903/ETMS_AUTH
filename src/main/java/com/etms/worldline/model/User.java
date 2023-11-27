package com.etms.worldline.model;

import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "employee")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long user_id;

    private String username;

    private String last_name;

    private String gender;

    private Date dob;

    private String email;

    private String college_name;

    private String college_location;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles=new HashSet<>();

    public User(String username, String email, String password, String last_name, String college_location, Date dob, String college_name, String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.last_name = last_name;
        this.gender = gender;
        this.dob = dob;
        this.college_location = college_location;
        this.college_name = college_name;
    }
}
