package uk.co.idv.otp.entities.verification;

public class MessageMother {

    private MessageMother() {
        // utility class
    }

    public static Message build() {
        Passcode passcode = PasscodeMother.build();
        return Message.builder()
                .passcode(passcode)
                .text(toMessage(passcode))
                .build();
    }

    private static String toMessage(Passcode passcode) {
        return String.format("Use one time code %s to make a payment of £9.99" +
                "to Amazon with card ending 8928. " +
                "Never share this code with anyone", passcode.getValue());
    }

}
