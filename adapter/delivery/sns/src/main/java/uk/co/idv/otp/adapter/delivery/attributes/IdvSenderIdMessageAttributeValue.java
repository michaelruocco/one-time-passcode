package uk.co.idv.otp.adapter.delivery.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;

public class IdvSenderIdMessageAttributeValue extends MessageAttributeValue {

    public IdvSenderIdMessageAttributeValue(String senderId) {
        withStringValue(senderId);
        withDataType("String");
    }

}
