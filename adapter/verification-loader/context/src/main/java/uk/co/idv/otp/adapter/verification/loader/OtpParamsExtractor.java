
package uk.co.idv.otp.adapter.verification.loader;

import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;
import uk.co.idv.otp.entities.send.OtpParams;
import uk.co.idv.otp.usecases.send.DeliveryMethodNotEligibleException;
import uk.co.idv.otp.usecases.send.OtpNotNextEligibleMethodException;

import java.util.UUID;

public class OtpParamsExtractor {

    public OtpParams extract(Verification verification, UUID deliveryMethodId) {
        var otp = findOtpWithEligibleDeliveryMethod(verification, deliveryMethodId);
        return OtpParams.builder()
                .otpConfig(otp.getConfig())
                .deliveryMethod(otp.getDeliveryMethod(deliveryMethodId))
                .build();
    }

    private Otp findOtpWithEligibleDeliveryMethod(Verification verification, UUID deliveryMethodId) {
        if (!verification.hasMethods()) {
            throw new OtpNotNextEligibleMethodException(verification.getContextId());
        }
        var otp = verification.getMethods().stream()
                .map(Otp.class::cast)
                .filter(method -> method.containsDeliveryMethod(deliveryMethodId))
                .findFirst()
                .orElseThrow(() -> new DeliveryMethodNotFoundException(deliveryMethodId));
        if (!otp.containsEligibleDeliveryMethod(deliveryMethodId)) {
            throw new DeliveryMethodNotEligibleException(deliveryMethodId);
        }
        return otp;
    }

}
