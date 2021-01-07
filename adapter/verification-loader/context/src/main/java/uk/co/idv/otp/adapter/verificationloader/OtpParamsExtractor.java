package uk.co.idv.otp.adapter.verificationloader;

import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;
import uk.co.idv.otp.entities.send.OtpParams;
import uk.co.idv.otp.usecases.send.DeliveryMethodNotEligibleException;
import uk.co.idv.otp.usecases.send.OtpNotNextEligibleMethodException;

import java.util.UUID;

public class OtpParamsExtractor {

    public OtpParams extract(Verification verification, UUID deliveryMethodId) {
        Otp otp = findOtpWithEligibleDeliveryMethod(verification, deliveryMethodId);
        DeliveryMethod deliveryMethod = otp.getDeliveryMethod(deliveryMethodId);
        if (!deliveryMethod.isEligible()) {
            throw new DeliveryMethodNotEligibleException(deliveryMethodId);
        }
        return OtpParams.builder()
                .otpConfig(otp.getConfig())
                .deliveryMethod(deliveryMethod)
                .build();
    }

    private Otp findOtpWithEligibleDeliveryMethod(Verification verification, UUID deliveryMethodId) {
        if (!verification.hasMethods()) {
            throw new OtpNotNextEligibleMethodException(verification.getContextId());
        }
        Otp otp = verification.getMethods().stream()
                .map(Otp.class::cast)
                .filter(method -> method.containsDeliveryMethod(deliveryMethodId))
                .findFirst()
                .orElseThrow(() -> new DeliveryMethodNotFoundException(deliveryMethodId));
        if (!otp.getDeliveryMethod(deliveryMethodId).isEligible()) {
            throw new DeliveryMethodNotEligibleException(deliveryMethodId);
        }
        return otp;
    }

}
