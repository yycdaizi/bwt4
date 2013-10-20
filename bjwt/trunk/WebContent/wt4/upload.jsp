<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>病案文件导入</title>
<jsp:include page="/include.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',title:'文件导入'"
			style="overflow: hidden;height:100px;align:center">
			<form id="formImport" action="" method="post"
				enctype="multipart/form-data" style="width: 600px;padding:10px;line-height: 18px">
				<label for="importfile" style="width: 30%; float: left">病例文件（*.xml）：</label>
				<input id="importfile" name="importfile" class="easyui-validatebox"
					type="file" required="true" style="width: 60%; float: left" /> 
				<a id="btnImport" href="#" class="easyui-linkbutton">导入</a>
			</form>
		</div>
		<div data-options="region:'center',title:'导入信息'" style="padding:5px;background:#eee;"></div>
	</div>
	<script type="text/javascript">
	$("#btnImport").click(function(){
		$('#formImport').form('submit',{  
            url: "${pageContext.request.contextPath}/wt4/medicalRecord/importFile.do",  
            onSubmit: function(){  
                return true;
            },  
            success: function(result){  
                var result = $.parseJSON(result);  
                if (result.success){  
                	$.messager.show({  
                        title: '提示',  
                        msg: result.message  
                    }); 
                } else {
                    $.messager.show({  
                        title: '错误',  
                        msg: result.message  
                    });  
                }
            }
        }); 
	});
	</script>
</body>
</html>