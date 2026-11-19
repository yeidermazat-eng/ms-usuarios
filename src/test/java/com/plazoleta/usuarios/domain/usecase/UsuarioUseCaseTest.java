package com.plazoleta.usuarios.domain.usecase;

import com.plazoleta.usuarios.domain.exception.DatosInvalidosException;
import com.plazoleta.usuarios.domain.exception.MenorDeEdadException;
import com.plazoleta.usuarios.domain.model.Rol;
import com.plazoleta.usuarios.domain.model.Usuario;
import com.plazoleta.usuarios.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuarios.domain.spi.IRolPersistencePort;
import com.plazoleta.usuarios.domain.spi.IUsuarioPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;
    @Mock
    private IRolPersistencePort rolPersistencePort;
    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    private Usuario propietarioValido;

    @BeforeEach
    void setUp() {
        propietarioValido = new Usuario();
        propietarioValido.setNombre("Carlos");
        propietarioValido.setApellido("Gómez");
        propietarioValido.setDocumentoDeIdentidad("123456789");
        propietarioValido.setCelular("+573005698325");
        propietarioValido.setFechaNacimiento(LocalDate.of(1995, 5, 15));
        propietarioValido.setCorreo("carlos@restaurante.com");
        propietarioValido.setClave("Clave123*");
    }

    @Test
    void crearPropietario_Exitoso() {
        Rol rolPropietario = new Rol(2L, "PROPIETARIO", "Rol propietario");
        when(usuarioPersistencePort.existePorCorreo(any())).thenReturn(false);
        when(usuarioPersistencePort.existePorDocumento(any())).thenReturn(false);
        when(rolPersistencePort.buscarPorNombre("PROPIETARIO")).thenReturn(Optional.of(rolPropietario));
        when(passwordEncoderPort.encode("Clave123*")).thenReturn("$2a$10$encoded");
        when(usuarioPersistencePort.guardarUsuario(any())).thenAnswer(i -> i.getArgument(0));

        Usuario resultado = usuarioUseCase.crearPropietario(propietarioValido);

        assertEquals("PROPIETARIO", resultado.getRol().getNombre());
        assertEquals("$2a$10$encoded", resultado.getClave());
        verify(usuarioPersistencePort).guardarUsuario(propietarioValido);
    }

    @Test
    void crearPropietario_MenorDeEdad_LanzaExcepcion() {
        propietarioValido.setFechaNacimiento(LocalDate.now().minusYears(17));
        assertThrows(MenorDeEdadException.class, () -> usuarioUseCase.crearPropietario(propietarioValido));
        verify(usuarioPersistencePort, never()).guardarUsuario(any());
    }

    @Test
    void crearPropietario_DocumentoNoNumerico_LanzaExcepcion() {
        propietarioValido.setDocumentoDeIdentidad("123ABC");
        assertThrows(DatosInvalidosException.class, () -> usuarioUseCase.crearPropietario(propietarioValido));
    }

    @Test
    void crearEmpleado_Exitoso() {
        Usuario empleado = new Usuario();
        empleado.setNombre("Ana");
        empleado.setApellido("Pérez");
        empleado.setDocumentoDeIdentidad("987654321");
        empleado.setCelular("+573112223344");
        empleado.setCorreo("ana@restaurante.com");
        empleado.setClave("Emp123*");
        empleado.setIdRestaurante(10L);

        when(usuarioPersistencePort.existePorCorreo(any())).thenReturn(false);
        when(usuarioPersistencePort.existePorDocumento(any())).thenReturn(false);
        when(rolPersistencePort.buscarPorNombre("EMPLEADO")).thenReturn(Optional.of(new Rol(3L, "EMPLEADO", "Rol empleado")));
        when(passwordEncoderPort.encode("Emp123*")).thenReturn("$2a$10$emp");
        when(usuarioPersistencePort.guardarUsuario(any())).thenAnswer(i -> i.getArgument(0));

        Usuario resultado = usuarioUseCase.crearEmpleado(empleado);
        assertEquals("EMPLEADO", resultado.getRol().getNombre());
        assertEquals(10L, resultado.getIdRestaurante());
    }

    @Test
    void crearCliente_Exitoso() {
        Usuario cliente = new Usuario();
        cliente.setNombre("Luis");
        cliente.setApellido("Díaz");
        cliente.setDocumentoDeIdentidad("555666777");
        cliente.setCelular("+573201112233");
        cliente.setCorreo("luis@cliente.com");
        cliente.setClave("Cli123*");

        when(usuarioPersistencePort.existePorCorreo(any())).thenReturn(false);
        when(usuarioPersistencePort.existePorDocumento(any())).thenReturn(false);
        when(rolPersistencePort.buscarPorNombre("CLIENTE")).thenReturn(Optional.of(new Rol(4L, "CLIENTE", "Rol cliente")));
        when(passwordEncoderPort.encode("Cli123*")).thenReturn("$2a$10$cli");
        when(usuarioPersistencePort.guardarUsuario(any())).thenAnswer(i -> i.getArgument(0));

        Usuario resultado = usuarioUseCase.crearCliente(cliente);
        assertEquals("CLIENTE", resultado.getRol().getNombre());
    }
}
