package org.bjdrgs.bjwt.core.bean;

import org.springframework.beans.BeansException;

public interface FieldAccessor {

	FieldDescriptor getFieldDescriptor(String fieldName) throws BeansException;
	FieldDescriptor[] getFieldDescriptors();

}