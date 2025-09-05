package com.jcanare2.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcanare2.api.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{

}
