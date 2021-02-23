package uk.co.idv.otp.entities.send.message;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.otp.entities.passcode.Passcode;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class Message {

    private final Passcode passcode;
    private final String text;

    public Message update(Passcode passcode) {
        return Message.builder()
                .passcode(passcode)
                .text(replacePasscodeInText(passcode))
                .build();
    }

    private String replacePasscodeInText(Passcode passcode) {
        return text.replace(this.passcode.getValue(), passcode.getValue());
    }

    public Optional<Passcode> getPasscodeIfValid(Instant now) {
        if (!passcode.hasExpired(now)) {
            return Optional.of(passcode);
        }
        return Optional.empty();
    }


}
