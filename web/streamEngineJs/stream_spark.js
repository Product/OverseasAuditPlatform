//星环配置

//跳转至星环首页
function SparkFirstPage() {
	GetSparkList(1);
}

//跳转至星环上一页
function SparkPreviousPage() {
    var pageIndex = (parseInt($("#SparkListPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#SparkListPaging .pageIndex").text()) - 1);
    GetSparkList(pageIndex);
}

//跳转至星环下一页
function SparkNextPage() {
	var standardPageIndex = parseInt($("#SparkListPaging .pageIndex").text());
	var standardPageCount = parseInt($("#SparkListPaging .pageCount").text());
    var pageIndex = standardPageIndex == standardPageCount ? standardPageIndex : (standardPageIndex + 1);
    GetSparkList(pageIndex);
}

//跳转至星环尾页
function SparkEndPage() {
	GetSparkList(parseInt($("#SparkListPaging .pageCount").text()));
}

//跳转至星环特定页
function GetSparkList(strPageIndex){
	$.ajax({
		type: "post",
		url: projectLocation 
				+ "servlet/StreamConfServlet?methodName=queryList&pageIndex=" + strPageIndex + "&pageSize=10",
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if (r == "false" || r == "") {
				$("#Spark_StreamingList").append("<tr><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
				return false;
			}
			var data = $.parseJSON(r);
			
			$("#SparkListPaging .dataCount").text(data.count);
			$("#SparkListPaging .pageCount").text(parseInt((parseInt(data.count) + 9) / 10));
			$("#SparkListPaging .pageIndex").text(strPageIndex);
			var str = "";
			for (var i = 0; i < data.list.length; i++) {
				str += "<tr>";
				str += "<td>" + (i+1) +"</td>";
				str += "<td>" + data.list[i].property +"</td>";
				str += "<td>" + data.list[i].value +"</td>";
				str += "<td>" + data.list[i].detail +"</td>";
				str += "<td>";
				/*str += "<button class=\"btn btn-default btn-xs\" onclick=\"SparkEdit("+data.list[i].id+","+data.list[i].property+","+data.list[i].value+","+data.list[i].detail+")\">配置</button>";*/
				str += "<button class=\"btn btn-default btn-xs\" onclick=\"SparkEdit("+data.list[i].id+")\">配置</button>";
				str += "</td></tr>";
			}
			$("#Spark_StreamingList").html(str);
		},
		error: function (e) {
			alert(e.responseText);
		}
	});
}
function SparkEdit(id){
	$("#rule_management_stream").hide();
	$("#Spark_Configuration_Edit").show();
	
	$("#SparkConfiguration_id").val(id);
	$("#txt_property").text("");
	$("#op_value").val("");
	$("#txt_detail").text("");
	
	$.ajax({
        type: "post",
        url: projectLocation 
			+ "servlet/StreamConfServlet?methodName=queryById&id="+id,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
            	$("#SparkConfiguration_id").val(r.id);
            	$("#txt_property").text(r.property);
            	$("#op_value").val(r.value);
            	$("#txt_detail").text(r.detail);
            }else if (r == "sessionOut") {
                doLogout();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//function SparkEdit(id,property,value,detail){
//	$("#rule_management_stream").hide();
//	$("#Spark_Configuration_Edit").show();
//	
//	$("#SparkConfiguration_id").val(id);
//	$("#txt_property").val(property);
//	$("#op_value").val(value);
//	$("#txt_detail").val(detail);
//}

function SaveSparkConfiguration(){
	var strId=$("#SparkConfiguration_id").val();
	var strValue=$("#op_value").val();
	
	$.ajax({
        type: "post",
        url: projectLocation 
			+ "servlet/StreamConfServlet?methodName=addOrUpdate&id="+strId+"&value="+strValue,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "success") {
            	$("#rule_management_stream").show();
            	$("#Spark_Configuration_Edit").hide();
            	alertText("配置成功！", 3500);
            	GetSparkList(1);
            }else if (r == "sessionOut") {
                doLogout();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}