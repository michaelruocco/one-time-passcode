package uk.co.idv.otp.config.verificationloader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.otp.adapter.verification.loader.ContextOtpVerificationLoader;
import uk.co.idv.otp.adapter.verification.loader.VerificationConverter;
import uk.co.idv.otp.adapter.verification.updater.ContextOtpVerificationUpdater;
import uk.co.idv.otp.config.VerificationLoaderConfig;
import uk.co.idv.otp.usecases.send.OtpVerificationLoader;
import uk.co.idv.otp.usecases.verify.OtpVerificationUpdater;


@Slf4j
@RequiredArgsConstructor
public class ContextVerificationLoaderConfig implements VerificationLoaderConfig {

    private final VerificationClient verificationClient;

    @Override
    public OtpVerificationLoader verificationLoader() {
        return ContextOtpVerificationLoader.builder()
                .verificationClient(verificationClient)
                .verificationConverter(new VerificationConverter())
                .build();
    }

    @Override
    public OtpVerificationUpdater verificationUpdater() {
        return ContextOtpVerificationUpdater.builder()
                .verificationClient(verificationClient)
                .verificationConverter(new VerificationConverter())
                .build();
    }

}
