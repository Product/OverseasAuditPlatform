package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.UserInfo;
import com.tonik.model.VentureAnalysis;
import com.tonik.model.VentureAnalysisStyle;
import com.tonik.model.Website;
import com.tonik.model.WebsiteStyle;
import com.tonik.service.VentureAnalysisService;

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
 * @web.servlet name="ventureAnalysisServlet"
 * @web.servlet-mapping url-pattern="/servlet/VentureAnalysisServlet"
 */
public class VentureAnalysisServlet extends BaseServlet
{
    private VentureAnalysisService ventureAnalysisService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ventureAnalysisService = (VentureAnalysisService) ctx.getBean("VentureAnalysisService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if(this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))
                {
                    String strQuery = request.getParameter("strQuery");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    if ("".equals(strStraTime))
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = ventureAnalysisService.VentureAnalysisTotal(strQuery, strStraTime, strEndTime);
                    List<VentureAnalysis> ls = ventureAnalysisService.VentureAnalysisPaging(
                            Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery, strStraTime,
                            strEndTime);

                    String strJson = "";

                    for (VentureAnalysis item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"Style\":\""
                                + item.getStyle().getName() + "\",\"WebsiteStyle\":\"" + item.getWebsiteStyle().getName()
                                + "\",\"Website\":\"" + item.getWebsite()
                                + "\",\"AnalysisFileName\":\"" + item.getAnalysisFileName() + "\",\"AnalysisFile\":\""
                                + item.getAnalysisFile() + "\",\"ReleaseState\":\""
                                + (item.getAnalysisFileName().length() > 0 ? "未上传" : "未上传") + "\",\"Illustration\":\""
                                + item.getIllustration() + "\",\"Template\":\"" + item.getTemplate()
                                + "\",\"CreatePerson\":\"" + item.getCreatePerson() + "\",\"CreateTime\":\""
                                + item.getCreateTime() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("edit".equals(methodName))
                {
                    VentureAnalysis ws = (VentureAnalysis)ventureAnalysisService.GetVentureAnalysisById(Long.parseLong(request.getParameter("id")));
                    ws.setId(Long.parseLong(request.getParameter("id")));
                    ws.setName(request.getParameter("name"));
                    ws.setStyle(ventureAnalysisService.getVentureAnalysisStyleById(Long.parseLong(request.getParameter("style"))));
                    ws.setWebsite(ventureAnalysisService.getWebsiteById(Long.parseLong(request.getParameter("website"))));
                    ws.setWebsiteStyle(ventureAnalysisService.getWebsiteStyleById(Long.parseLong(request.getParameter("webStyle"))));
//                    VentureAnalysisStyle vas = new VentureAnalysisStyle();
//                    vas.setId(Long.parseLong(request.getParameter("style")));
//                    ws.setStyle(vas);
//                    Website uas = new Website();
//                    uas.setId(Long.parseLong(request.getParameter("webStyle")));
//                    Website was = new Website();
//                    was.setId(Long.parseLong(request.getParameter("website")));
//                    ws.setWebsite(was);
                    ws.setAnalysisFileName(request.getParameter("analysisFileName"));
                    //ws.getAnalysisFile(request.getBytes("analysisFile"));
                    ws.setIllustration(request.getParameter("illustration"));
                    ws.setTemplate("template");
                    ws.setCreateTime(new Date());
                    ws.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    ventureAnalysisService.SaveVentureAnalysis(ws);
                    response.getWriter().write("true");
                }
                else if ("add".equals(methodName))
                {
                    VentureAnalysis ws = new VentureAnalysis();
                    ws.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ws.setStyle(ventureAnalysisService.getVentureAnalysisStyleById(Long.parseLong(request.getParameter("style"))));
                    ws.setWebsite(ventureAnalysisService.getWebsiteById(Long.parseLong(request.getParameter("website"))));
                    ws.setWebsiteStyle(ventureAnalysisService.getWebsiteStyleById(Long.parseLong(request.getParameter("webStyle"))));
//                    VentureAnalysisStyle vas = new VentureAnalysisStyle();
//                    vas.setId(Long.parseLong(request.getParameter("style")));
//                    ws.setStyle(vas);
//                    Website uas = new Website();
//                    uas.setId(Long.parseLong(request.getParameter("webStyle")));
//                    Website was = new Website();
//                    was.setId(Long.parseLong(request.getParameter("website")));
//                    ws.setWebsite(was);
                    ws.setAnalysisFileName(URLDecoder.decode(request.getParameter("analysisFileName"),"UTF-8"));
                    // ws.getAnalysisFile(request.getBytes("analysisFile"));
                    ws.setIllustration(URLDecoder.decode(request.getParameter("illustration"),"UTF-8"));
                    ws.setTemplate("template");
                    ws.setCreateTime(new Date());
                    ws.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    ventureAnalysisService.SaveVentureAnalysis(ws);
                    response.getWriter().write("true");
                }
                else if ("del".equals(methodName))
                {
                    ventureAnalysisService.DelVentureAnalysis(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if ("init".equals(methodName))
                {
                    VentureAnalysis ws = ventureAnalysisService.GetVentureAnalysisById(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("{\"Id\":\"" + ws.getId() + "\",\"Name\":\"" + ws.getName() 
                                    + "\",\"Style\":\"" + ws.getStyle().getName() + "\",\"WebsiteStyle\":\"" + ws.getWebsiteStyle().getName()
                                    + "\",\"Website\":\"" + ws.getWebsite().getName()
                                    + "\",\"AnalysisFileName\":\"" + ws.getAnalysisFileName() + "\",\"AnalysisFile\":\""
                                    + ws.getAnalysisFile() + "\",\"ReleaseState\":\"" + ws.getReleaseState()
                                    + "\",\"Illustration\":\"" + ws.getIllustration() + "\",\"Template\":\""
                                    + ws.getTemplate() + "\",\"CreatePerson\":\"" + ws.getCreatePerson()
                                    + "\",\"CreateTime\":\"" + ws.getCreateTime() + "\"}");
                }
                else if ("getVAStyle".equals(methodName))
                {
                    List<VentureAnalysisStyle> was = ventureAnalysisService.getVentureAnalysisStyle();
                    String strName = "";
                    for (VentureAnalysisStyle item : was)
                    {
                        strName += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strName != "")
                    {
                        strName = strName.substring(0, strName.length() - 1);
                    }

                    response.getWriter().write("{\"VentureAnalysisStyle\":[" + strName + "]}");
                }
                else if ("getWWStyle".equals(methodName))
                {
                    List<WebsiteStyle> was = ventureAnalysisService.getWebsiteStyle();
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
                else if ("getAWeb".equals(methodName))
                {
                    List<Website> was = ventureAnalysisService.getWebsite(Long.parseLong(request.getParameter("id")));
                    String strName = "";
                    for (Website item : was)
                    {
                        strName += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strName != "")
                    {
                        strName = strName.substring(0, strName.length() - 1);
                    }

                    response.getWriter().write("{\"Website\":[" + strName + "]}");
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
