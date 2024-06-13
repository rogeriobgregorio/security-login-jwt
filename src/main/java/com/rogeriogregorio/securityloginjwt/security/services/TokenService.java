package com.rogeriogregorio.securityloginjwt.security.services;

import com.rogeriogregorio.securityloginjwt.security.entities.User;
import org.springframework.stereotype.Component;

@Component
public interface TokenService {

    String generateAuthenticationToken(User user);

    String validateAuthenticationToken(String token);
}
