package org.bjdrgs.bjwt.wt4.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 检查总费用是否等于各分项费用之和
 * 
 * @author ying
 * 
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = AgeFitBirthdayValidator.class)
@Documented
public @interface AgeFitBirthday {
	String message() default "{javax.validation.constraints.AgeFitBirthday.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
