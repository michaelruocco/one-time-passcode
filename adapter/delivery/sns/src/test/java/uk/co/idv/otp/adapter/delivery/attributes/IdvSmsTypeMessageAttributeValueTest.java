package uk.co.idv.otp.adapter.delivery.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdvSmsTypeMessageAttributeValueTest {

    private final MessageAttributeValue value = new IdvSmsTypeMessageAttributeValue();

    @Test
    void shouldReturnDataType() {
        assertThat(value.getDataType()).isEqualTo("String");
    }

    @Test
    void shouldReturnValue() {
        assertThat(value.getStringValue()).isEqualTo("Transactional");
    }

}
