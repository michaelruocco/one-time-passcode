package uk.co.idv.otp.adapter.passcode.generator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import uk.co.idv.otp.entities.passcode.GeneratePasscodeRequest;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;

import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class IncrementingPasscodeGenerator implements PasscodeGenerator {

    private final AtomicInteger count = new AtomicInteger();
    private final Clock clock;

    @Override
    public Passcode generate(GeneratePasscodeRequest request) {
        Instant created = clock.instant();
        return Passcode.builder()
                .created(created)
                .expiry(created.plus(request.getPasscodeDuration()))
                .value(generateNumericStringAndIncrementCounter(request.getPasscodeLength()))
                .build();
    }

    private String generateNumericStringAndIncrementCounter(int length) {
        String value = Integer.toString(count.incrementAndGet());
        return StringUtils.leftPad(value, length, "0");
    }

}
