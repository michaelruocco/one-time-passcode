package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.otp.entities.send.LoadContextRequest;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.Verification;

@Builder
public class VerificationLoader {

    private final ContextLoader contextLoader;
    private final ContextConverter contextConverter;

    public Verification send(SendOtpRequest request) {
        Context context = contextLoader.load(toLoadContextRequest(request));
        return contextConverter.toVerification(request, context);
    }

    private LoadContextRequest toLoadContextRequest(SendOtpRequest request) {
        return LoadContextRequest.builder()
                .contextId(request.getContextId())
                .deliveryMethodId(request.getDeliveryMethodId())
                .build();
    }
}
