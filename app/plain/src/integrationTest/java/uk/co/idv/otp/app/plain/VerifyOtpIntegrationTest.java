package uk.co.idv.otp.app.plain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.verify.VerifyOtpRequest;
import uk.co.idv.otp.usecases.get.OtpVerificationAlreadyCompleteException;
import uk.co.idv.otp.usecases.get.OtpVerificationExpiredException;
import uk.co.idv.otp.usecases.get.OtpVerificationNotFoundException;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class VerifyOtpIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @BeforeEach
    void setUp() {
        harness.setupMdc();
    }

    @AfterEach
    void tearDown() {
        harness.clearMdc();
    }

    @Test
    void shouldThrowExceptionIfVerificationIsNotFound() {
        String notFoundId = "9ed739ec-a252-4a3f-840c-4e2bdccf56e6";
        VerifyOtpRequest request = VerifyOtpRequest.builder()
                .id(UUID.fromString(notFoundId))
                .passcodes(Collections.emptyList())
                .build();

        OtpVerificationNotFoundException error = catchThrowableOfType(
                () -> application.verifyOtp(request),
                OtpVerificationNotFoundException.class
        );

        assertThat(error.getMessage()).isEqualTo(notFoundId);
    }

    @Test
    void shouldThrowExceptionIfVerificationHasExpired() {
        OtpVerification verification = harness.givenIncompleteVerification();
        VerifyOtpRequest request = VerifyOtpRequest.builder()
                .id(verification.getId())
                .passcodes(Collections.emptyList())
                .build();
        Duration duration = Duration.ofHours(1);
        harness.fastForwardTimeBy(duration);

        OtpVerificationExpiredException error = catchThrowableOfType(
                () -> application.verifyOtp(request),
                OtpVerificationExpiredException.class
        );

        String expectedMessage = String.format("verification %s expired at 2021-01-07T19:35:00Z", verification.getId().toString());
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnUnsuccessfulVerificationIfPasscodeIsNotVerifiedCorrectly() {
        OtpVerification verification = harness.givenIncompleteVerification();
        VerifyOtpRequest request = toVerifyOtpRequestWithoutPasscode(verification.getId());

        OtpVerification completeVerification = application.verifyOtp(request);

        assertThat(completeVerification.isComplete()).isTrue();
        assertThat(completeVerification.isSuccessful()).isFalse();
    }

    @Test
    void shouldUpdateVerificationIfPasscodeIsNotVerifiedCorrectly() {
        OtpVerification verification = harness.givenIncompleteVerification();
        VerifyOtpRequest request = toVerifyOtpRequestWithoutPasscode(verification.getId());

        OtpVerification completeVerification = application.verifyOtp(request);

        ClientCompleteVerificationRequest lastRequest = harness.getLastCompleteVerificationClientRequest();
        assertThat(lastRequest.getContextId()).isEqualTo(completeVerification.getContextId());
        assertThat(lastRequest.getBody().getId()).isEqualTo(completeVerification.getId());
        assertThat(lastRequest.getBody().isSuccessful()).isFalse();
    }

    @Test
    void shouldReturnSuccessfulVerificationIfPasscodeIsVerifiedCorrectly() {
        OtpVerification verification = harness.givenIncompleteVerification();
        VerifyOtpRequest request = toVerifyOtpRequestWithCorrectPasscode(verification.getId());

        OtpVerification completeVerification = application.verifyOtp(request);

        assertThat(completeVerification.isComplete()).isTrue();
        assertThat(completeVerification.isSuccessful()).isTrue();
    }

    @Test
    void shouldUpdateVerificationIfPasscodeIsVerifiedCorrectly() {
        OtpVerification verification = harness.givenIncompleteVerification();
        VerifyOtpRequest request = toVerifyOtpRequestWithCorrectPasscode(verification.getId());

        OtpVerification completeVerification = application.verifyOtp(request);

        ClientCompleteVerificationRequest lastRequest = harness.getLastCompleteVerificationClientRequest();
        assertThat(lastRequest.getContextId()).isEqualTo(completeVerification.getContextId());
        assertThat(lastRequest.getBody().getId()).isEqualTo(completeVerification.getId());
        assertThat(lastRequest.getBody().isSuccessful()).isTrue();
    }

    @Test
    void shouldUpdateContextCompleteAndSuccessfulFromResponse() {
        OtpVerification verification = harness.givenIncompleteVerification();
        VerifyOtpRequest request = toVerifyOtpRequestWithCorrectPasscode(verification.getId());

        OtpVerification completeVerification = application.verifyOtp(request);

        assertThat(completeVerification.isContextComplete()).isTrue();
        assertThat(completeVerification.isContextSuccessful()).isTrue();
    }

    @Test
    void shouldNotResentOtpIfVerificationIsComplete() {
        OtpVerification verification = harness.givenIncompleteVerification();
        VerifyOtpRequest verifyRequest = toVerifyOtpRequestWithCorrectPasscode(verification.getId());
        OtpVerification completeVerification = application.verifyOtp(verifyRequest);
        ResendOtpRequest resendRequest = new ResendOtpRequest(completeVerification.getId());

        Throwable error = catchThrowable(() -> application.resendOtp(resendRequest));

        assertThat(error)
                .isInstanceOf(OtpVerificationAlreadyCompleteException.class)
                .hasMessage(completeVerification.getId().toString());
    }

    private VerifyOtpRequest toVerifyOtpRequestWithoutPasscode(UUID id) {
        return toVerifyOtpRequestBuilder(id)
                .passcodes(Collections.emptyList())
                .build();
    }

    private VerifyOtpRequest toVerifyOtpRequestWithCorrectPasscode(UUID id) {
        return toVerifyOtpRequestBuilder(id)
                .passcodes(Collections.singleton("00000001"))
                .build();
    }

    private VerifyOtpRequest.VerifyOtpRequestBuilder toVerifyOtpRequestBuilder(UUID id) {
        return VerifyOtpRequest.builder().id(id);
    }

}
