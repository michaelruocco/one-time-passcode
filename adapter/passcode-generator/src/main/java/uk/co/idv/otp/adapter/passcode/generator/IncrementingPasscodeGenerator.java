package uk.co.idv.otp.adapter.passcode.generator;

import org.apache.commons.lang3.StringUtils;

import java.time.Clock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;

public class IncrementingPasscodeGenerator extends DefaultPasscodeGenerator {

    public IncrementingPasscodeGenerator(Clock clock) {
        super(clock, new IncrementingValueGenerator());
    }

    private static class IncrementingValueGenerator implements IntFunction<String> {

        private final AtomicInteger count = new AtomicInteger();

        @Override
        public String apply(int length) {
            return generateValueAndIncrementCounter(length);
        }

        private String generateValueAndIncrementCounter(int length) {
            var value = Integer.toString(count.incrementAndGet());
            return StringUtils.leftPad(value, length, "0");
        }

    }

}
