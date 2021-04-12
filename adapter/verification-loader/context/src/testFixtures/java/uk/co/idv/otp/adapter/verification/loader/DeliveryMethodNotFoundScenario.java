package uk.co.idv.otp.adapter.verification.loader;

import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationScenario;
import uk.co.idv.method.entities.method.MethodsMother;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.util.UUID;

import static uk.co.idv.method.entities.otp.OtpMother.withDeliveryMethods;

public class DeliveryMethodNotFoundScenario implements CreateVerificationScenario {

    public static final UUID ID = UUID.fromString("a6af46ef-5080-4c77-9a26-27b966266d03");

    @Override
    public boolean shouldExecute(ClientCreateVerificationRequest request) {
        return ID.equals(request.getContextId());
    }

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        return VerificationMother.builder()
                .contextId(request.getContextId())
                .methods(MethodsMother.with(toOtpMethod(DeliveryMethodsMother.empty())))
                .build();
    }

    private static Otp toOtpMethod(DeliveryMethods deliveryMethods) {
        return withDeliveryMethods(deliveryMethods);
    }

}
