package uk.co.idv.otp.config.verificationloader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.otp.adapter.verificationloader.ContextOtpVerificationLoader;
import uk.co.idv.otp.adapter.verificationloader.VerificationConverter;
import uk.co.idv.otp.config.VerificationLoaderConfig;
import uk.co.idv.otp.usecases.send.OtpVerificationLoader;


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

}
