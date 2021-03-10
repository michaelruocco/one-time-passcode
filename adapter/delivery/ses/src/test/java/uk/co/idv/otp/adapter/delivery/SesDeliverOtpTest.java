package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SesDeliverOtpTest {

    private final SesDeliveryRequestConverter requestConverter = mock(SesDeliveryRequestConverter.class);
    private final AmazonSimpleEmailService client = mock(AmazonSimpleEmailService.class);

    private final DeliverOtpByMethod deliverOtp = SesDeliverOtp.builder()
            .requestConverter(requestConverter)
            .client(client)
            .build();

    @Test
    void shouldReturnDeliveryMethodName() {
        assertThat(deliverOtp.getDeliveryMethodName()).isEqualTo("email");
    }

    @Test
    void shouldReturnMessageId() {
        DeliveryRequest deliveryRequest = DeliveryRequestMother.build();
        SendEmailResult result = givenEmailSent(deliveryRequest);

        String messageId = deliverOtp.deliver(deliveryRequest);

        assertThat(messageId).isEqualTo(result.getMessageId());
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

}
