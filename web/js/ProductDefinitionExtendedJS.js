function FirstPage_ProductDefinitionBrand() {
    GetProductDefinitionBrandList(1);
}

function PreviousPage_ProductDefinitionBrand() {
    var pageIndex = (parseInt($("#ProductDefinitionBrandPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#ProductDefinitionBrandPaging .pageIndex").text()) - 1);
    GetProductDefinitionBrandList(pageIndex);
}

function NextPage_ProductDefinitionBrand() {
    var pageIndex = ($("#ProductDefinitionBrandPaging .pageIndex").text() == $("#ProductDefinitionBrandPaging .pageCount").text() ? parseInt($("#ProductDefinitionBrandPaging .pageIndex").text()) : parseInt($("#ProductDefinitionBrandPaging .pageIndex").text()) + 1);
    GetProductDefinitionBrandList(pageIndex);
}

function EndPage_ProductDefinitionBrand() {
    GetProductDefinitionBrandList(parseInt($("#ProductDefinitionBrandPaging .pageCount").text()));
}

function GetProductDefinitionBrandEntry(data,idx)
{
	var str = "<tr>";
	str += "<td><input type='radio' class='ProductDefinitionBrand' value='"+data.Id+"' name='ProductDefinitionBrand' id='PDB"+data.Id+"'></td>";
	str += "<td><label for='PDB"+data.Id+"'>" + data.Name_CN + "</label></td>";
	str += "<td>" + data.Name_EN + "</td>"
	str += "<td>" + data.Name_other + "</td>";
	str += "<td>" + data.CountryName + " " + data.AreaName + "</td>";
	str += "<td>" + data.CreateTime + "</td>";
	str += "</tr>";
    return str;
}
function GetProductDefinitionBrandList(strPageIndex) {
    var strQuery = $("#txt_ProductDefinitionBrandQuery").val();
    var strStraTime = $("#txt_ProductDefinitionBrandBeginDate").val();
    var strEndTime = $("#txt_ProductDefinitionBrandEndDate").val();
    
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/BrandServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#ProductDefinitionBrandList").html('');
            if (r == "false") {
                $("#ProductDefinitionBrandList")
						.append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#ProductDefinitionBrandPaging .dataCount").text(data.total);
            $("#ProductDefinitionBrandPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#ProductDefinitionBrandPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
            	str += GetProductDefinitionBrandEntry(data.webList[i], i+1);
            }
            $("#ProductDefinitionBrandList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function checkProductDefinitionBrandRadio(){
	var tmp = $("input[name='ProductDefinitionBrand']:checked");
    if(tmp.length == 0)
        alertText("没有品牌！", 3500);
    else{
        $('#txt_ProductDefinitionBrand').val(tmp.val());
        $('#txt_ProductDefinitionBrand').text(tmp.parent().parent().find('label').text());
        $("#ProductDefinitionBrandDom_Edit").animate({ width: "hide" });
        $('#ProductDefinitionDom_Edit').show();
    }
}

function FirstPage_ProductDefinitionProductType() {
    GetProductDefinitionProductTypeList(1);
}

function PreviousPage_ProductDefinitionProductType() {
    var pageIndex = (parseInt($("#ProductDefinitionBrandPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#ProductDefinitionProductTypePaging .pageIndex").text()) - 1);
    GetProductDefinitionProductTypeList(pageIndex);
}

function NextPage_ProductDefinitionProductType() {
    var pageIndex = ($("#ProductDefinitionProductTypePaging .pageIndex").text() == $("#ProductDefinitionProductTypePaging .pageCount").text() ? parseInt($("#ProductDefinitionProductTypePaging .pageIndex").text()) : parseInt($("#ProductDefinitionProductTypePaging .pageIndex").text()) + 1);
    GetProductDefinitionProductTypeList(pageIndex);
}

function EndPage_ProductDefinitionProductType() {
    GetProductDefinitionProductTypeList(parseInt($("#ProductDefinitionProductTypePaging .pageCount").text()));
}

function GetProductDefinitionProductTypeEntry(data, idx){
    var str = "";
    str += "<td><input type='radio' class='ProductDefinitionProductType' value='"+data.Id+"' name='ProductDefinitionProductType' id='PDPT"+data.Id+"'></td>";
	str += "<td><label for='PDPT"+data.Id+"'>" + data.Name + "</label></td>";
    str += "<td>" + data.PName + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showChildrenProductDefinitionProductType(\"1\", \"ptchild"
        + data.Id + "\",this)' /></td>";
    return str;
}

function showChildrenProductDefinitionProductType(med, name, obj){
    var tgr = $(obj).parent().parent();
    var idx = $('#ProductDefinitionProductTypeList tbody tr').index(tgr);
    var hed = $(tgr).children('td').eq(0).html();
    if(med == "0"){
        $(obj).val("查 看");
        $(obj).attr('onclick', 'showChildrenProductDefinitionProductType(\"1\", \"'+name+'\", this)');
        $('.'+name).each(function(){
            var aim = $(this).find('td input').eq(0).attr('onclick');
            if(aim != undefined){
                var judge = aim.charAt(25);
                
                if(judge == "0"){
                    var ii = 36;
                    while(aim.charAt(ii) != '"')
                        ii++;
                    var idd = aim.substring(30, ii);
                    $("."+idd).remove();
                }
            }
            });
        $('.'+name).remove();
        return;
    }
    $(obj).val("收 起");
    $(obj).attr('onclick', 'showChildrenProductDefinitionProductType(\"0\", \"'+name+'\", this)');
    $.ajax({
        type : "post",
        url : projectLocation
            + "servlet/ProductTypeServlet?methodName=sel&id="+name.substring(7),
        dataType : "text",
        async : false,
        success : function(r) {
            if (r == "false") {
                $(tgr).after("<tr class='"+name+"'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r == "sessionOut"){
                doLogout();
            }
            else{
                var data = $.parseJSON(r);
                var str = "";
                if(data.webList.length == 0)
                    $(tgr).after("<tr class='"+name+"'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                for (var i = 1; i <= data.webList.length; i++) {
                    str += "<tr class='"+name+"'>"+GetProductDefinitionProductTypeEntry(data.webList[i-1], hed+"."+i)+"</tr>";
                }
                $(tgr).after(str);
            }
        },
        error : function(e) {
            alert(e.responseText);
        }
    });
}

function GetProductDefinitionProductTypeList(strPageIndex){
	var strQuery = $("#txt_ProductDefinitionProductTypeQuery").val();
	var strStraTime = $("#txt_ProductDefinitionProductTypeBeginDate").val();
	var strEndTime = $("#txt_ProductDefinitionProductTypeEndDate").val();
    var strLevel = $('#op_ProductDefinitionProductTypeLevel').val();
    $('#op_ProductDefinitionProductTypeLevel').val(strLevel);
    //getProductTypeLevel(strLevel);
	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/ProductTypeServlet?methodName=QueryList&strQuery="
						+ encode(strQuery) + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10" +"&strLevel="+strLevel,
				dataType : "text",
				async : false,
				success : function(r) {
					$("#ProductDefinitionProductTypeList").html('');
					if (r == "false") {
						$("#ProductDefinitionProductTypeList")
								.append(
										"<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}
					else if(r == "sessionOut")	{
		            	doLogout();
		            }
	                else  {
	                	var data = $.parseJSON(r);

	                    $("#ProductDefinitionProductTypePaging .dataCount").text(data.total);
	                    $("#ProductDefinitionProductTypePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
	                    $("#ProductDefinitionProductTypePaging .pageIndex").text(strPageIndex);
	                    var str = "";
	                    for (var i = 1; i <= data.webList.length; i++) {
	                        str += "<tr>"+GetProductDefinitionProductTypeEntry(data.webList[i-1], i)+"</tr>";
						}
						$("#ProductDefinitionProductTypeList").append(str);
	                }
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
}

function checkProductDefinitionProductTypeRadio(){
	var tmp = $("input[name='ProductDefinitionProductType']:checked");
    if(tmp.length == 0)
        alertText("没有类型！", 3500);
    else{
        $('#txt_ProductDefinitionProductType').val(tmp.val());
        $('#txt_ProductDefinitionProductType').text(tmp.parent().parent().find('label').text());
        $("#ProductDefinitionProductTypeDom_Edit").animate({ width: "hide" });
        $('#ProductDefinitionDom_Edit').show();
    }
}