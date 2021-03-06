package uk.co.idv.otp.adapter.verification.loader;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.send.LoadOtpVerificationRequest;

@RequiredArgsConstructor
public class VerificationConverter {

    private final OtpParamsExtractor otpParamsExtractor;

    public VerificationConverter() {
        this(new OtpParamsExtractor());
    }

    public OtpVerification toOtpVerification(LoadOtpVerificationRequest request, Verification verification) {
        var otpParams = otpParamsExtractor.extract(verification, request.getDeliveryMethodId());
        var otpConfig = otpParams.getOtpConfig();
        return OtpVerification.builder()
                .id(verification.getId())
                .contextId(verification.getContextId())
                .created(verification.getCreated())
                .expiry(verification.getExpiry())
                .activity(verification.getActivity())
                .protectSensitiveData(verification.isProtectSensitiveData())
                .deliveryMethod(otpParams.getDeliveryMethod())
                .config(otpConfig)
                .deliveries(buildEmptyDeliveries(otpConfig))
                .build();
    }

    private Deliveries buildEmptyDeliveries(OtpConfig config) {
        return Deliveries.builder()
                .max(config.getMaxNumberOfPasscodeDeliveries())
                .build();
    }

}
