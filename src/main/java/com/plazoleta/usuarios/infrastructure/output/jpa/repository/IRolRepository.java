package com.plazoleta.usuarios.infrastructure.output.jpa.repository;

import com.plazoleta.usuarios.infrastructure.output.jpa.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRolRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
}
