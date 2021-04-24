package uk.co.idv.otp.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OtpVerificationJsonMother {

    static String incompleteSms() {
        return loadContentFromClasspath("verification/sms/incomplete-sms.json");
    }

    static String incompleteSmsMasked() {
        return loadContentFromClasspath("verification/sms/incomplete-sms-masked.json");
    }

    static String incompleteEmail() {
        return loadContentFromClasspath("verification/email/incomplete-email.json");
    }

    static String incompleteEmailMasked() {
        return loadContentFromClasspath("verification/email/incomplete-email-masked.json");
    }

    static String incompleteVoice() {
        return loadContentFromClasspath("verification/voice/incomplete-voice.json");
    }

    static String incompleteVoiceMasked() {
        return loadContentFromClasspath("verification/voice/incomplete-voice-masked.json");
    }

}
