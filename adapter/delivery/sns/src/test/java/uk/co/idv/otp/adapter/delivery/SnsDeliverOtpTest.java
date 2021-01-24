package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;
import uk.co.idv.otp.usecases.send.DeliverOtp;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SnsDeliverOtpTest {

    private static final Instant NOW = Instant.now();

    private final DeliveryRequestConverter converter = mock(DeliveryRequestConverter.class);
    private final AmazonSNS client = mock(AmazonSNS.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final DeliverOtp deliverOtp = SnsDeliverOtp.builder()
            .converter(converter)
            .client(client)
            .clock(clock)
            .build();

    @Test
    void shouldPopulateDeliveryMethodOnDelivery() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        givenDeliveryPublished(deliveryRequest);

        Delivery delivery = deliverOtp.deliver(deliveryRequest);

        assertThat(delivery.getMethod()).isEqualTo(deliveryRequest.getMethod());
    }

    @Test
    void shouldPopulateMessageOnDelivery() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        givenDeliveryPublished(deliveryRequest);

        Delivery delivery = deliverOtp.deliver(deliveryRequest);

        assertThat(delivery.getMessage()).isEqualTo(deliveryRequest.getMessage());
    }

    @Test
    void shouldPopulateMessageIdOnDelivery() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        String expectedMessageId = givenDeliveryPublished(deliveryRequest);

        Delivery delivery = deliverOtp.deliver(deliveryRequest);

        assertThat(delivery.getMessageId()).isEqualTo(expectedMessageId);
    }

    @Test
    void shouldPopulateSentTimeOnDelivery() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        givenDeliveryPublished(deliveryRequest);

        Delivery delivery = deliverOtp.deliver(deliveryRequest);

        assertThat(delivery.getSent()).isEqualTo(NOW);
    }

    private String givenDeliveryPublished(DeliveryRequest deliveryRequest) {
        PublishRequest publishRequest = givenConvertsToPublishRequest(deliveryRequest);
        String messageId = "message-id";
        PublishResult publishResult = givenPublishResultWithMessageId(messageId);
        given(client.publish(publishRequest)).willReturn(publishResult);
        return messageId;
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
