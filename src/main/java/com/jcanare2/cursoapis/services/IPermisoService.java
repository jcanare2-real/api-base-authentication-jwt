package com.jcanare2.cursoapis.services;

import java.util.List;
import java.util.Optional;

import com.jcanare2.cursoapis.entity.Permiso;

public interface IPermisoService {
	
	public List<Permiso> getAllPermisos();
	
	public Optional<Permiso> getById(Long idPermiso);
	
	public Permiso create(Permiso permiso);
	
	public Permiso update(Permiso permiso, Long idPermiso);
 	
	public void delete(Long id);

}
