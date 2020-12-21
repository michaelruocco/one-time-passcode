package uk.co.idv.otp.entities.verification;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class OtpDeliveryMethod {

    private final UUID id;
    private final String type;
    private final String value;

}
