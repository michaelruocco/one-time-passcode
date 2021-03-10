package uk.co.idv.otp.adapter.delivery.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class MessageAttributeBuilderTest {

    private static final String SENDER_ID = "sender-id";

    private final MessageAttributeBuilder builder = new MessageAttributeBuilder(SENDER_ID);

    @Test
    void shouldReturnSenderIdMessageAttribute() {
        Map<String, MessageAttributeValue> values = builder.build();

        assertThat(values).contains(entry(
                "AWS.SNS.SMS.SenderID",
                new IdvSenderIdMessageAttributeValue(SENDER_ID)
        ));
    }

    @Test
    void shouldReturnSenderMaxPriceAttribute() {
        Map<String, MessageAttributeValue> values = builder.build();

        assertThat(values).contains(entry(
                "AWS.SNS.SMS.MaxPrice",
                new IdvMaxPriceMessageAttributeValue()
        ));
    }

    @Test
    void shouldReturnSenderSmsTypeAttribute() {
        Map<String, MessageAttributeValue> values = builder.build();

        assertThat(values).contains(entry(
                "AWS.SNS.SMS.SMSType",
                new IdvSmsTypeMessageAttributeValue()
        ));
    }

}
