<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<!-- easy-ui依赖 -->
		<jsp:include page="/include.jsp"></jsp:include>
		<link href="css/basic.css" rel="stylesheet" type="text/css" />
		<link href="css/content.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/formutils.js"></script>
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
		<table id="grid_org" class="easyui-treegrid" style="height:360px" data-options="
				singleSelect:true,
				pagination:true,
				pageSize:10,
				idField: 'orgid',  
                treeField: 'orgname',
				onBeforeLoad: function(row,param){  
                    if (!row) { 
                        param.id = 0;
                    }
                }"
				url="${pageContext.request.contextPath}/org/page.do"
				fitColumns="true"
        		toolbar="#topations"
				>
		<thead>
			<tr>
				<th data-options="field:'orgid',align:'center',width:30,sortable:true">编号</th>
				<th data-options="field:'orgname',width:180">机构名称</th>
				<th data-options="field:'orgcode',width:120">机构编码</th>
				<th data-options="field:'parentOrg_showname',width:120">父机构</th>
				<th data-options="field:'orgaddr',width:200">所在地址</th>
				<th	data-options="field:'orgmanager_showname',width:120">机构负责人</th>
				<th	data-options="field:'ts',width:120">更新时间</th>
			</tr>
		</thead>
		</table>
		</div>
		<!-- 新增对话框 -->
		<div id="dialog_org" class="easyui-dialog" closed="true" cache="false" modal="true" buttons="#formSub-buttons" style="width:400px;height:240px;left:200;top:100;padding:10px 20px">
		<form id="formorg" method="post" class="fform">
			<input name="orgid" type="hidden" />
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
	            <input id="cg_porg" name="parentOrg.orgid" style="width:200px;"></input>
				<script type="text/javascript">
	            $(function(){
	            	$('#cg_porg').combogrid({  
	                    panelWidth:400,  
	                    url: '${pageContext.request.contextPath}/org/page.do',  
	                    idField:'orgid',  
	                    textField:'orgname', 
	                    mode:'remote',
	                    fitColumns:true,
	                    columns:[[  
	                        {field:'orgid',title:'主键',width:60},  
	                        {field:'orgcode',title:'编码',width:80},  
	                        {field:'orgname',title:'名称',width:120}
	                    ]]
	                }); 
	            });
	            </script>
	        </div>
	        <div class="fitem">    
	            <label>所在地址：</label>  
	            <input name="orgaddr" class="easyui-validatebox"  validType="maxLength[200]">
	        </div>
	        <div class="fitem">
	            <label>机构负责人：</label>
	            <input id="cg_manager" name="orgmanager.userid" style="width:200px;"></input>
				<script type="text/javascript">
	            $(function(){
	            	$('#cg_manager').combogrid({  
	                    panelWidth:280,
	                    url: '${pageContext.request.contextPath}/user/page.do',  
	                    idField:'userid',  
	                    textField:'username', 
	                    mode:'remote',
	                    fitColumns:true,
	                    columns:[[  
	                        {field:'userid',title:'主键',width:80},  
	                        {field:'username',title:'名称',width:200}
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
	<div id="ref_dialog_user"></div>
	<div id="ref_dialog_org"></div>
	<!-- 脚本 -->
	<script type="text/javascript">
	$(function(){
	// 新增
	$("#btn_add").click(function() {
		var data = $("#grid_org").treegrid('getData');
		dialogAddShow();
		clearForm();
	});
	// 修改
	$("#btn_edit").click(function(){
		if(!checkIsSel()){
			return false;
		}
		dialogEditShow();
		clearForm();
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
		if(selRow.parentOrg_orgid)
			$('#cg_porg').combogrid('setValue', selRow.parentOrg_orgid);
		if(selRow.orgmanager)
			$('#cg_manager').combogrid('setValue', selRow.orgmanager.userid);
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
                $('#grid_org').treegrid('reload');    // reload the user data  
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
						msg : '执行失败，请查看日志'
					});
				}

			}
		});
	}
	</script>
	</body>
</html>