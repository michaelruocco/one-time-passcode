package uk.co.idv.otp.adapter.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.send.message.Message;

import java.time.Duration;

public interface OtpVerificationMixin {

    @JsonIgnore
    int getPasscodeLength();

    @JsonIgnore
    Duration getPasscodeDuration();

    @JsonIgnore
    Delivery getFirstDelivery();

    @JsonIgnore
    Message getFirstMessage();

}
