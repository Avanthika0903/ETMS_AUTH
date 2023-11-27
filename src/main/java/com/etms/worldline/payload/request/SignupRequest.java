package com.etms.worldline.payload.request;

import java.util.Date;
import java.util.Set;

public class SignupRequest {

  private String username;



  private String email;

  private Set<String> role;


  private String password;
  private String last_name;
  private String gender;


  private Date dob;

  private String college_location;

  private String college_name;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<String> getRole() {
    return role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public String getCollege_location() {
    return college_location;
  }

  public void setCollege_location(String college_location) {
    this.college_location = college_location;
  }

  public String getCollege_name() {
    return college_name;
  }

  public void setCollege_name(String college_name) {
    this.college_name = college_name;
  }
}
