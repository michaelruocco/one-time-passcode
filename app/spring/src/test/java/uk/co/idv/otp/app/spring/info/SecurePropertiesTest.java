package uk.co.idv.otp.app.spring.info;

import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class SecurePropertiesTest {

    private static final String STANDARD_PROPERTY = "standard.property";
    private static final String STANDARD_PROPERTY_VALUE = "standard-property-value";

    private static final String MONGO_URI_PROPERTY = "spring.data.mongodb.uri";
    private static final String HIDDEN = "hidden";

    private final UnaryOperator<Properties> replaceProperties = new SecureProperties();

    @Test
    void shouldReturnCopyPropertiesIfReplacementPropertyNameNotFound() {
        Properties properties = new Properties();
        properties.put(STANDARD_PROPERTY, STANDARD_PROPERTY_VALUE);

        Properties replaced = replaceProperties.apply(properties);

        assertThat(replaced).containsExactlyEntriesOf(properties);
    }

    @Test
    void shouldReturnCopyPropertiesWithReplacementPropertyReplacedIfFound() {
        Properties properties = new Properties();
        properties.put(STANDARD_PROPERTY, STANDARD_PROPERTY_VALUE);
        properties.put(MONGO_URI_PROPERTY, "mongodb://mongo:27017/db");

        Properties replaced = replaceProperties.apply(properties);

        assertThat(replaced).hasSize(2)
                .containsEntry(STANDARD_PROPERTY, STANDARD_PROPERTY_VALUE)
                .containsEntry(MONGO_URI_PROPERTY, HIDDEN);
    }

}
