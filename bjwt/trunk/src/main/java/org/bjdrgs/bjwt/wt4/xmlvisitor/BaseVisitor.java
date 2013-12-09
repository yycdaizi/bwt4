package org.bjdrgs.bjwt.wt4.xmlvisitor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.bjdrgs.bjwt.core.exception.BaseException;
import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;
import org.dom4j.Visitor;
import org.springframework.format.annotation.DateTimeFormat;

public class BaseVisitor<T> extends Observable implements Visitor {
	private static final int BATCH_SIZE = Integer.MAX_VALUE;

	private Class<T> clazz;
	private List<T> data = new ArrayList<T>();
	private T current;
	private String rootName;
	// 缓存字段，提高解析速度
	private Map<String, Field> fieldCache = new HashMap<String, Field>();

	public BaseVisitor(Class<T> clazz) {
		this.clazz = clazz;
		try {
			Field rootNameField = clazz.getDeclaredField("ROOT_NAME");
			rootNameField.setAccessible(true);
			rootName = (String) rootNameField.get(null);

			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				fieldCache.put(field.getName(), field);
			}
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
	
	public void flush() {
		setChanged();
		notifyObservers(data);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void visit(Element node) {
		if (rootName.equalsIgnoreCase(node.getName())) {
			try {
				if(data.size() == BATCH_SIZE){
					flush();
					data.clear();
				}
				
				current = clazz.newInstance();
				data.add(current);
			} catch (Exception e) {
				throw new BaseException(e);
			}
		} else {
			if (current == null) {
				return;
			}
			String key = node.getName().toUpperCase();
			Field field = fieldCache.get(key);
			if (field == null) {
				return;
			}
			try {
				String text = node.getTextTrim();
				Object value = null;
				Type type = field.getGenericType();
				if (type instanceof Class) {
					if (type == Date.class) {
						if (field.isAnnotationPresent(DateTimeFormat.class)) {
							DateTimeFormat format = field
									.getAnnotation(DateTimeFormat.class);
							// value = new
							// SimpleDateFormat(format.pattern()).parse(text);
							value = DateFormatCache.getDateFormat(
									format.pattern()).parse(text);
						} else {
							throw new BaseException("类" + clazz.getName()
									+ "的字段" + key + "未找到日期格式化注解");
						}
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
				} else if (type instanceof ParameterizedType) {
					ParameterizedType ptype = (ParameterizedType) type;
					if (ptype.getRawType() == List.class) {
						Type[] argTypes = ptype.getActualTypeArguments();
						Class<?> argClazz = (Class<?>) argTypes[0];

						BaseVisitor<?> visitor = new BaseVisitor(argClazz);
						node.accept(visitor);
						value = visitor.getData();
					} else {
						throw new BaseException("未识别类" + clazz.getName()
								+ "的字段" + key + "的类型，无法处理");
					}
				} else {
					throw new BaseException("未识别类" + clazz.getName() + "的字段"
							+ key + "的类型，无法处理");
				}
				field.set(current, value);
			} catch (Exception e) {
				throw new BaseException(e);
			}
		}
	}

	private static class DateFormatCache {
		private static Map<String, DateFormat> cache = new HashMap<String, DateFormat>();

		// 需要synchronized吗?
		public static DateFormat getDateFormat(String pattern) {
			DateFormat format = cache.get(pattern);
			if (format == null) {
				format = new SimpleDateFormat(pattern);
				cache.put(pattern, format);
			}
			return format;
		}
	}

	@Override
	public void visit(Document document) {
	}

	@Override
	public void visit(DocumentType documentType) {
	}

	@Override
	public void visit(Attribute node) {
	}

	@Override
	public void visit(CDATA node) {
	}

	@Override
	public void visit(Comment node) {
	}

	@Override
	public void visit(Entity node) {
	}

	@Override
	public void visit(Namespace namespace) {
	}

	@Override
	public void visit(ProcessingInstruction node) {
	}

	@Override
	public void visit(Text node) {
	}
}
