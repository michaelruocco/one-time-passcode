package uk.co.idv.otp.entities.send;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class SendOtpRequest implements LoadOtpVerificationRequest {

    private final UUID contextId;
    private final UUID deliveryMethodId;

}
