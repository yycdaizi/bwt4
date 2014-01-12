package org.bjdrgs.bjwt.wt4.xml.template;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.bjdrgs.bjwt.core.exception.BaseException;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

public class TemplateHelper {
	private static final String TEMPLATE_FOLDER = "classpath:/org/bjdrgs/bjwt/wt4/xml/template";
	private static final Map<String, Document> documentCache = Collections
			.synchronizedMap(new WeakHashMap<String, Document>());

	/**
	 * 创建模板的一个新的Document对象
	 * 
	 * @param templateName
	 * @return
	 */
	public static Document createDocument(String templateName) {
		// 使用缓存，不必每次都从文件读取，加快速度
		Document document = documentCache.get(templateName);
		if (document == null) {
			document = loadTemplateFile(templateName);
			documentCache.put(templateName, document);
		}
		return (Document) document.clone();
	}

	private static Document loadTemplateFile(String templateName) {
		SAXReader reader = new SAXReader();
		Document document = null;

		String path = TEMPLATE_FOLDER + File.separatorChar + templateName
				+ ".xml";
		try {
			document = reader.read(ResourceUtils.getFile(path));
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return document;
	}
}
