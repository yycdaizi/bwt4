<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员管理</title>
<jsp:include page="/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'west'" style="width: 180px">
		<ul id="orgTree"></ul>
	</div>
	<div data-options="region:'center',title:'人员管理'">
		<form id="formPersonQuery" method="post" class="fform">
			<table width="100%">
				<colgroup>
					<col width="20%" />
					<col width="30%" />
					<col width="20%" />
					<col width="30%" />
				</colgroup>
				<tbody>
				<tr>
					<td align="right"><label for="eq_type">人员类型：</label></td>
					<td>    
		            <input id="eq_type" name="eq_type" class="easyui-combobox"
		            	data-options="
		            		editable: false,
		            		panelHeight: 'auto',
		            		valueField: 'code',
		            		textField: 'text',
		            		url: '${pageContext.request.contextPath}/dicdata/dicType/getDicData.do?type=PERSON-TYPE',
		            		required: true
		            	">
		            </td>
					<td align="right"><label for="blike_name">人员姓名：</label></td>
					<td><input name="blike_name" type="text"></td>
				</tr>
				</tbody>
			</table>
			<div align="center">
				<a id="person-btnQuery" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				<a id="person-btnClearQuery" href="#" class="easyui-linkbutton">清空</a>
			</div>
		</form>
		<table id="gridPerson" fit="true" class="easyui-datagrid"
			data-options="
			autoRowHeight: false, 
			singleSelect: true, 
			url:'${pageContext.request.contextPath}/common/person/page.do',
			pagination: true,
			rownumbers: true,
			fitColumns: true,
			toolbar: '#gridPerson-toolbar',
			onBeforeLoad:function(param){
				return param.orgId?true:false;
			}
			">
			<thead>
				<tr>
					<th data-options="field:'name',width:200">姓名</th>
					<th data-options="field:'type',width:100, formatter: personTypeFormatter">人员类型</th>
					<th data-options="field:'operate',width:150,formatter:personOperater">操作</th>
				</tr>
			</thead>
		</table>
		<div id="gridPerson-toolbar" style="height: 28px;">
			<a id="person-btnAdd" href="#" class="easyui-linkbutton"
				plain="true" iconCls="icon-add" style="float: right">新增</a>
		</div>
		
		<div id="dialogPerson" class="easyui-dialog" style="width:500px;height:300px;padding:10px 20px"
			data-options="
				closed: true,
				modal: true,
				buttons: '#formPerson-buttons'
			">
			<div class="ftitle">人员</div> 
			<form id="formPerson" method="post" class="fform">
				<input name="id" type="hidden">
				<input name="org.orgid" type="hidden">
				<div class="fitem">    
		            <label>人员类型：</label>    
		            <input name="type" class="easyui-combobox"
		            	data-options="
		            		editable: false,
		            		panelHeight: 'auto',
		            		valueField: 'code',
		            		textField: 'text',
		            		url: '${pageContext.request.contextPath}/dicdata/dicType/getDicData.do?type=PERSON-TYPE',
		            	"> 
		        </div>
		        <div class="fitem">    
		            <label for="name">人员姓名：</label>  
		            <textarea name="name" style="height:80px;width: 300px" class="easyui-validatebox" required="true"></textarea>    
		        </div> 
		        <div>注：可以填写多个人的姓名进行批量添加，姓名之间需以“#”号分隔。<br/>例如：张三#李四</div>
			</form>
		</div>
		<div id="formPerson-buttons" style="text-align: center;">
			<a id="formPerson-btnSubmit" href="#" class="easyui-linkbutton" iconCls="icon-ok">确定</a>    
	    	<a id="formPerson-btnCancel" href="#" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	</div>
	<script type="text/javascript">
	function personOperater(value, row, index){
		var html = [];
		//html.push($.operateButton('icon-edit','修改','updatePerson('+index+')'));
		//html.push($.operateSplit);
		html.push($.operateButton('icon-cancel','删除','deletePerson('+index+')'));
		return html.join('');
	}
	function personTypeFormatter(value){
		var stateDic = $('#eq_type').combobox('getData');
		for(var i=0,len=stateDic.length;i<len;i++){
			if(value==stateDic[i]['code']){
				return stateDic[i]['text'];
			}
		}
		return value;
	}

	function deletePerson(index){
		var rows = $('#gridPerson').datagrid('getRows');
		var row = rows[index];
		$.messager.confirm('提示', '确定要删除这条记录吗？', function(r){
            if (r){
				$.post('${pageContext.request.contextPath}/common/person/deleteById.do',{id:row.id},function(result){
					if (result.success){  
						$.messager.show({  
	                        title: '提示',  
	                        msg: result.message  
	                    }); 
	                    $('#gridPerson').datagrid('reload');    // reload the user data  
	                } else {  
	                    $.messager.show({  
	                        title: '错误',  
	                        msg: result.message  
	                    });  
	                }
				},'json');
			}  
        });
	}
	
	$(function(){
		$('#person-btnQuery').click(function(){
			var params = $('#formPersonQuery').serializeObject();
			var oldParms = $('#gridPerson').datagrid('options').queryParams;
			$('#gridPerson').datagrid('load',$.extend(oldParms, params));
		});
		
		$('#person-btnClearQuery').click(function(){
			$('#formPersonQuery').form('clear');
		});
		
		$("#orgTree").tree({
			url: '${pageContext.request.contextPath}/org/tree.do',
			onSelect: function(node){
				$('#formPersonQuery').form('clear');
				$("#gridPerson").datagrid('load', {orgId: node.id});
			}
		});
		
		$("#person-btnAdd").click(function(){
	        var curNode = $("#orgTree").tree('getSelected');
	        if(curNode){
				$("#dialogPerson").dialog({title:'新增人员',iconCls:'icon-add'}).dialog('open');  
		        $('#formPerson').form('clear');
	        	$("#formPerson [name='org.orgid']").val(curNode.id);
	        }else{
	        	$.messager.alert('提示','请先选择一个机构！','info');
	        }
		});
		
		$("#formPerson-btnSubmit").click(function(){
			$('#formPerson').form('submit',{  
	            url: "${pageContext.request.contextPath}/common/person/batchSave.do",  
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
	                    $('#dialogPerson').dialog('close');      // close the dialog  
	                    $('#gridPerson').datagrid('reload');    // reload the user data  
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
			$("#dialogPerson").dialog("close");
		});
	});
	</script>
</body>
</html>