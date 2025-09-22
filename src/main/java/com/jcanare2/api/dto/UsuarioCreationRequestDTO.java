package com.jcanare2.api.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreationRequestDTO {
    
	@NotBlank
    private String username;

	@NotBlank
    private String password;

    @Email
    private String email;

    private Boolean enabled;

    // Datos de Persona anidados
    @NotNull
    private Long documento;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    private Set<Long> rolIds;

}
