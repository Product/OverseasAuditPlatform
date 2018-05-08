package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.tonik.model.TestStyle;
import com.tonik.service.TestStyleService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * @web.servlet name="testStyleServlet"
 * @web.servlet-mapping url-pattern="/servlet/TestStyleServlet"
 */
public class TestStyleServlet extends BaseServlet
{
    private TestStyleService TestStyleService;
    
    public TestStyleService getTestStyleService()
    {
        return TestStyleService;
    }

    public void setTestStyleService(TestStyleService testStyleService)
    {
        TestStyleService = testStyleService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        TestStyleService = (TestStyleService) ctx.getBean("TestStyleService");
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
        PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equalsIgnoreCase(methodName))
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
                    //out.println(strQuery);
                    String strTotal = TestStyleService.TestStyleTotal(strQuery, strStraTime, strEndTime);
                    String strJson = "";
                    List<TestStyle> ts = TestStyleService.TestStylePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    for(TestStyle item:ts){
                        
                        strJson += TestStyleService.GetTestStyleInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
               else if ("edit".equalsIgnoreCase(methodName))
                {
                   TestStyle ts = new TestStyle();
                    ts.setId(Long.parseLong(request.getParameter("id")));
                    ts.setCreateTime(new Date());
                    ts.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ts.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ts.setType(Integer.parseInt(request.getParameter("type")));
                    TestStyleService.SaveTestStyle(ts, request.getParameter("UserId"));
                    out.print(TestStyleService.GetTestStyleInfo(ts));
                }
                else if ("add".equalsIgnoreCase(methodName))
                {
                    TestStyle ts = new TestStyle();
                    ts.setCreateTime(new Date());
                    ts.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ts.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ts.setType(Integer.parseInt(request.getParameter("type")));
                    TestStyleService.SaveTestStyle(ts, request.getParameter("UserId"));
                    out.print(TestStyleService.GetTestStyleInfo(ts));
                }
                else if ("del".equalsIgnoreCase(methodName))
                {
                    TestStyleService.DelTestStyle(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                else if("init".equalsIgnoreCase(methodName))
                {
                    TestStyle ts = TestStyleService.GetTestStyleById(Long.parseLong(request.getParameter("id")));
                    out.print(TestStyleService.GetTestStyleInfo(ts));
                }
            } catch (Exception e)
            {
                out.println("false1");
                out.println(e.getMessage());
            }
            finally
            {
                out.flush();
                out.close();
            }
        }
    }
}
