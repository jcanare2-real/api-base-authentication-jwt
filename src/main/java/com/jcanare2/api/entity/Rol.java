package com.jcanare2.api.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seg_rol")
@Builder
public class Rol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre_rol", unique = true, nullable = false)
	private String nombreRol;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rol_permisos",
    joinColumns = @JoinColumn(name = "rol_id"),
    inverseJoinColumns = @JoinColumn(name = "permiso_id"))
	private Set<Permiso> permisos = new HashSet<>();

}
