package com.rogeriogregorio.securityloginjwt.security.services.impl;

import com.rogeriogregorio.securityloginjwt.security.entities.dto.UserAuthDetails;
import com.rogeriogregorio.securityloginjwt.security.entities.dto.LoginRequest;
import com.rogeriogregorio.securityloginjwt.security.entities.dto.LoginResponse;
import com.rogeriogregorio.securityloginjwt.security.services.AuthenticationService;
import com.rogeriogregorio.securityloginjwt.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     TokenService tokenService) {

        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authenticate = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateAuthenticationToken((UserAuthDetails) authenticate.getPrincipal());
        return new LoginResponse(token);
    }
}
