package mx.com.openwebinars.tienda.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import mx.com.openwebinars.tienda.models.Pedido;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PedidoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6771541307408357921L;

	public PedidoNotFoundException(Long id){
		super("No se puede encontrar el pedido ID: " + id);
	}

	public PedidoNotFoundException(String message) {
		super("No se puede guardar el registo ''" + message + "''");		
	}

	public PedidoNotFoundException(Pedido pedido) {
		super("No se puede guardar el registo ''" + pedido.getCliente() + "'' \n" + pedido);
	}

	public PedidoNotFoundException() {
		super("No hay pedidos disponibles");
	}


}
