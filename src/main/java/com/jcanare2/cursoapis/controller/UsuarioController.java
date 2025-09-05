package com.jcanare2.cursoapis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcanare2.cursoapis.dto.CambiarPasswordRequestDTO;
import com.jcanare2.cursoapis.dto.UsuarioCreationRequestDTO;
import com.jcanare2.cursoapis.dto.UsuarioUpdateRequestDTO;
import com.jcanare2.cursoapis.entity.Usuario;
import com.jcanare2.cursoapis.exceptions.NotFoundException;
import com.jcanare2.cursoapis.services.IUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;
    
    public UsuarioController(IUsuarioService usuarioService) {
    	this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioById(id);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody UsuarioCreationRequestDTO request) {
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .enabled(request.getEnabled())
                .build();

        Usuario created = usuarioService.createUsuario(usuario, request.getPersonaId(), request.getRolIds());

        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id,
                                                 @Valid @RequestBody UsuarioUpdateRequestDTO request) {
        Usuario usuarioDetails = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .enabled(request.getEnabled())
                .build();

        Usuario updated = usuarioService.updateUsuario(id, usuarioDetails, request.getPersonaId(), request.getRolIds());
        return ResponseEntity.ok(updated);
    }
    
    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@PathVariable Long id, @RequestBody CambiarPasswordRequestDTO request) {
        try {
            usuarioService.cambiarPassword(id, request.getPasswordActual(), request.getNuevaPassword());
            return ResponseEntity.ok("Contraseña cambiada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
