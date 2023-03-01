package com.oscar_aguilar.ecommerceapiv2.onboarding.services;

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

    public User register(RegistryRequestDto registryRequestDto) {
        boolean isValidEmail = emailValidatorService.test(registryRequestDto.getEmail());
        if (!isValidEmail)
            throw new IllegalStateException("Invalid email");

        if (!registryRequestDto.getPassword().equals(registryRequestDto.getPasswordConfirmation()))
            throw new IllegalStateException("Password confirmation does not match");

        User user = userService.registerUser(new User(
                registryRequestDto.getName(),
                registryRequestDto.getLastname(),
                registryRequestDto.getEmail(),
                registryRequestDto.getPassword(),
                AppUserRole.USER
        ));
        return user;
    }

    @Transactional
    public void confirmCode(int code) {
        ConfirmationCode confirmationCode = confirmationCodeService
                .getCode(code)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationCode.getConfirmedAt() != null)
            throw new IllegalStateException("Email already confirmed");

        LocalDateTime expiresAt = confirmationCode.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Confirmation code expired");

        confirmationCodeService.setConfirmedAt(code);
        userService.enableUser(confirmationCode.getUser().getEmail());
    }
}
