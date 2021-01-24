package uk.co.idv.otp.adapter.delivery.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;

public class IdvSmsTypeMessageAttributeValue extends MessageAttributeValue {

    public IdvSmsTypeMessageAttributeValue() {
        withStringValue("Transactional");
        withDataType("String");
    }

}
