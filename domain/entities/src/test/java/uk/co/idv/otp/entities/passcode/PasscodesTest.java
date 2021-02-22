package uk.co.idv.otp.entities.passcode;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class PasscodesTest {

    @Test
    void shouldBeIterable() {
        Passcode passcode1 = PasscodeMother.withValue("11111111");
        Passcode passcode2 = PasscodeMother.withValue("22222222");

        Passcodes passcodes = PasscodesMother.with(passcode1, passcode2);

        assertThat(passcodes).containsExactly(passcode1, passcode2);
    }

    @Test
    void shouldReturnValues() {
        Passcode passcode1 = PasscodeMother.withValue("11111111");
        Passcode passcode2 = PasscodeMother.withValue("22222222");

        Passcodes passcodes = PasscodesMother.with(passcode1, passcode2);

        assertThat(passcodes.getValues()).containsExactly(passcode1, passcode2);
    }

    @Test
    void shouldReturnPasscodesThatAreActive() {
        Instant now = Instant.now();
        Passcode passcode1 = PasscodeMother.withExpiry(now.minusMillis(1));
        Passcode passcode2 = PasscodeMother.withExpiry(now.plusMillis(1));

        Passcodes passcodes = PasscodesMother.with(passcode1, passcode2);

        assertThat(passcodes.getActive(now)).containsExactly(passcode2);
    }

    @Test
    void shouldReturnIsValidTrueIfAnyAttemptedValueMatchesAnyActualValue() {
        Passcode passcode1 = PasscodeMother.withValue("11111111");
        Passcode passcode2 = PasscodeMother.withValue("22222222");
        Passcodes passcodes = PasscodesMother.with(passcode1, passcode2);

        boolean anyValid = passcodes.anyValid(Collections.singleton("22222222"));

        assertThat(anyValid).isTrue();
    }

    @Test
    void shouldReturnIsValidFalseIfAnyAttemptedValuesDoNotMatchAnyActualValues() {
        Passcode passcode1 = PasscodeMother.withValue("11111111");
        Passcode passcode2 = PasscodeMother.withValue("22222222");
        Passcodes passcodes = PasscodesMother.with(passcode1, passcode2);

        boolean anyValid = passcodes.anyValid(Collections.singleton("12345678"));

        assertThat(anyValid).isFalse();
    }

}
