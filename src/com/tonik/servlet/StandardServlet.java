package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Standard;
import com.tonik.service.StandardService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 匹配标准servlet
 * </p>
 * @since Apr 18, 2016
 * @version 1.0
 * @author liuyu
 * @web.servlet name="standardServlet"
 * @web.servlet-mapping url-pattern="/servlet/StandardServlet"
 */
public class StandardServlet extends BaseServlet
{
    private StandardService standardService;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        standardService = (StandardService) ctx.getBean("StandardService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if (this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("GetStandards".equals(methodName))
                {
                    List<Standard> standardList = standardService.getStandards();
                    
                    String strJson = "";

                    for (com.tonik.model.Standard standard : standardList)
                    {
                        strJson += "{\"Id\":\"" + standard.getId() + "\",\"Name\":\"" + standard.getName() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"standardList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                /**规则集的增删改查操作        by Ye 2016.07.01*/
                else if ("QueryList".equals(methodName))//加载规则集列表
                {
                    int pageSize = Integer.parseInt(URLDecoder.decode(request.getParameter("pageSize"), "UTF-8"));
                    int pageIndex = Integer.parseInt(URLDecoder.decode(request.getParameter("pageIndex"), "UTF-8"));
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    Integer system = Integer.parseInt(request.getParameter("system"));
                    List<Standard> standardList = standardService.getStandardsPaging(pageIndex, pageSize, strQuery, system);
                    int standardTotal = standardService.getStandardsTotal(strQuery, system);

                    String strJson = "";

                    for (com.tonik.model.Standard standard : standardList)
                    {
                        strJson += "{\"Id\":\"" + standard.getId() + "\",\"Name\":\"" + standard.getName() + "\",\"UpdateTime\":\"" + standard.getUpdateTime() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"Total\":\"" + standardTotal + "\",\"DataList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if("Del".equals(methodName))//删除某记录
                {
                    Long id = Long.parseLong(request.getParameter("id"));
                    standardService.removeStandard(id);
                    response.getWriter().write("true");
                }
                else if("Add".equals(methodName))//增加一个规则集
                {
                    Standard standard = new Standard();
                    String name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
                    String agency = URLDecoder.decode(request.getParameter("agency"),"UTF-8");
                    String remark = URLDecoder.decode(request.getParameter("remark"),"UTF-8");
                    Integer system = Integer.parseInt(request.getParameter("system"));
                    standard.setName(name);
                    standard.setAgency(agency);
                    standard.setRemark(remark);
                    standard.setCreateTime(new Date());
                    standard.setUpdateTime(new Date());
                    standard.setSystem(system);
                    standardService.saveStandard(standard);
                    response.getWriter().write("true");
                }
                else if("Edit".equals(methodName))//增加一个规则集
                {
                    Long id = Long.parseLong(request.getParameter("id"));
                    String name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
                    String agency = URLDecoder.decode(request.getParameter("agency"),"UTF-8");
                    String remark = URLDecoder.decode(request.getParameter("remark"),"UTF-8");
                    Standard standard = standardService.getStandardById(id);
                    Integer system = Integer.parseInt(request.getParameter("system"));
                    standard.setName(name);
                    standard.setAgency(agency);
                    standard.setRemark(remark);
                    standard.setUpdateTime(new Date());
                    standard.setSystem(system);
                    standardService.saveStandard(standard);
                    response.getWriter().write("true");
                }
                else if("View".equals(methodName))//查看一个规则集
                {
                    Long id = Long.parseLong(request.getParameter("id"));
                    Standard standard = standardService.getStandardById(id);
                    String dataList = "";
                    dataList += "{\"Id\":\"" + standard.getId() + "\",\"Name\":\"" + standard.getName() + "\",\"Agency\":\"" + standard.getAgency() + "\",\"Remark\":\"" + standard.getRemark() + "\",\"UpdateTime\":\"" + standard.getUpdateTime() + "\"},";
                    if(dataList.length() > 0){
                        dataList = dataList.substring(0, dataList.length() -1);
                    }
                    response.getWriter().write("{\"DataList\":[" + dataList + "]}");
                }
            }catch(Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
