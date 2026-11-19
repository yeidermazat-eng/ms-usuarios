package com.plazoleta.usuarios.infrastructure.input.rest;

import com.plazoleta.usuarios.application.dto.request.LoginRequestDto;
import com.plazoleta.usuarios.application.dto.response.JwtResponseDto;
import com.plazoleta.usuarios.application.handler.IAuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para inicio de sesión y generación de JWT (HU 5)")
public class AuthRestController {

    private final IAuthHandler authHandler;

    public AuthRestController(IAuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica usuario con correo y clave y retorna token JWT")
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authHandler.login(loginRequestDto));
    }
}
