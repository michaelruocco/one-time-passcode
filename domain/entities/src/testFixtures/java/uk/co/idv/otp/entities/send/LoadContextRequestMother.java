package uk.co.idv.otp.entities.send;

import java.util.UUID;

public interface LoadContextRequestMother {

    static LoadContextRequest build() {
        return builder().build();
    }

    static LoadContextRequest.LoadContextRequestBuilder builder() {
        return LoadContextRequest.builder()
                .contextId(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .deliveryMethodId(UUID.fromString("c9959188-969e-42f3-8178-42ef824c81d3"));
    }

}
