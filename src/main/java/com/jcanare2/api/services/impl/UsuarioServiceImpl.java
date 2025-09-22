package com.jcanare2.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcanare2.api.dto.UsuarioCreationRequestDTO;
import com.jcanare2.api.dto.UsuarioUpdateRequestDTO;
import com.jcanare2.api.entity.Persona;
import com.jcanare2.api.entity.Rol;
import com.jcanare2.api.entity.Usuario;
import com.jcanare2.api.exceptions.NotFoundException;
import com.jcanare2.api.repository.PersonaRepository;
import com.jcanare2.api.repository.RolRepository;
import com.jcanare2.api.repository.UsuarioRepository;
import com.jcanare2.api.services.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    private final UsuarioRepository usuarioRepository;

    private final PersonaRepository personaRepository;

    private final RolRepository rolRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PersonaRepository personaRepository, 
    							RolRepository rolRepository, PasswordEncoder passwordEncoder) {
    	this.usuarioRepository = usuarioRepository;
    	this.personaRepository = personaRepository;
    	this.rolRepository = rolRepository;
    	this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Crear un nuevo usuario tras validación básica.
     * @param usuario el usuario a crear
     * @return Usuario creado y persistido
     */
    @Override
    @Transactional
    public Usuario createUsuario(UsuarioCreationRequestDTO usuarioDTO, Set<Long> rolIds) {
        
    	// Verifica si ya existe un usuario con el mismo email
    	usuarioRepository.findByEmail(usuarioDTO.getEmail())
        .ifPresent(u -> {
            throw new DuplicateKeyException("Ya existe un usuario con este email: " + u.getEmail());
        });
    	
    	usuarioRepository.findByUsername(usuarioDTO.getUsername())
        .ifPresent(u -> {
            throw new DuplicateKeyException("Ya existe un usuario con este username: " + u.getUsername());
        });
    	
    	Persona persona = new Persona(usuarioDTO.getDocumento(), usuarioDTO.getNombres(), usuarioDTO.getApellidos());
    	persona = personaRepository.save(persona);

        Usuario usuario = Usuario.builder()
                .username(usuarioDTO.getUsername())
                .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                .email(usuarioDTO.getEmail())
                .enabled(true)
                .persona(persona)
                .build();

        Set<Rol> roles = rolRepository.findAllById(rolIds).stream().collect(Collectors.toSet());
        usuario.setRoles(roles);

        usuario = usuarioRepository.save(usuario);

        return usuario;
    }

    @Override
    @Transactional
    public Usuario updateUsuario(Long id, UsuarioUpdateRequestDTO usuarioDTO) {
        // 1. Encontrar el usuario o lanzar una excepción si no existe
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));
        
        usuarioRepository.findByUsername(usuarioDTO.getUsername())
        .ifPresent(u -> {
            throw new DuplicateKeyException("Ya existe un usuario con este username: " + u.getUsername());
        });

        // 2. Actualizar campos solo si se proporcionan en el DTO
        if (usuarioDTO.getUsername() != null) {
            usuario.setUsername(usuarioDTO.getUsername());
        }
        if (usuarioDTO.getEmail() != null) {
        	usuarioDTO.setEmail(usuarioDTO.getEmail());
        }
        
        if (usuarioDTO.getEnabled() != null) {
        	usuarioDTO.setEnabled(usuarioDTO.getEnabled());
        }

        // 3. Actualizar la persona si el ID se proporciona
        if (usuarioDTO.getPersonaId() != null) {
            Persona persona = personaRepository.findById(usuarioDTO.getPersonaId())
                .orElseThrow(() -> new NotFoundException("Persona no encontrada con id: " + usuarioDTO.getPersonaId()));
            usuario.setPersona(persona);
        }

        // 4. Actualizar roles si se proporcionan
        if (usuarioDTO.getRolIds() != null && !usuarioDTO.getRolIds().isEmpty()) {
            Set<Rol> roles = rolRepository.findAllById(usuarioDTO.getRolIds()).stream().collect(Collectors.toSet());
            usuario.setRoles(roles);
        }
        
        // 5. Guardar la entidad actualizada
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario cambiarPassword(Long usuarioId, String contraseñaActual, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + usuarioId));

        // Validar que la contraseña actual coincida con la almacenada
        if (!passwordEncoder.matches(contraseñaActual, usuario.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        // Codificar y actualizar la nueva contraseña
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        return usuarioRepository.save(usuario);
    }


    @Override
    public void deleteUsuario(Long id) {
        boolean exists = usuarioRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}