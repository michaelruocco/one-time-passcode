package uk.co.idv.otp.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OtpVerificationJsonMother {

    static String incomplete() {
        return loadContentFromClasspath("verification/incomplete.json");
    }

}
