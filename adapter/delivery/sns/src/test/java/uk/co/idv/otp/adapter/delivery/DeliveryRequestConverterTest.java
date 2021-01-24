package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.adapter.delivery.attributes.MessageAttributeBuilder;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;
import uk.co.idv.otp.entities.send.message.Message;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryRequestConverterTest {

    private final MessageAttributeBuilder attributeBuilder = mock(MessageAttributeBuilder.class);

    private final DeliveryRequestConverter converter = new DeliveryRequestConverter(attributeBuilder);

    @Test
    void shouldPopulateMessageTextOnPublishRequest() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();

        PublishRequest request = converter.toPublishRequest(deliveryRequest);

        Message message = deliveryRequest.getMessage();
        assertThat(request.getMessage()).isEqualTo(message.getText());
    }

    @Test
    void shouldPopulatePhoneNumberOnPublishRequest() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();

        PublishRequest request = converter.toPublishRequest(deliveryRequest);

        DeliveryMethod method = deliveryRequest.getMethod();
        assertThat(request.getPhoneNumber()).isEqualTo(method.getValue());
    }

    @Test
    void shouldPopulateMessageAttributesOnPublishRequest() {
        Map<String, MessageAttributeValue> attributes = Collections.emptyMap();
        given(attributeBuilder.build()).willReturn(attributes);
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();

        PublishRequest request = converter.toPublishRequest(deliveryRequest);

        assertThat(request.getMessageAttributes()).isEqualTo(attributes);
    }

}
