package uk.co.idv.otp.adapter.verification.updater;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequestMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.usecases.verify.OtpVerificationUpdater;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextOtpVerificationUpdaterTest {

    private final VerificationClient verificationClient = mock(VerificationClient.class);
    private final ClientCompleteVerificationRequestFactory factory = mock(ClientCompleteVerificationRequestFactory.class);

    private final OtpVerificationUpdater updater = ContextOtpVerificationUpdater.builder()
            .verificationClient(verificationClient)
            .factory(factory)
            .build();

    @Test
    void shouldCompleteOtpVerification() {
        OtpVerification otpVerification = OtpVerificationMother.incomplete();
        ClientCompleteVerificationRequest completeRequest = givenConvertedToCompleteVerificationRequest(otpVerification);
        Verification verification = givenVerificationCompleted(completeRequest);

        OtpVerification updated = updater.update(otpVerification);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("complete")
                .isEqualTo(otpVerification);
        assertThat(updated.isComplete()).isEqualTo(verification.isComplete());
    }

    private ClientCompleteVerificationRequest givenConvertedToCompleteVerificationRequest(OtpVerification verification) {
        ClientCompleteVerificationRequest completeRequest = ClientCompleteVerificationRequestMother.build();
        given(factory.build(verification)).willReturn(completeRequest);
        return completeRequest;
    }

    private Verification givenVerificationCompleted(ClientCompleteVerificationRequest request) {
        Verification verification = VerificationMother.successful();
        given(verificationClient.completeVerification(request)).willReturn(verification);
        return verification;
    }

}
