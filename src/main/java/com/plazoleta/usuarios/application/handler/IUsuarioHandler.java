package com.plazoleta.usuarios.application.handler;

import com.plazoleta.usuarios.application.dto.request.CrearClienteRequestDto;
import com.plazoleta.usuarios.application.dto.request.CrearEmpleadoRequestDto;
import com.plazoleta.usuarios.application.dto.request.CrearPropietarioRequestDto;
import com.plazoleta.usuarios.application.dto.response.UsuarioResponseDto;

public interface IUsuarioHandler {
    UsuarioResponseDto crearPropietario(CrearPropietarioRequestDto dto);
    UsuarioResponseDto crearEmpleado(CrearEmpleadoRequestDto dto);
    UsuarioResponseDto crearCliente(CrearClienteRequestDto dto);
    UsuarioResponseDto obtenerPorId(Long id);
}
