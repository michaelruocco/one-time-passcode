package uk.co.idv.otp.adapter.delivery.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdvSenderIdMessageAttributeValueTest {

    private static final String SENDER_ID = "sender-id";

    private final MessageAttributeValue value = new IdvSenderIdMessageAttributeValue(SENDER_ID);

    @Test
    void shouldReturnDataType() {
        assertThat(value.getDataType()).isEqualTo("String");
    }

    @Test
    void shouldReturnValue() {
        assertThat(value.getStringValue()).isEqualTo(SENDER_ID);
    }

}
