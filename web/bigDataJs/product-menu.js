
function initProTree() {
    var res = getFirstTree(0);
    $("#pro-tree").html("");
    $("#pro-tree").append(res);
}

//加载目录树
function  getMenuName() {
    var str = '<option value="-1">商品目录</option>';
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ThirdWebsiteServlet?methodName=list" ,
        dataType : "text",
        async: true,
        success: function (r) {
            $("#menuSelect").html('');
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        str += "<option value='"+ data[i].id +"'>" + data[i].name + "</option>";
                    }
                }
                $("#menuSelect").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function menuList() {
    if ($("#catalogue .content_area").is(":hidden")) {
        $("#catalogue>div").hide();
        $("#catalogue .menu_list").show();
        $("#catalogue .content_area").show();
        $("#catalogue .menu_list").children("div").removeClass("Title_tab");
        $("#menu").addClass("Title_tab");
        // 显示list
        $("#menuListContent").show();
        $("#menuTreeContent").hide();
        menuRelation(1);
        var str = '<option value="">请选择</option>';
        $.ajax({
            type: "post",
            url: projectLocation
            + "servlet/ThirdWebsiteServlet?methodName=list" ,
            dataType : "text",
            async: true,
            success: function (r) {
                $("#menuListId").html('');
                if (r == "false") {
                    return false;
                }
                else if(r =="sessionOut"){
                    doLogout();
                    return false;
                }
                else {
                    var data = $.parseJSON(r);
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            str += "<option value='"+ data[i].id +"'>" + data[i].name + "</option>";
                        }
                    }
                    $("#menuListId").html(str);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
    else {
        $("#catalogue>div").show();
        $("#catalogue .content_area").hide();
        $("#catalogue .menu_list").children("div").removeClass("Title_tab");
        $("#opera").addClass("Title_tab");
    }
}

function menuTreeContent() {
    // 隐藏list,显示关联关系
    $("#menuListContent").hide();
    $("#menuTreeContent").show();
    var str = "";
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ThirdWebsiteServlet?methodName=list" ,
        dataType : "text",
        async: true,
        success: function (r) {
            $("#websiteMenu").html('');
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        str += "<option value='"+ data[i].id +"'>" + data[i].name + "</option>";
                    }
                }
                $("#websiteMenu").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });

    var res = getFirstTree(0);
    $("#product-tree").html("");
    $("#product-tree").append(res);

    var websites = $("#websiteMenu").val() || 1;
    var rest = getFirstTree(0,undefined,undefined,websites);
    $("#websites-tree").html("");
    $("#websites-tree").append(rest);
}

function websitesChange () {
    var websites = $("#websiteMenu").val();
    var rest = getFirstTree(0,undefined,undefined,websites);
    $("#websites-tree").html("");
    $("#websites-tree").append(rest);
}

function getFirstTree (typeId,obj,hasChild,websites) {
    var str;
    if (typeId !=0 && (typeof (obj) ) != "undefined") {
        // init
        beforeInit(typeId,obj,hasChild,websites);

        str = "<ul>";

        if (obj) {
            if ($(obj).children("i").hasClass("fa-plus-circle")) {
                $(obj).children("i").removeClass('fa-plus-circle');
                $(obj).children("i").addClass("fa-minus-circle");
            }
            else {
                $(obj).children("i").removeClass('fa-minus-circle');
                $(obj).children("i").addClass("fa-plus-circle");
            }
            // 若之前请求加载过，目前只是显示和隐藏，不需要再次请求
            if ($(obj).hasClass('badge-orange')) {
                $(obj).removeClass('badge-orange');
                $(obj).next('ul').hide();
                $(obj).children("i").removeClass('fa-minus-circle');
                $(obj).children("i").addClass("fa-plus-circle");
                return ;
            }
            else {
                if ($(obj).hasClass('has')) {
                    $("#pro-tree li>span").filter('.badge-orange').removeClass('badge-orange');
                    $(obj).addClass('badge-orange');
                    $(obj).next('ul').show();
                    $(obj).children("i").removeClass('fa-plus-circle');
                    $(obj).children("i").addClass("fa-minus-circle");
                    return;
                }
            }
        }

    }
    else {
        str = "";
    }

    // 得到menuId
    var url = "";
    if ($("#menuTreeContent").is(":hidden")) {
        var menuId = "";
        menuId = $("#menuSelect").val();
        if (menuId == -1) {
            url = projectLocation + "servlet/ProductTypeServlet?methodName=typeDIR" + "&productTypeId=" + typeId;
        }
        else {
            typeId == 0 ? typeId = "" : typeId = typeId;
            url = projectLocation + "servlet/ThirdProductTypeServlet?methodName=list" + "&parent=" + typeId +"&thirdWebsite=" + menuId;
        }
    }
    else {
        if (typeof websites == "undefined") {
            $("#productType").val(typeId);
            url = projectLocation + "servlet/ProductTypeServlet?methodName=typeDIR" + "&productTypeId=" + typeId;
        }
        else {
            typeId == 0 ? typeId = "" : typeId = typeId;
            $("#thirdProductType").val(typeId);
            url = projectLocation + "servlet/ThirdProductTypeServlet?methodName=list" + "&parent=" + typeId +"&thirdWebsite=" + websites;
        }
    }

    $.ajax({
        type: "post",
        url: url,
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: false,
        success: function (r) {
            $("#websiteList").html('');
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.length > 0) {
                    $.each(data,function(i){
                        if (data[i].isVisible) {
                            str += "<li><span onclick='getFirstTree(" + data[i].id +",this,true,"+ websites +")'><i class='fa fa-plus-circle'></i>" + data[i].name + "</span></li>";
                        }
                        else {
                            str += "<li><span onclick='beforeInit("+ data[i].id +",this,false,"+websites+")'>" + data[i].name + "</span></li>";
                        }
                    });
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });


    if (typeId !=0 && (typeof (obj) ) != "undefined") {
        str += "</ul>";

        //让下级隐藏显示的上面代码已经排除，此时直接追加获取好的代码
        $(obj).children("i").removeClass('fa-plus-circle');
        $(obj).children("i").addClass("fa-minus-circle");
        $("#pro-tree li>span").filter('.badge-orange').removeClass('badge-orange');
        $(obj).addClass('badge-orange');
        $(obj).addClass('has');
        $(obj).parent('li').append(str);
    }

    return str;
}

function beforeInit(id,obj,hasChild,websites) {
    $("#hidden_edit").hide();
    $("#menuTreeAdd").hide();
    $("#menuAdd").show();
    $("#menuTreeDel").hide();
    var parentName;
    $("#hid_id").val(id);
    parentName = $(obj).parent().parent().siblings("span").text();
    if (parentName == "") {
        parentName = $(obj).text();
    }
    if (!hasChild) {
        // init方法
        if ($(obj).hasClass('badge-orange')) {
            $(obj).removeClass('badge-orange');
        }
        else {
            $("#pro-tree li>span").filter('.badge-orange').removeClass('badge-orange');
            $(obj).addClass('badge-orange');
        }

    }

    if ($("#menuTreeContent").is(":hidden")) {
        initProducttype(id,parentName);
    }
    else {
        if (typeof websites == "undefined") {
            $("#productType").val(id);
        }
        else {
            $("#thirdProductType").val(id);
        }
    }

}

function initProducttype(productId,parentName) {
    $("#propertyFirstSel").html('<option value="0">请选择</option>');
    $("#propertySecondSel").html('<option value="0">请选择</option>');
    // 得到menuId
    var menuId = $("#menuSelect").val();
    var initUrl = "";
    if (menuId == -1) {
        initUrl = projectLocation + "servlet/ProductTypeServlet?methodName=init&id=" + productId;
    }
    else {
        initUrl = projectLocation + "servlet/ThirdProductTypeServlet?methodName=get&id=" + productId;
    }
    $.ajax({
        type: "post",
        url: initUrl,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                if (menuId == -1) {
                    $("#hid_ProductTypeParam").val("methodName=edit&id=" + productId);
                    $("#txt_ProductTypeName").val(r.Name);
                    $("#displayType").text(parentName);
                    $("#hid_pId").val(r.PtId);
                    $("#txt_ProductTypeRemark").val(r.Remark);
                }
                else {
                    $("#hid_ProductTypeParam").val("methodName=edit&id=" + productId);
                    $("#txt_ProductTypeName").val(r.name);
                    $("#displayType").text(parentName);
                   $("#hid_pId").val(r.parent);
                    //$("#txt_ProductTypeRemark").val(r.Remark);
                }

            }
            else if(r == "sessionOut"){
                doLogout();
            }
            else  {
                alertText("编辑失败！",3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });

    //getProductMenu(0,0);
}

//根据id获取商品种类结构
function getProductMenu(id,level) {
    var str = '<option value="0">请选择</option>';
    // 得到menuId
    var menuId = $("#menuSelect").val();
    var getUrl = "";
    if (menuId == -1) {
        getUrl = projectLocation + "servlet/ProductTypeServlet?methodName=typeDIR" + "&productTypeId=" + id;
    }
    else {
        id == 0 ? id = "" : id = id;
        getUrl = projectLocation + "servlet/ThirdProductTypeServlet?methodName=list" + "&parent=" + id +"&thirdWebsite=" + menuId;
    }
    $.ajax({
        type: "post",
        url: getUrl ,
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: true,
        success: function (r) {
            $("#websiteList").html('');
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        str += "<option value='"+ data[i].id +"'>" + data[i].name + "</option>";
                    }
                }
                if (level == 0) {
                    $("#propertyFirstSel").html('');
                    $("#propertyFirstSel").append(str);
                }else if (level == 1){

                    $("#propertySecondSel").html('');
                    $("#propertySecondSel").append(str);
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function proSelect(level) {
    $("#displayType").text('');
    var parentId;
    if (level == 1) {
        parentId = $("#propertyFirstSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getProductMenu(parentId,level);
        }
    }
    else {
        parentId = $("#propertySecondSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getProductMenu(parentId,level);
        }
    }
}

function addProductType() {
    $("#menuTreeAdd").hide();
    $("#menuAdd").show();
    $("#menuTreeDel").hide();

    $("#hidden_edit").show();
    $("#hid_ProductTypeParam").val("methodName=add");
    $("#txt_ProductTypeName").val('');
    $("#txt_ProductTypeRemark").val('');
    $("#propertyFirstSel").val('');
    $("#propertySecondSel").val('');
    $("#hid_pId").val('');
    $("#displayType").text('');
    $("#propertyFirstSel").html('<option value="0">请选择</option>');
    $("#propertySecondSel").html('<option value="0">请选择</option>');

    $("#hid_pId").val("0");
    getProductMenu(0,0);
}

function SaveProductType() {
    var strName = encode($("#txt_ProductTypeName").val());
    var strRemark = encode($("#txt_ProductTypeRemark").val());
    var strPtid;
    if ($("#propertySecondSel").val() != null && $("#propertySecondSel").val() != '0') {
        strPtid = $("#propertySecondSel").val();
    }
    else {
        if ($("#propertyFirstSel").val() != null && $("#propertyFirstSel").val() != '0') {
            strPtid = $("#propertyFirstSel").val();
        }
        else {
            // 父ID没有更改
            strPtid = $("#hid_pId").val();
        }
    }

    if ($.trim(strName) == "") {
        alertText("类型名称不能为空！", 3500);
        return false;
    }
    // 得到menuId
    var menuId = $("#menuSelect").val();
    var getUrl = "";
    if (menuId == -1) {
        getUrl = projectLocation + "servlet/ProductTypeServlet?methodName="
            + getParam("hid_ProductTypeParam", "methodName")
            + "&id=" + getParam("hid_ProductTypeParam", "id")
            + "&name=" + strName + "&remark=" + strRemark
            + "&ptid=" + strPtid +"&PropertyTypeChoices=&choiceslength=0";
    }
    else {
        strPtid == 0 ? strPtid = "" : strPtid = strPtid;
        getUrl = projectLocation + "servlet/ThirdProductTypeServlet?methodName=save"
            + "&id=" + getParam("hid_ProductTypeParam", "id")
            + "&name=" + strName + "&remark=" + strRemark
            + "&parent=" + strPtid +"&thirdWebsite="+menuId;
    }
    $.ajax({
        type: "post",
        url: getUrl,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r.res == "false") {
                alertText("保存失败！", 3500);
            }
            else if(r == "sessionOut")	{
                doLogout();
            }
            else {
                alertText("保存成功！", 3500);
                initProTree();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function delProductType() {
    var id =$("#hid_id").val();
    if (id == "") {
        alertText("请选中左侧商品树！", 3500);
        return false;
    }
    // 得到menuId
    var menuId = $("#menuSelect").val();
    var getUrl = "";
    if (menuId == -1) {
        getUrl =  projectLocation + "servlet/ProductTypeServlet?methodName=del&id=" + id;
    }
    else {
        getUrl = projectLocation + "servlet/ThirdProductTypeServlet?methodName=del&id=" + id;
    }
    $.ajax({
        type : "post",
        url : getUrl,
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "true") {
                alertText("删除成功！",  3500);
                addProductType();
                initProTree();
            }
            else if(r == "sessionOut")	{
                doLogout();
            }
            else  {
                alertText("删除失败！",3500);
            }
        },
        error : function(e) {
            alert(e.responseText);
        }
    });
}
// 目录树的新增
function addMenuTree() {
    // 显示新增界面
    $("#menuTreeAdd").show();
    $("#menuAdd").hide();
    $("#menuTreeDel").hide();

    $("#txt_menuTreeName").val("");
    $("#txt_menuTreeRemark").val("");

}
// 目录树新增
function SaveMenuTree(){
    var menuName = encode($("#txt_menuTreeName").val());
    if ($.trim(menuName) == "") {
        alertText("名称不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ThirdWebsiteServlet?methodName=save"
        + "&name=" + menuName,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r.res == "false") {
                alertText("保存失败！", 3500);
            }
            else if(r == "sessionOut")	{
                doLogout();
            }
            else {
                alertText("保存成功！", 3500);
                getMenuName();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
// 目录树删除
function delMenuTree() {
    // 显示目录树删除界面
    $("#menuTreeAdd").hide();
    $("#menuAdd").hide();
    $("#menuTreeDel").show();
    var str = "";
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ThirdWebsiteServlet?methodName=list" ,
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: true,
        success: function (r) {
            $("#menusDel").html('');
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        str += '<tr class="twoTabNormal"><td class="tab_th"><input name="menuDel" type="radio" value="'+data[i].id+'">' +
                            '</td><td class="tab_tr">'+data[i].name+'</td></tr>';
                    }
                }
                $("#menusDel").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//删除目录树
function DelMenu() {
    var id = $('input:radio[name="menuDel"]:checked').val();
    if (id == "" || typeof id == "undefined") {
        alertText("请选中目录！", 3500);
        return false;
    }
    $.ajax({
        type : "post",
        url : projectLocation
        + "servlet/ThirdWebsiteServlet?methodName=del&id=" + id,
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "true") {
                alertText("删除成功！",  3500);
                getMenuName();
                delMenuTree();
            }
            else if(r == "sessionOut")	{
                doLogout();
            }
            else  {
                alertText("删除失败！",3500);
            }
        },
        error : function(e) {
            alert(e.responseText);
        }
    });
}

//目录关联list
function menuRelation(strPageIndex) {
    // 查询标题
    var strQuery = $("#txt_menuListQuery").val();
    var id = $("#menuListId").val();

    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ThirdProductTypeServlet?methodName=listRelTypes&strQuery=" + encode(strQuery)
        + "&pageIndex=" + strPageIndex + "&pageSize=10&thirdWebsite=" + id,
        contentType: "application/json;charset=utf-8",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#menuRelationList").html('');
            if (r == "false") {
                $("#menuRelationList")
                    .append("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if (r == "sessionOut") {
                doLogout();

            }
            var data = $.parseJSON(r);
          if (data.total == 0) {
                $("#menuRelationList")
                    .append("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            $("#menuRelationPaging .dataCount").text(data.total);
            $("#menuRelationPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#menuRelationPaging .pageIndex").text(strPageIndex);

            var str = "";
            for (var i = 0; i < data.list.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + data.list[i].productTypeName + "</td>";
                str += "<td>" + data.list[i].thirdProductTypeName + "</td>";
                str += "<td>" + data.list[i].thirdWebsiteName + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='删除' onclick='confirmShow(" + data.list[i].id + ",delEvaluation)' /></td>";
                str += "</tr>";
            }
            $("#menuRelationList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    })
};

function delEvaluation(id) {
    $.ajax({
        type : "post",
        url : projectLocation
        + "servlet/ThirdProductTypeServlet?methodName=delRelType&thirdProductType=" + id,
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "true") {
                alertText("删除成功！",  3500);
                menuRelation(1);
            }
            else if(r == "sessionOut")	{
                doLogout();
            }
            else  {
                alertText("删除失败！",3500);
            }
        },
        error : function(e) {
            alert(e.responseText);
        }
    });
}

//关联保存提交
function saveContent() {
    var productType = $("#productType").val();
    var thirdProductType = $("#thirdProductType").val();
    if ($.trim(productType) == "") {
        alertText("请选中！", 3500);
        return false;
    }
    if ($.trim(thirdProductType) == "") {
        alertText("请选中！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ThirdProductTypeServlet?methodName=saveRelType"
        + "&thirdProductType=" + thirdProductType + "&productType="+productType,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r.res == "false") {
                alertText("保存失败！", 3500);
            }
            else if(r == "sessionOut")	{
                doLogout();
            }
            else {
                alertText("保存成功！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });

}

function backList() {
    $("#menuTreeContent").hide();
    $("#menuListContent").show();
}

function bk_option(){
	$("#menuTreeAdd").hide();
	$("#menuAdd").show();
}

function bk_menuTreeDel(){
	$("#menuTreeDel").hide();
	$("#menuAdd").show();
}