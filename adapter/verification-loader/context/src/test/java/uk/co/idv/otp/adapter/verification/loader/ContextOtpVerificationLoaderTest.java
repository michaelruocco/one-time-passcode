package uk.co.idv.otp.adapter.verification.loader;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequestMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.entities.send.LoadOtpVerificationRequest;
import uk.co.idv.otp.entities.send.SendOtpRequestMother;
import uk.co.idv.otp.usecases.send.OtpVerificationLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextOtpVerificationLoaderTest {

    private final VerificationClient verificationClient = mock(VerificationClient.class);
    private final VerificationConverter verificationConverter = mock(VerificationConverter.class);
    private final ClientCreateVerificationRequestFactory factory = mock(ClientCreateVerificationRequestFactory.class);

    private final OtpVerificationLoader loader = ContextOtpVerificationLoader.builder()
            .verificationClient(verificationClient)
            .verificationConverter(verificationConverter)
            .factory(factory)
            .build();

    @Test
    void shouldLoadOtpVerification() {
        LoadOtpVerificationRequest request = SendOtpRequestMother.build();
        ClientCreateVerificationRequest createRequest = givenConvertedToCreateVerificationRequest(request);
        Verification verification = givenVerificationCreated(createRequest);
        OtpVerification expectedOtpVerification = givenConvertedToOtpVerification(request, verification);

        OtpVerification otpVerification = loader.load(request);

        assertThat(otpVerification).isEqualTo(expectedOtpVerification);
    }

    private ClientCreateVerificationRequest givenConvertedToCreateVerificationRequest(LoadOtpVerificationRequest request) {
        ClientCreateVerificationRequest createRequest = ClientCreateVerificationRequestMother.build();
        given(factory.build(request.getContextId())).willReturn(createRequest);
        return createRequest;
    }

    private Verification givenVerificationCreated(ClientCreateVerificationRequest request) {
        Verification verification = VerificationMother.successful();
        given(verificationClient.createVerification(request)).willReturn(verification);
        return verification;
    }

    private OtpVerification givenConvertedToOtpVerification(LoadOtpVerificationRequest request, Verification verification) {
        OtpVerification otpVerification = OtpVerificationMother.incomplete();
        given(verificationConverter.toOtpVerification(request, verification)).willReturn(otpVerification);
        return otpVerification;
    }

}
