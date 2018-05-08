package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thinvent.utils.WebUtil;
import com.tonik.model.Area;
import com.tonik.model.Country;
import com.tonik.model.UserInfo;
import com.tonik.model.Website;
import com.tonik.model.WebsiteStyle;
import com.tonik.service.AreasService;
import com.tonik.service.CountryService;
import com.tonik.service.WebsiteService;
import com.tonik.service.WebsiteService.WebsitePagingBean;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @web.servlet name="websiteServlet"
 * @web.servlet-mapping url-pattern="/servlet/WebsiteServlet"
 */
public class WebsiteServlet extends BaseServlet
{
    private WebsiteService websiteService;
    private CountryService countryService;
    private AreasService AreasService;


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        websiteService = (WebsiteService) ctx.getBean("WebsiteService");
        countryService = (CountryService) ctx.getBean("CountryService");
        AreasService = (AreasService) ctx.getBean("AreasService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");         // 跨域
        response.addHeader("Access-Control-Allow-Headers", "content-type");


        if(this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equalsIgnoreCase(methodName))//获取网站列表
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    if ("".equals(strStraTime))//如果搜索开始时间未设置，则设置为1980-1-1
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))//如果截止时间未设置，则设置为当前日期
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = websiteService.WebsiteTotal(strQuery, strStraTime, strEndTime);
                    List<Website> ls = websiteService.WebsitePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";

                    for (Website item : ls)
                    {
                        strJson += websiteService.getWebsiteInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"websiteList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }if ("QueryOutList".equalsIgnoreCase(methodName))//获取网站列表
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    if ("".equals(strStraTime))//如果搜索开始时间未设置，则设置为1980-1-1
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))//如果截止时间未设置，则设置为当前日期
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = websiteService.outWebsiteTotal(strQuery, strStraTime, strEndTime);
                    List<Website> ls = websiteService.outWebsitePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";

                    for (Website item : ls)
                    {
                        strJson += websiteService.getWebsiteInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"websiteList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
              //获得国内网站的列表　add by xiaoyu
                else if ("NativeQueryList".equalsIgnoreCase(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strStartTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    if ("".equals(strStartTime))//如果搜索开始时间未设置，则设置为1980-1-1
                    {
                        strStartTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))//如果截止时间未设置，则设置为当前日期
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }
                    //中国对应的ｃｏｕｎｔｒｙＩｄ＝５５９
                    Long countryId=new Long(559);
                    String strTotal = websiteService.getWebsiteTotalByCountryId(countryId);
                   List<WebsitePagingBean> ls = websiteService.GetNativeWebsitePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStartTime, strEndTime,countryId);
                    HashMap<String, Object> res = new HashMap<String, Object>();
                    res.put("total", strTotal);
                    res.put("websiteList", ls);
                    WebUtil.writeJSON(response, res);
                } else if ("NewQueryList".equalsIgnoreCase(methodName))//获取国内网站核实列表
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    String webType = request.getParameter("webType");

                    if ("".equals(strStraTime))//如果搜索开始时间未设置，则设置为1980-1-1
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))//如果截止时间未设置，则设置为当前日期
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = websiteService.NewWebsiteTotal(strQuery, strStraTime, strEndTime,webType);
                    List<WebsitePagingBean> ls = websiteService.NewWebsitePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime,webType);
                    HashMap<String, Object> res = new HashMap<String, Object>();
                    res.put("total", strTotal);
                    res.put("websiteList", ls);
                    WebUtil.writeJSON(response, res);
                } else if ("NewQueryOutList".equalsIgnoreCase(methodName))//获取国外网站核实列表
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    String webType = request.getParameter("webType");

                    if ("".equals(strStraTime))//如果搜索开始时间未设置，则设置为1980-1-1
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))//如果截止时间未设置，则设置为当前日期
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = websiteService.NewOutWebsiteTotal(strQuery, strStraTime, strEndTime,webType);
                    List<WebsitePagingBean> ls = websiteService.NewOutWebsitePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime,webType);
                    HashMap<String, Object> res = new HashMap<String, Object>();
                    res.put("total", strTotal);
                    res.put("websiteList", ls);
                    WebUtil.writeJSON(response, res);
                }else if("accept".equalsIgnoreCase(methodName))
                {
                    try
                    {
                        String ids = request.getParameter("ids");
                        String[] idArray = ids.split("#");
                        for (String id : idArray)
                        {
                            Website w = websiteService.GetWebsiteById(Long.parseLong(id));
                            w.setWebsiteType(0);
                            websiteService.SaveWebsiteByWeb(w);
                        }
                        
                    } catch (Exception e)
                    {
                        response.getWriter().write("false");
                    }
                    response.getWriter().write("true");
                    
                }else if("refuse".equalsIgnoreCase(methodName))
                {
                    try
                    {
                        String ids = request.getParameter("ids");
                        String[] idArray = ids.split("#");
                        for (String id : idArray)
                        {
                            Website w = websiteService.GetWebsiteById(Long.parseLong(id));
                            w.setWebsiteType(1);
                            websiteService.SaveWebsiteByWeb(w);
                        }
                        
                    } catch (Exception e)
                    {
                        response.getWriter().write("false");
                    }
                    response.getWriter().write("true");
                }
                else if("view".equals(methodName))//详情查看
                {
                    
                   Website w = websiteService.GetWebsiteById(Long.parseLong(request.getParameter("id")));
                   String strw = websiteService.getWebsiteInfo(w);
                   response.getWriter().write(strw);
                    
                    
                }
                else if ("edit".equals(methodName))//编辑网站
                {
                    Website w = new Website();
                    w.setId(Long.parseLong(request.getParameter("id")));
                    w.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    w.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    w.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    w.setCreatePerson(ui.getId().toString());
                    w.setLocation(URLDecoder.decode(request.getParameter("location"),"UTF-8"));
                   
                    w.setAddress(URLDecoder.decode(request.getParameter("address"),"UTF-8"));
                    if(request.getParameter("integrity") == "")
                        w.setIntegrityDegree(0);
                    else
                        w.setIntegrityDegree(Integer.parseInt(request.getParameter("integrity")));
                    if(request.getParameter("score") == "")
                        w.setComprehensiveScore(0);
                    else
                        w.setComprehensiveScore(Integer.parseInt(request.getParameter("score")));
                    
                    if(!"".equals(request.getParameter("countryId")))
                    {
                        Country country = countryService.getCountryById(Long.parseLong(request.getParameter("countryId")));
                        w.setCountry(country);
                    }
                    else
                        w.setCountry(null);
                    
                   
                    if(!"".equals(request.getParameter("areaId")))
                    {
                        Area area = AreasService.getAreaById(Long.parseLong(request.getParameter("areaId")));
                        
                        w.setArea(area);
                    }
                    else
                        w.setArea(null);
                    websiteService.SaveWebsite(w, request.getParameter("webStyle"));
                    
                    String strw = websiteService.getWebsiteInfo(w);
                    response.getWriter().write(strw);
                }
                else if ("add".equals(methodName))//新增网站
                {
                    Website w = new Website();
                    
                    w.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    w.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    w.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    w.setCreatePerson(ui.getId().toString());
                    w.setLocation(URLDecoder.decode(request.getParameter("location"),"UTF-8"));
                   
                    w.setAddress(URLDecoder.decode(request.getParameter("address"),"UTF-8"));
                    w.setGatherid(0L);
                    
                    if(request.getParameter("integrity") == "")
                        w.setIntegrityDegree(0);
                    else
                        w.setIntegrityDegree(Integer.parseInt(request.getParameter("integrity")));
                    if(request.getParameter("score") == "")
                        w.setComprehensiveScore(0);
                    else
                        w.setComprehensiveScore(Integer.parseInt(request.getParameter("score")));
                    
                    if(!"".equals(request.getParameter("countryId")))
                    {
                        Country country = countryService.getCountryById(Long.parseLong(request.getParameter("countryId")));
                        w.setCountry(country);
                    }
                    else
                        w.setCountry(null);
                   
                    if(!"".equals(request.getParameter("areaId")))
                    {
                        Area area = AreasService.getAreaById(Long.parseLong(request.getParameter("areaId")));
                        
                        w.setArea(area);
                    }
                    else
                        w.setArea(null);
                    
                    websiteService.SaveWebsite(w, request.getParameter("webStyle"));
                    String strw = websiteService.getWebsiteInfo(w);
                    response.getWriter().write(strw);
                }
                else if ("del".equals(methodName))//删除网站
                {
                    websiteService.DelWebsite(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if ("init".equals(methodName))//初始化网站信息
                {
                    Website w = websiteService.GetWebsiteById(Long.parseLong(request.getParameter("id")));
                    String strw = websiteService.getWebsiteInfo(w);
                    response.getWriter().write(strw);
                }
                else if ("getWebsiteStyle".equals(methodName))//加载网站类型下拉框
                {
                    List<WebsiteStyle> was = websiteService.getWebsiteStyle();
                    String strWebStyle = "";
                    for (WebsiteStyle item : was)
                    {
                        strWebStyle += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strWebStyle != "")
                    {
                        strWebStyle = strWebStyle.substring(0, strWebStyle.length() - 1);
                    }

                    response.getWriter().write("{\"WebsiteStyle\":[" + strWebStyle + "]}");
                }
                else if ("gatherWebsiteQueryList".equals(methodName))//DataGather网站列表获取
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    if ("".equals(strStraTime))//如果搜索开始时间未设置，则设置为1980-1-1
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))//如果截止时间未设置，则设置为当前日期
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = websiteService.GatherwebsiteTotal(strQuery, strStraTime, strEndTime);
                    List<Website> ls = websiteService.GatherwebsitePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";

                    for (Website item : ls)
                    {
                        strJson += websiteService.getWebsiteInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"websiteList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                
               
               
               

            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }

    }
}