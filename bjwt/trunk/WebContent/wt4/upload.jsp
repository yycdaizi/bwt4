<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>病案文件导入</title>
<jsp:include page="/include.jsp"></jsp:include>
<style type="text/css">
.uploader-buton{
position:relative;
display:inline-block;
overflow:hidden;
cursor:default;
margin:10px 0px;
height:100%;

}
.uploader-label{
position:relative;
display:inline-block;
overflow:hidden;
cursor:default;
padding:8px 12px;
margin:10px 0px;
}
.uploader{
position:relative;
display:inline-block;
overflow:hidden;
cursor:default;
padding:0;
margin:10px 0px;
-moz-box-shadow:0px 0px 5px #ddd;
-webkit-box-shadow:0px 0px 5px #ddd;
box-shadow:0px 0px 5px #ddd;

-moz-border-radius:5px;
-webkit-border-radius:5px;
border-radius:5px;
}

.filename{
float:left;
display:inline-block;
outline:0 none;
height:32px;
width:180px;
margin:0;
padding:8px 10px;
overflow:hidden;
cursor:default;
border:1px solid;
border-right:0;
font:9pt/100% Arial, Helvetica, sans-serif; color:#777;
text-shadow:1px 1px 0px #fff;
text-overflow:ellipsis;
white-space:nowrap;

-moz-border-radius:5px 0px 0px 5px;
-webkit-border-radius:5px 0px 0px 5px;
border-radius:5px 0px 0px 5px;

background:#f5f5f5;
background:-moz-linear-gradient(top, #fafafa 0%, #eee 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#fafafa), color-stop(100%,#f5f5f5));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#fafafa', endColorstr='#f5f5f5',GradientType=0);
border-color:#ccc;

-moz-box-shadow:0px 0px 1px #fff inset;
-webkit-box-shadow:0px 0px 1px #fff inset;
box-shadow:0px 0px 1px #fff inset;

-moz-box-sizing:border-box;
-webkit-box-sizing:border-box;
box-sizing:border-box;
}

.button{
float:left;
height:32px;
display:inline-block;
outline:0 none;
padding:8px 12px;
margin:0;
cursor:pointer;
border:1px solid;
font:bold 9pt/100% Arial, Helvetica, sans-serif;

-moz-border-radius:0px 5px 5px 0px;
-webkit-border-radius:0px 5px 5px 0px;
border-radius:0px 5px 5px 0px;

-moz-box-shadow:0px 0px 1px #fff inset;
-webkit-box-shadow:0px 0px 1px #fff inset;
box-shadow:0px 0px 1px #fff inset;
}


.uploader input[type=file]{
position:absolute;
top:0; right:0; bottom:0;
border:0;
padding:0; margin:0;
height:30px;
cursor:pointer;
filter:alpha(opacity=0);
-moz-opacity:0;
-khtml-opacity: 0;
opacity:0;
}

input[type=button]::-moz-focus-inner{padding:0; border:0 none; -moz-box-sizing:content-box;}
input[type=button]::-webkit-focus-inner{padding:0; border:0 none; -webkit-box-sizing:content-box;}
input[type=text]::-moz-focus-inner{padding:0; border:0 none; -moz-box-sizing:content-box;}
input[type=text]::-webkit-focus-inner{padding:0; border:0 none; -webkit-box-sizing:content-box;}

/* White Color Scheme ------------------------ */

.white .button{
color:#555;
text-shadow:1px 1px 0px #fff;
background:#ddd;
background:-moz-linear-gradient(top, #eeeeee 0%, #dddddd 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#eeeeee), color-stop(100%,#dddddd));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#eeeeee', endColorstr='#dddddd',GradientType=0);
border-color:#ccc;
}

.white:hover .button{
background:#eee;
background:-moz-linear-gradient(top, #dddddd 0%, #eeeeee 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#dddddd), color-stop(100%,#eeeeee));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#dddddd', endColorstr='#eeeeee',GradientType=0);
}
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',title:'文件导入'"
			style="overflow: hidden;height:140px;align:center">
			<form id="formImport" action="" method="post"
				enctype="multipart/form-data" style="width: 600px;padding:10px;line-height: 18px">
				
				<label class="uploader-label">病例文件（xml/zip）：</label>
				<div class="uploader white">
					<input type="text" class="filename" readonly="readonly"/>
					<input type="button" name="file" class="button" value="浏览..."/>
					<input type="file" size="30" id="importfile" name="importfile"/>
				</div>
				<div class="uploader white" style="margin:10px 0 10px 5px">
				<input id="btnImport" type="button" name="file" class="button" value="导 入"/>
				</div>	
			</form>
			<div style="padding:0px 22px;line-height: 18px;color:grey">
			<span style="color: red">注意</span>：只能导入xml文件或包含xml文件的zip文件。
			导入的病案数据中组织机构代码（ZA02C），病案号（AAA28）和出院时间（AAC01）不能为空。
			</div>
		</div>
		<div id="importInfo" data-options="region:'center',title:'导入过程信息'" style="padding:5px;line-height: 18px"></div>
	</div>
	<script type="text/javascript">
	$(function(){
		$("input[type=file]").change(function(){$(this).parents(".uploader").find(".filename").val($(this).val());});
		
		$("#btnImport").click(function(){
			$.messager.progress({
				//msg:'正在导入...',
				text:'正在导入...'
			});
			$('#formImport').form('submit',{  
	            url: "${pageContext.request.contextPath}/wt4/medicalRecord/importFile.do",  
	            onSubmit: function(){
	            	var filename = $("#importfile").val();
	            	if(!filename){
	            		$.messager.alert('提示','请先选择一个文件！','warning');
	            		return false;
	            	}else if(!(/.+\.(xml|zip)$/i.test(filename))){
	            		$.messager.alert('提示','文件格式错误！只能导入xml或zip文件！','warning');
	            		return false;
	            	}
	            	$("#importInfo").append('正在导入文件 <b>'+filename+'</b>，请稍候...<br/>');
	                return true;
	            },  
	            success: function(result){  
	                var result = $.parseJSON(result);  
	                if (result.success){  
	                	$.messager.show({  
	                        title: '提示',  
	                        msg: result.message  
	                    }); 
	                	var data = result.data;
	                	var html = "导入成功！  总共：<b>"+data.total+"</b>；  新增：<b>"+data.inserted
	                			+"</b>；  更新：<b>"+data.updated+"</b>。<br/>";
	                	$("#importInfo").append(html);
	                } else {
	                    $.messager.show({  
	                        title: '错误',  
	                        msg: result.message  
	                    });  
	                	$("#importInfo").append(result.message+"<br/>");
	                }
	                $.messager.progress('close');
	            }
	        }); 
			
		});
		
	});
	</script>
</body>
</html>