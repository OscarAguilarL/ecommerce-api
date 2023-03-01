package com.oscar_aguilar.ecommerceapiv2.onboarding.controllers;

import com.oscar_aguilar.ecommerceapiv2.onboarding.dtos.ConfirmAccountDto;
import com.oscar_aguilar.ecommerceapiv2.onboarding.dtos.RegistryRequestDto;
import com.oscar_aguilar.ecommerceapiv2.onboarding.services.OnboardingService;
import com.oscar_aguilar.ecommerceapiv2.shared.CustomResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping("register")
    public ResponseEntity<CustomResponseDto<?>> register(@RequestBody RegistryRequestDto requestDto) {
        try {
            onboardingService.register(requestDto);
            CustomResponseDto<?> response = CustomResponseDto
                    .builder()
                    .success(true)
                    .message("All ok")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            CustomResponseDto<?> response = CustomResponseDto
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("confirm")
    public ResponseEntity<CustomResponseDto<?>> confirm(@RequestBody ConfirmAccountDto confirmAccountDto) {
        try {
            onboardingService.confirmCode(confirmAccountDto.getCode());
            CustomResponseDto<?> responseDto = CustomResponseDto
                    .builder()
                    .success(true)
                    .message("Account verified")
                    .build();
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            CustomResponseDto<?> responseDto = CustomResponseDto
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    TODO: Añadir endpoint para renovar código de verifición
}
