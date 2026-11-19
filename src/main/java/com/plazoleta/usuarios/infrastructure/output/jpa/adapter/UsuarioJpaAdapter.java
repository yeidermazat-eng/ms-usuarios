package com.plazoleta.usuarios.infrastructure.output.jpa.adapter;

import com.plazoleta.usuarios.domain.model.Usuario;
import com.plazoleta.usuarios.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuarios.infrastructure.output.jpa.mapper.UsuarioEntityMapper;
import com.plazoleta.usuarios.infrastructure.output.jpa.repository.IUsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository usuarioRepository;
    private final UsuarioEntityMapper mapper;

    public UsuarioJpaAdapter(IUsuarioRepository usuarioRepository, UsuarioEntityMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return mapper.toDomain(usuarioRepository.save(mapper.toEntity(usuario)));
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).map(mapper::toDomain);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public boolean existePorDocumento(String documento) {
        return usuarioRepository.existsByDocumentoDeIdentidad(documento);
    }
}
