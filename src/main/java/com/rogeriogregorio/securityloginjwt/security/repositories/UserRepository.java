package com.rogeriogregorio.securityloginjwt.security.repositories;

import com.rogeriogregorio.securityloginjwt.security.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    UserDetails findByEmail(String emailLogin);

    Optional<User> findUserByEmail(String email);
}
