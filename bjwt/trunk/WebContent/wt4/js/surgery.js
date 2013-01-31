/**
 * 手术信息面板的操作函数
 */
var SurgeryPanel = {};

SurgeryPanel.eventhandles = {
		onSelect:function(title){
			//取得index
			var index = SurgeryPanel.divIndex(title);

			var surgerys = $(this).data("surgerys");
			var cur = $(this).data("current");
			
			var curTitle = SurgeryPanel.buildTitle(cur);
			
			if(cur!=index){
				if($(this).tabs('exists',curTitle)){
					SurgeryPanel.validateSurgery(curTitle);
				}
				//保存当前表单中的值
				if(surgerys[cur]){
					SurgeryPanel.saveCurrentSurgery();
				}
				//重新设置表单中的值
				if(!surgerys[index]){
					surgerys[index]=new Surgery();
				}
				SurgeryPanel.loadSurgery(surgerys[index]);
				//设置当前记录
				$(this).data("current",index);
			}
			SurgeryPanel.validateSurgery(title);
		},
		onClose:function(title){
			tabs = $('#surgerys').tabs("tabs");
			if(tabs.length==0){
				var surgerys = new Array();
				surgerys[0] = new Surgery();
				$('#surgerys').data("surgerys",surgerys);
				$('#surgerys').data("current",0);
				SurgeryPanel.addSurgeryTab(0);
				SurgeryPanel.loadSurgery(surgerys[0]);
			}else{
				//取得index
				var index = SurgeryPanel.divIndex(title);
				var surgerys = $('#surgerys').data("surgerys");
				surgerys[index]=undefined;
				$('#surgerys').data("surgerys",surgerys);
			}
		},
		onBeforeClose:function(title){
			return confirm("你即将删除这条手术记录，确定删除吗？");
		}
};

SurgeryPanel.init = function(){
	//保存当前病案记录的手术情况信息
	var surgerys = new Array();
	surgerys[0] = new Surgery();
	$('#surgerys').data("surgerys", surgerys);
	//保存当前页面上【手术信息】tab显示的手术信息记录的编号
	$('#surgerys').data("current",0);
	var options = $.extend({
		tools:[{
			iconCls:'icon-add',
			handler: function(){
				var surgerys = $('#surgerys').data("surgerys");
				//在当前病案记录中增加一条手术信息记录
				surgerys[surgerys.length] = new Surgery();
				//增加一个tab
				SurgeryPanel.addSurgeryTab(surgerys.length-1);
				
				$('#surgerys').data("surgerys",surgerys);
			}
		}]
	},SurgeryPanel.eventhandles);
	$('#surgerys').tabs(options);
};
//禁用用户事件
SurgeryPanel.disableEvents = function(){
	var options = {};
	for(var event in SurgeryPanel.eventhandles){
		options[event] = $.fn.tabs.defaults[event];
	}
	$('#surgerys').tabs(options);
};
//允许用户事件
SurgeryPanel.enableEvents = function(){
	$('#surgerys').tabs(SurgeryPanel.eventhandles);
};

SurgeryPanel.buildTitle = function(index){
	index++;
	return '手术信息-' + index;
};
SurgeryPanel.divIndex = function(title){
	var strs = title.split('-');
	return parseInt(strs[1])-1;
};

//增加一个手术信息tab页
SurgeryPanel.addSurgeryTab = function(index){
	var title = SurgeryPanel.buildTitle(index);
	$('#surgerys').tabs('add',{
		title:title,
		content:'',
		closable:true
	});
	return title;
};

//保存当前【手术信息】tab页中的数据
SurgeryPanel.saveCurrentSurgery = function(){
	var surgerys = $('#surgerys').data("surgerys");
	var cur = $('#surgerys').data("current");
	var obj = surgerys[cur];
	for(var tag in obj){
		if('ACA09S' == tag){
			obj[tag]= $("#"+tag).datagrid('getData').rows;
		}else{
			var el = $("#"+tag);
			if(el.length!=0){
				obj[tag]=el.getValue();
			}
		}
	}
	return obj;
};

//加载当前【手术信息】tab页中的数据
SurgeryPanel.loadSurgery = function(surgery){
	$("#surgery").form("load",surgery);
	$('#ACA09S').datagrid("loadData",{"total":surgery["ACA09S"].length,"rows":surgery["ACA09S"]});
};

//验证tab页
SurgeryPanel.validateSurgery = function(title){
	var validform = $("#surgery").form('validate');
	var validgrid = $("#ACA09S").datagrid('validateData');//validateGrid('ACA09S');
	var valid = validform&&validgrid;
	
	//var title = SurgeryPanel.buildTitle(index);
	//var tabs = $('#surgerys');
	if(valid){
		$('#surgerys').tabs('updateIcon',{title:title,iconCls:''});
		//updateTabIcon(tabs,title,'');
	}else{
		$('#surgerys').tabs('updateIcon',{title:title,iconCls:'icon-warn'});
		//updateTabIcon(tabs,title,'icon-warn');
	}
	
//	var surgerys = $('#surgerys').data("surgerys");
//	surgerys[index].validate = valid;
//	$('#surgerys').data("surgerys",surgerys);
	return valid;
};

//验证所有手术信息
SurgeryPanel.validate = function(){
	var valid = true;
	var tabs = $('#surgerys').tabs("tabs");
	for(var i=tabs.length-1;i>=0;i--){
		var title = tabs[i].panel('options').title;
		$('#surgerys').tabs("select",title);
		valid = valid && SurgeryPanel.validateSurgery(title);
	}
	return valid;
};

//加载手术信息数据
SurgeryPanel.loadData = function(surArray){
	SurgeryPanel.disableEvents();
	var tabs = $('#surgerys').tabs("tabs");
	for(var i=tabs.length-1;i>=0;i--){
		$('#surgerys').tabs("close",tabs[i].panel('options').title);
	}
	var surgerys = surArray.clone();
	for(var j=0;j<surgerys.length;j++){
		if(surgerys[j]){
			SurgeryPanel.addSurgeryTab(j);
		}
	}
	//若为空，则自增1
	tabs = $('#surgerys').tabs("tabs");
	if(tabs.length==0){
		surgerys = new Array();
		surgerys[0] = new Surgery();
		SurgeryPanel.addSurgeryTab(0);
	}
	//初始化，把第一个tab设为当前页，保存surgerys和current
	tabs = $('#surgerys').tabs("tabs");
	var title = tabs[0].panel('options').title;
	$('#surgerys').tabs("select",title);
	SurgeryPanel.loadSurgery(surgerys[0]);
	$('#surgerys').data("surgerys",surgerys);
	$('#surgerys').data("current",SurgeryPanel.divIndex(title));
	SurgeryPanel.enableEvents();
};
//获得所有手术信息数据
SurgeryPanel.getData = function(){
	SurgeryPanel.saveCurrentSurgery();
	var surgerys = $('#surgerys').data("surgerys");
	return surgerys.clone();
};
SurgeryPanel.clear = function(){
	SurgeryPanel.disableEvents();
	var tabs = $('#surgerys').tabs("tabs");
	for(var i=tabs.length-1;i>=0;i--){
		$('#surgerys').tabs("close",tabs[i].panel('options').title);
	}
	var surgerys = new Array();
	surgerys[0] = new Surgery();
	SurgeryPanel.addSurgeryTab(0);
	//保存surgerys和current
	SurgeryPanel.loadSurgery(surgerys[0]);
	$('#surgerys').data("surgerys",surgerys);
	$('#surgerys').data("current",0);
	SurgeryPanel.enableEvents();
};