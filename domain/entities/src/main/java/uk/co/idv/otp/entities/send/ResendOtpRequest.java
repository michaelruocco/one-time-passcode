package uk.co.idv.otp.entities.send;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class ResendOtpRequest {

    private final UUID verificationId;

}
