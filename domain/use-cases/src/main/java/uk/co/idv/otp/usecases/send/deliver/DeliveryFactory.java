package uk.co.idv.otp.usecases.send.deliver;

import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.entities.delivery.DefaultDeliveryRequest;
import uk.co.idv.otp.entities.delivery.Delivery;

import java.time.Clock;

@RequiredArgsConstructor
public class DeliveryFactory {

    private final Clock clock;

    public Delivery.DeliveryBuilder toDelivery(DefaultDeliveryRequest request) {
        return Delivery.builder()
                .method(request.getMethod())
                .message(request.getMessage())
                .sent(clock.instant());
    }

}
