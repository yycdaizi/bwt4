package org.bjdrgs.bjwt.core.util;

import org.bjdrgs.bjwt.core.bean.CashedFieldAccessor;
import org.bjdrgs.bjwt.core.bean.FieldAccessor;

public class FieldUtils {
	
	public static FieldAccessor getFieldAccessor(Class<?> clazz){
		return CashedFieldAccessor.forClass(clazz);
	}
}
