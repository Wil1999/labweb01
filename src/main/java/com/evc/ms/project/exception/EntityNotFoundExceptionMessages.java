/**
 * @file: ErrorMessage.java
 * @author: (c)2024 evalencia 
 * @created: Feb 15, 2024 1:42:34 PM
 */
package com.evc.ms.project.exception;

/**
 * Clase de utilidad que contiene mensajes de error para excepciones de tipo EntityNotFoundException.
 */
public final class EntityNotFoundExceptionMessages {

	public static final String INVESTIGADOR_NOT_FOUND = "El investigador con id proporcionado no fue encontrado";
	public static final String PROYECTO_NOT_FOUND = "El proyecto con id proporcionado no fue encontrado";
	public static final String AVANCE_NOT_FOUND = "El avance con id proporcionado no fue encontrado";
	public static final String USUARIO_NOT_FOUND = "El usuario con id proporcionado no fue encontrado";
	
	
    // Constructor privado para evitar instanciación
	private EntityNotFoundExceptionMessages() {
		throw new IllegalStateException ("Utility class");
	}
}
