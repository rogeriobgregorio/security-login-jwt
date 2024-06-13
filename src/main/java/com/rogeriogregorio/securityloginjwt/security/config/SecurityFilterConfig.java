package com.rogeriogregorio.securityloginjwt.security.config;

import com.rogeriogregorio.securityloginjwt.security.repositories.UserRepository;
import com.rogeriogregorio.securityloginjwt.security.services.CatchError;
import com.rogeriogregorio.securityloginjwt.security.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilterConfig extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final CatchError catchError;

    @Autowired
    public SecurityFilterConfig(TokenService tokenService,
                                UserRepository userRepository,
                                CatchError catchError) {

        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.catchError = catchError;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {

        String token = recoverToken(request);

        if (token != null) {
            String emailLogin = tokenService.validateAuthenticationToken(token);
            UserDetails user = userRepository.findByEmail(emailLogin);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        catchError.run(() -> {
            filterChain.doFilter(request, response);
            return null;
        });
    }

    private String recoverToken(HttpServletRequest httpServletRequest) {

        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}