package uk.co.idv.otp.entities.send;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.otp.entities.verification.Message;
import uk.co.idv.otp.entities.verification.OtpDeliveryMethod;

@Builder
@Data
public class DeliveryRequest {

    private final OtpDeliveryMethod method;
    private final Message message;

}
