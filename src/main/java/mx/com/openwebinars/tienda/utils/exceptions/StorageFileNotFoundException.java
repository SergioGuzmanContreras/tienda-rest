package mx.com.openwebinars.tienda.utils.exceptions;

public class StorageFileNotFoundException  extends StorageException{
	
	private static final long serialVersionUID = -4471362769995692422L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }
    
	public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
