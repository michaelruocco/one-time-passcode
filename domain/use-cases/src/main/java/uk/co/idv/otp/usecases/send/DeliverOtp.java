package uk.co.idv.otp.usecases.send;

import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.delivery.Delivery;

public interface DeliverOtp {

    Delivery deliver(DeliveryRequest request);

}
