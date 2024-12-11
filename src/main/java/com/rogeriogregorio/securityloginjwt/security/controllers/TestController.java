package com.rogeriogregorio.securityloginjwt.security.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/private")
    public ResponseEntity<String> test() {

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", "/api/v1/health-check.html")
                .build();
    }
}
