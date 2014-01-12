package org.bjdrgs.bjwt.core.bean;

import java.lang.reflect.Field;

import org.springframework.core.convert.TypeDescriptor;

public class FieldDescriptor {

	private Field field;
	private TypeDescriptor typeDescriptor;
	
	public FieldDescriptor(Field field){
		this.field = field;
		this.typeDescriptor = new TypeDescriptor(field);
	}
	
	public String getName(){
		return field.getName();
	}

	public TypeDescriptor getTypeDescriptor() {
		return typeDescriptor;
	}
	
}
