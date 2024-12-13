package com.rogeriogregorio.securityloginjwt.services;

import com.rogeriogregorio.securityloginjwt.security.dto.LoginRequest;
import com.rogeriogregorio.securityloginjwt.security.dto.LoginResponse;
import com.rogeriogregorio.securityloginjwt.security.dto.UserAuthDetails;
import com.rogeriogregorio.securityloginjwt.security.dto.UserResponse;
import com.rogeriogregorio.securityloginjwt.security.services.TokenService;
import com.rogeriogregorio.securityloginjwt.security.services.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    @DisplayName("authenticateUser - Autenticação bem-sucedida retorna token")
    void authenticateUser_shouldReturnToken() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        UserAuthDetails userAuthDetails = mock(UserAuthDetails.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userAuthDetails);
        when(tokenService.generateAuthenticationToken(userAuthDetails)).thenReturn("token123");

        // Act
        LoginResponse expectedResponse = authenticationService.authenticateUser(loginRequest);

        // Assert
        assertNotNull(expectedResponse, "Response should not be null");
        assertEquals("token123", expectedResponse.getToken(),
                "Expected and actual responses should be equal");
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication, times(1)).getPrincipal();
        verify(tokenService, times(1)).generateAuthenticationToken(userAuthDetails);
    }
}

