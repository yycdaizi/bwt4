package org.bjdrgs.bjwt.wt4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.wt4.Wt4Constants;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

public class XmlPreprocessor implements ElementHandler {
	public static String[] commonKey = new String[] { "ZA01C", "ZA02C", "ZA03",
			"ZA04" };
	public static final String[] keysToCheck = { "AAA28", "AAC01" };

	private Map<String, Object> commonPropMap = new HashMap<String, Object>();

	private Object[] uniquePropValues;
	private boolean valid = true;

	@Override
	public void onStart(ElementPath elementPath) {
		Element element = elementPath.getCurrent();
		if (valid) {
			if (MedicalRecord.ROOT_NAME.equalsIgnoreCase(element.getName())) {
				uniquePropValues = new Object[keysToCheck.length];
			}
		}
	}

	@Override
	public void onEnd(ElementPath elementPath) {
		Element element = elementPath.getCurrent();

		String key = element.getName().toUpperCase();

		// 验证，如果验证已经失败，就不用继续验证了
		if (valid) {
			if (MedicalRecord.ROOT_NAME.equals(key)) {
				checkUniqueProp();
				uniquePropValues = null;
			} else {
				int i = ArrayUtils.indexOf(keysToCheck, key);
				if (i != ArrayUtils.INDEX_NOT_FOUND) {
					if (uniquePropValues == null) {
						valid = false;
					} else {
						uniquePropValues[i] = element.getTextTrim();
					}
				}
			}

			if (ArrayUtils.contains(commonKey, key)) {
				commonPropMap.put(key, element.getTextTrim());
			}

			// 最后验证commonPropMap
			if (Wt4Constants.MR_XML_ROOT.equals(key)) {
				checkCommonProp();
			}
		}

		element.detach();
		element = null;
	}

	private void checkUniqueProp() {
		for (int i = 0; i < uniquePropValues.length; i++) {
			if (isEmpty(uniquePropValues[i])) {
				valid = false;
				break;
			}
		}
	}

	private boolean isEmpty(Object obj) {
		return obj instanceof CharSequence ? StringUtils
				.isEmpty((CharSequence) obj) : obj == null;
	}

	private void checkCommonProp() {
		// 因为ZA02C可以取当前用户的编码，所以不用验证了
		// if(isEmpty(commonPropMap.get("ZA02C"))){
		// valid = false;
		// }
	}

	public Map<String, Object> getCommonPropMap() {
		return commonPropMap;
	}

	public boolean isValid() {
		return valid;
	}
}
