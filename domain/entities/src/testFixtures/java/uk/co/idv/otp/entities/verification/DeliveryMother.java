package uk.co.idv.otp.entities.verification;

import java.time.Instant;

public interface DeliveryMother {

    static Delivery build() {
        return builder().build();
    }

    static Delivery withSent(Instant sent) {
        return builder().sent(sent).build();
    }

    static Delivery.DeliveryBuilder builder() {
        Passcode passcode = PasscodeMother.build();
        return Delivery.builder()
                .method(SmsOtpDeliveryMethodMother.sms())
                .passcode(passcode)
                .message(toMessage(passcode))
                .sent(Instant.parse("2020-09-14T20:03:02.002Z"));
    }

    private static String toMessage(Passcode passcode) {
        return String.format("Use one time code %s to make a payment of £9.99 to Amazon with card ending 8928. " +
                "Never share this code with anyone", passcode.getValue());
    }

}
