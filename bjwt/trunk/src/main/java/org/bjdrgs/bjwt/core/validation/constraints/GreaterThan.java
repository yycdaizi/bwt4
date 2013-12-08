package org.bjdrgs.bjwt.core.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.bjdrgs.bjwt.core.validation.validators.GreaterThanForNumber;

/**
 * The annotated element must be a number whose value must be higher to the
 * specified value.
 * <p/>
 * Supported types are:
 * <ul>
 * <li><code>BigDecimal</code></li>
 * <li><code>BigInteger</code></li>
 * <li><code>byte</code>, <code>short</code>, <code>int</code>,
 * <code>long</code>, and their respective wrappers</li>
 * </ul>
 * Note that <code>double</code> and <code>float</code> are not supported due to
 * rounding errors (some providers might provide some approximative support)
 * <p/>
 * <code>null</code> elements are considered valid
 * 
 * @author ying
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = GreaterThanForNumber.class)
public @interface GreaterThan {
	String message() default "{javax.validation.constraints.GreaterThan.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * @return value the element must be higher to
	 */
	long value();

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		GreaterThan[] value();
	}
}
