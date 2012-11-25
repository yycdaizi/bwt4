/*
 病案Xml生成类
 */
/**第一页*/
//患者信息
var AAATags = ["AAA01", "AAA02C", "AAA03", "AAA04", "AAA05C", "AAA40", "AAA42", "AAA06C", "AAA07", "AAA08C", "AAA09", "AAA10", "AAA11", "AAA43", "AAA44", "AAA45", "AAA46", "AAA47", "AAA13C", "AAA33C", "AAA14C", "AAA48", "AAA49", "AAA50", "AAA16C", "AAA36C", "AAA17C", "AAA18C", "AAA19", "AAA20", "AAA21C", "AAA22", "AAA23C", "AAA24", "AAA25", "AAA26C", "AAA27", "AAA28", "AAA29"];
//入院信息
var AABTags = ["AAB01",/*入院日期(时)*/ "AAB02C", "AAB03", "AAB06C"];
//出院信息
var AACTags = ["AAC01",/*出院日期*/ "AAC02C", "AAC03", "AAC04"];
//转经信息
var AADTags = ["AAD01C"];

/**第二页*/
//门急诊诊断信息
var ABATags = ["ABA01C", "ABA01N"];
//出院主要诊断信息
var ABCTags = ["ABC01C", "ABC01N", "ABC03C"];
//病理诊断信息
var ABFTags = ["ABF01C", "ABF01N", "ABF04", "ABF02C", "ABF03C"];
//损伤中毒诊断信息
var ABGTags = ["ABG01C", "ABG01N"];
//肿瘤分期
var ABHTags = ["ABH01C", "ABH0201C", "ABH0202C", "ABH0203C", "ABH03C"];

/**其他情况*/
//药物过敏信息
var AEBTags = ["AEB02C", "AEB01"];
//病案质控信息
var AEDTags = ["AED01C", "AED02", "AED03", "AED04"];
//医师信息
var AEETags = ["AEE01", "AEE02", "AEE03", "AEE11", "AEE09", "AEE04", "AEE05", "AEE07", "AEE08", "AEE10"];
//血液信息
var AEGTags = ["AEG01C", "AEG02C", "AEG04", "AEG05", "AEG06", "AEG07", "AEG08"];
//颅脑患者昏迷情况
var AEJTags = ["AEJ01", "AEJ02", "AEJ03", "AEJ04", "AEJ05", "AEJ06"];
//离院情况
var AEMTags = ["AEM01C", "AEM02", "AEM03C", "AEM04"];
//其他信息
var AEITags = ["AEI01C", "AEI09", "AEI10", "AEI08"];
//费用情况
var ADATags = ["ADA01", "ADA0101", "ADA11", "ADA21", "ADA22", "ADA23", "ADA24", "ADA25", "ADA26", "ADA27", "ADA28", "ADA13", "ADA15", "ADA12", "ADA29", "ADA03", "ADA30", "ADA31", "ADA32", "ADA07", "ADA08", "ADA33", "ADA34", "ADA35", "ADA36", "ADA37", "ADA38", "ADA02", "ADA39", "ADA09", "ADA10", "ADA04", "ADA40", "ADA41", "ADA42", "ADA43", "ADA44", "ADA05", "ADA06", "ADA20"];

//其他诊断情况表
var ABDTags = ["ABD01C", "ABD01N", "ABD03C"];
//新生儿表格
var AENTags = ["AEN01", "AEN02C", "AEN02N"];

//重症情况
var AEKTags = ["AEK01C", "AEK02", "AEK03"];

//手术情况
var ACATags = ["ACA01", "ACA11", "ACA02", "ACA03", "ACA04", "ACA06C", "ACA07C", "ACA08", "ACA10C"];
//术式信息
var ACA09Tags = ["ACA0901C", "ACA0901N", "ACA0902C", "ACA0903C"];

function CaseXmlWriter(){
    this.xmlstr = "";
    this.writer = new XMLWriter('UTF-8', '1.0');
    this.generateCase = function(){
        this.CaseHeader();
        this.writer.writeStartElement("CASE");
        //A首页
        this.writer.writeStartElement("A");
        //AA患者基本情况
        this.writer.writeStartElement("AA");
        //AAA，患者基本信息
        this.writer.writeStartElement("AAA");
        for (var i = 0; i < AAATags.length; i++) {
            this.writeCaseElement(AAATags[i]);
        }
        this.writer.writeEndElement();
        //End AAA患者基本信息
        //AAB 入院信息
        this.writer.writeStartElement("AAB");
        for (var i = 0; i < AABTags.length; i++) {
            this.writeCaseElement(AABTags[i]);
        }
        this.writer.writeEndElement();
        //End AAB
        //AAC 出院信息
        this.writer.writeStartElement("AAC");
        for (var i = 0; i < AACTags.length; i++) {
            this.writeCaseElement(AACTags[i]);
        }
        this.writer.writeEndElement();
        //End AAC
        //AAD 转经信息
        this.writeCaseElement("AAD01C");
        //End AAD
        this.writer.writeEndElement();
        //End AA 患者基本情况
        
        //AB 诊断情况
        this.writer.writeStartElement("AB");
        this.writer.writeStartElement("ABA");
        this.writeTags(ABATags);
        this.writer.writeEndElement();
        
        this.writer.writeStartElement("ABC");
        this.writeTags(ABCTags);
        this.writer.writeEndElement();
        //ABD 其他诊断集合===========================================
        this.writeQTZDQKGrid();
        
        this.writer.writeStartElement("ABF");
        this.writeTags(ABFTags);
        this.writer.writeEndElement();
        this.writer.writeStartElement("ABG");
        this.writeTags(ABGTags);
        this.writer.writeEndElement();
        this.writer.writeEndElement();
        //End 诊断情况
        
        //AC 手术情况======================================================
        this.writer.writeStartElement("AC");
        this.writeSurgeryGrid();
        this.writer.writeEndElement();
        //End AC 手术情况
        //AE 其他情况
        this.writer.writeStartElement("AE");
        this.writer.writeStartElement("AEB");
        this.writeTags(AEBTags);
        this.writer.writeEndElement();
        this.writer.writeStartElement("AED");
        this.writeTags(AEDTags);
        this.writer.writeEndElement();
        this.writer.writeStartElement("AEE");
        this.writeTags(AEETags);
        this.writer.writeEndElement();
        this.writer.writeStartElement("AEG");
        this.writeTags(AEGTags);
        this.writer.writeEndElement();
        this.writer.writeStartElement("AEJ");
        this.writeTags(AEJTags);
        this.writer.writeEndElement();
        this.writer.writeEndElement();
        //End AE 其他情况
        
        //重症监护情况=================================================
        this.writeZZQKGrid();
        //呼吸机使用情况
        this.writeCaseElement("AEL01");
        //新生儿表格=======================================================
        this.writeXSEQKGrid();
        
        //AD 费用情况
        this.writer.writeStartElement("AD");
        this.writer.writeStartElement("ADA");
        this.writeTags(ADATags);
        this.writer.writeEndElement();
        this.writer.writeEndElement();
        //End AD 费用情况
        this.writer.writeEndElement();
        //End A首页
        this.EndCaseHeader();
        //End XML
        var xmlStr = this.writer.flush();
        //generic exist checker
        var checkOrder = ["AAA", "AAB", "AAC", "AAD", "AA", "ABA", "ABB", "ABC", "ABE", "ABF", "ABG", "AB", "ACA", "ACAS", "AC", "ADA", "AD", "AEA", "AEB", "AEC", "AED", "AEE", "AEF", "AEG", "AEH", "AEI", "AEJ", "AE", "A", "CASE"];
        return this.checkXml(xmlStr, checkOrder);
    };
    this.checkXml = function(xmlStr, checkOrder){
        var retXml;
        xmlDoc = this.loadXMLString(xmlStr);
        for (var i = 0; i < checkOrder.length; i++) {
            var node = xmlDoc.getElementsByTagName(checkOrder[i]);
            if (node.length > 0) {
                if (node[0].childNodes.length == 0) {
                    node[0].parentNode.removeChild(node[0]);
                }
            }
        }
        if (document.all) {
            retXml = xmlDoc.xml;
        }
        else {
            retXml = (new XMLSerializer()).serializeToString(xmlDoc);
        }
        return retXml;
    };
    this.loadXMLString = function(txt){
        try //Internet Explorer
        {
            xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
            xmlDoc.async = "false";
            xmlDoc.loadXML(txt);
            return (xmlDoc);
        } 
        catch (e) {
            try //Firefox, Mozilla, Opera, etc.
            {
                parser = new DOMParser();
                xmlDoc = parser.parseFromString(txt, "text/xml");
                return (xmlDoc);
            } 
            catch (e) {
                alert(e.message);
            }
        }
        return (null);
    };
    this.init = function(){
        this.writer.indentChar = ' ';//indent with spaces
        this.writer.indentation = 2;//add 2 spaces per level
    };
    this.CaseHeader = function(){
        this.writer.writeStartDocument();
        this.writer.writeStartElement("CASES");
    };
    this.EndCaseHeader = function(){
        this.writer.writeEndElement();
        this.writer.writeEndDocument();
    };
    this.writeCaseElement = function(caseId){
        var value = this.getCaseValue(caseId);
        if (value != null && value != "") {
            this.writer.writeElementString(caseId, value);
        }
    };
    this.writeCaseElementSel = function(caseId){
        var value = this.getSelValue(caseId);
        if (value != null && value != "") {
            this.writer.writeElementString(caseId, value);
        }
    };
    this.writeMultiSelNode = function(caseId){
    };
    this.writeCommonNode = function(nodeName, nodeValue){
        if (nodeValue != null && nodeValue.length() > 0) {
            this.writer.writeElementString(nodeName, nodeValue);
        }
    };
    this.writeTags = function(tags){
        for (var i = 0; i < tags.length; i++) {
            this.writeCaseElement(tags[i]);
        }
    };
    this.getCaseValue = function(caseId){
        var val = $("#" + caseId).val();
        if ($.isEmptyObject(val)) {
            val = $("#" + caseId).parent().find("input[name='" + caseId + "']").val();
        }
        return val;
    };
    this.getCommonVal = function(caseId){
        return $("#" + caseId).val();
    };
    this.getSelValue = function(caseId){
        return $("#" + caseId).combobox('getValue');
    };
    this.writeAge = function(){
        var birthdate = $("#AAA03").parent().find("input[name='AAA03']");
        var age = null;
        var birthday = new Date(birthdate.toString().replace(/-/g, "\/"));
        var d = new Date();
        age = d.getFullYear() - birthday.getFullYear() - ((d.getMonth() < birthday.getMonth() || d.getMonth() == birthday.getMonth() && d.getDate() < birthday.getDate()) ? 1 : 0);
        if (!$.isEmptyObject(birthdate)) {
            this.writer.writeElementString("AAA04", age.toString());
        }
    };
    this.getGridData = function(id, colTags){
        var tableData = new Array();
        var tb_node = $("#" + id);
        tb_node.find("tr").each(function(trindex, tritem){
            tableData[trindex] = new Object();
            $.each(colTags, function(i, tag){
                var val = $(tritem).find("td." + tag).find("input").val();
                tableData[trindex][tag] = val;
            });
        });
        return tableData;
    };
    /**
     * 输出表格xml
     * @param {Object} id -- xml样例一致
     * @param {Object} colTags
     */
    this.writeDataGrid = function(id, colTags){
        var data = this.getGridData(id, colTags);
        if (!this.isNotEmptyTable(data, colTags)) 
            return;
        this.writer.writeStartElement(id);
        for (var i = 0; i < data.length; i++) {
            var rowData = data[i];
            if (this.isRowNotNull(rowData, colTags)) {
                var subId = id.substring(0, id.length - 1);
                this.writer.writeStartElement(subId);
                for (var j = 0; j < colTags.length; j++) {
                    this.writer.writeElementString(colTags[j], rowData[colTags[j]]);
                }
                this.writer.writeEndElement();
            }
        }
        this.writer.writeEndElement();
    };
    this.isNotEmptyTable = function(data, colTags){
        for (var i = 0; i < data.length; i++) {
            var rowData = data[i];
            if (this.isRowNotNull(rowData, colTags)) {
                return true;
            }
        }
        return false;
    };
    this.isRowNotNull = function(rowData, tags){
        for (var i = 0; i < tags.length; i++) {
            var prop = tags[i];
            if (!$.isEmptyObject(rowData[prop])) {
                return true;
            }
        }
        return false;
    };
    /**
     * 手术情况
     */
    this.writeSurgeryGrid = function(){
        this.writer.writeStartElement("ACAS");
        var topwriter = this;
        var surgerys = SurgeryPanel.getData();
        $.each(surgerys, function(index, item){
            if (!$.isEmptyObject(item)) {
                topwriter.writer.writeStartElement("ACA");
                for (var i = 0; i < ACATags.length; i++) {
                    topwriter.writeSurgeryData(item, ACATags[i]);
                }
                topwriter.writeOperationGrid(item["ACA09S"]);
                topwriter.writer.writeEndElement();
            }
        });
        this.writer.writeEndElement();
    };
    this.writeSurgeryData = function(surData, tag){
        var val = surData[tag];
        if (!$.isEmptyObject(val)) {
            this.writer.writeElementString(tag, val);
        }
    };
    this.writeOperationGrid = function(dataArray){
        var id = "ACA09S";
        var colTags = ACA09Tags;
        var data = dataArray;
        if (!this.isNotEmptyTable(data, colTags)) 
            return;
        this.writer.writeStartElement(id);
        for (var i = 0; i < data.length; i++) {
            var rowData = data[i];
            if (this.isRowNotNull(rowData, colTags)) {
                var subId = id.substring(0, id.length - 1);
                this.writer.writeStartElement(subId);
                for (var j = 0; j < colTags.length; j++) {
                    if ($.isEmptyObject(rowData[colTags[j]])) 
                        continue;
                    this.writer.writeElementString(colTags[j], rowData[colTags[j]]);
                }
                this.writer.writeEndElement();
            }
        }
        this.writer.writeEndElement();
    };
    /**
     * 重症情况
     * @param {Object} id
     * @param {Object} colTags
     */
    this.writeZZQKGrid = function(){
        this.writeEasyUIGrid("AEKS", AEKTags);
    };
    /**
     * 其他诊断情况
     */
    this.writeQTZDQKGrid = function(){
        this.writeEasyUIGrid("ABDS", ABDTags);
    };
    /**
     * 新生儿情况
     */
    this.writeXSEQKGrid = function(){
        this.writeEasyUIGrid("AENS", AENTags);
    };
    this.writeEasyUIGrid = function(id, tags){
       
        var data = $("#" + id).datagrid('getData');
        if (!this.isNotEmptyTableEasyUI(data, tags)) 
            return;
        this.writer.writeStartElement(id);
        for (var i = 0; i < data.rows.length; i++) {
            var rowData = data.rows[i];
            if (this.isRowNotNull(rowData, tags)) {
                var subId = id.substring(0, id.length - 1);
                this.writer.writeStartElement(subId);
                for (var j = 0; j < tags.length; j++) {
                    this.writer.writeElementString(tags[j], rowData[tags[j]]);
                }
                this.writer.writeEndElement();
            }
        }
        this.writer.writeEndElement();
    };
    this.isNotEmptyTableEasyUI = function(data, colTags){
        for (var i = 0; i < data.rows.length; i++) {
            var rowData = data.rows[i];
            if (this.isRowNotNull(rowData, colTags)) {
                return true;
            }
        }
        return false;
    };
}
