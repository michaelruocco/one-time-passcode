package uk.co.idv.otp.usecases.send.deliver;

import uk.co.idv.otp.entities.delivery.DeliveryRequest;

public interface DeliverOtpByMethod {

    String getDeliveryMethodName();

    String deliver(DeliveryRequest request);

}
