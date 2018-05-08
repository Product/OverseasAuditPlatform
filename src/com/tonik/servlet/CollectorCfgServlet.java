package com.tonik.servlet;

import java.io.IOException;
import java.io.Writer;
import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.service.CollectorCfgService;

/**
 * @author fuzhi
 * @web.servlet name="CollectorCfg"
 * @web.servlet-mapping url-pattern="/servlet/CollectorCfg"
 */
@SuppressWarnings("serial")
public class CollectorCfgServlet extends BaseServlet
{
    private CollectorCfgService collectorCfgService;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        ApplicationContext apc = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        collectorCfgService = (CollectorCfgService) apc.getBean("CollectorCfgService");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        Writer writer = response.getWriter();
        if (sessionCheck(request, response)){
            writer.write("sessionOut");
            return;
        }
        try{
            String method = request.getParameter("method");
            if (null != method && !"".equals(method)){
                if (method.equals("listInternal")){
                    String index = request.getParameter("pageIndex");
                    String size = request.getParameter("pageSize");
                    String str = collectorCfgService.getListInternal(index, size);
                    writer.write(str);
                }else if (method.equals("excuteInternal")){
                    String id = request.getParameter("id");
                    String result = collectorCfgService.excuteByIdInternal(id);
                    writer.write(result);
                }else if(method.equals("editOrAddInternal")){
                    String id = request.getParameter("id");
                    String url = URLDecoder.decode(request.getParameter("url"), "utf-8");
                    String pageTotal = URLDecoder.decode(request.getParameter("pageTotal"), "utf-8");
                    String titleSign = URLDecoder.decode(request.getParameter("titleSign"), "utf-8");
                    String createTimeSign = URLDecoder.decode(request.getParameter("createTimeSign"), "utf-8");
                    String nextSign = URLDecoder.decode(request.getParameter("nextSign"), "utf-8");
                    String siteName = URLDecoder.decode(request.getParameter("siteName"), "utf-8");
                    String name = URLDecoder.decode(request.getParameter("name"), "utf-8");
                    String rate = URLDecoder.decode(request.getParameter("rate"), "utf-8");
                    int type = 1;
                    String blockSign = "";
                    String contentSign = "";
                    String str = collectorCfgService.addOrUpdate(id, url, pageTotal, titleSign, createTimeSign, nextSign, siteName, type, name, rate, blockSign, contentSign);
                    writer.write(str);
                }else if (method.equals("listAbroad")){
                    String index = request.getParameter("pageIndex");
                    String size = request.getParameter("pageSize");
                    String str = collectorCfgService.getListAbroad(index, size);
                    writer.write(str);
                }else if(method.equals("editOrAddAbroad")){
                    String id = request.getParameter("id");
                    String url = URLDecoder.decode(request.getParameter("url"), "utf-8");
                    String pageTotal = URLDecoder.decode(request.getParameter("pageTotal"), "utf-8");
                    String titleSign = URLDecoder.decode(request.getParameter("titleSign"), "utf-8");
                    String createTimeSign = URLDecoder.decode(request.getParameter("createTimeSign"), "utf-8");
                    String nextSign = URLDecoder.decode(request.getParameter("nextSign"), "utf-8");
                    String siteName = URLDecoder.decode(request.getParameter("siteName"), "utf-8");
                    String name = URLDecoder.decode(request.getParameter("name"), "utf-8");
                    String rate = URLDecoder.decode(request.getParameter("rate"), "utf-8");
                    int type = 2;
                    String blockSign = URLDecoder.decode(request.getParameter("blockSign"), "utf-8");
                    String contentSign = URLDecoder.decode(request.getParameter("contentSign"), "utf-8");
                    String str = collectorCfgService.addOrUpdate(id, url, pageTotal, titleSign, createTimeSign, nextSign, siteName, type, name, rate, blockSign, contentSign);
                    writer.write(str);
                }else if (method.equals("excuteAbroad")){
                    String id = request.getParameter("id");
                    String result = collectorCfgService.excuteByIdAbroad(id);
                    writer.write(result);
                }else if (method.equals("listPublicOpinion")){
                    String index = request.getParameter("pageIndex");
                    String size = request.getParameter("pageSize");
                    String str = collectorCfgService.getListPublicOpinion(index, size);
                    writer.write(str);
                }else if(method.equals("editOrAddPublicOpinion")){
                    String id = request.getParameter("id");
                    String url = URLDecoder.decode(request.getParameter("url"), "utf-8");
                    String pageTotal = URLDecoder.decode(request.getParameter("pageTotal"), "utf-8");
                    String titleSign = URLDecoder.decode(request.getParameter("titleSign"), "utf-8");
                    String createTimeSign = URLDecoder.decode(request.getParameter("createTimeSign"), "utf-8");
                    String nextSign = URLDecoder.decode(request.getParameter("nextSign"), "utf-8");
                    String siteName = URLDecoder.decode(request.getParameter("siteName"), "utf-8");
                    String name = URLDecoder.decode(request.getParameter("name"), "utf-8");
                    String rate = URLDecoder.decode(request.getParameter("rate"), "utf-8");
                    int type = 3;
                    String blockSign = "";
                    String contentSign = "";
                    String str = collectorCfgService.addOrUpdate(id, url, pageTotal, titleSign, createTimeSign, nextSign, siteName, type, name, rate, blockSign, contentSign);
                    writer.write(str);
                }else if (method.equals("excutePublicOpinion")){
                    String id = request.getParameter("id");
                    String result = collectorCfgService.excuteByIdPublicOpinion(id);
                    writer.write(result);
                }else if (method.equals("listWebsite")){
                    String index = request.getParameter("pageIndex");
                    String size = request.getParameter("pageSize");
                    String str = collectorCfgService.getListWebsite(index, size);
                    writer.write(str);
                }else if(method.equals("editOrAddWebsite")){
                    String id = request.getParameter("id");
                    String url = URLDecoder.decode(request.getParameter("url"), "utf-8");
                    String pageTotal = URLDecoder.decode(request.getParameter("pageTotal"), "utf-8");
                    String titleSign = URLDecoder.decode(request.getParameter("titleSign"), "utf-8");
                    String createTimeSign = URLDecoder.decode(request.getParameter("createTimeSign"), "utf-8");
                    String nextSign = URLDecoder.decode(request.getParameter("nextSign"), "utf-8");
                    String siteName = URLDecoder.decode(request.getParameter("siteName"), "utf-8");
                    String name = URLDecoder.decode(request.getParameter("name"), "utf-8");
                    String rate = URLDecoder.decode(request.getParameter("rate"), "utf-8");
                    int type = 4;
                    String blockSign = "";
                    String contentSign = "";
                    String str = collectorCfgService.addOrUpdate(id, url, pageTotal, titleSign, createTimeSign, nextSign, siteName, type, name, rate, blockSign, contentSign);
                    writer.write(str);
                }else if (method.equals("excuteWebsite")){
                    String id = request.getParameter("id");
                    String result = collectorCfgService.excuteByIdWebsite(id);
                    writer.write(result);
                }else if (method.equals("listExpertRepository")){
                    String index = request.getParameter("pageIndex");
                    String size = request.getParameter("pageSize");
                    String str = collectorCfgService.getListExpertRepository(index, size);
                    writer.write(str);
                }else if(method.equals("editOrAddExpertRepository")){
                    String id = request.getParameter("id");
                    String url = URLDecoder.decode(request.getParameter("url"), "utf-8");
                    String pageTotal = URLDecoder.decode(request.getParameter("pageTotal"), "utf-8");
                    String titleSign = URLDecoder.decode(request.getParameter("titleSign"), "utf-8");
                    String createTimeSign = URLDecoder.decode(request.getParameter("createTimeSign"), "utf-8");
                    String nextSign = URLDecoder.decode(request.getParameter("nextSign"), "utf-8");
                    String siteName = URLDecoder.decode(request.getParameter("siteName"), "utf-8");
                    String name = URLDecoder.decode(request.getParameter("name"), "utf-8");
                    String rate = URLDecoder.decode(request.getParameter("rate"), "utf-8");
                    String blockSign = URLDecoder.decode(request.getParameter("blockSign"), "utf-8");
                    int type = 5;
                    String contentSign = "";
                    String str = collectorCfgService.addOrUpdate(id, url, pageTotal, titleSign, createTimeSign, nextSign, siteName, type, name, rate, blockSign, contentSign);
                    writer.write(str);
                }else if (method.equals("excuteExpertRepository")){
                    String id = request.getParameter("id");
                    String result = collectorCfgService.excuteByIdExpertRepository(id);
                    writer.write(result);
                }else if(method.equals("queryById")){
                    String id = request.getParameter("id");
                    String result = collectorCfgService.queryById(id);
                    writer.write(result);
                }else if(method.equals("del")){
                    String id = URLDecoder.decode(request.getParameter("id"), "utf-8");
                    String result = collectorCfgService.delById(id);
                    writer.write(result);
                }else{
                    writer.write("没有此方法名");
                }
            }else{
                writer.write("方法名不能为空");
            }
        } catch (Exception e){
            e.printStackTrace();
            writer.write("异常");
        }finally{
            writer.flush();
            writer.close();
        }
    }
}