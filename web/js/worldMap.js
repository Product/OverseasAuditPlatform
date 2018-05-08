// 路径配置
require.config({
    paths: {
        echarts: 'build/dist/echarts',
        'echarts/chart/map': 'build/dist/chart/map'
    }
});

$(function () {
    //加载商品类型  firstlevelType
    $.ajax({
        type: "post",
        url: "./servlet/DetectingSuggestServlet?methodName=productTypeInit&parentId=",
        dataType: "text",
        async: false,
        success: function (r) {
            if (r != "false") {
                var data = $.parseJSON(r);
                var str = "<option value='0'>请选择</option>";
                for (var i = 0; i < data.StyleList.length; i++) {
                    str += "<option value='" + data.StyleList[i].Id + "'>" + data.StyleList[i].Name + "</option>";
                }
                $("#firstSel").html("");
                $("#firstSel").append(str);
            }
            else if (r == "sessionOut") {
                indexDoLogout();
            }
            else {
               
            }
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
})

function info_back () {
    $("#information_publish").show();//信息发布展示
    $("#information_detail").hide();//信息发布展示
}
//搜索按钮点击事件
function queryData() {
    setTotals();
    var view = $('#hid_viewShow').val();
    var pageStatus = $("#hid_pageStatus").val();
    if(view == '0'){
        if (pageStatus == "0") {//监管情况纵览
        	RegulatoryMapInit();
        } else if (pageStatus == "1") {//样品检测建议
            DetectingSuggestMapInit();
        } else if (pageStatus == "2") {//疫情影响纵览
        	EpidemicAffectedMapInit();
        } else if (pageStatus == "3") {//事件影响纵览
            EventAffectedMapInit();
            if($("#hid_eventAffect").val() =="eventAffect"){
                initEventsInfo();
                $('#EventAffectSearch').hide();
                $('#EventExactInfo').show();
                $("#hid_eventAffect").val("eventShow");
            }
        }
    }else if(view == '1'){
        viewExcelFormDatum();
    }
}

//导出按钮点击事件
function queryImport() {
	 var pageStatus = $("#hid_pageStatus").val();
	    if (pageStatus == "0") {//监管情况纵览
	    
	    } else if (pageStatus == "1") {//样品检测建议
	    	DetectingSuggestImport();
	    } else if (pageStatus == "2") {//疫情影响纵览
	    	EpidemicAffectedMapImport();
	    } else if (pageStatus == "3") {//事件影响纵览
	    	EventAffectedMapImport();
	    }
}
                
function mapLoading(strTitle, strCountryNum) {
    // 使用
    require(
            [
                'echarts',
                'echarts/chart/map' // 使用柱状图就加载bar模块，按需加载
            ],
			function (ec) {
			    myChart = ec.init(document.getElementById('main'));

			    // world map option       
			    var option = {
			        title: {
			            text: '全球分布概况',
			            subtext: 'Global distribution profile',
			            x: 'center',
			            y: 'top'
			        },
			        toolbox: {
			            show: true,
			            orient: 'vertical',
			            x: 'right',
			            y: 'center',
			            feature: {
			                mark: { show: true },
			                dataView: { show: true, readOnly: false },
			                restore: { show: true },
			                saveAsImage: { show: true }
			            }
			        },
			        dataRange: {
			            min: 0,
			            max: 100,
			            text: ['High', 'Low'],
			            realtime: false,
			            calculable: true,
			            color: ['orangered', 'yellow', 'lightskyblue']
			        },
			        tooltip: {
			            trigger: 'item',
			            formatter: function (params) {
			                var disName = "";
			                for (var i = 0; i < strTitle.length; i++) {
			                    if (strTitle[i].name == params.name) {
			                    	
			                        disName = strTitle[i].title;
			                    }
			                }
			                
			                var pageStatus = $("#hid_pageStatus").val();
			                if (pageStatus == "0") {//监管情况纵览
			                    if ($('#hid_subModuleInfo').val() == "product") {
			                        return params.name + '<br/>该地区商品数量' + ' : ' + params.value + '<br/>' + disName;
			                    }
			                    else if ($('#hid_subModuleInfo').val() == "website") {
			                        return params.name + '<br/>该地区网站数量' + ' : ' + params.value + '<br/>' + disName;
			                    }
			                    else if ($('#hid_subModuleInfo').val() == "brand") {
			                        return params.name + '<br/>该地区品牌数量' + ' : ' + params.value + '<br/>' + disName;
			                    }
			                    else if($('#hid_subModuleInfo').val() == "productDefine"){
			                        return params.name + '<br/>该地区产品数量' + ' : ' + params.value + '<br/>' + disName;
			                    }
                                else if($('#hid_subModuleInfo').val() == "productType"){
                                    return params.name + '<br/>该地区产品类别数量' + ' : ' + params.value + '<br/>' + disName;
                                }
			                } else if (pageStatus == "1") {//样品检测建议
			                        return params.name + '<br/>该地区商品数量' + ' : ' + params.value + '<br/>' + disName;
			                } else if (pageStatus == "2") {//疫情影响纵览
								if ($('#hid_subModuleInfo').val() == "product") {
									return params.name + '<br/>该地区商品数量' + ' : '
											+ params.value + '<br/>' + disName;
								} else if ($('#hid_subModuleInfo').val() == "website") {
									return params.name + '<br/>该地区网站数量' + ' : '
											+ params.value + '<br/>' + disName;
								} else if ($('#hid_subModuleInfo').val() == "brand") {
									return params.name + '<br/>该地区品牌数量' + ' : '
											+ params.value + '<br/>' + disName;
								} else {
									return params.name + disName;
								}
			                } else if (pageStatus == "3") {//事件影响纵览
								if ($('#hid_subModuleInfo').val() == "product") {
									return params.name + '<br/>该地区商品数量' + ' : '
											+ params.value + '<br/>' + disName;
								} else if ($('#hid_subModuleInfo').val() == "website") {
									return params.name + '<br/>该地区网站数量' + ' : '
											+ params.value + '<br/>' + disName;
								} else if ($('#hid_subModuleInfo').val() == "brand") {
									return params.name + '<br/>该地区品牌数量' + ' : '
											+ params.value + '<br/>' + disName;
								} else {
									return params.name + disName;
								}
							}
			            }
			        },
			        series: [
        {
            type: 'map',
            mapType: 'world',
            roam: true,
            selectedMode: 'single',
            mapLocation: {
                y: 60
            },
            itemStyle: {
                normal: { label: { show: false} },
                emphasis: { label: { show: true} }
            },
            data: strCountryNum
                ,
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
                'Albania': '阿尔巴尼亚',
                'United Arab Emirates': '阿联酋',
                'Argentina': '阿根廷',
                'Armenia': '亚美尼亚',
                'French Southern and Antarctic Lands': '法属南半球和南极领地',
                'Australia': '澳大利亚',
                'Austria': '奥地利',
                'Azerbaijan': '阿塞拜疆',
                'Burundi': '布隆迪',
                'Belgium': '比利时',
                'Benin': '贝宁',
                'Burkina Faso': '布基纳法索',
                'Bangladesh': '孟加拉国',
                'Bulgaria': '保加利亚',
                'The Bahamas': '巴哈马',
                'Bosnia and Herzegovina': '波斯尼亚和黑塞哥维那',
                'Belarus': '白俄罗斯',
                'Belize': '伯利兹',
                'Bermuda': '百慕大',
                'Bolivia': '玻利维亚',
                'Ivory Coast': '象牙海岸',
                'Cameroon': '喀麦隆',
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
                'Fiji': '斐',
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

			    // to return back world map
			    $("#backWorld").click(function () {
			        myChart.setOption(option, true);
			    });
			});

}

// to get json lenght
function getJsonLength(jsonData) {
    var jsonLength = 0;
    for (var item in jsonData) {
        jsonLength++;
    }
    return jsonLength;
}