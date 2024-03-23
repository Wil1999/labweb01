/**
 * @file: InvestigadorService.java
 * @author: (c)2024 evalencia 
 * @created: Mar 6, 2024 11:57:38 PM
 */
package com.evc.ms.project.services;

import java.util.List;
import java.util.Map;

import com.evc.ms.project.domain.Investigador;
import com.evc.ms.project.exception.EntityNotFoundException;
import com.evc.ms.project.exception.IllegalOperationException;



/**
 * 
 */
public interface InvestigadorService {
	
	List<Investigador> listarTodos();
    Investigador buscarPorId(Long id);
    Investigador grabar(Investigador investigador) throws IllegalOperationException;
    Investigador actualizar(Investigador investigador, Long id) throws EntityNotFoundException, IllegalOperationException;
    void eliminar(Long i) throws EntityNotFoundException, IllegalOperationException;
    Investigador findByEmail(String email);
	Investigador findByDni(String dni);
	/**
	 * @param id
	 * @param investigador
	 */
	Investigador actualizarPorAtributos(Long id, Map<String, Object> camposActualizados)
			throws EntityNotFoundException, IllegalOperationException ;
}
