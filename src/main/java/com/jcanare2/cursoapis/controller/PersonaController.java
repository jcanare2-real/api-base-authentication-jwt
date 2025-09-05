package com.jcanare2.cursoapis.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcanare2.cursoapis.entity.Persona;
import com.jcanare2.cursoapis.services.IPersonaService;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {
	
	private final IPersonaService personaService;
	
	public PersonaController( IPersonaService personaService) {
		this.personaService = personaService;
	}
	
	@GetMapping()
	public ResponseEntity<List<Persona>> getAll(){
		
		List<Persona> lstPersonas = personaService.getAll();
		return ResponseEntity.ok(lstPersonas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Persona> getById(@PathVariable Long id){
		
		Persona persona = personaService.getById(id);
		return ResponseEntity.ok(persona);
	}
	
	 // Actualizar persona por ID
    @PutMapping("/{id}")
    public ResponseEntity<Persona> update(@RequestBody Persona persona, @PathVariable Long id) {
        Persona actualizada = personaService.update(persona, id);
        return ResponseEntity.ok(actualizada);
    }

    // Borrar persona (por cuerpo de request)
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody Persona persona) {
        personaService.delete(persona);
        return ResponseEntity.noContent().build();
    }

}
