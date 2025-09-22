package com.jcanare2.api.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.jcanare2.api.dto.UsuarioCreationRequestDTO;
import com.jcanare2.api.dto.UsuarioUpdateRequestDTO;
import com.jcanare2.api.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> getAllUsuarios();
	
	public Optional<Usuario> getUsuarioById(Long id);
	
	public Usuario createUsuario(UsuarioCreationRequestDTO usuario, Set<Long> rolIds);
	
	public Usuario updateUsuario(Long id, UsuarioUpdateRequestDTO usuarioDTO);
	
	public Usuario cambiarPassword(Long usuarioId, String contraseñaActual, String nuevaPassword);
	
	public void deleteUsuario(Long id);

}