function InfoFirstPage() {
    GetInfoPubList(1);
}

function InfoPreviousPage() {
    var pageIndex = (parseInt($("#infoPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#infoPaging .pageIndex").text()) - 1);
    GetInfoPubList(pageIndex);
}

function InfoNextPage() {
    var pageIndex = ($("#infoPaging .pageIndex").text() == $(
			"#infoPaging .pageCount").text() ? parseInt($(
			"#infoPaging .pageIndex").text()) : parseInt($(
			"#infoPaging .pageIndex").text()) + 1);
    GetInfoPubList(pageIndex);
}

function InfoEndPage() {
    GetInfoPubList(parseInt($("#infoPaging .pageCount").text()));
}

/*获取网站表项*/
function GetInfoEntry(data, idx){
    var str = "<tr>";
    str += "<td><img src='images/dot.png' align='left'/>&nbsp;<a href='#' onclick='getViewInfo("+data.id+")'>"+data.title+"</a></td>"
    str += "<td><div align='right'>"+data.creatTime.substring(0, 19)+"</div></td>";
    str += "</tr>";
    return str;
}


function UpdateGetInfoEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.type + "</td>";
    str += "<td>" + data.title + "</td>";
    str += "<td>" + data.author + "</td>";
    str += "<td>" + data.creatTime.substring(0, 19) + "</td>";
    str += "<td>" + data.keyword + "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='查看' onclick='getViewInfo(\""
        + data.id + "\")' /></td>";
    str += "</tr>";
    return str;
}
/*
 函数名：GetWebsiteLList
 作用：获取网站列表
 */
function GetInfoPubList(strPageIndex) {
    // 查询标题
    var strQuery = $("#key_word").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/getInfoDiffusion?method=queryByKey&keyword=" + encode(strQuery)
				 + "&pageIndex="+ strPageIndex + "&pageSize=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#info_list").html('');
            if (r == "false") {
                $("#info_list")
						.append("<tr class='noDataSrc'><td colspan='2' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();
            	
            }
            var data = $.parseJSON(r);
            if (data.total == 0) {
                $("#info_list")
                    .append("<tr class='noDataSrc'><td colspan='2' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
			$("#infoPaging .dataCount").text(data.total);
			$("#infoPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
			$("#infoPaging .pageIndex").text(strPageIndex);
			
            var str = "";
            for (var i = 0; i < data.infoList.length; i++) {
            	str += GetInfoEntry(data.infoList[i], i+1);
            }
            $("#info_list").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

/*
 函数名：getViewInfo
 作用：根据id获取信息详情
 */
function getViewInfo (id) {
    $("#iframeMain .wrap").hide();
    $("#information_publish").hide();
    $("#information_detail").show();
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/getInfoDiffusion?method=queryById&id=" + id,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                $("#content_title").text(r.title);
                $("#content_author").text("作者："+r.author);
                $("#content_time").text("时间："+r.creatTime.substring(0,19));
                $("#content_type").text("类型："+r.type);
                $("#content_keyword").text("关键字："+r.keyword);
                $("#content_content").text(r.content);
            }
            else if(r =="sessionOut"){
                doLogout();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function addInfo() {
    $("#iframeMain .wrap").hide();
    $("#information_add").show();
    $("#txt_infoName").val('');
    $("#txt_author").val('');
    $("#txt_type").val('');
    $("#txt_createTime").val('');
    $("#txt_keyword").val('');
    $("#txt_content").val('');
}

function saveInfo() {
    var title = $("#txt_infoName").val();
    var author = $("#txt_author").val();
    var type = $("#txt_type").val();
    var createTime = $("#txt_createTime").val();
    var keyword = $("#txt_keyword").val();
    var content = $("#txt_content").val();
    var publishPerson = $("#txt_publishPerson").val();

    if ($.trim(title) == "") {
        alertText("名称不能为空！", 3500);
        return false;
    }

    if ($.trim(createTime) == "") {
        alertText("发布时间不能为空！", 3500);
        return false;
    }

    var re = /^[0-9]*[1-9][0-9]*$/ ;
    if (!re.test(type)) {
        alertText("类别请输入整数！", 3500);
        return false;
    }

    $.ajax({
        type: "post",
        url: projectLocation + "servlet/getInfoDiffusion?method=add&title="+ encode(title) +
        "&author="+ encode(author) +"&type="+ encode(type) +"&createTime="+ createTime +
        "&keyword="+ encode(keyword) +"&content="+ encode(content)+"&target="+publishPerson,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r == false) {
                alertText("保存失败！", 3500);
            }
            else if(r =="sessionOut"){
                doLogout();

            }else {
                $("#key_word").val("");
                alertText("保存成功！", 3500);
                //var data = $.parseJSON(r);
                GetInfoPubList(1);
                UpdateInfo(r);
                $('#information_add').hide();
                $("#information_publish").show();
            }
        },
        error: function (e) {
            alertText(e.responseText, 3500);
           // alert(e.responseText);
        }
    });
}

function UpdateInfo (data) {
    var str = "";
    str += UpdateGetInfoEntry(data, '*');
    $("#info_list").find('.noDataSrc').remove();
    $("#info_list").prepend(str);
    var dataTotal = parseInt($("#infoPaging .dataCount").text())+1;
    $("#infoPaging .dataCount").text(dataTotal)
    $("#infoPaging .pageCount").text(parseInt((dataTotal + 9) / 10));
    $("#infoPaging .pageIndex").text(parseInt((dataTotal + 9) / 10));
}

