package uk.co.idv.otp.app.plain;

import org.slf4j.MDC;
import uk.co.idv.otp.adapter.delivery.InMemoryDeliverOtp;
import uk.co.idv.otp.adapter.passcode.generator.IncrementingPasscodeGenerator;
import uk.co.idv.otp.adapter.verification.StubVerificationClient;
import uk.co.idv.otp.app.plain.config.AppAdapter;
import uk.co.idv.otp.app.plain.config.DefaultAppAdapter;
import uk.co.idv.otp.config.OtpAppConfig;
import uk.co.idv.otp.config.DefaultOtpAppConfig;
import uk.co.idv.otp.config.RepositoryConfig;
import uk.co.idv.otp.config.VerificationLoaderConfig;
import uk.co.idv.otp.config.repository.InMemoryRepositoryConfig;
import uk.co.idv.otp.config.verificationloader.ContextVerificationLoaderConfig;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.mruoc.randomvalue.uuid.NonRandomUuidGenerator;
import uk.co.mruoc.test.clock.OverridableClock;

import java.time.Instant;
import java.util.UUID;

public class TestHarness {

    private static final Instant NOW = Instant.parse("2021-01-07T19:30:00.000Z");
    private final OverridableClock clock = new OverridableClock(NOW);

    private final AppAdapter appAdapter = DefaultAppAdapter.builder()
            .clock(clock)
            .uuidGenerator(new NonRandomUuidGenerator())
            .build();

    private final InMemoryDeliverOtp deliverOtp = InMemoryDeliverOtp.builder()
            .clock(appAdapter.getClock())
            .uuidGenerator(appAdapter.getUuidGenerator())
            .build();

    private final VerificationLoaderConfig loaderConfig = new ContextVerificationLoaderConfig(new StubVerificationClient(appAdapter.getClock()));

    private final RepositoryConfig repositoryConfig = new InMemoryRepositoryConfig();

    private final OtpAppConfig otpConfig = DefaultOtpAppConfig.builder()
            .clock(appAdapter.getClock())
            .repository(repositoryConfig.verificationRepository())
            .deliverOtp(deliverOtp)
            .verificationLoader(loaderConfig.verificationLoader())
            .verificationUpdater(loaderConfig.verificationUpdater())
            .passcodeGenerator(new IncrementingPasscodeGenerator(appAdapter.getClock()))
            .build();

    private final Application application = new Application(otpConfig);

    public void setupMdc() {
        MDC.put("correlation-id", UUID.randomUUID().toString());
        MDC.put("channel-id", "abc");
    }

    public void clearMdc() {
        MDC.clear();
    }

    public Application getApplication() {
        return application;
    }

    public Instant now() {
        return clock.instant();
    }

    public Delivery getLastDelivery() {
        return deliverOtp.getLastDelivery();
    }

}
