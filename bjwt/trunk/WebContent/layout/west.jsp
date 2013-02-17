<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {

		$('#layout_west_tree').tree({
			data:[{
				text:'字典管理',
				attributes:{
					url:'dicdata/manager.jsp'
				}
			},{
				text:'病案管理',
				children:[{
					text:'病案查询',
					attributes:{
						url:'wt4/list.jsp'
					}
				},{
					text:'病案导入',
					attributes:{
						url:'wt4/import.html'
					}
				}]
			}],
			parentField : 'pid',
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