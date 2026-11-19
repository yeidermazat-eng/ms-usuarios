package com.plazoleta.usuarios.application.handler;

import com.plazoleta.usuarios.application.dto.request.CrearClienteRequestDto;
import com.plazoleta.usuarios.application.dto.request.CrearEmpleadoRequestDto;
import com.plazoleta.usuarios.application.dto.request.CrearPropietarioRequestDto;
import com.plazoleta.usuarios.application.dto.response.UsuarioResponseDto;
import com.plazoleta.usuarios.application.mapper.UsuarioMapper;
import com.plazoleta.usuarios.domain.api.IUsuarioServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioHandlerImpl implements IUsuarioHandler {

    private final IUsuarioServicePort usuarioServicePort;
    private final UsuarioMapper usuarioMapper;

    public UsuarioHandlerImpl(IUsuarioServicePort usuarioServicePort, UsuarioMapper usuarioMapper) {
        this.usuarioServicePort = usuarioServicePort;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuarioResponseDto crearPropietario(CrearPropietarioRequestDto dto) {
        return usuarioMapper.toResponseDto(usuarioServicePort.crearPropietario(usuarioMapper.toDomain(dto)));
    }

    @Override
    public UsuarioResponseDto crearEmpleado(CrearEmpleadoRequestDto dto) {
        return usuarioMapper.toResponseDto(usuarioServicePort.crearEmpleado(usuarioMapper.toDomain(dto)));
    }

    @Override
    public UsuarioResponseDto crearCliente(CrearClienteRequestDto dto) {
        return usuarioMapper.toResponseDto(usuarioServicePort.crearCliente(usuarioMapper.toDomain(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDto obtenerPorId(Long id) {
        return usuarioMapper.toResponseDto(usuarioServicePort.obtenerUsuarioPorId(id));
    }
}
