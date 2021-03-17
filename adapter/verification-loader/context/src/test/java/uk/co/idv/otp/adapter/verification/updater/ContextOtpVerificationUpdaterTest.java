package uk.co.idv.otp.adapter.verification.updater;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequestMother;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.CompleteVerificationResultMother;
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
        CompleteVerificationResult result = givenVerificationCompleted(completeRequest);

        OtpVerification updated = updater.update(otpVerification);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("complete", "contextComplete", "contextSuccessful")
                .isEqualTo(otpVerification);
        assertThat(updated.isComplete()).isEqualTo(result.isVerificationComplete());
        assertThat(updated.isContextComplete()).isEqualTo(result.isContextComplete());
        assertThat(updated.isContextSuccessful()).isEqualTo(result.isContextSuccessful());
    }

    private ClientCompleteVerificationRequest givenConvertedToCompleteVerificationRequest(OtpVerification verification) {
        ClientCompleteVerificationRequest completeRequest = ClientCompleteVerificationRequestMother.build();
        given(factory.build(verification)).willReturn(completeRequest);
        return completeRequest;
    }

    private CompleteVerificationResult givenVerificationCompleted(ClientCompleteVerificationRequest request) {
        CompleteVerificationResult result = CompleteVerificationResultMother.successful();
        given(verificationClient.completeVerification(request)).willReturn(result);
        return result;
    }

}
