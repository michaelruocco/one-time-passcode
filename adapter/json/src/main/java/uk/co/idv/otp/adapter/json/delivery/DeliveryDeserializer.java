package uk.co.idv.otp.adapter.json.delivery;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.idv.otp.entities.send.message.Message;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;

public class DeliveryDeserializer extends StdDeserializer<Delivery> {

    public DeliveryDeserializer() {
        super(Deliveries.class);
    }

    @Override
    public Delivery deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Delivery.builder()
                .method(JsonNodeConverter.toObject(node.get("method"), parser, DeliveryMethod.class))
                .message(JsonNodeConverter.toObject(node.get("message"), parser, Message.class))
                .messageId(node.get("messageId").asText())
                .sent(Instant.parse(node.get("sent").asText()))
                .build();
    }

}
