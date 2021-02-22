package uk.co.idv.otp.entities.verify;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Builder
@Data
public class VerifyOtpRequest {

    private final UUID id;
    private final Collection<String> passcodes;

}
