package uk.co.idv.otp.app.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.otp.app.plain.Application;
import uk.co.idv.otp.entities.OtpVerification;
import uk.co.idv.otp.entities.send.ResendOtpRequest;
import uk.co.idv.otp.entities.send.SendOtpRequest;
import uk.co.idv.otp.entities.verify.VerifyOtpRequest;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/otp-verifications")
public class OtpController {

    private final Application application;

    @PostMapping
    public ResponseEntity<OtpVerification> sendOtp(@RequestBody SendOtpRequest request) {
        OtpVerification verification = application.sendOtp(request);
        return ResponseEntity
                .created(buildGetOtpUri(verification.getId()))
                .body(verification);
    }

    @GetMapping("/{id}")
    public OtpVerification getOtp(@PathVariable("id") UUID id) {
        return application.getOtp(id);
    }

    @PatchMapping("/passcodes")
    public OtpVerification resendOtp(@RequestBody ResendOtpRequest request) {
        return application.resendOtp(request);
    }

    @PatchMapping("/{id}")
    public OtpVerification verifyOtp(@RequestBody VerifyOtpRequest request) {
        return application.verifyOtp(request);
    }

    private static URI buildGetOtpUri(UUID id) {
        return linkTo(methodOn(OtpController.class).getOtp(id)).toUri();
    }

}
