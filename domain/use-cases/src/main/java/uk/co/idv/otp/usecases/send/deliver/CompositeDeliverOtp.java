package uk.co.idv.otp.usecases.send.deliver;

import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompositeDeliverOtp implements DeliverOtp {

    private final Map<String, DeliverOtpByMethod> deliverOtps;
    private final DeliveryFactory deliveryFactory;

    public CompositeDeliverOtp(DeliveryFactory deliveryFactory, DeliverOtpByMethod... deliverOtps) {
        this(deliveryFactory, Arrays.asList(deliverOtps));
    }

    public CompositeDeliverOtp(DeliveryFactory deliveryFactory, Collection<DeliverOtpByMethod> deliverOtps) {
        this.deliveryFactory = deliveryFactory;
        this.deliverOtps = toMap(deliverOtps);
    }

    @Override
    public Delivery deliver(DeliveryRequest request) {
        String deliveryMethodType = request.getDeliveryMethodType();
        return findDeliverOtp(deliveryMethodType)
                .map(deliverOtp -> deliverOtp.deliver(request))
                .map(messageId -> toDelivery(request, messageId))
                .orElseThrow(() -> new DeliveryMethodNotSupportedException(deliveryMethodType));
    }

    private Optional<DeliverOtpByMethod> findDeliverOtp(String deliveryMethod) {
        return Optional.ofNullable(deliverOtps.get(deliveryMethod));
    }

    private Delivery toDelivery(DeliveryRequest request, String messageId) {
        return deliveryFactory.toDelivery(request)
                .messageId(messageId)
                .build();
    }

    private static Map<String, DeliverOtpByMethod> toMap(Collection<DeliverOtpByMethod> deliverOtps) {
        return deliverOtps.stream()
                .collect(Collectors.toMap(DeliverOtpByMethod::getDeliveryMethodName, Function.identity()));
    }

}
