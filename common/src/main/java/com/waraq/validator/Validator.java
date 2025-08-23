package com.waraq.validator;

public interface Validator<P, R> {

    R validate(P input);
}
