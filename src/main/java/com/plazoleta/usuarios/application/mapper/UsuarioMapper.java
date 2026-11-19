package com.plazoleta.usuarios.application.mapper;

import com.plazoleta.usuarios.application.dto.request.CrearClienteRequestDto;
import com.plazoleta.usuarios.application.dto.request.CrearEmpleadoRequestDto;
import com.plazoleta.usuarios.application.dto.request.CrearPropietarioRequestDto;
import com.plazoleta.usuarios.application.dto.response.UsuarioResponseDto;
import com.plazoleta.usuarios.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDomain(CrearPropietarioRequestDto dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setDocumentoDeIdentidad(dto.getDocumentoDeIdentidad());
        u.setCelular(dto.getCelular());
        u.setFechaNacimiento(dto.getFechaNacimiento());
        u.setCorreo(dto.getCorreo());
        u.setClave(dto.getClave());
        return u;
    }

    public Usuario toDomain(CrearEmpleadoRequestDto dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setDocumentoDeIdentidad(dto.getDocumentoDeIdentidad());
        u.setCelular(dto.getCelular());
        u.setCorreo(dto.getCorreo());
        u.setClave(dto.getClave());
        u.setIdRestaurante(dto.getIdRestaurante());
        return u;
    }

    public Usuario toDomain(CrearClienteRequestDto dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setDocumentoDeIdentidad(dto.getDocumentoDeIdentidad());
        u.setCelular(dto.getCelular());
        u.setCorreo(dto.getCorreo());
        u.setClave(dto.getClave());
        return u;
    }

    public UsuarioResponseDto toResponseDto(Usuario u) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setApellido(u.getApellido());
        dto.setDocumentoDeIdentidad(u.getDocumentoDeIdentidad());
        dto.setCelular(u.getCelular());
        dto.setFechaNacimiento(u.getFechaNacimiento());
        dto.setCorreo(u.getCorreo());
        dto.setIdRestaurante(u.getIdRestaurante());
        if (u.getRol() != null) {
            dto.setRol(u.getRol().getNombre());
        }
        return dto;
    }
}
