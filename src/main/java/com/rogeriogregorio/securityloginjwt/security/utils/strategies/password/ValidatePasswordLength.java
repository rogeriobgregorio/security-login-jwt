package com.rogeriogregorio.securityloginjwt.security.utils.strategies.password;

import com.rogeriogregorio.securityloginjwt.security.utils.strategies.PasswordStrategy;
import org.springframework.stereotype.Component;

@Component
public class ValidatePasswordLength implements PasswordStrategy {

    public boolean validatePassword(String password) {

        return password.length() >= 8;
    }

    public String getRequirement() {
        return "eight characters";
    }
}
