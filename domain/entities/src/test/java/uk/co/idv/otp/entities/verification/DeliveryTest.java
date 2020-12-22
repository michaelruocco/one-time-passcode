package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.OtpDeliveryMethod;
import uk.co.idv.otp.entities.send.message.Message;

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
    void shouldReturnMessage() {
        Message message = mock(Message.class);

        Delivery delivery = Delivery.builder()
                .message(message)
                .build();

        assertThat(delivery.getMessage()).isEqualTo(message);
    }

    @Test
    void shouldReturnMessageId() {
        String messageId = "message-id";

        Delivery delivery = Delivery.builder()
                .messageId(messageId)
                .build();

        assertThat(delivery.getMessageId()).isEqualTo(messageId);
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
