package com.rogeriogregorio.securityloginjwt.security.services.impl;

import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.services.AuthorizationService;
import com.rogeriogregorio.securityloginjwt.security.services.CatchError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
    public UserDetails loadUserByUsername(String email) {
        return catchError.run(() -> userRepository.findByEmail(email));
    }
}
