package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SesDeliveryRequestConverterTest {

    private static final String SOURCE_EMAIL = "source@idv.com";

    private final SesDeliveryRequestConverter converter = new SesDeliveryRequestConverter(SOURCE_EMAIL);

    @Test
    void shouldPopulateSourceOnSendEmailRequest() {
        DeliveryRequest deliveryRequest = mock(DeliveryRequest.class);

        SendEmailRequest sendRequest = converter.toSendEmailRequest(deliveryRequest);

        assertThat(sendRequest.getSource()).isEqualTo(SOURCE_EMAIL);
    }

    @Test
    void shouldPopulateDestinationOnSendEmailRequest() {
        String destinationEmail = "destination@hotmail.com";
        DeliveryRequest deliveryRequest = deliveryRequestWithDestination(destinationEmail);

        SendEmailRequest sendRequest = converter.toSendEmailRequest(deliveryRequest);

        Destination destination = sendRequest.getDestination();
        assertThat(destination.getToAddresses()).containsExactly(destinationEmail);
    }

    @Test
    void shouldPopulateMessageSubjectOnSendEmailRequest() {
        DeliveryRequest deliveryRequest = mock(DeliveryRequest.class);

        SendEmailRequest sendRequest = converter.toSendEmailRequest(deliveryRequest);

        Message message = sendRequest.getMessage();
        assertThat(message.getSubject()).isEqualTo(utf8Content().withData("IDV Demo - OTP"));
    }

    @Test
    void shouldPopulateMessageBodyTextOnSendEmailRequest() {
        String messageText = "my message text";
        DeliveryRequest deliveryRequest = deliveryRequestWithMessageText(messageText);

        SendEmailRequest sendRequest = converter.toSendEmailRequest(deliveryRequest);

        Body body = sendRequest.getMessage().getBody();
        assertThat(body.getText()).isEqualTo(utf8Content().withData(messageText));
    }

    @Test
    void shouldPopulateMessageBodyHtmlTextOnSendEmailRequest() {
        String messageText = "my message text";
        DeliveryRequest deliveryRequest = deliveryRequestWithMessageText(messageText);

        SendEmailRequest sendRequest = converter.toSendEmailRequest(deliveryRequest);

        Body body = sendRequest.getMessage().getBody();
        assertThat(body.getHtml()).isEqualTo(utf8Content().withData("<p>" + messageText + "</p>"));
    }

    private DeliveryRequest deliveryRequestWithDestination(String destinationEmail) {
        DeliveryRequest request = mock(DeliveryRequest.class);
        given(request.getDeliveryMethodValue()).willReturn(destinationEmail);
        return request;
    }

    private DeliveryRequest deliveryRequestWithMessageText(String messageText) {
        DeliveryRequest request = mock(DeliveryRequest.class);
        given(request.getMessageText()).willReturn(messageText);
        return request;
    }

    private static Content utf8Content() {
        return new Content().withCharset(UTF_8.name());
    }

}
