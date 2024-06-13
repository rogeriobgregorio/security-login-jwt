package com.rogeriogregorio.securityloginjwt.security.services;

import com.rogeriogregorio.securityloginjwt.security.entities.dto.UserRequest;
import com.rogeriogregorio.securityloginjwt.security.entities.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    UserResponse registerUser(UserRequest userRequest);
}
