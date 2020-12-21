package uk.co.idv.otp.usecases;

import java.util.UUID;

public class VerificationNotFoundException extends RuntimeException {

    public VerificationNotFoundException(UUID id) {
        super(id.toString());
    }

}
