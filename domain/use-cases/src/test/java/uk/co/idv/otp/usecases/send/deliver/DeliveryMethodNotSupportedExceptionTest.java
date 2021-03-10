package uk.co.idv.otp.usecases.send.deliver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new DeliveryMethodNotSupportedException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
