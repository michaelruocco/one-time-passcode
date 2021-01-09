package uk.co.idv.otp.adapter.verificationloader;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.client.VerificationClient;
import uk.co.idv.context.adapter.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.VerificationMother;

import java.time.Clock;
import java.util.UUID;

@RequiredArgsConstructor
public class StubVerificationClient implements VerificationClient {

    private final Scenarios createScenarios;

    public StubVerificationClient(Clock clock) {
        this(new Scenarios(clock));
    }

    @Override
    public Verification createVerification(ClientCreateVerificationRequest request) {
        UUID contextId = request.getContextId();
        Scenario scenario = createScenarios.find(contextId);
        return scenario.apply(contextId);
    }

    @Override
    public Verification completeVerification(ClientCompleteVerificationRequest request) {
        return VerificationMother.successful();
    }

}
