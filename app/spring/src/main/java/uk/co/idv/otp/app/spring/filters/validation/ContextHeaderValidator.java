package uk.co.idv.otp.app.spring.filters.validation;

import uk.co.mruoc.spring.filter.validation.validator.CompositeHeaderValidator;
import uk.co.mruoc.spring.filter.validation.validator.CorrelationIdHeaderValidator;

public class ContextHeaderValidator extends CompositeHeaderValidator {

    public ContextHeaderValidator() {
        super(
                new CorrelationIdHeaderValidator(),
                new ChannelIdHeaderValidator()
        );
    }

}
