<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<!-- easy-ui依赖 -->
		<jsp:include page="/include.jsp"></jsp:include>
		<link href="css/basic.css" rel="stylesheet" type="text/css" />
		<link href="css/content.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/formutils.js"></script>
		<style type="text/css">
			.refbox{
   				border-style: solid;
    			border-width: 1px;
    			display: inline-block;
    			margin: 0;
    			padding: 0;
    			white-space: nowrap;
    			border-color: #ccc
			}
			.refbox-text{
			}
			.refbox-button{
				background: url("images/refbox_button.png") no-repeat scroll center center transparent;
				cursor: pointer;
    			display: inline-block;
   				height: 20px;
    			opacity: 0.6;
    			vertical-align: top;
    			width: 16px;
			}
		</style>
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
		<table id="grid_org" class="easyui-datagrid" style="height:360px" data-options="
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns:true,
				pageSize:10"
				url="${pageContext.request.contextPath}/org/page.do"
				fitColumns="true"
				sortName="orgid" 
        		sortOrder="asc"
        		toolbar="#topations"
				>
		<thead>
			<tr>
				<th data-options="field:'orgid',align:'center',width:30,sortable:true">编号</th>
				<th data-options="field:'orgcode',width:120">机构编码</th>
				<th data-options="field:'orgname',width:120">机构名称</th>
				<th data-options="field:'parentOrg_showname',width:120">父机构</th>
				<th data-options="field:'orgaddr',width:200">所在地址</th>
				<th	data-options="field:'orgmanager_showname',width:120">机构负责人</th>
				<th	data-options="field:'ts',width:120">更新时间</th>
			</tr>
		</thead>
		</table>
		</div>
		<!-- 新增对话框 -->
		<div id="dialog_org" class="easyui-dialog" closed="true" cache="false" modal="true" buttons="#formSub-buttons" style="width:400px;height:240px;padding:10px 20px">
		<form id="formorg" method="post" class="fform">
			<input name="orgid" type="hidden" />
			<input id="pid" name="parentOrg.orgid" type="hidden" />
			<div class="fitem">    
	            <label>机构编码：</label>  
	            <input name="orgcode" class="easyui-validatebox" required="true" validType="maxLength[30]">    
	        </div>
			<div class="fitem">    
	            <label>机构名称：</label>
	            <input name="orgname" class="easyui-validatebox" required="true" validType="maxLength[30]">    
	        </div>
	        <div class="fitem">
	            <label>父机构：</label>
				<span class="refbox" style="width: 200px; height: 20px;">
					<input type="text" id="orgref_showname" class="refbox-text" style="width: 180px; height: 20px; line-height: 20px;">
					<span>
						<span id="btn_orgref" class="refbox-button" ></span>
						<script type="text/javascript">
							$(function() {
								$("#btn_orgref").click(function(){
									$('#org_ref').dialog({  
									    title: '选择机构',  
									    width: 440,  
									    closed: true,  
									    cache: false, 
									    href: '${pageContext.request.contextPath}/ref/orgref.jsp',  
									    modal: true,
									    buttons:[{
											text:'确定',
											handler:function(){
												if( !checkGirdIsSel("grid_ref") ) return false;
												var selRow = $('#grid_ref').datagrid('getSelected');
												$("#pid").val(selRow.orgid);
												$("#orgref_showname").val(selRow.orgname);
												$('#org_ref').dialog("close");
											}
										},{
											text:'取消',
											handler:function(){
												$('#org_ref').dialog("close");
											}
										},{
											text:'清空',
											handler:function(){
												$("#pid").val('');
												$("#orgref_showname").val('');
												$('#org_ref').dialog("close");
											}
										}]
									}).dialog('open'); 
								});
							});
						</script>
					</span>
				</span>
	        </div>
	        <div class="fitem">    
	            <label>所在地址：</label>  
	            <input name="orgaddr" class="easyui-validatebox"  validType="maxLength[200]">
	        </div>
	        <div class="fitem">
	            <label>机构负责人：</label>
				<span class="refbox" style="width: 200px; height: 20px;">
					<input type="text" id="userref_showname" class="refbox-text" style="width: 180px; height: 20px; line-height: 20px;">
					<input type="hidden" id="userref_id" name="orgmanager.userid" />
					<span>
						<span id="btn_ref" class="refbox-button" ></span>
						<script type="text/javascript">
							$(function() {
								$("#btn_ref").click(function(){
									$('#user_ref').dialog({  
									    title: '选择用户',  
									    width: 420,  
									    closed: true,  
									    cache: false, 
									    href: '${pageContext.request.contextPath}/ref/userref.jsp',  
									    modal: true,
									    buttons:[{
											text:'确定',
											handler:function(){
												if( !checkGirdIsSel("grid_ref") ) return false;
												var selRow = $('#grid_ref').datagrid('getSelected');
												$("#userref_id").val(selRow.userid);
												$("#userref_showname").val(selRow.username);
												$('#user_ref').dialog("close");
											}
										},{
											text:'取消',
											handler:function(){
												$('#user_ref').dialog("close");
											}
										},{
											text:'清空',
											handler:function(){
												$("#userref_id").val("");
												$("#userref_showname").val("");
												$('#user_ref').dialog("close");
											}
										}]
									}).dialog('open'); 
								});
							});
						</script>
					</span>
				</span>
	        </div>
		</form>
	</div>
	<div id="formSub-buttons" style="text-align: center;">
		<a id="btn_submit" href="#" class="easyui-linkbutton" iconCls="icon-ok">保存</a>    
    	<a id="btn_cacel" href="#" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
	<div id="org_ref"></div>
	<div id="user_ref"></div>
	<!-- 脚本 -->
	<script type="text/javascript">
	// 新增
	$("#btn_add").click(function() {
		var data = $("#grid_org").datagrid('getData');
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
		$("#grid_org").datagrid('load',{
			keyword:$("#keyword").val()
		});
	});
	
	//对话框显示&隐藏
	function dialogAddShow(){
		$("#dialog_org").dialog({
			title : '新增机构',
			iconCls : 'icon-add'
		}).dialog('open');
	}
	function dialogEditShow(){
		$("#dialog_org").dialog({
			title : '编辑机构',
			iconCls : 'icon-edit'
		}).dialog('open');
	}
	function dialogHide(){
		$("#dialog_org").dialog("close");
		$(".validatebox-tip").hide();
	}
	function fillForm(){
		var selRow = $('#grid_org').datagrid('getSelected');
		$("#formorg").form('load',selRow);
		$("#pid").val(selRow.parentOrg_orgid);
		$("#orgref_showname").val(selRow.parentOrg_showname);
	}
	function clearForm(){
		$("#formorg").form('clear');
	}
	function checkIsSel(){
		var selRow = $('#grid_org').datagrid('getSelected');
		if(!selRow){
			$.messager.alert('错误','没有选中的行');
			return false;
		}
		return true;
	}
	//删除
	function doDel(){
		var selRow = $('#grid_org').datagrid('getSelected');
		$.messager.progress(); // display the progress bar
		$.post('${pageContext.request.contextPath}/org/deleteById.do',{orgid:selRow.orgid},function(result){
			$.messager.progress('close'); // hide progress bar
			if (result.success){
				$.messager.show({
                    title: '提示',  
                    msg: result.message  
                }); 
                $('#grid_org').datagrid('reload');    // reload the user data  
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
		$('#formorg').form('submit', {
			url : "${pageContext.request.contextPath}/org/save.do",
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
						$('#dialog_org').dialog('close'); // close the dialog
						$('#grid_org').datagrid('reload'); // reload the user
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