package uk.co.idv.otp.usecases.send;

import java.util.UUID;

public class DeliveryMethodNotEligibleException extends RuntimeException {

    public DeliveryMethodNotEligibleException(UUID deliveryMethodId) {
        super(deliveryMethodId.toString());
    }

}
