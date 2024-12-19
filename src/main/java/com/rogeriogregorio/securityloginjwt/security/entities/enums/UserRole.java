package com.rogeriogregorio.securityloginjwt.security.entities.enums;

public enum UserRole {

    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    USER("USER");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
