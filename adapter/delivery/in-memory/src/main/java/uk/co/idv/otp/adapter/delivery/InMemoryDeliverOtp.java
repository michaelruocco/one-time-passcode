package uk.co.idv.otp.adapter.delivery;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;

@Slf4j
@Builder
public class InMemoryDeliverOtp implements DeliverOtp {

    private final UuidGenerator uuidGenerator;
    private final Clock clock;

    @Getter
    private Delivery lastDelivery;

    @Override
    public Delivery deliver(DeliveryRequest request) {
        Delivery delivery = toDelivery(request);
        log.info("in memory delivery {}", delivery);
        lastDelivery = delivery;
        return delivery;
    }

    private Delivery toDelivery(DeliveryRequest request) {
        return Delivery.builder()
                .method(request.getMethod())
                .message(request.getMessage())
                .messageId(uuidGenerator.generate().toString())
                .sent(clock.instant())
                .build();
    }

}
