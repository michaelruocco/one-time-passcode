package uk.co.idv.otp.app.spring.filters.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.otp.adapter.json.protect.OtpVerificationJsonMasker;
import uk.co.mruoc.spring.filter.logging.response.ResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.TransformingResponseBodyExtractor;

public class OtpVerificationResponseLoggingFilter extends ResponseLoggingFilter {

    public OtpVerificationResponseLoggingFilter(ObjectMapper mapper) {
        super(buildResponseBodyExtractor(mapper));
    }

    private static ResponseBodyExtractor buildResponseBodyExtractor(ObjectMapper mapper) {
        return new TransformingResponseBodyExtractor(new OtpVerificationJsonMasker(mapper));
    }

}
