package com.jcanare2.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcanare2.api.dto.PermisoDTO;
import com.jcanare2.api.entity.Permiso;
import com.jcanare2.api.services.IPermisoService;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {
	
	private final IPermisoService permisoService;
	
	public PermisoController(IPermisoService permisoService) {
		this.permisoService = permisoService;
	}
	
	@GetMapping
	public List<Permiso> getAll(){
		List<Permiso> permisos = permisoService.getAllPermisos();
		
		return permisos;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Permiso> getById(@PathVariable Long id){
		
		Optional<Permiso> permiso = permisoService.getById(id);
		
		if(permiso.isPresent()) {
			return ResponseEntity.ok(permiso.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Permiso> create(@RequestBody PermisoDTO permisoRequest){
		Permiso permiso = Permiso.builder()
							.nombrePermiso(permisoRequest.getNombrePermiso())
							.build();
		Permiso permisoCreated = permisoService.create(permiso);
		
		return ResponseEntity.ok(permisoCreated);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Permiso> update(@RequestBody PermisoDTO permisoRequest, @PathVariable Long id){
		
		Permiso permiso = Permiso.builder()
							.nombrePermiso(permisoRequest.getNombrePermiso())
							.build();
		
		Permiso permisoUpdated = permisoService.update(permiso, id);
		
		return ResponseEntity.ok(permisoUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		
		permisoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
