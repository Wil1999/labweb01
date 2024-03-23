/**
 * @file: ApiResponse.java
 * @author: (c)2024 evalencia 
 * @created: Feb 20, 2024 9:29:17 PM
 */
package com.evc.ms.project.util;

import lombok.Data;

/**
 * Clase que representa una respuesta genérica para las API.
 *
 * @param <T> Tipo de datos asociado a la respuesta.
 */
@Data
public class ApiResponse<T> {
	private boolean success;
    private String message;
    private T data;
    
    /**
     * Constructor para crear un objeto ApiResponse.
     *
     * @param success Indica si la operación fue exitosa.
     * @param message Mensaje descriptivo de la respuesta.
     * @param data    Datos asociados a la respuesta.
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
