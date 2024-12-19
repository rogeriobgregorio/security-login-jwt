package com.rogeriogregorio.securityloginjwt.security.utils.strategies.password;

import com.rogeriogregorio.securityloginjwt.security.utils.strategies.PasswordStrategy;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidatePasswordSpecialChar implements PasswordStrategy {

    public boolean validatePassword(String password) {
        return Pattern.matches(".*\\W.*", password);
    }

    public String getRequirement() {
        return "one special character";
    }
}
