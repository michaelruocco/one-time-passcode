package uk.co.idv.otp.app.spring.filters.validation;

import org.springframework.web.servlet.HandlerExceptionResolver;
import uk.co.mruoc.spring.filter.validation.HeaderValidationFilter;

public class DefaultHeaderValidationFilter extends HeaderValidationFilter {

    public DefaultHeaderValidationFilter(HandlerExceptionResolver resolver) {
        super(new DefaultHeaderValidator(), resolver);
    }

}
