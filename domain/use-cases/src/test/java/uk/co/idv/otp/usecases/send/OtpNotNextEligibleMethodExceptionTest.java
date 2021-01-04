package uk.co.idv.otp.usecases.send;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OtpNotNextEligibleMethodExceptionTest {

    @Test
    void shouldReturnContextIdAsMessage() {
        UUID contextId = UUID.randomUUID();

        Throwable error = new OtpNotNextEligibleMethodException(contextId);

        assertThat(error.getMessage()).isEqualTo(contextId.toString());
    }

}
