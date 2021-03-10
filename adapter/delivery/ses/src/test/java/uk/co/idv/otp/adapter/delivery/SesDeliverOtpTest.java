package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
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

class SesDeliverOtpTest {

    private final SesDeliveryRequestConverter requestConverter = mock(SesDeliveryRequestConverter.class);
    private final AmazonSimpleEmailService client = mock(AmazonSimpleEmailService.class);
    private final DeliveryFactory deliveryFactory = mock(DeliveryFactory.class);

    private final DeliverOtpByMethod deliverOtp = SesDeliverOtp.builder()
            .requestConverter(requestConverter)
            .client(client)
            .deliveryFactory(deliveryFactory)
            .build();

    @Test
    void shouldReturnDeliveryMethodName() {
        assertThat(deliverOtp.getDeliveryMethodName()).isEqualTo("email");
    }

    @Test
    void shouldPopulateDeliveryMethodOnDelivery() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        SendEmailResult result = givenEmailSent(deliveryRequest);
        Delivery.DeliveryBuilder builder = givenConvertsToDeliveryBuilder(deliveryRequest, result);
        Delivery expectedDelivery = givenBuildsDelivery(builder);

        Delivery delivery = deliverOtp.deliver(deliveryRequest);

        assertThat(delivery).isEqualTo(expectedDelivery);
    }

    @Test
    void shouldPopulateMessageIdOnDelivery() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        SendEmailResult result = givenEmailSent(deliveryRequest);
        Delivery.DeliveryBuilder builder = givenConvertsToDeliveryBuilder(deliveryRequest, result);
        Delivery expectedDelivery = givenBuildsDelivery(builder);

        Delivery delivery = deliverOtp.deliver(deliveryRequest);

        verify(builder).messageId(result.getMessageId());
        assertThat(delivery).isEqualTo(expectedDelivery);
    }

    private SendEmailResult givenEmailSent(DeliveryRequest deliveryRequest) {
        SendEmailRequest sendEmailRequest = givenConvertsToSendEmailRequest(deliveryRequest);
        String messageId = "message-id";
        SendEmailResult sendEmailResult = givenSendEmailResultWithMessageId(messageId);
        given(client.sendEmail(sendEmailRequest)).willReturn(sendEmailResult);
        return sendEmailResult;
    }

    private SendEmailRequest givenConvertsToSendEmailRequest(DeliveryRequest deliveryRequest) {
        SendEmailRequest sendEmailRequest = mock(SendEmailRequest.class);
        given(requestConverter.toSendEmailRequest(deliveryRequest)).willReturn(sendEmailRequest);
        return sendEmailRequest;
    }

    private SendEmailResult givenSendEmailResultWithMessageId(String messageId) {
        SendEmailResult result = mock(SendEmailResult.class);
        given(result.getMessageId()).willReturn(messageId);
        return result;
    }

    private Delivery.DeliveryBuilder givenConvertsToDeliveryBuilder(DeliveryRequest request, SendEmailResult result) {
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
