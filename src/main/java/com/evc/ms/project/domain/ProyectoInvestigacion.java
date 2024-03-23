/**
 * @file: ProyectoInvestigacion.java
 * @author: (c)2024 evalencia 
 * @created: Mar 6, 2024 7:23:53 PM
 */
package com.evc.ms.project.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * 
 */
@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property="id")
public class ProyectoInvestigacion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
	@Temporal(TemporalType.DATE)
	private Date fechaInicioPry;
	@Temporal(TemporalType.DATE)
	private Date fechaCierrePry;
	private String estadoPry;
	private float presupuestoPry;
    @ManyToOne
    private Investigador responsable;
    
    @ManyToMany
    private List<Investigador> investigadores;
    
    @OneToMany(mappedBy = "proyecto" , cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<AvanceProyecto> avances;
}
