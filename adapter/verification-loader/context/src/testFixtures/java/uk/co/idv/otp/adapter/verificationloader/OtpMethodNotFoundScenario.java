package uk.co.idv.otp.adapter.verificationloader;

import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.VerificationMother;

import java.util.UUID;

public class OtpMethodNotFoundScenario implements Scenario {

    public static final String ID = "9a54c8f7-7a2f-4b68-91df-35689a7c5848";

    @Override
    public boolean shouldExecute(UUID contextId) {
        return ID.equals(contextId.toString());
    }

    @Override
    public Verification apply(UUID contextId) {
        return VerificationMother.builder()
                .contextId(contextId)
                .methods(MethodsMother.empty())
                .build();
    }


}
