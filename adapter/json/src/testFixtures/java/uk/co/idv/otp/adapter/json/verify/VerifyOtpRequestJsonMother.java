package uk.co.idv.otp.adapter.json.verify;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VerifyOtpRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("verify/verify-otp-request.json");
    }

}
