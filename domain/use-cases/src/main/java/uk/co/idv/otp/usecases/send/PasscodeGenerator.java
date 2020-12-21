package uk.co.idv.otp.usecases.send;

import uk.co.idv.otp.entities.verification.Passcode;

public interface PasscodeGenerator {

    Passcode generate();

}
