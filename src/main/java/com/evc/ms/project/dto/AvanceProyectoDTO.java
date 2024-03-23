/**
 * @file: AvanceProyectoDTO.java
 * @author: (c)2024 evalencia 
 * @created: Mar 19, 2024 6:24:00 PM
 */
package com.evc.ms.project.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.evc.ms.project.domain.ProyectoInvestigacion;

import jakarta.validation.constraints.AssertTrue;
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
public class AvanceProyectoDTO {
	 	private Long id;
	 	@NotBlank(message = "La descripcion no puede estar en blanco")
		@Size(min = 10, message = "La descripcion debe tener al menos 10 caracteres")
		private String descripcionAvance;
	 	@PastOrPresent
	 	@NotNull(message = "La fecha de registro del avance no puede estar vacía o no sigue el patron yyyy-MM-dd")
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date fechaRegAvance;
	 	@Pattern(regexp = "^(EN_EJECUCION|CANCELADO|ELIMINADO|TERMINADO)$", message = "Los estados permitidos son: EN_EJECUCION|CANCELADO|ELIMINADO|TERMINADO ")
		@Pattern(regexp = "^[A-Z]*$", message = "Los estados deben estar en mayuscula")
		private String estadoAv;
	 	@Positive
		private float presupuestoAsignado;
	 	@Positive
		private float presupuestoGastado;
		private ProyectoInvestigacion proyecto;
		
		
		@AssertTrue(message = "El presupuesto asignado debe ser mayor que el presupuesto gastado")
	    private boolean isPresupuestoValido() {
	        return presupuestoAsignado > presupuestoGastado;
	    }
}
