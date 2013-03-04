<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
	<table id="ref_grid_user" class="easyui-datagrid" style="width:420px;height:308px"
			url="${pageContext.request.contextPath}/user/page.do"
			iconCls="icon-search" toolbar="#tb"
			rownumbers="true" pagination="true" 
			singleSelect="true"
			fitColumns="true">
		<thead>
			<tr>
				<th data-options="field:'userid',align:'center',width:30,sortable:true">编号</th>
				<th data-options="field:'username',width:120">用户名</th>
			</tr>
		</thead>
	</table>
</body>
</html>