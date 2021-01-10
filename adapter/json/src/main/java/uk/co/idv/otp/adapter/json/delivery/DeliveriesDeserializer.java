package uk.co.idv.otp.adapter.json.delivery;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.otp.entities.delivery.Deliveries;
import uk.co.idv.otp.entities.delivery.Delivery;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class DeliveriesDeserializer extends StdDeserializer<Deliveries> {

    private static final TypeReference<Collection<Delivery>> DELIVERY_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public DeliveriesDeserializer() {
        super(Deliveries.class);
    }

    @Override
    public Deliveries deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Deliveries.builder()
                .max(node.get("max").asInt())
                .values(JsonNodeConverter.toCollection(node.get("values"), parser, DELIVERY_COLLECTION))
                .build();
    }

}
