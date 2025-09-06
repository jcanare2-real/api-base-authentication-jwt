package com.jcanare2.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jcanare2.api.entity.Permiso;
import com.jcanare2.api.entity.Rol;
import com.jcanare2.api.exceptions.NotFoundException;
import com.jcanare2.api.repository.PermisoRepository;
import com.jcanare2.api.repository.RolRepository;
import com.jcanare2.api.services.IRolService;

@Service
public class RolServiceImpl implements IRolService{
	
	private final RolRepository rolRepository;
	
	private final PermisoRepository permisoRepository;
	
	public RolServiceImpl(RolRepository rolRepository, PermisoRepository permisoRepository) {
		this.rolRepository = rolRepository;
		this.permisoRepository = permisoRepository;
	}

	@Override
	public List<Rol> getAll() {
		
		return rolRepository.findAll();
	}

	@Override
	public Optional<Rol> getById(Long id) {
		
		return rolRepository.findById(id);
	}

	@Override
	public Rol create(Rol rol, Set<Long> permisosIds) {
		
		if (permisosIds != null && !permisosIds.isEmpty()) {
            Set<Permiso> permisos = permisoRepository.findAllById(permisosIds).stream().collect(java.util.stream.Collectors.toSet());
            rol.setPermisos(permisos);
        }
		
		return rolRepository.save(rol);
	}

	@Override
	public Rol update(Rol rolRequest, Long id, Set<Long> permisosIds) {
		
		Rol rol = rolRepository.findById(id)
							.orElseThrow(()-> new NotFoundException("Rol No encontrado con ID" + id));
		
		rol.setNombreRol(rolRequest.getNombreRol());

		if (permisosIds != null && !permisosIds.isEmpty()) {
            Set<Permiso> permisos = permisoRepository.findAllById(permisosIds).stream().collect(java.util.stream.Collectors.toSet());
            rol.setPermisos(permisos);
        }
		
		return rolRepository.save(rol);
	}

	@Override
	public void delete(Long id) {
		boolean exists = rolRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Usuario no encontrado con id: " + id);
        }
        rolRepository.deleteById(id);
		
	}

}
