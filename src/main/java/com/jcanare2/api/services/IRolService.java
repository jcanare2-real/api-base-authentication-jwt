package com.jcanare2.api.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.jcanare2.api.entity.Rol;

public interface IRolService {
	
	public List<Rol> getAll();
	
	public Optional<Rol> getById(Long id);
	
	public Rol create(Rol rol, Set<Long> permisosIds);
	
	public Rol update(Rol rol, Long id, Set<Long> permisosIds);
	
	public void delete(Long id);

}
