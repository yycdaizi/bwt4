/**
 * 选中状态
 * @param {Object} node -- 输入框
 */
function inputFocus(node){
    var tr_node = $(node).parent().parent();
    tr_node.siblings(".selected").removeClass("selected");
    tr_node.addClass("selected");
}
/**
 * 删除行
 * @param {Object} node 按钮
 */
function delClick(node){
    var div_tb = $(node).parent().parent().parent();
    var tb_node = div_tb.find(".datagrid-body>table");
    var tr = tb_node.find("tr.selected").eq(0);
    if (!isFirstTr(tr)) {
        tr.prev().addClass("selected");
        tr.remove();
    }
}
/**
 * 是否为第一行
 * @param {Object} node 按钮
 */
function isFirstTr(node){
    var num = node.prevAll("tr").length;
    if (num == 0) {
        return true;
    }
    return false;
}

//表格取值
function getGridDataFromTable(id,colTags){
    var tableData = new Array();
    var tb_node = $("#" + id);
    tb_node.find("tr").each(function(trindex, tritem){
        tableData[trindex] = new Object();
        $.each(colTags, function(i, tag){
            var val = $(tritem).find("td." + tag).find("input").val();
            tableData[trindex][tag] = val;
        });
    });
    return tableData;
}
function delTr(id){
	var tb_node = $("#"+id);
    tb_node.find("tr").each(function(i,tr){
		if(i>0){
			$(tr).remove();
		}else{
			$(tr).find("input").val("");
		}
	});
}
