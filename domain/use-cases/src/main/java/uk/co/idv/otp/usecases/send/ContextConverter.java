package uk.co.idv.otp.usecases.send;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.otp.entities.Verification;

@RequiredArgsConstructor
public class ContextConverter {

    private final DeliveryMethodExtractor deliveryMethodExtractor;

    public Verification toVerification(Context context) {
        return null;
    }

}
