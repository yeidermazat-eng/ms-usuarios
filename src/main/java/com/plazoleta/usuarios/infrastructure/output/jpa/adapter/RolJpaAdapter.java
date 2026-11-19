package com.plazoleta.usuarios.infrastructure.output.jpa.adapter;

import com.plazoleta.usuarios.domain.model.Rol;
import com.plazoleta.usuarios.domain.spi.IRolPersistencePort;
import com.plazoleta.usuarios.infrastructure.output.jpa.mapper.UsuarioEntityMapper;
import com.plazoleta.usuarios.infrastructure.output.jpa.repository.IRolRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RolJpaAdapter implements IRolPersistencePort {

    private final IRolRepository rolRepository;
    private final UsuarioEntityMapper mapper;

    public RolJpaAdapter(IRolRepository rolRepository, UsuarioEntityMapper mapper) {
        this.rolRepository = rolRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Rol> buscarPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre).map(mapper::toDomain);
    }

    @Override
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id).map(mapper::toDomain);
    }
}
