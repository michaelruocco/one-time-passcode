package uk.co.idv.otp.app.spring.filters.validation;

import uk.co.mruoc.spring.filter.validation.validator.MandatoryHeaderValidator;

public class ChannelIdHeaderValidator extends MandatoryHeaderValidator {

    public ChannelIdHeaderValidator() {
        super("channel-id");
    }

}
