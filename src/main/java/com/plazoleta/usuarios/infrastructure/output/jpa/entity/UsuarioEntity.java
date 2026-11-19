package com.plazoleta.usuarios.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(name = "documento_identidad", nullable = false, unique = true, length = 30)
    private String documentoDeIdentidad;

    @Column(nullable = false, length = 13)
    private String celular;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false)
    private String clave;

    @Column(name = "id_restaurante")
    private Long idRestaurante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDocumentoDeIdentidad() { return documentoDeIdentidad; }
    public void setDocumentoDeIdentidad(String documentoDeIdentidad) { this.documentoDeIdentidad = documentoDeIdentidad; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }
    public RolEntity getRol() { return rol; }
    public void setRol(RolEntity rol) { this.rol = rol; }
}
