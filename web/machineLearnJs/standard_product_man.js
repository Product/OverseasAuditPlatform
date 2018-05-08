function staManFirstPage() {
    GetStandardManageList(1);
}

function staManPreviousPage() {
    var pageIndex = (parseInt($("#standardManagePaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#standardManagePaging .pageIndex").text()) - 1);
    GetStandardManageList(pageIndex);
}

function staManNextPage() {
    var pageIndex = ($("#standardManagePaging .pageIndex").text() == $(
			"#standardManagePaging .pageCount").text() ? parseInt($(
			"#standardManagePaging .pageIndex").text()) : parseInt($(
			"#standardManagePaging .pageIndex").text()) + 1);
    GetStandardManageList(pageIndex);
}

function staManEndPage() {
    GetStandardManageList(parseInt($("#standardManagePaging .pageCount").text()));
}

/*获取标准产品表项*/
function GetStandardManEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.productTrivialName + "</td>";
    str += "<td>" + data.productScientificName + "</td>";
    str += "<td>" + data.createTime.substring(0,19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='initStandardMan(\""
			+ data.id + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
			+ data.id + "\",DelStandardMan)' /></td>";
    str += "</tr>";
    return str;
}
/*
 函数名：GetStandardManageList
 作用：获取标准产品列表
 */
function GetStandardManageList(strPageIndex) {
    var productTrivialName = $("#productTrivialName").val();
    var productScientificName = $("#productScientificName").val();
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ProductTrivialNameServlet?methodName=queryList&productTrivialName=" + encode(productTrivialName)
                + "&productScientificName=" + encode(productScientificName) + "&pageIndex=" + strPageIndex + "&pageSize=10",
        contentType: "application/json;charset=utf-8",
        dataType: "text",
        async: false,
        success: function (r) {
            $("#standardManageList").html('');
            if (r == "false") {
                $("#standardManageList")
						.append("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();
            }
            else {
                var data = $.parseJSON(r);
                $("#standardManagePaging .dataCount").text(data.total);
                $("#standardManagePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
                $("#standardManagePaging .pageIndex").text(strPageIndex);
                if (data.list.length > 0) {
                    var str = "";
                    for (var i = 0; i < data.list.length; i++) {
                        str += GetStandardManEntry(data.list[i], i+1);
                    }
                    $("#standardManageList").append(str);
                }
                else {
                    $("#standardManageList")
                        .append("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
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
 作用：获取网站类型
 */
function initStandardMan(id){
    $("#standardManage_mac").hide();
    $("#standardManage_Edit").show();
    $("#standardManage_Edit .Title_tab").html("标准产品编辑");
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/ProductTrivialNameServlet?methodName=init&id="+id,
		dataType : "text",
		async : false,
		success : function(r) {
			 $("#op_website_style").html('');
			if (r != "false") {
				var data = $.parseJSON(r);
                $("#txt_productTrivialName").val(data.productTrivialName);
                $("#txt_productScientificName").val(data.productScientificName);
                $("#hid_standardManId").val(id);
			}
			else if(r =="sessionOut"){
            	doLogout();
            }
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

// 新增采集器
function addStreamPubOpiCol () {
    $("#standardManage_mac").hide();
    $("#standardManage_Edit").show();
    $("#standardManage_Edit .Title_tab").html("标准产品新增");

    $("#txt_productTrivialName").val("");
    $("#txt_productScientificName").val("");
    $("#hid_standardManId").val("");
}
/*
函数名：DelWebsite
作用：删除标准产品
参数：id（被删除的标准产品id）
*/
function DelStandardMan(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/ProductTrivialNameServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetStandardManageList(1);
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
函数名：SaveStandardMan
作用：保存标准产品
*/
function SaveStandardMan() {
    var trivialName = $("#txt_productTrivialName").val();
    var scientificName = $("#txt_productScientificName").val();
    var id = $("#hid_standardManId").val();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductTrivialNameServlet?methodName=save" + "&id=" + id
        + "&productTrivialName=" + encode(trivialName) + "&productScientificName=" + encode(scientificName),
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
                GetStandardManageList(1);
                $("#standardManage_mac").show();
                $("#standardManage_Edit").hide();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
