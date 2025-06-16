package groupe6.ldveh.model.logic;

public class UnknownPlaceException extends Exception {
	
	private static final long serialVersionUID = 8089784410628728635L;

	public UnknownPlaceException() {
		super("Unknow Place");
	}
	
	public UnknownPlaceException(String message) {
		super(message);
	}
}
