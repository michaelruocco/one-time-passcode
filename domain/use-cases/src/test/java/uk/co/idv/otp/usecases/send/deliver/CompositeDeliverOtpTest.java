package uk.co.idv.otp.usecases.send.deliver;

import org.junit.jupiter.api.Test;
import uk.co.idv.otp.entities.delivery.DefaultDeliveryRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryMother;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.delivery.DeliveryRequestMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeDeliverOtpTest {

    private final DeliveryFactory factory = mock(DeliveryFactory.class);
    private final DeliverOtpByMethod deliverByMethod = mock(DeliverOtpByMethod.class);
    private final DefaultDeliveryRequest request = DeliveryRequestMother.build();


    @Test
    void shouldReturnDeliveryWithMessageIdFromSupportedDeliverOtp() {
        givenDeliverByMethodTypeSupported(request.getDeliveryMethodType());
        String messageId = givenMessageIdReturnedFor(deliverByMethod, request);
        Delivery expectedDelivery = givenConvertedToDelivery(messageId);
        DeliverOtp composite = new CompositeDeliverOtp(factory, deliverByMethod);

        Delivery delivery = composite.deliver(request);

        assertThat(delivery).isEqualTo(expectedDelivery);
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodTypeNotSupported() {
        givenDeliverByMethodTypeSupported("other-type");
        DeliverOtp composite = new CompositeDeliverOtp(factory, deliverByMethod);

        Throwable error = catchThrowable(() -> composite.deliver(request));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotSupportedException.class)
                .hasMessage(request.getDeliveryMethodType());
    }

    private void givenDeliverByMethodTypeSupported(String methodType) {
        given(deliverByMethod.getDeliveryMethodName()).willReturn(methodType);
    }

    private static String givenMessageIdReturnedFor(DeliverOtpByMethod deliverOtp, DeliveryRequest request) {
        String messageId = "message-id";
        given(deliverOtp.deliver(request)).willReturn(messageId);
        return messageId;
    }

    private Delivery givenConvertedToDelivery(String messageId) {
        Delivery.DeliveryBuilder builder = mock(Delivery.DeliveryBuilder.class);
        given(factory.toDelivery(request)).willReturn(builder);
        given(builder.messageId(messageId)).willReturn(builder);
        Delivery delivery = DeliveryMother.build();
        given(builder.build()).willReturn(delivery);
        return delivery;
    }

}
