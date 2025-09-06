package com.jcanare2.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermisoDTO {
	
	@NotBlank(message = "El nombre del permiso es obligatorio")
    private String nombrePermiso;

}
