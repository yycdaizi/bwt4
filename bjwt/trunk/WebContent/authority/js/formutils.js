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

/**
 * 给refbox赋值
 * @param node -- id or '#id'
 * @param val -- 值
 * @param showVal -- 显示值
 */
function setValForRefbox(node,val,showVal){
	var jqNode = (typeof node == 'string' ? $(node) : node);
	var valNode = jqNode.find('input.vaulefield');
	var showNode = jqNode.find('input.refbox-text');
	valNode.val(val);
	showNode.val(showVal);
}