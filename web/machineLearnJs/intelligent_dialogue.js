
var webUrl = document.URL.split("TonikIntegration");
var wsUri = "ws:"+webUrl[0].slice(5)+"TonikIntegration/chat";
var websocket;
// 智能对话
function intelligentDialogue() {
    webSocket();
    $("#talk_record").scrollTop($("#talk_record")[0].scrollHeight);
    $("#jp-container").html("");
}

function webSocket() {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) {
        onOpen(evt)
    };
    websocket.onclose = function(evt) {
        onClose(evt)
    };
    websocket.onmessage = function(evt) {
        onMessage(evt)
    };
    websocket.onerror = function(evt) {
        onError(evt)
    };
}

function onOpen(evt) {
    //writeToScreen("CONNECTED");
    //doSend("WebSocket rocks");
}

function onClose(evt) {
   // writeToScreen("DISCONNECTED");
}

function onMessage(evt) {
    var data = JSON.parse(evt.data);
    if (data.answer) {
        if (data.answer.length > 500) {
            var len = Math.ceil(data.answer.length/500);
            for (var i = 0; i<len; i++) {
                var answerContent = data.answer.substr(i*500,500);
                forwardMessageFormat(answerContent,data.answerTime);
            }
        }
        else {
            forwardMessageFormat(data.answer,data.answerTime);
        }
    }


   // websocket.close();
}

function onError(evt) {
}

function p(s) {
    return s < 10 ? '0' + s: s;
}

function getFormatTime(currentDate) {
    //获取当前年
    var year=currentDate.getFullYear();
    //获取当前月
    var month=currentDate.getMonth()+1;
    var date=currentDate.getDate();
    var h=currentDate.getHours();       //获取当前小时数(0-23)
    var m=currentDate.getMinutes();     //获取当前分钟数(0-59)
    var s=currentDate.getSeconds();
    var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
    return now;
}

function doSend() {
    var message = $("#message").val();
    var myDate = getFormatTime(new Date());
    toMessageFormat(message,myDate);
    var content = "{ \"question\": \"" + message + "\", \"questionTime\" : \"" + myDate + "\"}";
    websocket.send(content);
}

function toMessageFormat (message,date) {
    var strUserCode = $("#txt_userCode").val();
    var str = "<div class='talk_recordboxme'>";
     str += "<div class='user'><img class='talk_img' src='images/picture.jpg' />"+strUserCode+"</div>";
     str += "<div class='talk_recordtextbg'>&nbsp;</div>";
     str += "<div class='talk_recordtext'>";
     str += "<h3>";
     str += message;
     str += "</h3>";
     str += "<span class='talk_time'>";
     str += date;
     str += "</span>";
     $("#jp-container").append(str);
     $("#talk_record").scrollTop($("#talk_record")[0].scrollHeight);

}

function forwardMessageFormat (message,time) {
    var str = "<div class='talk_recordbox'>";
    str += "<div class='user'><img class='talk_img' src='images/timg.jpg' />机器人</div>";
    str += "<div class='talk_recordtextbg'>&nbsp;</div>";
    str += "<div class='talk_recordtext'>";
    str += "<h3>";
    str += message;
    str += "</h3>";
    str += "<span class='talk_time'>";
    str += time.substring(0,19);
    str += "</span>";
    $("#jp-container").append(str);
    $("#talk_record").scrollTop($("#talk_record")[0].scrollHeight);
}

function doClean () {
    $("#message").val("");
}


function dialogueRecord() {
    var myDate = getFormatTime(new Date());
    $("#txt_dialogueDate").val(myDate);
    dialogueRecordList(1);
}

function diaRecFirstPage() {
    dialogueRecordList(1);
}

function diaRecPreviousPage() {
    var pageIndex = (parseInt($("#dialogueRecordPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#dialogueRecordPaging .pageIndex").text()) - 1);
    dialogueRecordList(pageIndex);
}

function diaRecNextPage() {
    var pageIndex = ($("#dialogueRecordPaging .pageIndex").text() == $(
			"#dialogueRecordPaging .pageCount").text() ? parseInt($(
			"#dialogueRecordPaging .pageIndex").text()) : parseInt($(
			"#dialogueRecordPaging .pageIndex").text()) + 1);
    dialogueRecordList(pageIndex);
}

function diaRecEndPage() {
    dialogueRecordList(parseInt($("#dialogueRecordPaging .pageCount").text()));
}

/*获取对话实体*/
function GetDiaRecEntry(data){
    var strUserCode = $("#txt_userCode").val();
    var str = "<ul class='dia_racord'>";
    str += "<li class='green_font'>"
    str += "<span>" + strUserCode + "</span>";
    str += "<span>" + data.questionTime.substring(0,19) + "</span>";
    str += "</li>";
    str += "<li>" + data.question + "</li>";
    str += "<li class='blue_font'>"
    str += "<span>机器人</span>";
    str += "<span>" + data.answerTime.substring(0,19) + "</span>";
    str += "</li>";
    str += "<li>" + data.answer + "</li>";
    str += "</ul>";
    return str;
}

/*
 函数名：dialogueRecordList
 作用：获取对话列表
 */
function dialogueRecordList(strPageIndex) {
    var query = $("#txt_dialogueQuery").val();
    var startTime = $("#txt_dialogueDate").val();
    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/ChattingMessageServlet?methodName=chattingRecord&strQuery="+ encode(query)
                +"&startTime="+ encodeURIComponent(startTime) +"&pageIndex="+ strPageIndex + "&pageSize=10",
        contentType: "application/json;charset=utf-8",
        dataType : "text",
        async: false,
        success: function (r) {
            $("#diaRecList").html('');
            if (r == "false") {
                $("#diaRecList")
						.html("<ul><li>暂无数据！！</li></ul>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();

            }
            else {
                var data = $.parseJSON(r);
                $("#dialogueRecordPaging .dataCount").text(data.total);
                $("#dialogueRecordPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
                $("#dialogueRecordPaging .pageIndex").text(strPageIndex);

                if (data.list.length > 0 ) {
                    var str = "";
                    for (var i = 0; i < data.list.length; i++) {
                        str += GetDiaRecEntry(data.list[i]);
                    }
                    $("#diaRecList").html(str);
                }
                else {
                    $("#diaRecList")
                        .html("<ul><li>暂无数据！！</li></ul>");
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}