package com.jcanare2.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jcanare2.api.dto.AuthenticationRequestDTO;
import com.jcanare2.api.dto.AuthenticationResponseDTO;


@Service
public class AuthenticationService {
	
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	

	public AuthenticationService(JwtService jwtService, AuthenticationManager authenticationManager) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}



	public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
		
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()
			)
		);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		var jwtToken = jwtService.generateToken(userDetails);
		
		return AuthenticationResponseDTO.builder()
			.token(jwtToken)
			.build();
	}
}