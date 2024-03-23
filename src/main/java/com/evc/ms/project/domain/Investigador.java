/**
 * @file: Investigador.java
 * @author: (c)2024 evalencia 
 * @created: Mar 6, 2024 7:20:31 PM
 */
package com.evc.ms.project.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property="idInvestigador")
public class Investigador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idInvestigador;
	@Column(unique = true)
	private String email;
	private String nombres;
	private String apePat;
	private String apeMat;
	@Column(unique = true)
	private String dni;
	@Temporal(TemporalType.DATE)
	private Date fechaRegInv;
	// Relación con ProyectoInvestigacion
    @ManyToMany(mappedBy = "investigadores")
    @JsonIgnore
    private List<ProyectoInvestigacion> proyectos;

}