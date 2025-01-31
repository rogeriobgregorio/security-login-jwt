package com.rogeriogregorio.securityloginjwt.security.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
@Tag(name = "Test API", description = "API para testar conexão com banco de dados")
public class TestController {

    @Operation(summary = "Teste", description = "Endpoint para teste de redirecionamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirecionamento para o health-check",
                    headers = @Header(name = "Location", description = "URL de redirecionamento para o health-check"))
    })
    @GetMapping
    public ResponseEntity<Void> test() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", "/api/v1/health-check.html")
                .build();
    }
}
