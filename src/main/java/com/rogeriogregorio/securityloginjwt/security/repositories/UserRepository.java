package com.rogeriogregorio.securityloginjwt.security.repositories;

import com.rogeriogregorio.securityloginjwt.security.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmail(String emailLogin);
}
