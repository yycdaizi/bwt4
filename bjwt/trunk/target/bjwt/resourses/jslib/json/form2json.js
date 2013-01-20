/**
 * 将一个表单序列化为json对象，可支持子对象和数组
 * 即：
 * 表单值									对象值
 * {abc:'123'}							==> {abc:'123'}
 * {abc.ab:'123'}						==> {abc:{ab:'123'}}
 * {abc[0]:'123', abc[1]:'234'}			==> {abc:['123', '234']}
 * {abc[0].ab:'123', abc[1].bc:'234'}	==> {abc:[{ab:'123'}, {bc:'234'}]}
 * 
 */
(function($) {
	$.fn.serializeObject = function() {
		if (!this.length) {
			return false;
		}

		var $el = this, data = {};
		var formValues = $el.serializeArray();

		$.each(formValues, function() {
			var name = this.name;
			var value = this.value;

			var obj;
			var lastName;// 最末尾的属性名,abc[0].ab的lastName为ab,ab.cd.ef[0]的lastName为ef[0]
			// 处理多个'.'号
			if (name.indexOf('.') < 0) {
				// data[name] = value;
				obj = data;
				lastName = name;
			} else {
				var simpleNames = name.split('.');
				// 构建命名空间
				obj = data;
				for ( var i = 0; i < simpleNames.length - 1; i++) {
					var simpleName = simpleNames[i];
					if (simpleName.indexOf('[') < 0) {
						if (obj[simpleName] == null) {
							obj[simpleName] = {};
						}
						obj = obj[simpleName];
					} else { // 数组10.
						// 分隔
						var arrNames = simpleName.split('[');
						var arrName = arrNames[0];
						var arrIndex = parseInt(arrNames[1]);
						if (obj[arrName] == null) {
							obj[arrName] = []; // new Array();
						}
						obj = obj[arrName];
						if (obj[arrIndex] == null) {
							obj[arrIndex] = {}; // new Object();
						}
						obj = obj[arrIndex];
					}
				}
				lastName = simpleNames[simpleNames.length - 1];
				// obj[simpleNames[simpleNames.length - 1]] = value;
			}
			// 处理最末尾的属性名
			if (lastName.indexOf('[') < 0) {
				obj[lastName] = value;
			} else {
				var arrNames = lastName.split('[');
				var arrName = arrNames[0];
				var arrIndex = parseInt(arrNames[1]);
				if (obj[arrName] == null) {
					obj[arrName] = []; // new Array();
				}
				obj[arrName][arrIndex] = value;
			}
		});
		return data;
	};
	//post方式发送application/json格式请求，发送数据必须为json格式的字符串
	$.postJSON = function(url, data, successCallback, errorCallback){
		$.ajax({
			type:'POST',
			contentType : 'application/json',
			url : url,
			dataType : 'json',
			data : data,
			success : successCallback,  
          	error : errorCallback
		});
	};
})(jQuery);