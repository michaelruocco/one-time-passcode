package uk.co.idv.otp.adapter.verification.loader;

import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;
import uk.co.idv.otp.adapter.verification.Scenario;

import java.util.UUID;

import static uk.co.idv.method.entities.otp.OtpMother.withDeliveryMethod;

public class DeliveryMethodNotEligibleScenario implements Scenario {

    public static final String ID = "02ebd8d2-0890-4661-b9e3-4156cfc72a0c";

    @Override
    public boolean shouldExecute(UUID contextId) {
        return ID.equals(contextId.toString());
    }

    @Override
    public Verification apply(UUID contextId) {
        return VerificationMother.withMethod(withDeliveryMethod(DeliveryMethodMother.ineligible()));
    }

}
