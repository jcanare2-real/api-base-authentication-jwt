package com.jcanare2.cursoapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcanare2.cursoapis.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{

}
