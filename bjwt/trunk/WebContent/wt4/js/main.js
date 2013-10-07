/**
 * 病案编辑表格的操作函数
 */
var MedicalRecordForm = {};

//加载病案数据
MedicalRecordForm.loadData = function(data){
    //将填充的原始数据缓存，以免部分表单中没有的字段丢失
    $("#mainTabs").data('MRdata', data)
    
	//转经科别 特殊处理（因为输入框需要的值是数组）
	if(!data["AAD01C"]){
		data["AAD01C"]='';
	}
	$("#formTab1").form("load",data);
	$("#AAD01C").combobox("setValues",data["AAD01C"].split(","));
	$("#formTab2").form("load",data);
	
	SurgeryPanel.loadData(data["ACAS"]);
	
	$("#formTab4").form("load",data);
	$("#formTab5").form("load",data);
	
	//诊断情况
	$('#ABDS').datagrid("loadData",data["ABDS"]);
	//重症情况
	$('#AEKS').datagrid("loadData",data["AEKS"]);
	//新生儿情况
	$('#AENS').datagrid("loadData",data["AENS"]);
};

//获得表单数据
MedicalRecordForm.getData = function(){
	var obj = $("#mainTabs").data('MRdata')||new MedicalRecord();
	var fields = MedicalRecord.fields;
	for (var tag in fields){
		//只遍历自身属性
		if(!fields.hasOwnProperty(tag)){
			continue;
		}
		switch(tag){
		//手术情况集合
		case 'ACAS':
			obj[tag]=SurgeryPanel.getData();
			break;
		//出院其他诊断集合
		case 'ABDS':
			obj[tag]= $("#"+tag).datagrid('getRows');
			break;
		//重症监护集合
		case 'AEKS':
			obj[tag]= $("#"+tag).datagrid('getRows');
			break;
		//新生儿情况集合
		case 'AENS':
			obj[tag]= $("#"+tag).datagrid('getRows');
			break;
		default:
			var el = $("#"+tag);
			if(el.length!=0){
				obj[tag]=el.getValue();
			}
		}
	}
	return obj;
};

//清除表单数据
MedicalRecordForm.clear = function(){
    //清除缓存到表单的数据
    $("#mainTabs").data('MRdata', null);
    
	//诊断情况
	$('#ABDS').datagrid("loadData",[]);
	//重症情况
	$('#AEKS').datagrid("loadData",[]);
	//新生儿情况
	$('#AENS').datagrid("loadData",[]);
	
	$("#formTab1").form("clear");
	$("#formTab2").form("clear");
	
	SurgeryPanel.clear();
	
	$("#formTab4").form("clear");
	$("#formTab5").form("clear");
	
};

MedicalRecordForm.updateMainTabsValidTip = function(title, valid){
	var mainTabs = $('#mainTabs');
	if(valid){
		mainTabs.tabs('updateIcon',{title:title,iconCls:''});
	}else{
		mainTabs.tabs('updateIcon',{title:title,iconCls:'icon-warn'});
	}
};

//校验总费用
MedicalRecordForm.validExpenses = function(){
	var tags = ["ADA11","ADA21","ADA22","ADA23","ADA24","ADA25","ADA26","ADA27","ADA28","ADA13","ADA15","ADA12",
	            "ADA29","ADA03","ADA30","ADA31","ADA32","ADA07","ADA08","ADA33","ADA34","ADA35","ADA36","ADA37","ADA38","ADA02",
	            "ADA39","ADA09","ADA10","ADA04","ADA40","ADA41","ADA42","ADA43","ADA44","ADA05","ADA06","ADA20"];
	var total = parseFloat($("#ADA01").val()||0);
	var count=0;
	for(var i=0;i<tags.length;i++){
		var exp = $("#"+tags[i]).val();
		if(exp){
			count += parseFloat(exp);
		}
	}
	return (total==count);
};

/**
 * 校验form
 */
MedicalRecordForm.validate = function(){
    var isvalid1 = this.validateTab1();
    var isvalid2 = this.validateTab2();
    var isvalid3 = this.validateTab3();
    var isvalid4 = this.validateTab4();
    var isvalid5 = this.validateTab5();
    var isvalid = (isvalid1 && isvalid2 && isvalid3 && isvalid4 && isvalid5);
	return isvalid;
};

//校验tab1
MedicalRecordForm.validateTab1 = function(){
	var validform = $("#formTab1").form('validate');
	this.updateMainTabsValidTip('患者基本信息', validform);
	return validform;
};

//校验tab2
MedicalRecordForm.validateTab2 = function(){
	var validform = $("#formTab2").form('validate');
	var validgrid = $("#ABDS").datagrid('validateData');//validateGrid('ABDS');
	var valid = validform&&validgrid;
	
	this.updateMainTabsValidTip('诊断信息', valid);
	return valid;
};

//校验tab3
MedicalRecordForm.validateTab3 = function(){
	var valid= SurgeryPanel.validate();
	this.updateMainTabsValidTip('手术及操作信息', valid);
	return valid;
};

//校验tab4
MedicalRecordForm.validateTab4 = function(){
	var validform = $("#formTab4").form('validate');
	var validgrid = $("#AEKS").datagrid('validateData')&&$("#AENS").datagrid('validateData');//validateGrid('AEKS')&&validateGrid('AENS');
	var valid = validform&&validgrid;
	
	this.updateMainTabsValidTip('其他医疗信息', valid);
	return valid;
};
//校验tab5
MedicalRecordForm.validateTab5 = function(){
	var validform = $("#formTab5").form('validate');
	
	this.updateMainTabsValidTip('费用信息', validform);
	return validform;
};

//解析病案XML文件
function parseMedicalRecordXML(path){
	var xmlDoc = null;
	try {
		xmlDoc = XmlUtils.loadXml(path);
	} catch (e) {
		$.messager.alert('系统错误','XML文件导入失败！请使用IE浏览器。','error');
		return;
	}
    var cases = xmlDoc.getElementsByTagName("CASE");
    var recordList = [];
    for(var i=0; i<cases.length; i++){
    	var record = MedicalRecord.parse(cases[i]);
    	recordList.push(record);
    }
    return recordList;
}
/**
 * 将病案对象的数组导出为xml字符串
 * @param {Array[MedicalRecord]} list
 * @returns {String}
 */
function exportMedicalRecordsToXML(list){
	list = list||[];
	
	var xml = '<?xml version="1.0" encoding="UTF-8"?>';
	xml +='<CASES>';
	for(var i=0; i<list.length; i++){
		var doc = MedicalRecord.toXmlDoc(list[i]);
		xml += XmlUtils.toXmlString(doc);
	}
	xml +='</CASES>';
	return xml;
}

//创建表格
jQuery.fn.initGrid = function(){
	var $grid = $(this);
	$grid.datagrid({
		fitColumns:true,
		toolbar : [{
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				var lastIndex = $grid.data("lastIndex");
				if(!$grid.datagrid('validateRow', lastIndex)){
					return;
				}
				$grid.datagrid('endEdit', lastIndex);
				$grid.datagrid('appendRow', {});
				lastIndex = $grid.datagrid('getRows').length - 1;
				$grid.datagrid('beginEdit', lastIndex);
				$grid.data("lastIndex",lastIndex);
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				var row = $grid.datagrid('getSelected');
				if(row) {
					var index = $grid.datagrid('getRowIndex', row);
					$grid.datagrid('deleteRow', index);
				}
			}
		}, '-', {
			text : '撤销',
			iconCls : 'icon-undo',
			handler : function() {
				$grid.datagrid('rejectChanges');
			}
		}, '-', {
			text : '完成',
			iconCls : 'icon-save',
			handler : function() {
				$grid.datagrid('acceptChanges');
			}
		}],
		onBeforeLoad : function() {
			$(this).datagrid('rejectChanges');
		},
		onClickRow : function(rowIndex) {
			var $grid = $(this);
			var lastIndex = $grid.data("lastIndex");
			if(!$grid.datagrid('validateRow', lastIndex)){
				return;
			}
			if(lastIndex != rowIndex) {
				$grid.datagrid('endEdit', lastIndex);
				$grid.datagrid('beginEdit', rowIndex);
			}else{
				$grid.datagrid('beginEdit', rowIndex);
			}
			$grid.data("lastIndex",rowIndex);
		}
	});
};