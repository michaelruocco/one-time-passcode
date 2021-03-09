package uk.co.idv.otp.entities.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.send.message.Message;

@Builder
@Data
//TODO unit test
public class DeliveryRequest {

    private final DeliveryMethod method;
    private final Message message;

    public String getDeliveryMethodType() {
        return method.getType();
    }

    public String getDeliveryMethodValue() {
        return method.getValue();
    }

    public String getMessageText() {
        return message.getText();
    }
}
