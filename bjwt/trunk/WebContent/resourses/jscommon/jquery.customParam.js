/**
 * jquery发送ajax请求时，会调用jQuery.param把数据转换为请求参数。它将{a:[{b:1}]}这种数据转换为 a[1][b]=1。
 * 而spring mvc进行参数绑定的时候，会将a[1][b]=1这种格式解析为 数组/List/Set/Map类型属性a的第1个元素是一个List/Set/Map类型的对象，
 * 对象中有个(b:1)的键值对。但是，实际上我们一般想要的是 数组/List/Set/Map类型属性a 的第1个元素是一个对象，对象的b属性的值是1。
 * 因此，要将{a:[{b:1}]}这种数据转换为 a[1].b=1 才能达到我们的要求。
 * 下面的扩展方法就能满足这种需求。
 */
(function($) {
	// copy from jquery.js
	var r20 = /%20/g, rbracket = /\[\]$/;

	$.extend({
		customParam : function(a, traditional) {
			var prefix, s = [], add = function(key, value) {
				// If value is a function, invoke it and return its value
				value = jQuery.isFunction(value) ? value()
						: (value == null ? "" : value);
				s[s.length] = encodeURIComponent(key) + "="
						+ encodeURIComponent(value);
			};

			// Set traditional to true for jQuery <= 1.3.2 behavior.
			if (traditional === undefined) {
				traditional = jQuery.ajaxSettings
						&& jQuery.ajaxSettings.traditional;
			}

			// If an array was passed in, assume that it is an array of form
			// elements.
			if (jQuery.isArray(a) || (a.jquery && !jQuery.isPlainObject(a))) {
				// Serialize the form elements
				jQuery.each(a, function() {
					add(this.name, this.value);
				});
			} else {
				// If traditional, encode the "old" way (the way 1.3.2 or older
				// did it), otherwise encode params recursively.
				for (prefix in a) {
					buildParams(prefix, a[prefix], traditional, add);
				}
			}
			// Return the resulting serialization
			return s.join("&").replace(r20, "+");
		}
	});

	function buildParams(prefix, obj, traditional, add) {
		var name;
		if (jQuery.isArray(obj)) {
			// Serialize array item.
			jQuery.each(obj, function(i, v) {
				if (traditional || rbracket.test(prefix)) {
					// Treat each array item as a scalar.
					add(prefix, v);
				} else {
					// If array item is non-scalar (array or object), encode its
					// numeric index to resolve deserialization ambiguity
					// issues.
					// Note that rack (as of 1.0.0) can't currently deserialize
					// nested arrays properly, and attempting to do so may cause
					// a server error. Possible fixes are to modify rack's
					// deserialization algorithm or to provide an option or flag
					// to force array serialization to be shallow.
					buildParams(prefix + "[" + (typeof v === "object" ? i : "")
							+ "]", v, traditional, add);
				}
			});
		} else if (!traditional && jQuery.type(obj) === "object") {
			// Serialize object item.
			for (name in obj) {
				//jquery原来的代码
				//buildParams( prefix + "[" + name + "]", obj[ name ], traditional, add );
				buildParams(prefix + "." + name, obj[name], traditional, add);
			}
		} else {
			// Serialize scalar item.
			add(prefix, obj);
		}
	}
})(jQuery);