package uk.co.idv.otp.adapter.delivery.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdvMaxPriceMessageAttributeValueTest {

    private final MessageAttributeValue value = new IdvMaxPriceMessageAttributeValue();

    @Test
    void shouldReturnDataType() {
        assertThat(value.getDataType()).isEqualTo("Number");
    }

    @Test
    void shouldReturnValue() {
        assertThat(value.getStringValue()).isEqualTo("0.50");
    }

}
