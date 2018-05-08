package com.tonik.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.Constant;
import com.tonik.ExportExcel;
import com.tonik.model.Event;
import com.tonik.service.EventAffectedMapService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响Map类别 servlet
 * </p>
 * @since Sep 04, 2015
 * @version 1.0
 * @author liuyu
 * @web.servlet name="eventAffectedMapServlet"
 * @web.servlet-mapping url-pattern="/servlet/EventAffectedMapServlet"
 */
public class EventAffectedMapServlet extends BaseServlet
{
    private EventAffectedMapService eventAffectedMapService;
    private SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式
    private ExportExcel excel = new ExportExcel();


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        eventAffectedMapService = (EventAffectedMapService) ctx.getBean("eventAffectedMapService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        /*
         * if(this.sessionCheck(request, response)) { response.getWriter().write("sessionOut"); return; }
         */
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("select".equals(methodName))
                {
                    Long[] eventIds;
                    Integer form = Integer.parseInt(request.getParameter("form"));
                    if(form == 1){
                        String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                        String strType = URLDecoder.decode(request.getParameter("strEventType"), "UTF-8");
                        Long eventTypeId = null;
                        if (!strType.equals(""))
                        {
                            eventTypeId = Long.parseLong(strType);
                        }
                        // 更改时间格式
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                        String strStartTime = request.getParameter("strStartTime");
                        String strEndTime = request.getParameter("strEndTime");
    
                        if (!strStartTime.equals(""))
                        {
                            strStartTime = dateFormater.format(format.parse(strStartTime));
                        }
                        if (!strEndTime.equals(""))
                        {
                            strEndTime = dateFormater.format(format.parse(strEndTime));
                        }
    
                        int strDays = Integer.parseInt(request.getParameter("strDays"));
    
                        Date date = new Date();
    
                        if ("".equals(strEndTime))
                        {
                            strEndTime = dateFormater.format(date);
                        }
                        if (strDays != 0)
                        {
                            Calendar c = Calendar.getInstance();
    
                            c.setTime(date);
                            c.add(Calendar.DAY_OF_YEAR, 0 - strDays);
                            strStartTime = dateFormater.format(c.getTime());
                        }
                        else if ("".equals(strStartTime))
                        {
                            strStartTime = "1980-01-01 00:00:01";
                        }
    
                        // 获取符合条件的所有事件
                        List<Event> events = eventAffectedMapService.getEvent(strQuery, eventTypeId, strStartTime,
                                strEndTime);
                        eventIds = new Long[events.size()];
    
                        int i = 0;
                        for (Event event : events)
                        {
                            eventIds[i++] = event.getId();
                        }
                    }else{
                        Integer len = Integer.parseInt(request.getParameter("clen"));
                        eventIds = new Long[len];
                        int i = 0;
                        if(len != 0){
                            for (String item : (request.getParameter("choices")).split("\\|"))
                            { 
                                eventIds[i++] = Long.parseLong(item);
                            }
                        }
                    }
                    // 未找到对应事件
                    if (eventIds.length <= 0)
                    {
                        response.getWriter()
                                .write("{\"brandTotal\":\"" + 0 + "\",\"productTotal\":\"" + 0
                                        + "\",\"productTypeTotal\":\"" + 0 + "\",\"websiteTotal\":\"" + 0 + "\",\"productDefinitonTotal\":\"" + 0
                                        + "\",\"countryList\":[]" + ",\"eventCNVList\":[]" + ",\"eventCNTList\":[]}");
                    }
                    else
                    {
                        

                        // 获取影响的品牌，商品种类，网站总数
                        int brandTotal = eventAffectedMapService.getEventAffectedBrandsTotal(eventIds);
                        int websiteTotal = eventAffectedMapService.getEventAffectedWebsitesTotal(eventIds);
                        int productTypeTotal = eventAffectedMapService.getEventAffectedProductTypesTotal(eventIds);
                        int productTotal = eventAffectedMapService.getEventAffectedProductsTotal(eventIds);
                        int productDefinitionTotal = eventAffectedMapService.getEventAffectedProductDefinitionTotal(eventIds);
                        
                        String strJson = "";

                        String strCNVList = "";
                        String strCNTList = "";

                        String selectType = request.getParameter("selectType");
                    
                        // 获取影响的品牌分布
                        if ("brand".equals(selectType))
                        {
                            List<Object[]> brandTotalList = eventAffectedMapService.getWorldMapBrandTotal(eventIds);
                            List<Object[]> brandList = eventAffectedMapService.getBrandListByEventsId(eventIds);
                            String title = "影响品牌：";
                            String[] returnList = getReturnList(brandTotalList, brandList, title)
                                    .split("<>", 2);
                            strCNVList = returnList[0];
                            strCNTList = returnList[1];
                        }
                        // 获得影响的商品类别分布
                        else if ("productType".equals(selectType))
                        {
                            List<Object[]> ptl = eventAffectedMapService.getWorldMapProductTypeInfo(eventIds);

                            for (Object[] obj : ptl)
                            {

                                strCNVList += "{\"name\":\"" + obj[1] + "\",\"value\":\"" + obj[4] + "\"},";
                            }

                            String OldName = "";

                            String title = "";
                            if (ptl.size() != 0)
                            {

                                OldName = (String) ptl.get(0)[1];
                                title = "主产类别Top10：";
                            }
                            int j = 0;
                            for (Object[] obj : ptl)
                            {
                                if (obj[1] == null)
                                    continue;

                                if (obj[1].equals(OldName) && j < 10)
                                {
                                    title += obj[3] + ",";
                                    j++;
                                    if (j % 3 == 0)
                                        title += "<br/>";
                                    if (obj.equals(ptl.get(ptl.size() - 1)))
                                    {
                                        title = title.substring(0, title.length() - 1);
                                        strCNTList += "{\"name\":\"" + OldName + "\",\"title\":\"" + title + "\"},";
                                    }
                                }
                                else if (obj[1].equals(OldName) && j == 10)
                                {
                                    title = title.substring(0, title.length() - 1);
                                    strCNTList += "{\"name\":\"" + OldName + "\",\"title\":\"" + title + "\"},";
                                    j++;
                                    continue;
                                }
                                else if (obj[1].equals(OldName) && j > 10)
                                {
                                    j++;
                                    continue;
                                }
                                else
                                {
                                    title = title.substring(0, title.length() - 1);
                                    strCNTList += "{\"name\":\"" + OldName + "\",\"title\":\"" + title + "\"},";

                                    title = "主产类别Top10：" + obj[3] + ",";
                                    j = 1;
                                    OldName = (String) obj[1];
                                    if (obj.equals(ptl.get(ptl.size() - 1)))
                                    {
                                        title = title.substring(0, title.length() - 1);
                                        strCNTList += "{\"name\":\"" + OldName + "\",\"title\":\"" + title + "\"},";
                                    }
                                }
                            }
                        }
                        // 获得影响的商品分布
                        else if ("product".equals(selectType))
                        {
                            int clength = Integer.parseInt(request.getParameter("choiceslength"));
                            Long[] ptl = new Long[clength];
                            int j = 0;

                            if (clength > 0)
                            {
                                for (String item : (request.getParameter("choices")).split("<>"))
                                {
                                    String[] itemArray = item.split("\\|");

                                    Long pt1 = Long.parseLong(itemArray[0]);
                                    Long pt2 = Long.parseLong(itemArray[1]);
                                    Long pt3 = Long.parseLong(itemArray[2]);

                                    if (pt3 != 0)
                                    {
                                        ptl[j++] = pt3;
                                    }
                                    else if (pt2 != 0)
                                    {
                                        ptl[j++] = pt2;
                                    }
                                    else if (pt1 != 0)
                                    {
                                        ptl[j++] = pt1;
                                    }
                                }
                            }

                            List<Object[]> productTotalList = eventAffectedMapService.getWorldMapProductTotal(eventIds,
                                    ptl);
                            List<Object[]> productList = eventAffectedMapService.getProductListByEventsId(eventIds,
                                    ptl);

                            String title = "代表商品：";
                            String[] returnList = getReturnList(productTotalList, productList, title)
                                    .split("<>", 2);
                            strCNVList = returnList[0];
                            strCNTList = returnList[1];
                        }
                     // 获得影响的产品分布
                        else if ("productDefine".equals(selectType))
                        {
                            int clength = Integer.parseInt(request.getParameter("choiceslength"));
                            Long[] ptl = new Long[clength];
                            int j = 0;

                            if (clength > 0)
                            {
                                for (String item : (request.getParameter("choices")).split("<>"))
                                {
                                    String[] itemArray = item.split("\\|");

                                    Long pt1 = Long.parseLong(itemArray[0]);
                                    Long pt2 = Long.parseLong(itemArray[1]);
                                    Long pt3 = Long.parseLong(itemArray[2]);

                                    if (pt3 != 0)
                                    {
                                        ptl[j++] = pt3;
                                    }
                                    else if (pt2 != 0)
                                    {
                                        ptl[j++] = pt2;
                                    }
                                    else if (pt1 != 0)
                                    {
                                        ptl[j++] = pt1;
                                    }
                                }
                            }

                            List<Object[]> productTotalList = eventAffectedMapService.getWorldMapProductDefinitionTotal(eventIds,
                                    ptl);
                            List<Object[]> productList = eventAffectedMapService.getProductDefinitionListByEventsId(eventIds,
                                    ptl);

                            String title = "代表产品：";
                            String[] returnList = getReturnList(productTotalList, productList, title)
                                    .split("<>", 2);
                            strCNVList = returnList[0];
                            strCNTList = returnList[1];
                        }
                        // 获得影响的网站分布
                        else if ("website".equals(selectType))
                        {
                            List<Object[]> websiteTotalList = eventAffectedMapService.getWorldMapWebsiteTotal(eventIds);
                            List<Object[]> websiteList = eventAffectedMapService.getWebsiteListByEventsId(eventIds);
                            String title = "代表网站：";
                            String[] returnList = getReturnList(websiteTotalList, websiteList, title)
                                    .split("<>", 2);
                            strCNVList = returnList[0];
                            strCNTList = returnList[1];
                        }
                        if (strCNVList.length() > 0)
                            strCNVList = strCNVList.substring(0, strCNVList.length() - 1);
                        if (strCNTList.length() > 0)
                            strCNTList = strCNTList.substring(0, strCNTList.length() - 1);

                        response.getWriter()
                                .write("{\"brandTotal\":\"" + brandTotal + "\",\"productTotal\":\"" + productTotal
                                        + "\",\"productTypeTotal\":\"" + productTypeTotal + "\",\"productDefinitonTotal\":\""
                                        + productDefinitionTotal + "\",\"websiteTotal\":\""
                                        + websiteTotal + "\",\"countryList\":[" + strJson + "]" + ",\"eventCNVList\":["
                                        + strCNVList + "]" + ",\"eventCNTList\":[" + strCNTList + "]}");
                    }
                }
                else if("eventsInfo".equals(methodName)){
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                    String strType = URLDecoder.decode(request.getParameter("strEventType"), "UTF-8");
                    Long eventType = null;
                    if (!strType.equals(""))
                    {
                        eventType = Long.parseLong(strType);
                    }

                    // 更改时间格式
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    String strStartTime = request.getParameter("strStartTime");
                    String strEndTime = request.getParameter("strEndTime");

                    if (!strStartTime.equals(""))
                    {
                        strStartTime = dateFormater.format(format.parse(strStartTime));
                    }
                    if (!strEndTime.equals(""))
                    {
                        strEndTime = dateFormater.format(format.parse(strEndTime));
                    }

                    int strDays = Integer.parseInt(request.getParameter("strDays"));

                    Date date = new Date();

                    if ("".equals(strEndTime))
                    {
                        strEndTime = dateFormater.format(date);
                    }
                    if (strDays != 0)
                    {
                        Calendar c = Calendar.getInstance();

                        c.setTime(date);
                        c.add(Calendar.DAY_OF_YEAR, 0 - strDays);
                        strStartTime = dateFormater.format(c.getTime());
                    }
                    else if ("".equals(strStartTime))
                    {
                        strStartTime = "1980-01-01 00:00:01";
                    }
                    Long pt = (long) -1;
                    String ptl = "";
                    if((request.getParameter("choices")) != ""){
                        for (String item : ((request.getParameter("choices")).split("<>")))
                        {
                            String[] itemArray = item.split("\\|");
                            
                            Long pt1 =Long.parseLong(itemArray[0]);
                            Long pt2 =Long.parseLong(itemArray[1]);
                            Long pt3 =Long.parseLong(itemArray[2]);
                            
                            if(pt1 != 0 && pt2 == 0 && pt3 == 0){
                                pt = pt1;
                            }  
                            else if (pt1!=0 && pt2!=0 && pt3 == 0){
                                pt = pt2;
                            }
                            else if (pt1 != 0 && pt2!=0 && pt3 != 0){
                                pt = pt3;
                            }
                            if(pt != -1)
                                ptl += "'"+pt+"',"; 
                        }
                    }
                    if(ptl.length() > 0)
                        ptl = ptl.substring(0, ptl.length()-1);
                    // 获取符合条件的所有事件
                    List<Object[]> events = eventAffectedMapService.getEventsInfo(strQuery, eventType, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStartTime),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime), ptl);
                    String str = "";
                    for(Object[] ds:events){
                        str += eventAffectedMapService.getEventJsonInfo(ds)+",";
                    }
                    if (str.length() > 0){
                        str = str.substring(0, str.length() - 1);
                    }
                    response.getWriter().print("{\"recordsTotal\":\""+events.size()+"\",\"recordsFiltered\":\""+events.size()+"\",\"data\":[" + str + "]}");
                    
                }
                else if("eventDriveExcel".equals(methodName)){
                    Integer len = Integer.parseInt(request.getParameter("choiceslength"));
                    Long[] eventIds = new Long[len];
                    int i = 0;
                    if(len != 0){
                        for (String item : (request.getParameter("choices")).split("\\|"))
                        { 
                            eventIds[i++] = Long.parseLong(item);
                        }
                    }
                    if (eventIds.length == 0)
                    {
                        response.getWriter().write("{\"webList\":[]}");
                    }else{
                        String str = "";
                        List<Object[]> dss = eventAffectedMapService.getEventAffectedList(eventIds, new Long[0]);
                        for(Object[] ds:dss){
                            str += eventAffectedMapService.getEventAffectedMapJsonInfo(ds)+",";
                        }
                        if (str.length() > 0){
                            str = str.substring(0, str.length() - 1);
                        }
                        response.getWriter().print("{\"webList\":[" + str + "]}");
                    }
                }
                // 导出Excel表格
                else if ("exportExcel".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                    String strType = URLDecoder.decode(request.getParameter("strEventType"), "UTF-8");
                    Long eventType = null;
                    if (!strType.equals(""))
                    {
                        eventType = Long.parseLong(strType);
                    }

                    // 更改时间格式
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    String strStartTime = request.getParameter("strStartTime");
                    String strEndTime = request.getParameter("strEndTime");

                    if (!strStartTime.equals(""))
                    {
                        strStartTime = dateFormater.format(format.parse(strStartTime));
                    }
                    if (!strEndTime.equals(""))
                    {
                        strEndTime = dateFormater.format(format.parse(strEndTime));
                    }

                    int strDays = Integer.parseInt(request.getParameter("strDays"));

                    Date date = new Date();

                    if ("".equals(strEndTime))
                    {
                        strEndTime = dateFormater.format(date);
                    }
                    if (strDays != 0)
                    {
                        Calendar c = Calendar.getInstance();

                        c.setTime(date);
                        c.add(Calendar.DAY_OF_YEAR, 0 - strDays);
                        strStartTime = dateFormater.format(c.getTime());
                    }
                    else if ("".equals(strStartTime))
                    {
                        strStartTime = "1980-01-01 00:00:01";
                    }

                    // 获取符合条件的所有事件
                    List<Event> events = eventAffectedMapService.getEvent(strQuery, eventType, strStartTime,
                            strEndTime);
                    int form = Integer.parseInt(request.getParameter("viewForm"));
                    // 未找到对应事件
                    if (events.size() == 0)
                    {
                        if(form == 1){
                            response.getWriter().write("未找到对应事件！");
                        }else if(form == 2){
                            response.getWriter().print("{\"webList\":[]}");
                        }
                    }
                    else
                    {
                        Long[] eventIds = new Long[events.size()];

                        int i = 0;
                        for (Event event : events)
                        {
                            eventIds[i++] = event.getId();
                        }

                        int clength = Integer.parseInt(request.getParameter("choiceslength"));
                        Long[] productTypes = new Long[clength];
                        int j = 0;

                        if (request.getParameter("choices") != "")
                        {
                            for (String item : (request.getParameter("choices")).split("<>"))
                            {
                                String[] itemArray = item.split("\\|");

                                Long pt1 = Long.parseLong(itemArray[0]);
                                Long pt2 = Long.parseLong(itemArray[1]);
                                Long pt3 = Long.parseLong(itemArray[2]);

                                if (pt3 != 0)
                                {
                                    productTypes[j++] = pt3;
                                }
                                else if (pt2 != 0)
                                {
                                    productTypes[j++] = pt2;
                                }
                                else if (pt1 != 0)
                                {
                                    productTypes[j++] = pt1;
                                }
                            }
                        }
                        
                        //excel形式输出显示
                        if(form == 1){
                            response.setHeader("Content-Disposition",
                                    "inline;filename=" + new String("事件影响纵览清单".getBytes("gb2312"), "ISO8859-1") + ".xls");
                            response.setContentType("application/octet-stream");
                            String[] headers = new String[] { "商品名称", "来源网站", "网站路径" };
                            OutputStream os = response.getOutputStream();
                            HSSFWorkbook workbook = excel.exportExcel("事件影响纵览清单", headers,
                                    eventAffectedMapService.getEventAffectedList(eventIds, productTypes));
                            workbook.write(os);
                            os.close();
                        }
                        //表格形式显示
                        else if(form == 2){
                            String str = "";
                            List<Object[]> dss = eventAffectedMapService.getEventAffectedList(eventIds, productTypes);
                            for(Object[] ds:dss){
                                str += eventAffectedMapService.getEventAffectedMapJsonInfo(ds)+",";
                            }
                            if (str.length() > 0){
                                str = str.substring(0, str.length() - 1);
                            }
                            response.getWriter().print("{\"webList\":[" + str + "]}");
                        }
                    }
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
    
    /**
     * @desc 将World Map分布信息转换为JSON字符串
     * @param worldMapValueList World Map各地区的数量分布
     * @param worldMapTitleList World Map各地区的名称分布
     * @param hint 提示信息
     * @return JSON字符串
     */
    private String getReturnList(List<Object[]> worldMapValueList, List<Object[]> worldMapTitleList, String hint)
    {
        String strCNVList = "";
        String strCNTList = "";
        for (Object[] obj : worldMapValueList)
        {
            strCNVList += "{\"name\":\"" + obj[0] + "\",\"value\":\"" + obj[1] + "\"},";
        }

        String OldName = "";
        int i = 0;
        String title = "";
        if (worldMapTitleList.size() != 0)
        {
            OldName = (String) worldMapTitleList.get(0)[0];
            title = hint;
        }

        for (Object[] obj : worldMapTitleList)
        {
            if (obj[0] == null)
                continue;
            if (obj[0].equals(OldName) && i < 10)
            {
                title += obj[1] + ",";
                i++;
                if (i % 3 == 0)
                {
                    title += "<br/>";
                }
                if (obj.equals(worldMapTitleList.get(worldMapTitleList.size() - 1)))
                {
                    title = title.substring(0, title.length() - 1);
                    strCNTList += "{\"name\":\"" + OldName + "\",\"title\":\"" + title + "\"},";
                }
            }
            else if (obj[0].equals(OldName) && i == 10)
            {
                title = title.substring(0, title.length() - 1);
                strCNTList += "{\"name\":\"" + OldName + "\",\"title\":\"" + title + "\"},";
                i++;
                continue;
            }
            else if (obj[0].equals(OldName) && i > 10)
            {
                i++;
                continue;
            }
            else
            {
                title = title.substring(0, title.length() - 1);
                strCNTList += "{\"name\":\"" + OldName + "\",\"title\":\"" + title + "\"},";

                title = hint + obj[1] + ",";
                i = 1;
                OldName = (String) obj[0];
                if (obj.equals(worldMapTitleList.get(worldMapTitleList.size() - 1)))
                {
                    title = title.substring(0, title.length() - 1);
                    strCNTList += "{\"name\":\"" + OldName + "\",\"title\":\"" + title + "\"},";
                }
            }
        }
        String strReturnList = strCNVList + "<>" + strCNTList;
        return strReturnList;
    }
}
