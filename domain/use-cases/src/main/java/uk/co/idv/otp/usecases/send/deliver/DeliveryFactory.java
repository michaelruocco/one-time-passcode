package uk.co.idv.otp.usecases.send.deliver;

import lombok.RequiredArgsConstructor;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;

import java.time.Clock;

@RequiredArgsConstructor
public class DeliveryFactory {

    private final Clock clock;

    public Delivery.DeliveryBuilder toDelivery(DeliveryRequest request) {
        return Delivery.builder()
                .method(request.getMethod())
                .message(request.getMessage())
                .sent(clock.instant());
    }

}
