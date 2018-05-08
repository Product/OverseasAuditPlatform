function sPubOpiFirstPage() {
    GetStreamPubOpiList(1);
}

function sPubOpiPreviousPage() {
    var pageIndex = (parseInt($("#streamPubOpiListPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#streamPubOpiListPaging .pageIndex").text()) - 1);
    GetStreamPubOpiList(pageIndex);
}

function sPubOpiNextPage() {
    var pageIndex = ($("#streamPubOpiListPaging .pageIndex").text() == $(
			"#streamPubOpiListPaging .pageCount").text() ? parseInt($(
			"#streamPubOpiListPaging .pageIndex").text()) : parseInt($(
			"#streamPubOpiListPaging .pageIndex").text()) + 1);
    GetStreamPubOpiList(pageIndex);
}

function sPubOpiEndPage() {
    GetStreamPubOpiList(parseInt($("#streamPubOpiListPaging .pageCount").text()));
}

/*
 函数名：GetStreamPubOpiList
 作用：获取舆情管理列表
 */
function GetStreamPubOpiList(strPageIndex) {
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ArticleManagementServlet?methodName=context&pageIndex="
				+ strPageIndex + "&pageSize=10",
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: false,
        success: function (r) {
            $("#streamPubOpiList").html('');
            if (r == "false") {
                $("#streamPubOpiList")
						.html("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();

            }
            var data = $.parseJSON(r);
			$("#streamPubOpiListPaging .dataCount").text(data.articleManagementCount);
			$("#streamPubOpiListPaging .pageCount").text(parseInt((parseInt(data.articleManagementCount) + 9) / 10));
			$("#streamPubOpiListPaging .pageIndex").text(strPageIndex);

            if (data.articleManagementList.length > 0 ) {
                var str = "";
                for (var i = 0; i < data.articleManagementList.length; i++) {
                    str += "<tr>"
                    str += "<td>" + (i+1) + "</td>";
                    str += "<td><a href='"+data.articleManagementList[i].articlesourceUrl+"' target='_blank'>" + data.articleManagementList[i].articletitle + "</a></td>";
                    str += "<td><a href='#' onclick='getArticleTranslation("+data.articleManagementList[i].articleId+")' >" + data.articleManagementList[i].translatedtitle + "</a></td>";
                    str += "<td>" + data.articleManagementList[i].keywords + "</td>";
                    str += "<td>" + data.articleManagementList[i].classification + "</td>";
                    str += "<td>" + data.articleManagementList[i].articlesourceUrl + "</td>";
                    str += "<td>" + data.articleManagementList[i].articleCreateTime + "</td>";
                    str += "</tr>";
                }
                $("#streamPubOpiList").html(str);
            }
            else {
                $("#streamPubOpiList")
                    .html("<tr class='noDataSrc'><td colspan='5' style='text-align:center;'>无数据！</td></tr>");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//获取全文翻译
function getArticleTranslation(id){
	$("#public_opinion_stream").hide();
	$("#translation_View_stream").show();

	$.ajax({
		type : "post",
		url : projectLocation
			+ "servlet/ArticleManagementServlet?methodName=queryById&id="+id,
		dataType : "json",
		async : false,
		success : function(r) {
			if (r != false) {
				$("#translation_title_stream").text(r.translatedtitle);
				$("#translation_content_stream").text(r.translatedContent);
				
			}else if(r =="sessionOut"){
            	doLogout();
            }
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

// 舆情采集按钮点击
function pubOpiCol() {
    $("#public_opinion_stream").hide();
    $("#public_opinion_collection").show();
    GetStreamPubOpiColList(1);
}

function sPubOpiColFirstPage() {
    GetStreamPubOpiColList(1);

}

function sPubOpiColPreviousPage() {
    var pageIndex = (parseInt($("#streamPubOpiColListPaging .pageIndex").text()) == 1 ? 1
        : parseInt($("#streamPubOpiColListPaging .pageIndex").text()) - 1);
    GetStreamPubOpiColList(pageIndex);
}

function sPubOpiColNextPage() {
    var pageIndex = ($("#streamPubOpiColListPaging .pageIndex").text() == $(
        "#streamPubOpiColListPaging .pageCount").text() ? parseInt($(
        "#streamPubOpiColListPaging .pageIndex").text()) : parseInt($(
        "#streamPubOpiColListPaging .pageIndex").text()) + 1);
    GetStreamPubOpiColList(pageIndex);
}

function sPubOpiColEndPage() {
    GetStreamPubOpiColList(parseInt($("#streamPubOpiColListPaging .pageCount").text()));
}

/*获取产品管理表项*/
function GetStreamPubOpiColEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.name + "</td>";
    str += "<td>" + data.pageTotal + "</td>";
    str += "<td>" + data.titleSign + "</td>";
    str += "<td>" + data.createTimeSign + "</td>";
    str += "<td>" + data.nextSign + "</td>";
    str += "<td>" + data.url + "</td>";
    str += "<td>" + data.siteName + "</td>";
    var rateStr = getRate(data.rate);
    str += "<td>" + rateStr + "</td>";
    str += "<td>舆情文章</td>";
    str += "<td>执行中</td>";
    str += "<td>" + data.createTime.substring(0,19) + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='InitStreamPubOpiCol(\""
        + data.id
        + "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
        + data.id + "\",DelStreamPubOpiCol)' /></td>";
    str += "</tr>";
    return str;
}

function getRate (rate) {
    var str = "";
    switch (rate) {
        case 1:
            str = "一天";
            break;
        case 2:
            str = "一周";
            break;
        default :
            str = "一个月";
    }
    return str;
}
/*
 函数名：GetStreamPubOpiColList
 作用：获取采集器列表
 */
function GetStreamPubOpiColList(strPageIndex) {
    $.ajax({
        type: "post",
        url: projectLocation
       // + "servlet/CollectorCfg?method=listPublicOpinion",
        + "servlet/CollectorCfg?method=listPublicOpinion&pageIndex="
        + strPageIndex + "&pageSize=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#streamPubOpiColList").html('');
            if (r == "false") {
                $("#streamPubOpiColList")
                    .html("<tr class='noDataSrc'><td colspan='13' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
            }
            else {
                var data = $.parseJSON(r);
                $("#streamPubOpiColListPaging .dataCount").text(data.count);
                $("#streamPubOpiColListPaging .pageCount").text(parseInt((parseInt(data.count) + 9) / 10));
                $("#streamPubOpiColListPaging .pageIndex").text(strPageIndex);

                if (data.infoList.length > 0 ) {
                    var str = "";
                    for (var i = 0; i < data.infoList.length; i++) {
                        str += GetStreamPubOpiColEntry(data.infoList[i], i+1);
                    }
                    $("#streamPubOpiColList").html(str);
                }
                else {
                    $("#streamPubOpiColList")
                        .html("<tr class='noDataSrc'><td colspan='13' style='text-align:center;'>无数据！</td></tr>");
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
 函数名：InitStreamPubOpiCol；
 作用：获取采集器
 */
function InitStreamPubOpiCol(id,obj){
	$("#public_opinion_collection").hide();
    $("#collection_Edit").show();
    $("#collection_Edit .Title_tab").html("采集器编辑");
    $("#hid_StreamPubOpiColParam").val('edit');
    $(obj).parent().parent().addClass('editObject');
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/CollectorCfg?method=queryById&id="+id,
		dataType : "text",
		async : false,
		success : function(r) {
			if (r != "false") {
                $("#collection_id").val(id);
				var data = $.parseJSON(r);
                $("#txt_collectionName").val(data.name);
                $("#txt_collectionRate").val(data.rate);
                $("#txt_collectionUrl").val(data.url);
                $("#txt_collectionTitleSign").val(data.titleSign);
                $("#txt_collectionCreateTimeSign").val(data.createTimeSign);
                $("#txt_collectionNextSign").val(data.nextSign);
                $("#txt_collectionPageTotal").val(data.pageTotal);
                $("#txt_collectionSiteName").val(data.siteName);
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
    $("#hid_StreamPubOpiColParam").val('add');

    $("#public_opinion_collection").hide();
    $("#collection_Edit").show();
    $("#collection_Edit .Title_tab").html("采集器新增");
    $("#collection_id").val("");
    $("#txt_collectionName").val("");
    $("#txt_collectionRate").val("");
    $("#txt_collectionUrl").val("");
    $("#txt_collectionTitleSign").val("");
    $("#txt_collectionCreateTimeSign").val("");
    $("#txt_collectionNextSign").val("");
    $("#txt_collectionPageTotal").val("");
    $("#txt_collectionSiteName").val("");
}

/*
函数名：DelStreamPubOpiCol
作用：删除采集器
参数：id（被删除的采集器id）
*/
function DelStreamPubOpiCol(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/CollectorCfg?method=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetStreamPubOpiColList(1);
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
函数名：StreamPubOpiCol
作用：保存采集器
*/
function SaveStreamPubOpiCol() {
    var id = $("#stream_id").val();
    var name = $("#txt_collectionName").val();
    var rate = $("#txt_collectionRate").val();
    var url = $("#txt_collectionUrl").val();
    var titleSign = $("#txt_collectionTitleSign").val();
    var createTimeSign = $("#txt_collectionCreateTimeSign").val();
    var nextSign = $("#txt_collectionNextSign").val();
    var pageTotal = $("#txt_collectionPageTotal").val();
    var siteName = $("#txt_collectionSiteName").val();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/CollectorCfg?method=editOrAddPublicOpinion"
        + "&id=" + id + "&url=" + encode(url) + "&pageTotal=" + encode(pageTotal) +"&titleSign=" + encode(titleSign)
        + "&createTimeSign=" + encode(createTimeSign) + "&nextSign=" + encode(nextSign) + "&siteName=" + encode(siteName) +
        "&name=" + encode(name) + "&rate=" + rate,
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
            	 var data = $.parseJSON(r);
                 UpdateStreamPubOpiCol(data);
                $("#public_opinion_collection").show();
                $("#collection_Edit").hide();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：UpdateStreamPubOpiCol
作用：保存后更新采集器列表
参数：data：被更改的采集器信息
*/
function UpdateStreamPubOpiCol(data){
    var str = "";
    if($("#hid_StreamPubOpiColParam").val() == "add"){
        str += GetStreamPubOpiColEntry(data, '*');
        $("#streamPubOpiColList").find('.noDataSrc').remove();
        $("#streamPubOpiColList").prepend(str);
    } else if($("#hid_StreamPubOpiColParam").val() == "edit") {
        var obj = $("#streamPubOpiColList").find('.editObject').find('td');
        obj.eq(0).html('*');
        obj.eq(1).html(data.name);
        obj.eq(2).html(data.pageTotal);
        obj.eq(3).html(data.titleSign);
        obj.eq(4).html(data.createTimeSign);
        obj.eq(5).html(data.nextSign);
        obj.eq(6).html(data.url);
        obj.eq(7).html(data.siteName);
        obj.eq(8).html(getRate(data.rate));
        obj.eq(9).html("舆情文章");
        obj.eq(10).html("执行中");
        obj.eq(11).html(data.createTime.substring(0,19));
        $("#streamPubOpiColList").find('.editObject').removeClass('editObject');
    }
}

////根据可选特征id#字符串获取名字
//function getOptions(str) {
//    var items = str ? str.split("#") : [];
//    var optionStr = "";
//    for (var i = 0; i < items.length; i++) {
//        switch (items[i]) {
//            case "1":
//                optionStr += "分段、";
//                break;
//            case "2":
//                optionStr += "分段、";
//                break;
//            case "3":
//                optionStr += "分段、";
//                break;
//            default :
//                optionStr += "分段、";
//                break;
//        }
//    }
//    optionStr = optionStr.substring(0,optionStr.length-1);
//    return optionStr;
//}
