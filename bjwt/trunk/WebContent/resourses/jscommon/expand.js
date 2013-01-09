/**
 * easy ui 扩展
 * @author ying
 */

/**
 * 扩展验证类型
 */
$.extend($.fn.validatebox.defaults.rules,
		{
			minLength : {
				validator : function(value, param) {
					return value.length >= param[0];
				},
				message : '最少输入{0}个字符'
			},
			maxLength : {
				validator : function(value, param) {
					return value.length <= param[0];
				},
				message : '最多输入{0}个字符'
			},
			mobile : {
				validator : function(value, param) {
					return /^(13[0-9]|15[0|1|2|3|6|7|8|9]|18[6|8|9])\d{8}$/
							.test(value);
				},
				message : '请输入正确的11位手机号码.格式:13120002221'
			},
			postcode : {
				validator : function(value, param) {
					return /^\d{6}$/.test(value);
				},
				message : '请输入正确的6位邮政编码'
			},
			datebefore : {
				validator : function(value, param) {
					var max = $(param[0]).val();
					if (max && value) {
						return value < max;
					}
					return true;
				},
				message : '输入的时间必须在[{1}]之前'
			},
			dateafter : {
				validator : function(value, param) {
					var min = $(param[0]).val();
					if (min && value) {
						return value > min;
					}
					return true;
				},
				message : '输入的时间必须在[{1}]之后'
			},
			comboboxfixed : {
				validator : function(value, param) {
					//如果value为空，则验证通过
					if (!value) {
						return true;
					}
					var $target = $("#" + param[0]);
					var data = $target.combobox("getData");
					var options = $target.combobox("options");
					if (!options.multiple) {
						//单选
						for ( var i = 0; i < data.length; i++) {
							if (data[i][options.textField] == value) {
								return true;
							}
						}
					} else {
						//多选
						var valArray = value.split(options.separator);
						for ( var k = 0; k < valArray.length; k++) {
							var flag = false;
							for ( var i = 0; i < data.length; i++) {
								if (data[i][options.textField] == valArray[k]) {
									flag = true;
									break;
								}
							}
							if (!flag) {
								return false;
							}
						}
						return true;
					}
					return false;
				},
				message : '输入值必须在下拉选项中'
			},
			fixedin : {
				validator : function(value, param) {
					var dic = param[0];
					var validField = param[1];
					if (!(value && dic && validField)) {
						return true;
					}
					for ( var i = 0; i < dic.length; i++) {
						if (dic[i][validField] == value) {
							return true;
						}
					}
					return false;
				},
				message : '输入值必须在下拉选项中'
			}
		});

/**
 * 设置下拉框默认的过滤方法
 */
$.fn.combobox.defaults.filter = function(q, row) {
	var options = $(this).combobox("options");
	return row[options.textField].indexOf(q) >= 0;
};

/**
 * 获得焦点和单击时自动出现下拉框
 */
$.extend($.fn.combobox.defaults.box, {
	onclick : function() {
		$(this).combobox('showPanel');
	}//,
//onfocus:function(){
//	$(this).combobox('showPanel');
//}
});

/**
 * 扩展表格
 */
$.extend($.fn.datagrid.defaults.editors, {
	datetimebox : {
		init : function(container, options) {
			var input = $('<input class="easyui-datetimebox">').appendTo(
					container);
			return input.datetimebox();
		},
		getValue : function(target) {
			return $(target).datetimebox("getValue");
		},
		setValue : function(target, value) {
			$(target).datetimebox("setValue", value);
		},
		resize : function(target, width) {
			var input = $(target);
			if ($.boxModel == true) {
				input.width(width - (input.outerWidth() - input.width()));
			} else {
				input.width(width);
			}
		}
	}
});
$.extend($.fn.datagrid.defaults.editors, {
	my97datetimebox : {
		init : function(container, options) {
			options = options || {};
			var input = $(
					'<input type="text" class="datagrid-editable-input" />')
					.appendTo(container);
			input.validatebox(options);
			//将options中的my97设置复制到一个新的对象
			//因为WdatePicker(my97options);创建一个日期下拉后会改变传入的my97options，
			//如果不复制，将改变options.my97中的设置，会对下一列的编辑框初始化造成影响。
			//var my97options = options.my97||{};
			//var my97json = JSON.stringify(my97options);
			//my97options = JSON.parse(my97json);
			var my97options = $.extend({}, options.my97);
			input.click(function() {
				WdatePicker(my97options);
			});
			return input;
		},
		destroy : function(target) {
			$(target).validatebox("destroy");
		},
		getValue : function(target) {
			return $(target).val();
		},
		setValue : function(target, value) {
			$(target).val(value);
		},
		resize : function(target, width) {
			var input = $(target);
			if ($.boxModel == true) {
				input.width(width - (input.outerWidth() - input.width()));
			} else {
				input.width(width);
			}
		}
	}
});

$.extend($.fn.datagrid.defaults.editors, {
	autocompletebox : {
		init : function(container, options) {
			options = options || {};
			var input = $(
					'<input type="text" class="datagrid-editable-input" />')
					.appendTo(container);
			input.validatebox(options);
			var source = options["source"];
			input.autocomplete(source, options);
			var resultHandler = options["resultHandler"];
			if (resultHandler) {
				input.result(resultHandler);
			}
			return input;
		},
		destroy : function(target) {
			$(target).validatebox("destroy");
		},
		getValue : function(target) {
			return $(target).val();
		},
		setValue : function(target, value) {
			$(target).val(value);
		},
		resize : function(target, width) {
			var input = $(target);
			if ($.boxModel == true) {
				input.width(width - (input.outerWidth() - input.width()));
			} else {
				input.width(width);
			}
		}
	}
});

/**
 * 扩展jQuery实例方法-输入控件统一取值
 */
jQuery.fn.getValue = function(id) {
	var el = this;
	if (el.hasClass("easyui-combobox")) {
		if (el.combobox("options").multiple) {
			return el.combobox("getValues");
		} else {
			return el.combobox("getValue");
		}
	}
	return el.val();
};

/**
 * 数组深度复制
 * @returns
 */
Array.prototype.clone = function() {
	return $.extend(true, new Array(), this);
};

/**
 * 计算年龄
 * @param birth 生日
 * @param dudate 计算日期
 * @returns
 */
function getAge(birth, dudate) {
	var returnAge = -1;
	var duYear = dudate.getFullYear();
	var duMonth = dudate.getMonth() + 1;
	var duDay = dudate.getDate();
	if (!birth) {
		return returnAge;
	}
	birthYear = birth.getFullYear();
	birthMonth = birth.getMonth() + 1;
	birthDay = birth.getDate();

	if (duYear == birthYear) {
		returnAge = 0;
		if (duMonth < birthMonth) {
			returnAge = -1;//同年 则为0岁
		}
		if (duMonth == birthMonth && duDay < birthDay) {
			returnAge = -1;
		}
	} else {
		var ageDiff = duYear - birthYear; //年之差
		if (ageDiff > 0) {
			if (duMonth == birthMonth) {
				var dayDiff = duDay - birthDay;//日之差
				if (dayDiff < 0) {
					returnAge = ageDiff - 1;
				} else {
					returnAge = ageDiff;
				}
			} else {
				var monthDiff = duMonth - birthMonth;//月之差
				if (monthDiff < 0) {
					returnAge = ageDiff - 1;
				} else {
					returnAge = ageDiff;
				}
			}
		} else {
			returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
		}
	}

	return returnAge;//返回周岁年龄
}