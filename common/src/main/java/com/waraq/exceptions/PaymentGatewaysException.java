package com.waraq.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentGatewaysException extends RuntimeException {

    private String code;

    private String description;

    private List<String> errors;

    private List<String> parameters;

    public PaymentGatewaysException(String message) {
        super(message);
    }
}
