package uk.co.idv.otp.adapter.verification.updater;

import lombok.Builder;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.otp.adapter.verification.loader.VerificationConverter;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.usecases.verify.OtpVerificationUpdater;

@Builder
public class ContextOtpVerificationUpdater implements OtpVerificationUpdater {

    private final VerificationClient verificationClient;
    private final VerificationConverter verificationConverter;

    @Builder.Default
    private final ClientCompleteVerificationRequestFactory factory = new ClientCompleteVerificationRequestFactory();

    public OtpVerification update(OtpVerification otpVerification) {
        ClientCompleteVerificationRequest request = factory.build(otpVerification);
        CompleteVerificationResult result = verificationClient.completeVerification(request);
        return otpVerification.toBuilder()
                .complete(result.isVerificationComplete())
                .contextSuccessful(result.isContextSuccessful())
                .contextComplete(result.isContextComplete())
                .build();
    }

}
