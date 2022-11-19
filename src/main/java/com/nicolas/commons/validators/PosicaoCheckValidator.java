package com.nicolas.commons.validators;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PosicaoCheckValidator implements ConstraintValidator<PosicaoCheck, CharSequence> {

    private List<String> valoresDoEnum;

    @Override
    public void initialize(PosicaoCheck params) {
        valoresDoEnum = Stream.of(params.enumClass().getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value != null && valoresDoEnum.contains(value.toString().toUpperCase());
    }
}