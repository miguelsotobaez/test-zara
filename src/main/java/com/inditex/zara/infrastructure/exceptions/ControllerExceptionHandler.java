package com.inditex.zara.infrastructure.exceptions;

import com.inditex.zara.domain.exceptions.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Manejador global de excepciones para la capa de infraestructura.
 * Captura excepciones del dominio y las convierte en respuestas HTTP apropiadas.
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	@ExceptionHandler(PriceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handlePriceNotFoundException(PriceNotFoundException ex) {
		log.error("Price not found: {}", ex.getMessage());
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.message(ex.getMessage())
				.status(HttpStatus.NOT_FOUND.value())
				.build();
				
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		log.error("Method argument type mismatch: {}", ex.getMessage());
		
		String message = "Invalid parameter format";
		if (ex.getName().equals("date")) {
			message = "Invalid date format. Expected formats: 'yyyy-MM-dd HH:mm:ss' or 'yyyy-MM-dd-HH.mm.ss'. " +
					 "Example: '2020-06-14 15:00:00' or '2020-06-14-15.00.00'";
		}
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.message(message)
				.status(HttpStatus.BAD_REQUEST.value())
				.build();
				
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorMessage> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
		log.error("Missing required parameter: {}", ex.getMessage());
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.message("Missing required parameter: " + ex.getParameterName())
				.status(HttpStatus.BAD_REQUEST.value())
				.build();
				
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
		log.error("Invalid argument: {}", ex.getMessage());
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value())
				.build();
				
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
}
