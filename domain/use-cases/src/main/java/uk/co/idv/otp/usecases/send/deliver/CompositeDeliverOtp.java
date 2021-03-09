package uk.co.idv.otp.usecases.send.deliver;

import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO unit test
public class CompositeDeliverOtp implements DeliverOtp {

    private final Map<String, DeliverOtp> deliverOtps;

    public CompositeDeliverOtp(DeliverOtpByMethod... deliverOtps) {
        this(Arrays.asList(deliverOtps));
    }

    public CompositeDeliverOtp(Collection<DeliverOtpByMethod> deliverOtps) {
        this.deliverOtps = toMap(deliverOtps);
    }

    @Override
    public Delivery deliver(DeliveryRequest request) {
        String deliveryMethodType = request.getDeliveryMethodType();
        return findDeliverOtp(deliveryMethodType)
                .map(deliverOtp -> deliverOtp.deliver(request))
                .orElseThrow(() -> new DeliveryMethodNotSupportedException(deliveryMethodType));
    }

    private Optional<DeliverOtp> findDeliverOtp(String deliveryMethod) {
        return Optional.ofNullable(deliverOtps.get(deliveryMethod));
    }

    private static Map<String, DeliverOtp> toMap(Collection<DeliverOtpByMethod> deliverOtps) {
        return deliverOtps.stream()
                .collect(Collectors.toMap(DeliverOtpByMethod::getDeliveryMethodName, Function.identity()));
    }

}
