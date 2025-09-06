package com.jcanare2.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDTO {

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombreRol;

    @NotNull(message = "El conjunto de permisos no puede ser nulo")
    private Set<Long> permisosIds;  // IDs de los permisos asociados al rol
}
