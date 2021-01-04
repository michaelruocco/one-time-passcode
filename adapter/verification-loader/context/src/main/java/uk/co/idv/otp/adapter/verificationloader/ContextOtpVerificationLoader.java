package uk.co.idv.otp.adapter.verificationloader;

import lombok.Builder;
import uk.co.idv.context.adapter.client.ContextClient;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.otp.entities.send.LoadOtpVerificationRequest;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.send.OtpVerificationLoader;

@Builder
public class ContextOtpVerificationLoader implements OtpVerificationLoader {

    private final ClientCreateVerificationRequestFactory factory;
    private final ContextClient contextClient;
    private final VerificationConverter verificationConverter;

    public OtpVerification load(LoadOtpVerificationRequest request) {
        ClientCreateVerificationRequest clientRequest = factory.build(request.getContextId());
        Verification verification = contextClient.createVerification(clientRequest);
        return verificationConverter.toVerification(request, verification);
    }

}
