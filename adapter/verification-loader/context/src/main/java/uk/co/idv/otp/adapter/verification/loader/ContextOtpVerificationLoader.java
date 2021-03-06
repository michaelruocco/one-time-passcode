package uk.co.idv.otp.adapter.verification.loader;

import lombok.Builder;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.otp.entities.send.LoadOtpVerificationRequest;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.send.OtpVerificationLoader;

@Builder
public class ContextOtpVerificationLoader implements OtpVerificationLoader {

    private final VerificationClient verificationClient;
    private final VerificationConverter verificationConverter;

    @Builder.Default
    private final ClientCreateVerificationRequestFactory factory = new ClientCreateVerificationRequestFactory();

    public OtpVerification load(LoadOtpVerificationRequest request) {
        var clientRequest = factory.build(request.getContextId());
        var verification = verificationClient.createVerification(clientRequest);
        return verificationConverter.toOtpVerification(request, verification);
    }

}
