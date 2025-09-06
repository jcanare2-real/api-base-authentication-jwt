package com.jcanare2.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcanare2.api.entity.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long>{
	
	Optional<Permiso> findByNombrePermiso(String nombrePermiso);

}
