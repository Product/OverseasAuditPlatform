function FirstPage_ExtractView() {
	GetExtractViewList(1);
}

function PreviousPage_ExtractView() {
    var pageIndex = (parseInt($("#ExtractViewPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#ExtractViewPaging .pageIndex").text()) - 1);
    GetExtractViewList(pageIndex);
}

function NextPage_ExtractView() {
    var pageIndex = ($("#ExtractViewPaging .pageIndex").text() == $(
			"#ExtractViewPaging .pageCount").text() ? parseInt($(
			"#ExtractViewPaging .pageIndex").text()) : parseInt($(
			"#ExtractViewPaging .pageIndex").text()) + 1);
    GetExtractViewList(pageIndex);
}

function EndPage_ExtractView() {
	GetExtractViewList(parseInt($("#ExtractViewPaging .pageCount").text()));
}

function GetExtractViewList(strPageIndex){
    var strQuery = $("#txt_ExtractViewQuery").val();
    var strStraTime = $("#txt_ExtractViewBeginDate").val();
    var strEndTime = $("#txt_ExtractViewEndDate").val();

    var str = "servlet/ExcelExportServlet?strQuery=" + strQuery + "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime;
    $("#DateExportHref").attr('href', str);
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ExtractionServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#ExtractAllViewList").html('');
            if (r == "false") {
                $("#ExtractAllViewList")
						.append("<tr><td colspan='7' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            var data = $.parseJSON(r);

            $("#ExtractViewPaging .dataCount").text(data.total);
            $("#ExtractViewPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#ExtractViewPaging .pageIndex").text(strPageIndex);
            var str = "";
            for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td>" + (i+1) +"</td>";
                str += "<td>" + data.webList[i].ProductName+"</td>";
                str += "<td>" + data.webList[i].RulesName +"</td>";
                str += "<td>" + data.webList[i].ProductBrand +"</td>";
                str += "<td>" + data.webList[i].ProductPrice +"</td>";
                str += "<td>" + data.webList[i].ExtractTime.substring(0, 19) + "</td>";
                str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showDialog_ProductDetail(\"productDetail\",\""
						+ data.webList[i].Id
						+ "\")' />";
                str += "</tr>";
            }
            $("#ExtractAllViewList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function extractionDialog(methodName,Id) {
	$.ajax({
		type : "post",
		url: projectLocation + "servlet/ExtractionServlet?methodName=" + methodName + "&Id=" + Id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r == "true") {
				alertText("抽取成功！", 3500);
			} else {
				alertText("抽取失败！", 3500);
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	alert("商品抽取中...");
}

function ExtractViewDialog(methodName,Id){
	$("#iframeMain .wrap").animate({
		width : "hide"
	});
	$('#rulesDom_extractDetail').show();
	$(obj).parent().parent().children("li").children("a")
			.removeClass("current");
	$(obj).addClass("current");
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/ExtractionServlet?methodName=ExtractView&Id="+Id,
		contentType : "application/json;charset=utf-8",
		async : false,
		success : function(r) {
			$("#ExtractViewList").html('');
			if (r == "false") {
				$("#ExtractViewList")
				.append("<tr><td colspan='5' style='text-align:center;'>没有符合规则的商品！</td></tr>");
				return false;
			}
			var data = $.parseJSON(r);
			var str = "";
			for (var i = 0; i < data.webList.length; i++) {
                str += "<tr>";
                str += "<td>" + data.webList[i].ProductName +"</td>";
                str += "<td>" + data.webList[i].RulesName +"</td>";
                str += "<td>" + data.webList[i].ProduceBrand +"</td>";
                str += "<td>" + data.webList[i].Price +"</td>";
                str += "<td>" + data.webList[i].ExtractTime.substring(0, 19) +"</td>";
                str += "</tr>";
			}
			$("#ExtractViewList").append(str);
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}