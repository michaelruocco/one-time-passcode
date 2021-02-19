package uk.co.idv.otp.adapter.verificationloader;

import lombok.RequiredArgsConstructor;
import uk.co.idv.activity.entities.OnlinePurchaseMother;
import uk.co.idv.method.entities.method.MethodsMother;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class ValidScenario implements Scenario {

    private final Clock clock;

    @Override
    public boolean shouldExecute(UUID contextId) {
        return true;
    }

    @Override
    public Verification apply(UUID contextId) {
        Otp otp = OtpMother.withDeliveryMethod(DeliveryMethodMother.eligible());
        Instant created = clock.instant();
        return VerificationMother.builder()
                .contextId(contextId)
                .created(clock.instant())
                .expiry(created.plus(otp.getDuration()))
                .activity(OnlinePurchaseMother.build())
                .methods(MethodsMother.with(otp))
                .build();
    }

}
