package app.model.exceptions;

public class UnknownPlaceException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6479266978456839619L;

	public UnknownPlaceException() {
    }

    public UnknownPlaceException(String message) {
        super(message);
    }
}
