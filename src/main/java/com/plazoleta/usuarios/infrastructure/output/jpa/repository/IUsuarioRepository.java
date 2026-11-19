package com.plazoleta.usuarios.infrastructure.output.jpa.repository;

import com.plazoleta.usuarios.infrastructure.output.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByDocumentoDeIdentidad(String documentoDeIdentidad);
}
