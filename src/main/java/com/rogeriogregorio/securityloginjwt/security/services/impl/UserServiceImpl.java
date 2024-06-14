package com.rogeriogregorio.securityloginjwt.security.services.impl;

import com.rogeriogregorio.securityloginjwt.security.entities.User;
import com.rogeriogregorio.securityloginjwt.security.entities.dto.UserRequest;
import com.rogeriogregorio.securityloginjwt.security.entities.dto.UserResponse;
import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;
import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import com.rogeriogregorio.securityloginjwt.security.utils.DataMapper;
import com.rogeriogregorio.securityloginjwt.security.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CatchError catchError;
    private final DataMapper dataMapper;
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder, CatchError catchError,
                           DataMapper dataMapper) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.catchError = catchError;
        this.dataMapper = dataMapper;
    }

    @Transactional
    public UserResponse registerUser(UserRequest userRequest) {

        User user = dataMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(UserRole.CLIENT);

        User savedUser = catchError.run(() -> userRepository.save(user));
        LOGGER.info("User registered: {}", savedUser);
        return dataMapper.map(savedUser, UserResponse.class);
    }
}
