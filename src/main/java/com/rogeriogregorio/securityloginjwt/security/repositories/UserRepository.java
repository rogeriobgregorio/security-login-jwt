package com.rogeriogregorio.securityloginjwt.security.repositories;

import com.rogeriogregorio.securityloginjwt.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String emailLogin);
}
