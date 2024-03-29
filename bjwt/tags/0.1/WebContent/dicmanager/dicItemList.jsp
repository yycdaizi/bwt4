<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典项</title>
<jsp:include page="/include.jsp"></jsp:include>
</head>
<body>
	<table id="gridDicItem" fit="true" 
		autoRowHeight="false" singleSelect="true" url="${pageContext.request.contextPath}/dicmanager/dicItem/findPage.do"
		pagination="true" rownumbers="true" fitColumns="true" sortName="code" 
        sortOrder="asc" toolbar="#gridDicItem-toolbar" >
		<thead>
			<tr>
				<th data-options="field:'code',width:100,sortable:true">编码</th>
				<th data-options="field:'text',width:200">名称</th>
				<th data-options="field:'type.id',width:100, formatter:function(){return $('#gridDicItem').data('dicType').code;}">字典类型</th>
				<th	data-options="field:'createTime',width:150,sortable:true">创建时间</th>
			</tr>
		</thead>
	</table>
	<div id="gridDicItem-toolbar">
		<a id="dicItem-btnDelete" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" style="float:right">删除</a>
		<div class="datagrid-btn-separator" style="float:right"></div>
		<a id="dicItem-btnUpdate" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-edit" style="float:right">修改</a>
		<div class="datagrid-btn-separator" style="float:right"></div>
		<a id="dicItem-btnAdd" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" style="float:right">新增</a>
	</div>
	<div id="dialogDicItem" class="easyui-dialog" closed="true" modal="true" buttons="#formDicItem-buttons" style="width:400px;height:270px;padding:10px 20px">
		<div class="ftitle">数据字典类型</div> 
		<form id="formDicItem" method="post" class="fform">
			<input name="id" type="hidden">
			<input name="createTime" type="hidden">
			<input name="type.id" type="hidden">
			<div class="fitem">    
	            <label>编码：</label>    
	            <input name="code" class="easyui-validatebox" required="true"> 
	        </div>
	        <div class="fitem">    
	            <label>名称：</label>  
	            <input name="text" class="easyui-validatebox">    
	        </div>
	        <div class="fitem">    
	            <label>描述：</label>  
	            <textarea name="description" class="easyui-validatebox"></textarea>    
	        </div> 
		</form>
	</div>
	<div id="formDicItem-buttons" style="text-align: center;" >
		<a id="formDicItem-btnSubmit" href="#" class="easyui-linkbutton" iconCls="icon-ok">确定</a>    
    	<a id="formDicItem-btnCancel" href="#" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
	<script type="text/javascript">
	$(function(){
		$("#gridDicItem").datagrid({
			onBeforeLoad:function(param){
				return param.typeId?true:false;
			}
		});
		$("#dicItem-btnAdd").click(function(){
			$("#dialogDicItem").dialog({title:'新增字典项',iconCls:'icon-add'}).dialog('open');  
	        $('#formDicItem').form('clear');
	        //$('#formDicItem').form('load',{type:{id:$('#gridDicItem').data('dicType').id}});
	        $("#formDicItem [name='type.id']").val($('#gridDicItem').data('dicType').id);
		});
		$("#dicItem-btnUpdate").click(function(){
			var row = $('#gridDicItem').datagrid('getSelected');  
	        if (row){  
	            $('#dialogDicItem').dialog({title:'编辑字典项',iconCls:'icon-edit'}).dialog('open');  
	            $('#formDicItem').form('load',row);
	            $("#formDicItem [name='type.id']").val($('#gridDicItem').data('dicType').id);
	        }else{
	        	$.messager.show({  
	                title: '提示',  
	                msg: '请选择一条记录'  
	            }); 
	        }
		});
		$("#dicItem-btnDelete").click(function(){
			var row = $('#gridDicItem').datagrid('getSelected');
			if(row){
				$.messager.confirm('提示', '确定要删除这条记录吗？', function(r){
		            if (r){
						$.post('${pageContext.request.contextPath}/dicmanager/dicItem/deleteById.do',{id:row.id},function(result){
							if (result.success){  
			                    $('#gridDicItem').datagrid('reload');    // reload the user data  
			                } else {  
			                    $.messager.show({  
			                        title: '错误',  
			                        msg: result.message  
			                    });  
			                }
						});
					}  
		        });
	        }else{
	        	$.messager.show({  
	                title: '提示',  
	                msg: '请选择一条记录'  
	            }); 
	        }
		});
		
		$("#formDicItem-btnSubmit").click(function(){
			$('#formDicItem').form('submit',{  
	            url: "${pageContext.request.contextPath}/dicmanager/dicItem/save.do",  
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
	                    $('#dialogDicItem').dialog('close');      // close the dialog  
	                    $('#gridDicItem').datagrid('reload');    // reload the user data  
	                } else {  
	                    $.messager.show({  
	                        title: '错误',  
	                        msg: result.message  
	                    });  
	                }
	            }
	        }); 
		});
		$("#formDicItem-btnCancel").click(function(){
			$("#dialogDicItem").dialog("close");
		});
	});
	</script>
</body>
</html>