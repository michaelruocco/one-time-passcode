package uk.co.idv.otp.entities.send.message;

import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.PasscodeMother;

public class MessageMother {

    private MessageMother() {
        // utility class
    }

    public static Message build() {
        return withPasscode(PasscodeMother.build());
    }

    public static Message withPasscode(Passcode passcode) {
        return Message.builder()
                .passcode(passcode)
                .text(toMessage(passcode))
                .build();
    }

    private static String toMessage(Passcode passcode) {
        return String.format("Use one time code %s " +
                "to make a payment of GBP 10.99 " +
                "to Amazon with card ending 1111. " +
                "Never share this code with anyone.", passcode.getValue());
    }

}
