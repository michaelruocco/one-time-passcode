package uk.co.idv.otp.app.spring.config.properties;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Properties;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class ReplaceProperties implements UnaryOperator<Properties> {

    private final Collection<String> propertyNames;
    private final String replacement;

    @Override
    public Properties apply(Properties properties) {
        Properties secured = new Properties();
        secured.putAll(properties);
        propertyNames.stream()
                .filter(secured::containsKey)
                .forEach(propertyName -> secured.setProperty(propertyName, replacement));
        return secured;
    }

}
