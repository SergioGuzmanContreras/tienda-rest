package mx.com.openwebinars.tienda.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mx.com.openwebinars.tienda.utils.exceptions.CategoriaNotFoundException;
import mx.com.openwebinars.tienda.utils.exceptions.ProductoNotFoundException;
import mx.com.openwebinars.tienda.utils.exceptions.SearchProductoNotResultException;
import mx.com.openwebinars.tienda.models.Error;

@RestControllerAdvice
public class GlobalControllerAdvice  extends ResponseEntityExceptionHandler{

	@ExceptionHandler({ ProductoNotFoundException.class, SearchProductoNotResultException.class})
	public ResponseEntity<Error> handlerProductoNoEncontrado(ProductoNotFoundException ex){		
		var error = Error.builder()
				.status(HttpStatus.NOT_FOUND)
				.date(LocalDateTime.now())
				.message(ex.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		var error = Error.builder()
				.status(status)
				.date(LocalDateTime.now())
				.message(ex.getMessage())
				.build();
		return ResponseEntity.status(status).headers(headers).body(error);		
	}

	
	@ExceptionHandler({ CategoriaNotFoundException.class})
	public ResponseEntity<Error> handlerCategoriaNoEncontrada(CategoriaNotFoundException ex){		
		var error = Error.builder()
				.status(HttpStatus.NOT_FOUND)
				.date(LocalDateTime.now())
				.message(ex.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
