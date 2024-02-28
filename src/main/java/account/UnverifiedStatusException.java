package account;

/**
 * Exception for user unverified status case.
 */
public class UnverifiedStatusException extends Exception {
    public UnverifiedStatusException(String message) {
            super(message);
        }
}
