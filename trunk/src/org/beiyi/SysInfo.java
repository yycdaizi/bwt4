package org.beiyi;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.beiyi.wt4.MappingXMLOperator;

public class SysInfo {
	private static Map<String, MappingXMLOperator> map;
	private static Properties config;
	static{
		map = new HashMap<String, MappingXMLOperator>();
		map.put("medicalRecord", new MappingXMLOperator("/org/beiyi/wt4/mapping/MedicalRecord.Mapping.xml"));
		map.put("diagnose", new MappingXMLOperator("/org/beiyi/wt4/mapping/Diagnose.Mapping.xml"));
		map.put("surgery", new MappingXMLOperator("/org/beiyi/wt4/mapping/Surgery.Mapping.xml"));
		map.put("operator", new MappingXMLOperator("/org/beiyi/wt4/mapping/Operation.Mapping.xml"));
		map.put("disabled", new MappingXMLOperator("/org/beiyi/wt4/mapping/Disabled.Mapping.xml"));
		map.put("icu", new MappingXMLOperator("/org/beiyi/wt4/mapping/Icu.Mapping.xml"));
		
		// 生成输入流  
        InputStream ins=SysInfo.class.getResourceAsStream("/cfg/config.properties");  
        // 生成properties对象  
        config = new Properties();  
        try {  
        	config.load(ins);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}

	public static MappingXMLOperator getMappingXMLOperator(String name){
		return map.get(name); 
	}
	
	public static String getParmeter(String name){
		if(config!=null){
			return config.getProperty(name);
		}
		return null;
	}
}
