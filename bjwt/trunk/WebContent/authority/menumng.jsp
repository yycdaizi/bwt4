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
				<li>
					<a id="btn_query" href="#" class="easyui-linkbutton" >查询</a> 
				</li>
			</ul>
		</div>
		<div style="width: 100%;height: 100%;">
		<table id="grid_menu" class="easyui-datagrid" style="height:400px" data-options="
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10"
				url="${pageContext.request.contextPath}/menu/page.do"
				fitColumns="true"
				sortName="menuid" 
        		sortOrder="asc"
				>
		<thead>
			<tr>
				<th data-options="field:'menuid',align:'center',width:30,sortable:true">编号</th>
				<th data-options="field:'menuname',width:120">菜单名称</th>
				<th data-options="field:'menuurl',width:200">菜单路径</th>
				<th	data-options="field:'menuicon',width:120">菜单图标</th>
				<th	data-options="field:'ts',width:120">更新时间</th>
				<th	data-options="field:'operate',width:150">操作</th>
			</tr>
		</thead>
		</table>
		</div>
		<!-- 新增对话框 -->
		<div id="dialog_menu" class="easyui-dialog" closed="true" cache="false" modal="true" buttons="#formSub-buttons" style="width:400px;height:200px;padding:10px 20px">
		<form id="formMenu" method="post" class="fform">
			<input name="menuid" type="hidden" />
			<div class="fitem">    
	            <label>菜单名称：</label>  
	            <input name="menuname" class="easyui-validatebox" required="true" validType="maxLength[30]">    
	        </div>
	        <div class="fitem">    
	            <label>菜单路径：</label>    
	            <input name="menuurl" class="easyui-validatebox" required="true" validType="maxLength[30]"> 
	        </div>
	        <div class="fitem">    
	            <label>菜单图标：</label>  
	            <input name="menuicon" class="easyui-validatebox"  validType="maxLength[30]">    
	        </div>
		</form>
	</div>
	<div id="formSub-buttons" style="text-align: center;">
		<a id="btn_submit" href="#" class="easyui-linkbutton" iconCls="icon-ok">保存</a>    
    	<a id="btn_cacel" href="#" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
	<script type="text/javascript" src="js/menumng.js"></script>
	<script type="text/javascript">
	// 保存（提交）
	$("#btn_submit").click(function() {
		doSubmit();
	});
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
												// submit successfully
				if (result && result.match("^\{(.+:.+,*){1,}\}$")) {// 若返回的是JSON数据
					var result = $.parseJSON(result);
					if (result.success) {
						$.messager.show({
							title : '提示',
							msg : result.message
						});
						$('#dialog_menu').dialog('close'); // close the dialog
						$('#grid_menu').datagrid('reload'); // reload the user
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
						msg : '页面找不到404'
					});
				}

			}
		});
	}
	</script>
	</body>
</html>