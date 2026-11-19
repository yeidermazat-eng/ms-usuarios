package com.plazoleta.usuarios.domain.api;

import com.plazoleta.usuarios.domain.model.Usuario;

public interface IUsuarioServicePort {
    Usuario crearPropietario(Usuario usuario);
    Usuario crearEmpleado(Usuario usuario);
    Usuario crearCliente(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorCorreo(String correo);
}
