//首页
function FirstPageEpidemic() {
	GetEpidemicList(1);
}

//上一页
function PreviousPageEpidemic() {
	var pageIndex = (parseInt($("#EpidemicPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#EpidemicPaging .pageIndex").text()) - 1);
	GetEpidemicList(pageIndex);
}

//下一页
function NextPageEpidemic() {
	var pageIndex = ($("#EpidemicPaging .pageIndex").text() == $("#EpidemicPaging .pageCount").text() 
			? parseInt($("#EpidemicPaging .pageIndex").text()) 
			: parseInt($("#EpidemicPaging .pageIndex").text()) + 1);
	GetEpidemicList(pageIndex);
}

//尾页
function EndPageEpidemic() {
	GetEpidemicList(parseInt($("#EpidemicPaging .pageCount").text()));
}

//将得到的数据转变成html格式代码字符串
function GetEpidemicEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
	str += "<td>" + data.Title + "</td>";
	str += "<td>" + data.Content + "</td>";
	str += "<td>" + data.Remark + "</td>";
	str += "<td>" + data.CreateTime.substring(0, 19) + "</td>";
	str += "<td><input class='btn btn-default btn-xs' type='button' value='查 看' onclick='showDialogEpidemicDetail(\"detail\",\"" + data.Id + "\")' />&nbsp;"
			 + "<input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showDialogEpidemic(\"edit\",\"" + data.Id + "\",this)' />&nbsp;"
			 + "<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\"" + data.Id + "\", DelEpidemic)' />"
			 + "</td>";
	str += "</tr>";
    return str;
}

//显示疫情列表
function GetEpidemicList(strPageIndex) {
	//关键字查询
	var strQuery = $("#txt_EpidemicQuery").val();
	//创建开始时间查询
	var strStraTime = $("#txt_EpidemicBeginDate").val();
	//创建结束时间查询
	var strEndTime = $("#txt_EpidemicEndDate").val();
	
	$.ajax({
				type : "post",
				url : projectLocation
						+ "servlet/EpidemicServlet?methodName=QueryList&strQuery="
						+ strQuery + "&strStraTime=" + strStraTime
						+ "&strEndTime=" + strEndTime + "&strPageIndex="
						+ strPageIndex + "&strPageCount=10",
				dataType : "text",
				async : false,
				success : function(r) {
					$("#EpidemicList").html('');
					if (r == "false") {
						$("#EpidemicList").append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
						return false;
					}
					else if (r == "sessionOut") {
		                doLogout();
		            }
					var data = $.parseJSON(r);
					$("#EpidemicPaging .dataCount").text(data.total);
					$("#EpidemicPaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
					$("#EpidemicPaging .pageIndex").text(strPageIndex);
					var str = "";
					for (var i = 0; i < data.EpidemicList.length; i++){
						 str += GetEpidemicEntry(data.EpidemicList[i], i+1);
					}
					$("#EpidemicList").append(str);
				},
				error : function(e) {
					alert(e.responseText);
				}
			});
}

//疫情库页面查看功能
function showDialogEpidemicDetail(methodName, Id) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#EpidemicDom_Detail').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EpidemicServlet?methodName=init&id=" + Id,
            dataType: "text",
            async: false,
            success: function (data) {
                if (data != "false") {
                	InitEPTypeDetail()
                    var r = $.parseJSON(data);
                    $("#hid_EpidemicParamA").val("methodName=detail&id=" + Id);
                    $("#txt_EpidemicNameA").val(r.Title);
					$("#txt_EpidemicContentA").val(r.Content);
					$("#txt_EpidemicRemarkA").val(r.Remark);
					for (var i = 0; i < document.getElementsByName("txt_EpidemicProductTypeA").length; i++) 
	                {
	                	for(var j = 0;j<r.epsList.length;j++)
	                	{
	                		if((document.getElementsByName("txt_EpidemicProductTypeA")[i].value)==r.epsList[j].Id)
	                			document.getElementsByName("txt_EpidemicProductTypeA")[i].checked=true;
	                	}
	            	}
                }
                else if (r == "sessionOut") {
                    doLogout();
                }
    			else{
    				alert("编辑失败！");
    			}
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    
}

//查看已保存的商品类型
function InitEPTypeDetail() {
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/EpidemicServlet?methodName=getProductStyle",
		dataType : "json",
		async : false,
		success : function(r) {
			if (r != "false") {
				var str = "";
				for (var i = 0; i < r.ProductStyle.length; i++)
				{
					str += "<input type='checkbox' id='txt_EpidemicProductTypeA' name='txt_EpidemicProductTypeA'" +
							" value='" + r.ProductStyle[i].Id + "'>" + r.ProductStyle[i].Name + "</input>&nbsp;&nbsp;&nbsp;";
				}
				$("#EPTypeDetailList").html(str);
			}
			else if (r == "sessionOut") {
                doLogout();
            }
			else{
				alert("加载失败！");
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

//疫情库新增及编辑功能
function showDialogEpidemic(methodName, Id, pos) {
	$("#iframeMain .wrap").animate({ width: "hide" });
    $('#EpidemicDom_Edit').show();
    $("#Epidemic").html("");
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    //编辑
    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/EpidemicServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	InitEPType();
                	$(pos).parent().parent().addClass('editObject');
                	$("#hid_EpidemicParamB").val("methodName=edit&id=" + Id);
					$("#txt_EpidemicNameB").val(r.Title);
					$("#txt_EpidemicContentB").val(r.Content);
					$("#txt_EpidemicRemarkB").val(r.Remark);
					for (var i = 0; i < document.getElementsByName("txt_EpidemicProductTypeB").length; i++) 
	                {						
	                	for(var k = 0;k<r.epsList.length;k++)
	                	{
	                		if((document.getElementsByName("txt_EpidemicProductTypeB")[i].value)==r.epsList[k].Id)
	                			document.getElementsByName("txt_EpidemicProductTypeB")[i].checked=true;
	                	}
	            	}
                }
                else if (r == "sessionOut") {
                    doLogout();
                }
    			else{
    				alert("编辑失败！");
    			}
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } 
    //新增
    else {
    	InitEPType();
    	$("#hid_EpidemicParamB").val("methodName=add");
		$("#txt_EpidemicNameB").val("");
		$("#txt_EpidemicContentB").val("");
		$("#txt_EpidemicRemarkB").val("");
    } 
}

//获取商品类型，编辑
function InitEPType() {
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/EpidemicServlet?methodName=getProductStyle",
		dataType : "json",
		async : false,
		success : function(r) {
			if (r != "false") {
				var str = "";
				for (var i = 0; i < r.ProductStyle.length; i++)
				{
				    str += "<span style='width:500px;word-break:break-all;'><input type='checkbox' name='txt_EpidemicProductTypeB'" +
							" value='" + r.ProductStyle[i].Id + "'>" + r.ProductStyle[i].Name + "&nbsp;&nbsp;&nbsp;</span>";
				}
				$("#EPTypeList").html(str);
			}
			else if (r == "sessionOut") {
                doLogout();
            }
			else{
				alert("加载失败！");
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}


//保存
function SaveEpidemic() {
	var Title = $("#txt_EpidemicNameB").val();
	var Content = $("#txt_EpidemicContentB").val();
    var Remark = $("#txt_EpidemicRemarkB").val();
  //记录勾选的商品类别
	var ProductType="";
	var calType=document.getElementsByName("txt_EpidemicProductTypeB");
	for(i=0;i<calType.length;i++)
 		{
		if(calType[i].checked)
			//多个商品类型之间用"_"分隔
			ProductType+=calType[i].value+"_";
 		}
	//判断商品类别字符串是否为空
	if(ProductType!="")
	{
		//删除字符串最后一位"_"
		ProductType = ProductType.substring(0,ProductType.length -1);
	}
	else{
		alertText('缺少商品类型！',5000);
	}
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/EpidemicServlet?methodName="
            					+ getParam("hid_EpidemicParamB", "methodName") + "&id=" 
            					+ getParam("hid_EpidemicParamB", "id") + "&title=" + encode(Title) 
                                + "&content=" + encode(Content) + "&productStyle=" + encode(ProductType)
                				+ "&remark=" + encode(Remark),
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "false") {
            	alertText("保存失败！", 3500);
            } 
            else if(r == "sessionOut")	{
            	doLogout();
            }
            else {
            	alertText("保存成功！", 3500);
                var data = $.parseJSON(r);
                UpdateEpidemic(data);//更新疫情列表
                $('#EpidemicDom_Edit').animate({ width: "hide" });
                $("#EpidemicDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}


//更新疫情库列表页面
function UpdateEpidemic(data){
    var str = "";
    //新增保存时新增一行全新的数据
    if(getParam("hid_EpidemicParamB", "methodName") == "add"){
        str += GetEpidemicEntry(data, '*');
        $("#EpidemicList").find('.noDataSrc').remove();
        $("#EpidemicList").prepend(str);
    }
    //编辑保存时更新所在行的数据
    else if(getParam("hid_EpidemicParamB", "methodName") == "edit"){
        var obj =  $("#EpidemicList").find('.editObject').find('td');
        obj.eq(1).html(data.Title);
        obj.eq(2).html(data.Content);
        obj.eq(3).html(data.Remark);
        obj.eq(4).html(data.CreateTime.substring(0, 19));
        $("#EpidemicList").find('.editObject').removeClass('editObject');
    }
}

//删除疫情条目
function DelEpidemic(id) {
		$.ajax({
			type : "post",
			url : projectLocation
					+ "servlet/EpidemicServlet?methodName=del&id=" + id,
			dataType : "text",
			async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetEpidemicList(1);
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