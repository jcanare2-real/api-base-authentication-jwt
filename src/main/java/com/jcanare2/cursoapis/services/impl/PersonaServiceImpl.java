package com.jcanare2.cursoapis.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcanare2.cursoapis.entity.Persona;
import com.jcanare2.cursoapis.repository.PersonaRepository;
import com.jcanare2.cursoapis.services.IPersonaService;

@Service
public class PersonaServiceImpl implements IPersonaService {
	
	private final PersonaRepository personaRepository;
	
	public PersonaServiceImpl(PersonaRepository personaRepository) {
		this.personaRepository = personaRepository;
	}

	@Override
	public List<Persona> getAll() {
		return this.personaRepository.findAll();
	}

	@Override
	public Persona getById(Long id) {
		return this.personaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));
	}

	@Override
	public Persona create(Persona persona) {
		return this.personaRepository.save(persona);
	}

	@Override
	public Persona update(Persona persona, Long idPersona) {
		Persona personaExistente = this.personaRepository.findById(idPersona)
				.orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + idPersona));
		
		personaExistente.setDocumento(persona.getDocumento());
        personaExistente.setNombres(persona.getNombres());
        personaExistente.setApellidos(persona.getApellidos());
        
		return this.personaRepository.save(personaExistente);
	}

	@Override
	public void delete(Persona persona) {
		this.personaRepository.delete(persona);
	}
	
	

}
