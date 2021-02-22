package uk.co.idv.otp.entities.verify;

import java.util.Arrays;
import java.util.UUID;

public interface VerifyOtpRequestMother {

    static VerifyOtpRequest build() {
        return build(UUID.fromString("624c63c6-a967-4b45-ab19-0237e1617122"));
    }

    static VerifyOtpRequest build(UUID verificationId) {
        return VerifyOtpRequest.builder()
                .id(verificationId)
                .passcodes(Arrays.asList("11111111", "22222222"))
                .build();
    }

}
