function WFirstPage() {
    GetWebsiteLList(1);
}

function WPreviousPage() {
    var pageIndex = (parseInt($("#websitePaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#websitePaging .pageIndex").text()) - 1);
    GetWebsiteLList(pageIndex);
}

function WNextPage() {
    var pageIndex = ($("#websitePaging .pageIndex").text() == $(
			"#websitePaging .pageCount").text() ? parseInt($(
			"#websitePaging .pageIndex").text()) : parseInt($(
			"#websitePaging .pageIndex").text()) + 1);
    GetWebsiteLList(pageIndex);
}

function WEndPage() {
    GetWebsiteLList(parseInt($("#websitePaging .pageCount").text()));
}
/*获取网站表项*/
function GetWebsiteEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.Name + "</td>";
    str += "<td>" + data.Location + "</td>";
    str += "<td>" + data.WebStyle + "</td>";
    str += "<td>" + data.Address + "</td>";
    str += "<td>" + data.Remark + "</td>";
    str += "<td>"
			+ data.CreateTime.substring(0, 19)
			+ "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编 辑' onclick='showWDialog(\"edit\",\""
			+ data.Id
			+ "\", this)' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='查看' onclick='showWDialog(\"view\",\""
			+ data.Id + "\")' />&nbsp;<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""
			+ data.Id + "\",DelWebsite)' /></td>";
    str += "</tr>";
    return str;
}
/*
 函数名：GetWebsiteLList
 作用：获取网站列表
 */
function GetWebsiteLList(strPageIndex) {
    var strQuery = $("#txt_WebsiteQuery").val();
    var strStraTime = $("#txt_WebsiteBeginDate").val();
    var strEndTime = $("#txt_WebsiteEndDate").val();

    $.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/WebsiteServlet?methodName=QueryList&strQuery=" + encode(strQuery)
				+ "&strStraTime=" + strStraTime + "&strEndTime=" + strEndTime + "&strPageIndex="
				+ strPageIndex + "&strPageCount=10",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (r) {
            $("#websiteList").html('');
            if (r == "false") {
                $("#websiteList")
						.append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
                return false;
            }
            else if(r =="sessionOut"){
            	doLogout();
            	
            }
            var data = $.parseJSON(r);

			$("#websitePaging .dataCount").text(data.total);
			$("#websitePaging .pageCount").text(parseInt((parseInt(data.total) + 9) / 10));
			$("#websitePaging .pageIndex").text(strPageIndex);
            var str = "";
            var j = 0;
            for (var i = 0; i < data.websiteList.length; i++) {
            	 str += GetWebsiteEntry(data.websiteList[i], i+1);
            }
            $("#websiteList").append(str);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
 函数名：InitWebsiteStyle；
 作用：获取网站类型
 */
function InitWebsiteStyle(){
	
	$.ajax({
		type : "post",
		url : projectLocation + "servlet/WebsiteServlet?methodName=getWebsiteStyle",
		dataType : "text",
		async : false,
		success : function(r) {
			 $("#op_website_style").html('');
			if (r != "false") {
				var data = $.parseJSON(r);
				var str = "";
	            for (var i = 0; i < data.WebsiteStyle.length; i++) {
					str += "<option value='" + data.WebsiteStyle[i].Id + "'>" + data.WebsiteStyle[i].Name + "</option>";
				}
				$("#op_website_style").append(str);
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
/*
函数名：DelWebsite
作用：删除网站
参数：id（被删除的网站id）
*/
function DelWebsite(id) {
 
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/WebsiteServlet?methodName=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetWebsiteLList(1);
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
函数名：showWDialog
作用：控制对于特定网站的查看，编辑和添加
参数：id（被操作的王炸id）
methodName：操作名称参数
pos：操作位置
*/

function showWDialog(methodName, Id, pos) {
	if(methodName == "view")
		{
		 $("#iframeMain .wrap").animate({ width: "hide" });
		    $('#websiteDetailsDom').show();
		    $(obj).parent().parent().children("li").children("a").removeClass("current");
		    $(obj).addClass("current");
		   
		    $.ajax({
	            type: "post",
	            url: projectLocation + "servlet/WebsiteServlet?methodName=view&id=" + Id,
	            dataType: "json",
	            async: false,
	            success: function (r) {
	                if (r != "false") {
	                    
	        $("#txt_DwebsiteId").val(r.Id);
		    $("#txt_DwebsiteName").val(r.Name);
		    $("#txt_DLocation").val(r.Location);
		    $("#txt_DwebsiteType").val(r.WebStyle);
		    $("#txt_DAddress").val(r.Address);
		    $("#txt_DwebsiteRemark").val(r.Remark);
		    $("#txt_DwebsiteCountry").val(r.CountryName);
		    $("#txt_DwebsiteArea").val(r.AreaName);
		    $("#txt_DwebsiteIntegrity").val(r.Integrity);
		    $("#txt_DwebsiteScore").val(parseInt(r.Score));
		    
		    
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
	else
		{
            $("#iframeMain .wrap").css("display", "none");
    $('#websiteListDom_Edit').show();
    $(obj).parent().parent().children("li").children("a").removeClass("current");
    $(obj).addClass("current");
    InitWebsiteStyle();

    if (methodName != "add") {
        $.ajax({
            type: "post",
            url: projectLocation + "servlet/WebsiteServlet?methodName=init&id=" + Id,
            dataType: "json",
            async: false,
            success: function (r) {
                if (r != "false") {
                	$(pos).parent().parent().addClass('editObject');
                    $("#hid_websiteListParam").val("methodName=edit&id=" + Id);
                    $("#txt_websiteName").val(r.Name);
                    $("#txt_websiteLocation").val(r.Location);
                    $("#op_website_style").val(r.WebStyle);
                    $("#txt_Address").val(r.Address);
                    $("#txt_websiteRemark").val(r.Remark);
                    $("#txt_websiteIntegrity").val(r.Integrity);
        		    $("#txt_websiteScore").val(parseInt(r.Score));
                    if(r.CountryId!="")
                    { 
                    	$('#txt_websitecountry').show();
                    	$("#txt_websitecountry").val(r.CountryId);
                    	$('#txt_websitecountry').text(r.CountryName+" ");
                    }
                   
                    if(r.AreaId!="")
                    {
                    	$('#txt_websitearea').show();
                        $("#txt_websitearea").val(r.AreaId);
                        $('#txt_websitearea').text(r.AreaName);
                    }
             
                  
                }
                else if(r =="sessionOut"){
                	doLogout();
                	
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    } else {//新增时清空编辑框
        $("#hid_websiteListParam").val("methodName=add");
        $("#txt_websiteName").val("");
        $("#txt_websiteLocation").val("");
        $("#op_website_style").val("");
        $("#txt_websiteAddress").val("");
        $("#txt_websiteRemark").val("");
        $("#txt_websitecountry").val("");
    	$('#txt_websitecountry').text("");
    	$("#txt_websitearea").val("");
        $('#txt_websitearea').text("");
        $("#txt_websiteIntegrity").val("");
	    $("#txt_websiteScore").val("");
    	
    }
		}
   
}

/*
函数名：SaveWebsite
作用：保存网站

*/
function SaveWebsite() {
    var strName = $("#txt_websiteName").val();
    var strLocation = $("#txt_websiteLocation").val();
    var strType = $("#op_website_style").val();
    var strAddress = $("#txt_Address").val();
    var strRemark = $("#txt_websiteRemark").val();
    var strAreaId = $("#txt_websitearea").val();
	var strCountryId = $("#txt_websitecountry").val();
	var strIntegrity = $("#txt_websiteIntegrity").val();
	var strScore = $("#txt_websiteScore").val();
    if ($.trim(strName) == "") {
        alertText("网站名不能为空！", 3500);
        return false;
    }
    var r = /^[0-9]*[1-9][0-9]*$/;　　//正整数
    if (!r.test($.trim(strIntegrity))) {
        alertText("网站诚信度为1-10整数", 3500);
        return false;
    }
    else {
        if(parseInt(strIntegrity) > 10){
            alertText("网站诚信度为1-10整数", 3500);
            return false;
        }
    }
    var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
    if (!reg.test($.trim(strLocation))) {
        alertText("请输入正确的网址", 3500);
        return false;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/WebsiteServlet?methodName="
            					+ getParam("hid_websiteListParam", "methodName") + "&id=" + getParam("hid_websiteListParam", "id")
                                + "&name=" + encode(strName) + "&location=" + encode(strLocation) +"&webStyle=" + strType + "&address=" + encode(strAddress) + "&remark=" + encode(strRemark) 
                                + "&areaId=" + strAreaId + "&countryId=" +strCountryId +  "&integrity=" +strIntegrity + "&score=" +strScore,
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
                 UpdateWebsite(data);
                 $('#websiteListDom_Edit').animate({ width: "hide" });
                 $("#websiteListDom").show();
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}
/*
函数名：UpdateWebsite
作用：保存后更新网站列表
参数：data：被更改的网站信息
*/
function UpdateWebsite(data){
    var str = "";
    if(getParam("hid_websiteListParam", "methodName") == "add"){
        str += GetWebsiteEntry(data, '*');
        $("#websiteList").find('.noDataSrc').remove();
        $("#websiteList").prepend(str);
        var dataTotal = parseInt($("#websitePaging .dataCount").text())+1;
        $("#websitePaging .dataCount").text(dataTotal)
        $("#websitePaging .pageCount").text(parseInt((dataTotal + 9) / 10));
        $("#websitePaging .pageIndex").text(parseInt((dataTotal + 9) / 10));
    }else if(getParam("hid_websiteListParam", "methodName") == "edit"){
        var obj =  $("#websiteList").find('.editObject').find('td');
        obj.eq(0).html('*');
        obj.eq(1).html(data.Name);
        obj.eq(2).html(data.Location);
        obj.eq(3).html(data.WebStyle);
        obj.eq(4).html(data.Address);
        obj.eq(5).html(data.Remark);
        obj.eq(6).html(data.CreateTime);
        $("#websiteList").find('.editObject').removeClass('editObject');
    }
}
/*函数作用：获取网站所属的地区
 * 复用Brand模块相关代码*/
function GetWebsiteArea()
{
	$("#hid_PreviousParam").val("websiteListDom_Edit");
	$("#hid_TypeParam").val("selectedType=website");
	$("#websiteListDom_Edit").animate({ width: "hide" });
    $('#BrandRegionDom_Edit').show();
    $("#txt_BrandCountryQuery").val("");
    $("#BrandRegionList").html('');
	
}

/**
 * 网站核实
 * add by tanshumei
 * @returns
 */
function WebsiteCheck(){
	$("#websiteListDom").hide();
	$("#websiteManage_mac").show();
	GetMachineWebsiteList(1,10);//调用机器学-网站管理list
}

/**
 * 网站管理-网站采集配置
 * add by tanshumei
 * @returns
 */
function WebsiteCollect(){
	WebsiteCollectList(1,10);
	$("#websiteListDom").hide();
	$("#WebAcquisitionDom_List").show();
}

/**
 * 网站采集配置-第一页
 * @returns
 */
function FirstPage_webAcquisition(){
	WebsiteCollectList(1,10);
}

function PreviousPage_webAcquisition(){
	var pageIndex = (parseInt($("#webAcquisitionPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#webAcquisitionPaging .pageIndex").text()) - 1);
	WebsiteCollectList(pageIndex,10);
}

function NextPage_webAcquisition(){
	var pageIndex = ($("#webAcquisitionPaging .pageIndex").text() == $("#webAcquisitionPaging .pageCount").text() ? parseInt($("#webAcquisitionPaging .pageIndex").text()) : parseInt($("#webAcquisitionPaging .pageIndex").text()) + 1);
	WebsiteCollectList(pageIndex,10);
}

function EndPage_webAcquisition(){
	WebsiteCollectList(parseInt($("#webAcquisitionPaging .pageCount").text()),10);
}

/**
 * 网站采集配置list
 * @param pageIndex
 * @param pageSize
 * @returns
 */
function WebsiteCollectList(pageIndex,pageSize){
	$.ajax({
		type: "post",
		url: projectLocation
			+"servlet/CollectorCfg?method=listWebsite&pageIndex="+pageIndex+"&pageSize="+pageSize,
		contentType: "application/json;charset=utf-8",
        async: false,
        success: function(r){
        	$("#webAcquisitionList").html('');
        	if(r=="false"){
        		$("#webAcquisitionList")
        			.append("<tr class='noDataSrc'><td colspan='8' style='text-align:center;'>无数据！</td></tr>");
        		return false;
        	}else if(r =="sessionOut"){
        		doLogout();
        	}else{
        		
        		var data = $.parseJSON(r);
        		
	        	$("#webAcquisitionPaging .dataCount").text(data.count);
	        	$("#webAcquisitionPaging .pageCount").text(parseInt((parseInt(data.count)+9)/10));
	        	$("#webAcquisitionPaging .pageIndex").text(pageIndex);
        	
	        	var str ="";
	        	for(var i=0; i<data.infoList.length; i++){
	        		str += "<tr>";
	        		str += "<td>"+(i+1)+"</td>";
	        		str += "<td>"+data.infoList[i].name+"</td>";
	        		str += "<td><a url='"+data.infoList[i].url+"' target='_blank'>"+data.infoList[i].url+"</a></td>";
	        		str += "<td>"+data.infoList[i].siteName+"</td>";
	        		str += "<td>"+data.infoList[i].titleSign+"</td>";
	        		str += "<td>"+data.infoList[i].createTimeSign+"</td>";
	        		str += "<td>";
		        	    if(data.infoList[i].rate == 0){
		        			str += "空";
		        		}else if(data.infoList[i].rate == 1){
		        	    	str += "一天";
		        	    }else if(data.infoList[i].rate == 7){
		        	    	str += "一周";
		        	    }else if(data.infoList[i].rate == 30){
		        	    	str += "一个月";
		        	    }else {
		        	    	str += "测试数据";
		        	    }
	                str +="</td>";   //采集频率
	        		str += "<td>"+data.infoList[i].createTime.toString(0,19)+"</td>";
	        		str += "<td><input class='btn btn-default btn-xs' type='button' value='编辑' onclick='EditWebAcquisition("+data.infoList[i].id+")' />&nbsp;&nbsp;"
	          		  		  +"<input class='btn btn-default btn-xs' type='button' value='执行' onclick='ExecWebAcquisition("+data.infoList[i].id+")' />" 
	          		  		  +"<input class='btn btn-default btn-xs' type='button' value='删除' onclick='DelWebAcquisition("+data.infoList[i].id+")'/></td>";
	        		str +="</tr>";
	        	}
	        	$("#webAcquisitionList").html(str);
          }
        },
        error: function (e) {
            alert(e.responseText);
        }
	});
}

/**
 * 网站采集-添加
 * @returns
 */
function AddWebAcquisition(){
	$("#iframeMain .wrap").animate({ width: "hide" });
	$("#WebAcquisitionDom_Edit").show();
	
	$("#hid_WebAcquisitionId").val('');
	$("#txt_WebAcquisitionName").val('');
	$("#txt_WebAcquisitionUrl").val('');
	$("#txt_WebAcquisitionType").val('');
	$("#txt_WebAcquisitionTitle").val('');
	$("#txt_WebAcquisitionNext").val('');
	$("#txt_WebAcquisitionDate").val('');
	$("#txt_WebAcquisitionPageTotal").val('');
	$("#op_WebAcquisitionFrequency").html('<option value="0">请选择</option><option value="1">一天</option><option value="7">一周</option><option value="30">一个月</option>'); 
	
}

/**
 * 网站采集-编辑
 * @returns
 */
function EditWebAcquisition(id){
	$("#iframeMain .wrap").animate({ width: "hide" });
	$("#WebAcquisitionDom_Edit").show();
	
	$("#hid_WebAcquisitionId").val(id);
	$("#txt_WebAcquisitionName").val('');
	$("#txt_WebAcquisitionUrl").val('');
	$("#txt_WebAcquisitionType").val('');
	$("#txt_WebAcquisitionTitle").val('');
	$("#txt_WebAcquisitionNext").val('');
	$("#txt_WebAcquisitionDate").val('');
	$("#txt_WebAcquisitionPageTotal").val('');
	$("#op_WebAcquisitionFrequency").html('<option value="0">请选择</option><option value="1">一天</option><option value="7">一周</option><option value="30">一个月</option>');
	
	$.ajax({
		type: "post",
		url: projectLocation
			+"servlet/CollectorCfg?method=queryById&id="+id,
		contentType: "application/json;charset=utf-8",
		async:false,
		success:function(r){
			if(r != "false"){
				var data = $.parseJSON(r);
				$("#hid_WebAcquisitionId").val(data.id);
				$("#txt_WebAcquisitionName").val(data.name);
				$("#txt_WebAcquisitionUrl").val(data.url);
				$("#txt_WebAcquisitionType").val(data.siteName);
				$("#txt_WebAcquisitionTitle").val(data.titleSign);
				$("#txt_WebAcquisitionNext").val(data.nextSign);
				$("#txt_WebAcquisitionDate").val(data.createTimeSign);
				$("#txt_WebAcquisitionPageTotal").val(data.pageTotal);
				if(data.rate>0){
					$("#op_WebAcquisitionFrequency").val(data.rate);
				}
			}else if(r == "sessionOut"){
				doLogout();
			}else{
				 alertText("编辑失败！",3500);
			}
		},
		error: function(e){
			alert(e.responseText);
		}
	});
}

/**
 * 网站采集编辑/添加-保存
 * @returns
 */
function SaveWebAcquisition(){
	var strAcquisitionName = $("#txt_WebAcquisitionName").val();
	var strAcquisitionUrl = $("#txt_WebAcquisitionUrl").val();
	var strAcquisitionType = $("#txt_WebAcquisitionType").val();
	var strAcquisitionTitle = $("#txt_WebAcquisitionTitle").val();
	var strAcquisitionNext = $("#txt_WebAcquisitionNext").val();
	var strAcquisitionDate = $("#txt_WebAcquisitionDate").val();
	var strAcquisitionPageTotal = $("#txt_WebAcquisitionPageTotal").val();
	var strAcquisitionFrequency = $("#op_WebAcquisitionFrequency").val();
	
	if ($.trim(strAcquisitionName) == "") {
        alertText("采集器名不能为空！", 3500);
        return false;
    }
	
	$.ajax({
		type: "post",
		url: projectLocation
			+ "servlet/CollectorCfg?method=editOrAddWebsite&id=" + $("#hid_WebAcquisitionId").val()
			+ "&siteName=" + encode(strAcquisitionName) + "&url=" + encode(strAcquisitionUrl) + "&nextSign=" + encode(strAcquisitionNext)
			+ "&createTimeSign=" + encode(strAcquisitionDate) + "&pageTotal="+encode(strAcquisitionPageTotal)+"&titleSign="+strAcquisitionTitle
			+ "&name="+strAcquisitionName+"&rate="+strAcquisitionFrequency,
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if(r !=null && r != "false" && r != "{}" && r != "异常" ){
				alertText("保存成功！", 3500);
				$("#WebAcquisitionDom_Edit").hide();
				$("#WebAcquisitionDom_List").show();
				WebsiteCollectList(1,10);
			}
		},
		error: function (e) {
			alertText("保存失败！", 3500);
			return false;
			//alert(e.responseText);
		}
	});
	
	
	
}

/**
 * 采集配置-执行
 * @param id
 * @returns
 */
function ExecWebAcquisition(id){
	$.ajax({
        type: "post",
        url: projectLocation
				+ "servlet/CollectorCfg?method=excuteWebsite&id=" + id,
        dataType: "text",
        async: false,
        success: function (r) {
            if (r == "success") {
                alertText("执行成功！后台正在爬去数据，请等待……", 4000);
            }else if(r =="sessionOut"){
            	doLogout();
            }else if(r == "false" || r == "error"){
            	alertText("执行失败！请查看是否参数配置错误！", 3500);
     	   }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}


/**
 * 网站采集-删除
 * @returns
 */
function DelWebAcquisition(id){
	$.ajax({
		type: "post",
		url: projectLocation
			+"servlet/CollectorCfg?method=del&id="+id,
		dataType:"text",
		async:false,
		success:function(r){
			if(r == "true"){
				 alertText("删除成功！", 3500);
				 WebsiteCollectList(1,10);
			}else if(r == "sessionOut"){
				doLogout();
			}else if(r == "false"){
				 alertText("删除失败！", 3500);
			}
		},
		error: function (e) {
            alert(e.responseText);
        }
	});
}

