package uk.co.idv.otp.adapter.json.protect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.CompositeJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;
import uk.co.mruoc.json.mask.email.EmailAddressJsonMasker;
import uk.co.mruoc.json.mask.phone.PhoneNumberJsonMasker;

import java.util.Collection;

public class OtpVerificationJsonMasker extends CompositeJsonMasker {

    public OtpVerificationJsonMasker(ObjectMapper mapper) {
        super(
                new OtpVerificationEmailAddressJsonMasker(mapper),
                new OtpVerificationPhoneNumberJsonMasker(mapper)
        );
    }

    private static class OtpVerificationEmailAddressJsonMasker extends EmailAddressJsonMasker {

        public OtpVerificationEmailAddressJsonMasker(ObjectMapper mapper) {
            super(mapper, paths());
        }

        private static Collection<JsonPath> paths() {
            return JsonPathFactory.toJsonPaths(
                    "$.deliveryMethod[?(@.type=='email')].value",
                    "$.deliveries.values[?(@.method.type=='email')].method.value"
            );
        }

    }

    private static class OtpVerificationPhoneNumberJsonMasker extends PhoneNumberJsonMasker {

        public OtpVerificationPhoneNumberJsonMasker(ObjectMapper mapper) {
            super(mapper, paths());
        }

        private static Collection<JsonPath> paths() {
            return JsonPathFactory.toJsonPaths(
                    "$.deliveryMethod[?(@.type=='sms')].value",
                    "$.deliveryMethod[?(@.type=='voice')].value",
                    "$.deliveries.values[?(@.method.type=='sms')].method.value",
                    "$.deliveries.values[?(@.method.type=='voice')].method.value"
            );
        }

    }

}
