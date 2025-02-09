/**
 * @file: InvestigadorDTO.java
 * @author: (c)2024 evalencia 
 * @created: Mar 7, 2024 12:17:47 AM
 */
package com.evc.ms.project.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.evc.ms.project.domain.ProyectoInvestigacion;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class InvestigadorDTO {
	private Long idInvestigador;
	@NotBlank(message = "El email no puede estar en blanco")
	@Email(message = " El email no cumple con una estructura valida")
	private String email;
	@NotBlank(message = "El nombre no puede estar en blanco")
	@Size(min = 2, message = "El nombre debe tener al menos 3 caracteres")
	private String nombres;
	@NotBlank(message = "El apellido paterno no puede estar en blanco")
	@Size(min = 2, message = "El apellido paterno debe tener al menos 3 caracteres")
	private String apePat;
	@NotBlank(message = "El apellido materno no puede estar en blanco")
	@Size(min = 2, message = "El apellido materno debe tener al menos 3 caracteres")
	private String apeMat;
	@NotBlank(message = "El DNI no puede estar en blanco")
    @Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 caracteres")
	@Pattern(regexp = "\\d+", message = "El DNI debe contener solo dígitos")
	private String dni;
	@NotNull(message = "La fecha de registro de investigación no puede estar vacía")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRegInv;
    private List<ProyectoInvestigacion> proyectos;
}
