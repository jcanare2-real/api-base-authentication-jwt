package com.jcanare2.cursoapis.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreationRequestDTO {
    
	@jakarta.validation.constraints.NotBlank
    private String username;

    @jakarta.validation.constraints.NotBlank
    private String password;

    @jakarta.validation.constraints.Email
    private String email;

    private Boolean enabled;

    private Long personaId;

    private Set<Long> rolIds;

}
