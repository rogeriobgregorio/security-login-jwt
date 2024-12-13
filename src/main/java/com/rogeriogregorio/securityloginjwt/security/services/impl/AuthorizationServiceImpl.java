package com.rogeriogregorio.securityloginjwt.security.services.impl;

import com.rogeriogregorio.securityloginjwt.security.dto.UserAuthDetails;
import com.rogeriogregorio.securityloginjwt.security.entities.User;
import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;
import com.rogeriogregorio.securityloginjwt.security.exceptions.NotFoundException;
import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.services.AuthorizationService;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Value("${api.security.password.secret}")
    private String secretPassword;
    private final UserRepository userRepository;
    private final CatchError catchError;

    @Autowired
    public AuthorizationServiceImpl(UserRepository userRepository, CatchError catchError) {
        this.userRepository = userRepository;
        this.catchError = catchError;
    }

    @Override
    public UserAuthDetails loadUserByUsername(String email) {

        return catchError.run(() -> userRepository.findByEmail(email))
                .map(UserAuthDetails::new)
                .orElseThrow(() -> new NotFoundException("User not found: " + email));
    }

    @PostConstruct
    private void createDefaultAdmin() {

        String encodedPassword = new BCryptPasswordEncoder().encode(secretPassword);

        User admin = User.newBuilder()
                .withName("Administrador")
                .withEmail("admin@email.com")
                .withPassword(encodedPassword)
                .withRole(UserRole.ADMIN)
                .build();

        catchError.run(() -> userRepository.save(admin));
    }

    @PreDestroy
    public void deleteDefaultAdminOnShutdown() {

        Optional<User> admin = userRepository.findByEmail("admin@email.com");
        catchError.run(() ->admin.ifPresent(userRepository::delete));
    }
}
