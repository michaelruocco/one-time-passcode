package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.message.MessageMother;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DeliveryTest {

    @Test
    void shouldReturnMethod() {
        DeliveryMethod method = mock(DeliveryMethod.class);

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
    void shouldReturnTextFromMessage() {
        Message message = MessageMother.build();

        Delivery delivery = Delivery.builder()
                .message(message)
                .build();

        assertThat(delivery.getMessageText()).isEqualTo(message.getText());
    }

    @Test
    void shouldReturnPasscodeFromMessage() {
        Message message = MessageMother.build();

        Delivery delivery = Delivery.builder()
                .message(message)
                .build();

        assertThat(delivery.getPasscode()).isEqualTo(message.getPasscode());
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
