package dk.missingbikes.exception;

/**
 * The thing that was asked for does not exist. Mapped to HTTP 404 in the web layer.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
