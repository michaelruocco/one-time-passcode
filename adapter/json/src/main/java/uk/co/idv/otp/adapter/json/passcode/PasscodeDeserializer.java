package uk.co.idv.otp.adapter.json.passcode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;

public class PasscodeDeserializer extends StdDeserializer<Passcode> {

    public PasscodeDeserializer() {
        super(Passcode.class);
    }

    @Override
    public Passcode deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Passcode.builder()
                .value(node.get("value").asText())
                .created(Instant.parse(node.get("created").asText()))
                .expiry(Instant.parse(node.get("expiry").asText()))
                .build();
    }

}
