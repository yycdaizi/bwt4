<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<!-- easy-ui依赖 -->
		<jsp:include page="/include.jsp"></jsp:include>
		<link href="css/basic.css" rel="stylesheet" type="text/css" />
		<link href="css/content.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery.refbox.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/formutils.js"></script>
	</head>
	<body>
		<div id="topations">
			<ul>
				<li>
					<a id="btn_add" href="#" class="easyui-linkbutton" >新增</a>
				</li>
				<li>
					<a id="btn_edit" href="#" class="easyui-linkbutton" >修改</a>  
				</li>
				<li>
					<a id="btn_del" href="#" class="easyui-linkbutton" >删除</a> 
				</li>
				<li>&nbsp;&nbsp;&nbsp;&nbsp;<li>
				<li style="float: right;">
					<label>关键字：</label>
					<input id="keyword" name="keyword" type="text" size="40" style="height:20px;border: 1px solid #ccc" />
					<a id="btn_query" href="#" class="easyui-linkbutton" >查询</a> 
				</li>
			</ul>
		</div>
		<div style="width: 100%;height: 100%;">
		<table id="grid_role" class="easyui-datagrid" style="height:400px" data-options="
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns:true,
				pageSize:10"
				url="${pageContext.request.contextPath}/role/page.do"
				fitColumns="true"
				sortName="roleid" 
        		sortOrder="asc"
        		toolbar="#topations"
				>
		<thead>
			<tr>
				<th data-options="field:'roleid',align:'center',width:30,sortable:true">编号</th>
				<th	data-options="field:'org_orgname',width:120">所属机构</th>
				<th data-options="field:'rolecode',width:120">角色编号</th>
				<th data-options="field:'rolename',width:200">角色名称</th>
				<th	data-options="field:'ts',width:120">更新时间</th>
				<th	data-options="field:'note',width:200">备注</th>
			</tr>
		</thead>
		</table>
		</div>
		<!-- 新增对话框 -->
		<div id="dialog_role" class="easyui-dialog" closed="true" cache="false" modal="true" buttons="#formSub-buttons" style="width:400px;height:250px;left:200;top:100;padding:10px 20px">
		<form id="form_role" method="post" class="fform">
			<input name="roleid" type="hidden" />
			<div class="fitem">    
	            <label>角色编码：</label>  
	            <input name="rolecode" class="easyui-validatebox" required="true" validType="maxLength[30]">    
	        </div>
			<div class="fitem">    
	            <label>角色名称：</label>  
	            <input name="rolename" class="easyui-validatebox" required="true" validType="maxLength[30]">    
	        </div>
	        <div class="fitem">
	            <label>所属机构：</label>
	            <input id="cg_ownorg" name="org.orgid" style="width:200px;"></input>
				<script type="text/javascript">
	            $(function(){
	            	$('#cg_ownorg').combogrid({  
	                    panelWidth:400,  
	                    url: '${pageContext.request.contextPath}/org/page.do',  
	                    idField:'orgid',  
	                    textField:'orgname', 
	                    mode:'remote',
	                    fitColumns:true,
	                    columns:[[  
	                        {field:'orgid',title:'主键',width:60},  
	                        {field:'orgcode',title:'编码',width:80},  
	                        {field:'orgname',title:'名称',width:120}
	                    ]]
	                }); 
	            });
	            </script>
	        </div>
	        <div class="fitem">    
	            <label>备注：</label>
	            <textarea name="note" class="easyui-validatebox" validType="maxLength[500]"></textarea>
	        </div>
		</form>
	</div>
	<div id="formSub-buttons" style="text-align: center;">
		<a id="btn_submit" href="#" class="easyui-linkbutton" iconCls="icon-ok">保存</a>    
    	<a id="btn_cacel" href="#" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
	<div id="ref_dialog_org"></div>
	<script type="text/javascript">
	$(function(){
	// 新增
	$("#btn_add").click(function() {
		var data = $("#grid_role").datagrid('getData');
		dialogAddShow();
		clearForm();
	});
	// 修改
	$("#btn_edit").click(function(){
		if(!checkIsSel()){
			return false;
		}
		dialogEditShow();
		clearForm();
		fillForm();
	});
	// 删除
	$("#btn_del").click(function(){
		if(!checkIsSel()){
			return false;
		}
		$.messager.confirm('确认','是否删除选择数据?',function(r){   
		    if (r){
		       doDel();
		    }   
		});  
	});
	//取消
	$("#btn_cacel").click(function() {
		dialogHide();
	});
	// 保存（提交）
	$("#btn_submit").click(function() {
		doSubmit();
	});
	//查询
	$("#btn_query").click(function(){
		$("#grid_role").datagrid('load',{
			keyword:$("#keyword").val()
		});
	});
	})
	//对话框显示or隐藏
	function dialogAddShow(){
		$("#dialog_role").dialog({
			title : '新增角色',
			iconCls : 'icon-add'
		}).dialog('open');
	}
	function dialogEditShow(){
		$("#dialog_role").dialog({
			title : '编辑角色',
			iconCls : 'icon-edit'
		}).dialog('open');
	}
	function dialogHide(){
		$("#dialog_role").dialog("close");
		$(".validatebox-tip").hide();
	}
	function fillForm(){
		var selRow = $('#grid_role').datagrid('getSelected');
		$("#form_role").form('load',selRow);
		if(selRow.org){
			$('#cg_ownorg').combogrid('setValue', selRow.org.orgid);
		}
	}
	function clearForm(){
		$("#form_role").form('clear');
	}
	function checkIsSel(){
		var selRow = $('#grid_role').datagrid('getSelected');
		if(!selRow){
			$.messager.alert('错误','没有选中的行');
			return false;
		}
		return true;
	}
	//删除
	function doDel(){
		var selRow = $('#grid_role').datagrid('getSelected');
		$.messager.progress(); // display the progress bar
		$.post('${pageContext.request.contextPath}/role/deleteById.do',{roleid:selRow.roleid},function(result){
			$.messager.progress('close'); // hide progress bar
			var result = $.parseJSON(result);
			if (result.success){
				$.messager.show({
                    title: '提示',  
                    msg: result.message  
                }); 
                $('#grid_role').datagrid('reload');    // reload the user data  
            } else {  
                $.messager.show({
                    title: '错误',  
                    msg: result.message  
                });
            }
		});
	}
	//提交
	function doSubmit(){
		$.messager.progress(); // display the progress bar
		$('#form_role').form('submit', {
			url : "${pageContext.request.contextPath}/role/save.do",
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close'); // hide progress bar while
													// the form is invalid
				}
				return isValid;
			},
			success : function(result) {
				$.messager.progress('close'); // hide progress bar while
				if (result && result.match("^\{(.+:.+,*){1,}\}$")) {// 若返回的是JSON数据
					var result = $.parseJSON(result);
					if (result.success) {
						$.messager.show({
							title : '提示',
							msg : result.message
						});
						$('#dialog_role').dialog('close'); // close the dialog
						$('#grid_role').datagrid('reload'); // reload the user
															// data
					} else {
						$.messager.show({
							title : '错误',
							msg : result.message
						});
					}
				} else {
					$.messager.show({
						title : '错误',
						msg : '执行失败，请查看日志'
					});
				}

			}
		});
	}
	</script>
	</body>
</html>