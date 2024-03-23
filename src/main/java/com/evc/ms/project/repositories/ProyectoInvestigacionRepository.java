/**
 * @file: ProyectoInvestigacionRespository.java
 * @author: (c)2024 evalencia 
 * @created: Mar 6, 2024 11:53:33 PM
 */
package com.evc.ms.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evc.ms.project.domain.Investigador;
import com.evc.ms.project.domain.ProyectoInvestigacion;

/**
 * 
 */
public interface ProyectoInvestigacionRepository extends JpaRepository<ProyectoInvestigacion, Long> {
	List<ProyectoInvestigacion> findByResponsable(Investigador responsable);
}
