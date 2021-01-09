package uk.co.idv.otp.adapter.passcode.generator;

import org.apache.commons.text.RandomStringGenerator;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.function.IntFunction;

public class RandomPasscodeGenerator extends DefaultPasscodeGenerator {

    public RandomPasscodeGenerator(Clock clock) {
        super(clock, new RandomValueGenerator());
    }

    private static class RandomValueGenerator implements IntFunction<String> {

        private final RandomStringGenerator randomDigitStringGenerator;

        public RandomValueGenerator() {
            this.randomDigitStringGenerator = new RandomStringGenerator.Builder()
                    .usingRandom(new SecureRandom()::nextInt)
                    .selectFrom("0123456789".toCharArray())
                    .build();
        }

        @Override
        public String apply(int length) {
            return randomDigitStringGenerator.generate(length);
        }

    }

}
