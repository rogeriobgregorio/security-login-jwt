package com.rogeriogregorio.securityloginjwt.security.services.impl;

import com.rogeriogregorio.securityloginjwt.security.entities.dto.UserAuthDetails;
import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.services.AuthorizationService;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;
    private final CatchError catchError;

    @Autowired
    public AuthorizationServiceImpl(UserRepository userRepository, CatchError catchError) {
        this.userRepository = userRepository;
        this.catchError = catchError;
    }

    @Override
    public UserAuthDetails loadUserByUsername(String email) {

        return catchError.run(() -> userRepository.findByEmail(email))
                .map(UserAuthDetails::new)
                .orElseThrow(() -> new RuntimeException("User cannot be loaded by email"));
    }
}
