$('#ABDS').datagrid({
								toolbar : [{
									text : '新增',
									iconCls : 'icon-add',
									handler : function() {
										var $grid = $('#ABDS');
										var lastIndex = $grid.data("lastIndex");
										if(!$grid.datagrid('validateRow', lastIndex)){
											return;
										}
										$grid.datagrid('endEdit', lastIndex);
										$grid.datagrid('appendRow', {
											ABD01C : '',
											ABD01N : '',
											ABD03C : ''
										});
										lastIndex = $grid.datagrid('getRows').length - 1;
										$grid.datagrid('beginEdit', lastIndex);
										$grid.data("lastIndex",lastIndex);
									}
								}, '-', {
									text : '删除',
									iconCls : 'icon-remove',
									handler : function() {
										var $grid = $('#ABDS');
										var row = $grid.datagrid('getSelected');
										if(row) {
											var index = $grid.datagrid('getRowIndex', row);
											$grid.datagrid('deleteRow', index);
										}
									}
								}, '-', {
									text : '撤销',
									iconCls : 'icon-undo',
									handler : function() {
										$('#ABDS').datagrid('rejectChanges');
									}
								}, '-', {
									text : '完成',
									iconCls : 'icon-save',
									handler : function() {
										$('#ABDS').datagrid('acceptChanges');
									}
								}],
								onBeforeLoad : function() {
									$(this).datagrid('rejectChanges');
								},
								onClickRow : function(rowIndex) {
									var $grid = $(this);
									var lastIndex = $grid.data("lastIndex");
									if(!$grid.datagrid('validateRow', lastIndex)){
										return;
									}
									if(lastIndex != rowIndex) {
										$grid.datagrid('endEdit', lastIndex);
										$grid.datagrid('beginEdit', rowIndex);
									}else{
										$grid.datagrid('beginEdit', rowIndex);
									}
									$grid.data("lastIndex",rowIndex);
								}
							});

$('#ACA09S').datagrid({
	toolbar : [{
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			var $grid = $('#ACA09S');
			var lastIndex = $grid.data("lastIndex");
			if(!$grid.datagrid('validateRow', lastIndex)){
				return;
			}
			$grid.datagrid('endEdit', lastIndex);
			$grid.datagrid('appendRow', {
				ACA0901C : '',
				ACA0901N : '',
				ACA0902C : '',
				ACA0903C : ''
			});
			lastIndex = $grid.datagrid('getRows').length - 1;
			$grid.datagrid('beginEdit', lastIndex);
			$grid.data("lastIndex",lastIndex);
		}
	}, '-', {
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			var $grid = $('#ACA09S');
			var row = $grid.datagrid('getSelected');
			if(row) {
				var index = $grid.datagrid('getRowIndex', row);
				$grid.datagrid('deleteRow', index);
			}
		}
	}, '-', {
		text : '撤销',
		iconCls : 'icon-undo',
		handler : function() {
			$('#ACA09S').datagrid('rejectChanges');
		}
	}, '-', {
		text : '完成',
		iconCls : 'icon-save',
		handler : function() {
			$('#ACA09S').datagrid('acceptChanges');
		}
	}],
	onBeforeLoad : function() {
		$(this).datagrid('rejectChanges');
	},
	onClickRow : function(rowIndex) {
		var $grid = $(this);
		var lastIndex = $grid.data("lastIndex");
		if(!$grid.datagrid('validateRow', lastIndex)){
			return;
		}
		if(lastIndex != rowIndex) {
			$grid.datagrid('endEdit', lastIndex);
			$grid.datagrid('beginEdit', rowIndex);
		}else{
			$grid.datagrid('beginEdit', rowIndex);
		}
		$grid.data("lastIndex",rowIndex);
	}
});
$('#AENS').datagrid({
	toolbar : [{
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			var $grid = $('#AENS');
			var lastIndex = $grid.data("lastIndex");
			if(!$grid.datagrid('validateRow', lastIndex)){
				return;
			}
			$grid.datagrid('endEdit', lastIndex);
			$grid.datagrid('appendRow', {
				AEN01 : '',
				AEN02C : '',
				AEN02N : ''
			});
			lastIndex = $grid.datagrid('getRows').length - 1;
			$grid.datagrid('beginEdit', lastIndex);
			$grid.data("lastIndex",lastIndex);
		}
	}, '-', {
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			var $grid = $('#AENS');
			var row = $grid.datagrid('getSelected');
			if(row) {
				var index = $grid.datagrid('getRowIndex', row);
				$grid.datagrid('deleteRow', index);
			}
		}
	}, '-', {
		text : '撤销',
		iconCls : 'icon-undo',
		handler : function() {
			$('#AENS').datagrid('rejectChanges');
		}
	}, '-', {
		text : '完成',
		iconCls : 'icon-save',
		handler : function() {
			$('#AENS').datagrid('acceptChanges');
		}
	}],
	onBeforeLoad : function() {
		$(this).datagrid('rejectChanges');
	},
	onClickRow : function(rowIndex) {
		var $grid = $(this);
		var lastIndex = $grid.data("lastIndex");
		if(!$grid.datagrid('validateRow', lastIndex)){
			return;
		}
		if(lastIndex != rowIndex) {
			$grid.datagrid('endEdit', lastIndex);
			$grid.datagrid('beginEdit', rowIndex);
		}else{
			$grid.datagrid('beginEdit', rowIndex);
		}
		$grid.data("lastIndex",rowIndex);
	}
});

$('#AEKS').datagrid({
	toolbar : [{
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			var $grid = $('#AEKS');
			var lastIndex = $grid.data("lastIndex");
			if(!$grid.datagrid('validateRow', lastIndex)){
				return;
			}
			$grid.datagrid('endEdit', lastIndex);
			$grid.datagrid('appendRow', {
				AEK01C : '',
				AEK02 : '',
				AEK03 : ''
			});
			lastIndex = $grid.datagrid('getRows').length - 1;
			$grid.datagrid('beginEdit', lastIndex);
			$grid.data("lastIndex",lastIndex);
		}
	}, '-', {
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			var $grid = $('#AEKS');
			var row = $grid.datagrid('getSelected');
			if(row) {
				var index = $grid.datagrid('getRowIndex', row);
				$grid.datagrid('deleteRow', index);
			}
		}
	}, '-', {
		text : '撤销',
		iconCls : 'icon-undo',
		handler : function() {
			$('#AEKS').datagrid('rejectChanges');
		}
	}, '-', {
		text : '完成',
		iconCls : 'icon-save',
		handler : function() {
			$('#AEKS').datagrid('acceptChanges');
		}
	}],
	onBeforeLoad : function() {
		$(this).datagrid('rejectChanges');
	},
	onClickRow : function(rowIndex) {
		var $grid = $(this);
		var lastIndex = $grid.data("lastIndex");
		if(!$grid.datagrid('validateRow', lastIndex)){
			return;
		}
		if(lastIndex != rowIndex) {
			$grid.datagrid('endEdit', lastIndex);
			$grid.datagrid('beginEdit', rowIndex);
		}else{
			$grid.datagrid('beginEdit', rowIndex);
		}
		$grid.data("lastIndex",rowIndex);
	}
});