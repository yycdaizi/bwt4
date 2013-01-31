/**
 * easy ui 扩展
 * @author ying
 */

/**
 * 扩展验证类型
 */

$.extend($.fn.validatebox.defaults.rules, {
	minLength : { // 判断最小长度
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : '最少输入{0}个字符。'
	},
	maxLength : {
		validator : function(value, param) {
			return value.length <= param[0];
		},
		message : '最多输入{0}个字符'
	},
	length:{validator:function(value,param){
		var len=$.trim(value).length;
			return len>=param[0]&&len<=param[1];
		},
			message:"内容长度介于{0}和{1}之间."
	},
	phone : {// 验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '格式不正确,请使用下面格式:020-88888888'
	},
	mobile : {// 验证手机号码
		validator : function(value) {
			return /^(13|15|18)\d{9}$/i.test(value);
		},
		message : '手机号码格式不正确(正确格式如：13450774432)'
	},
	phoneOrMobile:{//验证手机或电话
		validator : function(value) {
			return /^(13|15|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message:'请填入手机或电话号码,如13688888888或020-8888888'
	},
	idcard : {// 验证身份证
		validator : function(value) {
			return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
		},
		message : '身份证号码格式不正确'
	},
	floatOrInt : {// 验证是否为小数或整数
		validator : function(value) {
			return /^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);
		},
		message : '请输入数字，并保证格式正确'
	},
	currency : {// 验证货币
		validator : function(value) {
			return /^d{0,}(\.\d+)?$/i.test(value);
		},
		message : '货币格式不正确'
	},
	integer : {// 验证整数
		validator : function(value) {
			return /^[+]?[1-9]+\d*$/i.test(value);
		},
		message : '请输入整数'
	},
	unnormal : {// 验证是否包含空格和非法字符
		validator : function(value) {
			return /.+/i.test(value);
		},
		message : '输入值不能为空和包含其他非法字符'
	},
	username : {// 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
	},
	postalCode : {// 验证邮政编码
		validator : function(value) {
			return /^[1-9]\d{5}$/i.test(value);
		},
		message : '邮政编码格式不正确'
	},
	name : {// 验证姓名，可以是中文或英文
			validator : function(value) {
				return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
			},
			message : '请输入姓名'
	},same:{
		validator : function(value, param){
			if($("#"+param[0]).val() != "" && value != ""){
				return $("#"+param[0]).val() == value; 
			}else{
				return true;
			}
		},
		message : '两次输入的密码不一致！'	
	}
});
$.extend($.fn.validatebox.defaults.rules,
		{
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
 * 扩展表格编辑框
 */
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
	},
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
 * 扩展表格方法
 */
$.extend($.fn.datagrid.methods, {
	//验证表格中的所有行
	validateData : function(jq) {
		var $grid = $(jq[0]);
		var valid = true;
		for(var i=0;i<$grid.datagrid('getRows').length;i++){
			$grid.datagrid('beginEdit', i);
			if(!$grid.datagrid('validateRow', i)){
				valid = false;
			}else{
				$grid.datagrid('endEdit', i);
			}
		}
		return valid;
	}
});

/**
 * 扩展Tabs方法
 */
$.extend($.fn.tabs.methods,{
	//更新tab页标题图片
	updateIcon : function(jq, param) {
		return jq.each(function() {
			var pp = $(this).tabs('getTab',param.title);
			var tab = pp.panel("options").tab;
			tab.find("span.tabs-icon").attr("class", "tabs-icon");
			if (param.iconCls) {
				tab.find("span.tabs-title").addClass("tabs-with-icon");
				tab.find("span.tabs-icon").addClass(param.iconCls);
			} else {
				tab.find("span.tabs-title").removeClass("tabs-with-icon");
			}
		});
	}
});

/**
 * 扩展jQuery实例方法-输入控件统一取值
 */
jQuery.fn.getValue = function(id) {
	var el = this;
	if (el.hasClass("easyui-combobox")) {
		if (el.combobox("options").multiple) {
			return el.combobox("getValues").join(",");
		} else {
			return el.combobox("getValue");
		}
	}
	return el.val();
};
/**
 * 格式化字符串
 * 使用方法：formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量',...);
 * @returns 格式化后的字符串
 */
jQuery.formatString = function(str){
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 表格行中的操作按钮和分隔符
 */
jQuery.operateButton = function(iconCls,text,clickFn){
	var template='<span class="operate-btn" onclick="{2}"><span class="operate-icon {0}">{1}</span></span>';
	//var template = '&nbsp;<img src="{0}" onclick="{1}" style="cursor: pointer;"/>&nbsp;';
	return $.formatString(template,iconCls,text,clickFn);
};
jQuery.operateSplit = '<span class="operate-btn-separator"></span>';

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