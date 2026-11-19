package com.plazoleta.usuarios.infrastructure.input.rest;

import com.plazoleta.usuarios.application.dto.request.CrearClienteRequestDto;
import com.plazoleta.usuarios.application.dto.request.CrearEmpleadoRequestDto;
import com.plazoleta.usuarios.application.dto.request.CrearPropietarioRequestDto;
import com.plazoleta.usuarios.application.dto.response.UsuarioResponseDto;
import com.plazoleta.usuarios.application.handler.IUsuarioHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestión de cuentas de Propietario, Empleado y Cliente (HU 1, HU 6, HU 8)")
public class UsuarioRestController {

    private final IUsuarioHandler usuarioHandler;

    public UsuarioRestController(IUsuarioHandler usuarioHandler) {
        this.usuarioHandler = usuarioHandler;
    }

    @Operation(summary = "Crear cuenta de Propietario (HU 1)", description = "Solo ejecutable por un ADMINISTRADOR")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/propietario")
    public ResponseEntity<UsuarioResponseDto> crearPropietario(@Valid @RequestBody CrearPropietarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioHandler.crearPropietario(dto));
    }

    @Operation(summary = "Crear cuenta de Empleado (HU 6)", description = "Solo ejecutable por un PROPIETARIO")
    @PreAuthorize("hasRole('PROPIETARIO')")
    @PostMapping("/empleado")
    public ResponseEntity<UsuarioResponseDto> crearEmpleado(@Valid @RequestBody CrearEmpleadoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioHandler.crearEmpleado(dto));
    }

    @Operation(summary = "Crear cuenta de Cliente (HU 8)", description = "Registro público para clientes")
    @PostMapping("/cliente")
    public ResponseEntity<UsuarioResponseDto> crearCliente(@Valid @RequestBody CrearClienteRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioHandler.crearCliente(dto));
    }

    @Operation(summary = "Obtener usuario por ID", description = "Consulta información y rol de un usuario")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioHandler.obtenerPorId(id));
    }
}
