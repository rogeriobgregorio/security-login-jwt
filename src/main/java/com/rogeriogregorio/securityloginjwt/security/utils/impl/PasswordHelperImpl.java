package com.rogeriogregorio.securityloginjwt.security.utils.impl;

import com.rogeriogregorio.securityloginjwt.security.exceptions.PasswordException;
import com.rogeriogregorio.securityloginjwt.security.utils.PasswordHelper;
import com.rogeriogregorio.securityloginjwt.security.utils.strategies.PasswordStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordHelperImpl implements PasswordHelper {

    private final PasswordEncoder passwordEncoder;
    private final List<PasswordStrategy> passwordValidators;

    public PasswordHelperImpl(PasswordEncoder passwordEncoder,
                              List<PasswordStrategy> passwordValidators) {

        this.passwordEncoder = passwordEncoder;
        this.passwordValidators = passwordValidators;
    }

    public void validate(String password) {

        List<String> failures = new ArrayList<>();

        for (PasswordStrategy strategy : passwordValidators) {
            if (!strategy.validatePassword(password)) {
                failures.add(strategy.getRequirement());
            }
        }

        if (!failures.isEmpty()) {
            throw new PasswordException("The password must have at least: " + failures + ".");
        }
    }

    public String enconde(String password) {
        return passwordEncoder.encode(password);
    }
}
