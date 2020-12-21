package uk.co.idv.otp.entities.verification;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Delivery {

    private final OtpDeliveryMethod method;
    private final Passcode passcode;
    private final String message;
    private final Instant sent;

}
