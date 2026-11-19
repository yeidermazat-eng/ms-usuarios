package com.plazoleta.usuarios.domain.usecase;

import com.plazoleta.usuarios.domain.api.IUsuarioServicePort;
import com.plazoleta.usuarios.domain.exception.DatosInvalidosException;
import com.plazoleta.usuarios.domain.exception.MenorDeEdadException;
import com.plazoleta.usuarios.domain.exception.RecursoNoEncontradoException;
import com.plazoleta.usuarios.domain.exception.UsuarioYaExisteException;
import com.plazoleta.usuarios.domain.model.Rol;
import com.plazoleta.usuarios.domain.model.Usuario;
import com.plazoleta.usuarios.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuarios.domain.spi.IRolPersistencePort;
import com.plazoleta.usuarios.domain.spi.IUsuarioPersistencePort;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UsuarioUseCase implements IUsuarioServicePort {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern DOCUMENTO_PATTERN = Pattern.compile("^[0-9]+$");
    private static final Pattern CELULAR_PATTERN = Pattern.compile("^\\+?[0-9]{1,12}$");

    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IRolPersistencePort rolPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort,
                          IRolPersistencePort rolPersistencePort,
                          IPasswordEncoderPort passwordEncoderPort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.rolPersistencePort = rolPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public Usuario crearPropietario(Usuario usuario) {
        validarCamposComunes(usuario);
        if (usuario.getFechaNacimiento() == null) {
            throw new DatosInvalidosException("La fecha de nacimiento es obligatoria para el propietario.");
        }
        int edad = Period.between(usuario.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new MenorDeEdadException("El propietario debe ser mayor de edad (mínimo 18 años).");
        }
        return registrarConRol(usuario, "PROPIETARIO");
    }

    @Override
    public Usuario crearEmpleado(Usuario usuario) {
        validarCamposComunes(usuario);
        if (usuario.getIdRestaurante() == null) {
            throw new DatosInvalidosException("El empleado debe estar asociado a un restaurante.");
        }
        return registrarConRol(usuario, "EMPLEADO");
    }

    @Override
    public Usuario crearCliente(Usuario usuario) {
        validarCamposComunes(usuario);
        return registrarConRol(usuario, "CLIENTE");
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioPersistencePort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        return usuarioPersistencePort.buscarPorCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con correo: " + correo));
    }

    private Usuario registrarConRol(Usuario usuario, String nombreRol) {
        if (usuarioPersistencePort.existePorCorreo(usuario.getCorreo())) {
            throw new UsuarioYaExisteException("Ya existe un usuario registrado con el correo: " + usuario.getCorreo());
        }
        if (usuarioPersistencePort.existePorDocumento(usuario.getDocumentoDeIdentidad())) {
            throw new UsuarioYaExisteException("Ya existe un usuario registrado con el documento: " + usuario.getDocumentoDeIdentidad());
        }
        Rol rol = rolPersistencePort.buscarPorNombre(nombreRol)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado: " + nombreRol));
        usuario.setRol(rol);
        usuario.setClave(passwordEncoderPort.encode(usuario.getClave()));
        return usuarioPersistencePort.guardarUsuario(usuario);
    }

    private void validarCamposComunes(Usuario usuario) {
        if (usuario == null) {
            throw new DatosInvalidosException("Los datos del usuario son obligatorios.");
        }
        if (esVacio(usuario.getNombre()) || esVacio(usuario.getApellido())) {
            throw new DatosInvalidosException("Nombre y apellido son obligatorios.");
        }
        if (esVacio(usuario.getDocumentoDeIdentidad()) || !DOCUMENTO_PATTERN.matcher(usuario.getDocumentoDeIdentidad()).matches()) {
            throw new DatosInvalidosException("El documento de identidad debe ser únicamente numérico.");
        }
        if (esVacio(usuario.getCelular()) || usuario.getCelular().length() > 13 || !CELULAR_PATTERN.matcher(usuario.getCelular()).matches()) {
            throw new DatosInvalidosException("El celular debe contener máximo 13 caracteres numéricos y opcionalmente el símbolo + al inicio.");
        }
        if (esVacio(usuario.getCorreo()) || !EMAIL_PATTERN.matcher(usuario.getCorreo()).matches()) {
            throw new DatosInvalidosException("La estructura del correo electrónico no es válida.");
        }
        if (esVacio(usuario.getClave())) {
            throw new DatosInvalidosException("La clave es obligatoria.");
        }
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
