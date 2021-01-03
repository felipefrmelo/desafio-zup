package com.desafio.zup.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateValidator implements
        ConstraintValidator<CustomDateConstraint, String> {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    @Override
    public void initialize(CustomDateConstraint customDate) {
    }

    @Override
    public boolean isValid(String customDateField,
                           ConstraintValidatorContext cxt) {
        if(customDateField == null) return false;

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try
        {
            sdf.setLenient(false);
            Date d = sdf.parse(customDateField);
            return true;
        }
        catch (ParseException e)
        {
            return false;
        }
    }

}