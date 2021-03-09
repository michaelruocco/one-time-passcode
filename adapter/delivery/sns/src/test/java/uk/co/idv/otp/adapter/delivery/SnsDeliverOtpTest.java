package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;
import uk.co.idv.otp.usecases.send.deliver.DeliveryFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SnsDeliverOtpTest {

    private final SnsDeliveryRequestConverter converter = mock(SnsDeliveryRequestConverter.class);
    private final AmazonSNS client = mock(AmazonSNS.class);
    private final DeliveryFactory deliveryFactory = mock(DeliveryFactory.class);

    private final DeliverOtpByMethod deliverOtp = SnsDeliverOtp.builder()
            .converter(converter)
            .client(client)
            .deliveryFactory(deliveryFactory)
            .build();

    @Test
    void shouldReturnDeliveryMethodName() {
        assertThat(deliverOtp.getDeliveryMethodName()).isEqualTo("sms");
    }

    @Test
    void shouldPopulateDeliveryMethodOnDelivery() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        PublishResult result = givenDeliveryPublished(deliveryRequest);
        Delivery.DeliveryBuilder builder = givenConvertsToDeliveryBuilder(deliveryRequest, result);
        Delivery expectedDelivery = givenBuildsDelivery(builder);

        Delivery delivery = deliverOtp.deliver(deliveryRequest);

        assertThat(delivery).isEqualTo(expectedDelivery);
    }

    @Test
    void shouldPopulateMessageIdOnDelivery() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        PublishResult result = givenDeliveryPublished(deliveryRequest);
        Delivery.DeliveryBuilder builder = givenConvertsToDeliveryBuilder(deliveryRequest, result);
        Delivery expectedDelivery = givenBuildsDelivery(builder);

        Delivery delivery = deliverOtp.deliver(deliveryRequest);

        verify(builder).messageId(result.getMessageId());
        assertThat(delivery).isEqualTo(expectedDelivery);
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

    private Delivery.DeliveryBuilder givenConvertsToDeliveryBuilder(DeliveryRequest request, PublishResult result) {
        Delivery.DeliveryBuilder builder = mock(Delivery.DeliveryBuilder.class);
        given(deliveryFactory.toDelivery(request)).willReturn(builder);
        given(builder.messageId(result.getMessageId())).willReturn(builder);
        return builder;
    }

    private Delivery givenBuildsDelivery(Delivery.DeliveryBuilder builder) {
        Delivery delivery = mock(Delivery.class);
        given(builder.build()).willReturn(delivery);
        return delivery;
    }

}
