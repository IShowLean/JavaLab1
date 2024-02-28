package account;

/**
 * Exception for invalid deposit case.
 */
public class InvalidDepositException extends Exception {
    public InvalidDepositException(String message) {
        super(message);
    }
}
