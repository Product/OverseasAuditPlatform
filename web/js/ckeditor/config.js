/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';

    config.uiColor = '#F7F8F9'
    config.scayt_autoStartup = false
    config.language = 'zh-cn'; //中文
    config.filebrowserBrowseUrl = '../ckfinder/ckfinder.html';
    config.filebrowserImageBrowseUrl = '../ckfinder/ckfinder.html?Type=Images';
    config.filebrowserFlashBrowseUrl = '../ckfinder/ckfinder.html?Type=Flash';
    config.filebrowserUploadUrl = '../ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Files';
    config.filebrowserImageUploadUrl = '../ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Images';
    config.filebrowserFlashUploadUrl = '../ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Flash';


    // Define changes to default configuration here. For example:   
    //config.language = 'zh-cn'; //配置语言
    //config.uiColor = '#AADC6E'; //背景颜色   
    config.width = 650; //宽度   
    config.height = 300; //高度
    config.skin = 'office2003'; //编辑器样式（kama、office2003、v2）

    // 取消 “拖拽以改变尺寸”功能
    config.resize_enabled = true;

    // 基础工具栏
    //config.toolbar = "Basic";

    // 全能工具栏
    //config.toolbar = "Full";

    //自定义工具栏
 
    config.toolbar =
        [
         ['Templates', '-', 'Preview', '-', 'Source'], ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord'], ['Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RemoveFormat'], ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', 'Blockquote', 'ShowBlocks'], '/',
        ['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript'], ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'], ['Link', 'Unlink', 'Anchor'], ['Image', 'Flash', 'Table', 'HorizontalRule', 'SpecialChar'], '/',
        ['Styles', 'Format', 'Font', 'FontSize'], ['TextColor', 'BGColor'], ['Maximize', ]
        ];
   
};
