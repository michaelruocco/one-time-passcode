package uk.co.idv.otp.app.manual;

import org.slf4j.MDC;
import uk.co.idv.common.usecases.id.NonRandomIdGenerator;
import uk.co.idv.otp.adapter.delivery.InMemoryDeliverOtp;
import uk.co.idv.otp.adapter.passcode.generator.IncrementingPasscodeGenerator;
import uk.co.idv.otp.adapter.repository.InMemoryOtpVerificationRepository;
import uk.co.idv.otp.adapter.verificationloader.StubVerificationClient;
import uk.co.idv.otp.app.manual.config.AppAdapter;
import uk.co.idv.otp.app.manual.config.DefaultAppAdapter;
import uk.co.idv.otp.config.OtpAppConfig;
import uk.co.idv.otp.config.VerificationLoaderConfig;
import uk.co.idv.otp.config.verificationloader.ContextVerificationLoaderConfig;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.mruoc.test.clock.OverridableClock;

import java.time.Instant;
import java.util.UUID;

public class TestHarness {

    private static final Instant NOW = Instant.parse("2021-01-07T19:30:00.000Z");
    private final OverridableClock clock = new OverridableClock(NOW);

    private final AppAdapter appAdapter = DefaultAppAdapter.builder()
            .clock(clock)
            .idGenerator(new NonRandomIdGenerator())
            .build();

    private final InMemoryDeliverOtp deliverOtp = InMemoryDeliverOtp.builder()
            .clock(appAdapter.getClock())
            .idGenerator(appAdapter.getIdGenerator())
            .build();

    private final VerificationLoaderConfig loaderConfig = ContextVerificationLoaderConfig.builder()
            .clock(appAdapter.getClock())
            .verificationClient(new StubVerificationClient())
            .build();

    private final OtpAppConfig otpConfig = OtpAppConfig.builder()
            .clock(appAdapter.getClock())
            .repository(new InMemoryOtpVerificationRepository())
            .deliverOtp(deliverOtp)
            .verificationLoader(loaderConfig.verificationLoader())
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
