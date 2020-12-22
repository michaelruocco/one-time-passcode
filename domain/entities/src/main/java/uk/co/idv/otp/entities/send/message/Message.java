package uk.co.idv.otp.entities.send.message;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.otp.entities.passcode.Passcode;

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

}
