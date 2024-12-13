package com.rogeriogregorio.securityloginjwt.services;

import com.rogeriogregorio.securityloginjwt.security.dto.UserAuthDetails;
import com.rogeriogregorio.securityloginjwt.security.services.impl.TokenServiceImpl;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceImplTest {

    @Mock
    private CatchError catchError;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    @DisplayName("generateAuthenticationToken - Autenticação bem-sucedida retorna token")
    void generateAuthenticationToken_shouldReturnToken() {
        // Arrange
        UserAuthDetails userAuthDetails = mock(UserAuthDetails.class);
        when(catchError.run((CatchError.FunctionWithException<Object>) any())).thenReturn("mockedToken");

        // Act
        String token = tokenService.generateAuthenticationToken(userAuthDetails);

        // Assert
        assertNotNull(token, "Token should not be null");
        assertEquals("mockedToken", token);
    }

    @Test
    @DisplayName("validateAuthenticationToken - Validação bem-sucedida retorna subject")
    void validateAuthenticationToken_shouldReturnSubject() {
        // Arrange
        String token = "mockedToken";
        when(catchError.run((CatchError.FunctionWithException<Object>) any())).thenReturn("subject");

        // Act
        String subject = tokenService.validateAuthenticationToken(token);

        // Assert
        assertNotNull(subject, "Subject should not be null");
        assertEquals("subject", subject, "Should match");
    }
}

