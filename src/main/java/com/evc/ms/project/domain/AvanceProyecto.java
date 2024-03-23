/**
 * @file: AvanceProyecto.java
 * @author: (c)2024 evalencia 
 * @created: Mar 6, 2024 7:28:53 PM
 */
package com.evc.ms.project.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * 
 */
@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class AvanceProyecto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String descripcionAvance;
	@Temporal(TemporalType.DATE)
	private Date fechaRegAvance;
	private String estadoAv;
	private float presupuestoAsignado;
	private float presupuestoGastado;
	
    @ManyToOne
    @JsonBackReference
    private ProyectoInvestigacion proyecto;

}
