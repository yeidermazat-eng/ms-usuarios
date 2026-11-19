package com.plazoleta.usuarios.infrastructure.configuration;

import com.plazoleta.usuarios.domain.api.IAuthServicePort;
import com.plazoleta.usuarios.domain.api.IUsuarioServicePort;
import com.plazoleta.usuarios.domain.spi.IJwtProviderPort;
import com.plazoleta.usuarios.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuarios.domain.spi.IRolPersistencePort;
import com.plazoleta.usuarios.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuarios.domain.usecase.AuthUseCase;
import com.plazoleta.usuarios.domain.usecase.UsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IUsuarioServicePort usuarioServicePort(IUsuarioPersistencePort usuarioPersistencePort,
                                                  IRolPersistencePort rolPersistencePort,
                                                  IPasswordEncoderPort passwordEncoderPort) {
        return new UsuarioUseCase(usuarioPersistencePort, rolPersistencePort, passwordEncoderPort);
    }

    @Bean
    public IAuthServicePort authServicePort(IUsuarioPersistencePort usuarioPersistencePort,
                                            IPasswordEncoderPort passwordEncoderPort,
                                            IJwtProviderPort jwtProviderPort) {
        return new AuthUseCase(usuarioPersistencePort, passwordEncoderPort, jwtProviderPort);
    }
}
