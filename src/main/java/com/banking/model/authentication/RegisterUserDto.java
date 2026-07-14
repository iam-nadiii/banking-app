package com.banking.model.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, CredentialsDto represents the data a client must
    pass to the server for a login endpoint, and TokenDto represents the object that's returned from the server
    to the client from a login endpoint.
 */
public class RegisterUserDto {

    // FIX: `role` deliberately removed. A client-supplied role field means
    // anyone can register themselves as ADMIN by adding "role":"ADMIN" to
    // the request body — this app only ever has USER and ADMIN, and there's
    // no legitimate reason for a client to choose either. Registration
    // always creates a USER; promotion to ADMIN is a manual DB step.

    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}