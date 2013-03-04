/// <reference path="jquery.js"/>
/*
 * jSlider
 * version: 1.0.0 (06/09/2009)
 * @ jQuery v1.2.*
 */
(function($){
    $.extend($.fn, {
        ///<summary>
        /// apply a refbox UI
        ///</summary>
        jRefBox: function(setting){
            var ps = $.extend({
                //必输属性
                valuename: 'parentOrg.orgid',
                grid_id: 'ref_grid_org',
                grid_href: '../ref/orgref.jsp',
                grid_valuefield: 'orgid',
                grid_displayfield: 'orgname',
                dialog_id: 'ref_dialog_org',
                //
                dialog_width: 440
            }, setting);
            
            /* ---------->
             <span class="refbox" style="width: 200px; height: 20px;">
             <input type="text" id="orgref_showname" class="refbox-text" style="width: 180px; height: 20px; line-height: 20px;" />
             <input class="vaulefield"  name="parentOrg.orgid" type="hidden" />
             <span>
             <span id="btn_orgref" class="refbox-button" ></span>
             </span>
             </span>
             <-----------*/
            //输入控件
            var jrefBox = $('<span class="refbox" style="width: 200px; height: 20px;">' +
            '<input type="text" class="refbox-text" style="width: 180px; height: 20px; line-height: 20px;" />' +
            '<input class="vaulefield" name="' +
            ps.valuename +
            '" type="hidden" />' +
            '<span class="refbox-button" ></span></span>');
            this.append(jrefBox);
            
            var btn_ref = jrefBox.find('span.refbox-button');
            var valueNode = jrefBox.find('input.vaulefield');
            var displayNode = jrefBox.find('input.refbox-text');
            
            var ref_dialog = $("#" + ps.dialog_id);
            
            btn_ref.bind('click', function(){
                ref_dialog.dialog({
                    title: '选择',
                    width: 440,
                    closed: true,
                    cache: false,
                    href: ps.grid_href,
                    modal: true,
                    buttons: [{
                        text: '确定',
                        handler: function(){
                            var selRow = $('#' + ps.grid_id).datagrid('getSelected');
                            if (!selRow) {
                                $.messager.alert('错误', '没有选中的行');
                                return false;
                            }
                            valueNode.val(selRow[ps.grid_valuefield]);
                            displayNode.val(selRow[ps.grid_displayfield]);
                            ref_dialog.dialog("close");
                        }
                    }, {
                        text: '取消',
                        handler: function(){
                            ref_dialog.dialog("close");
                        }
                    }, {
                        text: '清空',
                        handler: function(){
                            valueNode.val('');
                            displayNode.val('');
                            ref_dialog.dialog("close");
                        }
                    }]
                }).dialog('open');
                ref_dialog.dialog('move', {
                    left: 200,
                    top: 100
                });
            });
            return jrefBox;
        }
    });
})(jQuery);
