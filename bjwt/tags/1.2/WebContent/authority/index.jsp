<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户权限管理</title>
<!-- easy-ui依赖 -->
<jsp:include page="/include.jsp"></jsp:include>
<link href="css/basic.css" rel="stylesheet" type="text/css" />
<link href="css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/menunavi.js"></script>
<script type="text/javascript">
//退出
$(function() {
  $('#loginOut').click(function() {
      $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
      	if (r) {
          	location.href = 'logout';
      	}
      });
  });
});
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px; background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
		<span style="float: right; padding-right: 20px;" class="head">欢迎
			查无此人<a href="#" id="loginOut">安全退出</a>
		</span>
		<span style="padding-left: 10px; font-size: 16px;"><img
			src="images/blocks.gif" width="20" height="20"  />
			用户权限管理
		</span>
	</div>
	<div region="west" hide="true" split="true" title="导航菜单"
		style="width: 180px;" id="west">
		<div id="nav" class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->
			<ul>
			<li>
			<div>
			<a href="javascript:void(0)" rel="menumng.jsp" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">菜单管理</span>
			</a>
			</div>
			</li>
			<li>
			<div>
			<a href="javascript:void(0)" rel="rolemng.jsp" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">角色管理</span>
			</a>
			</div>
			</li>
			<li>
			<div>
			<a href="javascript:void(0)" rel="usermng.jsp" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">用户管理</span>
			</a>
			</div>
			</li>
			<li>
			<div>
			<a href="javascript:void(0)" rel="orgmng.jsp" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">机构管理</span>
			</a>
			</div>
			</li>
			<li>
			<div>
			<a href="javascript:void(0)" rel="roleusermng.jsp" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">用户角色分配</span>
			</a>
			</div>
			</li>
			<li>
			<div>
			<a href="javascript:void(0)" rel="roleprevilegemng.jsp" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">角色权限分配</span>
			</a>
			</div>
			</li>
			</ul>
			
		</div>
	</div>
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用"
				style="padding: 20px; overflow: hidden;"></div>
		</div>
	</div>
	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>