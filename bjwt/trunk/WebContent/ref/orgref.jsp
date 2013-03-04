<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<!-- easy-ui依赖 -->
	<jsp:include page="/include.jsp"></jsp:include>
</head>
<body>
	<table id="ref_grid_org" class="easyui-datagrid" style="width:420px;height:308px"
			url="${pageContext.request.contextPath}/org/page.do"
			iconCls="icon-search" toolbar="#tb"
			rownumbers="true" pagination="true" 
			singleSelect="true"
			fitColumns="true">
		<thead>
			<tr>
				<th data-options="field:'orgid',align:'center',width:30,sortable:true">编号</th>
				<th data-options="field:'orgcode',width:120">机构编码</th>
				<th data-options="field:'orgname',width:120">机构名称</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
	</script>
</body>
</html>