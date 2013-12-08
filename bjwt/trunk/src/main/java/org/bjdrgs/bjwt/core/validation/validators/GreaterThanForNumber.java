package org.bjdrgs.bjwt.core.validation.validators;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bjdrgs.bjwt.core.validation.constraints.GreaterThan;

public class GreaterThanForNumber implements
		ConstraintValidator<GreaterThan, Number> {

	private long greaterThanValue;

	@Override
	public void initialize(GreaterThan constraintAnnotation) {
		this.greaterThanValue = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		// null values are valid
		if (value == null) {
			return true;
		}
		if (value instanceof BigDecimal) {
			return ((BigDecimal) value).compareTo(BigDecimal.valueOf(greaterThanValue)) == 1;
		} else if (value instanceof BigInteger) {
			return ((BigInteger) value).compareTo(BigInteger.valueOf(greaterThanValue)) == 1;
		} else {
			long longValue = value.longValue();
			return longValue > greaterThanValue;
		}
	}

}
