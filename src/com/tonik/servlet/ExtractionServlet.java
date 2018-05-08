package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Extraction;
import com.tonik.model.Product;
import com.tonik.model.Rules;
import com.tonik.model.RulesDetail;
import com.tonik.service.ExtractionService;
import com.tonik.service.RulesDetailService;
import com.tonik.service.RulesService;

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
 * @web.servlet name="extractionServlet"
 * @web.servlet-mapping url-pattern="/servlet/ExtractionServlet"
 */
public class ExtractionServlet extends HttpServlet
{
    private ExtractionService ExtractionService;
    private RulesDetailService RulesDetailService;
    private RulesService RulesService;


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ExtractionService = (ExtractionService) ctx.getBean("ExtractionService");
        RulesDetailService = (RulesDetailService) ctx.getBean("RulesDetailService");
        RulesService = (RulesService) ctx.getBean("RulesService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
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

                    String strTotal = ExtractionService.ExtractionTotal(strQuery, strStraTime, strEndTime);
                    List<Extraction> ls = ExtractionService.ExtractionPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";
                    for (Extraction item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"ProductName\":\"" + item.getProduct().getName()
                                + "\",\"RulesName\":\"" + item.getRules().getName() + "\",\"ProductBrand\":\""
                                + item.getProduct().getBrand() + "\",\"ExtractTime\":\"" + item.getExtractionTime()
                                + "\",\"ProductPrice\":\"" + item.getProduct().getPrice() + "\"},";
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
                else if ("extract".equals(methodName))
                {
                    Date date = new Date();
                    List<RulesDetail> rulesdetal = RulesDetailService
                            .GetRulesDetailById(Long.parseLong(request.getParameter("Id")));
                    Rules rules = RulesService.GetRulesById(Long.parseLong(request.getParameter("Id")));
                    ExtractionService.deleteByRulesId(Long.parseLong(request.getParameter("Id")));
                    List<String> values = new ArrayList<String>();
                    for (RulesDetail item : rulesdetal)
                    {
                        if ("商品描述".equals(item.getCondition()))
                        {
                            values.add(item.getValue());
                        }
                    }
                    List<Product> products = ExtractionService.getProductByrules(rules, values);
                    if (products != null)
                    {
                        for (Product item : products)
                        {
                            Extraction et = new Extraction();
                            // et.setRulesId(Long.parseLong(request.getParameter("Id")));
                            // et.setProductId(item.getId());
                            et.setProduct(item);
                            et.setRules(rules);
                            et.setExtractionTime(date);
                            ExtractionService.saveExtraction(et);
                        }
                    }
                    response.getWriter().write("true");
                }
                else if ("ExtractView".equals(methodName))
                {
                    List<Extraction> extractions = ExtractionService
                            .getExtractionListByRulesId(Long.parseLong(request.getParameter("Id")));
                    String strJson = "";
                    for (Extraction item : extractions)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"ProductName\":\"" + item.getProduct().getName()
                                + "\",\"RulesName\":\"" + item.getRules().getName() + "\",\"ProduceBrand\":\""
                                + item.getProduct().getBrand() + "\",\"Price\":\"" + item.getProduct().getPrice()
                                + "\",\"ExtractTime\":\"" + item.getExtractionTime() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"webList\":[" + strJson + "]}");
                    }
                    else
                        response.getWriter().write("false");
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
