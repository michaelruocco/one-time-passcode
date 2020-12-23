package uk.co.idv.otp.usecases.send;

import java.util.UUID;

public class OtpNotNextEligibleMethodException extends RuntimeException {

    public OtpNotNextEligibleMethodException(UUID contextId) {
        super(contextId.toString());
    }

}
