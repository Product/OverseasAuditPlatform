package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Product;
import com.tonik.model.TestTarget;
import com.tonik.model.Website;
import com.tonik.service.ProductService;
import com.tonik.service.TestTargetService;
import com.tonik.service.WebsiteService;

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
 * @web.servlet name="testTargetServlet"
 * @web.servlet-mapping url-pattern="/servlet/TestTargetServlet"
 */
public class TestTargetServlet extends BaseServlet
{
    private TestTargetService TestTargetService;
    private WebsiteService WebsiteService;
    private ProductService ProductService;

    public TestTargetService getTestTargetService()
    {
        return TestTargetService;
    }

    public void setTestTargetService(TestTargetService testTargetService)
    {
        TestTargetService = testTargetService;
    }
    
    public WebsiteService getWebsiteService()
    {
        return WebsiteService;
    }

    public void setWebsiteService(WebsiteService websiteService)
    {
        WebsiteService = websiteService;
    }

    public ProductService getProductService()
    {
        return ProductService;
    }

    public void setProductService(ProductService productService)
    {
        ProductService = productService;
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        TestTargetService = (TestTargetService) ctx.getBean("TestTargetService");
        WebsiteService = (WebsiteService) ctx.getBean("WebsiteService");
        ProductService = (ProductService) ctx.getBean("ProductService");
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
                if ("getTestTargets".equalsIgnoreCase(methodName))
                {
                    String strId = request.getParameter("Id");
                    List<TestTarget> tts = TestTargetService.GetTestTargetsByTest(strId);
                    String strJson = "", name="", location="", address="", style="";
                    
                    for(TestTarget tt:tts){
                        switch(tt.getType()){
                            case 1:
                                name = tt.getWebsite().getName();
                                location = tt.getWebsite().getLocation();
                                address = tt.getWebsite().getAddress();
                                style = tt.getWebsite().getWebStyle().getName();
                                break;
                            case 2:
                                name = tt.getProduct().getName();
                                location = tt.getProduct().getLocation();
                                address = tt.getProduct().getProducingArea();
                                style = tt.getProduct().getBrand();
                                break;
                        }
                        strJson += "{\"Id\":\"" +tt.getId() + "\",\"Name\":\""+name + "\",\"Style\":\""+style
                                +"\",\"Location\":\"" +location + "\",\"Address\":\"" + address+ "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + tts.size() + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
                else if ("del".equalsIgnoreCase(methodName))
                {
                    TestTargetService.DelTestTarget(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                else if("webTargetQuery".equalsIgnoreCase(methodName)){
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
                    String strWebJson = "";
                    String strwebTotal = "0";
                    //获取网站查询
                    
                        strwebTotal = WebsiteService.WebsiteTotal(strQuery, strStraTime, strEndTime);
                        List<Website> webls = WebsiteService.WebsitePaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                        
    
                        for (Website item : webls)
                        {
                            strWebJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"Location\":\""
                                    + item.getLocation() +"\",\"Style\":\"" +item.getWebStyle().getName() + "\",\"Address\":\"" + item.getAddress() +"\"},";
                        }
                    
                    if (strWebJson.length() > 0)
                    {
                        strWebJson = strWebJson.substring(0, strWebJson.length() - 1);
                        String res = "{\"webTotal\":\"" + strwebTotal + "\",\"webList\":[" + strWebJson + "]}";
                        out.print(res);
                    }else{
                        out.print("false");
                    }
                }
                else if("proTargetQuery".equalsIgnoreCase(methodName)){
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
                    String strProJson = "";
                    String strproTotal = "0";
                    strproTotal = ProductService.ProductTotal(strQuery, strStraTime, strEndTime);
                    List<Product> prols = ProductService.ProductPaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
    
                        for (Product item : prols)
                        {
                            strProJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"Location\":\""
                                    + item.getLocation() +"\",\"Style\":\"" +item.getBrand() + "\",\"Address\":\"" + item.getProducingArea() +"\"},";
                        }
                    
                    if (strProJson.length() > 0)
                    {
                        strProJson = strProJson.substring(0, strProJson.length() - 1);
                        String res = "{\"proTotal\":\"" + strproTotal + "\",\"proList\":[" + strProJson + "]}";
                        out.print(res);
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
