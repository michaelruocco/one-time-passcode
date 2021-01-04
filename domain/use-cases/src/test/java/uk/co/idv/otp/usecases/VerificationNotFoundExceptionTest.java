package uk.co.idv.otp.usecases;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationNotFoundExceptionTest {

    @Test
    void shouldReturnIdAsMessage() {
        UUID id = UUID.randomUUID();

        Throwable error = new VerificationNotFoundException(id);

        assertThat(error.getMessage()).isEqualTo(id.toString());
    }

}
