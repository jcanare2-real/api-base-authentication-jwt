package com.jcanare2.cursoapis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcanare2.cursoapis.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);

}
