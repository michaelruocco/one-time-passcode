package uk.co.idv.otp.adapter.passcode.generator;

import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.entities.passcode.GeneratePasscodeRequest;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;

import java.time.Clock;
import java.time.Instant;
import java.util.function.IntFunction;

@RequiredArgsConstructor
public class DefaultPasscodeGenerator implements PasscodeGenerator {

    private final Clock clock;
    private final IntFunction<String> valueGenerator;

    @Override
    public Passcode generate(GeneratePasscodeRequest request) {
        Instant created = clock.instant();
        return Passcode.builder()
                .created(created)
                .expiry(created.plus(request.getPasscodeDuration()))
                .value(valueGenerator.apply(request.getPasscodeLength()))
                .build();
    }

}
