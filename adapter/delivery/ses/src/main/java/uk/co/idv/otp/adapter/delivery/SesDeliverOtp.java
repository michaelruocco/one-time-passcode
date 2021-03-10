package uk.co.idv.otp.adapter.delivery;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtpByMethod;
import uk.co.idv.otp.usecases.send.deliver.DeliveryFactory;

@Builder
@Slf4j
public class SesDeliverOtp implements DeliverOtpByMethod {

    private final SesDeliveryRequestConverter requestConverter;
    private final AmazonSimpleEmailService client;
    private final DeliveryFactory deliveryFactory;

    @Override
    public String getDeliveryMethodName() {
        return "email";
    }

    @Override
    public Delivery deliver(DeliveryRequest request) {
        SendEmailRequest sendEmailRequest = requestConverter.toSendEmailRequest(request);
        log.debug("sending email request {}", sendEmailRequest);
        SendEmailResult result = client.sendEmail(sendEmailRequest);
        return deliveryFactory.toDelivery(request)
                .messageId(result.getMessageId())
                .build();
    }

}
