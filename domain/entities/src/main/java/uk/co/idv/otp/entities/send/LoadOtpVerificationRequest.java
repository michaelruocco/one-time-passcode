package uk.co.idv.otp.entities.send;

import java.util.UUID;

public interface LoadOtpVerificationRequest {

    UUID getContextId();

    UUID getDeliveryMethodId();

}
