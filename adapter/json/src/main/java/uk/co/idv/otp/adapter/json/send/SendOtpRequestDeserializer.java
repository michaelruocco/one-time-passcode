package uk.co.idv.otp.adapter.json.send;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

public class SendOtpRequestDeserializer extends StdDeserializer<SendOtpRequest> {

    public SendOtpRequestDeserializer() {
        super(SendOtpRequest.class);
    }

    @Override
    public SendOtpRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return SendOtpRequest.builder()
                .contextId(UUID.fromString(node.get("contextId").asText()))
                .deliveryMethodId(UUID.fromString(node.get("deliveryMethodId").asText()))
                .build();
    }

}
