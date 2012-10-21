//新生儿表格
function addClick(node){
    var div_tb = $(node).parent().parent().parent();
    var tb_node = div_tb.find(".datagrid-body>table");
    var tr = tb_node.find("tr").eq(0).clone();
    tr.find("input").val("");
    tr.appendTo(tb_node);
    tr.siblings(".selected").removeClass("selected");
    tr.addClass("selected");
    bindAutoComplete_B();
}

function bindAutoComplete_B(){
    $(".autocomplete_baby_icd10_code").autocomplete(zdData, {
        mustMatch: true,
        autoFill: false,
        formatItem: function(item){
            return item.code;
        }
    }).result(function(event, item){
    	item = item||{};
        $(this).parent().parent("tr").find("input.autocomplete_baby_icd10_name").val(item.name);
    });
    $(".autocomplete_baby_icd10_name").autocomplete(zdData, {
        mustMatch: true,
        autoFill: false,
        formatItem: function(item){
            return item.name;
        }
    }).result(function(event, item){
    	item = item||{};
        $(this).parent().parent("tr").find("input.autocomplete_baby_icd10_code").val(item.code);
    });
}
//表格赋值
function addTr_B(id){
	var tb_node = $("#"+id);
    var tr = tb_node.find("tr").eq(0).clone();
    tr.find("input").val("");
    tr.appendTo(tb_node);
    tr.siblings(".selected").removeClass("selected");
    tr.addClass("selected");
    bindAutoComplete_B();
}

function setGridDataToTable_B(id, dataArray){
	delTr(id);
	if(dataArray.length==0) return;
    var tb_node = $("#" + id);
	var trLen=tb_node.find("tr").length;
	for(var i=trLen;i<dataArray.length;i++){
		addTr_B(id);
	}
    //赋值
    $.each(dataArray, function(i, rowData){
        tb_node.find("tr").eq(i).find("td").each(function(tdIndex, tdItem){
            var prop = $(tdItem).attr("class");
            $(tdItem).find("input").val(rowData[prop]);
        });
    });
}

