function loadXMLDoc(xmlPath) {
	var xmlDoc = null;
	try {
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	} catch (e) {
		try {
			xmlDoc = document.implementation.createDocument("", "", null);
		} catch (e) {
			alert("xmlDoc创建失败：" + e.message);
		}
	}
	try {
		xmlDoc.async = false;
		xmlDoc.load(xmlPath);
		return xmlDoc;
	} catch (e) {
		alert("xml加载失败:" + e.message);
		return null;
	}

}

function getValue(root, tagName) {
	var nodes = root.getElementsByTagName(tagName);
	if (nodes.length != 0) {
		var child = nodes[0].childNodes[0];
		if(child){			
			return child.nodeValue;
		}
	}
	return null;
}

function parseMedicalRecordXML(path){
	var xmlDoc = loadXMLDoc(path);
    var cases = xmlDoc.getElementsByTagName("CASE");
    var recordList = new Object();
    var index = 0;
    for(var i=0; i<cases.length; i++){
    	var record = MedicalRecord.parse(cases[i]);
    	recordList[index] = record;
    }
    return recordList;
}
