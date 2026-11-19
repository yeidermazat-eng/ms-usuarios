package com.plazoleta.usuarios.application.handler;

import com.plazoleta.usuarios.application.dto.request.LoginRequestDto;
import com.plazoleta.usuarios.application.dto.response.JwtResponseDto;

public interface IAuthHandler {
    JwtResponseDto login(LoginRequestDto loginRequestDto);
}
