package com.rogeriogregorio.securityloginjwt.security.controllers;

import com.rogeriogregorio.securityloginjwt.security.entities.dto.LoginRequest;
import com.rogeriogregorio.securityloginjwt.security.entities.dto.LoginResponse;
import com.rogeriogregorio.securityloginjwt.security.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authenticate")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> authenticate(
            @Valid @RequestBody LoginRequest loginRequest) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authenticationService.authenticateUser(loginRequest));
    }
}