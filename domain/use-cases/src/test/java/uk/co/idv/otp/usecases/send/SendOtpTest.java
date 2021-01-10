package uk.co.idv.otp.usecases.send;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.delivery.DeliveryRequest;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.send.SendOtpRequestMother;
import uk.co.idv.otp.entities.send.message.GenerateMessageRequest;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.message.MessageGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SendOtpTest {

    private final OtpVerificationLoader verificationLoader = mock(OtpVerificationLoader.class);
    private final PasscodeGenerator passcodeGenerator = mock(PasscodeGenerator.class);
    private final MessageGenerator messageGenerator = mock(MessageGenerator.class);
    private final DeliverOtp deliverOtp = mock(DeliverOtp.class);
    private final OtpVerificationRepository repository = mock(OtpVerificationRepository.class);

    private final SendOtp sendOtp = SendOtp.builder()
            .verificationLoader(verificationLoader)
            .passcodeGenerator(passcodeGenerator)
            .messageGenerator(messageGenerator)
            .deliverOtp(deliverOtp)
            .repository(repository)
            .build();

    @Test
    void shouldReturnVerificationUpdatedWithOtpDelivery() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification originalVerification = givenOriginalVerification(request);
        Delivery delivery = givenOtpDelivered();
        OtpVerification updatedVerification = givenVerificationUpdatedWithDelivery(originalVerification, delivery);

        OtpVerification verification = sendOtp.send(request);

        assertThat(verification).isEqualTo(updatedVerification);
    }

    @Test
    void shouldSaveVerificationUpdatedWithOtpDelivery() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification originalVerification = givenOriginalVerification(request);
        Delivery delivery = givenOtpDelivered();
        OtpVerification updatedVerification = givenVerificationUpdatedWithDelivery(originalVerification, delivery);

        sendOtp.send(request);

        verify(repository).save(updatedVerification);
    }

    @Test
    void shouldPassPasscodeWhenGeneratingMessage() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification originalVerification = givenOriginalVerification(request);
        Passcode passcode = givenPasscodeGenerated(originalVerification);

        sendOtp.send(request);

        ArgumentCaptor<GenerateMessageRequest> captor = ArgumentCaptor.forClass(GenerateMessageRequest.class);
        verify(messageGenerator).apply(captor.capture());
        GenerateMessageRequest generateMessageRequest = captor.getValue();
        assertThat(generateMessageRequest.getPasscode()).isEqualTo(passcode);
    }

    @Test
    void shouldPassActivityWhenGeneratingMessage() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification originalVerification = OtpVerificationMother.incomplete();
        given(verificationLoader.load(request)).willReturn(originalVerification);

        sendOtp.send(request);

        ArgumentCaptor<GenerateMessageRequest> captor = ArgumentCaptor.forClass(GenerateMessageRequest.class);
        verify(messageGenerator).apply(captor.capture());
        GenerateMessageRequest generateMessageRequest = captor.getValue();
        assertThat(generateMessageRequest.getActivity()).isEqualTo(originalVerification.getActivity());
    }

    @Test
    void shouldPassDeliveryMethodWhenDeliveringMessage() {
        SendOtpRequest request = SendOtpRequestMother.build();
        OtpVerification originalVerification = OtpVerificationMother.incomplete();
        given(verificationLoader.load(request)).willReturn(originalVerification);

        sendOtp.send(request);

        ArgumentCaptor<DeliveryRequest> captor = ArgumentCaptor.forClass(DeliveryRequest.class);
        verify(deliverOtp).deliver(captor.capture());
        DeliveryRequest deliveryRequest = captor.getValue();
        assertThat(deliveryRequest.getMethod()).isEqualTo(originalVerification.getDeliveryMethod());
    }

    @Test
    void shouldPassMessageWhenDeliveringMessage() {
        SendOtpRequest request = SendOtpRequestMother.build();
        givenOriginalVerification(request);
        Message message = givenMessageGenerated();

        sendOtp.send(request);

        ArgumentCaptor<DeliveryRequest> captor = ArgumentCaptor.forClass(DeliveryRequest.class);
        verify(deliverOtp).deliver(captor.capture());
        DeliveryRequest deliveryRequest = captor.getValue();
        assertThat(deliveryRequest.getMessage()).isEqualTo(message);
    }

    private OtpVerification givenOriginalVerification(SendOtpRequest request) {
        OtpVerification originalVerification = mock(OtpVerification.class);
        given(verificationLoader.load(request)).willReturn(originalVerification);
        return originalVerification;
    }

    private Passcode givenPasscodeGenerated(OtpVerification verification) {
        Passcode passcode = mock(Passcode.class);
        given(passcodeGenerator.generate(verification)).willReturn(passcode);
        return passcode;
    }

    private Message givenMessageGenerated() {
        Message message = mock(Message.class);
        given(messageGenerator.apply(any(GenerateMessageRequest.class))).willReturn(message);
        return message;
    }

    private Delivery givenOtpDelivered() {
        Delivery delivery = mock(Delivery.class);
        given(deliverOtp.deliver(any(DeliveryRequest.class))).willReturn(delivery);
        return delivery;
    }

    private OtpVerification givenVerificationUpdatedWithDelivery(OtpVerification originalVerification, Delivery delivery) {
        OtpVerification updatedVerification = mock(OtpVerification.class);
        given(originalVerification.add(delivery)).willReturn(updatedVerification);
        return updatedVerification;
    }

}
