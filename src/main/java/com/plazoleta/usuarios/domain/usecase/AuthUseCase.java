package com.plazoleta.usuarios.domain.usecase;

import com.plazoleta.usuarios.domain.api.IAuthServicePort;
import com.plazoleta.usuarios.domain.exception.CredencialesInvalidasException;
import com.plazoleta.usuarios.domain.model.Usuario;
import com.plazoleta.usuarios.domain.spi.IJwtProviderPort;
import com.plazoleta.usuarios.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuarios.domain.spi.IUsuarioPersistencePort;

public class AuthUseCase implements IAuthServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IJwtProviderPort jwtProviderPort;

    public AuthUseCase(IUsuarioPersistencePort usuarioPersistencePort,
                       IPasswordEncoderPort passwordEncoderPort,
                       IJwtProviderPort jwtProviderPort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtProviderPort = jwtProviderPort;
    }

    @Override
    public String login(String correo, String clave) {
        Usuario usuario = usuarioPersistencePort.buscarPorCorreo(correo)
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o clave incorrectos."));
        if (!passwordEncoderPort.matches(clave, usuario.getClave())) {
            throw new CredencialesInvalidasException("Correo o clave incorrectos.");
        }
        return jwtProviderPort.generarToken(usuario);
    }
}
