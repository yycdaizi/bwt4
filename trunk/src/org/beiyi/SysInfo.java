package org.beiyi;

import java.util.HashMap;
import java.util.Map;

import org.beiyi.wt4.MappingXMLOperator;

public class SysInfo {
	private static Map<String, MappingXMLOperator> map;
	static{
		map = new HashMap<String, MappingXMLOperator>();
		map.put("medicalRecord", new MappingXMLOperator("/org/beiyi/wt4/mapping/MedicalRecord.Mapping.xml"));
		map.put("diagnose", new MappingXMLOperator("/org/beiyi/wt4/mapping/Diagnose.Mapping.xml"));
		map.put("surgery", new MappingXMLOperator("/org/beiyi/wt4/mapping/Surgery.Mapping.xml"));
		map.put("operator", new MappingXMLOperator("/org/beiyi/wt4/mapping/Operation.Mapping.xml"));
		map.put("disabled", new MappingXMLOperator("/org/beiyi/wt4/mapping/Disabled.Mapping.xml"));
		map.put("icu", new MappingXMLOperator("/org/beiyi/wt4/mapping/Icu.Mapping.xml"));
	}

	public static MappingXMLOperator getMappingXMLOperator(String name){
		return map.get(name); 
	}
}
