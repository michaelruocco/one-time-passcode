package uk.co.idv.otp.entities.verification;

import uk.co.idv.otp.entities.delivery.OtpDeliveryMethod;

import java.util.UUID;

public interface SmsOtpDeliveryMethodMother {

    static OtpDeliveryMethod sms() {
        return builder().build();
    }

    static OtpDeliveryMethod.OtpDeliveryMethodBuilder builder() {
        return OtpDeliveryMethod.builder()
                .id(UUID.fromString("cedbf56f-66d5-41e0-a669-01559b031c7c"))
                .type("sms")
                .value("+447809123456");
    }

}
