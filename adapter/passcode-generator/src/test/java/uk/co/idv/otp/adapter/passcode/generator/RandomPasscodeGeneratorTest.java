package uk.co.idv.otp.adapter.passcode.generator;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.Verification;
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

class RandomPasscodeGeneratorTest {

    private static final Instant NOW = Instant.now();

    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final PasscodeGenerator generator = new RandomPasscodeGenerator(clock);

    @Test
    void shouldPopulateCreatedOnPasscode() {
        Verification verification = mock(Verification.class);

        Passcode passcode = generator.generate(verification);

        assertThat(passcode.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateExpiryOnPasscodeBasedOnPasscodeDuration() {
        Duration duration = Duration.ofMinutes(5);
        Verification verification = mock(Verification.class);
        given(verification.getPasscodeDuration()).willReturn(duration);

        Passcode passcode = generator.generate(verification);

        assertThat(passcode.getExpiry()).isEqualTo(NOW.plus(duration));
    }

    @Test
    void shouldReturnPasscodeWithDigitValuesOfPasscodeLength() {
        int length = 8;
        GeneratePasscodeRequest request = mock(GeneratePasscodeRequest.class);
        given(request.getPasscodeLength()).willReturn(length);

        Passcode passcode = generator.generate(request);

        assertThat(passcode.getValue())
                .containsOnlyDigits()
                .hasSize(length);
    }

}
