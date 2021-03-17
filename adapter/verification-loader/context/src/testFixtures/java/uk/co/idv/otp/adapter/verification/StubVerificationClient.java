package uk.co.idv.otp.adapter.verification;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.CompleteVerificationResultMother;
import uk.co.idv.method.entities.verification.Verification;

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
    public CompleteVerificationResult completeVerification(ClientCompleteVerificationRequest request) {
        return CompleteVerificationResultMother.successful();
    }

}
