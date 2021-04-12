package uk.co.idv.otp.adapter.verification.loader;

import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationScenario;
import uk.co.idv.method.entities.method.MethodsMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.util.UUID;

public class OtpMethodNotFoundScenario implements CreateVerificationScenario {

    public static final UUID ID = UUID.fromString("9a54c8f7-7a2f-4b68-91df-35689a7c5848");

    @Override
    public boolean shouldExecute(ClientCreateVerificationRequest request) {
        return ID.equals(request.getContextId());
    }

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        return VerificationMother.builder()
                .contextId(request.getContextId())
                .methods(MethodsMother.empty())
                .build();
    }


}
