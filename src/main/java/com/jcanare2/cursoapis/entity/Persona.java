package com.jcanare2.cursoapis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="grl_persona")
public class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="documento", nullable = false, length = 10, unique = true)
	private Long documento;
	
	@Column(name="nombres", nullable = false, length = 60)
	private String nombres;
	
	@Column(name="apellidos", nullable = false, length = 60)
	private String apellidos;
	
	//Constructor sin ID
	public Persona( Long documento, String nombres, String apellidos) {
		this.documento = documento;
		this.nombres = nombres;
		this.apellidos = apellidos;
	}

}
