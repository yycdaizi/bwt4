package org.beiyi.common.tools;

import java.io.File;

import org.beiyi.wt4.service.ICaseService;
import org.beiyi.wt4.service.impl.CaseServiceImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class AppTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AppTest build = new AppTest();
		String xml = build.LoadMappingXML("/org/beiyi/wt4/example.xml");
		ICaseService caseService = new CaseServiceImpl();
		try {
			caseService.save(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String LoadMappingXML(String path){
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(this.getClass().getResourceAsStream(path));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document.asXML();
	}

}
