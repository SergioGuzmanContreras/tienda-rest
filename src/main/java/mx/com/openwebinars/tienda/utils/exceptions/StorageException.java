package mx.com.openwebinars.tienda.utils.exceptions;

public class StorageException extends RuntimeException{

	private static final long serialVersionUID = -6068616473298014621L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
