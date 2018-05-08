		var menuWidth = 185;
        var mainHeight = 126;
		window.onload = function () {
            mainHeight = $("#header").height() + $("#footer").height();
            if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE8#0") {
                mainHeight = mainHeight + 4;
				}
				menuWidth = $("#leftmenu").width() + $("#slidebar").width();
				$("#rightbox").width($("#header").width() - menuWidth);
				$("#content").height($("html").height() - mainHeight - $("#topbar").height());
				$("#slidebar").height($("html").height() - mainHeight);
				$("#content").width($("#header").width() - menuWidth);
				//���˵��۵�
				$("#menutree0 span").click(function () {
                $(this).parent().siblings().children("span").removeClass("current");
                if ($(this).attr("class") == "") {
                    $(this).addClass("current").next().slideDown(100).parent().siblings().children("ul").slideUp(100);
                } else {
                    $(this).removeClass("current").next().slideUp(100);
                }
                return false;
            });
			//����չ�����˵�
			$("#slidebar").click(function () {
                if ($("#leftmenu").css('display') == 'none') {
                    var spanW = ($("#header").width() - $("#leftmenu").width() - $("#slidebar").width()) + "px";
                    $("#rightbox").animate({ width: spanW }, "fast");
                    $("#content").animate({ width: spanW }, "fast");
                    $("#leftmenu").show("fast", function () {
                        menuWidth = $("#leftmenu").width() + $("#slidebar").width();
                    });
                    $(this).css("background", "#ccc").mouseout(function () {
                        $(this).css("background", "#ccc");
                    });
                } else {
                    var spanW = ($("#header").width() - $("#slidebar").width()) + "px";
                    $("#rightbox").animate({ width: spanW }, "fast");
                    $("#content").animate({ width: spanW }, "fast");
                    $("#leftmenu").hide("fast", function () {
                        menuWidth = $("#slidebar").width();
                    });
                    $(this).css("background", "#1E71B1").mouseout(function () {
                        $(this).css("background", "#1E71B1");
                    });
                }
            }).mouseover(function () {
                $(this).css("background", "#1E71B1");

            }).mouseout(function () {
                if ($("#leftmenu").css('display') == 'none') {
                    $(this).css("background", "#1E71B1");
                } else {
                    $(this).css("background", "#ccc");
                }
            })

			/*function totopUrl(obj, id) {
				$("#headerMenu li a").removeClass("current");
				$(obj).addClass("current");
				$("#menutree0").addClass("display_none");
				$("#menutree0:eq(" + id + ")").removeClass("display_none").addClass("display_block");
				if ($("#menutree0:eq(" + id + ") li:first").children("span").attr("class") == "") {
					$("#menutree0:eq(" + id + ") li:first").children("span").click();
				}
				$("#menutree0:eq(" + id + ") li:eq(1)").children("a").click();
			}*/
		}


		//������Ӳ˵������ͣʱ����ɫ�Ŀ���
		$(document).ready(function(){
			$("#son_menu li").hover(function(){
				$(this).css("background-color","white").siblings().css("background-color","#fffccc");
			},function(){
				$(this).css("background-color","#fffccc");	
			})

		})
		
		


		
			

			