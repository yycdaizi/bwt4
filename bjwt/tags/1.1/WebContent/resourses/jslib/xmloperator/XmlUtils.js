var XmlUtils = {
	isIE: !+"/v1",

	/**
	 * 创建XMLDOM对象
	 * @returns {Document}
	 */
	createDocument : function(){
		if (this.isIE) {
			return new ActiveXObject("Microsoft.XMLDOM");
		} else {
			return document.implementation.createDocument("", "", null);
		}
	},
	/**
	 * 加载xml文件
	 * @param {String} xmlPath ： xml文件路径
	 * @returns {Document}
	 * @throws
	 */
	loadXml : function(xmlPath) {
		var xmlDoc = this.createDocument();
		xmlDoc.async = false;
		xmlDoc.load(xmlPath);
		return xmlDoc;
	},
	/**
	 * 加载XML字符串
	 * @param {String} xmlString ： xml字符串
	 * @returns {Document}
	 */
	loadXmlString : function(xmlString) {
		if (this.isIE) {
			var xmlDoc = this.createDocument();
			xmlDoc.loadXML(xmlString);
			return xmlDoc;
		} else {
			var parser = new DOMParser();
			return parser.parseFromString(xmlString, "text/xml");
		}
	},
	/** 
	 * 输出xml，参数： 
	 * @param {Object} node：要输出的节点 
	 * @returns {String}
	 */
	toXmlString : function(node) {
		if(!node){
			return "";
		}
		if (typeof node == 'string')
			return node;
		return this.isIE ? node.xml : new XMLSerializer()
				.serializeToString(node);
	}
};