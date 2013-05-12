<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典管理</title>
<jsp:include page="/include.jsp"></jsp:include>
</head>
<body>
	<table id="gridDicType" style="height:400px" class="easyui-datagrid" title='字典类型'
		autoRowHeight="false" singleSelect="true" url="${pageContext.request.contextPath}/dicdata/dicType/page.do"
		pagination="true" rownumbers="true" fitColumns="true" sortName="createTime" 
        sortOrder="desc" toolbar="#gridDicType-toolbar">
		<thead>
			<tr>
				<th data-options="field:'code',width:100,sortable:true">字典类型</th>
				<th data-options="field:'name',width:200">字典名称</th>
				<th	data-options="field:'createTime',width:150,sortable:true">创建时间</th>
				<th	data-options="field:'operate',width:100,formatter:dicTypeOperater">操作</th>
			</tr>
		</thead>
	</table>
	<div id="gridDicType-toolbar" style="padding:5px;height:auto">
		<span>关键字:</span>
		<input id="dicType-keyword"  style="width:300px"></input>
		<a id="dicType-btnQuery" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		<a id="dicType-btnAdd" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" style="float:right">新增</a>
	</div>
	<div id="dialogDicType" class="easyui-dialog" closed="true" modal="true" buttons="#formDicType-buttons" style="width:400px;height:200px;padding:10px 20px">
		<div class="ftitle">数据字典类型</div> 
		<form id="formDicType" method="post" class="fform">
			<input name="id" type="hidden">
			<input name="createTime" type="hidden">
			<div class="fitem">    
	            <label>字典类型：</label>    
	            <input name="code" class="easyui-validatebox" required="true" validType="length[4,30]"> 
	        </div>
	        <div class="fitem">    
	            <label>字典名称：</label>  
	            <input name="name" class="easyui-validatebox" validType="maxLength[60]">    
	        </div> 
		</form>
	</div>
	<div id="formDicType-buttons" style="text-align: center;">
		<a id="formDicType-btnSubmit" href="#" class="easyui-linkbutton" iconCls="icon-ok">确定</a>    
    	<a id="formDicType-btnCancel" href="#" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
	
	<div id="winDicItemEdit" class="easyui-window" title="字典数据项列表" style="width:600px;height:400px"
		collapsible="false" minimizable="false" closed="true" modal="true" href="${pageContext.request.contextPath}/dicdata/dicItemList.jsp">
	</div>
<script type="text/javascript">
function dicTypeOperater(value, row, index){
	var html = [];
	html.push($.operateButton('icon-wrench','字典项管理','manageDicItems('+index+')'));
	html.push($.operateSplit);
	html.push($.operateButton('icon-edit','修改','updateDicType('+index+')'));
	html.push($.operateSplit);
	html.push($.operateButton('icon-cancel','删除','deleteDicType('+index+')'));
	return html.join('');
}
function updateDicType(index){
	var rows = $('#gridDicType').datagrid('getRows');
	var row = rows[index];
	$('#dialogDicType').dialog({title:'编辑数据字典',iconCls:'icon-edit'}).dialog('open');  
    $('#formDicType').form('load',row);
}
function deleteDicType(index){
	var rows = $('#gridDicType').datagrid('getRows');
	var row = rows[index];
	$.messager.confirm('提示', '确定要删除这条记录吗？', function(r){  
        if (r){
			$.post('${pageContext.request.contextPath}/dicdata/dicType/deleteById.do',{id:row.id},function(result){
				if (result.success){
					$.messager.show({  
                        title: '提示',  
                        msg: result.message  
                    }); 
                    $('#gridDicType').datagrid('reload');    // reload the user data  
                } else {  
                    $.messager.show({  
                        title: '错误',  
                        msg: result.message  
                    });  
                }
			});
		}  
    });
}
function manageDicItems(index){
	var rows = $('#gridDicType').datagrid('getRows');
	var row = rows[index];
	var hasLoaded = $("#winDicItemEdit").data('hasLoaded');
	if(hasLoaded){
		if($("#gridDicItem").data('dicType').id != row.id){
			$("#winDicItemEdit").window('setTitle',row.name +" — 字典数据项列表");
			initItemList(row);
		}
	}else{
        $("#winDicItemEdit").window({
        	title:row.name +" — 字典数据项列表",
        	onLoad:function(){
        		//第一次加载页面时查询数据项
            	$("#winDicItemEdit").data('hasLoaded',true);
            	initItemList(row);
        	}	
        });
	}
    $("#winDicItemEdit").window("open");
    
    function initItemList(row){
		$("#gridDicItem").data('dicType',row);
        $("#gridDicItem").datagrid('load',{typeId:row.id});
	}
}

$(function(){
	$("#dicType-btnQuery").click(function(){
		$("#gridDicType").datagrid('load',{
			keyword:$("#dicType-keyword").val()
		});
	});
	$("#dicType-btnAdd").click(function(){
		$("#dialogDicType").dialog({title:'新增数据字典',iconCls:'icon-add'}).dialog('open');  
        $('#formDicType').form('clear');
	});
	
	$("#formDicType-btnSubmit").click(function(){
		$('#formDicType').form('submit',{  
            url: "${pageContext.request.contextPath}/dicdata/dicType/save.do",  
            onSubmit: function(){  
                return $(this).form('validate');
            },  
            success: function(result){  
                var result = $.parseJSON(result);  
                if (result.success){  
                	$.messager.show({  
                        title: '提示',  
                        msg: result.message  
                    }); 
                    $('#dialogDicType').dialog('close');      // close the dialog  
                    $('#gridDicType').datagrid('reload');    // reload the user data  
                } else {  
                    $.messager.show({  
                        title: '错误',  
                        msg: result.message  
                    });  
                }
            }
        }); 
	});
	$("#formDicType-btnCancel").click(function(){
		$("#dialogDicType").dialog("close");
	});
});
</script>
</body>
</html>