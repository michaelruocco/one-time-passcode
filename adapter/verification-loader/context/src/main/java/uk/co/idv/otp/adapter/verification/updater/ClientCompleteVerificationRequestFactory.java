package uk.co.idv.otp.adapter.verification.updater;

import org.slf4j.MDC;
import uk.co.idv.context.adapter.verification.client.header.ContextRequestHeaders;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.otp.entities.OtpVerification;

public class ClientCompleteVerificationRequestFactory {

    public ClientCompleteVerificationRequest build(OtpVerification verification) {
        return toClientRequest(toCompleteRequest(verification));
    }

    private CompleteVerificationRequest toCompleteRequest(OtpVerification verification) {
        return CompleteVerificationRequest.builder()
                .contextId(verification.getContextId())
                .id(verification.getId())
                .successful(verification.isSuccessful())
                .build();
    }

    private ClientCompleteVerificationRequest toClientRequest(CompleteVerificationRequest request) {
        return ClientCompleteVerificationRequest.builder()
                .body(request)
                .headers(ContextRequestHeaders.build(MDC.getCopyOfContextMap()))
                .build();
    }

}
