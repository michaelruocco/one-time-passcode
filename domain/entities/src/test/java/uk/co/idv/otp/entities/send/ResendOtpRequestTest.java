package uk.co.idv.otp.entities.send;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ResendOtpRequestTest {

    @Test
    void shouldReturnVerificationId() {
        UUID verificationId = UUID.randomUUID();

        ResendOtpRequest request = new ResendOtpRequest(verificationId);

        assertThat(request.getId()).isEqualTo(verificationId);
    }

}
