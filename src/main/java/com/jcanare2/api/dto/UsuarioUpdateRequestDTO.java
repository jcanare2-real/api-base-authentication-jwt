package com.jcanare2.api.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioUpdateRequestDTO {
    private String username;
    
    private String email;
    
    private Boolean enabled;
    
    private Long personaId;
    
    private Set<Long> rolIds;
}
