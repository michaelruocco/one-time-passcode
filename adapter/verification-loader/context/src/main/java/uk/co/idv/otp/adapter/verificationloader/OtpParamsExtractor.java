package uk.co.idv.otp.adapter.verificationloader;

import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;
import uk.co.idv.otp.entities.send.OtpParams;
import uk.co.idv.otp.usecases.send.OtpNotNextEligibleMethodException;

import java.util.UUID;
import java.util.stream.Stream;

public class OtpParamsExtractor {

    public OtpParams extract(UUID deliveryMethodId, Verification verification) {
        Stream<Otp> otpMethods = verification.getMethods().stream()
                .map(Otp.class::cast)
                .filter(Method::isEligible);
        if (isEmpty(otpMethods)) {
            throw new OtpNotNextEligibleMethodException(verification.getContextId());
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
