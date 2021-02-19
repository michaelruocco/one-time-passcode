package uk.co.idv.otp.adapter.verificationloader;

import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredError;
import uk.co.idv.method.entities.verification.Verification;

import java.time.Instant;
import java.util.UUID;

public class ContextExpiredScenario implements Scenario {

    public static final String ID = "2b1f8ba4-00e7-4ad9-819f-5249af834f2e";
    public static final Instant EXPIRY = Instant.parse("2021-01-04T23:24:07.385Z");

    @Override
    public boolean shouldExecute(UUID contextId) {
        return ID.equals(contextId.toString());
    }

    @Override
    public Verification apply(UUID contextId) {
        throw new ApiErrorClientException(new ContextExpiredError(contextId, EXPIRY));
    }


}
