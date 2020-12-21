package uk.co.idv.otp.entities.send;

import java.util.UUID;

public interface ResendOtpRequestMother {

    static ResendOtpRequest build() {
        return build(UUID.fromString("624c63c6-a967-4b45-ab19-0237e1617122"));
    }

    static ResendOtpRequest build(UUID verificationId) {
        return new ResendOtpRequest(verificationId);
    }

}
