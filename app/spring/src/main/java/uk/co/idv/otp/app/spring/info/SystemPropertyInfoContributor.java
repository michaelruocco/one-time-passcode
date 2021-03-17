package uk.co.idv.otp.app.spring.info;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

import java.util.Properties;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class SystemPropertyInfoContributor implements InfoContributor {

    private final UnaryOperator<Properties> secureProperties;

    @Override
    public void contribute(Info.Builder builder) {
        Properties secured = secureProperties.apply(System.getProperties());
        builder.withDetail("systemProperties", secured);
    }

}
