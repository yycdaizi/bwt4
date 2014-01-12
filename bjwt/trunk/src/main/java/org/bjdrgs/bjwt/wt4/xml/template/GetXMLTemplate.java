package org.bjdrgs.bjwt.wt4.xml.template;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class GetXMLTemplate {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
		Document document = null;
		
		String base = "/org/bjdrgs/bjwt/wt4/template/";
		String path1 = base + "MedicalRecord.xml";
		String path2 = base +"BirthDefect.xml";
		String path3 = base +"Diagnose.xml";
		String path4 = base +"ICU.xml";
		String path5 = base +"Operation.xml";
		String path6 = base +"Surgery.xml";
		try {
			document = reader.read(GetXMLTemplate.class.getResourceAsStream(path6));
			Element root = document.getRootElement();
			clear(root);
			System.out.println(root.asXML());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void clear(Element element){
		List<Node> children = element.content();
		for(int i=children.size()-1;i>=0;i--) {
			Node node = children.get(i);
			int type = node.getNodeType();
			switch(type){
			case Node.ELEMENT_NODE:
				//System.out.println(node.getName());
				clear((Element)node);
				break;
			case Node.TEXT_NODE:
				node.detach();
				break;
			}
		}
		if(element.isTextOnly()){
			element.clearContent();
		}
	}
}
