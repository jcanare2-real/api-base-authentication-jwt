package com.jcanare2.cursoapis.services;

import java.util.List;

import com.jcanare2.cursoapis.entity.Persona;

public interface IPersonaService {
	
	public List<Persona> getAll();
	
	public Persona getById(Long id);
	
	public Persona create(Persona persona);
	
	public Persona update(Persona persona, Long idPersona);
	
	public void delete(Persona persona);

}
