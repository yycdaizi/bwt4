package org.bjdrgs.bjwt.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.bjdrgs.bjwt.core.exception.BaseException;

public class BeanUtils extends org.springframework.beans.BeanUtils {

	public static Object getProperty(Object bean, String propertyName) {
		PropertyDescriptor property = getPropertyDescriptor(bean.getClass(),
				propertyName);
		if (property == null) {
			throw new BaseException("No such property");
		}
		Method readMethod = property.getReadMethod();
		if (readMethod == null) {
			throw new BaseException("property" + propertyName
					+ " can't be read");
		}
		try {
			return readMethod.invoke(bean);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}
}
