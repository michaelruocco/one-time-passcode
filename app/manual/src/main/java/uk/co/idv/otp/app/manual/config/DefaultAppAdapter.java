package uk.co.idv.otp.app.manual.config;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.RandomIdGenerator;

import java.time.Clock;

@Builder
@Getter
public class DefaultAppAdapter implements AppAdapter {

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    @Builder.Default
    private final IdGenerator idGenerator = new RandomIdGenerator();

}
