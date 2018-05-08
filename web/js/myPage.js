$(function () {
    //    $("#tablePrint tr:not(:first)").each(function () {
    //        $(this).children(":first").addClass("tdFirst")
    //    })
    $(":text").focus(function () {
        $(this).css("background", "#C0ED8B");
    }).blur(function () {
        $(this).css("background", "#fff");
    })
    $(".mytable tr:gt(0)").mousemove(function () {
        if (!$(this).hasClass("th")) {
            $(this).css("background-color", "#C4DFF7")
        }
    }).mouseout(function () {
        if (!$(this).hasClass("th")) {
            $(this).css("background-color", "#ffffff")
        }
    })
    $("#AspNetPager1 a[disabled]").css("color", "#ccc");

    $("#AspNetPager1 span").click(function () {

        if ($(this).children("a").length > 0) {
            if ($(this).children("a[disabled]").length > 0) {
                return false;
            }
            location.href = $(this).children("a:first").attr("href");
        }

    });
    $(".mysubmit").click(function () {
        art.artDialog.close();
    });
    $(".mybutton").click(function () {
        art.artDialog.close();
    });
    myTabs();

});

//激活选项卡
function myTabs() {
    if ($("#tabs").length > 0) {
        $("#tabs li").corner("top 5px"); //选项卡圆角
        $("#tabs").find("li").click(function (e) {
            if (e.target == this) {
                var tabs = $(this).parent().children("li");
                var panels = $(this).parent().parent().children("div");
                var index = $.inArray(this, tabs);
                if (panels.eq(index)[0]) {
                    tabs.removeClass("ui-tabs-selected")
                        .eq(index).addClass("ui-tabs-selected");
                    panels.addClass("ui-tabs-hide")
                        .eq(index).removeClass("ui-tabs-hide");
                }
            }
        });
    }
}


function GoUrl(strUrl, strTitle) {

    var str = strTitle.split(':')[0];
    if (str!="null") {
        $(window.parent.document.getElementById("currentPosition")).html(strTitle);
    }
    location.href = strUrl;
}


//function artDialog(objIdx, txt) {
//    art.dialog({
//        id: 'msg',
//        title: '提示',
//        content: txt,
//        fixed: false,
//        drag: false,
//        lock: true,
//        background: "#000",
//        opacity: 0.3,
//        resize: false,
//        cancel: function () { $(objIdx).focus().select(); },
//        cancelVal: "确 认",
//        icon: "warning",
//        padding: '20px 25px 10px 10px'

//    })
//}


//提示并选中
function alertFocus(objIdx, txt) {
    alert(txt);
    $(objIdx).focus().select();
}

//返回指定选项卡后(0为第一项) 提示并选中
function alertFocus(objIdx, txt, index) {
    $("#tabs ul li:eq(" + index + ")").click();
    alert(txt);
    $(objIdx).focus().select();
    
}

//V1 method
String.prototype.format = function () {
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,
        function (m, i) {
            return args[i];
        });
}

//V2 static
String.format = function () {
    if (arguments.length == 0)
        return null;

    var str = arguments[0];
    for (var i = 1; i < arguments.length; i++) {
        var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
}