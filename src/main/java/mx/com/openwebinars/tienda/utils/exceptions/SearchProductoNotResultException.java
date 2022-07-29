package mx.com.openwebinars.tienda.utils.exceptions;

public class SearchProductoNotResultException extends RuntimeException{

	private static final long serialVersionUID = 6860994929030413902L;

	public SearchProductoNotResultException() {
		super("La búsqueda de productos no produjo resultados");
	}

	public SearchProductoNotResultException(String message) {
		super(String.format("El término de búsqueda '' %s '' no produjo resultados", message));
	}
}
