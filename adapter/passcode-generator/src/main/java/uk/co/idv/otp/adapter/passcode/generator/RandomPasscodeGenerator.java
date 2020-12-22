package uk.co.idv.otp.adapter.passcode.generator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.GeneratePasscodeRequest;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;

import java.time.Clock;
import java.time.Instant;

@RequiredArgsConstructor
public class RandomPasscodeGenerator implements PasscodeGenerator {

    private static final boolean USE_LETTERS = false;
    private static final boolean USE_NUMBERS = true;

    private final Clock clock;

    @Override
    public Passcode generate(GeneratePasscodeRequest request) {
        Instant created = clock.instant();
        return Passcode.builder()
                .created(created)
                .expiry(created.plus(request.getPasscodeDuration()))
                .value(generateRandomNumericString(request.getPasscodeLength()))
                .build();
    }

    private static String generateRandomNumericString(int length) {
        return RandomStringUtils.random(length, USE_LETTERS, USE_NUMBERS);
    }

}
