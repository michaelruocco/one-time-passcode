package uk.co.idv.otp.adapter.passcode.generator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.GeneratePasscodeRequest;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;

@RequiredArgsConstructor
public class RandomPasscodeGenerator implements PasscodeGenerator {

    private final Clock clock;
    private final RandomStringGenerator stringGenerator;

    public RandomPasscodeGenerator(Clock clock) {
        this(clock, buildGenerator());
    }

    @Override
    public Passcode generate(GeneratePasscodeRequest request) {
        Instant created = clock.instant();
        return Passcode.builder()
                .created(created)
                .expiry(created.plus(request.getPasscodeDuration()))
                .value(generateRandomNumericString(request.getPasscodeLength()))
                .build();
    }

    private String generateRandomNumericString(int length) {
        return stringGenerator.generate(length);
    }

    private static RandomStringGenerator buildGenerator() {
        return new RandomStringGenerator.Builder()
                .usingRandom(new SecureRandom()::nextInt)
                .selectFrom("0123456789".toCharArray())
                .build();
    }

}
