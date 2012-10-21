//诊断表格
function addClick_D(node){
    var div_tb = $(node).parent().parent().parent();
    var tb_node = div_tb.find(".datagrid-body>table");
    var tr = tb_node.find("tr").eq(0).clone();
    tr.find("input").val("");
    tr.appendTo(tb_node);
    tr.siblings(".selected").removeClass("selected");
    tr.addClass("selected");
    bindAutoComplete_D();
}

function bindAutoComplete_D(){
    $(".autocomplete_diagnose_icd10_code").autocomplete(zdData, {
        mustMatch: true,
        autoFill: false,
        formatItem: function(item){
            return item.code;
        }
    }).result(function(event, item){
    	item = item||{};
        $(this).parent().parent("tr").find("input.autocomplete_diagnose_icd10_name").val(item.name);
    });
    $(".autocomplete_diagnose_icd10_name").autocomplete(zdData, {
        mustMatch: true,
        autoFill: false,
        formatItem: function(item){
            return item.name;
        }
    }).result(function(event, item){
    	item = item||{};
        $(this).parent().parent("tr").find("input.autocomplete_diagnose_icd10_code").val(item.code);
    });
    $(".autocomplete_rybq_name").autocomplete(dic_rybq, {
        mustMatch: true,
        autoFill: false,
        formatItem: function(item){
            return item.text;
        },
        formatResult: function(data){
            return data.value;
        }
    });
}

function addTr_D(id){
	var tb_node = $("#"+id);
    var tr = tb_node.find("tr").eq(0).clone();
    tr.find("input").val("");
    tr.appendTo(tb_node);
    tr.siblings(".selected").removeClass("selected");
    tr.addClass("selected");
    bindAutoComplete_D();
}

//表格赋值
function setGridDataToTable_D(id, dataArray){
	delTr(id);
	if(dataArray.length==0) return;
    var tb_node = $("#" + id);
	var trLen=tb_node.find("tr").length;
	for(var i=trLen;i<dataArray.length;i++){
		addTr_D(id);
	}
    //赋值
    $.each(dataArray, function(i, rowData){
        tb_node.find("tr").eq(i).find("td").each(function(tdIndex, tdItem){
            var prop = $(tdItem).attr("class");
            $(tdItem).find("input").val(rowData[prop]);
        });
    });
}