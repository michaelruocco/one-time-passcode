package uk.co.idv.otp.entities.delivery;

public class NoDeliveriesRemainingException extends RuntimeException {

    public NoDeliveriesRemainingException(int max) {
        super(Integer.toString(max));
    }

}
