package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;

@Builder
@Slf4j
public class SesDeliverOtp implements DeliverOtpByMethod {

    private final SesDeliveryRequestConverter requestConverter;
    private final AmazonSimpleEmailService client;

    @Override
    public String getDeliveryMethodName() {
        return "email";
    }

    @Override
    public String deliver(DeliveryRequest request) {
        var sendEmailRequest = requestConverter.toSendEmailRequest(request);
        log.debug("sending email request {}", sendEmailRequest);
        var result = client.sendEmail(sendEmailRequest);
        return result.getMessageId();
    }

}
