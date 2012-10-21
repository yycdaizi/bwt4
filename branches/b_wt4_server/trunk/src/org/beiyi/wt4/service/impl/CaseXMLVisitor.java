package org.beiyi.wt4.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beiyi.SysInfo;
import org.beiyi.wt4.MappingXMLOperator;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;

public class CaseXMLVisitor extends VisitorSupport {

	private Map<String, Object> data = new HashMap<String, Object>();
	private MappingXMLOperator operator;

	public CaseXMLVisitor(MappingXMLOperator operator) {
		this.operator = operator;
	}

	@Override
	public void visit(Element node) {
		String code = node.getName();
		Node cfgNode = operator.getNodeByCode(code);
		if (cfgNode != null) {
			if ("field".equalsIgnoreCase(cfgNode.getName())) {
				String text = node.getTextTrim();
				Object value = text;
				String datatype = operator.getDataType(cfgNode);
				if ("int".equals(datatype)) {
					value = new Integer(text);
				} else if ("double".equals(datatype)) {
					value = new Double(text);
				} else if ("date".equals(datatype)) {
					try {
						value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(text);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				data.put(operator.getColumn(cfgNode), value);
			}
			if ("list".equalsIgnoreCase(cfgNode.getName())) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				data.put(operator.getId(cfgNode), list);
				String object_ref = operator.getObjectRef(cfgNode);
				for (Iterator i = node.elementIterator(); i.hasNext();) {
					Element element = (Element) i.next();
					CaseXMLVisitor visitor = new CaseXMLVisitor(
							SysInfo.getMappingXMLOperator(object_ref));
					element.accept(visitor);
					list.add(visitor.getData());
				}
			}
		}
	}

	public MappingXMLOperator getOperator() {
		return operator;
	}

	public void setOperator(MappingXMLOperator operator) {
		this.operator = operator;
	}
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
