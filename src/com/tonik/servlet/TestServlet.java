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

import com.tonik.model.Test;
import com.tonik.model.TestStyle;
import com.tonik.model.UserInfo;
import com.tonik.service.TestService;
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
 * @web.servlet name="testServlet"
 * @web.servlet-mapping url-pattern="/servlet/TestServlet"
 */
public class TestServlet extends BaseServlet
{
    private TestService TestService;
    private TestStyleService TestStyleService;

    public TestStyleService getTestStyleService()
    {
        return TestStyleService;
    }

    public void setTestStyleService(TestStyleService testStyleService)
    {
        TestStyleService = testStyleService;
    }

    public TestService getTestService()
    {
        return TestService;
    }

    public void setTestService(TestService testService)
    {
        TestService = testService;
    }


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        TestService = (TestService) ctx.getBean("TestService");
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
                    String strTotal = TestService.TestTotal(strQuery, strStraTime, strEndTime);
                    String strJson = "";
                    List<Test> ls = TestService.TestPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    for(Test item: ls){
                        strJson += TestService.getTestInfo(item)+",";
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
                   Test ts = new Test();
                    ts.setId(Long.parseLong(request.getParameter("id")));
                    ts.setEndtime( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("endTime")));
                    ts.setStarttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("startTime")));
                    ts.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ts.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ts.setNum(Integer.parseInt(request.getParameter("num")));
                    ts.setScore(Integer.parseInt(request.getParameter("score")));
                    ts.setCreateperson((UserInfo) request.getSession().getAttribute("userInfo"));
                    TestService.SaveTest(ts, request.getParameter("style"), request.getParameter("webTarget"), request.getParameter("proTarget"));
                    String strTest = TestService.getTestInfo(ts);

                    out.print(strTest);
                }
                else if ("add".equalsIgnoreCase(methodName))
                {
                    Test ts = new Test();
                    ts.setEndtime( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("endTime")));
                    ts.setStarttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("startTime")));
                    ts.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ts.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ts.setNum(Integer.parseInt(request.getParameter("num")));
                    ts.setScore(Integer.parseInt(request.getParameter("score")));
                    ts.setCreateperson((UserInfo) request.getSession().getAttribute("userInfo"));
                    TestService.SaveTest(ts, request.getParameter("style"), request.getParameter("webTarget"), request.getParameter("proTarget"));
                    String strTest = TestService.getTestInfo(ts);

                    out.print(strTest);
                }
                else if ("del".equalsIgnoreCase(methodName))
                {
                    TestService.DelTest(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                else if("init".equalsIgnoreCase(methodName))
                {
                    Test ts = TestService.GetTestById(Long.parseLong(request.getParameter("id")));
                    String strTest = TestService.getTestInfo(ts);

                    out.print(strTest);
                }
                else if("style".equalsIgnoreCase(methodName)){
                    List<TestStyle> tts = TestStyleService.GetTestStyles();
                    String strStyle = "";//, name;
                    for(TestStyle item: tts){
                        strStyle+="{\"Id\":\""+item.getId() +"\", \"Name\":\""+item.getName() +"\", \"Type\":\""+item.getType()+"\"},";
                    }
                    if(strStyle.length() > 0){
                        strStyle = strStyle.substring(0, strStyle.length() - 1);
                        out.print("{\"total\":\"" + tts.size() + "\",\"webList\":[" + strStyle + "]}");
                    }else{
                        out.print("false");
                    }
                }
            }catch (Exception e)
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
