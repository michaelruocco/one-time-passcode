package uk.co.idv.otp.app.spring.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

public class SystemPropertyInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("systemProperties", System.getProperties());
    }

}
