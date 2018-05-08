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
 * Description: 疫情影响Map类别 servlet
 * </p>
 * @since Nov 04, 2015
 * @version 1.0
 * @author liuyu
 * @web.servlet name="epidemicAffectedMapServlet"
 * @web.servlet-mapping url-pattern="/servlet/EpidemicAffectedMapServlet"
 */
public class EpidemicAffectedMapServlet extends BaseServlet
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

        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("select".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                    String strEpidemicType = URLDecoder.decode(request.getParameter("strEpidemicType"), "UTF-8");
                    Long epidemicTypeId = null;
                    if (!strEpidemicType.equals(""))
                    {
                        epidemicTypeId = Long.parseLong(strEpidemicType);
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
                    List<Event> events = eventAffectedMapService.getEpidemicEvent(strQuery, epidemicTypeId,
                            strStartTime, strEndTime);

                    // 未找到对应事件
                    if (events.size() <= 0)
                    {
                        response.getWriter()
                                .write("{\"brandTotal\":\"" + 0 + "\",\"productTotal\":\"" + 0
                                        + "\",\"productTypeTotal\":\"" + 0 + "\",\"websiteTotal\":\"" + 0
                                        + "\",\"countryList\":[]" + ",\"eventCNVList\":[]" + ",\"eventCNTList\":[]}");
                    }
                    else
                    {
                        Long[] eventIds = new Long[events.size()];

                        int i = 0;
                        for (Event event : events)
                        {
                            eventIds[i++] = event.getId();
                        }

                        // 获取影响的品牌，商品种类，网站总数
                        int brandTotal = eventAffectedMapService.getEventAffectedBrandsTotal(eventIds);
                        int websiteTotal = eventAffectedMapService.getEventAffectedWebsitesTotal(eventIds);
                        int productTypeTotal = eventAffectedMapService.getEventAffectedProductTypesTotal(eventIds);
                        int productTotal = eventAffectedMapService.getEventAffectedProductsTotal(eventIds);

                        String strJson = "";

                        String strCNVList = "";
                        String strCNTList = "";

                        String selectType = request.getParameter("selectType");

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

                        // 按国家分布获得商品信息
                        if ("product".equals(selectType))
                        {
                            Long[] ptl = new Long[0];

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
                        if ("website".equals(selectType))
                        {
                            List<Object[]> websiteTotalList = eventAffectedMapService.getWorldMapWebsiteTotal(eventIds);
                            List<Object[]> websiteList = eventAffectedMapService.getWebsiteListByEventsId(eventIds);
                            String title = "代表网站：";
                            String[] returnList = getReturnList(websiteTotalList, websiteList, title)
                                    .split("<>", 2);
                            strCNVList = returnList[0];
                            strCNTList = returnList[1];
                        }
                        if ("productType".equals(selectType))
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
                        if (strCNVList.length() > 0)
                            strCNVList = strCNVList.substring(0, strCNVList.length() - 1);
                        if (strCNTList.length() > 0)
                            strCNTList = strCNTList.substring(0, strCNTList.length() - 1);

                        response.getWriter()
                                .write("{\"brandTotal\":\"" + brandTotal + "\",\"productTotal\":\"" + productTotal
                                        + "\",\"productTypeTotal\":\"" + productTypeTotal + "\",\"websiteTotal\":\""
                                        + websiteTotal + "\",\"countryList\":[" + strJson + "]" + ",\"eventCNVList\":["
                                        + strCNVList + "]" + ",\"eventCNTList\":[" + strCNTList + "]}");
                    }
                }
                else if ("exportExcel".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                    String strEpidemicType = URLDecoder.decode(request.getParameter("strEpidemicType"), "UTF-8");
                    Long epidemicTypeId = null;
                    if (!strEpidemicType.equals(""))
                    {
                        epidemicTypeId = Long.parseLong(strEpidemicType);
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
                    List<Event> events = eventAffectedMapService.getEpidemicEvent(strQuery, epidemicTypeId,
                            strStartTime, strEndTime);

                    // 未找到对应事件
                    if (events.size() == 0)
                    {
                        response.getWriter().write("false");
                    }
                    else
                    {
                        Long[] eventIds = new Long[events.size()];

                        int i = 0;
                        for (Event event : events)
                        {
                            eventIds[i++] = event.getId();
                        }
                        Long[] productTypes = new Long[0];

                        response.setHeader("Content-Disposition",
                                "inline;filename=" + new String("疫情影响纵览清单".getBytes("gb2312"), "ISO8859-1") + ".xls");
                        response.setContentType("application/octet-stream");
                        String[] headers = new String[] { "商品名称", "来源网站", "网站路径" };
                        OutputStream os = response.getOutputStream();
                        HSSFWorkbook workbook = excel.exportExcel("疫情影响纵览清单", headers,
                                eventAffectedMapService.getEventAffectedList(eventIds, productTypes));
                        try
                        {
                            workbook.write(os);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        os.close();
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
