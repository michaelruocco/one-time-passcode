package uk.co.idv.otp.adapter.verificationloader;

import java.time.Instant;

public class StubContextConstants {

    private StubContextConstants() {
        // constants class
    }

    public static final String CONTEXT_NOT_FOUND_ID = "9ed739ec-a252-4a3f-840c-4e2bdccf56e6";
    public static final String CONTEXT_EXPIRED_ID = "2b1f8ba4-00e7-4ad9-819f-5249af834f2e";
    public static final String OTP_METHOD_NOT_FOUND_ID = "9a54c8f7-7a2f-4b68-91df-35689a7c5848";
    public static final String DELIVERY_METHOD_NOT_FOUND_ID = "a6af46ef-5080-4c77-9a26-27b966266d03";
    public static final String DELIVERY_METHOD_NOT_ELIGIBLE_ID = "02ebd8d2-0890-4661-b9e3-4156cfc72a0c";

    public static final Instant CONTEXT_EXPIRY = Instant.parse("2021-01-04T23:24:07.385Z");

}
