/**
 * @file: InvestigadorServiceImp.java
 * @author: (c)2024 evalencia 
 * @created: Mar 7, 2024 12:01:52 AM
 */
package com.evc.ms.project.services;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
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
public class InvestigadorServiceImp implements InvestigadorService {

	@Autowired
    private InvestigadorRepository invRep;
	@Autowired
    private ProyectoInvestigacionRepository pryInvRep;
	
	@Override
	@Transactional
	public List<Investigador> listarTodos() {
		return invRep.findAll();
	}
	
	@Override
	@Transactional
	public Investigador buscarPorId(Long id) throws EntityNotFoundException {
		Optional<Investigador> investigador = invRep.findById(id);
		if(investigador.isEmpty())throw new EntityNotFoundException(EntityNotFoundExceptionMessages.INVESTIGADOR_NOT_FOUND);
		return investigador.get();
	}
             
	@Override
	@Transactional
	public Investigador grabar(Investigador investigador) throws IllegalOperationException {
		if(!invRep.findByEmail(investigador.getEmail()).isEmpty()) {
			throw new IllegalOperationException("Ya existe un investigador con ese email");
		}
		if(!invRep.findByDni(investigador.getDni()).isEmpty()) {
			throw new IllegalOperationException("Ya existe un investigador con ese dni");
		}
		
		return invRep.save(investigador);
	}
	
	@Override
	@Transactional
	public void eliminar(Long invId) throws EntityNotFoundException, IllegalOperationException {
		Investigador invEntity = invRep.findById(invId).orElseThrow(
				()->new EntityNotFoundException(EntityNotFoundExceptionMessages.INVESTIGADOR_NOT_FOUND)
				);			
		List<ProyectoInvestigacion> proyectos = pryInvRep.findByResponsable(invEntity);
		if (!proyectos.isEmpty()) {
			throw new IllegalOperationException("El investigador es responsable de uno o mas proyectos");
		} 
		if(invEntity.getProyectos()!=null) {
			throw new IllegalOperationException("El investigador tiene proyectos asignados");
		}
		invRep.deleteById(invId);
	}
	
	@Override
	@Transactional
	public Investigador actualizar(Investigador investigador, Long id) throws EntityNotFoundException, IllegalOperationException {
		Optional<Investigador> invEntity = invRep.findById(id);
		//Validar si el investigador existe o no en la bd
		if(!invEntity.isPresent())
			throw new EntityNotFoundException(EntityNotFoundExceptionMessages.INVESTIGADOR_NOT_FOUND);
			
		// Validar si ya existe un investigador con el mismo email
	    Optional<Investigador> existingByEmail = invRep.findByEmail(investigador.getEmail());
	    if (existingByEmail.isPresent() && !existingByEmail.get().getIdInvestigador().equals(id)
	    		&& !investigador.getEmail().equalsIgnoreCase(invEntity.get().getEmail())) {
	        throw new IllegalOperationException("Ya existe un investigador con ese email");
	    }

	    // Validar si ya existe un investigador con el mismo DNI
	    Optional<Investigador> existingByDni = invRep.findByDni(investigador.getDni());
	    if (existingByDni.isPresent() && !existingByDni.get().getIdInvestigador().equals(id)) {
	        throw new IllegalOperationException("Ya existe un investigador con ese dni");
	    }
				
		investigador.setIdInvestigador(id);		
		return invRep.save(investigador);
	}
	

	@Override
	public Investigador findByEmail(String email) {
		Optional<Investigador> investigador = invRep.findByEmail(email);
		if(investigador.isEmpty())throw new EntityNotFoundException(EntityNotFoundExceptionMessages.INVESTIGADOR_NOT_FOUND);
		return investigador.get();
		
	}
	@Override
	public Investigador findByDni(String dni) {
		Optional<Investigador> investigador = invRep.findByDni(dni);
		if(investigador.isEmpty())throw new EntityNotFoundException(EntityNotFoundExceptionMessages.INVESTIGADOR_NOT_FOUND);
		return investigador.get();
		
	}
	
	

	@Override
	@Transactional
	public Investigador actualizarPorAtributos(Long id, Map<String, Object> camposActualizados)
			throws EntityNotFoundException, IllegalOperationException {
		Investigador investigador = invRep.findById(id).
				orElseThrow(()-> new EntityNotFoundException(EntityNotFoundExceptionMessages.INVESTIGADOR_NOT_FOUND));
		Optional<Investigador> existingByDni = invRep.findByDni(investigador.getDni());
	    if (existingByDni.isPresent() && !existingByDni.get().getIdInvestigador().equals(id)) {
	        throw new IllegalOperationException("Ya existe un investigador con ese dni");
	    }
	    
	    Optional<Investigador> existingByEmail = invRep.findByEmail(investigador.getEmail());
	    if (existingByEmail.isPresent() && !existingByEmail.get().getIdInvestigador().equals(id)
	    		&& !investigador.getEmail().equalsIgnoreCase((String) camposActualizados.get("email"))) {
	        throw new IllegalOperationException("Ya existe un investigador con ese email");
	    }
	    
	    
		camposActualizados.forEach((campo, valor) -> {
            switch (campo) {
            	case "apeMat":
            		investigador.setApeMat((String) valor);
            		break;
            	case "apePat":
            		investigador.setApePat((String) valor);
            		break;
                case "dni":
                    investigador.setDni((String) valor);
                    break;          		
                case "email":
                    investigador.setEmail((String) valor);
                    break;
                case "fechaRegInv":
                	String dateValue = (String)valor;
					try {
						investigador.setFechaRegInv(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue));
					} catch (ParseException e) {
						e.printStackTrace();// TODO Auto-generated catch block
						//throw new IllegalOperationException(e);
					}
				
                    break;
                case "nombres":
                    investigador.setNombres((String) valor);
                    break;
                default:
                    // No se hace nada para otros campos no reconocidos
                    break;
            }
        });
	
		
		return invRep.save(investigador);
		
		
	}
	
	
}
