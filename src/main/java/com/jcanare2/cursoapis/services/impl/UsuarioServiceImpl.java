package com.jcanare2.cursoapis.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcanare2.cursoapis.entity.Persona;
import com.jcanare2.cursoapis.entity.Rol;
import com.jcanare2.cursoapis.entity.Usuario;
import com.jcanare2.cursoapis.exceptions.NotFoundException;
import com.jcanare2.cursoapis.repository.PersonaRepository;
import com.jcanare2.cursoapis.repository.RolRepository;
import com.jcanare2.cursoapis.repository.UsuarioRepository;
import com.jcanare2.cursoapis.services.IUsuarioService;

import jakarta.transaction.Transactional;

@Service
@Transactional
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

    @Override
    public Usuario createUsuario(Usuario usuario, Long personaId, Set<Long> rolIds) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new NotFoundException("Persona no encontrada con id: " + personaId));
        usuario.setPersona(persona);

        Set<Rol> roles = rolRepository.findAllById(rolIds).stream().collect(java.util.stream.Collectors.toSet());
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuarioDetails, Long personaId, Set<Long> rolIds) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        usuario.setUsername(usuarioDetails.getUsername());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDetails.getPassword()));
        usuario.setEnabled(usuarioDetails.getEnabled());

        if (personaId != null) {
            Persona persona = personaRepository.findById(personaId)
                    .orElseThrow(() -> new NotFoundException("Persona no encontrada con id: " + personaId));
            usuario.setPersona(persona);
        }

        if (rolIds != null && !rolIds.isEmpty()) {
            Set<Rol> roles = rolRepository.findAllById(rolIds).stream().collect(java.util.stream.Collectors.toSet());
            usuario.setRoles(roles);
        }

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