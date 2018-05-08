function FirstPage_WebsiteStyle() {
    GetWebsiteStyleList(1);
}

function PreviousPage_WebsiteStyle() {
    var pageIndex = (parseInt($("#websiteStylePaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#websiteStylePaging .pageIndex").text()) - 1);
    GetWebsiteStyleList(pageIndex);
}

function NextPage_WebsiteStyle() {
    var pageIndex = ($("#websiteStylePaging .pageIndex").text() == $(
			"#websiteStylePaging .pageCount").text() ? parseInt($(
			"#websiteStylePaging .pageIndex").text()) : parseInt($(
			"#websiteStylePaging .pageIndex").text()) + 1);
    GetWebsiteStyleList(pageIndex);
}

function EndPage_WebsiteStyle() {
    GetWebsiteStyleList(parseInt($("#websiteStylePaging .pageCount").text()));
}
/*
函数名：GetWebsiteStyleEntry
作用：获得网站分类列表中的一个条目
参数：data：网站分类的一条信息
idx:序列号
*/
function GetWebsiteStyleEntry(data, idx){//获取websiteStyle列表行
	
	  var str = "<tr>";
      str += "<td>" + idx + "</td>";
      str += "<td>" + data.Name + "</td>";
      str += "<td>" + data.Remark + "</td>";
      str += "<td>"
				+ data.CreateTime.substring(0, 19)
				+ "</td>";
      str += "<td><button class='btn btn-default btn-xs' onclick='showDialog(\"edit\",\""
				+ data.Id
				 + "\", this)'>编 辑</button>&nbsp;<button class='btn btn-default btn-xs' onclick='confirmShow(\""
				+ data.Id + "\", DelWebsiteStyle)'>删 除</button></td>";
      str += "</tr>";

    return str;
}
/*
函数名：GetWebsiteStyleList
作用：获取网站类型列表
*/
function GetWebsiteStyleList(strPageIndex) {//获取列表
    var strQuery = $("#txt_websiteStyleQuery").val();
    var strStraTime = $("#txt_websiteStyleBeginDate").val();
    var strEndTime = $("#txt_websiteStyleEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/WebsiteStyleServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
		// 查询过程中还未得到数据，显示loading界面
		beforeSend:function(XMLHttpRequest){
             $("#loading1").css("display","block");
			},
        success: function (r) {
            $("#websiteStyleList").html('');
			// 查询已经有了一个结果(不管有数据还是没有数据)，把loading界面关闭
            $("#loading1").css("display","none");
            if (r == "false") {
                $("#websiteStyleList")
						.append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();
            	
            }
            var data = $.parseJSON(r);
            $("#websiteStylePaging .dataCount").text(data.total);
            $("#websiteStylePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
            $("#websiteStylePaging .pageIndex").text(strPageIndex);//页码，条目数修改
            var str = "";
           
            for (var i = 0; i < data.webList.length; i++) {
            	 str += GetWebsiteStyleEntry(data.webList[i], i+1);
                
                
            }
            $("#websiteStyleList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：DelWebsiteStyle
作用：删除网站类型
参数：id（被删除的网站类型id）
*/
function DelWebsiteStyle(id) {
       $.ajax({
           type: "post",
           url: projectLocation
				+ "servlet/WebsiteStyleServlet?methodName=del&id=" + id,
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
                   GetWebsiteStyleList(1);
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
函数名：showDialog
作用：控制对于特定网站类型的编辑和添加
参数：id（被操作的条目id）
methodName：操作名称参数
pos：操作位置
*/
function showDialog(methodName, Id, pos) {
    // $("#iframeMain .wrap").animate({ width: "hide" });
    $("#iframeMain .wrap").css("display","none");
    $('#websiteStyleDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/WebsiteStyleServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
			// 添加过程中，显示loading界面
		    beforeSend:function(XMLHttpRequest){
             $("#loading1").css("display","block");
			},
            success: function (r) {
				// 添加操作完成(不管添加成功与否)，隐藏loading界面
			   $("#loading1").css("display","none");
                if (r != "false") {
                	var strRemark = r.Remark;
                	strRemark = strRemark.replace(/<br\/>/g,"\n");
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_websiteStyleParam").val("methodName=edit&id=" + Id);
                    $("#txt_websitestyleName").val(r.Name);
                    
                    $("#txt_websitestyleRemark").val(strRemark);
                }
                else if(r =="sessionOut"){
                	doLogout();
                	
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {//新增部分
        $("#hid_websiteStyleParam").val("methodName=add");
        $("#txt_websitestyleName").val("");
        
        $("#txt_websitestyleRemark").val("");
    }
}

/*
函数名：SaveWebsiteStyle
作用：保存网站类型

*/
function SaveWebsiteStyle() {
    var strName = $("#txt_websitestyleName").val();
    var strRemark = $("#txt_websitestyleRemark").val();
   
    strRemark = strRemark.replace(/\n/g,"<br/>");
    if ($.trim(strName) == "") {
        alertText("类型名不能为空！", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/WebsiteStyleServlet?methodName="
            					+ getParam("hid_websiteStyleParam", "methodName") + "&id=" + getParam("hid_websiteStyleParam", "id")
                                + "&name=" + encode(strName) + "&remark=" + encode(strRemark),
        dataType: "text",
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        async: false,
			// 保存过程中，显示loading界面
		beforeSend:function(XMLHttpRequest){
           $("#loading1").css("display","block");
		},
        success: function (r) {
			// 保存操作完成(不管保存成功与否)，隐藏loading界面
			$("#loading1").css("display","none");
            if (r == "false") {
            	 alertText("保存失败！", 3500);
                
            }else if(r =="sessionOut"){
            	doLogout();
            	
            }
            else {
            	alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateWebsiteStyle(data);
                $('#websiteStyleDom_Edit').animate({ width: "hide" });
                $("#websiteStyleDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：UpdateWebsiteStyle
作用：保存后更新网站类型列表
参数：data：被更改的网站类型信息
*/
function UpdateWebsiteStyle(data){
    var str = "";
    if(getParam("hid_websiteStyleParam", "methodName") == "add"){
        str += GetWebsiteStyleEntry(data, '*');
        $("#websiteStyleList").find('.noDataSrc').remove();
        $("#websiteStyleList").prepend(str);
        var dataTotal = parseInt($("#websiteStylePaging .dataCount").text())+1;
        $("#websiteStylePaging .dataCount").text(dataTotal)
        $("#websiteStylePaging .pageCount").text(parseInt((dataTotal + 9) / 10));
        $("#websiteStylePaging .pageIndex").text(parseInt((dataTotal + 9) / 10));
    }else if(getParam("hid_websiteStyleParam", "methodName") == "edit"){
        var obj =  $("#websiteStyleList").find('.editObject').find('td');
        obj.eq(0).html('*');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.Remark);
        obj.eq(3).html(data.CreateTime);
        $("#websiteStyleList").find('.editObject').removeClass('editObject');
    }
}