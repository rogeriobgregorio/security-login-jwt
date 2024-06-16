package com.rogeriogregorio.securityloginjwt.security.services;

import com.rogeriogregorio.securityloginjwt.security.dto.UserRequest;
import com.rogeriogregorio.securityloginjwt.security.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    UserResponse registerUser(UserRequest userRequest);
}
