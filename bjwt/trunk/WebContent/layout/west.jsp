<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#layout_west_tree').tree({
			url:'${pageContext.request.contextPath}/authority/menu.do',
			lines : true,
			onClick : function(node) {
				if(node.attributes){
					$("#content").attr('src',node.attributes.url);
				}
			}
		});
	});
</script>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<ul id="layout_west_tree"></ul>
</div>