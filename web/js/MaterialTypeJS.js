function FirstPage_MaterialType() {
    GetMaterialTypeList(1);
}

function PreviousPage_MaterialType() {
    var pageIndex = (parseInt($("#MaterialTypePaging .pageIndex").text()) == 1 ? 1 : parseInt($("#MaterialTypePaging .pageIndex").text()) - 1);
    GetMaterialTypeList(pageIndex);
}

function NextPage_MaterialType() {
    var pageIndex = ($("#MaterialTypePaging .pageIndex").text() == $("#MaterialTypePaging .pageCount").text() ? parseInt($("#MaterialTypePaging .pageIndex").text()) : parseInt($("#MaterialTypePaging .pageIndex").text()) + 1);
    GetMaterialTypeList(pageIndex);
}

function EndPage_MaterialType() {
    GetMaterialTypeList(parseInt($("#MaterialTypePaging .pageCount").text()));
}

function GetMaterialTypeEntry(data,idx)
{
	var str = "<tr>";
	str += "<td>" + idx + "</td>";
	str += "<td>" + data.Name + "</td>";
	str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialog_MaterialType(\"Edit\",\""+ data.Id + "\",this)' />" +
			"&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+ data.Id + "\", DelMaterialType)' /></td>";
	str += "</tr>";
    return str;
}
function GetMaterialTypeList(strPageIndex) {
    var strQuery = $("#txt_MaterialTypeQuery").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/MaterialTypeServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#MaterialTypeList").html('');
            if (r == "false") {
                $("#MaterialTypeList")
						.append("<tr class='noDataSrc'><td colspan='3' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#MaterialTypePaging .dataCount").text(data.total);
            $("#MaterialTypePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#MaterialTypePaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetMaterialTypeEntry(data.webList[i], i+1);
            }
            $("#MaterialTypeList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showDialog_MaterialType(methodName,Id,pos){
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#MaterialTypeDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/MaterialTypeServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_MaterialTypeParam").val("methodName=edit&id=" + Id);
                    $("#txt_MaterialTypeName").val(r.Name);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_MaterialTypeParam").val("methodName=add");
        $("#txt_MaterialTypeName").val("");
    }
}

function SaveMaterialType(){
	var strMaterialTypeName = $("#txt_MaterialTypeName").val();
	if ($.trim(strMaterialTypeName) == "") {
        alertText("类别名称不能为空！", 3500);
        return false;
    }
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/MaterialTypeServlet?methodName="
            					+ getParam("hid_MaterialTypeParam", "methodName") + "&id=" + getParam("hid_MaterialTypeParam", "id")
                                + "&MaterialTypeName=" + encode(strMaterialTypeName) +  "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            }else{
            	alertText("保存成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdateMaterialType(data);
                $('#MaterialTypeDom_Edit').animate({ width: "hide" });
                $("#MaterialTypeDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelMaterialType(id)
{
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/MaterialTypeServlet?methodName=del&id=" + id,
        dataType: "text",
        async: false,
		   // 删除过程中还未得到删除结果，显示loading界面
		   beforeSend:function(XMLHttpRequest){
          $("#loading1").css("display","block");
			},
        success: function (r) {
			   // 删除操作完成(不管删除成功与否)，隐藏loading界面
			   $("#loading1").css("display","none");
            if (r == "true") {
                alertText("删除成功！", 3500);
                GetMaterialTypeList(1);
            } else if(r=="error"){
            	alertText("无法删除已有原料的类别！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateMaterialType(data){
	var str = "";
    if(getParam("hid_MaterialTypeParam", "methodName") == "add"){
        str += GetMaterialTypeEntry(data, '*');
        $("#MaterialTypeList").find('.noDataSrc').remove();
        $("#MaterialTypeList").prepend(str);
    }else if(getParam("hid_MaterialTypeParam", "methodName") == "edit"){
        var obj =  $("#MaterialTypeList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        $("#MaterialTypeList").find('.editObject').removeClass('editObject');
    }
}