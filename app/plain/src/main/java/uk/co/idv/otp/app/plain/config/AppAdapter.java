package uk.co.idv.otp.app.plain.config;

import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;

public interface AppAdapter {

    Clock getClock();

    UuidGenerator getUuidGenerator();

}
