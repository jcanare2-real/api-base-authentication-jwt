package com.jcanare2.api.dto;

import lombok.Data;

@Data
public class CambiarPasswordRequestDTO {
	
	private String passwordActual;
    private String nuevaPassword;

}
