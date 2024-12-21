package com.rogeriogregorio.securityloginjwt;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Security Login JWT", version = "1.0", description = "API desenvolvida para Autenticação e Autorização"))
public class SecurityLoginJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityLoginJwtApplication.class, args);
	}
}
