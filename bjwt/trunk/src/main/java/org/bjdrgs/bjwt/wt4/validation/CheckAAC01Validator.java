package org.bjdrgs.bjwt.wt4.validation;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bjdrgs.bjwt.wt4.model.MedicalRecord;

public class CheckAAC01Validator implements
		ConstraintValidator<CheckAAC01, MedicalRecord> {

	@Override
	public void initialize(CheckAAC01 constraintAnnotation) {

	}

	@Override
	public boolean isValid(MedicalRecord obj, ConstraintValidatorContext context) {
		Date leaveTime = obj.getAAC01(); // 出院日期
		if(leaveTime == null){
			return true;
		}
		
		Date birthday = obj.getAAA03();
		if(birthday != null && !leaveTime.after(birthday)){
			return false;
		}
		
		Date hospitalizedDate = obj.getAAB01(); // 入院日期

		if(hospitalizedDate != null && !leaveTime.after(hospitalizedDate)){
			return false;
		}

		return true;
	}
}
