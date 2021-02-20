package uk.co.idv.otp.adapter.verificationloader;

import org.slf4j.MDC;
import uk.co.idv.context.adapter.verification.client.header.ContextRequestHeaders;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;

import java.util.UUID;

public class ClientCreateVerificationRequestFactory {

    public ClientCreateVerificationRequest build(UUID contextId) {
        return toClientRequest(toCreateRequest(contextId));
    }

    private CreateVerificationRequest toCreateRequest(UUID contextId) {
        return CreateVerificationRequest.builder()
                .methodName("one-time-passcode")
                .contextId(contextId)
                .build();
    }

    private ClientCreateVerificationRequest toClientRequest(CreateVerificationRequest request) {
        return ClientCreateVerificationRequest.builder()
                .body(request)
                .headers(ContextRequestHeaders.build(MDC.getCopyOfContextMap()))
                .build();
    }

}
