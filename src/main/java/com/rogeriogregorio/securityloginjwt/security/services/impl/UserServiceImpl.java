package com.rogeriogregorio.securityloginjwt.security.services.impl;

import com.rogeriogregorio.securityloginjwt.security.dto.UserRequest;
import com.rogeriogregorio.securityloginjwt.security.dto.UserResponse;
import com.rogeriogregorio.securityloginjwt.security.entities.User;
import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;
import com.rogeriogregorio.securityloginjwt.security.exceptions.NotFoundException;
import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.services.UserService;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import com.rogeriogregorio.securityloginjwt.security.utils.DataMapper;
import com.rogeriogregorio.securityloginjwt.security.utils.PasswordHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordHelper passwordHelper;
    private final CatchError catchError;
    private final DataMapper dataMapper;
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordHelper passwordHelper,
                           CatchError catchError,
                           DataMapper dataMapper) {

        this.userRepository = userRepository;
        this.passwordHelper = passwordHelper;
        this.catchError = catchError;
        this.dataMapper = dataMapper;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {

        return catchError.run(() -> userRepository.findAll(pageable))
                .map(user -> dataMapper.map(user, UserResponse.class));
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {

        passwordHelper.validate(userRequest.getPassword());
        String encodedPassword = passwordHelper.enconde(userRequest.getPassword());

        User user = User.newBuilder()
                .withName(userRequest.getName())
                .withEmail(userRequest.getEmail())
                .withRole(UserRole.USER)
                .withPassword(encodedPassword)
                .build();

        User savedUser = catchError.run(() -> userRepository.save(user));
        LOGGER.info("User created: {}", savedUser);
        return dataMapper.map(savedUser, UserResponse.class);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(String id) {

        return catchError.run(() -> userRepository.findById(id))
                .map(user -> dataMapper.map(user, UserResponse.class))
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id + "."));
    }

    @Transactional
    public UserResponse updateUser(String id, UserRequest userRequest) {

        if (Objects.nonNull(userRequest.getPassword())) {
            passwordHelper.validate(userRequest.getPassword());
            String encodedPassword = passwordHelper.enconde(userRequest.getPassword());
            userRequest.setPassword(encodedPassword);
        }

        User user = getUserIfExists(id);
        dataMapper.map(userRequest, user);

        User savedUser = catchError.run(() -> userRepository.save(user));
        LOGGER.info("User updated: {}", savedUser);
        return dataMapper.map(savedUser, UserResponse.class);
    }

    @Transactional
    public UserResponse updateUserRole(String id, String userRole) {

        User updatedUser = getUserIfExists(id)
                .toBuilder()
                .withRole(UserRole.valueOf(userRole))
                .build();

        User savedUser = catchError.run(() -> userRepository.save(updatedUser));
        LOGGER.info("User role updated: {}", savedUser);
        return dataMapper.map(savedUser, UserResponse.class);
    }

    @Transactional
    public void deleteUser(String id) {

        User user = getUserIfExists(id);

        catchError.run(() -> userRepository.delete(user));
        LOGGER.warn("User deleted: {}", user);
    }

    @Transactional(readOnly = true)
    public User getUserIfExists(String id) {

        return catchError.run(() -> userRepository.findById(id))
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id + "."));
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {

        return catchError.run(() -> userRepository.findByEmail(email)
                .map(user -> dataMapper.map(user, UserResponse.class))
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email + ".")));
    }
}
