package org.bjdrgs.bjwt.wt4.xml;

import java.util.Collection;

import net.sf.cglib.beans.BeanMap;

import org.bjdrgs.bjwt.core.bean.FieldAccessor;
import org.bjdrgs.bjwt.core.exception.BaseException;
import org.bjdrgs.bjwt.core.util.FieldUtils;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

public class SerializeVisitor extends VisitorSupport {

	private Object data;
	private BeanMap beanMap;
	private FieldAccessor fieldAccessor;
	
	private ConversionService conversionService = SpringContextUtils
			.getBean(ConversionService.class);


	public SerializeVisitor(Object obj) {
		this.data = obj;
		beanMap = BeanMap.create(obj);
		fieldAccessor = FieldUtils.getFieldAccessor(obj.getClass());
	}

	@Override
	public void visit(Element node) {
		String key = node.getName().toUpperCase();
		Object value = beanMap.get(key);
		if (value == null) {
			return;
		}
		TypeDescriptor typeDescriptor = fieldAccessor.getFieldDescriptor(key)
				.getTypeDescriptor();
		if (typeDescriptor.isCollection()) {
			Collection<?> list = (Collection<?>) value;
			for (Object object : list) {
				Document child;
				try {
					child = XmlTransferUtils.toXML(object);
				} catch (Exception e) {
					throw new BaseException(e);
				}
				node.add(child.getRootElement());
			}
		} else if (typeDescriptor.isArray() || typeDescriptor.isMap()) {
			throw new BaseException("未识别类" + data.getClass().getName() + "的字段"
					+ key + "的类型，无法处理");
		} else {
			String text = (String) conversionService.convert(value,
					typeDescriptor, TypeDescriptor.valueOf(String.class));
			node.addText(text);
		}
	}
}
