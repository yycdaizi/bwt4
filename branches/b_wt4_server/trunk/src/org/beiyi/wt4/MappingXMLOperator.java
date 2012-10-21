package org.beiyi.wt4;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class MappingXMLOperator {
	private Document document;

	public Document getDocument() {
		return document;
	}

//	public MappingXMLOperator(){
//		this.LoadMappingXML("/org/beiyi/wt4/mapping/MedicalRecord.Mapping.xml");
//	}
	
	public MappingXMLOperator(String path){
		this.LoadMappingXML(path);
	}
	
	private void LoadMappingXML(String path){
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(this.getClass().getResourceAsStream(path));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Node getNodeByCode(String code){
		return getNodeByCode(document, code);
	}
	public Node getNodeByColumn(String column){
		return getNodeByColumn(document, column);
	}
	public Node getNodeById(String id){
		return getNodeById(document, id);
	}
	public Node getNodeByCode(Node root, String code){
		return root.selectSingleNode("//*[@code='"+code+"']");
	}
	public Node getNodeByColumn(Node root, String column){
		return root.selectSingleNode("//*[@column='"+column+"']");
	}
	public Node getNodeById(Node root, String id){
		return root.selectSingleNode("//*[@id='"+id+"']");
	}
	
	public String getTable(){
		return document.selectSingleNode("//entity[1]").valueOf("@table");
	}
	
	public String getId(Node node){
		return node.valueOf("@id");
	}
	
	public String getCode(Node node){
		return node.valueOf("@code");
	}
	public String getColumn(Node node){
		return node.valueOf("@column");
	}
	public String getDataType(Node node){
		return node.valueOf("@datatype");
	}
	public String getObjectRef(Node node){
		return node.valueOf("@object-ref");
	}
	
	public static void main(String[] args){
		MappingXMLOperator operator= new MappingXMLOperator("/org/beiyi/wt4/mapping/MedicalRecord.Mapping.xml");
		Element node = (Element) operator.getNodeByCode("A1");
		System.out.print(node.getTextTrim());
	}
}
