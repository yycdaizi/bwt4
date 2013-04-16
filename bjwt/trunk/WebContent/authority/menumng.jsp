<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<!-- easy-ui依赖 -->
		<jsp:include page="/include.jsp"></jsp:include>
		<link href="css/basic.css" rel="stylesheet" type="text/css" />
		<link href="css/content.css" rel="stylesheet" type="text/css" />
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
		<table id="grid_menu" class="easyui-treegrid" style="height:400px" data-options="
				url: '${pageContext.request.contextPath}/menu/page.do',
				singleSelect:true,
                rownumbers: true,  
                pagination: true,  
                idField: 'menuid',  
                treeField: 'menuname'"
                toolbar="#topations"
                fitColumns="true"
				>
		<thead>
			<tr>
				<th data-options="field:'menuname',width:260">菜单名称</th>
				<th data-options="field:'menuurl',width:200">菜单路径</th>
				<th	data-options="field:'menuicon',width:120">菜单图标</th>
				<th	data-options="field:'ts',width:120">更新时间</th>
			</tr>
		</thead>
		</table>
		</div>
		<!-- 新增对话框 -->
		<div id="dialog_menu" class="easyui-dialog" closed="true" cache="false" modal="true" buttons="#formSub-buttons" style="width:400px;height:220px;left:200px;top:100px;padding:10px 20px">
		<form id="formMenu" method="post" class="fform">
			<input name="menuid" type="hidden" />
			<div class="fitem">    
	            <label>菜单名称：</label>  
	            <input name="menuname" class="easyui-validatebox" required="true" validType="maxLength[30]">    
	        </div>
	        <div class="fitem">    
	            <label>菜单路径：</label>    
	            <input name="menuurl" class="easyui-validatebox" validType="maxLength[30]"> 
	        </div>
	        <div class="fitem">    
	            <label>菜单图标：</label>  
	            <input name="menuicon" class="easyui-validatebox"  validType="maxLength[30]">    
	        </div>
	        <div class="fitem">    
	            <label>父菜单：</label>  
	            <input id="cg_pmenu" name="parentid" style="width:200px;"></input>
				<script type="text/javascript">
	            $(function(){
	            	$('#cg_pmenu').combogrid({  
	                    panelWidth:400,  
	                    url: '${pageContext.request.contextPath}/menu/page.do?query_all=true',  
	                    idField:'menuid',  
	                    textField:'menuname', 
	                    mode:'remote',
	                    fitColumns:true,
	                    columns:[[  
	                        {field:'menuid',title:'主键',width:60},  
	                        {field:'menuname',title:'名称',width:120},
	                        {field:'menuurl',title:'路径',width:120}
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
	<script type="text/javascript">
	$(function(){
	// 新增
	$("#btn_add").click(function() {
		dialogAddShow();
		clearForm();
	});
	// 修改
	$("#btn_edit").click(function(){
		if(!checkIsSel()){
			return false;
		}
		dialogEditShow();
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
		$("#grid_menu").datagrid('load',{
			keyword:$("#keyword").val()
		});
	});
	});
	
	//对话框显示&隐藏
	function dialogAddShow(){
		$("#dialog_menu").dialog({
			title : '新增菜单',
			iconCls : 'icon-add'
		}).dialog('open');
	}
	function dialogEditShow(){
		$("#dialog_menu").dialog({
			title : '编辑菜单',
			iconCls : 'icon-edit'
		}).dialog('open');
	}
	function dialogHide(){
		$("#dialog_menu").dialog("close");
		$(".validatebox-tip").hide();
	}
	function fillForm(){
		var selRow = $('#grid_menu').datagrid('getSelected');
		$("#formMenu").form('load',selRow);
	}
	function clearForm(){
		$("#formMenu").form('clear');
	}
	function checkIsSel(){
		var selRow = $('#grid_menu').datagrid('getSelected');
		if(!selRow){
			$.messager.alert('错误','没有选中的行');
			return false;
		}
		return true;
	}
	//删除
	function doDel(){
		var selRow = $('#grid_menu').datagrid('getSelected');
		$.messager.progress(); // display the progress bar
		$.post('${pageContext.request.contextPath}/menu/deleteById.do',{menuid:selRow.menuid},function(result){
			$.messager.progress('close'); // hide progress bar
			var result = $.parseJSON(result);
			if (result.success){
				$.messager.show({
                    title: '提示',  
                    msg: result.message  
                }); 
                $('#grid_menu').treegrid('reload');    // reload the user data  
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
		$('#formMenu').form('submit', {
			url : "${pageContext.request.contextPath}/menu/save.do",
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
						$('#dialog_menu').dialog('close'); // close the dialog
						$('#grid_menu').treegrid('reload'); // reload the user
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