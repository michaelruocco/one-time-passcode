package uk.co.idv.otp.entities.send;

import uk.co.idv.method.entities.otp.OtpConfigMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;

public interface OtpParamsMother {

    static OtpParams build() {
        return OtpParams.builder()
                .otpConfig(OtpConfigMother.build())
                .deliveryMethod(DeliveryMethodMother.eligible())
                .build();
    }

}
