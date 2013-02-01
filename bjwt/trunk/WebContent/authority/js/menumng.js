$(function() {
	// 新增
	$("#btn_add").click(function() {
		dialogAddShow();
		clearForm();
	});
	// 修改
	$("#btn_edit").click(function(){
		var selRow = $('#grid_menu').datagrid('getSelected');
		if(!selRow){
			$.messager.alert('错误','没有选中的行');
			return false;
		}
		dialogEditShow();
		fillForm();
	});
	//取消
	$("#btn_cacel").click(function() {
		dialogHide();
	});
});
//对话框显示&隐藏
function dialogAddShow(){
	$("#dialog_menu").dialog({
		title : '新增菜单',
		iconCls : 'icon-add'
	}).dialog('open');
}
function dialogEditShow(){
	$("#dialog_menu").dialog({
		title : '编辑菜单',
		iconCls : 'icon-edit'
	}).dialog('open');
}
function dialogHide(){
	$("#dialog_menu").dialog("close");
	$(".validatebox-tip").hide();
}
function fillForm(){
	var selRow = $('#grid_menu').datagrid('getSelected');
	$("#formMenu").form('load',selRow);
}
function clearForm(){
	$("#formMenu").form('clear');
}
