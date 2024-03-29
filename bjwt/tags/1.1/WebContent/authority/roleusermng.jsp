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
		<table id="grid_roleuser" class="easyui-datagrid" style="height:400px" data-options="
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns:true,
				pageSize:10"
				url="${pageContext.request.contextPath}/roleuser/page.do"
				fitColumns="true"
				sortName="ts"
        		sortOrder="desc"
        		toolbar="#topations"
				>
		<thead>
			<tr>
				<th data-options="field:'roleuserid',align:'center',width:30,sortable:true">编号</th>
				<th	data-options="field:'org',width:120" formatter="formatOrg">所属机构</th>
				<th data-options="field:'user',width:200" formatter="formatUser">用户名称</th>
				<th data-options="field:'role',width:200" formatter="formatRole">角色名称</th>
				<th	data-options="field:'ts',width:120">更新时间</th>
			</tr>
		</thead>
		</table>
		</div>
		<!-- 新增对话框 -->
		<div id="dialog_roleuser" class="easyui-dialog" closed="true" cache="false" modal="true" buttons="#formSub-buttons" style="width:400px;height:250px;left:200;top:100;padding:10px 20px">
		<form id="form_roleuser" method="post" class="fform">
			<input name="roleuserid" type="hidden" />
			<div class="fitem">    
	            <label>用户：</label>
	            <input id="cg_ownuser" name="user.userid" style="width:200px;"></input>
	            <script type="text/javascript">
	            $(function(){
	            	$('#cg_ownuser').combogrid({  
	                    panelWidth:400,  
	                    url: '${pageContext.request.contextPath}/user/page.do',  
	                    idField:'userid',  
	                    textField:'username', 
	                    mode:'remote',
	                    fitColumns:true,
	                    columns:[[  
	                        {field:'userid',title:'主键',width:80},  
	                        {field:'username',title:'名称',width:200}
	                    ]]
	                }); 
	            });
	            </script>
	        </div>
			<div class="fitem">    
	            <label>分配角色：</label>
	            <input id="cg_ownrole" name="role.roleid" style="width:200px;"></input>
	            <script type="text/javascript">
	            $(function(){
	            	$('#cg_ownrole').combogrid({  
	                    panelWidth:400,  
	                    url: '${pageContext.request.contextPath}/role/page.do',  
	                    idField:'roleid',  
	                    textField:'rolename', 
	                    mode:'remote',
	                    fitColumns:true,
	                    columns:[[  
	                        {field:'roleid',title:'主键',width:60},  
	                        {field:'rolecode',title:'编码',width:80},  
	                        {field:'rolename',title:'名称',width:120}
	                    ]]
	                }); 
	            });
	            </script>
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
		var data = $("#grid_roleuser").datagrid('getData');
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
		$("#grid_roleuser").datagrid('load',{
			keyword:$("#keyword").val()
		});
	});
	})
	//对话框显示or隐藏
	function dialogAddShow(){
		$("#dialog_roleuser").dialog({
			title : '新增',
			iconCls : 'icon-add'
		}).dialog('open');
	}
	function dialogEditShow(){
		$("#dialog_roleuser").dialog({
			title : '编辑',
			iconCls : 'icon-edit'
		}).dialog('open');
	}
	function dialogHide(){
		$("#dialog_roleuser").dialog("close");
		$(".validatebox-tip").hide();
	}
	function fillForm(){
		var selRow = $('#grid_roleuser').datagrid('getSelected');
		$("#form_roleuser").form('load',selRow);
		if(selRow.user){
			$("#cg_ownuser").combogrid('setValue', selRow.user.userid);
		}
		if(selRow.role){
			$("#cg_ownrole").combogrid('setValue', selRow.role.roleid);
		}
		if(selRow.org){
			$('#cg_ownorg').combogrid('setValue', selRow.org.orgid);
		}
	}
	function clearForm(){
		$("#form_roleuser").form('clear');
	}
	function checkIsSel(){
		var selRow = $('#grid_roleuser').datagrid('getSelected');
		if(!selRow){
			$.messager.alert('错误','没有选中的行');
			return false;
		}
		return true;
	}
	//删除
	function doDel(){
		var selRow = $('#grid_roleuser').datagrid('getSelected');
		$.messager.progress(); // display the progress bar
		$.post('${pageContext.request.contextPath}/roleuser/deleteById.do',{roleuserid:selRow.roleuserid},function(result){
			$.messager.progress('close'); // hide progress bar
			var result = $.parseJSON(result);
			if (result.success){
				$.messager.show({
                    title: '提示',  
                    msg: result.message  
                }); 
                $('#grid_roleuser').datagrid('reload');    // reload the user data  
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
		$('#form_roleuser').form('submit', {
			url : "${pageContext.request.contextPath}/roleuser/save.do",
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
						$('#dialog_roleuser').dialog('close'); // close the dialog
						$('#grid_roleuser').datagrid('reload'); // reload the user
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