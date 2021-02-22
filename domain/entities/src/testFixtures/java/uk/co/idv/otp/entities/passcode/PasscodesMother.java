package uk.co.idv.otp.entities.passcode;


public interface PasscodesMother {

    static Passcodes with(Passcode... passcodes) {
        return new Passcodes(passcodes);
    }

}
