package com.rogeriogregorio.securityloginjwt.services;

import com.rogeriogregorio.securityloginjwt.security.dto.UserRequest;
import com.rogeriogregorio.securityloginjwt.security.dto.UserResponse;
import com.rogeriogregorio.securityloginjwt.security.entities.User;
import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;
import com.rogeriogregorio.securityloginjwt.security.exceptions.NotFoundException;
import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.services.impl.UserServiceImpl;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import com.rogeriogregorio.securityloginjwt.security.utils.DataMapper;
import com.rogeriogregorio.securityloginjwt.security.utils.PasswordHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHelper passwordHelper;

    @Mock
    private CatchError catchError;

    @Mock
    private DataMapper dataMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static User user;
    private static UserRequest userRequest;
    private static UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, passwordHelper, catchError, dataMapper);

        user = User.newBuilder()
                .withId("1")
                .withName("Teste")
                .withEmail("teste@email.com")
                .withPassword("Teste123$")
                .withRole(UserRole.ADMIN)
                .build();

        userRequest = UserRequest.newBuilder()
                .withName("Teste")
                .withEmail("teste@email.com")
                .withPassword("Teste123$")
                .withRole(UserRole.ADMIN)
                .build();

        userResponse = UserResponse.newBuilder()
                .withId("1")
                .withName("Teste")
                .withEmail("teste@email.com")
                .withRole(UserRole.ADMIN)
                .build();
    }

    @Test
    @DisplayName("getAllUsers - Busca bem-sucedida retorna lista de usuários")
    void shouldGetAllUsers() {
        UserResponse expectedResponse = userResponse;
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(catchError.run(any(CatchError.FunctionWithException.class))).thenReturn(userPage);
        when(dataMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(userResponse);

        Page<UserResponse> actualResponse = userService.getAllUsers(Pageable.unpaged());

        assertNotNull(actualResponse, "User should not be null");
        assertEquals(1, actualResponse.getTotalElements());
        assertEquals(expectedResponse, actualResponse.getContent().get(0));
        verify(catchError).run(any(CatchError.FunctionWithException.class));
    }

    @Test
    @DisplayName("createUser - Registro bem-sucedida retorna usuário registrado")
    void shouldCreateUser() {
        // Arrange
        UserResponse expectedResponse = userResponse;

        doNothing().when(passwordHelper).validate(userRequest.getPassword());
        when(passwordHelper.enconde(userRequest.getPassword())).thenReturn(String.valueOf(String.class));
        when(userRepository.save(user)).thenReturn(user);
        when(catchError.run(any(CatchError.FunctionWithException.class))).thenAnswer(invocation -> userRepository.save(user));
        when(dataMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);

        // Act
        UserResponse actualResponse = userService.createUser(userRequest);

        // Assert
        assertNotNull(actualResponse, "User should not be null");
        assertEquals(expectedResponse, actualResponse, "Expected and actual responses should be equal");
        verify(passwordHelper, times(1)).validate(userRequest.getPassword());
        verify(passwordHelper, times(1)).enconde(userRequest.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(dataMapper, times(1)).map(user, UserResponse.class);
        verify(catchError, times(1)).run(any(CatchError.FunctionWithException.class));
    }

    @Test
    @DisplayName("updateUser - Atualização bem-sucedida retorna usuário atualizado")
    void shouldUpdateUserRole() {
        // Arrange
        UserResponse expectedResponse = userResponse;

        when(dataMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(dataMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);
        when(catchError.run(any(CatchError.FunctionWithException.class))).then(invocation -> invocation
                .getArgument(0, CatchError.FunctionWithException.class).run());

        // Act
        UserResponse actualResponse = userService.updateUserRole(user.getId(), userRequest.getRole().toString());

        // Assert
        assertNotNull(actualResponse, "User should not be null");
        assertEquals(expectedResponse.getId(), actualResponse.getId(), "IDs should match");
        assertEquals(expectedResponse, actualResponse, "Expected and actual responses should be equal");
        verify(dataMapper, times(1)).map(user, UserResponse.class);
        verify(userRepository, times(1)).save(user);
        verify(catchError, times(2)).run(any(CatchError.FunctionWithException.class));
    }

    @Test
    @DisplayName("getUserById - Busca bem-sucedida retorna usuário")
    void shouldGetUserById() {
        // Arrange
        UserResponse expectedResponse = userResponse;

        when(dataMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(catchError.run(any(CatchError.FunctionWithException.class))).thenAnswer(invocation -> userRepository.findById(user.getId()));

        // Act
        UserResponse actualResponse = userService.getUserById(user.getId());

        // Assert
        assertNotNull(actualResponse, "User should not be null");
        assertEquals(expectedResponse.getId(), actualResponse.getId(), "IDs should match");
        assertEquals(expectedResponse, actualResponse, "Expected and actual responses should be equal");
        verify(dataMapper, times(1)).map(user, UserResponse.class);
        verify(userRepository, times(1)).findById(user.getId());
        verify(catchError, times(1)).run(any(CatchError.FunctionWithException.class));
    }

    @Test
    @DisplayName("getUserById - Exceção ao tentar buscar usuário inexistente")
    void shouldThrowExceptionWhenUserNotFoundById() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        when(catchError.run(any(CatchError.FunctionWithException.class))).thenAnswer(invocation -> userRepository.findById(user.getId()));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.getUserById("1"),
                "Expected NotFoundException to be thrown");
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, never()).save(user);
        verify(catchError, times(1)).run(any(CatchError.FunctionWithException.class));
    }

    @Test
    @DisplayName("updateUser - Atualização bem-sucedida retorna usuário atualizado")
    void shouldUpdateUser() {
        // Arrange
        UserResponse expectedResponse = userResponse;

        doNothing().when(passwordHelper).validate(userRequest.getPassword());
        when(passwordHelper.enconde(userRequest.getPassword())).thenReturn(userRequest.getPassword());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(dataMapper.map(userRequest, user)).thenReturn(user);
        when(dataMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);
        when(catchError.run(any(CatchError.FunctionWithException.class))).then(invocation -> invocation
                .getArgument(0, CatchError.FunctionWithException.class).run());

        // Act
        UserResponse actualResponse = userService.updateUser(user.getId(), userRequest);

        // Assert
        assertNotNull(actualResponse, "User should not be null");
        assertEquals(expectedResponse.getId(), actualResponse.getId(), "IDs should match");
        assertEquals(expectedResponse, actualResponse, "Expected and actual should match");
        verify(passwordHelper, times(1)).validate(userRequest.getPassword());
        verify(passwordHelper, times(1)).enconde(userRequest.getPassword());
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);
        verify(dataMapper, times(1)).map(userRequest, user);
        verify(dataMapper, times(1)).map(user, UserResponse.class);
        verify(catchError, times(2)).run(any(CatchError.FunctionWithException.class));
    }

    @Test
    @DisplayName("deleteUser - Exclusão bem-sucedida do usuário")
    void shouldDeleteUser() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(catchError.run(any(CatchError.FunctionWithException.class))).then(invocation -> userRepository.findById(user.getId()));
        doAnswer(invocation -> {
            userRepository.delete(user);
            return null;
        }).when(catchError).run(any(CatchError.ProcedureWithException.class));
        doNothing().when(userRepository).delete(user);

        // Act
        userService.deleteUser(user.getId());

        // Assert
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
        verify(catchError, times(1)).run(any(CatchError.FunctionWithException.class));
        verify(catchError, times(1)).run(any(CatchError.ProcedureWithException.class));
    }
}

