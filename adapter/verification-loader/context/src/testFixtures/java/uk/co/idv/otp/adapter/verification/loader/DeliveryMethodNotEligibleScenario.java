package uk.co.idv.otp.adapter.verification.loader;

import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationScenario;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.util.UUID;

import static uk.co.idv.method.entities.otp.OtpMother.withDeliveryMethod;

public class DeliveryMethodNotEligibleScenario implements CreateVerificationScenario {

    public static final UUID ID = UUID.fromString("02ebd8d2-0890-4661-b9e3-4156cfc72a0c");

    @Override
    public boolean shouldExecute(ClientCreateVerificationRequest request) {
        return ID.equals(request.getContextId());
    }

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        return VerificationMother.withMethod(withDeliveryMethod(DeliveryMethodMother.ineligible()));
    }

}
