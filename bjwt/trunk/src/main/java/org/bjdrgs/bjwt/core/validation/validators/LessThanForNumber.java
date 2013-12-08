package org.bjdrgs.bjwt.core.validation.validators;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bjdrgs.bjwt.core.validation.constraints.LessThan;

public class LessThanForNumber implements
		ConstraintValidator<LessThan, Number> {

	private long lessThanValue;

	@Override
	public void initialize(LessThan constraintAnnotation) {
		this.lessThanValue = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		// null values are valid
		if (value == null) {
			return true;
		}
		if (value instanceof BigDecimal) {
			return ((BigDecimal) value).compareTo(BigDecimal.valueOf(lessThanValue)) == -1;
		} else if (value instanceof BigInteger) {
			return ((BigInteger) value).compareTo(BigInteger.valueOf(lessThanValue)) == -1;
		} else {
			long longValue = value.longValue();
			return longValue < lessThanValue;
		}
	}

}
