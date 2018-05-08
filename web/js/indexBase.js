var timeCount //计时器对象，用于定时刷新
var projectLocation = "./";
$(function () {
    setTotals();
    checkStatus();
    getFirstlevelType();
    getProductDefineData("","");
    $('#hid_moduleInfo').val("chargeCon");
    $('#hid_subModuleInfo').val("productDefine");
    $("#standardContents").hide();
    $("#similarStandard_page").hide();

    $(".standardWorldMap").hide();//风险展示地图
    $("#standardGroup").hide();//风险展示数据
	$("#realTimeMonitoring_page").hide();//风险展示记录列表
	
	//test status
	$("#typeGroup").hide();//隐藏商品数量分类显示
    $("#standard_page").hide();//隐藏匹配标准页面
    $("#unstandard_page").hide();//隐藏非标准页面
    $("#page-wrapper .worldMap").hide();//隐藏舆情地图
    $("#information_publish").hide();//信息发布展示
	
    $(".standardWorldMap").show();//风险展示地图
    $("#standardGroup").show();//风险展示数据
	$("#realTimeMonitoring_page").show();//风险展示记录列表
	standardMapInit()//加载舆情地图
	getStandardGrade()//加载记录数
	getRealTimeStandardsList(3,1);
	realTimeMonitoringCount();
	//test end
	
    $(".dropdown-toggle").click(function () {
        if ($(".dropdown-menu").is(':hidden')) {
            $(".dropdown-menu").show();
        }
        else {
            $(".dropdown-menu").hide();
        }
    });
    // 控制搜索条件显示隐藏
    $("#side-menu li").click(function () {
    	
   		stopRealTimeMonitoringCount();
        $(this).addClass("open");
        $(this).siblings().removeClass("open");
        var key = $(this).attr("rel");
        
        $("#standardContents").hide();//配料页面
        $("#similarStandard_page").hide();//类似页面

        if (key == "chargeCon") {
        	$("#productType").show();
            $("#dangerType").hide();
            $("#dangerType2").hide();
            $("#epidemicTime").hide();
            $("#epidemicKey").hide();
            $("#eventTime").hide();
            $("#eventKey").hide();
            $("#typeGroup").show();
            $("#importSuggest").hide();
            $('#eventKeyWord').hide();
            $("#evaluationRange").hide();
            $(".chioceDisplay").html('');
            $('#chioceShow').hide();
            $('#EventAffectSearch').show();
            $('#EventExactInfo').hide();
            $("#information_publish").hide();//信息发布展示
            
            $("#standard_page").hide();//匹配标准页面
            $("#unstandard_page").show();//非标准页面
            $("#page-wrapper .worldMap").show();//舆情地图
            
            $(".standardWorldMap").hide();//风险展示地图
            $("#standardGroup").hide();//风险展示数据
        	$("#realTimeMonitoring_page").hide();//风险展示记录列表
            setTotals();
            clearSelection();
            getProductDefineData("","");
        }
        else if (key == "checkCon") {
        	$("#productType").show();
            $("#typeGroup").hide();
            $("#dangerType").show();
            $("#importSuggest").show();
            $("#epidemicTime").hide();
            $("#epidemicKey").hide();
            $("#eventTime").hide();
            $("#eventKey").hide();
            $("#dangerType2").show();
            $('#eventKeyWord').show();
            $("#evaluationRange").show();
            $(".chioceDisplay").html('');
            $('#chioceShow').hide();
            $('#EventAffectSearch').show();
            $('#EventExactInfo').hide();
            $("#information_publish").hide();//信息发布展示
            
            $("#standard_page").hide();//匹配标准页面
            $("#unstandard_page").show();//非标准页面
            $("#page-wrapper .worldMap").show();//舆情地图
            
            $(".standardWorldMap").hide();//风险展示地图
            $("#standardGroup").hide();//风险展示数据
        	$("#realTimeMonitoring_page").hide();//风险展示记录列表
            clearSelection();
            mapclearing();
        }
        else if (key == "epidemicCon") {
        	$("#productType").hide();
            $("#dangerType").hide();
            $("#dangerType2").hide();
            $("#epidemicTime").show();
            $("#epidemicKey").show();
            $("#eventTime").hide();
            $("#eventKey").hide();
            $("#typeGroup").show();
            $("#importSuggest").show();
            ShowEpidemicType();
            EpidemicTimeContent();
            $(".chioceDisplay").html('');
            $('#chioceShow').hide();
            $('#eventKeyWord').hide();
            $("#evaluationRange").hide();
            $('#EventAffectSearch').show();
            $('#EventExactInfo').hide();
            $("#information_publish").hide();//信息发布展示
            
            $("#standard_page").hide();//匹配标准页面
            $("#unstandard_page").show();//非标准页面
            $("#page-wrapper .worldMap").show();//舆情地图
            
            $(".standardWorldMap").hide();//风险展示地图
            $("#standardGroup").hide();//风险展示数据
        	$("#realTimeMonitoring_page").hide();//风险展示记录列表
            mapclearing();
        }
        else if(key == "standardCon"){//标准匹配
        	$("#standard_page .productList tbody").html("<tr><td colspan='6' style='text-align: center;'>暂无数据</td></tr>");
        	$("#standard_page .pageCount").html(1);
        	$("#standard_page .dataCount").html(0);
        	$("#hid_similarStandard").val("");
        	$(".chioceDisplay").html('');
        	$(".chioceShow").hide();
        	$("#typeGroup").hide();//隐藏商品数量分类显示
            $("#standard_page").show();//显示匹配标准页面
            $("#unstandard_page").hide();//隐藏非标准页面
            $("#page-wrapper .worldMap").hide();//隐藏舆情地图
            $(".standardWorldMap").hide();//风险展示地图
            $("#information_publish").hide();//信息发布展示
            $("#standardGroup").hide();//风险展示数据
        	$("#realTimeMonitoring_page").hide();//风险展示记录列表
            initStandards()//加载(匹配)标准
            clearSelection();
            mapclearing();
        }
        else if(key == "realTimeMonitoringCon"){//实时监控
        	$("#typeGroup").hide();//隐藏商品数量分类显示
            $("#standard_page").hide();//隐藏匹配标准页面
            $("#unstandard_page").hide();//隐藏非标准页面
            $("#page-wrapper .worldMap").hide();//隐藏舆情地图
            $("#information_publish").hide();//信息发布展示
        	
            $(".standardWorldMap").show();//风险展示地图
            $("#standardGroup").show();//风险展示数据
        	$("#realTimeMonitoring_page").show();//风险展示记录列表
        	standardMapInit()//加载舆情地图
        	getStandardGrade()//加载记录数
        	getRealTimeStandardsList(3,1);
        	realTimeMonitoringCount();
        }
        else if(key == "informationCon") {//信息发布
            $("#typeGroup").hide();//隐藏商品数量分类显示
            $("#standard_page").hide();//隐藏匹配标准页面
            $("#unstandard_page").hide();//隐藏非标准页面
            $("#page-wrapper .worldMap").hide();//隐藏舆情地图
            $(".standardWorldMap").hide();//风险展示地图
            $("#standardGroup").hide();//风险展示数据
            $("#realTimeMonitoring_page").hide();//风险展示记录列表

            $("#information_publish").show();//信息发布展示
            var contentHeight = $(window).height() - 70;
            $("#information_publish").css("height", contentHeight);
            $("#information_detail").css("height", contentHeight);
            GetInfoPubList(1);
        }
        else {
            $("#information_publish").hide();//信息发布展示
        	$("#productType").show();
            $("#dangerType").hide();
            $("#dangerType2").hide();
            $("#epidemicTime").hide();
            $("#epidemicKey").hide();
            $("#eventTime").show();
            $("#eventKey").show();
            $("#typeGroup").show();
            $("#importSuggest").show();
            ShowEventType();
            EventTimeContent();
            $('#eventKeyWord').hide();
            $("#evaluationRange").hide();
            $(".chioceDisplay").html('');
            $('#chioceShow').hide();
            $('#EventAffectSearch').show();
            $('#EventExactInfo').hide();
            
            $("#standard_page").hide();//匹配标准页面
            $("#unstandard_page").show();//非标准页面
            $("#page-wrapper .worldMap").show();//舆情地图
            clearSelection();
            $("#hid_eventAffect").val("eventAffect");
            
            $(".standardWorldMap").hide();//风险展示地图
            $("#standardGroup").hide();//风险展示数据
        	$("#realTimeMonitoring_page").hide();//风险展示记录列表
            mapclearing();
        }
    });

    // to open second menu
    $(".subCollapse").click(function () {
        if ($(this).siblings("ul").is(":hidden")) {
            $(this).parent().addClass("open");
            $(this).siblings("ul").slideDown();
        }
        else {
            $(this).parent().removeClass("open");
            //$(this).children("ul").addClass("collapse");
            $(this).siblings("ul").slideUp();
        }
    });

    // 当时间选择的是自定义时，出现时间输入框
    $("input:radio[name='eventTime']").click(function () {
        var val = $('input:radio[name="eventTime"]:checked').val();
        if (val == "自定义") {
            $(".timeContent").css("display", "inline");
        }
        else {
            $(".timeContent").css("display", "none");
        }
    });
    // 当时间选择的是自定义时，出现时间输入框
    $("input:radio[name='epidemicTime']").click(function () {
        var val = $('input:radio[name="epidemicTime"]:checked').val();
        if (val == "自定义") {
            $(".timeContent").css("display", "inline");
        }
        else {
            $(".timeContent").css("display", "none");
        }
    });
    
    $('#timeStart').datepicker({});
    $('#timeEnd').datepicker({});
    $('#timeStartEvent').datepicker({});
    $('#timeEndEvent').datepicker({});

    $("#choiceSure").click(function () {
        // to get the chioces
        //获得三个选择框的文本内容和对应Id
        var firObj = document.getElementById('firstSel');
        var firChoice = firObj.options[firObj.selectedIndex].text;
        var secObj = document.getElementById('secondSel');
        var secChoice = secObj.options[secObj.selectedIndex].text;
        var thiObj = document.getElementById('thirdSel');
        var thiChoice = thiObj.options[thiObj.selectedIndex].text;
        var firChoiceId = $("#firstSel").val();
        var secChoiceId = $("#secondSel").val();
        var thiChoiceId = $("#thirdSel").val();


        if (firChoice == "请选择") {
            return;
        }
        // to show chioces
        $("#chioceShow").show();
        if (secChoice != "请选择" && thiChoice != "请选择") {
            var str = "<span class='choices' value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleChoice(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "&nbsp;|&nbsp;" + thiChoice + "<a>&times</a></span>";
        }
        else if (secChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleChoice(this)'>" + firChoice + "<a>&times</a></span>";
        }
        else if (thiChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleChoice(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "<a>&times</a></span>";
        }
        $("#chioceDisplay").append(str);
    });

    $("#standard_page .choiceSure").click(function () {
        // to get the chioces
        //获得三个选择框的文本内容和对应Id
        var firChoice = $('#standard_page .firstSel :selected').text();
        var secChoice = $('#standard_page .secondSel :selected').text();
        var thiChoice = $('#standard_page .thirdSel :selected').text();
        var firChoiceId = $("#standard_page .firstSel").val();
        var secChoiceId = $("#standard_page .secondSel").val();
        var thiChoiceId = $("#standard_page .thirdSel").val();

        if (firChoice == "请选择") {
            return;
        }
        // to show chioces
        $("#standard_page .chioceShow").show();
        if (secChoice != "请选择" && thiChoice != "请选择") {
            var str = "<span class='choices' value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleChoice(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "&nbsp;|&nbsp;" + thiChoice + "<a>&times</a></span>";
        }
        else if (secChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleChoice(this)'>" + firChoice + "<a>&times</a></span>";
        }
        else if (thiChoice == "请选择") {
            var str = "<span class='choices'  value='" + firChoiceId + "|" + secChoiceId + "|" + thiChoiceId + "<>' onclick='deleChoice(this)'>" + firChoice + "&nbsp;|&nbsp;" + secChoice + "<a>&times</a></span>";
        }
        $("#standard_page .chioceDisplay").append(str);
    });

    //add by lxt
    //当第一级菜单变化时，加载第二级菜单
    $("#firstSel").change(
		function () {
		    $("#secondSel").html('');
		    var str = "";
		    str += "<option value='0'>" + "请选择" + "</option>";
		    var firVal = $(this).val();

		    if (firVal != "0") {
		        $.ajax({
		            type: "post",
		            url: "./servlet/ProductTypeServlet?methodName=sel&id=" + firVal,
		            dataType: "text",
		            async: false,
		            success: function (r) {

		                if (r != "false") {
		                    var data = $.parseJSON(r);
		                    for (var i = 0; i < data.webList.length; i++) {
		                        str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
		                    }

		                }
		                else if (r == "sessionOut") {
		                    doLogout();

		                }
		            },
		            error: function (e) {
		                alert(e.responseText);
		            }
		        });
		    }
		    $("#secondSel").append(str);
		    $("#thirdSel").html("<option value='0'>请选择</option>");
		});


    //add by lxt
    //当第二级菜单发生变化时，加载第三级菜单
    $("#secondSel").change(
		function () {
		    $("#thirdSel").html('');
		    var str = "";
		    str += "<option value='0'>请选择</option>";
		    var secVal = $(this).val();
		    if (secVal != "0") {

		        $.ajax({
		            type: "post",
		            url: "./servlet/ProductTypeServlet?methodName=sel&id=" + secVal,
		            dataType: "text",
		            async: false,
		            success: function (r) {

		                if (r != "false") {
		                    var data = $.parseJSON(r);

		                    for (var i = 0; i < data.webList.length; i++) {
		                        str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
		                    }

		                }
		                else if (r == "sessionOut") {
		                    doLogout();

		                }
		            },
		            error: function (e) {
		                alert(e.responseText);
		            }
		        });

		    }
		    $("#thirdSel").append(str);
		});
    
    //standard第一级菜单变化时，加载第二级菜单 
    $("#standard_page .firstSel").change(
    	function () {
    		$("#standard_page .secondSel").html('');
    		var str = "";
    		str += "<option value='0'>" + "请选择" + "</option>";
    		var firVal = $(this).val();
    		
    		if (firVal != "0") {
    			$.ajax({
    				type: "post",
    				url: "./servlet/ProductTypeServlet?methodName=sel&id=" + firVal,
    				dataType: "text",
    				async: false,
    				success: function (r) {	
    					if (r != "false") {
    						var data = $.parseJSON(r);
    						for (var i = 0; i < data.webList.length; i++) {
    							str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
    						}	
    					}
    					else if (r == "sessionOut") {
    						doLogout();
    					}
    				},
    				error: function (e) {
    					alert(e.responseText);
    				}
    			});
    		}
    		$("#standard_page .secondSel").append(str);
    		$("#standard_page .thirdSel").html("<option value='0'>请选择</option>");
    	});
    
    $("#standard_page .secondSel").change(
    	function () {
    	    $("#standard_page .thirdSel").html('');
    	    var str = "";
   		    str += "<option value='0'>请选择</option>";
   		    var secVal = $(this).val();
   		    if (secVal != "0") {

  		        $.ajax({
   		            type: "post",
   		            url: "./servlet/ProductTypeServlet?methodName=sel&id=" + secVal,
   		            dataType: "text",
   		            async: false,
   		            success: function (r) {
   		            	if (r != "false") {
   		            		var data = $.parseJSON(r);

   		            		for (var i = 0; i < data.webList.length; i++) {
   		            			str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
   		            		}
   		            	}
   		            	else if (r == "sessionOut") {
   		            		doLogout();
   		            	}
   		            },
   		            error: function (e) {
   		            	alert(e.responseText);
   		            }
  		        });
   		    }
   		    $("#standard_page .thirdSel").append(str);
    	});
    
    $("#ddl_standardName").change(function () {
        //获得选择框的文本内容和对应Id
        var choiceId = $("#ddl_standardName").val();
        var choiceName = $("#ddl_standardName").find("option:selected").text();

        var str = "";
        if (choiceId == '-1'){
        	return false;
        }else if (choiceId == '0') {
        	$("#ddl_standardName .standardName").each(function(){
        		str += "<span class='choices' value='" + this.value + "' onclick='deleChoice(this)'>" + this.text + "<a>&times</a></span>";
        	});
        	
        	$("#standard_page .standardChioceDisplay").html(str);
        }else{
        	var mark = true;
        	$("#standard_page .standardChioceDisplay span").each(function(){
        		if($(this).attr('value') == choiceId){
        			mark = false;
        			return false;
        		}
        	});
        	if(mark){
        		str = "<span class='choices' value='" + choiceId + "' onclick='deleChoice(this)'>" + choiceName + "<a>&times</a></span>";
        		$("#standard_page .standardChioceDisplay").append(str);
        	}
        }
    });
});
function getFirstlevelType(){
	$("#firstSel").html('');
	$("#standard_page .firstSel")
	$("#propertySecondSel").html("<option value='0'>请选择</option>");
	$("#propertyThirdSel").html("<option value='0'>请选择</option>");
	var str = "";
	str +=  "<option value='0'>" + "请选择" + "</option>";
	$.ajax({
		type : "post",
		url : "./servlet/ProductTypeServlet?methodName=getRootType",
		contentType: "application/json;charset=utf-8",
        async: false,
		success : function(r) {
		
			if (r != "false") {
				var data = $.parseJSON(r);
	            for (var i = 0; i < data.webList.length; i++) {
					str += "<option value='" + data.webList[i].Id + "'>" + data.webList[i].Name + "</option>";
				}	
			}
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
	$("#firstSel").append(str);
	$("#standard_page .firstSel").html(str);
}


// delete the selected 商品种类
function deleChoice(obj){
	// delete current span
	$(obj).remove();
	// if current content is null,to hide the 商品描述
	if ($(".chioceDisplay").children().length <= 0)
	{
		$(".chioceShow").hide();
	}
}

//清除选择框中的内容
function clearSelection(){
    $('#firstSel').val(0);
    $('#secondSel').val(0);
    $('#thirdSel').val(0);
}



//add by lxt
//点击各个子模块时，将hid_subModuleInfo的value设置为相应的子模块名称，并加载该模块需要的搜索条件数据
function setProductDefineParams()//点击网站方框时触发的函数
{
    $('#hid_subModuleInfo').val("productDefine");
    $('.active').removeClass('active');
    $('#productDefineNum').parent().children('databox-left').addClass('active');
}

function setWebsiteParams()//点击网站方框时触发的函数
{
	$('#hid_subModuleInfo').val("website");
    $('.active').removeClass('active');
    $('#websiteNum').parent().children('databox-left').addClass('active');
}


function setBrandParams()//点击品牌方框时触发的函数
{
	$('#hid_subModuleInfo').val("brand");
    $('.active').removeClass('active');
    $('#brandNum').parent().children('databox-left').addClass('active');
}

function setProductParams()//点击商品方框时触发的函数
{
	$('#hid_subModuleInfo').val("product");
	$('.active').removeClass('active');
    $('#productNum').parent().children('databox-left').addClass('active');	
}

function setProductTypeParams()//点击商品类型方框时触发的函数
{
	$('#hid_subModuleInfo').val("productType");
	$('.active').removeClass('active');
    $('#productTypeNum').parent().children('databox-left').addClass('active');
}

function indexDoLogout() {
    location.href = "main.html";
}

/**
 * @desc 显示所有事件类型
 * @author liuyu
 */
function ShowEventType(){
	$("#eventType").html("<option selected value=''>全部</option>");

	  $.ajax({
	        type: "post",
	        url: "./servlet/EventTypeServlet?methodName=EventMap",
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.eventTypeList, function(key, val) {
	                $("#eventType").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}

/**
 * @desc 显示所有疫情类型
 * @author liuyu
 */
function ShowEpidemicType(){
	$("#epidemicType").html("<option selected value=''>全部</option>");

	  $.ajax({
	        type: "post",
	        url: "./servlet/EpidemicServlet?methodName=getEpidemic",
	        dataType: "json",
	        async: false,
	        success: function (r) {
	        	$.each(r.EpidemicList, function(key, val) {
	                $("#epidemicType").append('<option value="' + val.Id + '" >' + val.Name + '</option>');
	            });
	        },
	        error: function (e) {
	            alert(e.responseText);
	        }
	    });
}

function EpidemicTimeContent() {
	var val = $('input:radio[name="epidemicTime"]:checked').val();
	if (val == "自定义") {
		$(".timeContent").css("display", "inline");
	} else {
		$(".timeContent").css("display", "none");
	}
}

function EventTimeContent() {
	var val = $('input:radio[name="eventTime"]:checked').val();
	if (val == "自定义") {
		$(".timeContent").css("display", "inline");
	} else {
		$(".timeContent").css("display", "none");
	}
}

function gobacktoEventAffect(){
    $("#hid_eventAffect").val("eventAffect");
    $("#EventAffectSearch").show();
    $("#EventExactInfo").hide();
}

function mapclearing(){
    mapLoading([], []);
}

function getChoices(){
    var strChoices = ""
    $("#chioceDisplay span").each(function () {
        strChoices += $(this).attr("value");
    });
    if (strChoices != "") {
        strChoices = strChoices.substring(0, strChoices.length - 2);
    }
    return strChoices;
}

//二次编码 ，解决中文乱码
function encode(s)
{
	return encodeURIComponent(encodeURIComponent(s));
}

//加载标准下拉菜单
function initStandards(){
	$.ajax({
		type : "post",
		url : "./servlet/StandardServlet?methodName=GetStandards",
		dataType : "text",
		async : false,
		success : function(r) {
			var str = "<option value='-1'>请选择</option><option value='0'>全部</option>";
			if (r == "false" || r == "") {//加载失败
				
			}
			else if(r =="sessionOut"){//session过期
            	doLogout();
            }else {
            	var data = $.parseJSON(r);
	            for (var i = 0; i < data.standardList.length; i++) {
					str += "<option class='standardName' value='" + data.standardList[i].Id + "'>" + data.standardList[i].Name + "</option>";
				}
	            $("#ddl_standardName").html(str);
            }
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}