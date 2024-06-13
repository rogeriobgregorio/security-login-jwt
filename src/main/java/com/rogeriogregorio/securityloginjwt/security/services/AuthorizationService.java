package com.rogeriogregorio.securityloginjwt.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public interface AuthorizationService extends UserDetailsService {
}
