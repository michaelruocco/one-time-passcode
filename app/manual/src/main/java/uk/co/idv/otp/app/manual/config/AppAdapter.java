package uk.co.idv.otp.app.manual.config;

import uk.co.idv.common.usecases.id.IdGenerator;

import java.time.Clock;

public interface AppAdapter {

    Clock getClock();

    IdGenerator getIdGenerator();

}
