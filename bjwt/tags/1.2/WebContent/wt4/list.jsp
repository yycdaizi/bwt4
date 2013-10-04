<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>病案查询</title>
<jsp:include page="/include.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/wt4/css/wt4.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourses/jslib/autocomplete/jquery.autocomplete.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resourses/jslib/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resourses/jslib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resourses/jslib/blockui/jquery.blockUI.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resourses/jslib/xmloperator/XmlUtils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resourses/jslib/oop.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resourses/jscommon/ModelValidator.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/wt4/data/dics/dic.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wt4/js/model.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/wt4/js/surgery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wt4/js/main.js"></script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="overflow: hidden;height:150px;" title="病案查询">
		<form id="formMedicalRecordQuery" method="post" class="fform">
			<table width="100%">
				<colgroup>
					<col width="20%" />
					<col width="30%" />
					<col width="20%" />
					<col width="30%" />
				</colgroup>
				<tbody>
				<tr>
					<td align="right"><label for="blike_AAA28">病案号：</label></td>
					<td><input name="blike_AAA28" type="text"></td>
					<td align="right"><label for="blike_AAA01">姓名：</label></td>
					<td><input name="blike_AAA01" type="text"></td>
				</tr>
				<tr>
					<td align="right"><label for="eq_AAB02C">入院科别：</label></td>
					<td><input id="eq_AAB02C" name="eq_AAB02C" type="text"	class="easyui-combobox" validType="comboboxfixed['eq_AAB02C']"
							 data-options="data:dic.medicalSubject"/></td>
					<td align="right"><label for="eq_AEM01C">离院方式：</label></td>
					<td><input name="eq_AEM01C" type="text"	class="easyui-combobox" editable="false" 
							panelHeight="auto" data-options="data:dic.liYuanFangShi"/></td>
				</tr>
				<tr>
					<td align="right"><label for="ge_AAC01">出院时间晚于：</label></td>
					<td><input name="ge_AAC01" type="text" onclick="WdatePicker();"></td>
					<td align="right"><label for="le_AAC01">出院时间早于：</label></td>
					<td><input name="le_AAC01" type="text" onclick="WdatePicker();"></td>
				</tr>
				</tbody>
			</table>
			<div align="center">
				<a id="medicalRecord-btnQuery" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				<a id="medicalRecord-btnClearQuery" href="#" class="easyui-linkbutton">清空</a>
			</div>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="gridMedicalRecord" class="easyui-datagrid" fit="true"
			autoRowHeight="false" singleSelect="true" url="${pageContext.request.contextPath}/wt4/medicalRecord/page.do"
			pagination="true" rownumbers="true" fitColumns="true" sortName="updateTime" 
	        sortOrder="desc" toolbar="#gridMedicalRecord-toolbar">
			<thead>
				<tr>
					<th data-options="field:'AAA28',width:100,sortable:true">病案号</th>
					<th data-options="field:'AAA01',width:200">姓名</th>
					<th data-options="field:'AAB02C',width:200,formatter:medicalSubject_Formatter">入院科别</th>
					<th data-options="field:'AAC01',width:200">出院时间</th>
					<th data-options="field:'AEM01C',width:200,formatter:liYuanFangShi_Formatter">离院方式</th>
					<th	data-options="field:'createTime',width:150,sortable:true">创建时间</th>
					<th	data-options="field:'updateTime',width:150,sortable:true">最后修改时间</th>
					<th	data-options="field:'operate',width:100,formatter:medicalRecordOperater">操作</th>
				</tr>
			</thead>
		</table>
		<div id="gridMedicalRecord-toolbar" style="height:28px">
			<a id="medicalRecord-btnExport" href="#" class="easyui-linkbutton" plain="true" style="float:right">导出</a>
			<a id="medicalRecord-btnAdd" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" style="float:right">新增</a>
		</div>
	</div>
</div>
	
<div id="dialogMedicalRecordEdit" class="easyui-dialog" title="病案" maximized="true"
	collapsible="false" modal="true" buttons="#dialogMREdit-buttons">
	<div id="mainTabs" class="easyui-tabs" fit="true" border="false">
	    <div title="患者基本信息" style="padding:10px;" href="view/tab1.html">
	    </div>
	    <div title="诊断信息" style="padding:10px;" href="view/tab2.html">
	    </div>
	    <div title="手术及操作信息" style="padding:10px;" href="view/tab3.html">
	    </div>
	    <div title="其他医疗信息" style="padding:10px;" href="view/tab4.html">
	    </div>
	    <div title="费用信息" style="padding:10px;" href="view/tab5.html">
	    </div>
	    <!--
	    -->
	</div>
</div>
<div id="dialogMREdit-buttons" style="text-align: center;">
	<a id="dialogMREdit-btnSubmit" href="#" class="easyui-linkbutton" iconCls="icon-ok">确定</a>    
   	<a id="dialogMREdit-btnCancel" href="#" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
function medicalSubject_Formatter(value) {
	var medicalSubject = dic.medicalSubject;
	for ( var i = 0, len = medicalSubject.length; i < len; i++) {
		if (medicalSubject[i].value == value)
			return medicalSubject[i].text;
	}
	return value;
}
function liYuanFangShi_Formatter(value) {
	var liYuanFangShi = dic.liYuanFangShi;
	for ( var i = 0, len = liYuanFangShi.length; i < len; i++) {
		if (liYuanFangShi[i].value == value)
			return liYuanFangShi[i].text;
	}
	return value;
}
function medicalRecordOperater(value, row, index){
	var html = [];
	if(row.editable){
		html.push($.operateButton('icon-edit','修改','updateMedicalRecord('+index+')'));
	}
	return html.join('');
}
function updateMedicalRecord(index){
	var rows = $('#gridMedicalRecord').datagrid('getRows');
	var row = rows[index];
	MedicalRecordForm.loadData(row);
	$('#dialogMedicalRecordEdit').dialog('setTitle','修改病案').dialog('open'); 
}
function loadAllTabs(){
    //遍历加载所有的tab
    var tabs = $('#mainTabs').tabs().tabs("tabs");
    for(var i=tabs.length-1;i>=0;i--){
        $('#mainTabs').tabs("select",tabs[i].panel('options').title);
    }
}
$(function(){
	loadAllTabs();
	$('#dialogMedicalRecordEdit').dialog('close');
	
	$('#medicalRecord-btnQuery').click(function(){
		$('#gridMedicalRecord').datagrid('load',$('#formMedicalRecordQuery').serializeObject());
	});
	$('#medicalRecord-btnClearQuery').click(function(){
		$('#formMedicalRecordQuery').form('clear');
	});
	
	$('#dialogMREdit-btnSubmit').click(function(){
		$.blockUI({
		    css: {
    		    border: 'none', 
    		    padding: '15px', 
    		    backgroundColor: '#000', 
    		    '-webkit-border-radius': '10px', 
                '-moz-border-radius': '10px', 
                opacity: .5, 
                color: '#fff' 
            },
            message:"<b>请稍后...</b>"
        });
		if(!MedicalRecordForm.validExpenses()){
			$.unblockUI({
			    onUnblock: function(){
			        $.messager.alert('验证错误','[总费用]应等于其他详细费用之和！','error');
		        }
	        });
			return false;
		}
		if (!MedicalRecordForm.validate()) {
			$.unblockUI({
                onUnblock: function(){
                    $.messager.alert('验证错误','输入数据有误，请更正后再提交！','error'); 
                }
            });
			return false;
		}
		var record = MedicalRecordForm.getData();
		$.ajax({
			type: "POST",
			url : '${pageContext.request.contextPath}/wt4/medicalRecord/save.do',
			async : true,
			contentType : 'application/json;utf-8',
			dataType : 'json',
			data : JSON.stringify([record]),
			success : function(result){
				if (result.success){
	            	$('#gridMedicalRecord').datagrid('reload');
	            	$('#dialogMedicalRecordEdit').dialog('close');
	            	
	            	$.unblockUI({
                        onUnblock: function(){
                            $.messager.show({title: '提示', msg: result.message}); 
                        }
                    });
	            } else {
	                $.unblockUI({
                        onUnblock: function(){
                            $.messager.alert('错误',result.message,'error');
                        }
                    });  
	            }
			},
			error : function (XMLHttpRequest, textStatus, errorThrown) {
				$.unblockUI({
                    onUnblock: function(){
                        $.messager.alert('错误','对不起，系统出错啦！','error');
                    }
                });
			}
		});
	});
	
	$('#dialogMREdit-btnCancel').click(function(){
		$('#dialogMedicalRecordEdit').dialog('close');
	});
	
	$("#medicalRecord-btnAdd").click(function(){
		MedicalRecordForm.clear();
		$('#dialogMedicalRecordEdit').dialog('setTitle','新增病案').dialog('open'); 
	});
	
	$("#medicalRecord-btnExport").click(function(){
		$.blockUI({
            css: {
                border: 'none', 
                padding: '15px', 
                backgroundColor: '#000', 
                '-webkit-border-radius': '10px', 
                '-moz-border-radius': '10px', 
                opacity: .5, 
                color: '#fff' 
            },
            message:"<b>正在导出，请稍后...</b>"
        });
		$("#formMedicalRecordQuery").form('load', $('#gridMedicalRecord').datagrid('options').queryParams);
		$("#formMedicalRecordQuery").attr("action", "${pageContext.request.contextPath}/wt4/medicalRecord/export.do");
		$("#formMedicalRecordQuery").submit();
		$.unblockUI();
	});
});
</script>
</body>
</html>