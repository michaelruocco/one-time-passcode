package uk.co.idv.otp.adapter.json.send;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

public class ResendOtpRequestDeserializer extends StdDeserializer<ResendOtpRequest> {

    public ResendOtpRequestDeserializer() {
        super(ResendOtpRequest.class);
    }

    @Override
    public ResendOtpRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new ResendOtpRequest(UUID.fromString(node.get("id").asText()));
    }

}
