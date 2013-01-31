function MedicalRecord() {
	this.id = null;
//	this.ZA01C = null;
//	this.ZA02C = null;
//	this.ZA03 = null;
//	this.ZA04 = null;
//	this.ZB01C = null;
//	this.ZB02 = null;
//	this.ZB03 = null;
//	this.ZB04 = null;
//	this.ZB05 = null;
//	this.ZB06 = null;
//	this.ZB07 = null;
//	this.ZB08 = null;
//	this.ZB09 = null;
	this.AAA01 = null;
	this.AAA02C = null;
	this.AAA03 = null;
	this.AAA04 = null;
	this.AAA05C = null;
	this.AAA40 = null;
	this.AAA42 = null;
	this.AAA06C = null;
	this.AAA07 = null;
	this.AAA08C = null;
	this.AAA09 = null;
	this.AAA10 = null;
	this.AAA11 = null;
	this.AAA43 = null;
	this.AAA44 = null;
	this.AAA45 = null;
	this.AAA46 = null;
	this.AAA47 = null;
	this.AAA13C = null;
	this.AAA33C = null;
	this.AAA14C = null;
	this.AAA48 = null;
	this.AAA49 = null;
	this.AAA50 = null;
	this.AAA16C = null;
	this.AAA36C = null;
	this.AAA51 = null;
	this.AAA17C = null;
	this.AAA18C = null;
	this.AAA19 = null;
	this.AAA20 = null;
	this.AAA21C = null;
	this.AAA22 = null;
	this.AAA23C = null;
	this.AAA24 = null;
	this.AAA25 = null;
	this.AAA26C = null;
	this.AAA27 = null;
	this.AAA28 = null;
	this.AAA29 = null;
	this.AAB01 = null;
	this.AAB02C = null;
	this.AAB03 = null;
	this.AAB06C = null;
	this.AAC01 = null;
	this.AAC02C = null;
	this.AAC03 = null;
	this.AAC04 = null;
	//转经科别代码
	this.AAD01C = null;
	this.ABA01C = null;
	this.ABA01N = null;
	this.ABC01C = null;
	this.ABC01N = null;
	this.ABC03C = null;
	
	//出院其他诊断集合
	this.ABDS = new Array();
//	ABD01C : null;
//	ABD01N : null;
//	ABD03C : null;
	
	this.ABF01C = null;
	this.ABF01N = null;
	this.ABF04 = null;
	this.ABF02C = null;
	this.ABF03C = null;
	this.ABG01C = null;
	this.ABG01N = null;
	this.ABH01C = null;
	this.ABH0201C = null;
	this.ABH0202C = null;
	this.ABH0203C = null;
	this.ABH03C = null;
	
	//手术情况集合
	this.ACAS = new Array();
	this.ACAS.push(new Surgery());
//	this.ACA01 = null;
//	this.ACA11 = null;
//	this.ACA02 = null;
//	this.ACA03 = null;
//	this.ACA04 = null;
//	this.ACA06C = null;
//	this.ACA07C = null;
//	this.ACA08 = null;
//	this.ACA10C = null;
	
//	this.ACA0901C = null;
//	this.ACA0901N = null;
//	this.ACA0902C = null;
//	this.ACA0903C = null;
	
	this.AEB02C = null;
	this.AEB01 = null;
	this.AED01C = null;
	this.AED02 = null;
	this.AED03 = null;
	this.AED04 = null;
	this.AEE01 = null;
	this.AEE02 = null;
	this.AEE03 = null;
	this.AEE11 = null;
	this.AEE09 = null;
	this.AEE04 = null;
	this.AEE05 = null;
	this.AEE07 = null;
	this.AEE08 = null;
	this.AEE10 = null;
	this.AEG01C = null;
	this.AEG02C = null;
	this.AEG04 = null;
	this.AEG05 = null;
	this.AEG06 = null;
	this.AEG07 = null;
	this.AEG08 = null;
	this.AEJ01 = null;
	this.AEJ02 = null;
	this.AEJ03 = null;
	this.AEJ04 = null;
	this.AEJ05 = null;
	this.AEJ06 = null;
	
	//重症监护集合
	this.AEKS = new Array();
//	this.AEK01C = null;
//	this.AEK02 = null;
//	this.AEK03 = null;
	
	this.AEL01 = null;
	
	//新生儿情况集合
	this.AENS = new Array();
//	this.AEN01 = null;
//	this.AEN02C = null;
//	this.AEN02N = null;
	
	this.AEM01C = null;
	this.AEM02 = null;
	this.AEM03C = null;
	this.AEM04 = null;
	this.AEI01C = null;
	this.AEI09 = null;
	this.AEI10 = null;
	this.AEI08 = null;
	this.ADA01 = null;
	this.ADA0101 = null;
	this.ADA11 = null;
	this.ADA21 = null;
	this.ADA22 = null;
	this.ADA23 = null;
	this.ADA24 = null;
	this.ADA25 = null;
	this.ADA26 = null;
	this.ADA27 = null;
	this.ADA28 = null;
	this.ADA13 = null;
	this.ADA15 = null;
	this.ADA12 = null;
	this.ADA29 = null;
	this.ADA03 = null;
	this.ADA30 = null;
	this.ADA31 = null;
	this.ADA32 = null;
	this.ADA07 = null;
	this.ADA08 = null;
	this.ADA33 = null;
	this.ADA34 = null;
	this.ADA35 = null;
	this.ADA36 = null;
	this.ADA37 = null;
	this.ADA38 = null;
	this.ADA02 = null;
	this.ADA39 = null;
	this.ADA09 = null;
	this.ADA10 = null;
	this.ADA04 = null;
	this.ADA40 = null;
	this.ADA41 = null;
	this.ADA42 = null;
	this.ADA43 = null;
	this.ADA44 = null;
	this.ADA05 = null;
	this.ADA06 = null;
	this.ADA20 = null;
}

MedicalRecord.parse = function (node){
	var obj = new MedicalRecord();
	for (var tag in obj){
		switch(tag){
		//出院其他诊断集合
		case 'ABDS':
			var tagNodes = node.getElementsByTagName(tag);
			if(tagNodes.length!=0){
				var childNodes = tagNodes[0].childNodes;
				for(var i=0; i<childNodes.length;i++){
					obj[tag][i]=Diagnose.parse(childNodes[i]);
				}
			}
			break;
		//手术情况集合
		case 'ACAS':
			var tagNodes = node.getElementsByTagName(tag);
			if(tagNodes.length!=0){
				var childNodes = tagNodes[0].childNodes;
				for(var i=0; i<childNodes.length;i++){
					obj[tag][i]=Surgery.parse(childNodes[i]);
				}
			}
			break;
		//重症监护集合
		case 'AEKS':
			var tagNodes = node.getElementsByTagName(tag);
			if(tagNodes.length!=0){
				var childNodes = tagNodes[0].childNodes;
				for(var i=0; i<childNodes.length;i++){
					obj[tag][i]=ICU.parse(childNodes[i]);
				}
			}
			break;
		//新生儿情况集合
		case 'AENS':
			var tagNodes = node.getElementsByTagName(tag);
			if(tagNodes.length!=0){
				var childNodes = tagNodes[0].childNodes;
				for(var i=0; i<childNodes.length;i++){
					obj[tag][i]=Disabled.parse(childNodes[i]);
				}
			}
			break;
		default:
			obj[tag]=getValue(node, tag);
		}
	}
	return obj;
};

function Diagnose(){
	this.ABD01C = null;
	this.ABD01N = null;
	this.ABD03C = null;
}
Diagnose.parse = function(node){
	var obj = new Diagnose();
	for(var tag in obj){
		obj[tag]=getValue(node, tag);
	}
	return obj;
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
		obj[tag]=getValue(node, tag);
	}
	return obj;
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
			var tagNodes = node.getElementsByTagName(tag);
			if(tagNodes.length!=0){
				var childNodes = tagNodes[0].childNodes;
				for(var i=0; i<childNodes.length;i++){
					obj[tag][i]=Operation.parse(childNodes[i]);
				}
			}
		}else{
			obj[tag]=getValue(node, tag);
		}
	}
	return obj;
};
//Surgery.clone = function(oldObj){  
//  var obj = new Surgery();
//	for(var tag in obj){
//		if('ACA09S' == tag){
//			var tagNodes = node.getElementsByTagName(tag);
//			if(tagNodes.length!=0){
//				var childNodes = tagNodes[0].childNodes;
//				for(var i=0; i<childNodes.length;i++){
//					obj[tag][i]=Operation.parse(childNodes[i]);
//				}
//			}
//		}else{
//			obj[tag]=getValue(node, tag);
//		}
//	}
//	return obj;
//}

function ICU(){
	this.AEK01C = null;
	this.AEK02 = null;
	this.AEK03 = null;
}
ICU.parse = function(node){
	var obj = new ICU();
	for(var tag in obj){
		obj[tag]=getValue(node, tag);
	}
	return obj;
};
function Disabled(){
	this.AEN01 = null;
	this.AEN02C = null;
	this.AEN02N = null;
}
Disabled.parse = function(node){
	var obj = new Disabled();
	for(var tag in obj){
		obj[tag]=getValue(node, tag);
	}
	return obj;
};
