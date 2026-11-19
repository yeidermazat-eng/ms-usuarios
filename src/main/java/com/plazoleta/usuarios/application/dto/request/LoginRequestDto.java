package com.plazoleta.usuarios.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Estructura de correo inválida")
    private String correo;

    @NotBlank(message = "La clave es obligatoria")
    private String clave;

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
