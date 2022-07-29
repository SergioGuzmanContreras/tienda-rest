package mx.com.openwebinars.tienda.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6771541307408357921L;

	public ProductoNotFoundException(Long id){
		super("No se puede encontrar el producto ID: " + id);
	}

}
