package uk.co.idv.otp.usecases.send.deliver;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeDeliverOtpTest {

    private static final String METHOD_TYPE = "method-type";

    private final DeliverOtpByMethod deliverByMethod = deliverOtpByMethod();

    private final DeliverOtp composite = new CompositeDeliverOtp(deliverByMethod);

    @Test
    void shouldReturnDeliveryFromSupportedDeliverOtp() {
        DeliveryRequest request = deliveryRequestByMethod(METHOD_TYPE);
        Delivery expectedDelivery = givenDeliveryReturnedFor(deliverByMethod, request);

        Delivery delivery = composite.deliver(request);

        assertThat(delivery).isEqualTo(expectedDelivery);
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodTypeNotSupported() {
        String methodType = "unsupported-type";
        DeliveryRequest request = deliveryRequestByMethod(methodType);

        Throwable error = catchThrowable(() -> composite.deliver(request));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotSupportedException.class)
                .hasMessage(methodType);
    }

    private static DeliverOtpByMethod deliverOtpByMethod() {
        DeliverOtpByMethod deliverOtp = mock(DeliverOtpByMethod.class);
        given(deliverOtp.getDeliveryMethodName()).willReturn(METHOD_TYPE);
        return deliverOtp;
    }

    private static DeliveryRequest deliveryRequestByMethod(String methodType) {
        DeliveryRequest request = mock(DeliveryRequest.class);
        given(request.getDeliveryMethodType()).willReturn(methodType);
        return request;
    }

    private static Delivery givenDeliveryReturnedFor(DeliverOtpByMethod deliverOtp, DeliveryRequest request) {
        Delivery delivery = mock(Delivery.class);
        given(deliverOtp.deliver(request)).willReturn(delivery);
        return delivery;
    }

}
