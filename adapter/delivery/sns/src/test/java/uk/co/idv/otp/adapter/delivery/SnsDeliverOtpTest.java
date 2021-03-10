package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SnsDeliverOtpTest {

    private final SnsDeliveryRequestConverter converter = mock(SnsDeliveryRequestConverter.class);
    private final AmazonSNS client = mock(AmazonSNS.class);

    private final DeliverOtpByMethod deliverOtp = SnsDeliverOtp.builder()
            .converter(converter)
            .client(client)
            .build();

    @Test
    void shouldReturnDeliveryMethodName() {
        assertThat(deliverOtp.getDeliveryMethodName()).isEqualTo("sms");
    }

    @Test
    void shouldReturnMessageId() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        PublishResult result = givenDeliveryPublished(deliveryRequest);

        String messageId = deliverOtp.deliver(deliveryRequest);

        assertThat(messageId).isEqualTo(result.getMessageId());
    }

    private PublishResult givenDeliveryPublished(DeliveryRequest deliveryRequest) {
        PublishRequest publishRequest = givenConvertsToPublishRequest(deliveryRequest);
        String messageId = "message-id";
        PublishResult publishResult = givenPublishResultWithMessageId(messageId);
        given(client.publish(publishRequest)).willReturn(publishResult);
        return publishResult;
    }

    private PublishRequest givenConvertsToPublishRequest(DeliveryRequest deliveryRequest) {
        PublishRequest publishRequest = mock(PublishRequest.class);
        given(converter.toPublishRequest(deliveryRequest)).willReturn(publishRequest);
        return publishRequest;
    }

    private PublishResult givenPublishResultWithMessageId(String messageId) {
        PublishResult result = mock(PublishResult.class);
        given(result.getMessageId()).willReturn(messageId);
        return result;
    }

}
