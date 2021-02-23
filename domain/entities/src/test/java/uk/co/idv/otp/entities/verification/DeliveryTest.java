package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryMother;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.message.MessageMother;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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

    @Test
    void shouldReturnPasscodeFromMessageIfValid() {
        Instant now = Instant.now();
        Passcode expectedPasscode = mock(Passcode.class);
        Message message = givenMessageWithValidPasscode(now, expectedPasscode);
        Delivery delivery = DeliveryMother.withMessage(message);

        Optional<Passcode> passcode = delivery.getPasscodeIfValid(now);

        assertThat(passcode).contains(expectedPasscode);
    }

    @Test
    void shouldNotReturnPasscodeFromMessageIfInvalid() {
        Instant now = Instant.now();
        Message message = givenMessageWithInvalidPasscode(now);
        Delivery delivery = DeliveryMother.withMessage(message);

        Optional<Passcode> passcode = delivery.getPasscodeIfValid(now);

        assertThat(passcode).isEmpty();
    }

    private Message givenMessageWithValidPasscode(Instant now, Passcode passcode) {
        Message message = mock(Message.class);
        given(message.getPasscodeIfValid(now)).willReturn(Optional.of(passcode));
        return message;
    }

    private Message givenMessageWithInvalidPasscode(Instant now) {
        Message message = mock(Message.class);
        given(message.getPasscodeIfValid(now)).willReturn(Optional.empty());
        return message;
    }

}
