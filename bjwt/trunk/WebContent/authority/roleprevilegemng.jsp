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
		<table id="grid_previlege" class="easyui-datagrid" style="height:400px" data-options="
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns:true,
				pageSize:10"
				url="${pageContext.request.contextPath}/previlege/page.do"
				fitColumns="true"
				sortName="ts"
        		sortOrder="desc"
        		toolbar="#topations"
				>
		<thead>
			<tr>
				<th data-options="field:'previlegeid',align:'center',width:30,sortable:true">编号</th>
				<th	data-options="field:'org',width:120" formatter="formatOrg">所属机构</th>
				<th data-options="field:'role',width:200" formatter="formatRole">角色</th>
				<th data-options="field:'menu',width:200" formatter="formatMenu">菜单</th>
				<th data-options="field:'permission',width:200" >是否有权限</th>
				<th	data-options="field:'ts',width:120">更新时间</th>
			</tr>
		</thead>
		</table>
		</div>
		<!-- 新增对话框 -->
		<div id="dialog_previlege" class="easyui-dialog" closed="true" cache="false" modal="true" buttons="#formSub-buttons" style="width:400px;height:250px;left:200;top:100;padding:10px 20px">
		<form id="form_previlege" method="post" class="fform">
			<input name="previlegeid" type="hidden" />
			<input name="master" value="role" type="hidden" />
			<input name="resource" value="menu" type="hidden" />
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
	            <label>角色：</label>
	            <input id="cg_ownrole" name="mastervalue" style="width:200px;"></input>
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
	            <label>分配菜单：</label>
	            <input id="cg_ownmenu" name="resourcevalue" style="width:200px;"></input>
	            <script type="text/javascript">
	            $(function(){
	            	$('#cg_ownmenu').combogrid({  
	                    panelWidth:400,  
	                    url: '${pageContext.request.contextPath}/menu/page.do',  
	                    idField:'menuid',  
	                    textField:'menuname', 
	                    mode:'remote',
	                    fitColumns:true,
	                    columns:[[  
	                        {field:'menuid',title:'主键',width:60},  
	                        {field:'menuname',title:'菜单名称',width:80},  
	                        {field:'menuurl',title:'菜单路径',width:120}
	                    ]]
	                }); 
	            });
	            </script>
	        </div>
	        <div class="fitem">    
	            <label>是否启用：</label>
	            <select id="cc" class="easyui-combobox" name="permission" style="width:200px;">  
    				<option value="0">锁定</option>  
    				<option value="1">启用</option>
				</select>
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
		var data = $("#grid_previlege").datagrid('getData');
		dialogAddShow();
		clearForm();
		initForm();
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
		$("#grid_previlege").datagrid('load',{
			keyword:$("#keyword").val()
		});
	});
	})
	//对话框显示or隐藏
	function dialogAddShow(){
		$("#dialog_previlege").dialog({
			title : '新增角色',
			iconCls : 'icon-add'
		}).dialog('open');
	}
	function dialogEditShow(){
		$("#dialog_previlege").dialog({
			title : '编辑角色',
			iconCls : 'icon-edit'
		}).dialog('open');
	}
	function dialogHide(){
		$("#dialog_previlege").dialog("close");
		$(".validatebox-tip").hide();
	}
	function fillForm(){
		var selRow = $('#grid_previlege').datagrid('getSelected');
		$("#form_previlege").form('load',selRow);
		if(selRow.role){
			$("#cg_ownrole").combogrid('setValue', selRow.role.roleid);
		}
		if(selRow.org){
			$('#cg_ownorg').combogrid('setValue', selRow.org.orgid);
		}
		if(selRow.menu){
			$("#cg_ownuser").combogrid('setValue', selRow.menu.menuid);
		}

	}
	function clearForm(){
		$("#form_previlege").form('clear');
	}
	function initForm(){
		$('#form_previlege').form('load',{
			master:'role',
			resource:'menu'
		});
	}
	function checkIsSel(){
		var selRow = $('#grid_previlege').datagrid('getSelected');
		if(!selRow){
			$.messager.alert('错误','没有选中的行');
			return false;
		}
		return true;
	}
	//删除
	function doDel(){
		var selRow = $('#grid_previlege').datagrid('getSelected');
		$.messager.progress(); // display the progress bar
		$.post('${pageContext.request.contextPath}/previlege/deleteById.do',{previlegeid:selRow.previlegeid},function(result){
			$.messager.progress('close'); // hide progress bar
			if (result.success){
				$.messager.show({
                    title: '提示',  
                    msg: result.message  
                }); 
                $('#grid_previlege').datagrid('reload');    // reload the user data  
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
		$('#form_previlege').form('submit', {
			url : "${pageContext.request.contextPath}/previlege/save.do",
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
						$('#dialog_previlege').dialog('close'); // close the dialog
						$('#grid_previlege').datagrid('reload'); // reload the user
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