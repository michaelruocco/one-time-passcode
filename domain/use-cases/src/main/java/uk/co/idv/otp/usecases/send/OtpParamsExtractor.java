package uk.co.idv.otp.usecases.send;

import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.otp.GetOtpIfNextEligible;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;
import uk.co.idv.otp.entities.send.OtpParams;

import java.util.UUID;
import java.util.stream.Stream;

public class OtpParamsExtractor {

    public OtpParams extract(UUID deliveryMethodId, Context context) {
        Stream<Otp> otpMethods = context.query(new GetOtpIfNextEligible());
        if (isEmpty(otpMethods)) {
            throw new OtpNotNextEligibleMethodException(context.getId());
        }
        Otp otp = findOtpWithDeliveryMethod(otpMethods, deliveryMethodId);
        return OtpParams.builder()
                .otpConfig(otp.getConfig())
                .deliveryMethod(otp.getDeliveryMethod(deliveryMethodId))
                .build();
    }

    private boolean isEmpty(Stream<Otp> methods) {
        return methods.count() < 1;
    }

    private Otp findOtpWithDeliveryMethod(Stream<Otp> otpMethods, UUID deliveryMethodId) {
        return otpMethods.filter(method -> method.containsDeliveryMethod(deliveryMethodId))
                .findFirst()
                .orElseThrow(() -> new DeliveryMethodNotFoundException(deliveryMethodId));
    }

}
