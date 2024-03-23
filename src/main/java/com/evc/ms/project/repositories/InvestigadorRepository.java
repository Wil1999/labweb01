/**
 * @file: InvestigadorRepository.java
 * @author: (c)2024 evalencia 
 * @created: Mar 6, 2024 11:50:52 PM
 */
package com.evc.ms.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evc.ms.project.domain.Investigador;

/**
 * 
 */
public interface InvestigadorRepository extends JpaRepository<Investigador, Long> {
	Optional<Investigador> findByEmail(String email);
	Optional<Investigador> findByDni(String dni);
}
