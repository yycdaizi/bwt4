<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>病案管理系统</title>
<jsp:include page="/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',href:'${pageContext.request.contextPath}/layout/north.jsp'" style="height: 60px;overflow: hidden;" class="logo"></div>
	<div data-options="region:'west',title:'功能导航',href:'${pageContext.request.contextPath}/layout/west.jsp'" style="width: 200px;overflow: hidden;"></div>
	<div data-options="region:'center',title:'欢迎使用病案管理系统',href:'${pageContext.request.contextPath}/layout/center.jsp'" style="overflow: hidden;"></div>
	<div data-options="region:'south',href:'${pageContext.request.contextPath}/layout/south.jsp'" style="height: 27px;overflow: hidden;"></div>
</body>
</html>