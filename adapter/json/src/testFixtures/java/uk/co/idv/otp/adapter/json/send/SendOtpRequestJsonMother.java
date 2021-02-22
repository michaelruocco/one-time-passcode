package uk.co.idv.otp.adapter.json.send;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SendOtpRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("send/send-otp-request.json");
    }

}
