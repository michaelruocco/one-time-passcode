package uk.co.idv.otp.adapter.passcode.generator;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.passcode.GeneratePasscodeRequest;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IncrementingPasscodeGeneratorTest {

    private static final Instant NOW = Instant.now();

    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final PasscodeGenerator generator = new IncrementingPasscodeGenerator(clock);

    @Test
    void shouldPopulateCreatedOnPasscode() {
        OtpVerification verification = mock(OtpVerification.class);

        Passcode passcode = generator.generate(verification);

        assertThat(passcode.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateExpiryOnPasscodeBasedOnPasscodeDuration() {
        Duration duration = Duration.ofMinutes(5);
        OtpVerification verification = mock(OtpVerification.class);
        given(verification.getPasscodeDuration()).willReturn(duration);

        Passcode passcode = generator.generate(verification);

        assertThat(passcode.getExpiry()).isEqualTo(NOW.plus(duration));
    }

    @Test
    void shouldReturnPasscodeWithDigitsOfPasscodeLengthAndIncrementingValue() {
        int length = 8;
        GeneratePasscodeRequest request = mock(GeneratePasscodeRequest.class);
        given(request.getPasscodeLength()).willReturn(length);

        assertThat(generator.generate(request).getValue()).isEqualTo("00000001");
        assertThat(generator.generate(request).getValue()).isEqualTo("00000002");
        assertThat(generator.generate(request).getValue()).isEqualTo("00000003");
    }

}
