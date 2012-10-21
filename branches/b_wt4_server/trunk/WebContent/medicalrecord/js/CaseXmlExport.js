/**
 * 校验form
 */
function checkValid(){
    var isvalid1 = $("#formTab1").form('validate');
    var isvalid2 = $("#formTab2").form('validate');
    var isvalid3 = $("#surgery").form('validate');
    var isvalid4 = $("#formTab4").form('validate');
    var isvalid5 = $("#formTab5").form('validate');
    var isvalid = (isvalid1 && isvalid2 && isvalid3 && isvalid4 && isvalid5);
	return isvalid;
}

function caseXmlExportClick(){
    var isvalid = $(document).form('validate');
    if (!isvalid) {
        alert("输入数据有误，请修正！");
        return false;
    }
    var xmlWriter = new CaseXmlWriter();
    var xmlStr = xmlWriter.generateCase();
    alert(xmlStr);
}
