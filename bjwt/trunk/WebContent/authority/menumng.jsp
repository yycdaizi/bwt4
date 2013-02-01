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
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th data-options="field:'menuid',width:80,sortable:true">菜单编码</th>
				<th data-options="field:'menuname',width:120">菜单名称</th>
				<th data-options="field:'menuurl',width:200">菜单路径</th>
				<th	data-options="field:'menuicon',width:180">菜单图标</th>
				<th	data-options="field:'operate',width:150">操作</th>
			</tr>
		</thead>
		</table>
		</div>
		<!-- 新增对话框 -->
		<div id="dialog_menu" class="easyui-dialog" closed="true" cache="false" modal="true" buttons="#formSub-buttons" style="width:400px;height:200px;padding:10px 20px">
		<form id="formMenu" method="post" class="fform">
			<div class="fitem">    
	            <label>菜单名称：</label>  
	            <input name="menuname" class="easyui-validatebox" required="true" validType="maxLength[30]">    
	        </div>
	        <div class="fitem">    
	            <label>菜单URL：</label>    
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
		<script type="text/javascript">
		$(function() {
			$("#btn_add").click(function(){
				$("#dialog_menu").dialog({title:'新增菜单',iconCls:'icon-add'}).dialog('open');
				$("#formMenu").form('clear');
			});
			$("#btn_cacel").click(function(){
				$("#dialog_menu").dialog("close");
				$(".validatebox-tip").hide();
			});
			
		});
		</script>
	</body>
</html>