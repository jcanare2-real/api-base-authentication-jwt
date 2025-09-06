package com.jcanare2.api.controllers;

import com.jcanare2.api.dto.RolDTO;
import com.jcanare2.api.entity.Rol;
import com.jcanare2.api.exceptions.NotFoundException;
import com.jcanare2.api.services.IRolService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final IRolService rolService;

    public RolController(IRolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolService.getAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        return rolService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Rol> createRol(
            @Valid @RequestBody RolDTO rolRequest,
            @RequestParam(required = false) Set<Long> permisosIds) {
    	
    	Rol rol = Rol.builder()
    				.nombreRol(rolRequest.getNombreRol())
    				.build();
        
        Rol createdRol = rolService.create(rol, permisosIds);
        return new ResponseEntity<>(createdRol, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(
            @PathVariable Long id,
            @Valid @RequestBody RolDTO rolRequest,
            @RequestParam(required = false) Set<Long> permisosIds) {
    	
    	Rol rol = Rol.builder()
				.nombreRol(rolRequest.getNombreRol())
				.build();
        
        Rol updatedRol = rolService.update(rol, id, permisosIds);
        return ResponseEntity.ok(updatedRol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
