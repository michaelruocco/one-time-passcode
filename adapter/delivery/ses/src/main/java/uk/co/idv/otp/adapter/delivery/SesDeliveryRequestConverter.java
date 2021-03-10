package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public class SesDeliveryRequestConverter {

    private static final String TITLE = "IDV Demo - OTP";

    private static final String HTML_TEMPLATE = "<p>%s</p>";
    private static final Content SUBJECT = buildSubject();

    private final String sourceEmail;

    public SendEmailRequest toSendEmailRequest(DeliveryRequest request) {
        return new SendEmailRequest()
                .withDestination(toDestination(request.getDeliveryMethodValue()))
                .withMessage(toMessage(request.getMessageText()))
                .withSource(sourceEmail);
    }

    private static Destination toDestination(String address) {
        return new Destination().withToAddresses(address);
    }

    private static Message toMessage(String messageText) {
        return new Message()
                .withBody(toBody(messageText))
                .withSubject(SUBJECT);
    }

    private static Body toBody(String messageText) {
        return new Body()
                .withHtml(utf8Content().withData(toHtml(messageText)))
                .withText(utf8Content().withData(messageText));
    }

    private static String toHtml(String messageText) {
        return String.format(HTML_TEMPLATE, messageText);
    }

    private static Content buildSubject() {
        return utf8Content().withData(TITLE);
    }

    private static Content utf8Content() {
        return new Content().withCharset(UTF_8.name());
    }

}
