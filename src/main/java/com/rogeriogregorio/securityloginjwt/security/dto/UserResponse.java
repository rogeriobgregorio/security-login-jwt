package com.rogeriogregorio.securityloginjwt.security.dto;

import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private UserRole role;

    public UserResponse() {
    }

    private UserResponse(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setEmail(builder.email);
        setRole(builder.role);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Builder toBuilder() {

        return new Builder()
                .withId(this.id)
                .withName(this.name)
                .withEmail(this.email)
                .withRole(this.role);
    }

    public static final class Builder {
        private String id;
        private String name;
        private String email;
        private UserRole role;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }
}
