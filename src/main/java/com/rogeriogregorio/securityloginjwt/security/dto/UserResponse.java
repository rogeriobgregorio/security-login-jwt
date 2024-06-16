package com.rogeriogregorio.securityloginjwt.security.dto;

import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String email;
    private UserRole userRole;

    public UserResponse() {
    }

    public UserResponse(UUID id, String name, String email, UserRole userRole) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
