package com.rogeriogregorio.securityloginjwt.security.services;

import com.rogeriogregorio.securityloginjwt.security.dto.UserAuthDetails;
import org.springframework.stereotype.Component;

@Component
public interface TokenService {

    String generateAuthenticationToken(UserAuthDetails user);

    String validateAuthenticationToken(String token);
}
