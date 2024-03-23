/**
 * @file: ProyectoInvestigacionService.java
 * @author: (c)2024 evalencia 
 * @created: Mar 19, 2024 7:03:33 PM
 */
package com.evc.ms.project.services;

import java.util.List;


import com.evc.ms.project.domain.ProyectoInvestigacion;
import com.evc.ms.project.exception.EntityNotFoundException;
import com.evc.ms.project.exception.IllegalOperationException;

/**
 * 
 */
public interface ProyectoInvestigacionService {
	List<ProyectoInvestigacion> listarTodos();
	ProyectoInvestigacion buscarPorId(Long id);
	ProyectoInvestigacion grabar(ProyectoInvestigacion proyecto) throws IllegalOperationException;
	ProyectoInvestigacion actualizar(ProyectoInvestigacion proyecto, Long id) throws EntityNotFoundException, IllegalOperationException;
    void eliminar(Long i) throws EntityNotFoundException, IllegalOperationException;
    public ProyectoInvestigacion asignarResponsable (Long idPry, Long idInv) throws EntityNotFoundException, IllegalOperationException;
    public ProyectoInvestigacion asignarInvestigador (Long idPry, Long idInv) throws EntityNotFoundException, IllegalOperationException;

}
