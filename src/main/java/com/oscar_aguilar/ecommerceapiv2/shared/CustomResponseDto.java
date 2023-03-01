package com.oscar_aguilar.ecommerceapiv2.shared;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomResponseDto<T> {
    private Boolean success;
    private String message;
    private T data;
    private List<String> errors;
}
