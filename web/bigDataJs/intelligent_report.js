// echar图形路径配置
require.config({
    paths: {
        echarts: 'build/dist'
    }
});

$(function(){
    $("#time_month").hide();
    $("#time_day").show();
});

function creatChart() {
    var dataSource = $('input:radio[name="dataSource"]:checked').val();
    var chartType = $('input:radio[name="chartType"]:checked').val();
    var number = $('input:radio[name="number"]:checked').val();
    var startTime = $('#start_time').val();
    var endTime = $('#end_time').val();
    if (startTime == "") {
        alertText("请输入开始日期！", 3500);
        return;
    }
    if (endTime == "") {
        alertText("请输入结束日期！", 3500);
        return ;
    }
    if (!checkEndTime()) {
        alertText("结束时间必须大于开始时间！", 3500);
        return;
    }
    $('#reportForm').hide();
    $("#lineChart").show();
    var chartTypeValue;
    switch(chartType) {
        case "pie":
            chartTypeValue = 2;
            break;
        default :
            chartTypeValue = 1;
            break;
    }
    var title;
    switch(dataSource) {
        case "1":
            title = "各国家商品风险统计";
            break;
        case "2":
            title = "各电商平台商品风险统计";
            break;
        case "3":
            title = "各品类商品风险统计";
            break;
        default :
            title = "各品牌商品风险统计";
            break;
    }
    $.ajax({
        type: "post",
        url: projectLocation + "servlet/MismatchProductServlet?methodName=MismatchForm&dataSource="+ encode(dataSource) +
        "&chartType="+ chartTypeValue +"&number="+ number +"&startTime="+ startTime +
        "&endTime="+ endTime,
        dataType: "json",
        async: false,
        success: function (r) {
            if (r == "false") {
                alertText("保存失败！", 3500);
            }
            else if(r =="sessionOut"){
                doLogout();
            }else {
                //alertText("保存成功！", 3500);
                //var data = $.parseJSON(r);
                $('#reportForm').hide();
                if (chartType=="line") {
                    $("#lineChart").show();
                    $("#barChart").hide();
                    $("#pieChart").hide();
                    lineChart(r,title);
                }
                else if(chartType=="bar") {
                    $("#barChart").show();
                    $("#lineChart").hide();
                    $("#pieChart").hide();
                   barChart(r,title);
                }
                else if(chartType=="pie") {
                    $("#pieChart").show();
                    $("#barChart").hide();
                    $("#lineChart").hide();
                    pieChart(r,title);
                }
                //UpdateInfo(data);
            }
        },
        error: function (e) {
            alertText(e.responseText, 3500);
            // alert(e.responseText);
        }
    });
}

function timeTypeChange() {
    var timeType = $('input:radio[name="timeType"]:checked').val();
    if (timeType == "month") {
        $("#time_month").show();
        $("#time_day").hide();
    }
    else if (timeType == "day") {
        $("#time_day").show();
        $("#time_month").hide();
    }

}

function checkEndTime(){
    var startTime=$("#start_time").val();
    var start=new Date(startTime.replace("-", "/").replace("-", "/"));
    var endTime=$("#end_time").val();
    var end=new Date(endTime.replace("-", "/").replace("-", "/"));
    if(end<start){
        return false;
    }
    return true;
}

//折线图
function lineChart(source,title) {
    var xData = [];
    var legendData = [];
    var tempData = [];
    for (var j=0; j < source[0].result.length; j++) {
        legendData.push(source[0].result[j].item);
        var subData = {};
        subData.name = source[0].result[j].item;
        subData.type = "line";
        subData.data = [];
        tempData.push(subData);
    }

    for (var i= 0; i < source.length; i++) {
        xData.push(source[i].date);
        for (var k=0; k < source[i].result.length; k++) {
            for(var w=0;w < tempData.length;w++) {
                if (source[i].result[k].item == tempData[w].name) {
                    tempData[w].data.push(source[i].result[k].result);
                }
            }
        }
    }

    if (tempData.length == 0) {
        $("#line_data").html("暂无数据！");
        return;
    }

    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            var myChart = ec.init(document.getElementById('line_data'));
            var option = {
                title: {
                    text: title
                },
                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>时间：{b}<br/>风险商品数量：{c}"
                },
                legend: {
                    x: 'right',
                    data: legendData
                },
                xAxis: [
                    {
                        type: "category",
                        name: "时间",
                        splitLine: { show: false },
                        data: xData
                    }
                ],
                yAxis: [
                    {
                        type: "value",
                        name: "风险商品数量"
                    }
                ],
                series: tempData
            };
            myChart.setOption(option);
        });
}

//柱状图
function barChart(source,title) {
    var xData = [];
    var legendData = [];
    var tempData = [];
    for (var j=0; j < source[0].result.length; j++) {
        legendData.push(source[0].result[j].item);
        var subData = {};
        subData.name = source[0].result[j].item;
        subData.type = "bar";
        subData.data = [];
        tempData.push(subData);
    }

    for (var i= 0; i < source.length; i++) {
        xData.push(source[i].date);
        for (var k=0; k < source[i].result.length; k++) {
            for(var w=0;w < tempData.length;w++) {
                if (source[i].result[k].item == tempData[w].name) {
                    tempData[w].data.push(source[i].result[k].result);
                }
            }
        }
    }

    if (tempData.length == 0) {
        $("#bar_data").html("暂无数据！");
        return;
    }
    require(
        [
            'echarts',
            'echarts/chart/bar'
        ],
        function (ec) {
            var myChart = ec.init(document.getElementById('bar_data'));
            var option = {
                title: {
                    text: title
                },
                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>时间：{b}<br/>风险商品数量：{c}"
                },
                legend: {
                    x: 'right',
                    data: legendData
                },
                xAxis: [
                    {
                        type: "category",
                        name: "时间",
                        splitLine: { show: false },
                        data: xData
                    }
                ],
                yAxis: [
                    {
                        type: "value",
                        name: "风险商品数量"
                    }
                ],
                series: tempData
            };
            myChart.setOption(option);
        });
}

//饼图
function pieChart(source,title) {
    var legendData = [];
    var tempData = [];
    source.pop();
    for (var j=0; j < source.length; j++) {
        legendData.push(source[j].name);
    }
    var subData = {};
    subData.name = "风险商品数量";
    subData.type = "pie";
    subData.radius = "55%";
    subData.center = ['50%', '60%'];
    subData.data = source;
    tempData.push(subData);

    if (tempData.length == 0) {
        $("#pie_data").html("暂无数据！");
        return;
    }

    require(
        [
            'echarts',
            'echarts/chart/pie'
        ],
        function (ec) {
            var myChart = ec.init(document.getElementById('pie_data'));
            var option = {
                title: {
                    text: title,
                    x:"center"
                },
                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>{b} : {c} ({d}%"
                },
                legend: {
                    x: 'right',
                    data: legendData
                },

                series: tempData
            };
            myChart.setOption(option);
        });
}