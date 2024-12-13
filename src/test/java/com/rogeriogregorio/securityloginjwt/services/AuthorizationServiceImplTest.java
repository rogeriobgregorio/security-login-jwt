package com.rogeriogregorio.securityloginjwt.services;

import com.rogeriogregorio.securityloginjwt.security.dto.UserAuthDetails;
import com.rogeriogregorio.securityloginjwt.security.entities.User;
import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;
import com.rogeriogregorio.securityloginjwt.security.exceptions.NotFoundException;
import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.services.impl.AuthorizationServiceImpl;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CatchError catchError;

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Test
    @DisplayName("loadUserByUsername - Busca bem-sucedida retorna usuário")
    void loadUserByUsername_shouldReturnUserAuthDetails() {
        // Arrange
        User user = User.newBuilder()
                .withName("user")
                .withEmail("user@example.com")
                .withPassword("password")
                .withRole(UserRole.CLIENT)
                .build();

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(catchError.run((CatchError.FunctionWithException<Object>) any()))
                .thenAnswer(invocationOnMock -> userRepository.findByEmail("user@example.com"));

        // Act
        UserAuthDetails expectedResponse = authorizationService.loadUserByUsername("user@example.com");

        // Assert
        assertNotNull(expectedResponse, "Response should not be null");
        assertEquals("user@example.com", expectedResponse.getUsername());
        verify(userRepository, times(1)).findByEmail("user@example.com");
        verify(catchError, times(1)).run((CatchError.FunctionWithException<Object>) any());
    }

    @Test
    void loadUserByUsername_shouldThrowNotFoundException() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        when(catchError.run((CatchError.FunctionWithException<Object>) any()))
                .thenAnswer(invocationOnMock -> userRepository.findByEmail("nonexistent@example.com"));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> authorizationService.loadUserByUsername("nonexistent@example.com"),
                "Expected NotFoundException to be thrown");
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
        verify(catchError, times(1)).run((CatchError.FunctionWithException<Object>) any());
    }
}

