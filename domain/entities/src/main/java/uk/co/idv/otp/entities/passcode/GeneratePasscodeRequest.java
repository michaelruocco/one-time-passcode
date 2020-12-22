package uk.co.idv.otp.entities.passcode;

import java.time.Duration;

public interface GeneratePasscodeRequest {

    int getPasscodeLength();

    Duration getPasscodeDuration();

}
