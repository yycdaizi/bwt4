package org.bjdrgs.bjwt.wt4.validation;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.time.DateUtils;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;

public class AgeFitBirthdayValidator implements
		ConstraintValidator<AgeFitBirthday, MedicalRecord> {

	@Override
	public void initialize(AgeFitBirthday constraintAnnotation) {

	}

	@Override
	public boolean isValid(MedicalRecord obj, ConstraintValidatorContext context) {
		Date birthday = obj.getAAA03();
		Date hospitalizedDate = obj.getAAB01(); // 入院日期
		Integer age = obj.getAAA04();

		if (birthday == null || hospitalizedDate == null) {
			// birthday和hospitalizedDate中有一个为null，age必须为null
			return age == null;
		} else {
			//birthday和hospitalizedDate都不为null
			if(age == null){
				return false;
			}
			int calAge = getAge(birthday, hospitalizedDate);
			return age.intValue() == calAge;
		}
	}

	private int getAge(Date birthday, Date dudate) {
		Calendar birthDate = DateUtils.toCalendar(birthday);
		Calendar duDate = DateUtils.toCalendar(dudate);
		birthDate = DateUtils.truncate(birthDate, Calendar.DATE);
		duDate = DateUtils.truncate(duDate, Calendar.DATE);

		if (duDate.before(birthDate)) {
			return -1;
		}

		int yearOfBirth = birthDate.get(Calendar.YEAR);
		int monthOfBirth = birthDate.get(Calendar.MONTH);
		int dayOfBirth = birthDate.get(Calendar.DATE);

		int yearOfDudate = duDate.get(Calendar.YEAR);
		int monthOfDudate = duDate.get(Calendar.MONTH);
		int dayOfDudate = duDate.get(Calendar.DATE);

		int age = yearOfDudate - yearOfBirth;

		if (monthOfDudate == monthOfBirth) {
			if (dayOfDudate < dayOfBirth) {
				age--;
			}
		} else if (monthOfDudate < monthOfBirth) {
			age--;
		}

		return age;
	}
}
