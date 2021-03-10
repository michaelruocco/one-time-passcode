package uk.co.idv.otp.entities.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.send.message.Message;

@Builder
@Data
public class DefaultDeliveryRequest implements DeliveryRequest {

    private final DeliveryMethod method;
    private final Message message;

    @Override
    public String getDeliveryMethodType() {
        return method.getType();
    }

    @Override
    public String getDeliveryMethodValue() {
        return method.getValue();
    }

    @Override
    public String getMessageText() {
        return message.getText();
    }
}
