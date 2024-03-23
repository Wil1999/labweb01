/**
 * @file: InvestigadorController.java
 * @author: (c)2024 evalencia 
 * @created: Mar 7, 2024 12:28:30 AM
 */
package com.evc.ms.project.controllers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evc.ms.project.domain.Investigador;
import com.evc.ms.project.dto.InvestigadorDTO;
import com.evc.ms.project.exception.EntityNotFoundException;
import com.evc.ms.project.exception.IllegalOperationException;
import com.evc.ms.project.services.InvestigadorService;
import com.evc.ms.project.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * Se utiliza version en cabecera, utilizar Key = X-VERSION y Value 1.0.0
 */
@RestController
@RequestMapping(value = "/api/investigadores")//, headers ="X-VERSION=1.0.0")
public class InvestigadorController {
	@Autowired
    private InvestigadorService invService;

    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public ResponseEntity<?> obtenerTodos(){
    	List<Investigador> investigadores = invService.listarTodos();
    	if(investigadores==null || investigadores.isEmpty()) {
    		return ResponseEntity.noContent().build();
    	}
    	else {     
    		List<InvestigadorDTO> investigadorDTOs = investigadores.stream()
    				.map(departamento -> modelMapper.map(departamento, InvestigadorDTO.class))
    				.collect(Collectors.toList());
    		ApiResponse<List<InvestigadorDTO>> response = new ApiResponse<>(true, "Lista de investigadores obtenida con éxito", investigadorDTOs);
    		return ResponseEntity.ok(response);
    	}

    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {      
    	Investigador investigador = invService.buscarPorId(id);
    	InvestigadorDTO investigadorDTO = modelMapper.map(investigador, InvestigadorDTO.class);
    	ApiResponse<InvestigadorDTO> response = new ApiResponse<>(true, "Datos del Investigador obtenidos con éxito", investigadorDTO);
    	return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody InvestigadorDTO investigadorDTO) throws IllegalOperationException{	   	    	
    	Investigador investigador = modelMapper.map(investigadorDTO, Investigador.class);
    	invService.grabar(investigador);
    	InvestigadorDTO savedInvestigadorDTO = modelMapper.map(investigador, InvestigadorDTO.class);
    	ApiResponse<InvestigadorDTO> response = new ApiResponse<>(true, "Datos del Investigador grabados con éxito", savedInvestigadorDTO);
    	return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
 
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody InvestigadorDTO investigadorDTO, @PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {       
  	
    	Investigador investigador = modelMapper.map(investigadorDTO, Investigador.class);
    	invService.actualizar(investigador, id);
    	InvestigadorDTO updatedInvestigadorDTO = modelMapper.map(investigador, InvestigadorDTO.class);
    	ApiResponse<InvestigadorDTO> response = new ApiResponse<>(true, "Datos del Investigador actualizados con éxito", updatedInvestigadorDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
    @PatchMapping("/{id}")
	public ResponseEntity<?> actualizarParcial(@Valid @RequestBody Map<String, Object> camposActualizados, BindingResult bindingResult  ,@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException{
    	
    	// Validación manual
        bindingResult = new BeanPropertyBindingResult(camposActualizados, "camposActualizados");
        
        // Validar el formato del correo electrónico
        if (camposActualizados.containsKey("email")) {
            String email = (String) camposActualizados.get("email");
            if (!isValidEmail(email)) {
                throw new IllegalOperationException ("El atributo email no es valido");            	
            }
        }

        // Validar el formato del DNI
        if (camposActualizados.containsKey("dni")) {
            String dni = (String) camposActualizados.get("dni");
            if (!isValidDni(dni)) {
            	throw new IllegalOperationException ("El atributo DNI no es valido");
            }
        }
        
        // Validar que el nombre no este en blanco
        if (camposActualizados.containsKey("nombres")) {
            String nombres = (String) camposActualizados.get("nombres");
            if (!isValidString(nombres)) {
            	throw new IllegalOperationException ("El atributo nombres no debe estar en blanco");
            }
        }
        
        // Validar que el apepat no este en blanco
        if (camposActualizados.containsKey("apePat")) {
            String apellido = (String) camposActualizados.get("apePat");
            if (!isValidString(apellido)) {
            	throw new IllegalOperationException ("El atributo apellido Paterno no debe estar en blanco");
            }
        }
        
        // Validar que el apemat no este en blanco
        if (camposActualizados.containsKey("apeMat")) {
            String apellido = (String) camposActualizados.get("apeMat");
            if (!isValidString(apellido)) {
            	throw new IllegalOperationException ("El atributo apellido Materno no debe estar en blanco");
            }
        }
        // Validar que el fechaRegInv sea una fecha valida
        if (camposActualizados.containsKey("fechaRegInv")) {
            String fecha = (String) camposActualizados.get("fechaRegInv");
            if (!isValidDateFormat(fecha)) {
            	throw new IllegalOperationException ("El atributo fechaRegInv no tiene un formato valido (yyyy-MM-dd)");
            }
            
            if (!isValidDate(fecha)) {
            	throw new IllegalOperationException ("El atributo fechaRegInv no tiene un formato valido (yyyy-MM-dd)");
            }
            
            
        }
        
        
        

        if (bindingResult.hasErrors()) {
            // Manejar los errores de validación, por ejemplo, devolver una respuesta con los errores encontrados
            return ResponseEntity.badRequest().build();
        }
    	
    	
    	//Despues de las validaciones realizamos la actualizacion
    	
    	Investigador investigador = invService.actualizarPorAtributos(id,camposActualizados);   	
    	
    	InvestigadorDTO updatedInvestigadorDTO = modelMapper.map(investigador, InvestigadorDTO.class);
    	ApiResponse<InvestigadorDTO> response = new ApiResponse<>(true, "Datos del Investigador actualizados con éxito", updatedInvestigadorDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
	}
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
    	invService.eliminar(id);
    	ApiResponse<?> response = new ApiResponse<>(true, "Investigador eliminado con éxito", null);
    	return ResponseEntity.status(HttpStatus.OK).body(response);//NO_CONTENT
    }   
    
    // Método para validar el formato del correo electrónico
    private boolean isValidEmail(String email) {
        // Utiliza una expresión regular para validar el formato del correo electrónico
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Método para validar el formato del DNI
    private boolean isValidDni(String dni) {
        // Utiliza una expresión regular para validar que el DNI contenga solo dígitos y tenga exactamente 8 caracteres
        return dni != null && dni.matches("\\d{8}");
    }
    
    // Método para validar que una cadena no este en blanco
    private boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    // Método para validar que una cadena tiene el formato de fecha
    @SuppressWarnings("unused")
	private boolean isValidDate(String dateString) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Para hacer que el formato sea estricto

        try {
            // Intenta parsear el String en un objeto Date
          	Date parsedDate = dateFormat.parse(dateString);
            return true; // Si se puede parsear correctamente, el String es una fecha válida
        } catch (ParseException e) {
            return false; // Si ocurre una excepción, el String no es una fecha válida
        }
    }
    
    private static boolean isValidDateFormat(String dateString) {
        // Define una expresión regular para el patrón yyyy-MM-dd
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        
        // Comprueba si la cadena coincide con el patrón
        return dateString.matches(regex);
    }
    
}
