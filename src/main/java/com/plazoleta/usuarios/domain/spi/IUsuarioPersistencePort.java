package com.plazoleta.usuarios.domain.spi;

import com.plazoleta.usuarios.domain.model.Usuario;
import java.util.Optional;

public interface IUsuarioPersistencePort {
    Usuario guardarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorCorreo(String correo);
    boolean existePorCorreo(String correo);
    boolean existePorDocumento(String documento);
}
