package com.rogeriogregorio.securityloginjwt.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    @NotBlank(message = "The name must not be blank.")
    @Pattern(regexp = "^[\\p{L}\\s.]+$", message = "The name must contain only letters and spaces.")
    @Size(min = 5, max = 250, message = "The name must have between 5 and 250 characters.")
    private String name;

    @Column(name = "email", unique = true)
    @NotBlank(message = "The email must not be blank.")
    @Email(message = "Please enter a valid email address. Example: user@example.com")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "The password must not be blank.")
    private String password;

    @Column(name = "role")
    @NotNull(message = "The user role cannot be null.")
    private UserRole role;

    public User() {
    }

    private User(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setEmail(builder.email);
        setPassword(builder.password);
        setRole(builder.role);
    }

    public static Builder newBuilder() {
        return new Builder();
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                .withPassword(this.password)
                .withRole(this.role);
    }

    public static final class Builder {
        private UUID id;
        private String name;
        private String email;
        private String password;
        private UserRole role;

        private Builder() {
        }

        public Builder withId(UUID id) {
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

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "[User: id= " + id
                + ", name= " + name
                + ", email= " + email
                + ", role= " + role
                + "]";
    }
}