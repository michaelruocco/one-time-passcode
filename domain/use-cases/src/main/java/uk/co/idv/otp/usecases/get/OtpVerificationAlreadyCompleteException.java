package uk.co.idv.otp.usecases.get;

import java.util.UUID;

public class OtpVerificationAlreadyCompleteException extends RuntimeException {

    public OtpVerificationAlreadyCompleteException(UUID id) {
        super(id.toString());
    }

}
