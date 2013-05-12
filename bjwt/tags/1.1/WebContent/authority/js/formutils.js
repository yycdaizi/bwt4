
/**
 * 检查gird是否选择行
 * @param grid -- 表格ID
 * @returns {Boolean}
 */
function checkGirdIsSel(gridId) {
	var selRow = $('#'+gridId).datagrid('getSelected');
	if (!selRow) {
		$.messager.alert('错误', '没有选中的行');
		return false;
	}
	return true;
}

//格式化
function formatOrg(val,row){
	return val.orgname;
}
function formatUser(val,row){
	return val.username;
}
function formatUserId(val,row){
	return val.userid;
}
function formatRole(val,row){
	return val.rolename;
}
function formatRoleId(val,row){
	return val.roleid;
}
function formatMenu(val,row){
	return val.menuname;
}