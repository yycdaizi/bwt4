/**
 * 校验form
 */
function checkValid(){
    var isvalid1 = validateTab1();
    var isvalid2 = validateTab2();
    var isvalid3 = validateTab3();
    var isvalid4 = validateTab4();
    var isvalid5 = validateTab5();
    var isvalid = (isvalid1 && isvalid2 && isvalid3 && isvalid4 && isvalid5);
	return isvalid;
}
//校验tab1
function validateTab1(){
	var validform = $("#formTab1").form('validate');
	var mainTabs = $('#mainTabs');
	if(validform){
		updateTabIcon(mainTabs,'患者基本信息','');
	}else{
		updateTabIcon(mainTabs,'患者基本信息','icon-warn');
	}
	return validform;
}

//校验tab2
function validateTab2(){
	var validform = $("#formTab2").form('validate');
	var validgrid = validateGrid('ABDS');
	var valid = validform&&validgrid;
	var mainTabs = $('#mainTabs');
	if(valid){
		updateTabIcon(mainTabs,'诊断信息','');
	}else{
		updateTabIcon(mainTabs,'诊断信息','icon-warn');
	}
	return valid;
}

//校验tab3
function validateTab3(){
	var valid= SurgeryPanel.validate();
	var mainTabs = $('#mainTabs');
	if(valid){
		updateTabIcon(mainTabs,'手术及操作信息','');
	}else{
		updateTabIcon(mainTabs,'手术及操作信息','icon-warn');
	}
	return valid;
}

//校验tab4
function validateTab4(){
	var validform = $("#formTab4").form('validate');
	var validgrid = validateGrid('AEKS')&&validateGrid('AENS');
	var valid = validform&&validgrid;
	var mainTabs = $('#mainTabs');
	if(valid){
		updateTabIcon(mainTabs,'其他医疗信息','');
	}else{
		updateTabIcon(mainTabs,'其他医疗信息','icon-warn');
	}
	return valid;
}
//校验tab5
function validateTab5(){
	var validform = $("#formTab5").form('validate');
	var mainTabs = $('#mainTabs');
	if(validform){
		updateTabIcon(mainTabs,'费用信息','');
	}else{
		updateTabIcon(mainTabs,'费用信息','icon-warn');
	}
	return validform;
}

//校验表格中的所有数据
function validateGrid(id){
	var $grid = $("#"+id);
	var valid = true;
	for(var i=0;i<$grid.datagrid('getRows').length;i++){
		$grid.datagrid('beginEdit', i);
		if(!$grid.datagrid('validateRow', i)){
			valid = false;
		}else{
			$grid.datagrid('endEdit', i);
		}
	}
	return valid;
}

//校验总费用
function validExpenses(){
	var tags = ["ADA0101","ADA11","ADA21","ADA22","ADA23","ADA24","ADA25","ADA26","ADA27","ADA28","ADA13","ADA15","ADA12",
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
}

function updateTabIcon(tabs,title,iconCls){
	var tab = tabs.tabs('getTab',title);
	tabs.tabs('update', {
		tab: tab,
		options:{
			iconCls:iconCls
		}
	});
}

function caseXmlExportClick(){
    var isvalid = $(document).form('validate');
    if (!isvalid) {
        alert("输入数据有误，请修正！");
        return false;
    }
    var xmlWriter = new CaseXmlWriter();
    var xmlStr = xmlWriter.generateCase();
    alert(xmlStr);
}
