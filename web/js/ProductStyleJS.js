
function PSFirstPage() {
	GetProductStyleList(1);
}

function PSPreviousPage() {
	var pageIndex = (parseInt($("#ProductStylePaging .pageIndex").text()) == 1 ? 1 : parseInt($("#ProductStylePaging .pageIndex").text()) - 1);
	GetProductStyleList(pageIndex);
}

function PSNextPage() {
	var pageIndex = ($("#ProductStylePaging .pageIndex").text() == $("#ProductStylePaging .pageCount").text() ? parseInt($("#ProductStylePaging .pageIndex").text()) : parseInt($("#ProductStylePaging .pageIndex").text()) + 1);
	GetProductStyleList(pageIndex);
}

function PSEndPage() {
	GetProductStyleList(parseInt($("#ProductStylePaging .pageCount").text()));
}
function GetProductStyleEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
	str += "<td>" + data.Remark + "</td>";
	str += "<td>" + data.CreatePerson + "</td>";
	str += "<td>"
			+ data.CreateTime.substring(0, 19)
			+ "</td>";
        str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showPDialog(\"edit\",\""
				+ data.Id
				+ "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
				+ data.Id + "\",DelProductStyle)' /></td>";
	str += "</tr>";
    return str;
}
function GetProductStyleList(strPageIndex) {
	var strQuery = $("#txt_ProductStyleQuery").val();
	var strStraTime = $("#txt_ProductStyleBeginDate").val();
	var strEndTime = $("#txt_ProductStyleEndDate").val();

	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/ProductStyleServlet?methodName=QueryList&strQuery="
						+ encode(strQuery) + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10",
				dataType : "text",
				async : false,
				success : function(r) {
					$("#ProductStyleList").html('');
					if (r == "false") {
						$("#ProductStyleList").append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}else if(r =="sessionOut"){
		            	doLogout();
		            	
		            }
					var data = $.parseJSON(r);

					$("#ProductStylePaging .dataCount").text(data.total);
					$("#ProductStylePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
					$("#ProductStylePaging .pageIndex").text(strPageIndex);
					var str = "";
					for (var i = 0; i < data.proList.length; i++) {
						str += GetProductStyleEntry(data.proList[i], i+1);
					}
					$("#ProductStyleList").append(str);
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
	
}

function DelProductStyle(id) {
	
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/ProductStyleServlet?methodName=del&id=" + id,
			dataType : "text",
			async : false,
			success : function(r) {
				  if (r == "true") {
	                    alertText("删除成功！", 3500);
					GetProductStyleList(1);
				}else if(r =="sessionOut"){
	            	doLogout();
	            	
	            }
				 else if(r == "false")
          	   {
          	   alertText("删除失败！", 3500);
          	   }
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
	
}

function showPDialog(methodName, Id, pos) {
    $("#iframeMain .wrap").animate({ width: "hide" });
    $('#productStyleDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/ProductStyleServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_productStyleParam").val("methodName=edit&id=" + Id);
                    $("#txt_productstyleName").val(r.Name);
                    $("#txt_productstyleRemark").val(r.Remark);
                }else if(r =="sessionOut"){
                	doLogout();
                	
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {
        $("#hid_productStyleParam").val("methodName=add");
        $("#txt_productstyleName").val("");
        $("#txt_productstyleRemark").val("");
        
        
    }
    
    
}

function SaveProductStyle() {
    var strName = $("#txt_productstyleName").val();
    var strRemark = $("#txt_productstyleRemark").val();
    if ($.trim(strName) == "") {
        alertText("类型名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ProductStyleServlet?methodName="
            					+ getParam("hid_productStyleParam", "methodName") + "&id=" + getParam("hid_productStyleParam", "id")
                                + "&name=" + encode(strName) + "&remark=" + encode(strRemark) ,
        dataType: "text",
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            } else if(r =="sessionOut"){
            	doLogout();
            	
            }else {
                alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateProductStyle(data);
                $('#productStyleDom_Edit').animate({ width: "hide" });
                $("#productStyleDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
function UpdateProductStyle(data){
    var str = "";
    if(getParam("hid_productStyleParam", "methodName") == "add"){
        str += GetProductStyleEntry(data, '*');
        $("#ProductStyleList").find('.noDataSrc').remove();
        $("#ProductStyleList").prepend(str);
        var dataTotal = parseInt($("#ProductStylePaging .dataCount").text())+1;
        $("#ProductStylePaging .dataCount").text(dataTotal)
        $("#ProductStylePaging .pageCount").text(parseInt((dataTotal + 9) / 10));
        $("#ProductStylePaging .pageIndex").text(parseInt((dataTotal + 9) / 10));
    }else if(getParam("hid_productStyleParam", "methodName") == "edit"){
        var obj =  $("#ProductStyleList").find('.editObject').find('td');
        obj.eq(0).html('*');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.Remark);
        obj.eq(3).html(data.CreatePerson);
        obj.eq(4).html(data.CreateTime);
        $("#ProductStyleList").find('.editObject').removeClass('editObject');
    }
}


