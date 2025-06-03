package com.inditex.zara.infrastructure.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Mensaje de error estándar para respuestas HTTP.
 * Proporciona información estructurada sobre errores.
 */
@Data
@Builder
public class ErrorMessage {

	private int status;
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
	private String message;
	private String path;
}
