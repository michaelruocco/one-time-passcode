package uk.co.idv.otp.adapter.verificationloader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ClientCreateVerificationRequestFactoryTest {

    private static final UUID CORRELATION_ID = UUID.randomUUID();
    private static final String CHANNEL_ID = "abc";

    private final ClientCreateVerificationRequestFactory factory = new ClientCreateVerificationRequestFactory();

    @BeforeEach
    public void setupMdc() {
        MDC.put("correlation-id", CORRELATION_ID.toString());
        MDC.put("channel-id", CHANNEL_ID);
    }

    @Test
    void shouldPopulateContextIdOnBody() {
        UUID contextId = UUID.randomUUID();

        ClientCreateVerificationRequest request = factory.build(contextId);

        CreateVerificationRequest body = request.getBody();
        assertThat(body.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldPopulateMethodNameOnBody() {
        UUID contextId = UUID.randomUUID();

        ClientCreateVerificationRequest request = factory.build(contextId);

        CreateVerificationRequest body = request.getBody();
        assertThat(body.getMethodName()).isEqualTo("one-time-passcode");
    }

    @Test
    void shouldPopulateCorrelationIdOnHeaders() {
        UUID contextId = UUID.randomUUID();

        ClientCreateVerificationRequest request = factory.build(contextId);

        ContextRequestHeaders headers = request.getHeaders();
        assertThat(headers.getCorrelationId()).isEqualTo(CORRELATION_ID);
    }

    @Test
    void shouldPopulateChannelIdOnHeaders() {
        UUID contextId = UUID.randomUUID();

        ClientCreateVerificationRequest request = factory.build(contextId);

        ContextRequestHeaders headers = request.getHeaders();
        assertThat(headers.getChannelId()).isEqualTo(CHANNEL_ID);
    }

}
