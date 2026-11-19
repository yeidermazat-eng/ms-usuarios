package com.plazoleta.usuarios.domain.spi;

import com.plazoleta.usuarios.domain.model.Usuario;

public interface IJwtProviderPort {
    String generarToken(Usuario usuario);
}
