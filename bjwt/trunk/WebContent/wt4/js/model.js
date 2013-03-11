var Disabled = $.ModelValidator.definedModel({
	AEN01 : {
		validators : [ "number[0]", "min[1]", "max[9999]" ]
	},
	AEN02C : {
		validators : [ "fixedin[dic.ICD10,'code']" ]
	},
	AEN02N : {
		validators : [ "fixedin[dic.ICD10,'name']" ]
	}
});

var Operation = $.ModelValidator.definedModel({
	ACA0901C : {
		validators : [ "required", "fixedin[dic.ICD9,'code']" ]
	},
	ACA0901N : {
		validators : [ "required", "fixedin[dic.ICD9,'name']" ]
	},
	ACA0902C : {
		validators : [ "required", "fixedin[dic.bool]" ]
	},
	ACA0903C : {
		validators : [ "required", "fixedin[dic.bool]" ]
	}
});

var ICU = $.ModelValidator.definedModel({
	AEK01C : {
		validators : [ "required", "fixedin[dic.ICU]" ]
	},
	AEK02 : {
		validators : [ "required", "datetime['yyyy-MM-dd HH:mm:ss']" ]
	},
	AEK03 : {
		validators : [ "required", "datetime['yyyy-MM-dd HH:mm:ss']" ]
	}
});

var Diagnose = $.ModelValidator.definedModel({
	ABD01C : {
		validators : [ "required", "fixedin[dic.ICD10,'code']" ]
	},
	ABD01N : {
		validators : [ "required", "fixedin[dic.ICD10,'name']" ]
	},
	ABD03C : {
		validators : [ "required", "fixedin[dic.ruYuanBingQing]" ]
	}
});

var Surgery = $.ModelValidator.definedModel({
	ACA01 : {
		validators : [ "required", "datetime['yyyy-MM-dd HH:mm:ss']" ]
	},
	ACA11 : {
		validators : [ "required", "datetime['yyyy-MM-dd HH:mm:ss']" ]
	},
	ACA02 : {
		validators : [ "required", "length[0,20]" ]
	},
	ACA03 : {
		validators : [ "length[0,20]" ]
	},
	ACA04 : {
		validators : [ "length[0,20]" ]
	},
	ACA06C : {
		validators : [ "fixedin[dic.maZuiFangShi]" ]
	},
	ACA07C : {
		validators : [ "fixedin[dic.qieKouYuHe]" ]
	},
	ACA08 : {
		validators : [ "length[0,20]" ]
	},
	ACA10C : {
		validators : [ "required", "fixedin[dic.surgeryLevel]" ]
	},
	ACA09S : {
		defaultValue : new Array(),
		validators : [{
			rule : "func",
			param : [function(field, value, element, param){
				if(!value){
					return true;
				}
				if($.isArray(value)){
					var valid = true;
					for(var i=0,len=value.length;i<len;i++){
						if(Operation.validate(value[i]).length>0){
							valid = false;
							break;
						}
					}
					return valid;
				}else{
					return false;
				}
			}]
		}]
	}
});

var MedicalRecord = $.ModelValidator.definedModel({
	id : {
		validators : []
	},
	ABDS : {
		defaultValue : new Array(),
		validators : [{
			rule : "func",
			param : [function(field, value, element, param){
				if(!value){
					return true;
				}
				if($.isArray(value)){
					var valid = true;
					for(var i=0,len=value.length;i<len;i++){
						if(Diagnose.validate(value[i]).length>0){
							valid = false;
							break;
						}
					}
					return valid;
				}else{
					return false;
				}
			}]
		}]
	},
	ACAS : {
		defaultValue : [new Surgery()],
		validators : [{
			rule : "func",
			param : [function(field, value, element, param){
				if(!value){
					return true;
				}
				if($.isArray(value)){
					var valid = true;
					for(var i=0,len=value.length;i<len;i++){
						if(Surgery.validate(value[i]).length>0){
							valid = false;
							break;
						}
					}
					return valid;
				}else{
					return false;
				}
			}]
		}]
	},
	AEKS : {
		defaultValue : new Array(),
		validators : [{
			rule : "func",
			param : [function(field, value, element, param){
				if(!value){
					return true;
				}
				if($.isArray(value)){
					var valid = true;
					for(var i=0,len=value.length;i<len;i++){
						if(ICU.validate(value[i]).length>0){
							valid = false;
							break;
						}
					}
					return valid;
				}else{
					return false;
				}
			}]
		}]
	},
	AENS : {
		defaultValue : new Array(),
		validators : [{
			rule : "func",
			param : [function(field, value, element, param){
				if(!value){
					return true;
				}
				if($.isArray(value)){
					var valid = true;
					for(var i=0,len=value.length;i<len;i++){
						if(Disabled.validate(value[i]).length>0){
							valid = false;
							break;
						}
					}
					return valid;
				}else{
					return false;
				}
			}]
		}]
	},
	AAA28 : {
		validators : [ "required", "length[0,20]" ]
	},
	AAA01 : {
		validators : [ "required", "length[0,50]" ]
	},
	AAA02C : {
		validators : [ "required", "fixedin[dic.sex]" ]
	},
	AAA03 : {
		validators : [ "required", "date['yyyy-MM-dd']" ]
	},
	AAA04 : {
		validators : [ "number[0]", "min[0]", "max[999]" ]
	},
	AAA05C : {
		validators : [ "fixedin[dic.country]" ]
	},
	AAA40 : {
		validators : [ "number[0]", "min[1]", "max[365]" ]
	},
	AAA42 : {
		validators : [ "number[0]", "min[1]", "max[9999]" ] // TODO 年龄天数AAA40小于28天时，新生儿体重必填
	},
	AAA06C : {
		validators : [ "fixedin[dic.nation]" ]
	},
	AAA07 : {
		validators : [ "length[18,18]" ]
	},
	AAA08C : {
		validators : [ "required", "fixedin[dic.maritalStaus]" ]
	},
	AAA16C : {
		validators : [ "required", "fixedin[dic.area]" ]
	},
	AAA18C : {
		validators : [ "required", "fixedin[dic.profession]" ]
	},
	AAA19 : {
		validators : [ "length[0,200]" ]
	},
	AAA20 : {
		validators : [ "length[0,20]" ]
	},
	AAA21C : {
		validators : [ "number[0]", "min[100000]", "max[999999]" ]
	},
	AAA22 : {
		validators : [ "length[0,20]" ]
	},
	AAA23C : {
		validators : [ "fixedin[dic.relationship]" ]
	},
	AAA24 : {
		validators : [ "length[0,200]" ]
	},
	AAA25 : {
		validators : [ "length[0,20]" ]
	},
	AAA26C : {
		validators : [ "required", "fixedin[dic.paymentMethod]" ]
	},
	AAA27 : {
		validators : [ "length[0,40]" ]
	},
	AAA29 : {
		validators : [ "required", "number[0]", "min[1]", "max[9999]" ]
	},
	AAA09 : {
		validators : [ "fixedin[dic.address,'text']" ]
	},
	AAA10 : {
		//validators : [ "fixedin['AAA10']" ]
	},
	AAA11 : {
		//validators : [ "fixedin['AAA11']" ] TODO
	},
	AAA43 : {
		//validators : [ "fixedin['AAA43']" ]
	},
	AAA44 : {
		//validators : [ "fixedin['AAA44']" ]
	},
	AAA45 : {
		//validators : [ "fixedin['AAA45']" ]
	},
	AAA46 : {
		//validators : [ "fixedin['AAA46']" ]
	},
	AAA47 : {
		//validators : [ "fixedin['AAA47']" ]
	},
	AAA13C : {
		validators : [ "fixedin[dic.area]" ]
	},
	AAA33C : {
		validators : [ "fixedin[dic.streat,'code']" ]
	},
	AAA14C : {
		validators : [ "number[0]", "min[100000]", "max[999999]" ]
	},
	AAA48 : {
		validators : [ "length[0,50]" ]
	},
	AAA49 : {
		validators : [ "length[0,50]" ]
	},
	AAA50 : {
		validators : [ "length[0,50]" ]
	},
	AAA36C : {
		validators : [ "fixedin[dic.streat,'code']" ]
	},
	AAA51 : {
		validators : [ "length[0,20]" ]
	},
	AAA17C : {
		validators : [ "number[0]", "min[100000]", "max[999999]" ]
	},
	AAB01 : {
		validators : [ "required", "datebefore['#AAC01','出院时间']",//TODO
				"datetime['yyyy-MM-dd HH:mm:ss']" ]
	},
	AAB02C : {
		validators : [ "required", "fixedin[dic.medicalSubject]" ]
	},
	AAB03 : {
		validators : [ "length[0,20]" ]
	},
	AAB06C : {
		validators : [ "required", "fixedin[dic.ruYuanTuJing]" ]
	},
	AAC01 : {
		validators : [ "required", "dateafter['#AAB01','入院时间']",//TODO
				"datetime['yyyy-MM-dd HH:mm:ss']" ]
	},
	AAC02C : {
		validators : [ "required", "fixedin[dic.medicalSubject]" ]
	},
	AAC03 : {
		validators : [ "length[0,20]" ]
	},
	AAC04 : {
		validators : [ "required", "number[0]", "min[1]", "max[999999]" ]
	},
	AAD01C : {
		//validators : [ "fixedin[dic.medicalSubject]" ] TODO 多选
	},
	AEE01 : {
		validators : [ "length[0,20]" ]
	},
	AEE02 : {
		validators : [ "required", "length[0,20]" ]
	},
	AEE03 : {
		validators : [ "required", "length[0,20]" ]
	},
	AEE11 : {
		validators : [ "length[0,16]" ]
	},
	AEE09 : {
		validators : [ "length[0,20]" ]
	},
	AEE04 : {
		validators : [ "length[0,20]" ]
	},
	AEE05 : {
		validators : [ "length[0,20]" ]
	},
	AEE07 : {
		validators : [ "length[0,20]" ]
	},
	AEE08 : {
		validators : [ "length[0,20]" ]
	},
	AEE10 : {
		validators : [ "length[0,20]" ]
	},
	ABA01C : {
		validators : [ "fixedin[dic.ICD10,'code']" ]
	},
	ABA01N : {
		validators : [ "fixedin[dic.ICD10,'name']" ]
	},
	ABC01C : {
		validators : [ "required", "fixedin[dic.ICD10,'code']" ]
	},
	ABC01N : {
		validators : [ "required", "fixedin[dic.ICD10,'name']" ]
	},
	ABC03C : {
		validators : [ "required", "fixedin[dic.ruYuanBingQing]" ]
	},
	ABF01C : {
		validators : [ "fixedin[dic.BLZD,'code']" ]
	},
	ABF01N : {
		validators : [ "fixedin[dic.BLZD,'name']" ]
	},
	ABF04 : {
		validators : [ "length[0,50]" ]
	},
	ABF02C : {
		validators : [ "fixedin[dic.diagnosticEvidence]" ]
	},
	ABF03C : {
		validators : [ "fixedin[dic.fenHuaChengDu]" ]
	},
	ABG01C : {
		validators : [ "fixedin[dic.ICD10,'code']" ]
	},
	ABG01N : {
		validators : [ "fixedin[dic.ICD10,'name']" ]
	},
	ABH01C : {
		validators : [ "fixedin[dic.bool]" ]
	},
	ABH0201C : {
		validators : [ "fixedin[dic.TNM_T]" ]
	},
	ABH0202C : {
		validators : [ "fixedin[dic.TNM_N]" ]
	},
	ABH0203C : {
		validators : [ "fixedin[dic.TNM_M]" ]
	},
	ABH03C : {
		validators : [ "fixedin[dic.zhongLiuFenQi]" ]
	},
	AEB02C : {
		validators : [ "required", "fixedin[dic.haveOrNot]" ]
	},
	AEB01 : {
		validators : [ "length[0,200]" ]
	},
	AED01C : {
		validators : [ "fixedin[dic.binAnZhiLiang]" ]
	},
	AED02 : {
		validators : [ "length[0,20]" ]
	},
	AED03 : {
		validators : [ "length[0,20]" ]
	},
	AED04 : {
		validators : [ "date['yyyy-MM-dd']" ]
	},
	AEG01C : {
		validators : [ "fixedin[dic.bloodType]" ]
	},
	AEG02C : {
		validators : [ "fixedin[dic.Rh]" ]
	},
	AEG04 : {
		validators : [ "number[0]", "min[1]", "max[999999]" ]
	},
	AEG05 : {
		validators : [ "number[0]", "min[1]", "max[999999]" ]
	},
	AEG06 : {
		validators : [ "number[0]", "min[1]", "max[999999]" ]
	},
	AEG07 : {
		validators : [ "number[0]", "min[1]", "max[999999]" ]
	},
	AEG08 : {
		validators : [ "number[0]", "min[1]", "max[999999]" ]
	},
	AEJ01 : {
		validators : [ "number[0]", "min[0]", "max[99999]" ]
	},
	AEJ02 : {
		validators : [ "number[0]", "min[0]" ]
	},
	AEJ03 : {
		validators : [ "number[0]", "min[0]" ]
	},
	AEJ04 : {
		validators : [ "number[0]", "min[0]", "max[99999]" ]
	},
	AEJ05 : {
		validators : [ "number[0]", "min[0]", "max[23]" ]
	},
	AEJ06 : {
		validators : [ "number[0]", "min[0]", "max[59]" ]
	},
	AEL01 : {
		validators : [ "number[0]", "min[1]", "max[999999]" ]
	},
	AEM01C : {
		validators : [ "required", "fixedin[dic.liYuanFangShi]" ]
	},
	AEM02 : {
		validators : [ "length[0,100]" ]
	},
	AEM03C : {
		validators : [ "required", "fixedin[dic.bool]" ]
	},
	AEM04 :{
		validators : [ "length[0,500]" ]
	},
	AEI01C : {
		validators : [ "fixedin[dic.bool]" ]
	},
	AEI09 : {
		validators : [ "number[0]", "min[0]", "max[100]" ]
	},
	AEI10 : {
		validators : [ "number[0]", "min[0]", "max[100]" ]
	},
	AEI08 :{
		validators : [ "length[0,500]" ]
	},
	ADA01 : {
		validators : [ "required", "number[2]", "min[0.01]" ]
	},
	ADA0101 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA11 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA21 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA22 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA23 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA24 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA25 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA26 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA27 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA28 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA13 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA15 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA12 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA29 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA03 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA30 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA31 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA32 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA07 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA08 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA33 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA34 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA35 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA36 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA37 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA38 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA02 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA39 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA09 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA10 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA04 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA40 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA41 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA42 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA43 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA44 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA05 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA06 : {
		validators : [ "number[2]", "min[0]" ]
	},
	ADA20 : {
		validators : [ "number[2]", "min[0]" ]
	}
});

MedicalRecord.parse = function (node){
	var obj = new MedicalRecord();
	for (var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
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
	//验证病案数据
	obj.valid = obj.validate();
	return obj;
};
MedicalRecord.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(MedicalRecord.XmlTemplate);
	var xmlNode = $(doc);
	for (var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
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
Diagnose.parse = function(node){
	var obj = new Diagnose();
	for(var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
		obj[tag]=getNodeValue(node, tag);
	}
	return obj;
};
Diagnose.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(Diagnose.XmlTemplate);
	var xmlNode = $(doc);
	for (var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
		var node=xmlNode.find(tag).first();
		node.text(obj[tag]);
	}
	removeEmptyNode(doc);
	return doc;
};
Operation.parse = function(node){
	var obj = new Operation();
	for(var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
		obj[tag]=getNodeValue(node, tag);
	}
	return obj;
};
Operation.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(Operation.XmlTemplate);
	var xmlNode = $(doc);
	for (var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
		var node=xmlNode.find(tag).first();
		node.text(obj[tag]);
	}
	removeEmptyNode(doc);
	return doc;
};
Surgery.parse = function(node){
	var obj = new Surgery();
	for(var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
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
	var doc = XmlUtils.loadXmlString(Surgery.XmlTemplate);
	var xmlNode = $(doc);
	for (var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
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
ICU.parse = function(node){
	var obj = new ICU();
	for(var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
		obj[tag]=getNodeValue(node, tag);
	}
	return obj;
};
ICU.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(ICU.XmlTemplate);
	var xmlNode = $(doc);
	for (var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
		var node=xmlNode.find(tag).first();
		node.text(obj[tag]);
	}
	removeEmptyNode(doc);
	return doc;
};
Disabled.parse = function(node){
	var obj = new Disabled();
	for(var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
		obj[tag]=getNodeValue(node, tag);
	}
	return obj;
};
Disabled.toXmlDoc = function(obj){
	var doc = XmlUtils.loadXmlString(Disabled.XmlTemplate);
	var xmlNode = $(doc);
	for (var tag in obj){
		//只遍历自身属性
		if(!obj.hasOwnProperty(tag)){
			continue;
		}
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
/**
 * 删除xml中的空节点
 * @param root xml元素节点
 */
function removeEmptyNode(root){
	$(root).children().each(function(){
		removeEmptyNode(this);
	});
	if(!$.trim($(root).text())){
		$(root).remove();
	}
}

MedicalRecord.XmlTemplate = "<CASE><A><AA><AAA><AAA01/><AAA02C/><AAA03/><AAA04/><AAA05C/><AAA40/><AAA42/><AAA06C/><AAA07/><AAA08C/><AAA09/><AAA10/><AAA11/><AAA43/><AAA44/><AAA45/><AAA46/><AAA47/><AAA13C/><AAA33C/><AAA14C/><AAA48/><AAA49/><AAA50/><AAA16C/><AAA36C/><AAA51/><AAA17C/><AAA18C/><AAA19/><AAA20/><AAA21C/><AAA22/><AAA23C/><AAA24/><AAA25/><AAA26C/><AAA27/><AAA28/><AAA29/></AAA><AAB><AAB01/><AAB02C/><AAB03/><AAB06C/></AAB><AAC><AAC01/><AAC02C/><AAC03/><AAC04/></AAC><AAD01C/></AA><AB><ABA><ABA01C/><ABA01N/></ABA><ABC><ABC01C/><ABC01N/><ABC03C/></ABC><ABDS/><ABF><ABF01C/><ABF01N/><ABF04/><ABF02C/><ABF03C/></ABF><ABG><ABG01C/><ABG01N/></ABG><ABH><ABH01C/><ABH0201C/><ABH0202C/><ABH0203C/><ABH03C/></ABH></AB><AC><ACAS/></AC><AE><AEB><AEB02C/><AEB01/></AEB><AED><AED01C/><AED02/><AED03/><AED04/></AED><AEE><AEE01/><AEE02/><AEE03/><AEE11/><AEE09/><AEE04/><AEE05/><AEE07/><AEE08/><AEE10/></AEE><AEG><AEG01C/><AEG02C/><AEG04/><AEG05/><AEG06/><AEG07/><AEG08/></AEG><AEJ><AEJ01/><AEJ02/><AEJ03/><AEJ04/><AEJ05/><AEJ06/></AEJ><AEKS/><AEL01/><AENS/><AEM><AEM01C/><AEM02/><AEM03C/><AEM04/></AEM><AEI><AEI01C/><AEI09/><AEI10/><AEI08/></AEI></AE><AD><ADA><ADA01/><ADA0101/><ADA11/><ADA21/><ADA22/><ADA23/><ADA24/><ADA25/><ADA26/><ADA27/><ADA28/><ADA13/><ADA15/><ADA12/><ADA29/><ADA03/><ADA30/><ADA31/><ADA32/><ADA07/><ADA08/><ADA33/><ADA34/><ADA35/><ADA36/><ADA37/><ADA38/><ADA02/><ADA39/><ADA09/><ADA10/><ADA04/><ADA40/><ADA41/><ADA42/><ADA43/><ADA44/><ADA05/><ADA06/><ADA20/></ADA></AD></A></CASE>";
Disabled.XmlTemplate = "<AEN><AEN01/><AEN02C/><AEN02N/></AEN>";
Diagnose.XmlTemplate = "<ABD><ABD01C/><ABD01N/><ABD03C/></ABD>";
ICU.XmlTemplate = "<AEK><AEK01C/><AEK02/><AEK03/></AEK>";
Operation.XmlTemplate = "<ACA09><ACA0901C/><ACA0901N/><ACA0902C/><ACA0903C/></ACA09>";
Surgery.XmlTemplate = "<ACA><ACA01/><ACA11/><ACA02/><ACA03/><ACA04/><ACA06C/><ACA07C/><ACA08/><ACA10C/><ACA09S/></ACA>";