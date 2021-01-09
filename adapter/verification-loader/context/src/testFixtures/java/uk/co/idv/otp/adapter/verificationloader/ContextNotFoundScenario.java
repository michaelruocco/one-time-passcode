package uk.co.idv.otp.adapter.verificationloader;

import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.json.error.contextnotfound.ContextNotFoundError;
import uk.co.idv.context.entities.verification.Verification;

import java.util.UUID;

public class ContextNotFoundScenario implements Scenario {

    public static final String ID = "9ed739ec-a252-4a3f-840c-4e2bdccf56e6";

    @Override
    public boolean shouldExecute(UUID contextId) {
        return ID.equals(contextId.toString());
    }

    @Override
    public Verification apply(UUID contextId) {
        throw new ApiErrorClientException(new ContextNotFoundError(contextId.toString()));
    }


}
