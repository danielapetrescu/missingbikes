package dk.missingbikes.exception;

/**
 * The caller sent something the business rules reject: a photo with a forbidden
 * extension, a brand that already exists, an unknown brand id.
 *
 * It carries only a message. Deciding that this becomes HTTP 400 is the web
 * layer's job, in GlobalExceptionHandler, so that a page controller can render
 * the form again instead while an API controller answers with JSON.
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
