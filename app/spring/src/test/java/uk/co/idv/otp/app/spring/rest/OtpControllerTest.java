package uk.co.idv.otp.app.spring.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.otp.app.manual.Application;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.send.ResendOtpRequestMother;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.send.SendOtpRequestMother;

import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpControllerTest {

    private final Application application = mock(Application.class);

    private final OtpController controller = new OtpController(application);

    @Test
    void shouldSendOtp() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification expectedVerification = givenVerificationCreatedFor(request);

        ResponseEntity<OtpVerification> response = controller.sendOtp(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnLocationForCreatedOtpVerification() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification expectedVerification = givenVerificationCreatedFor(request);

        ResponseEntity<OtpVerification> response = controller.sendOtp(request);

        String expectedLocation = String.format("/v1/otp-verifications/%s", expectedVerification.getId());
        assertThat(response.getHeaders()).contains(entry("Location", singletonList(expectedLocation)));
    }

    @Test
    void shouldGetOtpVerification() {
        UUID id = UUID.randomUUID();
        OtpVerification expectedVerification = givenVerificationLoadedFor(id);

        OtpVerification verification = controller.getOtp(id);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldResendOtp() {
        ResendOtpRequest request = ResendOtpRequestMother.build();
        OtpVerification expectedVerification = givenOtpResentFor(request);

        OtpVerification verification = controller.resendOtp(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    private OtpVerification givenVerificationCreatedFor(SendOtpRequest request) {
        OtpVerification verification = OtpVerificationMother.incomplete();
        given(application.sendOtp(request)).willReturn(verification);
        return verification;
    }

    private OtpVerification givenVerificationLoadedFor(UUID id) {
        OtpVerification verification = OtpVerificationMother.incomplete();
        given(application.getOtp(id)).willReturn(verification);
        return verification;
    }

    private OtpVerification givenOtpResentFor(ResendOtpRequest request) {
        OtpVerification verification = OtpVerificationMother.incomplete();
        given(application.resendOtp(request)).willReturn(verification);
        return verification;
    }

}
