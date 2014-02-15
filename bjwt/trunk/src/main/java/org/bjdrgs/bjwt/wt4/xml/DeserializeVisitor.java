package org.bjdrgs.bjwt.wt4.xml;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.bean.FieldAccessor;
import org.bjdrgs.bjwt.core.bean.FieldDescriptor;
import org.bjdrgs.bjwt.core.exception.BaseException;
import org.bjdrgs.bjwt.core.util.BeanUtils;
import org.bjdrgs.bjwt.core.util.FieldUtils;
import org.bjdrgs.bjwt.wt4.Wt4Constants;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.annotation.DateTimeFormat;

public class DeserializeVisitor<T> extends VisitorSupport {

	private Class<T> clazz;
	private List<T> data = new ArrayList<T>();
	private T current;
	private String rootName;

	private BeanMap beanMap;
	private FieldAccessor fieldAccessor;

	// private ConversionService conversionService = SpringContextUtils
	// .getBean(ConversionService.class);

	public DeserializeVisitor(Class<T> clazz) {
		this.clazz = clazz;
		this.fieldAccessor = FieldUtils.getFieldAccessor(clazz);
		this.beanMap = BeanMap.create(BeanUtils.instantiate(clazz));
		try {
			Field rootNameField = clazz.getDeclaredField("ROOT_NAME");
			rootNameField.setAccessible(true);
			rootName = (String) rootNameField.get(null);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	@Override
	public void visit(Element node) {
		if (rootName.equalsIgnoreCase(node.getName())) {
			current = BeanUtils.instantiate(clazz);
			data.add(current);
		} else {
			if (current == null) {
				return;
			}
			String key = node.getName().toUpperCase();
			FieldDescriptor fd = null;
			try {
				fd = fieldAccessor.getFieldDescriptor(key);
			} catch (Exception e) {
				return;
			}

			TypeDescriptor typeDescriptor = fd.getTypeDescriptor();
			if (typeDescriptor.isCollection()) {
				if (typeDescriptor.getType() == List.class) {
					Class<?> elementClass = typeDescriptor
							.getElementTypeDescriptor().getType();
					beanMap.put(current, key,
							XmlTransferUtils.parseXML(node, elementClass));
				} else {
					throw new BaseException("未识别类" + clazz.getName() + "的字段"
							+ key + "的类型，无法处理");
				}
			} else if (typeDescriptor.isArray() || typeDescriptor.isMap()) {
				throw new BaseException("未识别类" + clazz.getName() + "的字段" + key
						+ "的类型，无法处理");
			} else {
				// Object value = conversionService.convert(node.getTextTrim(),
				// TypeDescriptor.valueOf(String.class), typeDescriptor);
				String text = node.getTextTrim();
				if(StringUtils.isEmpty(text)){
					return;
				}
				
				Object value = null;
				Class<?> type = typeDescriptor.getType();
				try {
					if (type == Date.class) {
						DateTimeFormat format = (DateTimeFormat) typeDescriptor
								.getAnnotation(DateTimeFormat.class);
						String pattern = null;
						if (format != null) {
							pattern = format.pattern();
						} else {
							pattern = Wt4Constants.DEFAULT_DATE_FORMAT_PATTERN;
						}
						value = new SimpleDateFormat(pattern).parse(text);
					} else if (type == Double.class) {
						value = Double.valueOf(text);
					} else if (type == BigDecimal.class) {
						value = new BigDecimal(text);
					} else if (type == Integer.class) {
						value = Integer.valueOf(text);
					} else if (type == String.class) {
						value = text;
					} else {
						throw new BaseException("未识别类" + clazz.getName()
								+ "的字段" + key + "的类型，无法处理");
					}
				} catch (Exception e) {
					throw new BaseException(e);
				}

				beanMap.put(current, key, value);
			}
		}
	}
}
