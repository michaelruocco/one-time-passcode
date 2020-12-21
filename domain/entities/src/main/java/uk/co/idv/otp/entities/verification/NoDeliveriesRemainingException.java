package uk.co.idv.otp.entities.verification;

public class NoDeliveriesRemainingException extends RuntimeException {

    public NoDeliveriesRemainingException(int max) {
        super(Integer.toString(max));
    }

}
