package cinema;

public class PurchasedSeatException extends InvalidSeatException {
    public PurchasedSeatException(String message) {
        super(message);
    }
}
