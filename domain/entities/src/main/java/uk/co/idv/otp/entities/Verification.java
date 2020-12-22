package uk.co.idv.otp.entities;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.passcode.GeneratePasscodeRequest;
import uk.co.idv.otp.entities.send.message.Message;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Builder(toBuilder = true)
@Data
public class Verification implements GeneratePasscodeRequest {

    private final UUID id;
    private final UUID contextId;
    private final Instant created;
    private final Instant expiry;
    private final Activity activity;
    private final OtpConfig config;
    private final Deliveries deliveries;
    private final boolean successful;
    private final boolean complete;

    public Verification add(Delivery delivery) {
        return toBuilder()
                .deliveries(deliveries.add(delivery))
                .build();
    }

    public Delivery getFirstDelivery() {
        return deliveries.first();
    }

    public Message getFirstMessage() {
        return getFirstDelivery().getMessage();
    }

    @Override
    public int getPasscodeLength() {
        return config.getPasscodeLength();
    }

    @Override
    public Duration getPasscodeDuration() {
        return config.getPasscodeDuration();
    }

}