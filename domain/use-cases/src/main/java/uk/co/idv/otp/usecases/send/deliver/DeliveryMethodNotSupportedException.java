package uk.co.idv.otp.usecases.send.deliver;

public class DeliveryMethodNotSupportedException extends RuntimeException {

    public DeliveryMethodNotSupportedException(String message) {
        super(message);
    }

}
