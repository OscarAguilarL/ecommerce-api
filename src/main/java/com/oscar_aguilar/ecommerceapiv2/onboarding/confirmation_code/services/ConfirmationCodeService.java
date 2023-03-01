package com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.services;

import com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.entities.ConfirmationCode;
import com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.repositories.ConfirmationCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository repository;

    public void saveConfirmationCode(ConfirmationCode code) {
        repository.save(code);
    }

    public Optional<ConfirmationCode> getCode(Integer code) {
        return repository.findByCode(code);
    }

    public void setConfirmedAt(Integer code) {
        repository.updateConfirmedAt(code, LocalDateTime.now());
    }
}
