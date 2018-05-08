$(function () {
    var chart_FactorySum = new FusionCharts("js/FusionCharts/Pie3D.swf", "FactorySum", "650", "450", "1", "1");
    chart_FactorySum.setDataXML("<graph caption='2015-01至2015-04XXX报表' subCaption='' decimalPrecision='0' showNames='1' numberSuffix='' pieSliceDepth='30' formatNumberScale='0'><set name='2015-01' value='24' color='AFD8F8'/><set name='2015-02' value='44' color='F6BD0F'/><set name='2015-03' value='54' color='8BBA00'/><set name='2015-04' value='176' color='A66EDD'/></graph>");
    chart_FactorySum.render("FactorySumDiv");
});
