package mx.com.openwebinars.tienda.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoriaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4830167939191975849L;

	public CategoriaNotFoundException(Long id) {
		super("No se puede encontrar la categoria ID: " + id);
	}

	public CategoriaNotFoundException(String message) {
		super("No se puede guardar el registo ''" + message + "''");
	}

	public CategoriaNotFoundException(CategoriaEntity categoria) {
		super("No se puede guardar el registo ''" + categoria.getCategoria() + "'' ya existe con ID: " + categoria.getId());
	}

	public CategoriaNotFoundException() {
		super("No hay categorias disponibles");
	}

}