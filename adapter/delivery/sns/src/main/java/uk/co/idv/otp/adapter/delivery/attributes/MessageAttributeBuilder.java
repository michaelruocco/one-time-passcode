package uk.co.idv.otp.adapter.delivery.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MessageAttributeBuilder {

    private static final String SENDER_ID_KEY = "AWS.SNS.SMS.SenderID";
    private static final String MAX_PRICE_KEY = "AWS.SNS.SMS.MaxPrice";
    private static final String SMS_TYPE_KEY = "AWS.SNS.SMS.SMSType";

    private final String senderId;

    public Map<String, MessageAttributeValue> build() {
        return Map.of(
                SENDER_ID_KEY, new IdvSenderIdMessageAttributeValue(senderId),
                MAX_PRICE_KEY, new IdvMaxPriceMessageAttributeValue(),
                SMS_TYPE_KEY, new IdvSmsTypeMessageAttributeValue()
        );
    }

}
