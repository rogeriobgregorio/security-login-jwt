package com.rogeriogregorio.securityloginjwt.security.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rogeriogregorio.securityloginjwt.security.dto.UserAuthDetails;
import com.rogeriogregorio.securityloginjwt.security.services.TokenService;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String ISSUER_NAME = "ecommerce-manager";
    private static final Instant EXPIRY_TIME = Instant.now().plus(2, ChronoUnit.HOURS);

    @Value("${api.security.token.secret}")
    private String secretKey;
    private final CatchError catchError;

    @Autowired
    public TokenServiceImpl(CatchError catchError) {
        this.catchError = catchError;
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    public String generateAuthenticationToken(UserAuthDetails userAuthDetails) {

        return catchError.run(() -> JWT
                        .create()
                        .withIssuer(ISSUER_NAME)
                        .withSubject(userAuthDetails.getUsername())
                        .withExpiresAt(EXPIRY_TIME)
                        .sign(algorithm()));
    }

    public String validateAuthenticationToken(String token) {

        return catchError.run(() -> JWT
                        .require(algorithm())
                        .withIssuer(ISSUER_NAME)
                        .build()
                        .verify(token)
                        .getSubject());
    }
}
