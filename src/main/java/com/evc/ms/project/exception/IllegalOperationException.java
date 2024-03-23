/**
 * @file: IllegalOperationException.java
 * @author: (c)2024 evalencia 
 * @created: Feb 15, 2024 1:49:51 PM
 */
package com.evc.ms.project.exception;

/**
 * Excepcion que se lanza cuando se realiza una operacion ilegal
 */
public class IllegalOperationException extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalOperationException(String message) {
		super (message);
	}

}
