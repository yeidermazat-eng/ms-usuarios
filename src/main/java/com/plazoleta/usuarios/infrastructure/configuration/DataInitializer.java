package com.plazoleta.usuarios.infrastructure.configuration;

import com.plazoleta.usuarios.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuarios.infrastructure.output.jpa.entity.RolEntity;
import com.plazoleta.usuarios.infrastructure.output.jpa.entity.UsuarioEntity;
import com.plazoleta.usuarios.infrastructure.output.jpa.repository.IRolRepository;
import com.plazoleta.usuarios.infrastructure.output.jpa.repository.IUsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IRolRepository rolRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IPasswordEncoderPort passwordEncoder;

    public DataInitializer(IRolRepository rolRepository,
                           IUsuarioRepository usuarioRepository,
                           IPasswordEncoderPort passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        List<String> roles = List.of("ADMINISTRADOR", "PROPIETARIO", "EMPLEADO", "CLIENTE");
        for (String r : roles) {
            rolRepository.findByNombre(r).orElseGet(() ->
                    rolRepository.save(new RolEntity(null, r, "Rol " + r)));
        }

        if (!usuarioRepository.existsByCorreo("admin@plazoleta.com")) {
            RolEntity adminRol = rolRepository.findByNombre("ADMINISTRADOR").orElseThrow();
            UsuarioEntity admin = new UsuarioEntity();
            admin.setNombre("Admin");
            admin.setApellido("Plazoleta");
            admin.setDocumentoDeIdentidad("1000000001");
            admin.setCelular("+573001234567");
            admin.setFechaNacimiento(LocalDate.of(1990, 1, 1));
            admin.setCorreo("admin@plazoleta.com");
            admin.setClave(passwordEncoder.encode("Admin123*"));
            admin.setRol(adminRol);
            usuarioRepository.save(admin);
        }
    }
}
