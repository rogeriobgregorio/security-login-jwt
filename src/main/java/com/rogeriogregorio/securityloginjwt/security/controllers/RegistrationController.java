package com.rogeriogregorio.securityloginjwt.security.controllers;

import com.rogeriogregorio.securityloginjwt.security.dto.LoginResponse;
import com.rogeriogregorio.securityloginjwt.security.dto.UserRequest;
import com.rogeriogregorio.securityloginjwt.security.dto.UserResponse;
import com.rogeriogregorio.securityloginjwt.security.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registrar usuário", description = "Endpoint para registrar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Usuário já registrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao tentar registrar")
    })
    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userRequest));
    }
}
