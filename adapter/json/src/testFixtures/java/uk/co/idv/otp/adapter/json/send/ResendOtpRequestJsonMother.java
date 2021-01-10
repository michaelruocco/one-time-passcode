package uk.co.idv.otp.adapter.json.send;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ResendOtpRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("request/resend-otp-request.json");
    }

}
