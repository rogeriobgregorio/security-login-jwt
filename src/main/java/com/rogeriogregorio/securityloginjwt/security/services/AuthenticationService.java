package com.rogeriogregorio.securityloginjwt.security.services;

import com.rogeriogregorio.securityloginjwt.security.dto.LoginRequest;
import com.rogeriogregorio.securityloginjwt.security.dto.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {

    LoginResponse authenticateUser(LoginRequest loginRequest);
}
