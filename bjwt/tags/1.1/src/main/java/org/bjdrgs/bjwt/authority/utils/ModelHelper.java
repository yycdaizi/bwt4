package org.bjdrgs.bjwt.authority.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

public class ModelHelper {
	/**
	 * 合并对象，将srcBean合并到tgtBean,去掉srcBean中的null值
	 * 
	 * @param srcBean
	 *            -- 初始对象
	 * @param tgtBean
	 *            -- 目标对象
	 * @param ignoreProperties -- 忽略属性
	 */
	public static void updateModel(Object srcBean, Object tgtBean,String[] ignoreProperties) {
		PropertyDescriptor[] propDescs = BeanUtils
				.getPropertyDescriptors(tgtBean.getClass());
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		if (propDescs != null && propDescs.length > 0) {
			for(PropertyDescriptor tgtProp : propDescs){
				if(tgtProp.getWriteMethod() == null ||(ignoreProperties!=null && ignoreList.contains(tgtProp.getName()))){
					continue;
				}
				PropertyDescriptor clientPd = BeanUtils.getPropertyDescriptor(srcBean.getClass(), tgtProp.getName());
				if (clientPd != null && clientPd.getReadMethod() != null) {
					try {
						Method readMethod = clientPd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(srcBean);
						if(value == null){
							continue;
						}
						Method writeMethod = tgtProp.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(tgtBean, value);
					}
					catch (Exception ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
	/**
	 * 合并对象，将srcBean合并到tgtBean,去掉srcBean中的null值
	 * @param srcBean
	 * @param tgtBean
	 */
	public static void updateModel(Object srcBean, Object tgtBean) {
		updateModel(srcBean, tgtBean,null);
	}
}
