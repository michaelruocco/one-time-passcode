package uk.co.idv.otp.app.manual.config;

import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;

public interface AppAdapter {

    Clock getClock();

    UuidGenerator getUuidGenerator();

}
