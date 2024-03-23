/**
 * @file: UsuarioRepository.java
 * @author: (c)2024 evalencia 
 * @created: Mar 6, 2024 11:56:02 PM
 */
package com.evc.ms.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evc.ms.project.domain.Usuario;

/**
 * 
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
}
