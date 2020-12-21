package uk.co.idv.otp.usecases.send.message;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "error-message";

        Throwable error = new ActivityNotSupportedException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
