package com.rogeriogregorio.securityloginjwt.security.utils.strategies;

import org.springframework.stereotype.Component;

@Component
public interface PasswordStrategy {

    boolean validatePassword(String password);

    String getRequirement();
}
