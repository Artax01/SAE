package groupe6.ldveh.model.logic;

public class ExistPlaceException extends Exception{

	private static final long serialVersionUID = 1362953947101527356L;

	public ExistPlaceException() {
		super("Already exsting place");
	}
	
	public ExistPlaceException (String message) {
		super(message);
	}
}
