package uk.co.idv.otp.usecases.send;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;
import uk.co.idv.otp.entities.delivery.DefaultDeliveryRequest;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.PasscodeMother;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.send.ResendOtpRequestMother;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.idv.otp.usecases.OtpVerificationRepository;
import uk.co.idv.otp.usecases.get.GetOtp;
import uk.co.idv.otp.usecases.passcode.PasscodeGenerator;
import uk.co.idv.otp.usecases.send.deliver.DeliverOtp;
import uk.co.idv.otp.usecases.send.message.MessageGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ResendOtpTest {

    private final GetOtp getOtp = mock(GetOtp.class);
    private final PasscodeGenerator passcodeGenerator = mock(PasscodeGenerator.class);
    private final MessageGenerator messageGenerator = mock(MessageGenerator.class);
    private final DeliverOtp deliverOtp = mock(DeliverOtp.class);
    private final OtpVerificationRepository repository = mock(OtpVerificationRepository.class);

    private final ResendOtp resendOtp = ResendOtp.builder()
            .getOtp(getOtp)
            .passcodeGenerator(passcodeGenerator)
            .messageGenerator(messageGenerator)
            .deliverOtp(deliverOtp)
            .repository(repository)
            .build();

    @Test
    void shouldReturnVerificationUpdatedWithRedelivery() {
        ResendOtpRequest request = ResendOtpRequestMother.build();
        OtpVerification originalVerification = givenOriginalVerification(request);
        Delivery delivery = givenOtpDelivered();
        OtpVerification updatedVerification = givenVerificationUpdatedWithDelivery(originalVerification, delivery);
        Passcode passcode = givenPasscodeGenerated(originalVerification);
        givenUpdatedMessage(originalVerification, passcode);

        OtpVerification verification = resendOtp.resend(request);

        assertThat(verification).isEqualTo(updatedVerification);
    }

    @Test
    void shouldSaveVerificationUpdatedWithRedelivery() {
        ResendOtpRequest request = ResendOtpRequestMother.build();
        OtpVerification originalVerification = givenOriginalVerification(request);
        Delivery delivery = givenOtpDelivered();
        OtpVerification updatedVerification = givenVerificationUpdatedWithDelivery(originalVerification, delivery);
        Passcode passcode = givenPasscodeGenerated(originalVerification);
        givenUpdatedMessage(originalVerification, passcode);

        resendOtp.resend(request);

        verify(repository).save(updatedVerification);
    }

    @Test
    void shouldPassFirstMessageUpdatedWithGeneratedPasscodeWhenDelivering() {
        ResendOtpRequest request = ResendOtpRequestMother.build();
        OtpVerification originalVerification = givenOriginalVerification(request);
        Passcode passcode = givenPasscodeGenerated(originalVerification);
        Message message = givenUpdatedMessage(originalVerification, passcode);

        resendOtp.resend(request);

        ArgumentCaptor<DefaultDeliveryRequest> captor = ArgumentCaptor.forClass(DefaultDeliveryRequest.class);
        verify(deliverOtp).deliver(captor.capture());
        DefaultDeliveryRequest deliveryRequest = captor.getValue();
        assertThat(deliveryRequest.getMessage()).isEqualTo(message);
    }

    @Test
    void shouldPassDeliveryMethodWhenDelivering() {
        ResendOtpRequest request = ResendOtpRequestMother.build();
        OtpVerification verification = OtpVerificationMother.incomplete();
        given(getOtp.get(request.getId())).willReturn(verification);
        given(passcodeGenerator.generate(verification)).willReturn(PasscodeMother.build());

        resendOtp.resend(request);

        ArgumentCaptor<DefaultDeliveryRequest> captor = ArgumentCaptor.forClass(DefaultDeliveryRequest.class);
        verify(deliverOtp).deliver(captor.capture());
        DefaultDeliveryRequest deliveryRequest = captor.getValue();
        assertThat(deliveryRequest.getMethod()).isEqualTo(verification.getDeliveryMethod());
    }

    private OtpVerification givenOriginalVerification(ResendOtpRequest request) {
        OtpVerification originalVerification = mock(OtpVerification.class);
        given(getOtp.get(request.getId())).willReturn(originalVerification);
        return originalVerification;
    }

    private Delivery givenOtpDelivered() {
        Delivery delivery = mock(Delivery.class);
        given(deliverOtp.deliver(any(DefaultDeliveryRequest.class))).willReturn(delivery);
        return delivery;
    }

    private Passcode givenPasscodeGenerated(OtpVerification verification) {
        Passcode passcode = mock(Passcode.class);
        given(passcodeGenerator.generate(verification)).willReturn(passcode);
        return passcode;
    }

    private Message givenUpdatedMessage(OtpVerification verification, Passcode passcode) {
        Message originalMessage = mock(Message.class);
        given(verification.getFirstMessage()).willReturn(originalMessage);
        Message updatedMessage = mock(Message.class);
        given(originalMessage.update(passcode)).willReturn(updatedMessage);
        return updatedMessage;
    }

    private OtpVerification givenVerificationUpdatedWithDelivery(OtpVerification originalVerification, Delivery delivery) {
        OtpVerification updatedVerification = mock(OtpVerification.class);
        given(originalVerification.add(delivery)).willReturn(updatedVerification);
        return updatedVerification;
    }

}
