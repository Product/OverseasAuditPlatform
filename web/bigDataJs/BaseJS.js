var bigDatatimeCount; //计时器对象，用于定时刷新

// echar图形路径配置
require.config({
    paths: {
        echarts: 'build/dist'
    }
});

function lineChartRes(res) {
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            var myChart = ec.init(document.getElementById('website'));
            var option = {
                title: {
                    text: '跨境网站数量折线图'
                },
                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>{b} : {c}"
                },
                legend: {
                    x: 'right',
                    data: ["跨境网站数"]
                },
                xAxis: [
                    {
                        type: "category",
                        name: "月份",
                        splitLine: { show: false },
                        data: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]
                    }
                ],
                yAxis: [
                    {
                        type: "value",
                        name: "金额（百万）"
                    }
                ],
                series: [
                    {
                        name: "累计增长",
                        type: "line",
                        data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
                    }
                    //,
                    //{
                    //    name: "月度增长",
                    //    type: "line",
                    //    data: Mres
                    //
                    //}
                ]
            };
            myChart.setOption(option);
        });
}

//var projectLocation = "http://192.168.30.11:8080/TonikIntegration/";  //叶楷

var projectLocation = "./";

// 获取地址栏参数值
// paras:地址栏参数
function request(paras) {
    var url = location.href;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {};
    var j;
    for (var i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}

//刷新页面，跳转到登录
function doLogout() {
    location.href = location.href;
}

// 获取地址栏参数值
// paras:地址栏参数
function getParam(hidName, paras) {
    var url = $("#" + hidName).val();
    var paraString = url.split("&");
    var paraObj = {};
    var j;

    for (var i = 0; i < paraString.length; i++) {
    	j = paraString[i];
    	paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}

// 写入cookie
function SetCookie(name, value) {
    var Days = 30; // 此 cookie 将被保存 30 天
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}

// 删除cookie
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

// 读取cookie
function getCookie(name) {
    var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null)
        return unescape(arr[2]);
    return null;
}

// 日期操作 参数为天数增减
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate() + AddDayCount); // 获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1; // 获取当前月份的日期
    var d = dd.getDate();
    if (m.toString().length == 1) {
        m = "0" + m;
    }
    if (d.toString().length == 1) {
        d = "0" + d;
    }
    return y + "-" + m + "-" + d;
}


function LogOut() {
    $("#first").hide();
    $("#loginbox").show();
}

var menuWidth = 185;
var mainHeight = 127;


window.onload = function () {

    // for form validate,same name must be diffent id,add by liqiuling
    if ($.validator) {
        $.validator.prototype.elements = function () {
            var validator = this,
            rulesCache = {};
            return $([]).add(this.currentForm.elements)
        .filter(":input")
        .not(":submit, :reset, :image, [disabled]")
        .not(this.settings.ignore)
        .filter(function () {
            var elementIdentification = this.id || this.name;
            !elementIdentification && validator.settings.debug && window.console && console.error("%o has no id nor name assigned", this);
            // select only the first element for each name, and only those with rules specified
            if (elementIdentification in rulesCache || !validator.objectLength($(this).rules()))
                return false;
            rulesCache[elementIdentification] = true;
            return true;
        });
        };
    }

    // input获取焦点时清掉文字，失去焦点添加文字
    $('.input_text').bind({
        focus: function () {
            if (this.value == this.defaultValue) {
                this.value = "";
            }
        }
    });

    //  $(".menuTree span").click(function () {
    //      $(this).parent().siblings().children("span").removeClass("current");
    //      $(this).addClass("current").next().slideDown(500).parent().siblings().children("div").slideUp(500);
    //      return false;
    //  });

    $('.queryTargetResult span').click(function () {
        if ($(this).val() == 1) {
            $(this).next().slideDown(500);
            $(this).val(0);
        } else {
            $(this).next().slideUp(500);
            $(this).val(1);
        }
    });

    $("#bt_login").click(function () {
        var strUserCode = $("#txt_userCode").val();
        var strUserPwd = $("#txt_userPwd").val();
        if ($.trim(strUserCode) == "" || $.trim(strUserCode) == "") {
            alert("提醒：请正确填写账号密码！");
            return false;
        }
       $.ajax({
            type: "post",
            url: projectLocation + "servlet/LoginServlet?methodName=login&userCode="
        					+ strUserCode + "&userPwd=" + strUserPwd +"&system=1",
            dataType: "text",
            async: false,
            success: function (r) {
                if (r != "false") {
                    var data = $.parseJSON(r);
                    var pFlag = 1;
                    for (var i = 0; i < data.menuList.length; i++) {
                        if (data.menuList[i].Level == "1") {
                            $("#headerMenu").append("<li><a rel='" + data.menuList[i].NodeId + "' onclick='toTopUrl(this," + i + ")' >" + data.menuList[i].Name + "</a></li>");
                            if (pFlag == 1) {
                                pFlag = 2;
                                $(".mainMenu").append("<ul id='menu_" + data.menuList[i].Id + "' class='menuTree'>");
                            } else {
                                $(".mainMenu").append("<ul id='menu_" + data.menuList[i].Id + "' class='menuTree display_none'>");
                            }
                        } else if (data.menuList[i].Level == "2") {
                            //$("#menu_" + data.menuList[i].ParentId).append("<li><span>" +
                            //                "<img src='" + data.menuList[i].PicUrl + "' width='32'>" + data.menuList[i].Name + "</span>" +
                            //                "<div><ul id='menu_" + data.menuList[i].Id + "'></ul></div>" +
                            //            "</li>");
                            $("#menu_" + data.menuList[i].ParentId).append("<li><span rel='" + data.menuList[i].NodeId + "' onclick='toUrl(this)'>" +
                                "<img src='" + data.menuList[i].PicUrl + "' width='32'>" + data.menuList[i].Name + "</span>" +
                               "</li>");
                        } else if (data.menuList[i].Level == "3") {
                            $("#menu_" + data.menuList[i].ParentId).append("<li><a rel='" + data.menuList[i].NodeId + "' onclick='toUrl(this)'>" +
                                            "<img src='" + data.menuList[i].PicUrl + "' width='24'>" + data.menuList[i].Name + "</a></li>" +
                                        "</li>");
                        }
                    }

                    $("#loginbox").hide();
                    $("#first").show();
                    $("#iframeMain").children("div").hide();
                    //进入界面，默认头部导航第一个菜单选中
                    $(".headerMenu li:first a").addClass("current");
                    //$(".menuTree span:eq(0)").children("div").children("a").click();

                    mainHeight = $(".header").height() + $(".footer").height();
                    if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE8.0") {
                        mainHeight = mainHeight + 4;
                    }
                    menuWidth = $(".mainMenu").width() + $("#mainSpan").width();
                    $("#mainContent").width($(".header").width() - menuWidth);
                    $("#iframeMain").height($("html").height() - mainHeight - $(".currentPosition").height());
                    $("#mainSpan").height($("html").height() - mainHeight);
                    $("#iframeMain").width($(".header").width() - menuWidth);
                    $(".menuTree span").click(function () {
                        if ($(this).next().is(":hidden")) {
                            $(this).next().slideDown();
                        } else {
                            $(this).next().slideUp();
                        }
                    });
                } else {
                    alert("账号或密码错误！");
                }
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    });


    $.validator.setDefaults({
        submitHandler: function (form) {
            form.submit();
        }
    });


    $("#nextStepTask").click(function () {
        if ($("#firstTaskForm").valid()) {
            progressFirst('first', 'second');
        }
    });
    
    $("#nextStepNewsGatherTask").click(function(){
    	if ($("#NewsGatherTaskfirstTaskForm").valid()) {
            progressFirst('NewsGatherTaskfirst', 'NewsGatherTasksecond');
    	}
    });

    $("#second_div").click(function () {
        if ($("#firstTaskForm").valid()) {
            progressBack('second', 'first', 'third');
        }
    });

    $("#third_div").click(function () {
        if ($("#firstTaskForm").valid()) {
            progressBack('third', 'first', 'second');
        }
    });

    $("#mainSpan").click(function () {
        if ($(".mainMenu").css('display') == 'none') {
            var spanW = ($(".header").width() - $(".mainMenu").width() - $("#mainSpan").width()) + "px";
            $("#mainContent").animate({ width: spanW }, 1000);
            $("#iframeMain").animate({ width: spanW }, 1000);
            $(".mainMenu").show(1000, function () {
                menuWidth = $(".mainMenu").width() + $("#mainSpan").width();
            });
            $(this).css("background", "#ccc").mouseout(function () {
                $(this).css("background", "#ccc");
            });
        } else {
            var spanW = ($(".header").width() - $("#mainSpan").width()) + "px";
            $("#mainContent").animate({ width: spanW }, 1000);

            $("#iframeMain").animate({ width: spanW }, 1000);
            $(".mainMenu").hide(1000, function () {
                menuWidth = $("#mainSpan").width();
            });
            $(this).css("background", "#1E71B1").mouseout(function () {
                $(this).css("background", "#1E71B1");
            });
        }
    }).mouseover(function () {
        $(this).css("background", "#1E71B1");
    }).mouseout(function () {
        if ($(".mainMenu").css('display') == 'none') {
            $(this).css("background", "#1E71B1");
        } else {
            $(this).css("background", "#ccc");
        }
    });

    $("#checkTaskTitle").mousedown(function (e)//e鼠标事件 
    {
        $(this).css("cursor", "move"); //改变鼠标指针的形状 
        var offset = $(this).offset(); //DIV在页面的位置 
        var x = e.pageX - offset.left; //获得鼠标指针离DIV元素左边界的距离 
        var y = e.pageY - offset.top; //获得鼠标指针离DIV元素上边界的距离 
        $(document).bind("mousemove", function (ev)//绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件 
        {
            $("#checkTaskShow").stop(); //加上这个之后 

            var _x = ev.pageX - x; //获得X轴方向移动的值 
            var _y = ev.pageY - y; //获得Y轴方向移动的值 

            $("#checkTaskShow").animate({ left: _x + "px", top: _y + "px" }, 10);
        });
    });

    $(document).mouseup(function () {
        $("#checkTaskShow").css("cursor", "default");
        $(this).unbind("mousemove");
    })
    $("#lblDate").html(GetDateStr(0));

    $("a[url]").css("cursor", "pointer");
}
var resizeTimer = null;
$(window).resize(function () { //浏览器窗口变化 
    if (resizeTimer) {
        clearTimeout(resizeTimer)
    }
    resizeTimer = setTimeout(function () {
        $("#mainContent").width($(".header").width() - menuWidth);
        $("#iframeMain").width($(".header").width() - menuWidth);
        var sh = mainHeight;
        if ($(".header").css('display') == 'none') {
            var sh = mainHeight - $(".header").height();
        }
        $("#mainSpan").height($("html").height() - sh);
        $("#iframeMain").height($("html").height() - sh - $(".currentPosition").height());
        $("#checkTaskShow .content").css("height", parseInt($("#checkTaskShow").height() - 20));
    }, 400);
});


// 登陆界面，enter压下登陆，added by liqiuling
function enterLogin() {
	var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (code == 13) {
        $("#bt_login").click();
    }
}

//target 转 iframe 页面
function toUrl(obj) {
    if ($("#" + $(obj).attr('rel')).is(":hidden")) {
        $("#iframeMain .wrap").css("display", "none");
        $('#' + $(obj).attr('rel')).show();
        if (obj.nodeName == "SPAN") {
            $("#currentPosition").html("当前位置：" + $(obj).text());
        }
        
        $(obj).parent().parent().children("li").children("a").removeClass("current");
        $(obj).parent().parent().parent().parent().siblings().children("div").children("ul").children("li").children("a").removeClass("current");
        $(".mainMenu li span").removeClass("current");
        $(obj).addClass("current");
    }
    
    //$("#txt_NewsDocumentsEvent").val(-1);
   

    //商品目录
    if ($(obj).attr('rel') == 'catalogue') {
        initProTree();
        getMenuName();
    }
    //专家知识库
    else if ($(obj).attr('rel') == 'repository') {
        initRepositoryTree();
    }
    // 评价指标
    else if ($(obj).attr('rel') == 'evaluation_index') {
        //getIndexTree();
        getProTypeList();
        getIndexList();
        // 加载目录指标树
        getIndexTrees("1");
        initIndeTree();
    }

	//报告系统
    else if($(obj).attr('rel')== 'reporting_system'){
    	FirstPage_Reporting();
 	}
	
    //基础数据-网站管理
    else if($(obj).attr('rel')== 'websiteListDom'){
    	WFirstPage();
    }
    
    //基础数据-商品管理
    if($(obj).attr('rel')== 'productListDom'){
    	PFirstPage();
    }
    
}

function sideBarHide () {
    var spanW = ($(".header").width() - $("#mainSpan").width()) + "px";
    $("#mainContent").animate({ width: spanW }, 500);
    $("#iframeMain").animate({ width: spanW }, 500);
    $(".mainMenu").hide(500, function () {
        menuWidth = $("#mainSpan").width();
    });
    $(this).css("background", "#1E71B1").mouseout(function () {
        $(this).css("background", "#1E71B1");
    });
}
//头部导航栏跳转
function toTopUrl(obj, id) {
    $(".headerMenu li a").removeClass("current");
    $(obj).addClass("current");
    $(".menuTree").addClass("display_none");
    $(".menuTree:eq(" + id + ")").removeClass("display_none").addClass("display_block");
    toUrl(obj);
    $(".mainMenu li:nth-child(1) span").addClass("current");//选中头部导航栏时，默认选中左边导航第一个标签
    if ($(obj).attr('rel') == 'reportForm') {

        // sidebar自动隐藏
        sideBarHide();
    }
    // 否则显示sidebar
    else {
        // 否则显示sidebar
        var spanW = ($(".header").width() - $(".mainMenu").width() - $("#mainSpan").width()) + "px";
        $("#mainContent").animate({ width: spanW }, 500);
        $("#iframeMain").animate({ width: spanW }, 500);
        $(".mainMenu").show(500, function () {
            menuWidth = $(".mainMenu").width() + $("#mainSpan").width();
        });
        $(this).css("background", "#ccc").mouseout(function () {
            $(this).css("background", "#ccc");
        });
    }


}

//首页提示跳转
function toIndexUrl(obj) {
    $('#' + $(obj).attr('target')).attr('src', $(obj).attr('url'));
    $("#currentPosition").html("当前位置：首页");
}

//header显示/隐藏 big
function TopNone(obj) {
    if ($(".header").css('display') == 'none') {
        var spanH = ($("html").height() - mainHeight) + "px";
        $("#mainSpan").animate({ height: spanH }, 500);
        $("#iframeMain").height($("html").height() - mainHeight - $(".currentPosition").height());
        $(".header").slideDown(500);
        $(obj).text("收缩");
    } else {
        var spanH = ($("html").height() - mainHeight + $(".header").height()) + "px";
        $("#mainSpan").animate({ height: spanH }, 500);
        $("#iframeMain").height($("html").height() - mainHeight - $(".currentPosition").height() + $(".header").height());
        $(".header").slideUp(500);
        $(obj).text("展开");
    }
}

function alertText(str, time) {
    if (str != "") {
        $("#messgeBox .mbContent").html(str);
        $("#messgeBox").slideDown(500);
        timer = setTimeout(function () { $("#messgeBox").slideUp(500); }, time);
        $("#messgeBox").hover(
           function () {
               if (timer) clearTimeout(timer);
           },
           function () {
               timer = setTimeout(function () { $("#messgeBox").slideUp(500); }, time);
           });
    }
}

// 显示遮罩层
function confirmShow(id, callback,text) {
    $("#bg").css("display", "block");
    $("#show").css("display", "block");
	if (text != null)
	{
		$("#wordsarea").text("");
		$("#wordsarea").append(text);
	}
	//移除其它绑定事件
	$("#btnCloseSure").unbind();
	$("#btnCloseCancel").unbind();
    //确定按钮事件
    $("#btnCloseSure").click(function () {
        $("#bg").css("display", "none");
        $("#show").css("display", "none");
        if (typeof (callback) == 'function') {
			if (id == null)
			{
				callback();
			} else {
				callback(id);
			}
        }
    });
    //取消按钮事件
    $("#btnCloseCancel").click(function () {
        $("#bg").css("display", "none");
        $("#show").css("display", "none");
    });
}

// 显示alert信息
function alertShow(text) {
    $("#bg").css("display", "block");
    $("#showAlert").css("display", "block");
	if (text != null)
	{
		$("#alertText").text("");
		$("#alertText").append(text);
	}
	 //确认按钮事件
    $("#btnCloseSureAlert").click(function () {
		$("#bg").css("display", "none");
		$("#showAlert").css("display", "none");
    });
}

//function alertText(DomName, str, color, time) {
//    if (str != "") {
//        $("#" + DomName).html(str);
//        $("#" + DomName).css("color", color);
//        $("#" + DomName).slideDown(500);
//        setTimeout(function () {
//            $("#" + DomName).slideUp(500);
//        }, time);
//    }
//}

var i = 0;
function progressFirst(name, str) {
    if (i > 100) {
        $("#" + name + "Progress").css("display", "none");
        $("#" + str + "Progress").css("display", "block");
        //加载完毕提示 
        $("#progressBar" + name).children("div").removeClass("second_div");
        $("#progressBar" + name).children("span").removeClass("second_span");
        $("#progressBar" + name).children("div").addClass("first_div");
        $("#progressBar" + name).children("span").addClass("first_span");
        i = 0;
        return;
    }
    if (i <= 100) {
        setTimer = setTimeout("progressFirst('" + name + "','" + str + "')", 0.5);
        setProgress(i, name);
        i++;
    }
}

function setProgress(progress, name) {
    if (progress) {
        $("#" + name + "Img> div").css("width", String(progress) + "%"); //控制#loading div宽度 
    }
}

function progressPrevoius(name, str) {
    if (name == "") {
        $("#iframeMain .wrap").css("display", "none");
        if(str !="NewsGatherTaskfirst")
        	$('#TaskListDom').css("display", "block");
        else
        	$('#NewsGatherTaskListDom').css("display", "block");
    }
    $("#" + name + "Progress").css("display", "block");
    $("#" + str + "Progress").css("display", "none");
    $("#" + name + "Img> div").css("width", "0");
    $("#progressBar" + name).children("div").removeClass("first_div");
    $("#progressBar" + name).children("span").removeClass("first_span");
    $("#progressBar" + name).children("div").addClass("second_div");
    $("#progressBar" + name).children("span").addClass("second_span");

}

function progressBack(name, str, partName) {
    $("#" + name + "Progress").css("display", "block");
    $("#" + str + "Progress").css("display", "none");
    $("#" + partName + "Progress").css("display", "none");
    if (name == 'first') {
        $("#" + name + "Img> div").css("width", "0");
        $("#" + str + "Img> div").css("width", "0");
        changeProgress();
    }
    else if (name == "second") {
        changeProgress();
        progressFirst("first", "second");
        $("#" + name + "Img> div").css("width", "0");
    }
    else if (name == "third") {
        $("#firstImg> div").css("width", "100%");
        $("#progressBarfirst").children("div").removeClass("second_div");
        $("#progressBarfirst").children("span").removeClass("second_span");
        $("#progressBarfirst").children("div").addClass("first_div");
        $("#progressBarfirst").children("span").addClass("first_span");
        progressFirst("second", "third");
    }
}


function changeProgress() {
    $("#progressBarfirst").children("div").removeClass("first_div");
    $("#progressBarfirst").children("span").removeClass("first_span");
    $("#progressBarfirst").children("div").addClass("second_div");
    $("#progressBarfirst").children("span").addClass("second_span");
    $("#progressBarsecond").children("div").removeClass("first_div");
    $("#progressBarsecond").children("span").removeClass("first_span");
    $("#progressBarsecond").children("div").addClass("second_div");
    $("#progressBarsecond").children("span").addClass("second_span");
}

function encode(s)
{
	return encodeURIComponent(encodeURIComponent(s));
//	return encodeURI(encodeURI(s));
}

//只允许输入数字
function onlyNum() {
    if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
    if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))){
    	event.returnValue=false;
    	alertText("只允许输入数字", 4000);
    }
    
}


function workflowBasic(){
	$("#workflow").hide();
	$("#websiteListDom").show();
	
	 /*$.ajax({
         type: "post",
         url: projectLocation
					+ "servlet/MismatchProductServlet?methodName=workFlowPic",
         dataType: "json",
         async: false,
         success: function (r) {
             if (r.Url == "images/risk_workflow_500.png") {
                 $("#img500").show();
                 $("#img1000").hide();
                 $("#img2000").hide(); 
                 $("#img1").hide();
             }else if(r.Url =="images/risk_workflow_1000.png"){
            	 $("#img500").hide();
                 $("#img1000").show();
                 $("#img2000").hide(); 
                 $("#img1").hide();
             }else if(r.Url == "images/risk_workflow_2000.png"){
            	 $("#img500").hide();
                 $("#img1000").hide();
                 $("#img2000").show(); 
                 $("#img1").hide();
      	   	 }else{
	      	   	 $("#img500").hide();
	             $("#img1000").hide();
	             $("#img2000").hide(); 
	             $("#img1").show();
      	   	 }
         },
         error: function (e) {
             alert(e.responseText);
         }
     });*/
}

function workflowProduct(){
	$("#workflow").hide();
	$("#productListDom").show();
	
}

function workflowCatalogue(){
	$("#workflow").hide();
	$("#catalogue").show();
	
}

function workflowStandard(){
	$("#workflow").hide();
	$("#ProductDefinitionDom").show();
	
}

function workflowRegular(){
	$("#workflow").hide();
	$("#StandardDom").show();
	
}

function workflowRiskPublish(){
	$("#workflow").hide();
	$("#information_publish").show();
}