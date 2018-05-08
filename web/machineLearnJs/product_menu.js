function proMenuFirstPage() {
    GetStandardManageList(1);
}

function proMenuPreviousPage() {
    var pageIndex = (parseInt($("#productMenuPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#productMenuPaging .pageIndex").text()) - 1);
    GetStandardManageList(pageIndex);
}

function proMenuNextPage() {
    var pageIndex = ($("#productMenuPaging .pageIndex").text() == $(
			"#productMenuPaging .pageCount").text() ? parseInt($(
			"#productMenuPaging .pageIndex").text()) : parseInt($(
			"#productMenuPaging .pageIndex").text()) + 1);
    GetStandardManageList(pageIndex);
}

function proMenuEndPage() {
    GetStandardManageList(parseInt($("#productMenuPaging .pageCount").text()));
}


/*获取目录管理表项*/
function GetProMenuEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.thirdType + "</td>";
    str += "<td>" + data.secondType + "</td>";
    str += "<td>" + data.firstType + "</td>";
    str += "<td>" + data.productStyle + "</td>";
    str += "<td>" + data.relateTime.substring(0,19) + "</td>";
    str += "<td>" + data.status + "</td>";
    var editName = data.firstType + " " + data.secondType + " " +data.thirdType;
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='initProMenu(\""
			+ data.id + "\",\""+ editName + "\",\""+ data.productStyle + "\",\""+ data.status + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
			+ data.id + "\",DelProMenu)' /></td>";
    str += "</tr>";
    return str;
}
/*
 函数名：GetStandardManageList
 作用：获取目录管理列表
 */
function GetProMenuList(strPageIndex) {
    var productFirstSel = $("#productFirstSel").val();
    var productSecondSel = $("#productSecondSel").val();
    var productThirdSel = $("#productThirdSel").val();
    var productType = "";
    if (productThirdSel != "") {
        productType = productThirdSel;
    }
    else {
        if(productSecondSel != "") {
            productType = productSecondSel;
        }
        else {
            productType = productFirstSel;
        }
    }
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/RelProductTypeStyleServlet?methodName=queryList&productType=" + productType
                + "&pageIndex=" + strPageIndex + "&pageSize=10",
        contentType: "application/json;charset=utf-8",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#productMenuList").html('');
            if (r == "false") {
                $("#productMenuList")
						.append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();
            }
            else {
                var data = $.parseJSON(r);
                $("#productMenuPaging .dataCount").text(data.total);
                $("#productMenuPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
                $("#productMenuPaging .pageIndex").text(strPageIndex);
                if (data.list.length > 0) {
                    var str = "";
                    for (var i = 0; i < data.list.length; i++) {
                        str += GetProMenuEntry(data.list[i], i+1);
                    }
                    $("#productMenuList").append(str);
                }
                else {
                    $("#productMenuList")
                        .append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                }

            }

        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
 函数名：initStandardMan；
 作用：获取目录管理
 */
function initProMenu(id,editName,productStyle,status){
    $("#catalogManage_mac").hide();
    $("#catalogManage_Edit").show();
    $("#catalogManage_Edit .Title_tab").html("目录管理编辑");
    $("#hid_menuId").val(id);

}

// 新增目录管理
function addProMenu () {
    $("#catalogManage_mac").hide();
    $("#catalogManage_Edit").show();
    $("#catalogManage_Edit .Title_tab").html("目录管理新增");

    $("#txt_productFirstSel").val("0");
    $("#txt_productSecondSel").val("0");
    $("#txt_productThirdSel").val("0");
    $("#txt_relationMenu").val("0");
    $("#txt_state").val("0");
    $("#hid_menuId").val("");

    //关联监管目录
    getRelationMenu();

    // 初始化下拉列表
    getProductMenuTxt(0,0);

}

// 关联监管目录下拉txt_relationMenu
function getRelationMenu () {
    var str = '<option value="">请选择</option>';
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ProductStyleServlet?methodName=allProductStyles" ,
        contentType: "application/json;charset=utf-8",
        dataType:"text",
        async: true,
        success: function (r) {
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.list.length > 0) {
                    for (var i = 0; i < data.list.length; i++) {
                        str += "<option value='"+ data.list[i].id +"'>" + data.list[i].name + "</option>";
                    }
                }
                $("#txt_relationMenu").html('');
                $("#txt_relationMenu").append(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/*
函数名：DelProMenu
作用：删除目录管理
参数：id（被删除的目录管理id）
*/
function DelProMenu(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/RelProductTypeStyleServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetProMenuList(1);
                }
                else if(r =="sessionOut"){
                	doLogout();
                }
                else if(r == "false")
         	   {
         	   alertText("删除失败！", 3500);
         	   }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
}

/*
函数名：SaveProMenu
作用：保存目录管理
*/
function SaveProMenu() {
    var txt_productFirstSel = $("#txt_productFirstSel").val();
    var txt_productSecondSel = $("#txt_productSecondSel").val();
    var txt_productThirdSel = $("#txt_productThirdSel").val();
    var id = $("#hid_menuId").val();
    var productType;
    if (txt_productThirdSel != "" && txt_productThirdSel != "0") {
        productType = txt_productThirdSel;
    }
    else {
        if(txt_productSecondSel != "" && txt_productThirdSel != "0") {
            productType = txt_productSecondSel;
        }
        else {
            productType = txt_productFirstSel;
        }
    }
    var txt_relationMenu = $("#txt_relationMenu").val();
    var txt_state = $("#txt_state").val();

    if ($.trim(txt_productFirstSel) == "") {
        alertText("商品一级目录不能为空！", 3500);
        return false;
    }
    if ($.trim(txt_relationMenu) == "") {
        alertText("关联监管目录！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/RelProductTypeStyleServlet?methodName=save" + "&id=" + id
        + "&productType=" + productType + "&productStyle=" + txt_relationMenu + "&status=" + txt_state,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	 alertText("保存失败！", 3500);
            } 
            else if(r =="sessionOut"){
            	doLogout();
            	
            }else {
            	alertText("保存成功！", 3500);
                GetProMenuList(1);
                $("#catalogManage_mac").show();
                $("#catalogManage_Edit").hide();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}


//根据id获取商品种类结构
function getProductMenu(id,level) {
    var str = '<option value="">请选择</option>';
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ProductTypeServlet?methodName=typeDIR"
        + "&productTypeId=" + id ,
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (r) {
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
                    $("#productFirstSel").html('');
                    $("#productFirstSel").append(str);
                }else if (level == 1){

                    $("#productSecondSel").html('');
                    $("#productSecondSel").append(str);
                }else {

                    $("#productThirdSel").html('');
                    $("#productThirdSel").append(str);
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
        parentId = $("#productFirstSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getProductMenu(parentId,level);
        }
    }
    else {
        parentId = $("#productSecondSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getProductMenu(parentId,level);
        }
    }
}


//根据id获取商品种类结构
function getProductMenuTxt(id,level) {
    var str = '<option value="">请选择</option>';
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/ProductTypeServlet?methodName=typeDIR"
        + "&productTypeId=" + id ,
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (r) {
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
                    $("#txt_productFirstSel").html('');
                    $("#txt_productFirstSel").append(str);
                }else if (level == 1){

                    $("#txt_productSecondSel").html('');
                    $("#txt_productSecondSel").append(str);
                }else {

                    $("#txt_productThirdSel").html('');
                    $("#txt_productThirdSel").append(str);
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function proSelectTxt(level) {
    $("#displayType").text('');
    var parentId;
    if (level == 1) {
        parentId = $("#txt_productFirstSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getProductMenuTxt(parentId,level);
        }
    }
    else {
        parentId = $("#txt_productSecondSel").val();
        if (parentId == '') {
            return;
        }
        else {
            getProductMenuTxt(parentId,level);
        }
    }
}