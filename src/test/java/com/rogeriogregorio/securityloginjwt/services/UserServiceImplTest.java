package com.rogeriogregorio.securityloginjwt.services;

import com.rogeriogregorio.securityloginjwt.security.dto.UserRequest;
import com.rogeriogregorio.securityloginjwt.security.dto.UserResponse;
import com.rogeriogregorio.securityloginjwt.security.entities.User;
import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;
import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.services.impl.UserServiceImpl;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import com.rogeriogregorio.securityloginjwt.security.utils.DataMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CatchError catchError;

    @Mock
    private DataMapper dataMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("registerUser - Registro bem-sucedida retorna usuário registrado")
    void registerUser_shouldReturnUserResponse() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        UserRequest userRequest = new UserRequest(uuid, "Administrador", "11911111111",
                "admin@email.com", "11122233344", "password", UserRole.ADMIN);

        User user = User.newBuilder()
                .withName("Administrador")
                .withEmail("admin@email.com")
                .withPassword("encodedPassword")
                .withRole(UserRole.ADMIN)
                .build();

        UserResponse expectedResponse = new UserResponse(uuid, "Administrador",
                "admin@email.com", UserRole.ADMIN);
        
        when(dataMapper.map(userRequest, User.class)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(catchError.run((CatchError.FunctionWithException<Object>) any()))
                .thenAnswer(invocationOnMock -> userRepository.save(user));
        when(dataMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);

        // Act
        UserResponse actualResponse = userService.createUser(userRequest);

        // Assert
        assertNotNull(actualResponse, "User should not be null");
        assertEquals(expectedResponse, actualResponse, "Expected and actual responses should be equal");
        verify(passwordEncoder, times(1)).encode(userRequest.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(dataMapper, times(1)).map(user, UserResponse.class);
        verify(catchError, times(1)).run(any(CatchError.FunctionWithException.class));
    }
}

