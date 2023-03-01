package com.oscar_aguilar.ecommerceapiv2.onboarding.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistryRequestDto {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String passwordConfirmation;
}
