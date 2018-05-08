package com.tonik.servlet;

import java.io.IOException;
import java.io.Writer;
import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.service.CollectorCfgCommodityService;

/**
 * @web.servlet name="CollectorCfgCommodityServlet"
 * @author fuzhi
 * @web.servlet-mapping url-pattern="/servlet/CollectorCfgCommodity"
 */
@SuppressWarnings("serial")
public class CollectorCfgCommodityServlet extends BaseServlet
{
    private CollectorCfgCommodityService collectorCfgCommodityService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        collectorCfgCommodityService = (CollectorCfgCommodityService) wac.getBean("CollectorCfgCommodityService");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        Writer writer = response.getWriter();
        String methodName = request.getParameter("methodName");
        try
        {
            if (null != methodName && !"".equals(methodName))
            {
                if (methodName.equals("queryList"))
                {
                    String pageIndex = request.getParameter("pageIndex");
                    String pageSize = request.getParameter("pageSize");
                    String str = collectorCfgCommodityService.findList(pageIndex, pageSize);
                    writer.write(str);
                }
                else if (methodName.equals("queryById"))
                {
                    String id = URLDecoder.decode(request.getParameter("id"), "utf-8");
                    String str = collectorCfgCommodityService.findById(id);
                    writer.write(str);
                }
                else if (methodName.equals("addOrUpdate"))
                {
                    String id = URLDecoder.decode(request.getParameter("id"), "utf-8");
                    String name = URLDecoder.decode(request.getParameter("name"), "utf-8");
                    String rate = URLDecoder.decode(request.getParameter("rate"), "utf-8");

                    String url = URLDecoder.decode(request.getParameter("url"), "utf-8");
                    String contentSign = URLDecoder.decode(request.getParameter("contentSign"), "utf-8");
                    String titleSign = URLDecoder.decode(request.getParameter("titleSign"), "utf-8");
                    String priceSign = URLDecoder.decode(request.getParameter("priceSign"), "utf-8");
                    String saleMountSign = URLDecoder.decode(request.getParameter("saleMountSign"), "utf-8");
                    String nextSign = URLDecoder.decode(request.getParameter("nextSign"), "utf-8");
                    String pageTotal = URLDecoder.decode(request.getParameter("pageTotal"), "utf-8");

                    String brandSign = URLDecoder.decode(request.getParameter("brandSign"), "utf-8");
                    String producingAreaSign = URLDecoder.decode(request.getParameter("producingAreaSign"), "utf-8");
                    String suitableAgeSign = URLDecoder.decode(request.getParameter("suitableAgeSign"), "utf-8");
                    String standardSign = URLDecoder.decode(request.getParameter("standardSign"), "utf-8");
                    String expirationDateSign = URLDecoder.decode(request.getParameter("expirationDateSign"), "utf-8");
                    String countrySign = URLDecoder.decode(request.getParameter("countrySign"), "utf-8");
                    String productTypeSign = URLDecoder.decode(request.getParameter("productTypeSign"), "utf-8");

                    String websiteId = URLDecoder.decode(request.getParameter("websiteId"), "utf-8");
                    String productStyleId = URLDecoder.decode(request.getParameter("productStyleId"), "utf-8");
                    String firstlevelType = URLDecoder.decode(request.getParameter("firstlevelType"), "utf-8");
                    String secondlevelType = URLDecoder.decode(request.getParameter("secondlevelType"), "utf-8");
                    String thirdlevelType = URLDecoder.decode(request.getParameter("thirdlevelType"), "utf-8");

                    String str = collectorCfgCommodityService.addOrUpdate(id, name, rate, url, contentSign, titleSign,
                            priceSign, saleMountSign, nextSign, pageTotal, brandSign, producingAreaSign,
                            suitableAgeSign, standardSign, expirationDateSign, countrySign, productTypeSign, websiteId,
                            productStyleId, firstlevelType, secondlevelType, thirdlevelType);
                    writer.write(str);
                }
                else if (methodName.equals("delete"))
                {
                    String id = URLDecoder.decode(request.getParameter("id"), "utf-8");
                    collectorCfgCommodityService.delete(id);
                    writer.write("success");
                }
                else if (methodName.equals("excute"))
                {
                    Long id = Long.valueOf(request.getParameter("id"));
                    collectorCfgCommodityService.excute(id);
                    writer.write("success");
                }
                else
                {
                    writer.write("没有此方法名");
                }
            }
            else
            {
                writer.write("方法名不能为空");
            }
        } catch (Exception e)
        {
            writer.write("异常");
        }
        finally
        {
            writer.flush();
            writer.close();
        }
    }
}
