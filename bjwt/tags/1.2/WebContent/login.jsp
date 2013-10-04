<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登录</title>
<jsp:include page="/include.jsp"></jsp:include>
</head>
<body>
<div id="loginWin" class="easyui-window" title="登录" style="width:350px;height:228px;padding:5px;"
   minimizable="false" maximizable="false" resizable="false" collapsible="false" closable="false">
    <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding:5px;background:#fff;border:1px solid #ccc;">
        <form id="loginForm" method="post">
            <div style="padding:5px 0;">
                <label for="login">帐&nbsp;&nbsp;号:</label>
                <input type="text" name="username" style="width:260px;"></input>
            </div>
            <div style="padding:5px 0;">
                <label for="password">密&nbsp;&nbsp;码:</label>
                <input type="password" name="password" style="width:260px;"></input>
            </div>
			<div style="padding:5px 0;">
                <label for="checkcode">验证码:</label>
                <input type="text" name="checkcode" style="width:100px; "></input>
				<img src="${pageContext.request.contextPath}/user/checkcode.do" onclick="changeCheckCode(this)" title="刷新验证码" style="cursor: hand; vertical-align:bottom" />
            </div>
             <div style="padding:5px 0;text-align: center;color: red;" id="showMsg"></div>
        </form>
            </div>
            <div region="south" border="false" style="text-align:right;padding:5px 0;">
                <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="login()">登录</a>
                <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cleardata()">重置</a>
            </div>
    </div>
</div>
</body>
<script type="text/javascript">

function changeCheckCode(obj){
	var timeNow = new Date().getTime();
	obj.src="${pageContext.request.contextPath}/user/checkcode.do?time="+timeNow;
}

document.onkeydown = function(e){
    var event = e || window.event;  
    var code = event.keyCode || event.which || event.charCode;
    if (code == 13) {
        login();
    }
}
$(function(){
    $("input[name='username']").focus();
});
function cleardata(){
    $('#loginForm').form('clear');
}
function login(){
     if($("input[name='username']").val()=="" ){
         $("#showMsg").html("用户名为空，请输入");
         $("input[name='username']").focus();
    }else if($("input[name='checkcode']").val()==""){
		$("#showMsg").html("验证码为空，请输入");
	}
	else{
            //ajax异步提交  
           $.ajax({            
                  type:"POST",   //post提交方式默认是get
                  url:"${pageContext.request.contextPath}/user/login.do",
                  data:$("#loginForm").serialize(),   //序列化               
                  error:function(request) {      // 设置表单提交出错                 
                      $("#showMsg").html(request);  //登录错误提示信息
                  },
                  success:function(result) {
				  	var result = $.parseJSON(result);
				  	if(result.success){
						 document.location = "${pageContext.request.contextPath}/index.jsp";
					}else{
						$.messager.show({
							title : '错误',
							msg : result.message
						});
					}
                  }
            });       
        } 
}
</script>
</html>