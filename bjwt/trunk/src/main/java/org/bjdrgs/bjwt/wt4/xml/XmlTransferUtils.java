package org.bjdrgs.bjwt.wt4.xml;

import java.util.Collection;
import java.util.List;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.bean.FieldAccessor;
import org.bjdrgs.bjwt.core.bean.FieldDescriptor;
import org.bjdrgs.bjwt.core.exception.BaseException;
import org.bjdrgs.bjwt.core.util.FieldUtils;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.wt4.xml.template.TemplateHelper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

public class XmlTransferUtils {

	/**
	 * Object转xml<br/>
	 * 可转化以下类型的model
	 * @param entity
	 * @return
	 */
	public static Document toXML(Object entity) {
		Document document = TemplateHelper.createDocument(entity.getClass()
				.getSimpleName());

		SerializeVisitor visitor = new SerializeVisitor(entity);
		document.accept(visitor);

		// 去除空节点
		removeEmptyNode(document.getRootElement());
		return document;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> parseXML(Node node, Class<T> entityClass){
		DeserializeVisitor visitor = new DeserializeVisitor(entityClass);
		node.accept(visitor);
		return visitor.getData();
	}

	public static void removeEmptyNode(Element element) {
		if (element == null) {
			return;
		}
		for (Object child : element.elements()) {
			removeEmptyNode((Element) child);
		}
		String content = element.getStringValue();
		if (StringUtils.isBlank(content)) {
			element.detach();
		}
	}
	
	@Deprecated
	protected static Document toXML1(Object entity) {
		ConversionService conversionService = SpringContextUtils
				.getBean(ConversionService.class);
		Document document = TemplateHelper.createDocument(entity.getClass()
				.getSimpleName());

		FieldAccessor accessor = FieldUtils.getFieldAccessor(entity.getClass());
		FieldDescriptor[] fieldDescriptors = accessor.getFieldDescriptors();

		BeanMap beanMap = BeanMap.create(entity);
		for (FieldDescriptor fd : fieldDescriptors) {
			String key = fd.getName();
			if (beanMap.get(key) == null) {
				continue;
			}

			Node node = document.selectSingleNode("//" + key);
			if (node == null) {
				continue;
			}

			TypeDescriptor typeDescriptor = fd.getTypeDescriptor();
			Object value = beanMap.get(key);
			String text = "";
			if (typeDescriptor.isCollection()) {
				Collection<?> list = (Collection<?>) value;
				for (Object object : list) {
					Document child = toXML1(object);
					((Element) node).add(child.getRootElement());
				}
			} else if (typeDescriptor.isArray() || typeDescriptor.isMap()) {
				throw new BaseException("未识别类" + entity.getClass().getName()
						+ "的字段" + key + "的类型，无法处理");
			} else {
				/*
				 * //自己转换 Class<?> type = typeDescriptor.getType(); if (type ==
				 * Date.class) { DateTimeFormat format = (DateTimeFormat)
				 * typeDescriptor .getAnnotation(DateTimeFormat.class); String
				 * pattern = null; if (format != null) { pattern =
				 * format.pattern(); } else { pattern =
				 * Wt4Constants.DEFAULT_DATE_FORMAT_PATTERN; } text = new
				 * SimpleDateFormat(pattern).format(value); } else if
				 * (Number.class.isAssignableFrom(type)) { NumberFormat format =
				 * (NumberFormat) typeDescriptor
				 * .getAnnotation(NumberFormat.class); if (format != null) {
				 * text = new DecimalFormat(format.pattern()) .format(value); }
				 * else { if (type == Byte.class || type == Short.class || type
				 * == Integer.class || type == BigInteger.class || type ==
				 * Long.class) { text = value.toString(); } else { text = new
				 * DecimalFormat( Wt4Constants.DEFAULT_NUMBER_FORMAT_PATTERN)
				 * .format(value); } } } else if (type == String.class) { text =
				 * (String) value; } else { throw new BaseException("未识别类" +
				 * entity.getClass().getName() + "的字段" + key + "的类型，无法处理"); }
				 */
				text = (String) conversionService.convert(value,
						typeDescriptor, TypeDescriptor.valueOf(String.class));
				node.setText(text);
			}
		}
		// 去除空节点
		removeEmptyNode(document.getRootElement());
		return document;
	}
}
