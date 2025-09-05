package com.jcanare2.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.jcanare2.api.dto.AuthenticationRequestDTO;
import com.jcanare2.api.dto.AuthenticationResponseDTO;


@Service
public class AuthenticationService {
	
	private UserDetailsService userDetailsService;
	
    private final JwtService jwtService;
    
    private AuthenticationManager authenticationManager;
    
    public AuthenticationService(JwtService jwtService) {
    	this.jwtService = jwtService;
    }


    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        // Autenticar con AuthenticationManager usando email y password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        // Cargar detalles del usuario implementando UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        
        // Generar token JWT usando UserDetails
        var jwtToken = jwtService.generateToken(userDetails);
        
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

}
