package uk.co.idv.otp.usecases.send.message;

public class ActivityNotSupportedException extends RuntimeException {

    public ActivityNotSupportedException(String message) {
        super(message);
    }

}
