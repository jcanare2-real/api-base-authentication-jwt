package com.jcanare2.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcanare2.api.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
	
	Optional<Rol> findByNombreRol(String name);

}
