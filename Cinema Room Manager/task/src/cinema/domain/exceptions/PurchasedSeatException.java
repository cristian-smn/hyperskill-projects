package cinema.domain.exceptions;

public class PurchasedSeatException extends InvalidSeatException {
    public PurchasedSeatException(String message) {
        super(message);
    }
}
