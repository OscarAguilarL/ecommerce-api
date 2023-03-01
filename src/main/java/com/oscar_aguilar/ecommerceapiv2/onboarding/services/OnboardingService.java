package com.oscar_aguilar.ecommerceapiv2.onboarding.services;

import com.oscar_aguilar.ecommerceapiv2.email.EmailDetails;
import com.oscar_aguilar.ecommerceapiv2.email.JavaMailService;
import com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.entities.ConfirmationCode;
import com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.services.ConfirmationCodeService;
import com.oscar_aguilar.ecommerceapiv2.onboarding.dtos.RegistryRequestDto;
import com.oscar_aguilar.ecommerceapiv2.user.entities.AppUserRole;
import com.oscar_aguilar.ecommerceapiv2.user.entities.User;
import com.oscar_aguilar.ecommerceapiv2.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class OnboardingService {

    private final UserService userService;
    private final EmailValidatorService emailValidatorService;
    private final ConfirmationCodeService confirmationCodeService;
    private final JavaMailService javaMailService;

    public void register(RegistryRequestDto registryRequestDto) {
        boolean isValidEmail = emailValidatorService.test(registryRequestDto.getEmail());
        if (!isValidEmail)
            throw new IllegalStateException("Invalid email");

        ConfirmationCode confirmationCode = userService.registerUser(new User(
                registryRequestDto.getName(),
                registryRequestDto.getLastname(),
                registryRequestDto.getEmail(),
                registryRequestDto.getPassword(),
                AppUserRole.USER
        ));
        javaMailService.sendHtmlEmail(new EmailDetails(
                registryRequestDto.getEmail(),
                String.format("Confirmation code: %s", confirmationCode.getCode()),
                "Verify your account"
        ));
    }

    @Transactional
    public void confirmCode(int code) {
        ConfirmationCode confirmationCode = confirmationCodeService
                .getCode(code)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationCode.getConfirmedAt() != null)
            throw new IllegalStateException("Email already confirmed");

        LocalDateTime expiredAt = confirmationCode.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Confirmation code expired");

        confirmationCodeService.setConfirmedAt(code);
        userService.enableUser(confirmationCode.getUser().getEmail());
    }
}
