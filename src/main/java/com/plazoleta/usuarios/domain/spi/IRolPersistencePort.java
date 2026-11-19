package com.plazoleta.usuarios.domain.spi;

import com.plazoleta.usuarios.domain.model.Rol;
import java.util.Optional;

public interface IRolPersistencePort {
    Optional<Rol> buscarPorNombre(String nombre);
    Optional<Rol> buscarPorId(Long id);
}
