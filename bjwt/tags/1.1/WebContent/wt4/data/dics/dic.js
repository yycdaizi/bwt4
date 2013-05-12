var dic = {};

var dicfiles = [ "dic-address.js", "dic-area.js", "dic-binAnZhiLiang.js",
		"dic-bloodType.js", "dic-BLZD.js", "dic-bool.js", "dic-country.js",
		"dic-diagnosticEvidence.js", "dic-fenHuaChengDu.js",
		"dic-haveOrNot.js", "dic-ICD10.js", "dic-ICD9.js", "dic-ICU.js",
		"dic-liYuanFangShi.js", "dic-maritalStatus.js", "dic-maZuiFangShi.js",
		"dic-medicalSubject.js", "dic-nation.js", "dic-paymentMethod.js",
		"dic-profession.js", "dic-qieKouYuHe.js", "dic-relationship.js",
		"dic-Rh.js", "dic-ruYuanBingQing.js", "dic-ruYuanTuJing.js",
		"dic-sex.js", "dic-streat.js", "dic-surgeryLevel.js", "dic-TNM-M.js",
		"dic-TNM-N.js", "dic-TNM-T.js", "dic-zhongLiuFenQi.js" ];
for(var i=0,len=dicfiles.length;i<len;i++){
	var path;
	$("script").each(function(){
		src = $(this).attr("src");
		var result = /^(.*)dic\.js$/.exec(src);
		if(result){
			path = result[1];
			return false;
		}
	});
	document.writeln('<script type="text/javascript" src="'+path + dicfiles[i]+'"></script>');
}