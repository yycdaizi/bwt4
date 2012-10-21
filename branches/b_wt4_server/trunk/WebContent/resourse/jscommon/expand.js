/**
 * easy ui 扩展
 * @author ying
 */
 
 /**
  * 扩展验证类型
  */
$.extend($.fn.validatebox.defaults.rules,{  
   mobile:{  
       validator:function(value,param){  
           return /^(13[0-9]|15[0|1|2|3|6|7|8|9]|18[6|8|9])\d{8}$/.test(value);  
       },  
       message:'请输入正确的11位手机号码.格式:13120002221'  
   },  
   postcode:{  
   validator:function(value,param){  
       return /^\d{6}$/.test(value);  
   },  
   message:'请输入正确的6位邮政编码'  
      }  
});

/**
 * 设置下拉框默认的过滤方法
 */
$.fn.combobox.defaults.filter = function(q,row){
	var options=$(this).combobox("options");
	return row[options.textField].indexOf(q)>=0;
};

 /**
  * 扩展表格
  */
$.extend($.fn.datagrid.defaults.editors, {
    datetimebox: {
        init: function(container, options){
            var input = $('<input class="easyui-datetimebox">').appendTo(container);
            return input.datetimebox();
        },
        getValue: function(target){
            return $(target).datetimebox("getValue");
        },
        setValue: function(target, value){
            $(target).datetimebox("setValue", value);
        },
        resize: function(target, width){
            var input = $(target);
            if ($.boxModel == true) {
                input.width(width - (input.outerWidth() - input.width()));
            }
            else {
                input.width(width);
            }
        }
    }
});
$.extend($.fn.datagrid.defaults.editors, {
	my97datetimebox : {
		init : function(container, options) {
			var input = $('<input type="text" class="datagrid-editable-input" />').appendTo(container);
			input.validatebox(options);
			input.click(function(){
        			WdatePicker(options);
        		});
			return input;
		},
		destroy:function(target){
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
			var input = $('<input type="text" class="datagrid-editable-input" />').appendTo(container);
			input.validatebox(options);
			var source = options["source"];
    		input.autocomplete(source, options);
    		var resultHandler = options["resultHandler"];
    		if(resultHandler){    			
    			input.result(resultHandler);
    		}
			return input;
		},
		destroy:function(target){
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
jQuery.fn.getValue = function(id){
	var el = this;
	if(el.hasClass("easyui-combobox")){
		if(el.combobox("options").multiple){
			return el.combobox("getValues");
		}else{
			return el.combobox("getValue");
		}
	}        		
	return el.val();
};