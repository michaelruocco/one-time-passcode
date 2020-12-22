package uk.co.idv.otp.entities.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.otp.entities.send.message.Message;

import java.time.Instant;

@Builder
@Data
public class Delivery {

    private final OtpDeliveryMethod method;
    private final Message message;
    private final String messageId;
    private final Instant sent;

}
