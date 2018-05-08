/*机器学习-信息管理-第三方报告采集*/

function FirstPage_reporting(){
    GetReportingList(1,10);
}

function PreviousPage_reporting() {
    var pageIndex = (parseInt($("#reportingPaging .pageIndex").text()) == 1 ? 1
			: parseInt($("#reportingPaging .pageIndex").text()) - 1);
    GetReportingList(pageIndex,10);
}

function NextPage_reporting() {
	var pageIndex = ($("#reportingPaging .pageIndex").text() == $("#reportingPaging .pageCount").text() ? parseInt($("#reportingPaging .pageIndex").text()) : parseInt($("#reportingPaging .pageIndex").text()) + 1);
    GetReportingList(pageIndex,10);
}

function EndPage_reporting() {
	GetReportingList(parseInt($("#reportingPaging .pageCount").text()),10);
}

/**
 * 第三方报告采集的list
 * @param strPageIndex
 * @param pageSize
 */
function GetReportingList(strPageIndex,pageSize) {
   $.ajax({
       type: "post",
       url: projectLocation
		+ "servlet/ThirdReport?method=listAbroad&pageIndex="+ strPageIndex+"&pageSize="+pageSize,
       contentType: "application/json;charset=utf-8",
       async: false,
       success: function (r) {
           $("#infoList").html('');
           if (r == "false"){
               $("#infoList")
						.append("<tr class='noDataSrc'><td colspan='6' style='text-align:center;'>无数据！</td></tr>");
               return false;
           }else if(r =="sessionOut"){
           		doLogout();
           }
           var data = $.parseJSON(r);
			$("#reportingPaging .dataCount").text(data.count);
			$("#reportingPaging .pageCount").text(parseInt((parseInt(data.count) + 9) / 10));
			$("#reportingPaging .pageIndex").text(strPageIndex);
			
           var str = "";
           var j = 0;
           for (var i = 0; i < data.infoList.length; i++) {
           	 str += GetReportingEntry(data.infoList[i], i+1);
           }
           $("#infoList").append(str);
       },
       error: function (e) {
           alert(e.responseText);
       }
   });
}

/**
 * 获取第三方报告采集数据
 * @param data
 * @param idx
 * @returns {String}
 */
function GetReportingEntry(data, idx){
    var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td><a target='_blank' href='"+data.url+"'>" + data.title + "</a></td>";
    str	+= "<td><a  href='javascript:void(0)' onclick='translation("+data.id+")' >"+data.titleTrans+"</a></td>";
    str += "<td>" + data.country+ "</td>";
    str += "<td>" + data.brand+ "</td>";
    str += "<td>" + data.creatTime.substring(0, 19)+ "</td>";
    str += "</tr>";
    return str;
}

//显示翻译文章
function translation(id){
	$.ajax({
		type : "post",
		url : projectLocation
			+ "servlet/ThirdReport?method=queryById&id="+id,
		dataType : "json",
		async : false,
		success : function(r) {
			if (r != "false") {
				$("#translation_title").text(r.titleTrans);
				$("#translation_content").text(r.contentTrans);
				
				$("#thirdPartyReport_mac").hide();
				$("#translation_View").show();
				
			}else if(r =="sessionOut"){
            	doLogout();
            	
            }
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}


/*第三方报告采集爬虫配置*/
function FirstPage_acquisition() {
	$("#thirdPartyReport_mac").hide();
	$("#acquisitionDom_List").show();
	GetAcquisitionList(1,10);
}

function PreviousPage_acquisition() {
	var pageIndex = (parseInt($("#acquisitionPaging .pageIndex").text()) == 1 ? 1 : parseInt($("#acquisitionPaging .pageIndex").text()) - 1);
	GetAcquisitionList(pageIndex,10);
}

function NextPage_acquisition() {
	var pageIndex = ($("#acquisitionPaging .pageIndex").text() == $("#acquisitionPaging .pageCount").text() ? parseInt($("#acquisitionPaging .pageIndex").text()) : parseInt($("#acquisitionPaging .pageIndex").text()) + 1);
	GetAcquisitionList(pageIndex,10);
}

function EndPage_acquisition() {
	GetAcquisitionList(parseInt($("#acquisitionPaging .pageCount").text()),10);
}


/**
 * 获取报告采集配置list数据并分页
 * @param strPageIndex
 * @param pageSize
 */
function GetAcquisitionList(strPageIndex,pageSize) {
	$.ajax({
			type : "post",
			url : projectLocation
				+ "servlet/CollectorCfg?method=listAbroad&pageIndex="
				+ strPageIndex + "&pageSize=10",
			dataType : "text",
			async : false,
			success : function(r) {
				$("#acquisitionList").html('');
				if (r == "false") {
					$("#acquisitionList").append("<tr class='noDataSrc'><td colspan='10' style='text-align:center;'>无数据！</td></tr>");
					return false;
				}
				else if(r =="sessionOut"){
	            	doLogout();
	            	
	            }
				var data = $.parseJSON(r);

				$("#acquisitionPaging .dataCount").text(data.count);
				$("#acquisitionPaging .pageCount").text(parseInt((parseInt(data.count) + 9) / 10));
				$("#acquisitionPaging .pageIndex").text(strPageIndex);
				var str = "";
				var j = 0;
				 for (var i = 0; i < data.infoList.length; i++) {
					 str += GetAcquisitionEntry(data.infoList[i], i+1);
					
				}
				$("#acquisitionList").append(str);
			},
			error : function(e) {
				alert(e.responseText);
			}
		});
}

/**
 * 循环输出报告采集配置数据
 * @param data
 * @param idx
 * @returns {String}
 */
function GetAcquisitionEntry(data, idx){
	var str = "<tr>";
    str += "<td>" + idx + "</td>";
    str += "<td>" + data.siteName + "</td>";
    str += "<td>" + data.url + "</td>";
    str += "<td>" + data.titleSign + "</td>";
    str += "<td>" + data.nextSign + "</td>";
    str += "<td>" + data.pageTotal + "</td>";
    str += "<td>";
	    if(data.rate == 0){
			str += "空";
		}else if(data.rate == 1){
	    	str += "一天";
	    }else if(data.rate == 7){
	    	str += "一周";
	    }else if(data.rate == 30){
	    	str += "一个月";
	    }else {
	    	str += "测试数据";
	    }
    str +="</td>";   //采集频率
    str += "<td>" + data.name + "</td>";  //采集类型
    str += "<td>" + data.createTime+ "</td>";
    str += "<td><input class='btn btn-default btn-xs' type='button' value='编辑' onclick='acquisitionEdit(" + data.id + ")' />&nbsp;" +
    		"<input class='btn btn-default btn-xs' type='button' value='执行' onclick='ExecAcquisition(" + data.id + ")' />&nbsp;" +
    		"<input class='btn btn-default btn-xs' type='button' value='删 除' onclick='confirmShow(\""+ data.id + "\",DelAcquisition)' /></td>";
    str += "</tr>";
    return str;
}

/**
 * 报告采集新增
 */
function acquisitionAdd(){
	$("#iframeMain .wrap").animate({ width: "hide" });
	$('#acquisitionDom_Edit').show();
	
    $('#hid_acquisitionId').val('');
	$("#txt_siteName").val("");
	$("#txt_title").val("");
	$("#txt_siteLocation").val("");
	$("#txt_pageTotal").val(""); 
	$("#txt_Date").val(""); 
	$("#txt_next").val(""); 
	$("#txt_typeName").val(""); 
	$("#op_frequency").html('<option value="0">请选择</option><option value="1">一天</option><option value="7">一周</option><option value="30">一个月</option>'); 
}

/**
 * 报告采集编辑
 * acquisitionEdit
 * @param id
 */
function acquisitionEdit(id){
    $('#hid_acquisitionId').val(id);
    $("#txt_siteName").val("");
    $("#txt_siteLocation").val("");
    $("#txt_pageTotal").val(""); 
    $("#txt_title").val("");
	$("#txt_Date").val(""); 
	$("#txt_next").val(""); 
	$("#txt_typeName").val(""); 
	$("#op_frequency").html('<option value="0">请选择</option><option value="1">一天</option><option value="7">一周</option><option value="30">一个月</option>'); 

	$("#acquisitionDom_List").hide();
    $("#acquisitionDom_Edit").show();
    
	$.ajax({
        type: "post",
        url: projectLocation + "servlet/CollectorCfg?method=queryById&id=" + id,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r != "false") {
                $("#hid_acquisitionId").val(id);
                $("#txt_siteName").val(r.siteName);
                $("#txt_siteLocation").val(r.url);
                $("#txt_pageTotal").val(r.pageTotal);
                $("#txt_title").val(r.titleSign);
                $("#txt_Date").val(r.createTimeSign);
                $("#txt_next").val(r.nextSign);
                $("#txt_typeName").val(r.name); 
            	$("#op_frequency").val(r.rate); 
                
            }
            else if(r == "sessionOut"){
                doLogout();
            }
            else  {
                alertText("编辑失败！",3500);
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
	
}


/**
 * 报告采集保存
 * @returns {Boolean}
 */
function SaveAcquisition() {
	var strName = $("#txt_siteName").val();
	var strTitle = $("#txt_title").val();
    var strLocation = $("#txt_siteLocation").val();
    var strNext = $("#txt_next").val();
    var strDate = $("#txt_Date").val();
    var strpageTotal = $("#txt_pageTotal").val();
	var strTypeName = $("#txt_typeName").val();
	var strFrequency= $("#op_frequency").val();
    
    if ($.trim(strName) == "") {
        alertText("网址名不能为空！", 3500);
        return false;
    } 
    
    var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
    if (!reg.test($.trim(strLocation))) {
        alertText("请输入正确的网址! 例如：https://www.baidu.com", 3500);
        return false;
    }
    
    $.ajax({
		type: "post",
		url: projectLocation
			+ "servlet/CollectorCfg?method=editOrAddAbroad&id=" + $("#hid_acquisitionId").val()
			+ "&siteName=" + encode(strName) + "&url=" + encode(strLocation) + "&nextSign=" + encode(strNext)
			+ "&createTimeSign=" + encode(strDate) + "&pageTotal="+encode(strpageTotal)+"&titleSign="+strTitle
			+ "&name="+strTypeName+"&rate="+strFrequency,
		contentType: "application/json;charset=utf-8",
		async: false,
		success: function (r) {
			if(r !=null && r != "false" && r != "{}" ){
				alertText("保存成功！", 3500);
				$("#acquisitionDom_Edit").hide();
				$("#acquisitionDom_List").show();
				GetAcquisitionList(1,10);
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
 */
function ExecAcquisition(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/CollectorCfg?method=excuteAbroad&id=" + id,
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
 * 删除采集配置
 * @param id
 */
function DelAcquisition(id) {
        $.ajax({
            type: "post",
            url: projectLocation
					+ "servlet/CollectorCfg?method=del&id=" + id,
            dataType: "text",
            async: false,
            success: function (r) {
                if (r == "true") {
                    alertText("删除成功！", 3500);
                    GetAcquisitionList(1,10);
                }else if(r =="sessionOut"){
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

