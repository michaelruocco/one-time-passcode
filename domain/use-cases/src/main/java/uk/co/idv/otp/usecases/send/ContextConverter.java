package uk.co.idv.otp.usecases.send;

import lombok.Builder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.otp.entities.Verification;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.send.OtpParams;
import uk.co.idv.otp.entities.send.SendOtpRequest;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Builder
public class ContextConverter {

    private final IdGenerator idGenerator;
    private final Clock clock;
    private final OtpParamsExtractor otpParamsExtractor;

    public Verification toVerification(SendOtpRequest request, Context context) {
        Instant created = clock.instant();
        UUID deliveryMethodId = request.getDeliveryMethodId();
        OtpParams otpParams = otpParamsExtractor.extract(deliveryMethodId, context);
        OtpConfig otpConfig = otpParams.getOtpConfig();
        return Verification.builder()
                .id(idGenerator.generate())
                .contextId(context.getId())
                .created(created)
                .expiry(created.plus(otpConfig.getDuration()))
                .activity(context.getActivity())
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
