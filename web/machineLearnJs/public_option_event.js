$(function(){
	$("#displayTitle").change(function(){
		var strid= $("#editID").val();
		var strName =$("#displayTitle").val();
		if($.trim(strName) == ""){
			alertText("事件名不能为空！", 3500);
	        return false;
		}
		$.ajax({
			type : "post",
			url : "servlet/EventServlet?methodName=editEventName&id="+strid+"&eventName="+encode(strName),
			dataType : "text",
			async : false,
			success : function(r) {
				if (r == "success" ) {
					alertText("编辑成功！",3500);
					initPubOpiTree();
				}else if(r =="sessionOut"){
	            	doLogout();
	            	
	            }
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
		
	});
});
function initPubOpiTree() {
    var res = getPubOpiEventTree();
    $("#eventList-tree").html("");
    $("#eventList-tree").append(res);
}

function getPubOpiEventTree () {
    var str="";
    $.ajax({
        type: "post",
        url: projectLocation
        + "servlet/EventServlet?methodName=QueryListInit" ,
        contentType: "application/json;charset=utf-8",
        dataType:"text",
        async: false,
        success: function (r) {
            if (r == "false") {
                return false;
            }
            else if(r =="sessionOut"){
                doLogout();
                return false;
            }
            else {
                var data = $.parseJSON(r);
                if (data.eventList.length > 0) {
                    for (var i = 0; i < data.eventList.length; i++) {
                        str += "<li><span onclick='beforeInit("+ data.eventList[i].Id +",this)'>" + data.eventList[i].Name + "</span></li>";
                    }
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });

    return str;
}

function beforeInit(id,obj) {
    var parentName;
    parentName = $(obj).parent().parent().siblings("span").text();
    if (parentName == "") {
        parentName = $(obj).text();
    }
    // init方法
    if ($(obj).hasClass('badge-orange')) {
        $(obj).removeClass('badge-orange');
    }
    else {
        $(obj).parent().parent().siblings().removeClass('badge-orange');
        $(obj).parent().siblings().children().removeClass('badge-orange');
        $(obj).addClass('badge-orange');
    }
    initEvent(id,parentName);
}

function initEvent(id,parentName) {
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/ArticleServlet?methodName=QueryListInit&strEventId=" + id,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
                $("#displayTitle").val(parentName);
                $("#editID").val(id);
                var data = $.parseJSON(r);
                var str = "";
                if (data.webList.length > 0) {
                    for (var i = 0; i < data.webList.length; i++) {
                        str += "<div style='margin-bottom: -45px;'>";
                        if(i == 0) {
                            str += "<div style='float: left' class='circle'>开始</div>";
                        }
                        else {
                            str += "<div style='float: left' class='circle'>传播</div>";
                        }
                        
                        str += "<div style='float: left;position: relative;left:30%'>";
                        str += "<span></span><br>";
                        str += "<span>"+ data.webList[i].CreateTime +"</span></div>";
                        str += "<div class='circle_title'></div>";
                        str += "<div class='time-content'><a href='"+data.webList[i].Location+"' target='_blank'>"+ data.webList[i].Name +"</a></div>";
                        str += "</div>";
                   }
                }
                else {
                    str = "暂无数据！";
                }
                $("#event_display").html(str);
            }
            else if(r == "sessionOut"){
                doLogout();
            }
            else  {
                $("#event_display").html("暂无数据！");
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

