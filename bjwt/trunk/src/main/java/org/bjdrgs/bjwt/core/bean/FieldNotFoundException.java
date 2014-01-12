package org.bjdrgs.bjwt.core.bean;

import org.springframework.beans.BeansException;

public class FieldNotFoundException extends BeansException {
	private static final long serialVersionUID = 9037374798734077068L;

	private Class<?> beanClass;

	private String fieldName;

	/**
	 * Create a new InvalidPropertyException.
	 * 
	 * @param beanClass
	 *            the offending bean class
	 * @param propertyName
	 *            the offending property
	 * @param msg
	 *            the detail message
	 */
	public FieldNotFoundException(Class<?> beanClass, String fieldName) {
		this(beanClass, fieldName, null);
	}

	/**
	 * Create a new InvalidPropertyException.
	 * 
	 * @param beanClass
	 *            the offending bean class
	 * @param propertyName
	 *            the offending property
	 * @param msg
	 *            the detail message
	 * @param cause
	 *            the root cause
	 */
	public FieldNotFoundException(Class<?> beanClass, String fieldName,
			Throwable cause) {
		super("Field '" + fieldName + "' of bean class ["
				+ beanClass.getName() + "] cann't found", cause);
		this.beanClass = beanClass;
		this.fieldName = fieldName;
	}

	/**
	 * Return the offending bean class.
	 */
	public Class<?> getBeanClass() {
		return beanClass;
	}

	/**
	 * Return the name of the offending field.
	 */
	public String getFieldName() {
		return fieldName;
	}
}
