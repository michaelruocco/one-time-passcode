package uk.co.idv.otp.adapter.verificationloader;

import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.VerificationMother;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;

import java.util.UUID;

import static uk.co.idv.method.entities.otp.OtpMother.withDeliveryMethods;

public class DeliveryMethodNotFoundScenario implements Scenario {

    public static final String ID = "a6af46ef-5080-4c77-9a26-27b966266d03";

    @Override
    public boolean shouldExecute(UUID contextId) {
        return ID.equals(contextId.toString());
    }

    @Override
    public Verification apply(UUID contextId) {
        return VerificationMother.withMethod(toOtpMethod(DeliveryMethodsMother.empty()));
    }

    private static Otp toOtpMethod(DeliveryMethods deliveryMethods) {
        return withDeliveryMethods(deliveryMethods);
    }

}
