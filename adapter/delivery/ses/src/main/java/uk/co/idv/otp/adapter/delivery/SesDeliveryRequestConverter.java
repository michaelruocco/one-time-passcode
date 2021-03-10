package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
//TODO unit test
public class SesDeliveryRequestConverter {

    private static final String TITLE = "IDV Demo - OTP";

    private static final String HTML_TEMPLATE = "<p>%s</p>";
    private static final Content SUBJECT = buildSubject();

    private final String sourceEmailAddress;

    public SendEmailRequest toSendEmailRequest(DeliveryRequest request) {
        return new SendEmailRequest()
                .withDestination(toDestination(request.getMethod()))
                .withMessage(toMessage(request.getMessageText()))
                .withSource(sourceEmailAddress);
    }

    private static Destination toDestination(DeliveryMethod method) {
        return new Destination().withToAddresses(method.getValue());
    }

    private static Message toMessage(String messageText) {
        return new Message()
                .withBody(toBody(messageText))
                .withSubject(SUBJECT);
    }

    private static Body toBody(String messageText) {
        return new Body()
                .withHtml(buildUtf8Content().withData(toHtml(messageText)))
                .withText(buildUtf8Content().withData(messageText));
    }

    private static String toHtml(String messageText) {
        return String.format(HTML_TEMPLATE, messageText);
    }

    private static Content buildSubject() {
        return buildUtf8Content().withData(TITLE);
    }

    private static Content buildUtf8Content() {
        return new Content().withCharset(UTF_8.name());
    }

}
