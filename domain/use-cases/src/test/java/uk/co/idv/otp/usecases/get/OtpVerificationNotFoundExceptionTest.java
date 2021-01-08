package uk.co.idv.otp.usecases.get;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OtpVerificationNotFoundExceptionTest {

    @Test
    void shouldReturnIdAsMessage() {
        UUID id = UUID.randomUUID();

        Throwable error = new OtpVerificationNotFoundException(id);

        assertThat(error.getMessage()).isEqualTo(id.toString());
    }

}
