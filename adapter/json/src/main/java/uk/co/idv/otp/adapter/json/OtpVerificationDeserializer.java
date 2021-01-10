package uk.co.idv.otp.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;
import java.util.UUID;

public class OtpVerificationDeserializer extends StdDeserializer<OtpVerification> {

    protected OtpVerificationDeserializer() {
        super(OtpVerification.class);
    }

    @Override
    public OtpVerification deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return OtpVerification.builder()
                .id(extractUuid(node.get("id")))
                .contextId(extractUuid(node.get("contextId")))
                .created(extractInstant(node.get("created")))
                .expiry(extractInstant(node.get("expiry")))
                .activity(JsonNodeConverter.toObject(node.get("activity"), parser, Activity.class))
                .deliveryMethod(JsonNodeConverter.toObject(node.get("deliveryMethod"), parser, DeliveryMethod.class))
                .config(JsonNodeConverter.toObject(node.get("config"), parser, OtpConfig.class))
                .deliveries(JsonNodeConverter.toObject(node.get("deliveries"), parser, Deliveries.class))
                .protectSensitiveData(node.get("protectSensitiveData").asBoolean())
                .successful(node.get("successful").asBoolean())
                .complete(node.get("complete").asBoolean())
                .build();
    }

    private static UUID extractUuid(JsonNode node) {
        return UUID.fromString(node.asText());
    }

    private static Instant extractInstant(JsonNode node) {
        return Instant.parse(node.asText());
    }

}
