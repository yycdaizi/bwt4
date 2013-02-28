function MedicalRecord() {
	this.id = null;
	var tags = [ /*'ZA01C', 'ZA02C', 'ZA03', 'ZA04', 'ZB01C', 'ZB02', 'ZB03',
			'ZB04', 'ZB05', 'ZB06', 'ZB07', 'ZB08', 'ZB09',*/ 'AAA01', 'AAA02C',
			'AAA03', 'AAA04', 'AAA05C', 'AAA40', 'AAA42', 'AAA06C', 'AAA07',
			'AAA08C', 'AAA09', 'AAA10', 'AAA11', 'AAA43', 'AAA44', 'AAA45',
			'AAA46', 'AAA47', 'AAA13C', 'AAA33C', 'AAA14C', 'AAA48', 'AAA49',
			'AAA50', 'AAA16C', 'AAA36C', 'AAA51', 'AAA17C', 'AAA18C', 'AAA19',
			'AAA20', 'AAA21C', 'AAA22', 'AAA23C', 'AAA24', 'AAA25', 'AAA26C',
			'AAA27', 'AAA28', 'AAA29', 'AAB01', 'AAB02C', 'AAB03', 'AAB06C',
			'AAC01', 'AAC02C', 'AAC03', 'AAC04', 'AAD01C', 'ABA01C', 'ABA01N',
			'ABC01C', 'ABC01N', 'ABC03C', /*'ABD01C', 'ABD01N', 'ABD03C',*/
			'ABF01C', 'ABF01N', 'ABF04', 'ABF02C', 'ABF03C', 'ABG01C',
			'ABG01N', 'ABH01C', 'ABH0201C', 'ABH0202C', 'ABH0203C', 'ABH03C',
			/*'ACA01', 'ACA11', 'ACA02', 'ACA03', 'ACA04', 'ACA06C', 'ACA07C',
			'ACA08', 'ACA10C', 'ACA0901C', 'ACA0901N', 'ACA0902C', 'ACA0903C',*/
			'AEB02C', 'AEB01', 'AED01C', 'AED02', 'AED03', 'AED04', 'AEE01',
			'AEE02', 'AEE03', 'AEE11', 'AEE09', 'AEE04', 'AEE05', 'AEE07',
			'AEE08', 'AEE10', 'AEG01C', 'AEG02C', 'AEG04', 'AEG05', 'AEG06',
			'AEG07', 'AEG08', 'AEJ01', 'AEJ02', 'AEJ03', 'AEJ04', 'AEJ05',
			'AEJ06', /*'AEK01C', 'AEK02', 'AEK03',*/ 'AEL01', /*'AEN01', 'AEN02C',
			'AEN02N',*/ 'AEM01C', 'AEM02', 'AEM03C', 'AEM04', 'AEI01C', 'AEI09',
			'AEI10', 'AEI08', 'ADA01', 'ADA0101', 'ADA11', 'ADA21', 'ADA22',
			'ADA23', 'ADA24', 'ADA25', 'ADA26', 'ADA27', 'ADA28', 'ADA13',
			'ADA15', 'ADA12', 'ADA29', 'ADA03', 'ADA30', 'ADA31', 'ADA32',
			'ADA07', 'ADA08', 'ADA33', 'ADA34', 'ADA35', 'ADA36', 'ADA37',
			'ADA38', 'ADA02', 'ADA39', 'ADA09', 'ADA10', 'ADA04', 'ADA40',
			'ADA41', 'ADA42', 'ADA43', 'ADA44', 'ADA05', 'ADA06', 'ADA20' ];

	for(var i=0; i<tags.length; i++){
		this[tags[i]] = null;
	}
	
	//出院其他诊断集合
	this.ABDS = new Array();
	//手术情况集合
	this.ACAS = new Array();
	this.ACAS.push(new Surgery());
	//重症监护集合
	this.AEKS = new Array();
	//新生儿情况集合
	this.AENS = new Array();
}

MedicalRecord.parse = function (node){
	var obj = new MedicalRecord();
	for (var tag in obj){
		switch(tag){
		//出院其他诊断集合
		case 'ABDS':
			obj[tag] = getNodeValues(node, tag, Diagnose);
			break;
		//手术情况集合
		case 'ACAS':
			obj[tag] = getNodeValues(node, tag, Surgery);
			break;
		//重症监护集合
		case 'AEKS':
			obj[tag] = getNodeValues(node, tag, ICU);
			break;
		//新生儿情况集合
		case 'AENS':
			obj[tag] = getNodeValues(node, tag, Disabled);
			break;
		default:
			obj[tag]=getNodeValue(node, tag);
		}
	}
	return obj;
};
MedicalRecord.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(XmlTemplate.MedicalRecord);
	var xmlNode = $(doc);
	for (var tag in obj){
		var node=xmlNode.find(tag).first();
		switch(tag){
		//出院其他诊断集合
		case 'ABDS':
			var list = obj[tag];
			if(list){
				for(var i=0;i<list.length;i++){
					node.append($(Diagnose.toXmlDoc(list[i])).children());
				}
			}
			break;
		//手术情况集合
		case 'ACAS':
			var list = obj[tag];
			if(list){
				for(var i=0;i<list.length;i++){
					node.append($(Surgery.toXmlDoc(list[i])).children());
				}
			}
			break;
		//重症监护集合
		case 'AEKS':
			var list = obj[tag];
			if(list){
				for(var i=0;i<list.length;i++){
					node.append($(ICU.toXmlDoc(list[i])).children());
				}
			}
			break;
		//新生儿情况集合
		case 'AENS':
			var list = obj[tag];
			if(list){
				for(var i=0;i<list.length;i++){
					node.append($(Disabled.toXmlDoc(list[i])).children());
				}
			}
			break;
		default:
			node.text(obj[tag]);
		}
	}
	removeEmptyNode(doc);
	return doc;
};

function Diagnose(){
	this.ABD01C = null;
	this.ABD01N = null;
	this.ABD03C = null;
}
Diagnose.parse = function(node){
	var obj = new Diagnose();
	for(var tag in obj){
		obj[tag]=getNodeValue(node, tag);
	}
	return obj;
};
Diagnose.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(XmlTemplate.Diagnose);
	var xmlNode = $(doc);
	for (var tag in obj){
		var node=xmlNode.find(tag).first();
		node.text(obj[tag]);
	}
	removeEmptyNode(doc);
	return doc;
};

function Operation(){
	this.ACA0901C = null;
	this.ACA0901N = null;
	this.ACA0902C = null;
	this.ACA0903C = null;
}
Operation.parse = function(node){
	var obj = new Operation();
	for(var tag in obj){
		obj[tag]=getNodeValue(node, tag);
	}
	return obj;
};
Operation.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(XmlTemplate.Operation);
	var xmlNode = $(doc);
	for (var tag in obj){
		var node=xmlNode.find(tag).first();
		node.text(obj[tag]);
	}
	removeEmptyNode(doc);
	return doc;
};
function Surgery(){
	this.ACA01 = null;
	this.ACA11 = null;
	this.ACA02 = null;
	this.ACA03 = null;
	this.ACA04 = null;
	this.ACA06C = null;
	this.ACA07C = null;
	this.ACA08 = null;
	this.ACA10C = null;
	//术式信息集合
	this.ACA09S = new Array();
}
Surgery.parse = function(node){
	var obj = new Surgery();
	for(var tag in obj){
		if('ACA09S' == tag){
			obj[tag] = getNodeValues(node, tag, Operation);
		}else{
			obj[tag]=getNodeValue(node, tag);
		}
	}
	//若手术级别为空，则默认为 0-操作
	if(!obj['ACA10C']){
		obj['ACA10C'] = '0';
	}
	return obj;
};
Surgery.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(XmlTemplate.Surgery);
	var xmlNode = $(doc);
	for (var tag in obj){
		var node=xmlNode.find(tag).first();
		if('ACA09S' == tag){
			var list = obj[tag];
			if(list){
				for(var i=0;i<list.length;i++){
					node.append($(Operation.toXmlDoc(list[i])).children());
				}
			}
		}else{
			node.text(obj[tag]);
		}
	}
	removeEmptyNode(doc);
	return doc;
};
function ICU(){
	this.AEK01C = null;
	this.AEK02 = null;
	this.AEK03 = null;
}
ICU.parse = function(node){
	var obj = new ICU();
	for(var tag in obj){
		obj[tag]=getNodeValue(node, tag);
	}
	return obj;
};
ICU.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(XmlTemplate.ICU);
	var xmlNode = $(doc);
	for (var tag in obj){
		var node=xmlNode.find(tag).first();
		node.text(obj[tag]);
	}
	removeEmptyNode(doc);
	return doc;
};
function Disabled(){
	this.AEN01 = null;
	this.AEN02C = null;
	this.AEN02N = null;
}
Disabled.parse = function(node){
	var obj = new Disabled();
	for(var tag in obj){
		obj[tag]=getNodeValue(node, tag);
	}
	return obj;
};
Disabled.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(XmlTemplate.Disabled);
	var xmlNode = $(doc);
	for (var tag in obj){
		var node=xmlNode.find(tag).first();
		node.text(obj[tag]);
	}
	removeEmptyNode(doc);
	return doc;
};

function getNodeValue(root, tag){
	var value = $(root).find(tag).first().text();
	return $.trim(value);
}
function getNodeValues(root, tag, model){
	var res = [];
	$(root).find(tag).first().children().each(function(){
		res.push(model.parse(this));
	});
	return res;
}
function removeEmptyNode(root){
	$(root).children().each(function(){
		removeEmptyNode(this);
	});
	if(!$.trim($(root).text())){
		$(root).remove();
	}
}