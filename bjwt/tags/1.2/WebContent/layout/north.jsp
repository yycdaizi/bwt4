<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.bjdrgs.bjwt.authority.model.User"%>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div id="north_main">
	<span class="head">欢迎:<shiro:principal property="username"/>,&nbsp;
	<a href="#" id="changePswd">修改密码</a>&nbsp;|
	<a href="${pageContext.request.contextPath }/user/loginout.do" id="loginOut">安全退出</a>
	</span>
	<span style="padding-left: 10px; font-size: 16px;"><img src="${pageContext.request.contextPath}/resourses/images/blocks.gif"
		width="20" height="20" /> 病案管理 </span>
</div>
<!-- 新增对话框 -->
<div id="dialog_changpswd" class="easyui-dialog" closed="true" cache="false"
	modal="true" buttons="#formSub-buttons"
	style="width: 400px; height: 220px; padding: 10px 20px">
	<form id="formchangpswd" method="post" class="fform">
		<div class="fitem">
			<label>旧密码：</label> <input id="oldPswd" name="oldPswd" type="password" class="easyui-validatebox">
		</div>
		<div class="fitem"> 
			<label>新密码：</label> <input id="newPswd" name="newPswd" type="password" class="easyui-validatebox" required="true" validType="length[6,30]">
		</div>
		<div class="fitem">
			<label>新密码确认：</label> <input id="newPswd2" name="newPswd2" type="password" class="easyui-validatebox" required="true" validType="same['newPswd']">
		</div>
	</form>
</div>
<div id="formSub-buttons" style="text-align: center;">
	<a id="btn_submit" href="#" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a id="btn_cacel" href="#" class="easyui-linkbutton"
		iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
	$(function(){
		$("#changePswd").click(function(){
			$("#dialog_changpswd").dialog({
				title : '修改密码'
			}).dialog('open');
		});
		//取消
		$("#btn_cacel").click(function() {
			dialogHide();
		});
		// 保存（提交）
		$("#btn_submit").click(function() {
			doSubmit();
		});
	});
	function dialogHide(){
		$("#dialog_changpswd").dialog("close");
		$(".validatebox-tip").hide();
	}
	function doSubmit(){
		$.messager.progress(); // display the progress bar
		$('#formchangpswd').form('submit', {
			url : "${pageContext.request.contextPath}/user/changepswd.do",
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close'); // hide progress bar while// the form is invalid
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
						$('#dialog_changpswd').dialog('close'); // close the dialog
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