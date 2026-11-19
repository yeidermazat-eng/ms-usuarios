package com.plazoleta.usuarios.infrastructure.output.jpa.mapper;

import com.plazoleta.usuarios.domain.model.Rol;
import com.plazoleta.usuarios.domain.model.Usuario;
import com.plazoleta.usuarios.infrastructure.output.jpa.entity.RolEntity;
import com.plazoleta.usuarios.infrastructure.output.jpa.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityMapper {

    public Rol toDomain(RolEntity entity) {
        if (entity == null) return null;
        return new Rol(entity.getId(), entity.getNombre(), entity.getDescripcion());
    }

    public RolEntity toEntity(Rol rol) {
        if (rol == null) return null;
        return new RolEntity(rol.getId(), rol.getNombre(), rol.getDescripcion());
    }

    public Usuario toDomain(UsuarioEntity e) {
        if (e == null) return null;
        return new Usuario(
                e.getId(),
                e.getNombre(),
                e.getApellido(),
                e.getDocumentoDeIdentidad(),
                e.getCelular(),
                e.getFechaNacimiento(),
                e.getCorreo(),
                e.getClave(),
                e.getIdRestaurante(),
                toDomain(e.getRol())
        );
    }

    public UsuarioEntity toEntity(Usuario u) {
        if (u == null) return null;
        UsuarioEntity e = new UsuarioEntity();
        e.setId(u.getId());
        e.setNombre(u.getNombre());
        e.setApellido(u.getApellido());
        e.setDocumentoDeIdentidad(u.getDocumentoDeIdentidad());
        e.setCelular(u.getCelular());
        e.setFechaNacimiento(u.getFechaNacimiento());
        e.setCorreo(u.getCorreo());
        e.setClave(u.getClave());
        e.setIdRestaurante(u.getIdRestaurante());
        e.setRol(toEntity(u.getRol()));
        return e;
    }
}
