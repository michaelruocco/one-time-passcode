package uk.co.idv.otp.adapter.verification.updater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import uk.co.idv.context.adapter.verification.client.header.IdvRequestHeaders;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.OtpVerificationMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ClientCompleteVerificationRequestFactoryTest {

    private static final UUID CORRELATION_ID = UUID.randomUUID();
    private static final String CHANNEL_ID = "abc";

    private final ClientCompleteVerificationRequestFactory factory = new ClientCompleteVerificationRequestFactory();

    @BeforeEach
    public void setupMdc() {
        MDC.put("correlation-id", CORRELATION_ID.toString());
        MDC.put("channel-id", CHANNEL_ID);
    }

    @Test
    void shouldPopulateContextIdOnBody() {
        OtpVerification verification = OtpVerificationMother.incomplete();

        ClientCompleteVerificationRequest request = factory.build(verification);

        CompleteVerificationRequest body = request.getBody();
        assertThat(body.getContextId()).isEqualTo(verification.getContextId());
    }

    @Test
    void shouldPopulateIdOnBody() {
        OtpVerification verification = OtpVerificationMother.incomplete();

        ClientCompleteVerificationRequest request = factory.build(verification);

        CompleteVerificationRequest body = request.getBody();
        assertThat(body.getId()).isEqualTo(verification.getId());
    }

    @Test
    void shouldPopulateSuccessfulOnBody() {
        OtpVerification verification = OtpVerificationMother.incomplete();

        ClientCompleteVerificationRequest request = factory.build(verification);

        CompleteVerificationRequest body = request.getBody();
        assertThat(body.isSuccessful()).isEqualTo(verification.isSuccessful());
    }

    @Test
    void shouldPopulateCorrelationIdOnHeaders() {
        OtpVerification verification = OtpVerificationMother.incomplete();

        ClientCompleteVerificationRequest request = factory.build(verification);

        IdvRequestHeaders headers = request.getHeaders();
        assertThat(headers.getCorrelationId()).isEqualTo(CORRELATION_ID);
    }

    @Test
    void shouldPopulateChannelIdOnHeaders() {
        OtpVerification verification = OtpVerificationMother.incomplete();

        ClientCompleteVerificationRequest request = factory.build(verification);

        IdvRequestHeaders headers = request.getHeaders();
        assertThat(headers.getChannelId()).isEqualTo(CHANNEL_ID);
    }

}
