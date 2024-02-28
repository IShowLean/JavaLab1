package account;

/**
 * Exception for invalid withdraw case.
 */
public class InvalidWithdrawException extends Exception{
    public InvalidWithdrawException(String message) {
        super(message);
    }
}