/**
 * @file: ProyectoInvestigacionServiceImp.java
 * @author: (c)2024 evalencia 
 * @created: Mar 19, 2024 7:08:59 PM
 */
package com.evc.ms.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evc.ms.project.domain.Investigador;
import com.evc.ms.project.domain.ProyectoInvestigacion;
import com.evc.ms.project.exception.EntityNotFoundException;
import com.evc.ms.project.exception.EntityNotFoundExceptionMessages;
import com.evc.ms.project.exception.IllegalOperationException;
import com.evc.ms.project.repositories.InvestigadorRepository;
import com.evc.ms.project.repositories.ProyectoInvestigacionRepository;

/**
 * 
 */
@Service
public class ProyectoInvestigacionServiceImp implements ProyectoInvestigacionService {
	@Autowired
    private ProyectoInvestigacionRepository pryInvRep;
	
	@Autowired
	private InvestigadorRepository invRep;
	
	@Override
	@Transactional
	public List<ProyectoInvestigacion> listarTodos() {
		return pryInvRep.findAll();
	}

	@Override
	@Transactional
	public ProyectoInvestigacion buscarPorId(Long id) throws EntityNotFoundException {
		Optional<ProyectoInvestigacion> pryInvestigacion = pryInvRep.findById(id);
		if(pryInvestigacion.isEmpty())throw new EntityNotFoundException(EntityNotFoundExceptionMessages.PROYECTO_NOT_FOUND);
		return pryInvestigacion.get();
	}

	@Override
	@Transactional
	public ProyectoInvestigacion grabar(ProyectoInvestigacion proyecto) throws IllegalOperationException {	
		return pryInvRep.save(proyecto);
	}

	@Override
	@Transactional
	public ProyectoInvestigacion actualizar(ProyectoInvestigacion proyecto, Long id) throws EntityNotFoundException, IllegalOperationException {
		Optional<ProyectoInvestigacion> pryEntity = pryInvRep.findById(id);
		//Validar si el proyecto existe o no en la bd
		if(!pryEntity.isPresent())
			throw new EntityNotFoundException(EntityNotFoundExceptionMessages.PROYECTO_NOT_FOUND);
		proyecto.setId(id);		
		return pryInvRep.save(proyecto);
	}

	@Override
	@Transactional
	public void eliminar(Long pryId) throws EntityNotFoundException, IllegalOperationException {
		
		pryInvRep.findById(pryId).orElseThrow(
				()->new EntityNotFoundException(EntityNotFoundExceptionMessages.PROYECTO_NOT_FOUND)
				);					
		pryInvRep.deleteById(pryId);
	}
	
	
	@Override
	@Transactional
	public ProyectoInvestigacion asignarResponsable (Long idPry, Long idInv) throws EntityNotFoundException, IllegalOperationException {
		ProyectoInvestigacion pryEntity = pryInvRep.findById(idPry)
				.orElseThrow(()-> new EntityNotFoundException(EntityNotFoundExceptionMessages.PROYECTO_NOT_FOUND));
		
		Investigador invEntity = invRep.findById(idInv)
				.orElseThrow(
						()-> new EntityNotFoundException(EntityNotFoundExceptionMessages.INVESTIGADOR_NOT_FOUND)
						);
			
		if (pryEntity.getResponsable()!= null) {
			throw new IllegalOperationException("El proyecto ya tiene asignado un responsable");

        } 
		pryEntity.setResponsable(invEntity);
		return pryInvRep.save(pryEntity);
		
	}
	
	@Override
	@Transactional
	public ProyectoInvestigacion asignarInvestigador (Long idPry, Long idInv) throws EntityNotFoundException, IllegalOperationException {
		ProyectoInvestigacion pryEntity = pryInvRep.findById(idPry)
				.orElseThrow(()-> new EntityNotFoundException(EntityNotFoundExceptionMessages.PROYECTO_NOT_FOUND));
		
		Investigador invEntity = invRep.findById(idInv)
				.orElseThrow(
						()-> new EntityNotFoundException(EntityNotFoundExceptionMessages.INVESTIGADOR_NOT_FOUND)
						);
			
		if (pryEntity.getInvestigadores().contains(invEntity)){
			throw new IllegalOperationException("El investigador ya esta asignado a este proyecto");

        } 
		pryEntity.getInvestigadores().add(invEntity);
		return pryInvRep.save(pryEntity);
		
	}
	
	

}
