package uk.co.idv.otp.adapter.json.send.message;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class MessageDeserializer extends StdDeserializer<Message> {

    public MessageDeserializer() {
        super(Message.class);
    }

    @Override
    public Message deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Message.builder()
                .passcode(JsonNodeConverter.toObject(node.get("passcode"), parser, Passcode.class))
                .text(node.get("text").asText())
                .build();
    }

}
