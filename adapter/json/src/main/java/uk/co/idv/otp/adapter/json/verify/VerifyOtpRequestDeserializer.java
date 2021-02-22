package uk.co.idv.otp.adapter.json.verify;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.otp.entities.verify.VerifyOtpRequest;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;
import java.util.UUID;

public class VerifyOtpRequestDeserializer extends StdDeserializer<VerifyOtpRequest> {

    private static final TypeReference<Collection<String>> PASSCODE_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public VerifyOtpRequestDeserializer() {
        super(VerifyOtpRequest.class);
    }

    @Override
    public VerifyOtpRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return VerifyOtpRequest.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .passcodes(JsonNodeConverter.toCollection(node.get("passcodes"), parser, PASSCODE_COLLECTION))
                .build();
    }

}
