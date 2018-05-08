/**
 * 实时检测页面
 */
$(function () {
	var standardPageSize = 3;//默认每页3条记录
	
	//====== 不匹配商品分页展示 ======
	$("#realTimeMonitoring_page .firstPage").click(function(){//首页
		getRealTimeStandardsList(standardPageSize,1);
	});
	
	$("#realTimeMonitoring_page .previousPage").click(function(){//上一页
		var pageIndex = parseInt($("#realTimeMonitoring_page .pageIndex").html()) - 1;
		if(pageIndex < 1){
			pageIndex = 1;
		}
		getRealTimeStandardsList(standardPageSize,pageIndex);
	});
	
	$("#realTimeMonitoring_page .nextPage").click(function(){//下一页
		var pageIndex = parseInt($("#realTimeMonitoring_page .pageIndex").html()) + 1;
		var pageCount = parseInt($("#realTimeMonitoring_page .pageCount").html());
		if(pageIndex > pageCount){
			pageIndex = pageCount;
		}
		getRealTimeStandardsList(standardPageSize,pageIndex);
	});
	
	$("#realTimeMonitoring_page .endPage").click(function(){//尾页
		var pageIndex = parseInt($("#realTimeMonitoring_page .pageCount").html());
		getRealTimeStandardsList(standardPageSize,pageIndex);
	});
	
	$("#menuHide").click(function () {
        if ($(".sidebar").css('display') == 'none') {
        	$(".page-wrapper-inner").animate({ marginLeft:"255px" }, 1000);
            $("#menuHide").animate({ marginLeft:"250px" }, 1000);
            $(".sidebar").show(1000);
        } else {
        	$("#menuHide").animate({ marginLeft:"0px" }, 1000);
        	$(".page-wrapper-inner").animate({ marginLeft:"5px" }, 1000);
            $(".sidebar").hide(1000);
        }
    }).mouseover(function () {
        $(this).css("background", "#1E71B1");
    }).mouseout(function () {
        if ($(".sidebar").css('display') == 'none') {
            $(this).css("background", "#1E71B1");
        } else {
            $(this).css("background", "#ccc");
        }
    });
});

//添加计时器，自动刷新页面
function realTimeMonitoringCount(){
	standardMapInit()//加载舆情地图
	getStandardGrade()//加载记录数
	getRealTimeStandardsList(3,1);//加载不匹配商品列表
	timeCount = setTimeout("realTimeMonitoringCount()",5000);//每5秒刷新一次
}

//关闭计时器
function stopRealTimeMonitoringCount(){
	if(typeof timeCount != "undefined"){
		clearTimeout(timeCount);
	}
}

//加载不匹配商品列表
/*var flag=0;
function getRealTimeStandardsList(pageSize,pageIndex){
	var standardId = encode($("#standard_page .standard").val());
	var choiceLength = encode($("#standard_page .chioceDisplay span").length);
	var choices = ""; 
	
	$("#standard_page .chioceDisplay span").each(function () {
		choices += $(this).attr("value");
	});
	
	if (choices != "") {
		choices = encode(choices.substring(0, choices.length - 2));
	}

	$.ajax({
        type: "post",
        url: "./servlet/MismatchProductServlet?methodName=QueryList&module=All&pageSize=" + pageSize + "&pageIndex=" + pageIndex + "&choiceLength=0&choices=&orderBy=CREATE_TIME&orderType=2",
        dataType: "text",
        async: false,
        success: function (r) {
        	var pageCount = 1;//页码值
        	var dataCount = 0;//记录总数
        	var str = "<tr><td colspan='6' style='text-align:center;'>暂无数据</td><tr>";

        	if(r == "false" || r == ""){//查找失败
        		$("#realTimeMonitoring_page .pageTotal").html('0');
        	}
        	else if (r == "sessionOut") {//session过期
        		indexDoLogout();
        	}
        	else {
                var data = $.parseJSON(r);
				if (flag == 1) {
					data = {"Total":"1263","DataList":[{"Id":"59114","ProductName":"美素佳儿","WebName":"路口","Url":"http://www.lukou.com/gocmd/1/41307003336?from=commodity&feed=5713796","MismatchContent":"锌|大于等于0.1，小于等于0.3|0.743","CreateTime":"2016-12-26 14:21:00","StandardName":"GB10767-2017"},
						{"Id":"59113","ProductName":"惠氏","WebName":"母婴之家","Url":"http://item.muyingzhijia.com/151547.html","MismatchContent":"维生素A|大于等于18.0，小于等于54.0|823.5295","CreateTime":"2016-12-26 9:48:22","StandardName":"GB10767-2016"},
						{"Id":"59112","ProductName":"美赞成A+","WebName":"母婴之家","Url":"http://item.muyingzhijia.com/154635.html","MismatchContent":"维生素D|大于等于0.25，小于等于0.75|2.143||钠|小于等于20.0|35.7144||钾|大于等于18.0，小于等于69.0|115.7143||铜|大于等于7.0，小于等于35.0|61.4287||铁|大于等于0.25，小于等于0.5|1.4287||锌|大于等于0.1，小于等于0.3|0.743","CreateTime":"2016-12-26 19:18:21","StandardName":"GB16767-2010"}]}
				}
				else if (flag == 2){
					data = {"Total":"1263","DataList":[{"Id":"59114","ProductName":"雅士利","WebName":"路口","Url":"http://www.lukou.com/gocmd/1/41307003336?from=commodity&feed=5713796","MismatchContent":"锌|大于等于0.1，小于等于0.3|0.743","CreateTime":"2017-3-20 14:21:00","StandardName":"GB10767-2014"},
						{"Id":"59113","ProductName":"雅培","WebName":"母婴之家","Url":"http://item.muyingzhijia.com/151547.html","MismatchContent":"蛋白质|大于等于0.7，小于等于1.2|20.5883||脂肪|大于等于0.7，小于等于1.4|44.1177||维生素A|大于等于18.0，小于等于54.0|823.5295","CreateTime":"2017-3-20 19:18:22","StandardName":"GB10767-2016"},
						{"Id":"59112","ProductName":"雀巢","WebName":"母婴之家","Url":"http://item.muyingzhijia.com/154635.html","MismatchContent":"钠|小于等于20.0|35.7144||铁|大于等于0.25，小于等于0.5|1.4287||锌|大于等于0.1，小于等于0.3|0.743","CreateTime":"2017-3-20 9:18:21","StandardName":"GB16767-2013"}]}
				}
				else{
					flag = 0;
				}
                if(data.DataList.length>0){
                	str = "";
                	$(data.DataList).each(function(){
                		str += "<tr rel='" + this.Id + "'>" 
                			+  "<td>" + this.CreateTime + "</td>"
                			+  "<td>" + this.ProductName + "</td>"//商品名称
                			+  "<td>" + this.StandardName + "</td>";//规则名称
                		var contentText = "";
                		var mismatchContent = this.MismatchContent.split("||");
                		for(var i = 0; i< mismatchContent.length;i++){                			
                			var content = mismatchContent[i].split("|");
                			contentText +=  content[0] + ", ";
                		}
                		if(contentText != ""){
                			contentText = contentText.substring(0,contentText.length -2);
                		}
                		str += "<td>" + contentText + "</td>";//不匹配的内容 TODO 文本可能变化
                		str += "</tr>";
                	});
                }
                if(data.Total != ""){
                	pageCount = parseInt((data.Total-1)/5) + 1;
                	dataCount = data.Total;
                }
            }
        	$("#realTimeMonitoring_page .pageCount").html(pageCount);
        	$("#realTimeMonitoring_page .dataCount").html(dataCount);
        	$("#realTimeMonitoring_page .pageIndex").html(pageIndex);
        	$("#realTimeMonitoring_page .productList tbody").html(str);
			flag ++;
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}*/

var flag=0;
function getRealTimeStandardsList(pageSize,pageIndex){
	var standardId = encode($("#standard_page .standard").val());
	var choiceLength = encode($("#standard_page .chioceDisplay span").length);
	var choices = ""; 
	
	$("#standard_page .chioceDisplay span").each(function () {
		choices += $(this).attr("value");
	});
	
	if (choices != "") {
		choices = encode(choices.substring(0, choices.length - 2));
	}

	$.ajax({
        type: "post",
        url: "./servlet/MismatchProductServlet?methodName=QueryList&module=All&pageSize=" + pageSize + "&pageIndex=" + pageIndex + "&choiceLength=0&choices=&orderBy=CREATE_TIME&orderType=2",
        dataType: "text",
        async: false,
        success: function (r) {
        	var pageCount = 1;//页码值
        	var dataCount = 0;//记录总数
        	var str = "<tr><td colspan='6' style='text-align:center;'>暂无数据</td><tr>";

        	if(r == "false" || r == ""){//查找失败
        		$("#realTimeMonitoring_page .pageTotal").html('0');
        	}
        	else if (r == "sessionOut") {//session过期
        		indexDoLogout();
        	}
        	else {
                var data = $.parseJSON(r);
                if(data.DataList.length>0){
                	str = "";
                	$(data.DataList).each(function(){
                		str += "<tr rel='" + this.Id + "'>" 
                			+  "<td>" + this.CreateTime + "</td>"
                			+  "<td>" + this.ProductName + "</td>"//商品名称
                			+  "<td>" + this.StandardName + "</td>";//规则名称
                		var contentText = "";
                		var mismatchContent = this.MismatchContent.split("||");
                		for(var i = 0; i< mismatchContent.length;i++){                			
                			var content = mismatchContent[i].split("|");
                			contentText +=  content[0] + ", ";
                		}
                		if(contentText != ""){
                			contentText = contentText.substring(0,contentText.length -2);
                		}
                		str += "<td>" + contentText + "</td>";//不匹配的内容 TODO 文本可能变化
                		str += "</tr>";
                	});
                }
                if(data.Total != ""){
                	pageCount = parseInt(((data.Total-1)/3)-1) +1;
                	dataCount = data.Total-3;
                }
            }
        	$("#realTimeMonitoring_page .pageCount").html(pageCount);
        	$("#realTimeMonitoring_page .dataCount").html(dataCount);
        	$("#realTimeMonitoring_page .pageIndex").html(pageIndex);
        	$("#realTimeMonitoring_page .productList tbody").html(str);
        	if(flag<pageCount){
        		flag++;
        	}else{
        		flag=1;
        	}
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}



//获得产品数量、商品数量、规则数量和疑似商品数量
function getStandardGrade(){
	$.ajax({
        type: "post",
        url: "./servlet/MismatchProductServlet?methodName=StandardGrade",
        dataType: "text",
        async: false,
        success: function (r) {
        	if(r == "false" || r == ""){//查找失败
        	}
        	else if (r == "sessionOut") {//session过期
        		indexDoLogout();
        	}
        	else {
                var data = $.parseJSON(r);
                if(data.WebsiteTotal != $("#standardWebsiteNum").html()){//网站数量
                	$("#standardWebsiteNum").fadeOut();
                	$("#standardWebsiteNum").html(data.WebsiteTotal);
                	$("#standardWebsiteNum").fadeIn();
                }
                if(data.ProductTotal != $("#stanndardProductNum").html()){//商品数量
                	$("#stanndardProductNum").fadeOut();
                	$("#stanndardProductNum").html(data.ProductTotal);
                	$("#stanndardProductNum").fadeIn();
                }
/*                if(data.StandardTotal != $("#standardNum").html()){//规则数量
                	$("#standardNum").fadeOut();
                	$("#standardNum").html(data.StandardTotal);
                	$("#standardNum").fadeIn();
                }*/
                if(data.MisMatchProductTotal != $("#misMatchProductNum").html()){//风险商品数量 
                	$("#misMatchProductNum").fadeOut();
                	$("#misMatchProductNum").html(data.MisMatchProductTotal);
                	$("#misMatchProductNum").fadeIn();
                }
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

//获取各个地区风险商品的数量
function standardMapInit(){
	var lineData = [];
	var pointData = [];
	
	$.ajax({
        type: "post",
        url: "./servlet/MismatchProductServlet?methodName=GetCountry",
        dataType: "text",
        async: false,
        success: function (r) {
        	if(r == "false" || r == ""){//查找失败
        	}
        	else if (r == "sessionOut") {//session过期
        		indexDoLogout();
        	}
        	else {
                var data = $.parseJSON(r);
                $(data.DataList).each(function(){
                	lineData.push([{name:this.Name,value:this.Value},{name:'中国大陆'}]);
                	pointData.push({name:this.Name,value:this.Value});
                });
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
/*	//测试数据
	lineData = [
					[{name:'俄罗斯',value:95}, {name:'中国大陆'}],
					[{name:'韩国',value:90}, {name:'中国大陆'}],
					[{name:'美国',value:80}, {name:'中国大陆'}],
					[{name:'加拿大',value:70}, {name:'中国大陆'}],
					[{name:'荷兰',value:60}, {name:'中国大陆'}],
					[{name:'比利时',value:50}, {name:'中国大陆'}],
					[{name:'澳大利亚',value:40}, {name:'中国大陆'}],
					[{name:'台湾',value:10}, {name:'中国大陆'}],
				];
	pointData = [
						{name:'俄罗斯',value:95},
						{name:'韩国',value:90},
						{name:'美国',value:80},
						{name:'加拿大',value:70},
						{name:'荷兰',value:60},
						{name:'比利时',value:50},
						{name:'澳大利亚',value:40},
						{name:'台湾',value:10},
					];*/
	standardMapLoading(lineData,pointData);
}

//加载实时检测图
function standardMapLoading(lineData,pointData) {
	 // 使用
   require(
           [
               'echarts',
               'echarts/chart/map' // 使用柱状图就加载bar模块，按需加载
           ],
			function (ec) {
			    myChart = ec.init(document.getElementById('standardMap'));

			    // world map option       
			    var option = {
						backgroundColor: '#3c8dd7',
						color: ['gold','aqua','lime'],
						tooltip : {
							trigger: 'item',
							formatter: '{b}'
						},
						 dataRange: {
							min : 0,
							max : 100,
							calculable : true,
							color: ['#ff3333', 'orange', 'yellow','lime','aqua'],//
						},
						series : [
							{
								name: '世界',
								type: 'map',
								roam: true,
								hoverable: false,
								mapType: 'world',
								itemStyle:{
									normal:{
										borderColor:'rgba(0,0,0,1)',
										borderWidth:0.8,
										areaStyle:{
											color: '#53aa8d'
										}
									}
								},
								data:[
								      {
								    	  name : '中国',
								    	  itemStyle : {
								    		  normal : {
								    			  areaStyle : {
								    				  color : '#ffe165'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '美国',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '荷兰',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '韩国',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '日本',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      }, 
								      {
								    	  name: '法国',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      }, 
								      {
								    	  name: '英国',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '挪威',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '马来西亚',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '瑞士',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '丹麦',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '新西兰',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '澳大利亚',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '德国',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '泰国',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '加拿大',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '西班牙',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '意大利',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '俄罗斯',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '比利时',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      {
								    	  name: '香港',
								    	  itemStyle: {
								    		  normal: {
								    			  areaStyle: {
								    				  color: '#f74bb0'
								    			  },
								    		  }
								    	  },
								      },
								      ],
								nameMap: {
									'Brazil': '巴西',
									'Brunei': '文莱',
									'Bhutan': '不丹',
									'Botswana': '博茨瓦纳',
									'Central African Republic': '中非共和国',
									'Canada': '加拿大',
									'Switzerland': '瑞士',
									'Chile': '智利',
									'China': '中国',
									'Afghanistan': '阿富汗',
									'Angola': '安哥拉',
									'Albania' : '阿尔巴尼亚',
									'United Arab Emirates' : '阿联酋',
									'Argentina' : '阿根廷',
									'Armenia' : '亚美尼亚',
									'French Southern and Antarctic Lands' : '法属南半球和南极领地',
									'Australia' : '澳大利亚',
									'Austria' : '奥地利',
									'Azerbaijan' : '阿塞拜疆',
									'Burundi' : '布隆迪',
									'Belgium' : '比利时',
									'Benin' : '贝宁',
									'Burkina Faso' : '布基纳法索',
									'Bangladesh' : '孟加拉国',
									'Bulgaria' : '保加利亚',
									'The Bahamas' : '巴哈马',
									'Bosnia and Herzegovina' : '波斯尼亚和黑塞哥维那',
									'Belarus' : '白俄罗斯',
									'Belize' : '伯利兹',
									'Bermuda' : '百慕大',
									'Bolivia' : '玻利维亚',
									'Ivory Coast' : '象牙海岸',
									'Cameroon' : '喀麦隆',
									'Democratic Republic of the Congo': '刚果民主共和国',
									'Republic of the Congo': '刚果共和国',
									'Colombia': '哥伦比亚',
									'Costa Rica': '哥斯达黎加',
									'Cuba': '古巴',
									'Northern Cyprus': '北塞浦路斯',
									'Cyprus': '塞浦路斯',
									'Czech Republic': '捷克共和国',
									'Germany': '德国',
									'Djibouti': '吉布提',
									'Denmark': '丹麦',
									'Dominican Republic': '多明尼加共和国',
									'Algeria': '阿尔及利亚',
									'Ecuador': '厄瓜多尔',
									'Egypt': '埃及',
									'Eritrea': '厄立特里亚',
									'Spain': '西班牙',
									'Estonia': '爱沙尼亚',
									'Ethiopia': '埃塞俄比亚',
									'Finland': '芬兰',
									'Fiji': '斐济',
									'Falkland Islands': '福克兰群岛',
									'France': '法国',
									'Gabon': '加蓬',
									'United Kingdom': '英国',
									'Georgia': '格鲁吉亚',
									'Ghana': '加纳',
									'Guinea': '几内亚',
									'Gambia': '冈比亚',
									'Guinea Bissau': '几内亚比绍',
									'Equatorial Guinea': '赤道几内亚',
									'Greece': '希腊',
									'Greenland': '格陵兰',
									'Guatemala': '危地马拉',
									'French Guiana': '法属圭亚那',
									'Guyana': '圭亚那',
									'Honduras': '洪都拉斯',
									'Croatia': '克罗地亚',
									'Haiti': '海地',
									'Hungary': '匈牙利',
									'Indonesia': '印尼',
									'India': '印度',
									'Ireland': '爱尔兰',
									'Iran': '伊朗',
									'Iraq': '伊拉克',
									'Iceland': '冰岛',
									'Israel': '以色列',
									'Italy': '意大利',
									'Jamaica': '牙买加',
									'Jordan': '约旦',
									'Japan': '日本',
									'Kazakhstan': '哈萨克斯坦',
									'Kenya': '肯尼亚',
									'Kyrgyzstan': '吉尔吉斯斯坦',
									'Cambodia': '柬埔寨',
									'South Korea': '韩国',
									'Kosovo': '科索沃',
									'Kuwait': '科威特',
									'Laos': '老挝',
									'Lebanon': '黎巴嫩',
									'Liberia': '利比里亚',
									'Libya': '利比亚',
									'Sri Lanka': '斯里兰卡',
									'Lesotho': '莱索托',
									'Lithuania': '立陶宛',
									'Luxembourg': '卢森堡',
									'Latvia': '拉脱维亚',
									'Morocco': '摩洛哥',
									'Moldova': '摩尔多瓦',
									'Madagascar': '马达加斯加',
									'Mexico': '墨西哥',
									'Macedonia': '马其顿',
									'Mali': '马里',
									'Myanmar': '缅甸',
									'Montenegro': '黑山',
									'Mongolia': '蒙古',
									'Mozambique': '莫桑比克',
									'Mauritania': '毛里塔尼亚',
									'Malawi': '马拉维',
									'Malaysia': '马来西亚',
									'Namibia': '纳米比亚',
									'New Caledonia': '新喀里多尼亚',
									'Niger': '尼日尔',
									'Nigeria': '尼日利亚',
									'Nicaragua': '尼加拉瓜',
									'Netherlands': '荷兰',
									'Norway': '挪威',
									'Nepal': '尼泊尔',
									'New Zealand': '新西兰',
									'Oman': '阿曼',
									'Pakistan': '巴基斯坦',
									'Panama': '巴拿马',
									'Peru': '秘鲁',
									'Philippines': '菲律宾',
									'Papua New Guinea': '巴布亚新几内亚',
									'Poland': '波兰',
									'Puerto Rico': '波多黎各',
									'North Korea': '北朝鲜',
									'Portugal': '葡萄牙',
									'Paraguay': '巴拉圭',
									'Qatar': '卡塔尔',
									'Romania': '罗马尼亚',
									'Russia': '俄罗斯',
									'Rwanda': '卢旺达',
									'Western Sahara': '西撒哈拉',
									'Saudi Arabia': '沙特阿拉伯',
									'Sudan': '苏丹',
									'South Sudan': '南苏丹',
									'Senegal': '塞内加尔',
									'Solomon Islands': '所罗门群岛',
									'Sierra Leone': '塞拉利昂',
									'El Salvador': '萨尔瓦多',
									'Somaliland': '索马里兰',
									'Somalia': '索马里',
									'Republic of Serbia': '塞尔维亚共和国',
									'Suriname': '苏里南',
									'Slovakia': '斯洛伐克',
									'Slovenia': '斯洛文尼亚',
									'Sweden': '瑞典',
									'Swaziland': '斯威士兰',
									'Syria': '叙利亚',
									'Chad': '乍得',
									'Togo': '多哥',
									'Thailand': '泰国',
									'Tajikistan': '塔吉克斯坦',
									'Turkmenistan': '土库曼斯坦',
									'East Timor': '东帝汶',
									'Trinidad and Tobago': '特里尼达和多巴哥',
									'Tunisia': '突尼斯',
									'Turkey': '土耳其',
									'United Republic of Tanzania': '坦桑尼亚联合共和国',
									'Uganda': '乌干达',
									'Ukraine': '乌克兰',
									'Uruguay': '乌拉圭',
									'United States of America': '美国',
									'Uzbekistan': '乌兹别克斯坦',
									'Venezuela': '委内瑞拉',
									'Vietnam': '越南',
									'Vanuatu': '瓦努阿图',
									'West Bank': '西岸',
									'Yemen': '也门',
									'South Africa': '南非',
									'Zambia': '赞比亚',
									'Zimbabwe': '津巴布韦'
								},
								geoCoord: {
									'阿富汗': [69.11,34.28],
									'阿尔巴尼亚': [19.49,41.18],
									'阿尔及利亚': [3.08,36.42],
									'美属萨摩亚': [-170.43,-14.16],
									'安道尔': [1.32,42.31],
									'安哥拉': [13.15,-8.50],
									'安提瓜和巴布达': [-61.48,17.20],
									'阿根廷': [-60.00,-36.30],
									'亚美尼亚': [44.31,40.10],
									'阿鲁巴': [-70.02,12.32],
									'澳大利亚': [149.08,-35.15],
									'奥地利': [16.22,48.12],
									'阿塞拜疆': [49.56,40.29],
									'巴哈马': [-77.20,25.05],
									'巴林': [50.30,26.10],
									'孟加拉国': [90.26,23.43],
									'巴巴多斯': [-59.30,13.05],
									'白俄罗斯': [27.30,53.52],
									'比利时': [4.21,50.51],
									'伯利兹': [-88.30,17.18],
									'贝宁': [2.42,6.23],
									'不丹': [89.45,27.31],
									'玻利维亚': [-68.10,-16.20],
									'波斯尼亚和黑塞哥维那': [18.26,43.52],
									'博茨瓦纳': [25.57,-24.45],
									'巴西': [-47.55,-15.47],
									'英属维尔京群岛': [-64.37,18.27],
									'文莱': [115.00,4.52],
									'保加利亚': [23.20,42.45],
									'布基纳法索': [-1.30,12.15],
									'布隆迪': [29.18,-3.16],
									'柬埔寨': [104.55,11.33],
									'喀麦隆': [11.35,3.50],
									'加拿大': [-75.42,45.27],
									'佛得角': [-23.34,15.02],
									'开曼群岛': [-81.24,19.20],
									'中非共和国': [18.35,4.23],
									'乍得': [14.59,12.10],
									'智利': [-70.40,-33.24],
									'中国大陆': [116.20,39.55],
									'哥伦比亚': [-74.00,4.34],
									'科摩罗': [43.16,-11.40],
									'刚果': [15.12,-4.09],
									'哥斯达黎加': [-84.02,9.55],
									'科特迪瓦': [-5.17,6.49],
									'克罗地亚': [15.58,45.50],
									'古巴': [-82.22,23.08],
									'塞浦路斯': [33.25,35.10],
									'捷克共和国': [14.22,50.05],
									'朝鲜': [125.30,39.09],
									'刚果(扎伊尔)': [15.15,-4.20],
									'丹麦': [12.34,55.41],
									'吉布提': [42.20,11.08],
									'多米尼加': [-61.24,15.20],
									'多米尼加共和国': [-69.59,18.30],
									'东帝汶': [125.34,-8.29],
									'厄瓜多尔': [-78.35,-0.15],
									'埃及': [31.14,30.01],
									'萨尔瓦多': [-89.10,13.40],
									'赤道几内亚': [8.50,3.45],
									'厄立特里亚': [38.55,15.19],
									'爱沙尼亚': [24.48,59.22],
									'埃塞俄比亚': [38.42,9.02],
									'福克兰群岛(马尔维纳斯群岛)': [-59.51,-51.40],
									'法罗群岛': [-6.56,62.05],
									'斐济': [178.30,-18.06],
									'芬兰': [25.03,60.15],
									'法国': [2.20,48.50],
									'法属圭亚那': [-52.18,5.05],
									'法属波利尼西亚': [-149.34,-17.32],
									'加蓬': [9.26,0.25],
									'冈比亚': [-16.40,13.28],
									'格鲁吉亚': [44.50,41.43],
									'德国': [13.25,52.30],
									'加纳': [-0.06,5.35],
									'希腊': [23.46,37.58],
									'格陵兰': [-51.35,64.10],
									'瓜德罗普岛': [-61.44,16.00],
									'危地马拉': [-90.22,14.40],
									'根西岛': [-2.33,49.26],
									'几内亚': [-13.49,9.29],
									'几内亚比绍': [-15.45,11.45],
									'圭亚那': [-58.12,6.50],
									'海地': [-72.20,18.40],
									'赫德岛和麦当劳群岛': [74.00,-53.00],
									'洪都拉斯': [-87.14,14.05],
									'匈牙利': [19.05,47.29],
									'冰岛': [-21.57,64.10],
									'印度': [77.13,28.37],
									'印度尼西亚': [106.49,-6.09],
									'伊朗': [51.30,35.44],
									'伊拉克': [44.30,33.20],
									'爱尔兰': [-6.15,53.21],
									'以色列': [35.12,31.47],
									'意大利': [12.29,41.54],
									'牙买加': [-76.50,18.00],
									'约旦': [35.52,31.57],
									'哈萨克斯坦': [71.30,51.10],
									'肯尼亚': [36.48,-1.17],
									'基里巴斯': [173.00,1.30],
									'科威特': [48.00,29.30],
									'吉尔吉斯斯坦': [74.46,42.54],
									'老挝': [102.36,17.58],
									'拉脱维亚': [24.08,56.53],
									'黎巴嫩': [35.31,33.53],
									'莱索托': [27.30,-29.18],
									'利比里亚': [-10.47,6.18],
									'阿拉伯利比亚民众国': [13.07,32.49],
									'列支敦士登': [9.31,47.08],
									'立陶宛': [25.19,54.38],
									'卢森堡': [6.09,49.37],
									'马达加斯加': [47.31,-18.55],
									'马拉维': [33.48,-14.00],
									'马来西亚': [101.41,3.09],
									'马尔代夫': [73.28,4.00],
									'马里': [-7.55,12.34],
									'马耳他': [14.31,35.54],
									'马提尼克岛': [-61.02,14.36],
									'毛里塔尼亚': [57.30,-20.10],
									'马约特岛': [45.14,-12.48],
									'墨西哥': [-99.10,19.20],
									'密克罗尼西亚(联邦) ': [158.09,6.55],
									'摩尔多瓦共和国': [28.50,47.02],
									'莫桑比克': [32.32,-25.58],
									'缅甸': [96.20,16.45],
									'纳米比亚': [17.04,-22.35],
									'尼泊尔': [85.20,27.45],
									'荷兰': [4.54,52.23],
									'荷属安的列斯': [-69.00,12.05],
									'新喀里多尼亚': [166.30,-22.17],
									'新西兰': [174.46,-41.19],
									'尼加拉瓜': [-86.20,12.06],
									'尼日尔': [2.06,13.27],
									'尼日利亚': [7.32,9.05],
									'诺福克岛': [168.43,-45.20],
									'北马里亚纳群岛': [145.45,15.12],
									'挪威': [10.45,59.55],
									'阿曼': [58.36,23.37],
									'巴基斯坦': [73.10,33.40],
									'帕劳': [134.28,7.20],
									'巴拿马': [-79.25,9.00],
									'巴布亚新几内亚': [147.08,-9.24],
									'巴拉圭': [-57.30,-25.10],
									'秘鲁': [-77.00,-12.00],
									'菲律宾': [121.03,14.40],
									'波兰': [21.00,52.13],
									'葡萄牙': [-9.10,38.42],
									'波多黎各': [-66.07,18.28],
									'卡塔尔': [51.35,25.15],
									'韩国': [126.58,37.31],
									'罗马尼亚': [26.10,44.27],
									'俄罗斯': [37.35,55.45],
									'卢旺达': [30.04,-1.59],
									'圣基茨和尼维斯': [-62.43,17.17],
									'圣卢西亚': [-60.58,14.02],
									'圣皮埃尔和密克隆': [-56.12,46.46],
									'圣文森特和格林纳丁斯': [-61.10,13.10],
									'萨摩亚': [-171.50,-13.50],
									'圣马力诺': [12.30,43.55],
									'圣多美和普林西比': [6.39,0.10],
									'沙特阿拉伯': [46.42,24.41],
									'塞内加尔': [-17.29,14.34],
									'塞拉利昂': [-13.17,8.30],
									'斯洛伐克': [17.07,48.10],
									'斯洛文尼亚': [14.33,46.04],
									'所罗门群岛': [159.57,-9.27],
									'索马里': [45.25,2.02],
									'比勒陀利亚': [28.12,-25.44],
									'西班牙': [-3.45,40.25],
									'苏丹': [32.35,15.31],
									'苏里南': [-55.10,5.50],
									'斯威士兰': [31.06,-26.18],
									'瑞典': [18.03,59.20],
									'瑞士': [7.28,46.57],
									'阿拉伯叙利亚共和国': [36.18,33.30],
									'塔吉克斯坦': [68.48,38.33],
									'泰国': [100.35,13.45],
									'马其顿': [21.26,42.01],
									'多哥': [1.20,6.09],
									'汤加': [-174.00,-21.10],
									'突尼斯': [10.11,36.50],
									'土耳其': [32.54,39.57],
									'土库曼斯坦': [57.50,38.00],
									'图瓦卢': [179.13,-8.31],
									'乌干达': [32.30,0.20],
									'乌克兰': [30.28,50.30],
									'阿联酋': [54.22,24.28],
									'英国': [-0.05,51.36],
									'坦桑尼亚': [35.45,-6.08],
									'美国': [-77.02,39.91],
									'美属维尔京群岛': [-64.56,18.21],
									'乌拉圭': [-56.11,-34.50],
									'乌兹别克斯坦': [69.10,41.20],
									'瓦努阿图': [168.18,-17.45],
									'委内瑞拉': [-66.55,10.30],
									'越南': [105.55,21.05],
									'南斯拉夫': [20.37,44.50],
									'赞比亚': [28.16,-15.28],
									'津巴布韦': [31.02,-17.43],
									'台湾':[120.58,23.58],
						}
					},
				{
					name: '入境',
					type: 'map',
					mapType: 'world',
					data:[],
					markLine : {
						smooth:true,
						effect : {
							show: true,
							scaleSize: 1,
							period: 30,
							color: '#fff',
							shadowBlur: 10
						},
						itemStyle : {
							normal: {
								label:{
									show:false
								},
								borderWidth:1,
								lineStyle: {
									type: 'solid',
									shadowBlur: 10
								},
							},
						},
						data :  lineData,
					},
					markPoint : {
						symbol:'emptyCircle',
						symbolSize : function (v){
							return 10 + v/10
						},
						effect : {
							show: true,
							shadowBlur : 0
						},
						itemStyle:{
							normal:{
								label:{show:false}
							},
							emphasis: {
								label:{position:'top'}
							}
						},
						data : pointData,
					}
				}
			]
		};
	    // to set world map
	    myChart.setOption(option, true);
	    var ecConfig = require('echarts/config');
	    myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param) {
            setTotals(encode(param.target));
	    });
	});
}
