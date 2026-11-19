package com.plazoleta.usuarios.domain.usecase;

import com.plazoleta.usuarios.domain.exception.CredencialesInvalidasException;
import com.plazoleta.usuarios.domain.model.Rol;
import com.plazoleta.usuarios.domain.model.Usuario;
import com.plazoleta.usuarios.domain.spi.IJwtProviderPort;
import com.plazoleta.usuarios.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuarios.domain.spi.IUsuarioPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;
    @Mock
    private IPasswordEncoderPort passwordEncoderPort;
    @Mock
    private IJwtProviderPort jwtProviderPort;

    @InjectMocks
    private AuthUseCase authUseCase;

    @Test
    void login_Exitoso_RetornaToken() {
        Usuario u = new Usuario();
        u.setCorreo("admin@plazoleta.com");
        u.setClave("$2a$10$hash");
        u.setRol(new Rol(1L, "ADMINISTRADOR", "Admin"));

        when(usuarioPersistencePort.buscarPorCorreo("admin@plazoleta.com")).thenReturn(Optional.of(u));
        when(passwordEncoderPort.matches("Admin123*", "$2a$10$hash")).thenReturn(true);
        when(jwtProviderPort.generarToken(u)).thenReturn("jwt-token-valido");

        String token = authUseCase.login("admin@plazoleta.com", "Admin123*");
        assertEquals("jwt-token-valido", token);
    }

    @Test
    void login_ClaveIncorrecta_LanzaExcepcion() {
        Usuario u = new Usuario();
        u.setCorreo("admin@plazoleta.com");
        u.setClave("$2a$10$hash");

        when(usuarioPersistencePort.buscarPorCorreo("admin@plazoleta.com")).thenReturn(Optional.of(u));
        when(passwordEncoderPort.matches("Incorrecta", "$2a$10$hash")).thenReturn(false);

        assertThrows(CredencialesInvalidasException.class,
                () -> authUseCase.login("admin@plazoleta.com", "Incorrecta"));
    }
}
