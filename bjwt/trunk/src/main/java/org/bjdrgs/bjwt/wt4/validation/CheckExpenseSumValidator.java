package org.bjdrgs.bjwt.wt4.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bjdrgs.bjwt.core.util.BeanUtils;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;

public class CheckExpenseSumValidator implements
		ConstraintValidator<CheckExpenseSum, MedicalRecord> {

	private static final String sumField = "ADA01";
	private static final String[] expenseFields = { "ADA11", "ADA21", "ADA22",
			"ADA23", "ADA24", "ADA25", "ADA26", "ADA27", "ADA28", "ADA13",
			"ADA15", "ADA12", "ADA29", "ADA03", "ADA30", "ADA31", "ADA32",
			"ADA07", "ADA08", "ADA33", "ADA34", "ADA35", "ADA36", "ADA37",
			"ADA38", "ADA02", "ADA39", "ADA09", "ADA10", "ADA04", "ADA40",
			"ADA41", "ADA42", "ADA43", "ADA44", "ADA05", "ADA06", "ADA20" };

	@Override
	public void initialize(CheckExpenseSum constraintAnnotation) {

	}

	@Override
	public boolean isValid(MedicalRecord obj, ConstraintValidatorContext context) {
		BigDecimal sum = new BigDecimal(0);
		for (String field : expenseFields) {
			Object expense = BeanUtils.getProperty(obj, field);
			if (expense != null) {
				sum = sum.add((BigDecimal) expense);
			}
		}
		Object sumFieldValue = BeanUtils.getProperty(obj, sumField);
		return sum.equals(sumFieldValue);
	}
}
