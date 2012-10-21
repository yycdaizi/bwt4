function inputFocus(node){
    var tr_node = $(node).parent().parent();
    tr_node.siblings(".selected").removeClass("selected");
    tr_node.addClass("selected");
}

function addClick2(node){
    var div_tb = $(node).parent().parent().parent();
    var tb_node = div_tb.find(".datagrid-body>table");
    var tr = tb_node.find("tr").eq(0).clone();
    tr.find("input").val("");
    tr.appendTo(tb_node);
    tr.siblings(".selected").removeClass("selected");
    tr.addClass("selected");
    bindAutoComplete2();
}

function bindAutoComplete2(){
    $(".autocomplete_icd9_code").autocomplete(czData, {
        mustMatch: true,
        autoFill: false,
        formatItem: function(item){
            return item.code;
        }
    }).result(function(event, item){
    	item = item||{};
        $(this).parent().parent("tr").find("input.autocomplete_icd9_name").val(item.name);
    });
    $(".autocomplete_icd9_name").autocomplete(czData, {
        mustMatch: true,
        autoFill: false,
        formatItem: function(item){
            return item.name;
        }
    }).result(function(event, item){
    	item = item||{};
        $(this).parent().parent("tr").find("input.autocomplete_icd9_code").val(item.code);
    });
    
    $(".autocomplete_isornot").autocomplete(dic_bool, {
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

function delClick(node){
    var div_tb = $(node).parent().parent().parent();
    var tb_node = div_tb.find(".datagrid-body>table");
    var tr = tb_node.find("tr.selected").eq(0);
    if (!isFirstTr(tr)) {
        tr.prev().addClass("selected");
        tr.remove();
    }
}

function isFirstTr(node){
    var num = node.prevAll("tr").length;
    if (num == 0) {
        return true;
    }
    return false;
}

//表格取值
function getGridDataFromTable_SS(id){
    var tableData = new Array();
    var tb_node = $("#" + id);
    tb_node.find("tr").each(function(trindex, tritem){
        tableData[trindex] = new Object();
        $.each(ACA09Tags, function(i, tag){
            var val = $(tritem).find("td." + tag).find("input").val();
            tableData[trindex][tag] = val;
        });
    });
    return tableData;
}
function addTr_SS(id){
    var tb_node =$("#"+id);
    var tr = tb_node.find("tr").eq(0).clone();
    tr.find("input").val("");
    tr.appendTo(tb_node);
    tr.siblings(".selected").removeClass("selected");
    tr.addClass("selected");
    bindAutoComplete2();
}

//表格赋值
function setGridDataToTable_SS(id, dataArray){
	delTr(id);
	if(dataArray.length==0) return;
    var tb_node = $("#" + id);
	var trLen=tb_node.find("tr").length;
	for(var i=trLen;i<dataArray.length;i++){
		addTr_SS(id);
	}
    //赋值
    $.each(dataArray, function(i, rowData){
        tb_node.find("tr").eq(i).find("td").each(function(tdIndex, tdItem){
            var prop = $(tdItem).attr("class");
            $(tdItem).find("input").val(rowData[prop]);
        });
    });
}
