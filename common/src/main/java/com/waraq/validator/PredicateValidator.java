package com.waraq.validator;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class PredicateValidator<P, R> implements Validator<P, List<R>> {

    private final Predicate<P> inputPredicate;
    private final Function<P, R> function;

    public PredicateValidator(Predicate<P> inputPredicate, R error) {
        this.inputPredicate = inputPredicate;
        this.function = p -> error;
    }

    @Override
    public List<R> validate(P input) {
        return inputPredicate.test(input) ?
                singletonList(function.apply(input)) :
                emptyList();
    }
}
