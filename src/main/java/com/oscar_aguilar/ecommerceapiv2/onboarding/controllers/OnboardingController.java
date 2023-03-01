package com.oscar_aguilar.ecommerceapiv2.onboarding.controllers;

import com.oscar_aguilar.ecommerceapiv2.onboarding.dtos.ConfirmAccountDto;
import com.oscar_aguilar.ecommerceapiv2.onboarding.dtos.RegistryRequestDto;
import com.oscar_aguilar.ecommerceapiv2.onboarding.services.OnboardingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegistryRequestDto requestDto) {
        onboardingService.register(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("confirm")
    public ResponseEntity confirm(@RequestBody ConfirmAccountDto confirmAccountDto) {
        onboardingService.confirmCode(confirmAccountDto.getCode());
        return ResponseEntity.ok().build();
    }
}
