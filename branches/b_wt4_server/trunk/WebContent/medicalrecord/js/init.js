//总数据集，保存左边病案列表中的所有病案记录
window.dataset = new Dataset();
//初始化，添加一条新的空记录
var index = window.dataset.add(new MedicalRecord());
window.dataset.current = index;

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
		SurgeryPanel.loadData(record["ACAS"]);
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
	
	SurgeryPanel.loadData(record["ACAS"]);
	
	$("#formTab4").form("load",record);
	$("#formTab5").form("load",record);
	//主键
	$("#pk").val(record["id"]);
	
	//诊断情况
	$('#ABDS').datagrid("loadData",{"total":record["ABDS"].length,"rows":record["ABDS"]});
	//重症情况
	$('#AEKS').datagrid("loadData",{"total":record["AEKS"].length,"rows":record["AEKS"]});
	//新生儿情况
	$('#AENS').datagrid("loadData",{"total":record["AENS"].length,"rows":record["AENS"]});
}

//保存当前页面的数据
function saveMediaRecord(){
	var obj = window.dataset.getCurrent();
	for (var tag in obj){
		switch(tag){
		//手术情况集合
		case 'ACAS':
			obj[tag]=SurgeryPanel.getData();
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
