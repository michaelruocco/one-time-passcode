package uk.co.idv.otp.adapter.verificationloader;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.client.VerificationClient;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequestMother;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.VerificationMother;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.entities.send.LoadOtpVerificationRequest;
import uk.co.idv.otp.entities.send.SendOtpRequestMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextOtpVerificationLoaderTest {

    private final VerificationClient verificationClient = mock(VerificationClient.class);
    private final VerificationConverter verificationConverter = mock(VerificationConverter.class);
    private final ClientCreateVerificationRequestFactory factory = mock(ClientCreateVerificationRequestFactory.class);

    private final ContextOtpVerificationLoader loader = ContextOtpVerificationLoader.builder()
            .verificationClient(verificationClient)
            .verificationConverter(verificationConverter)
            .factory(factory)
            .build();

    @Test
    void shouldLoadOtpVerification() {
        LoadOtpVerificationRequest request = SendOtpRequestMother.build();
        ClientCreateVerificationRequest createVerificationRequest = givenConvertedToCreateVerificationRequest(request);
        Verification verification = givenVerificationCreated(createVerificationRequest);
        OtpVerification expectedOtpVerification = givenConvertedToOtpVerification(request, verification);

        OtpVerification otpVerification = loader.load(request);

        assertThat(otpVerification).isEqualTo(expectedOtpVerification);
    }

    private ClientCreateVerificationRequest givenConvertedToCreateVerificationRequest(LoadOtpVerificationRequest request) {
        ClientCreateVerificationRequest createVerificationRequest = ClientCreateVerificationRequestMother.build();
        given(factory.build(request.getContextId())).willReturn(createVerificationRequest);
        return createVerificationRequest;
    }

    private Verification givenVerificationCreated(ClientCreateVerificationRequest request) {
        Verification verification = VerificationMother.successful();
        given(verificationClient.createVerification(request)).willReturn(verification);
        return verification;
    }

    private OtpVerification givenConvertedToOtpVerification(LoadOtpVerificationRequest request, Verification verification) {
        OtpVerification otpVerification = OtpVerificationMother.build();
        given(verificationConverter.toOtpVerification(request, verification)).willReturn(otpVerification);
        return otpVerification;
    }

}
