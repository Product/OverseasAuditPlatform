function FirstPageRoleMenu() {
    GetRoleMenuList(1);
}

function PreviousPageRoleMenu() {
    var pageIndex = (parseInt($("#roleMenuPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#roleMenuPaging .pageIndex").text()) - 1);
    GetRoleMenuList(pageIndex);
}

function NextPageRoleMenu() {
    var pageIndex = ($("#roleMenuPaging .pageIndex").text() == $("#roleMenuPaging .pageCount").text() ? parseInt($("#roleMenuPaging .pageIndex").text()) : parseInt($("#roleMenuPaging .pageIndex").text()) + 1);
    GetRoleMenuList(pageIndex);
}

function EndPageRoleMenu() {
    GetRoleMenuList(parseInt($("#roleMenuPaging .pageCount").text()));
}

function GetRoleMenuList(strPageIndex) {
    var strQuery = $("#txt_roleMenuQuery").val();
    var strStraTime = $("#txt_roleMenuBeginDate").val();
    var strEndTime = $("#txt_roleMenuEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/RoleMenuServlet?methodName=QueryList&strQuery="
            					+ strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
                                + strPageIndex + "&strPageCount=10",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#roleMenuList").html('');
            if (r == "false") {
                $("#roleMenuList").append("<tr><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#roleMenuPaging .dataCount").text(data.total);
            $("#roleMenuPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#roleMenuPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i + 1) + "</td>";
                str += "<td>" + data.webList[i].Name + "</td>";
                str += "<td>" + data.webList[i].Sort + "</td>";
                str += "<td>" + data.webList[i].CreateTime.substring(0, 19) + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showRoleMenuDialog(\"edit\",\"" + data.webList[i].Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='DelRoleMenu(\"" + data.webList[i].Id + "\")' /></td>";
                str += "</tr>";
            }
            $("#roleMenuList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelRoleMenu(id) {
    if (confirm("是否删除？")) {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/RoleMenuServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("roleMenuMessge", "删除成功！", "green", 3500);
                    GetRoleMenuList(1);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
}

function showRoleMenuDialog(methodName, Id) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#roleMenuDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if ($("#hid_menuTreeInitStatus").val() == "1") {
        $("#roleTree").jstree("open_all");
        if (methodName != "add") {
            $.ajax({
                type: "post",
                url: projectLocation + "servlet/RoleMenuServlet?methodName=init&id=" + Id,
                dataType: "json",
                async: false,
                success: function (r) {
                    if (r != "false") {
                        $("#hid_roleMenuParam").val("methodName=edit&id=" + Id);
                        $("#txt_roleMenuName").val(r.Name);
                        $("#txt_roleMenuSort").val(r.Sort);

                        var arrMenu = r.Menus.split("_");
                        $("#roleTree").find("li").each(function () {
                            for (var i = 0; i < arrMenu.length; i++) {
                                if ($(this).attr("id") == arrMenu[i]) {
                                    $("#roleTree").jstree("check_node", $(this));
                                    break;
                                }
                            }
                        })
                    }
                },
                error: function (e) {
                    alert(e.responseText);
                }
            });
        } else {
            $("#hid_roleMenuParam").val("methodName=add");
            $("#txt_roleMenuName").val("");
            $("#txt_roleMenuSort").val("");
        }
    } else {
	    $.ajax({
	        type: "post",
	        url: projectLocation + "servlet/RoleMenuServlet?methodName=getMenuList&userCode=" + $("#txt_userCode").val(),
	        dataType: "text",
	        async: false,
	        success: function (r) {
	            if (r != "false") {
	                $("#roleTree").jstree({
	                    "themes": { "theme": "default", "dots": true, "icons": false },
	                    "json_data": {
	                        "data": $.parseJSON(r),
	                        "progressive_render": true
	                    },
	                    "plugins": ["themes", "json_data", "checkbox", "ui"]
	                }).bind("loaded.jstree", function (event, data) {//tree load完才做其他事
	                    $("#roleTree").jstree("open_all");
	                    $("#hid_menuTreeInitStatus").val("1");
	                    if (methodName != "add") {
	                        $.ajax({
	                            type: "post",
	                            url: projectLocation + "servlet/RoleMenuServlet?methodName=init&id=" + Id,
	                            dataType: "json",
	                            async: false,
	                            success: function (r) {
	                                if (r != "false") {
	                                    $("#hid_roleMenuParam").val("methodName=edit&id=" + Id);
	                                    $("#txt_roleMenuName").val(r.Name);
	                                    $("#txt_roleMenuSort").val(r.Sort);
	
	                                    var arrMenu = r.Menus.split("_");
	                                    $("#roleTree").find("li").each(function () {
	                                        for (var i = 0; i < arrMenu.length; i++) {
	                                            if ($(this).attr("id") == arrMenu[i]) {
	                                                $("#roleTree").jstree("check_node", $(this));
	                                                break;
	                                            }
	                                        }
	                                    });
	                                }
	                            },
	                            error: function (e) {
	                                alert(e.responseText);
	                            }
	                        });
	                    } else {
	                        $("#hid_roleMenuParam").val("methodName=add");
	                        $("#txt_roleMenuName").val("");
	                        $("#txt_roleMenuSort").val("");
	                    }
	                });
	            }
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });    
    }
}

function SaveRoleMenu() {
    var strName = $("#txt_roleMenuName").val();
    var strSort = $("#txt_roleMenuSort").val();
    if ($.trim(strName) == "") {
        alertText("角色名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/RoleMenuServlet?methodName="
                                + getParam("hid_roleMenuParam", "methodName") + "&id=" + getParam("hid_roleMenuParam", "id")
                                + "&name=" + encode(strName) + "&sort=" + encode(strSort) + "&menuIds=" + encode(getCheckedNodeIDS()),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "true") {
                alertText("保存成功！", 3500);
            } else {
                alertText("保存失败！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//反回所有选中的菜单ID
function getCheckedNodeIDS() {
    var checked_ids = [];
    $('#roleTree').jstree("get_checked", null, true).each(function () {
        checked_ids.push(this.id);
    });
    return checked_ids.join(",");
}

//给菜单赋值  _分隔ID
function setCheckedNode(ids) {
    $("#roleTree").jstree("uncheck_all"); //清空
    if (ids != "") {
        var tempCheckStr = ids.split('_');
        $(tempCheckStr).each(function (i, item) {
            $("#roleTree").jstree("check_node", "#" + item);
        })
    }
}