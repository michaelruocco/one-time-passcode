package uk.co.idv.otp.adapter.delivery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.usecases.send.DeliverOtp;

import java.time.Clock;

@Slf4j
@RequiredArgsConstructor
public class InMemoryDeliverOtp implements DeliverOtp {

    private final IdGenerator idGenerator;
    private final Clock clock;

    @Override
    public Delivery deliver(DeliveryRequest request) {
        Delivery delivery = toDelivery(request);
        log.info("in memory delivery {}", delivery);
        return delivery;
    }

    private Delivery toDelivery(DeliveryRequest request) {
        return Delivery.builder()
                .method(request.getMethod())
                .message(request.getMessage())
                .messageId(idGenerator.generate().toString())
                .sent(clock.instant())
                .build();
    }

}
