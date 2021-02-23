package uk.co.idv.otp.adapter.verification.loader;

import uk.co.idv.method.entities.method.MethodsMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;
import uk.co.idv.otp.adapter.verification.Scenario;

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
