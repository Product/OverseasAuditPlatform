function FirstPage_Materials() {
    GetMaterialsList(1,"");
}

function PreviousPage_Materials() {
    var pageIndex = (parseInt($("#MaterialsPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#MaterialsPaging .pageIndex").text()) - 1);
    GetMaterialsList(pageIndex,"");
}

function NextPage_Materials() {
    var pageIndex = ($("#MaterialsPaging .pageIndex").text() == $("#MaterialsPaging .pageCount").text() ? parseInt($("#MaterialsPaging .pageIndex").text()) : parseInt($("#MaterialsPaging .pageIndex").text()) + 1);
    GetMaterialsList(pageIndex,"");
}

function EndPage_Materials() {
    GetMaterialsList(parseInt($("#MaterialsPaging .pageCount").text()),"");
}

function GetMaterialsEntry(data,idx)
{
	var str = "<tr>";
	str += "<td>" + idx + "</td>";
	str += "<td>" + data.Name + "</td>";
	str += "<td>" + data.MaterialType + "</td>"
	str += "<td>" + data.remark + "</td>";
	str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialog_Materials(\"Edit\",\""+ data.Id + "\",this)' />" +
			"&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+ data.Id + "\", DelMaterials)' /></td>";
	str += "</tr>";
    return str;
}
function GetMaterialsList(strPageIndex) {
    var strQuery = $("#txt_MaterialsQuery").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/MaterialsServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#MaterialsList").html('');
            if (r == "false") {
                $("#MaterialsList")
						.append("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#MaterialsPaging .dataCount").text(data.total);
            $("#MaterialsPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#MaterialsPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetMaterialsEntry(data.webList[i], i+1);
            }
            $("#MaterialsList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function showDialog_Materials(methodName,Id,pos)
{
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#MaterialsDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/MaterialsServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_MaterialsParam").val("methodName=edit&id=" + Id);
                    $("#txt_MaterialsName").val(r.Name);
                    $("#txt_MaterialsRemark").val(r.remark);
                    getMaterialTypeList();
                    $("#txt_MaterialMenu").val(r.MaterialTypeId);
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_MaterialsParam").val("methodName=add");
        $("#txt_MaterialsName").val("");
        $("#txt_MaterialsRemark").val("");
        getMaterialTypeList();
    }
}

function getMaterialTypeList()
{
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/MaterialTypeServlet?methodName=getMaterialTypeList",
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
            	//var data = $.parseJSON(r);
            	var str="";
            	//str = "<tr><td width='100' align='right' class='tab_th'>原料类别：</td><td>";
            	//str += "<select id='txt_MaterialMenu'>";
            	$("#txt_MaterialMenu").html('');
                for (var i = 0; i < r.webList.length; i++)
                {
                	str +="<option value='" + r.webList[i].Id + "'>" + r.webList[i].Name + "&nbsp;&nbsp;&nbsp;</option>"
                }
            	//str+="</td></tr>";
            	$("#txt_MaterialMenu").append(str);
            }
            else{
//            	var str="";
//            	str = "<tr><td width='100' align='right' class='tab_th'>原料类别：</td>";
//            	str += "<td>无可选类别</td></tr>";
//            	$("#MaterialTypeMenu").html(str);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function SaveMaterial()
{
	var strMaterialName = $("#txt_MaterialsName").val();
	var strMaterialRemark = $("#txt_MaterialsRemark").val();
	var strMaterialType= $("#txt_MaterialMenu").val();
	if ($.trim(strMaterialName) == "") {
        alertText("原料名称不能为空！", 3500);
        return false;
    }
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/MaterialsServlet?methodName="
            					+ getParam("hid_MaterialsParam", "methodName") + "&id=" + getParam("hid_MaterialsParam", "id")
                                + "&MaterialName=" + encode(strMaterialName) + "&MaterialRemark=" + encode(strMaterialRemark) +  "&materialTypeid=" + encode(strMaterialType) +  "&userId=" + getCookie(""),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            }else{
            	alertText("保存成功！", 3500);
            	var data = $.parseJSON(r);
            	UpdateMaterials(data);
                $('#MaterialsDom_Edit').animate({ width: "hide" });
                $("#MaterialsDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function DelMaterials(Id)
{
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/MaterialsServlet?methodName=del&id=" + Id,
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
                GetMaterialsList(1);
            } else if(r=="error"){
            	alertText("无法删除已有地区的国家！", 3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function UpdateMaterials(data){
	var str = "";
    if(getParam("hid_MaterialsParam", "methodName") == "add"){
        str += GetMaterialsEntry(data, '*');
        $("#MaterialsList").find('.noDataSrc').remove();
        $("#MaterialsList").prepend(str);
    }else if(getParam("hid_MaterialsParam", "methodName") == "edit"){
        var obj =  $("#MaterialsList").find('.editObject').find('td');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.MaterialType);
        obj.eq(3).html(data.remark);
        $("#MaterialsList").find('.editObject').removeClass('editObject');
    }
}