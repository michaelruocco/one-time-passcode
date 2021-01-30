package uk.co.idv.otp.app.spring.filters.validation;

import org.springframework.web.servlet.HandlerExceptionResolver;
import uk.co.mruoc.spring.filter.validation.HeaderValidationFilter;

public class ContextHeaderValidationFilter extends HeaderValidationFilter {

    public ContextHeaderValidationFilter(HandlerExceptionResolver resolver) {
        super(new ContextHeaderValidator(), resolver);
    }

}
