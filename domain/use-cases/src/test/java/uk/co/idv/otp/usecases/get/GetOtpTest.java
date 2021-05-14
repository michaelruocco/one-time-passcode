package uk.co.idv.otp.usecases.get;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.usecases.OtpVerificationRepository;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetOtpTest {

    private static final Instant NOW = Instant.now();

    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());
    private final OtpVerificationRepository repository = mock(OtpVerificationRepository.class);

    private final GetOtp getOtp = GetOtp.builder()
            .clock(clock)
            .repository(repository)
            .build();

    @Test
    void shouldThrowExceptionIfVerificationNotFound() {
        UUID id = UUID.fromString("50afc389-ede8-4820-8498-f1ac32aeeb45");
        given(repository.load(id)).willReturn(Optional.empty());

        Throwable error = catchThrowable(() -> getOtp.get(id));

        assertThat(error)
                .isInstanceOf(OtpVerificationNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnOtpVerificationIfFound() {
        Instant expiry = NOW.plus(Duration.ofMillis(1));
        OtpVerification expectedVerification = OtpVerificationMother.withExpiry(expiry);
        UUID id = expectedVerification.getId();
        given(repository.load(id)).willReturn(Optional.of(expectedVerification));

        OtpVerification verification = getOtp.get(id);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldThrowExceptionIfVerificationExpired() {
        UUID id = UUID.fromString("50afc389-ede8-4820-8498-f1ac32aeeb45");
        Instant expiry = NOW.minus(Duration.ofMillis(1));
        OtpVerification expiredVerification = OtpVerificationMother.withExpiry(expiry);
        given(repository.load(id)).willReturn(Optional.of(expiredVerification));

        OtpVerificationExpiredException error = catchThrowableOfType(
                () -> getOtp.get(id),
                OtpVerificationExpiredException.class
        );

        assertThat(error.getId()).isEqualTo(id);
        assertThat(error.getExpiry()).isEqualTo(expiry);
    }

    @Test
    void shouldReturnOtpVerificationIfFoundNotExpiredAndIncomplete() {
        Instant expiry = NOW.plus(Duration.ofMillis(1));
        OtpVerification expectedVerification = OtpVerificationMother.withExpiry(expiry);
        UUID id = expectedVerification.getId();
        given(repository.load(id)).willReturn(Optional.of(expectedVerification));

        OtpVerification verification = getOtp.getIfIncomplete(id);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnOtpVerificationIfFoundNotExpiredButComplete() {
        Instant expiry = NOW.plus(Duration.ofMillis(1));
        OtpVerification expectedVerification = OtpVerificationMother.builder()
                .expiry(expiry)
                .complete(true)
                .build();
        UUID id = expectedVerification.getId();
        given(repository.load(id)).willReturn(Optional.of(expectedVerification));

        Throwable error = catchThrowable(() -> getOtp.getIfIncomplete(id));

        assertThat(error)
                .isInstanceOf(OtpVerificationAlreadyCompleteException.class)
                .hasMessage(expectedVerification.getId().toString());
    }

}
