package com.jcanare2.cursoapis.dto;

import lombok.Data;

@Data
public class CambiarPasswordRequestDTO {
	
	private String passwordActual;
    private String nuevaPassword;

}
