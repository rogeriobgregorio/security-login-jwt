package com.rogeriogregorio.securityloginjwt.security.services;

import com.rogeriogregorio.securityloginjwt.security.dto.UserRequest;
import com.rogeriogregorio.securityloginjwt.security.dto.UserResponse;
import com.rogeriogregorio.securityloginjwt.security.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(String id);

    UserResponse updateUser(String id, UserRequest userRequest);

    UserResponse updateUserRole(String id, String userRole);

    void deleteUser(String id);

    User getUserIfExists(String id);

    UserResponse getUserByEmail(String email);
}
