package org.bjdrgs.bjwt.core.bean;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.beans.BeansException;
import org.springframework.util.ReflectionUtils;

public class CashedFieldAccessor implements FieldAccessor {
	private static final Map<Class<?>, CashedFieldAccessor> classCache = Collections
			.synchronizedMap(new WeakHashMap<Class<?>, CashedFieldAccessor>());

	public static FieldAccessor forClass(Class<?> beanClass) {
		CashedFieldAccessor accessor = classCache.get(beanClass);
		if (accessor == null) {
			accessor = new CashedFieldAccessor(beanClass);
			classCache.put(beanClass, accessor);
		}
		return accessor;
	}

	private final Class<?> targetClass;
	private final Map<String, FieldDescriptor> fieldDescriptorCache = new HashMap<String, FieldDescriptor>();

	private CashedFieldAccessor(Class<?> beanClass) {
		this.targetClass = beanClass;
		ReflectionUtils.doWithFields(beanClass,
				new ReflectionUtils.FieldCallback() {
					public void doWith(Field field) {
						if (fieldDescriptorCache.containsKey(field.getName())) {
							// ignore superclass declarations of fields already
							// found in a subclass
						} else {
							fieldDescriptorCache.put(field.getName(), new FieldDescriptor(field));
						}
					}
				});
	}

	public Class<?> getTargetClass() {
		return this.targetClass;
	}

	private void checkFieldExist(String fieldName) throws BeansException {
		FieldDescriptor fd = this.fieldDescriptorCache.get(fieldName);
		if (fd == null) {
			throw new FieldNotFoundException(targetClass, fieldName);
		}
	}

	@Override
	public FieldDescriptor getFieldDescriptor(String fieldName)
			throws BeansException {
		checkFieldExist(fieldName);
		return this.fieldDescriptorCache.get(fieldName);
	}

	@Override
	public FieldDescriptor[] getFieldDescriptors() {
		FieldDescriptor[] fds = new FieldDescriptor[this.fieldDescriptorCache.size()];
		int i = 0;
		for (FieldDescriptor fd : this.fieldDescriptorCache.values()) {
			fds[i] = fd;
			i++;
		}
		return fds;
	}
}
