package uk.co.idv.otp.usecases.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.verify.AttemptedPasscodes;
import uk.co.idv.otp.entities.verify.VerifyOtpRequest;
import uk.co.idv.otp.entities.verify.VerifyOtpRequestMother;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.get.GetOtp;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyOtpTest {

    private static final Instant NOW = Instant.now();

    private final GetOtp getOtp = mock(GetOtp.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());
    private final OtpVerificationUpdater updater = mock(OtpVerificationUpdater.class);
    private final OtpVerificationRepository repository = mock(OtpVerificationRepository.class);

    private final VerifyOtp verifyOtp = VerifyOtp.builder()
            .getOtp(getOtp)
            .clock(clock)
            .updater(updater)
            .repository(repository)
            .build();

    @Test
    void shouldVerifyAttemptsPasscodesAgainstVerification() {
        VerifyOtpRequest request = VerifyOtpRequestMother.build();
        OtpVerification original = givenOriginalOtpVerification(request);

        verifyOtp.verify(request);

        ArgumentCaptor<AttemptedPasscodes> captor = ArgumentCaptor.forClass(AttemptedPasscodes.class);
        verify(original).verify(captor.capture());
        AttemptedPasscodes attemptedPasscodes = captor.getValue();
        assertThat(attemptedPasscodes.getTimestamp()).isEqualTo(NOW);
        assertThat(attemptedPasscodes.getValues()).isEqualTo(request.getPasscodes());
    }

    @Test
    void shouldUpdateAndSaveVerificationAfterPreformingVerify() {
        VerifyOtpRequest request = VerifyOtpRequestMother.build();
        OtpVerification original = givenOriginalOtpVerification(request);
        OtpVerification verified = givenVerifiedOtpVerification(original);
        OtpVerification updated = givenUpdatedOtpVerification(verified);

        OtpVerification verification = verifyOtp.verify(request);

        verify(repository).save(updated);
        assertThat(verification).isEqualTo(updated);
    }

    private OtpVerification givenOriginalOtpVerification(VerifyOtpRequest request) {
        OtpVerification verification = mock(OtpVerification.class);
        given(getOtp.get(request.getId())).willReturn(verification);
        return verification;
    }

    private OtpVerification givenVerifiedOtpVerification(OtpVerification original) {
        OtpVerification verified = mock(OtpVerification.class);
        given(original.verify(any(AttemptedPasscodes.class))).willReturn(verified);
        return verified;
    }

    private OtpVerification givenUpdatedOtpVerification(OtpVerification verified) {
        OtpVerification updated = mock(OtpVerification.class);
        given(updater.update(verified)).willReturn(updated);
        return updated;
    }

}
