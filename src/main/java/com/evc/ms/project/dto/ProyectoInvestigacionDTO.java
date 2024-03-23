/**
 * @file: ProyectoInvestigacionDTO.java
 * @author: (c)2024 evalencia 
 * @created: Mar 19, 2024 6:23:36 PM
 */
package com.evc.ms.project.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.evc.ms.project.domain.AvanceProyecto;
import com.evc.ms.project.domain.Investigador;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 
 */
@Data
public class ProyectoInvestigacionDTO {
	private Long id;   
	@NotBlank(message = "El titulo no puede estar en blanco")
	@Size(min = 10, message = "El titulo debe tener al menos 10 caracteres")
    private String titulo;
	@PastOrPresent
	@NotNull(message = "La fecha de inicio de la investigación no puede estar vacía o no sigue el patron yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicioPry;
	@Future
	@NotNull(message = "La fecha de cierre de la investigación no puede estar vacía o no sigue el patron yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCierrePry;
	@Pattern(regexp = "^(CREADO|EN_EJECUCION|CANCELADO|ELIMINADO|TERMINADO)$", message = "Los estados permitidos son: CREADO|EN_EJECUCION|CANCELADO|ELIMINADO|TERMINADO ")
	@Pattern(regexp = "^[A-Z]*$", message = "Los estados deben estar en mayuscula")
	private String estadoPry;
	@Positive
	private float presupuestoPry;
    private Investigador responsable;
    private List<Investigador> investigadores;
    private List<AvanceProyecto> avances;
}
