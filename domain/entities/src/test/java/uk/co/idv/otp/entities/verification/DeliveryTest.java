package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DeliveryTest {

    @Test
    void shouldReturnMethod() {
        OtpDeliveryMethod method = mock(OtpDeliveryMethod.class);

        Delivery delivery = Delivery.builder()
                .method(method)
                .build();

        assertThat(delivery.getMethod()).isEqualTo(method);
    }

    @Test
    void shouldReturnPasscode() {
        Passcode passcode = mock(Passcode.class);

        Delivery delivery = Delivery.builder()
                .passcode(passcode)
                .build();

        assertThat(delivery.getPasscode()).isEqualTo(passcode);
    }

    @Test
    void shouldReturnMessage() {
        String message = "message";

        Delivery delivery = Delivery.builder()
                .message(message)
                .build();

        assertThat(delivery.getMessage()).isEqualTo(message);
    }

    @Test
    void shouldReturnSent() {
        Instant sent = Instant.now();

        Delivery delivery = Delivery.builder()
                .sent(sent)
                .build();

        assertThat(delivery.getSent()).isEqualTo(sent);
    }

}
