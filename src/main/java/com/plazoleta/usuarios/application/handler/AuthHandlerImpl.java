package com.plazoleta.usuarios.application.handler;

import com.plazoleta.usuarios.application.dto.request.LoginRequestDto;
import com.plazoleta.usuarios.application.dto.response.JwtResponseDto;
import com.plazoleta.usuarios.domain.api.IAuthServicePort;
import org.springframework.stereotype.Service;

@Service
public class AuthHandlerImpl implements IAuthHandler {

    private final IAuthServicePort authServicePort;

    public AuthHandlerImpl(IAuthServicePort authServicePort) {
        this.authServicePort = authServicePort;
    }

    @Override
    public JwtResponseDto login(LoginRequestDto loginRequestDto) {
        String token = authServicePort.login(loginRequestDto.getCorreo(), loginRequestDto.getClave());
        return new JwtResponseDto(token);
    }
}
