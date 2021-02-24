package uk.co.idv.otp.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.PasscodeMother;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.send.message.MessageMother;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    @Test
    void shouldReturnPasscode() {
        Passcode passcode = PasscodeMother.build();

        Message message = Message.builder()
                .passcode(passcode)
                .build();

        assertThat(message.getPasscode()).isEqualTo(passcode);
    }

    @Test
    void shouldReturnText() {
        String text = "message-text";

        Message message = Message.builder()
                .text(text)
                .build();

        assertThat(message.getText()).isEqualTo(text);
    }

    @Test
    void shouldReturnMessageWithUpdatedPasscodeAndPasscodeReplacedInText() {
        String messageFormat = "message with passcode %s";
        Passcode originalPasscode = PasscodeMother.withValue("11111111");
        Message originalMessage = Message.builder()
                .passcode(originalPasscode)
                .text(String.format(messageFormat, originalPasscode.getValue()))
                .build();
        Passcode updatedPasscode = PasscodeMother.withValue("22222222");

        Message updatedMessage = originalMessage.update(updatedPasscode);

        assertThat(updatedMessage.getPasscode()).isEqualTo(updatedPasscode);
        assertThat(updatedMessage.getText()).isEqualTo(String.format(messageFormat, updatedPasscode.getValue()));
    }

    @Test
    void shouldReturnPasscodeIfValid() {
        Instant now = Instant.now();
        Passcode validPasscode = PasscodeMother.withExpiry(now.plusMillis(1));
        Message message = MessageMother.withPasscode(validPasscode);

        Optional<Passcode> passcode = message.getPasscodeIfValid(now);

        assertThat(passcode).contains(validPasscode);
    }

    @Test
    void shouldReturnEmptyPasscodeIfNotValid() {
        Instant now = Instant.now();
        Passcode invalidPasscode = PasscodeMother.withExpiry(now.minusMillis(1));
        Message message = MessageMother.withPasscode(invalidPasscode);

        Optional<Passcode> passcode = message.getPasscodeIfValid(now);

        assertThat(passcode).isEmpty();
    }

}
