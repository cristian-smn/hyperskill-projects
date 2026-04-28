package cinema.domain.exceptions;

public class SeatOutOfBoundsException extends InvalidSeatException {
    public SeatOutOfBoundsException(String message) {
        super(message);
    }
}
