package uk.co.idv.otp.entities.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.otp.entities.send.message.Message;

@Builder
@Data
public class DeliveryRequest {

    private final OtpDeliveryMethod method;
    private final Message message;

}
