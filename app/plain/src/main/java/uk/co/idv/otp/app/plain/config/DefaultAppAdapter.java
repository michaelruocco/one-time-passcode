package uk.co.idv.otp.app.plain.config;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.randomvalue.uuid.RandomUuidGenerator;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;

@Builder
@Getter
public class DefaultAppAdapter implements AppAdapter {

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    @Builder.Default
    private final UuidGenerator uuidGenerator = new RandomUuidGenerator();

}
