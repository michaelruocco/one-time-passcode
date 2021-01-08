package uk.co.idv.otp.usecases.get;

import java.util.UUID;

public class OtpVerificationNotFoundException extends RuntimeException {

    public OtpVerificationNotFoundException(UUID id) {
        super(id.toString());
    }

}
