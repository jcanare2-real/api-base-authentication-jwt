package com.jcanare2.cursoapis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcanare2.cursoapis.dto.AuthenticationRequestDTO;
import com.jcanare2.cursoapis.dto.AuthenticationResponseDTO;
import com.jcanare2.cursoapis.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    
    public AuthenticationController(AuthenticationService authenticationService) {
    	this.authenticationService = authenticationService;
    }

    /**
     * Endpoint para autenticar un usuario y obtener token JWT.
     * Se valida el request y se devuelve la respuesta con el token generado.
     * 
     * @param request DTO con email y password.
     * @return ResponseEntity con AuthenticationResponseDTO y status HTTP adecuado.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @Valid @RequestBody AuthenticationRequestDTO request) {
        try {
            AuthenticationResponseDTO response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Se puede personalizar más para manejar excepciones específicas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
