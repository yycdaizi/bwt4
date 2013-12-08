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
 * 出院日期必须晚于出生日期和入院日期<br/>
 * null is valid
 * 
 * @author ying
 * 
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckAAC01Validator.class)
@Documented
public @interface CheckAAC01 {
	String message() default "{javax.validation.constraints.CheckAAC01.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
