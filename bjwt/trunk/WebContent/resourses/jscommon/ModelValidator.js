;(function($){
	var Model = Class.extend({
		//构造函数
		init : function(){
			var fields = this.getModel().fields;
			for(var key in fields){
				//只遍历自身属性
				if(!fields.hasOwnProperty(key)){
					continue;
				}
				var defaultValue = fields[key].defaultValue;
				switch($.type(defaultValue)){
				    case 'undefined':
				    	this[key] = null;
				        break;
				    case 'boolean':
				    case 'number':
				    case 'string':
				    case 'function':
				        this[key] = defaultValue;
				        break;
				    case 'array':
                        this[key] = $.extend(true, new Array(), defaultValue);
                        break;
				    default:
				        this[key] = $.extend(true, {}, defaultValue);
				}
			}
		},
		/**
		 * 继承自Model的类的对象可以调用自身的validate方法来验证。
		 * @returns
		 */
		validate : function(){
			return $.ModelValidator.validate(this.getModel(), this);
		},
		getModel : function(){
			return this.constructor;
		}
	},{
		fields : {},
		/**
		 * 用此Model来验证普通对象
		 * @param obj 被验证的对象，可以不是此Model的实例，比如json对象
		 * @returns
		 */
		validate : function(obj){
			return $.ModelValidator.validate(this, obj);
		}
	});
	
	var Regex = {
		decimal	: /^-?(?:0|[1-9]\d*)(?:\.\d+)?$/,
		integer : /^-?(?:0|[1-9]\d*)$/,
		numeric : /^[0-9]+$/,
		email : /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
		url : /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i,
		postalCode : /^[1-9]\d{5}$/i
	};
	$.ModelValidator = {
		//"rule[$F(function(){}),]"
		keyword : {
			MSG : '@',
			FUNC : '$F',
			EL : '$E',
			SELECTOR : '$S'
		},
		rules : {},
		definedModel : function(fields){
			var model = Model.extend({},{fields:fields});
			//model.fields = fields; 
			return model;
		},
		validate : function(model, obj){
			var errors = [];
			var fields = model.fields;
			for(var key in fields){
				//只遍历自身属性
				if(!fields.hasOwnProperty(key)){
					continue;
				}
				var field = fields[key]||{};
				var validators = field.validators||[];
				$.each(validators, function(i, n){
					n = $.ModelValidator.parseValidator.call(obj,n);
					var ruleName = n.rule;
					var rule = $.ModelValidator.rules[ruleName];
					var param = n.param;
					if(rule){
						var ret = rule.validate.call(obj, field, obj[key], null, param);
						
						var result = true, message=n.message;
						if(!ret){
							result = false;
							message = message||$.ModelValidator.rules[ruleName].message;
						}else{
							var r = (ret).result;
							if($.type(r)=='boolean'&&r==false){
								result = false;
								message = message||(ret).message||$.ModelValidator.rules[ruleName].message;
							}
						}
						if(!result){
							errors.push({
								field : key,
								message : $.ModelValidator.format(message, param)
							});
							return false;
						}
					}
				});				
			}
			return errors;
		},
		parseValidator : function(val){
			// TODO
			if($.type(val) == 'string'){
				var result = /([a-zA-Z_]+)(.*)/.exec(val);
				return {
					rule : result[1],
					param : eval(result[2])
				};
			}else{
				return val;
			}
		},
		format : function(msg, param){
			// TODO
			if (param) {
				for ( var i = 0; i < param.length; i++) {
					msg = msg.replace(new RegExp("\\{" + i + "\\}", "g"), param[i]);
				}
			}
			return msg;
		}
	};
	$.extend($.ModelValidator.rules, {
		required : {
			validate : function(field, value, element, param){
				param = param||[];
				var depend = param[0];
				switch($.type(depend)){
				    case 'undefined':
				        break;
				    case 'boolean':
				        if(depend==false){
				            return true;
				        }
				        break;
			        case 'function':
			            if(!depend.apply(this, arguments)){
			                return true;    
			            }
			            break;
			        default:
			            $.error('required验证器定义出错！');
				}
				return $.trim(value).length>0;
			},
			message : "必填项"
		},
		func : {
			validate : function(field, value, element, param){
				return param[0].apply(this, arguments);
			},
			message : "自定义验证未通过"
		},
		length : {
			validate : function(field, value, element, param){
				var len = $.trim(value).length;
				return len >= param[0] && len <= param[1];
			},
			message : "长度必须在{0}和{1}之间"
		},
		maxLength : {
			validate : function(field, value, element, param){
				return $.trim(value).length <= param[0];
			},
			message : "长度不得大于{0}个字符"
		},
		minLength : {
			validate : function(field, value, element, param){
				return $.trim(value).length >= param[0];
			},
			message : "长度不得小于{0}个字符"
		},
		number : {
			validate : function(field, value, element, param){
				if($.trim(value).length==0){
					return true;
				}
				param = param||[];
				var precision = param[0];
				switch($.type(precision)){
                    case 'undefined':
                        return Regex.decimal.test(value);
                        break;
                    case 'number':
                        if(precision === 0){
                            return Regex.integer.test(value);
                        }else if(precision>0){
                            var regStr = "^-?(?:0|[1-9]\\d*)(?:\\.\\d{"+precision+"})?$";
                            var reg = new RegExp(regStr);
                            return reg.test(value);
                        }else{
                            $.error('number验证器定义出错！');
                        }
                        break;
                    default:
                        $.error('number验证器定义出错！');
                }
			},
			message : "必须为数值"
		},
		integer : {
			validate : function(field, value, element, param){
				if($.trim(value).length==0){
					return true;
				}
				return Regex.integer.test(value);
			},
			message : "必须为整数"
		},
		range : {
			validate : function(field, value, element, param){
				if($.trim(value).length==0){
					return true;
				}
				value = parseFloat(value);
				return value >= param[0] && value <= param[1];
			},
			message : "值必须在{0}和{1}之间"
		},
		max : {
			validate : function(field, value, element, param){
				if($.trim(value).length==0){
					return true;
				}
				value = parseFloat(value);
				return value <= param[0];
			},
			message : "不得大于{0}"
		},
		min : {
			validate : function(field, value, element, param){
				if($.trim(value).length==0){
					return true;
				}
				value = parseFloat(value);
				return value >= param[0];
			},
			message : "不得小于{0}"
		},
		fixedin : {
			validate : function(field, value, element, param){
			    if(!value){
			        return true;
			    }
				var dic = param[0];
				var validField = param[1]||'value';
				switch($.type(dic)){
                    case 'undefined':
                    case 'array':
                        break;
                    case 'function':
                        dic = dic.apply(this, arguments);
                        break;
                    default:
                        $.error('fixedin验证器参数定义出错！');
                }
				if (!dic) {
					return true;
				}
				for ( var i = 0,len=dic.length; i < len; i++) {
					if (dic[i][validField] == value) {
						return true;
					}
				}
				return false;
			},
			message : "不在选择项中"
		},
		date : {
            validate : function(field, value, element, param){
                //TODO 完善
                if(!value){
                    return true;
                }
                return /^\d{4}-(?:0[1-9]|1[012])-(?:0[1-9]|[12]\d|3[01])$/.test(value);
            },
            message : "非法的日期"
        },
        datetime : {
            validate : function(field, value, element, param){
                //TODO 
                if(!value){
                    return true;
                }
                return /^\d{4}-(?:0[1-9]|1[012])-(?:0[1-9]|[12]\d|3[01]) (?:[0-1]\d|2[0-4]):(?:[0-5]\d):(?:[0-5]\d)$/.test(value);
            },
            message : "非法的时间"
        }
	});
})(jQuery);