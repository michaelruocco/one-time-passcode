package uk.co.idv.otp.entities.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.entities.Verification;

@Builder
@Data
public class DeliveryRequest {

    private final Verification verification;
    private final Message message;

}
