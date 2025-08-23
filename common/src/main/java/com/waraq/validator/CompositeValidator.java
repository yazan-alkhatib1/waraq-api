package com.waraq.validator;

import com.waraq.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.join;
import static java.util.Objects.isNull;

public class CompositeValidator<P, R> implements Validator<P, List<R>> {
    private final List<Validator<P, List<R>>> validators = new ArrayList<>();

    public static void joinViolations(List<String> violations) {
        if (!violations.isEmpty()) {
            throw new BadRequestException(join(",", violations));
        }
    }

    public static boolean isStringEmpty(String string) {
        return isNull(string) || string.isBlank();
    }

    public CompositeValidator<P, R> addValidator(Predicate<P> predicate, R error) {
        validators.add(new PredicateValidator<>(predicate, error));
        return this;
    }


    public static boolean hasValue(String str) {
        return str != null && !str.trim().isEmpty();
    }

    @Override
    public List<R> validate(P input) {
        return validators.stream()
                .map(v -> v.validate(input))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
