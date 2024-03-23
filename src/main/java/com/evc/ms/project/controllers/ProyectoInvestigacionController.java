/**
 * @file: ProyectoInvestigacionController.java
 * @author: (c)2024 evalencia 
 * @created: Mar 19, 2024 8:08:16 PM
 */
package com.evc.ms.project.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evc.ms.project.domain.ProyectoInvestigacion;
import com.evc.ms.project.dto.ProyectoInvestigacionDTO;
import com.evc.ms.project.exception.EntityNotFoundException;
import com.evc.ms.project.exception.IllegalOperationException;
import com.evc.ms.project.services.ProyectoInvestigacionService;
import com.evc.ms.project.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * 
 */
@RestController
@RequestMapping(value = "/api/proyectos")//, headers ="X-VERSION=1.0.0")
public class ProyectoInvestigacionController {
	@Autowired
    private ProyectoInvestigacionService pryService;

    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public ResponseEntity<?> obtenerTodos(){
    	List<ProyectoInvestigacion> proyectos = pryService.listarTodos();
    	if(proyectos==null || proyectos.isEmpty()) {
    		return ResponseEntity.noContent().build();
    	}
    	else {     
    		List<ProyectoInvestigacionDTO> proyectoDTOs = proyectos.stream()
    				.map(proyecto -> modelMapper.map(proyecto, ProyectoInvestigacionDTO.class))
    				.collect(Collectors.toList());
    		ApiResponse<List<ProyectoInvestigacionDTO>> response = new ApiResponse<>(true, "Lista de proyectos de investigacion obtenida con éxito", proyectoDTOs);
    		return ResponseEntity.ok(response);
    	}

    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {      
    	ProyectoInvestigacion proyecto = pryService.buscarPorId(id);
    	ProyectoInvestigacionDTO proyectoDTO = modelMapper.map(proyecto, ProyectoInvestigacionDTO.class);
    	ApiResponse<ProyectoInvestigacionDTO> response = new ApiResponse<>(true, "Datos del Proyecto obtenidos con éxito", proyectoDTO);
    	return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ProyectoInvestigacionDTO investigadorDTO) throws IllegalOperationException{	   	    	
    	ProyectoInvestigacion proyecto = modelMapper.map(investigadorDTO, ProyectoInvestigacion.class);
    	pryService.grabar(proyecto);
    	ProyectoInvestigacionDTO savedProyectoDTO = modelMapper.map(proyecto, ProyectoInvestigacionDTO.class);
    	ApiResponse<ProyectoInvestigacionDTO> response = new ApiResponse<>(true, "Datos del Proyecto grabados con éxito", savedProyectoDTO);
    	return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody ProyectoInvestigacionDTO proyectoDTO, @PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {       
  	
    	ProyectoInvestigacion proyecto = modelMapper.map(proyectoDTO, ProyectoInvestigacion.class);
    	pryService.actualizar(proyecto, id);
    	ProyectoInvestigacionDTO updatedProyectoDTO = modelMapper.map(proyecto, ProyectoInvestigacionDTO.class);
    	ApiResponse<ProyectoInvestigacionDTO> response = new ApiResponse<>(true, "Datos del Proyecto actualizados con éxito", updatedProyectoDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
    	pryService.eliminar(id);
    	ApiResponse<?> response = new ApiResponse<>(true, "Proyecto eliminado con éxito", null);
    	return ResponseEntity.status(HttpStatus.OK).body(response);//NO_CONTENT
    }   
    
    //Asignar un responsable a un proyecto
    // uri: api/proyectos/{idPry}/responsable/{idInv}
    @PutMapping(value = "/{idPry}/responsable/{idInv}")
    public ResponseEntity<?> asignarResponsable (@PathVariable Long idPry, @PathVariable Long idInv) throws EntityNotFoundException, IllegalOperationException{
    	ProyectoInvestigacion proyecto = pryService.asignarResponsable(idPry, idInv);
    	ProyectoInvestigacionDTO proyectoDTO = modelMapper.map(proyecto, ProyectoInvestigacionDTO.class);
    	ApiResponse<?> response = new ApiResponse<>(true, "Investigador Asignado al Proyecto con éxito", proyectoDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    //Asignar un investigador a un proyecto
    // uri: api/proyectos/{idPry}/investigador/{idInv}
    @PutMapping(value = "/{idPry}/investigador/{idInv}")
    public ResponseEntity<?> asignarInvestigador (@PathVariable Long idPry, @PathVariable Long idInv) throws EntityNotFoundException, IllegalOperationException{
    	ProyectoInvestigacion proyecto = pryService.asignarInvestigador(idPry, idInv);
    	ProyectoInvestigacionDTO proyectoDTO = modelMapper.map(proyecto, ProyectoInvestigacionDTO.class);
    	ApiResponse<?> response = new ApiResponse<>(true, "Investigador Asignado al Proyecto con éxito", proyectoDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
}
