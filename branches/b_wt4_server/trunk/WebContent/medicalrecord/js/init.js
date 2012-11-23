//总数据集，保存左边病案列表中的所有病案记录
window.dataset = new Dataset();
//初始化，添加一条新的空记录
var index = window.dataset.add(new MedicalRecord());
window.dataset.current = index;

//全局变量，保存当前病案记录的手术情况信息
$.surgerys = copyArray(window.dataset.getCurrent()["ACAS"]);
//保存当前页面上【手术信息】tab显示的手术信息记录的编号
$.surgerys.current = 0;

//增加一个手术信息tab页
function addSurgeryTab(index){
	$('#surgerys').tabs('add',{
		title:'手术信息-' + index,
		content:'',
		closable:true
	});
}

//复制一个数组，数组中的对象并没有复制
function copyArray(obj){
	if(!obj){
		return obj;
	}
	var cloneObj = new Array();
	for(var i=0;i<obj.length;i++){
		cloneObj[i]=obj[i];
	}
	return cloneObj;
}

//加载病案记录到页面
function loadRecordToTab(record, title) {
	switch(title){
	case "第一页":
		$("#formTab1").form("load",record);
		break;
	case "第二页":
		$("#formTab2").form("load",record);
		//诊断情况
		$('#ABDS').datagrid("loadData",{"total":record["ABDS"].length,"rows":record["ABDS"]});
		break;
	case "第三页":
		var tabs = $('#surgerys').tabs("tabs");
		for(var i=tabs.length-1;i>=0;i--){
			$('#surgerys').tabs("close",tabs[i].panel('options').title);
		}
		$.surgerys = copyArray(record["ACAS"]);
		for(var j=0;j<$.surgerys.length;j++){
			if($.surgerys[j]){
				addSurgeryTab(j+1);
			}
		}
		break;
	case "第四页":
		$("#formTab4").form("load",record);
		//重症情况
		$('#AEKS').datagrid("loadData",{"total":record["AEKS"].length,"rows":record["AEKS"]});
		//新生儿情况
		$('#AENS').datagrid("loadData",{"total":record["AENS"].length,"rows":record["AENS"]});
		break;
	case "第五页":
		$("#formTab5").form("load",record);
		break;
	default:
		break;
	}
	//主键
	$("#pk").val(record["id"]);
}

//加载病案记录到页面
function loadRecord(record) {
	$("#formTab1").form("load",record);
	$("#formTab2").form("load",record);
	//$("#AAD01C").combobox("setValues",record["AAD01C"].split(","));
	//$('#ABDS').datagrid("loadData",{"total":record["ABDS"].length,"rows":record["ABDS"]});
	//..................................................................................................
	////////////////////////////////////////////////////////////////////////
	var tabs = $('#surgerys').tabs("tabs");
	for(var i=tabs.length-1;i>=0;i--){
		$('#surgerys').tabs("close",tabs[i].panel('options').title);
	}
	$.surgerys = copyArray(record["ACAS"]);
	for(var j=0;j<$.surgerys.length;j++){
		if($.surgerys[j]){
			addSurgeryTab(j+1);
		}
	}
	$("#formTab4").form("load",record);
	$("#formTab5").form("load",record);
	//主键
	$("#pk").val(record["id"]);
	
	//诊断情况
	//setGridDataToTable_D("ABDS",record["ABDS"]);
	$('#ABDS').datagrid("loadData",{"total":record["ABDS"].length,"rows":record["ABDS"]});
	//重症情况
	$('#AEKS').datagrid("loadData",{"total":record["AEKS"].length,"rows":record["AEKS"]});
	//新生儿情况
	//setGridDataToTable_B("AENS",record["AENS"]);
	$('#AENS').datagrid("loadData",{"total":record["AENS"].length,"rows":record["AENS"]});
}
//加载当前【手术信息】tab页中的数据
function loadSurgery(surgery){
	$("#surgery").form("load",surgery);
	$('#ACA09S').datagrid("loadData",{"total":surgery["ACA09S"].length,"rows":surgery["ACA09S"]});
}

//保存当前【手术信息】tab页中的数据
function saveSurgery(){
	var obj = $.surgerys[$.surgerys.current];
	for(var tag in obj){
		if('ACA09S' == tag){
			//...................................................................
			//var data=getGridDataFromTable_SS("ACA09S");
			obj[tag]= $("#"+tag).datagrid('getData').rows;
		}else{
			var el = $("#"+tag);
			if(el.length!=0){
				obj[tag]=el.getValue();
			}
		}
	}
	return obj;
}
//保存当前页面的数据
function saveMediaRecord(){
	var obj = window.dataset.getCurrent();
	for (var tag in obj){
		switch(tag){
		//手术情况集合
		case 'ACAS':
			saveSurgery();
			obj[tag]=copyArray($.surgerys);
			break;
		//出院其他诊断集合
		case 'ABDS':
			obj[tag]= $("#"+tag).datagrid('getData').rows;
			break;
		//重症监护集合
		case 'AEKS':
			obj[tag]= $("#"+tag).datagrid('getData').rows;
			break;
		//新生儿情况集合
		case 'AENS':
			obj[tag]= $("#"+tag).datagrid('getData').rows;
			break;
		case 'id':
			obj[tag]=$("#pk").val();
			break;
		default:
			var el = $("#"+tag);
			if(el.length!=0){
				obj[tag]=el.getValue();
			}
		}
	}
}

//创建表格
jQuery.fn.initGrid = function(){
	var $grid = $(this);
	$grid.datagrid({
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
