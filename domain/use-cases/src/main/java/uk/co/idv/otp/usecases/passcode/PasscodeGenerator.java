package uk.co.idv.otp.usecases.passcode;

import uk.co.idv.otp.entities.passcode.Passcode;
import uk.co.idv.otp.entities.passcode.GeneratePasscodeRequest;

public interface PasscodeGenerator {

    Passcode generate(GeneratePasscodeRequest verification);

}
