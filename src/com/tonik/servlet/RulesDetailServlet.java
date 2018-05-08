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
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.RulesDetail;
import com.tonik.model.UserInfo;
import com.tonik.service.RulesDetailService;

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
 * @web.servlet name="rulesDetailServlet"
 * @web.servlet-mapping url-pattern="/servlet/RulesDetailServlet"
 */
public class RulesDetailServlet extends BaseServlet
{
    private RulesDetailService rulesDetailService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        rulesDetailService = (RulesDetailService) ctx.getBean("RulesDetailService");
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
                //查询
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

                    String strTotal = rulesDetailService.RulesDetailTotal(strQuery, strStraTime, strEndTime);
                    List<RulesDetail> ls = rulesDetailService.RulesDetailPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";

                    for (RulesDetail item : ls)
                    {
                        strJson += rulesDetailService.getRulesDetailInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"rulesDetailList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
                
                //编辑
                else if ("edit".equalsIgnoreCase(methodName))
                {
                    RulesDetail ws = (RulesDetail)rulesDetailService.GetRulesDetailById(Long.parseLong(request.getParameter("id")));
                    ws.setId(Long.parseLong(request.getParameter("id")));
                    ws.setRulesid(Long.parseLong(request.getParameter("rulesid")));
                    ws.setCondition(request.getParameter("condition"));
                    ws.setRelationship(request.getParameter("relationship"));
                    ws.setValue(request.getParameter("value"));
                    ws.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    ws.setCreatePerson(ui.getId().toString());
                    rulesDetailService.SaveRulesDetail(ws);
                    String strRulesDetail = rulesDetailService.getRulesDetailInfo(ws);
                    out.print(strRulesDetail);
                }
                
                //新增
                else if ("add".equalsIgnoreCase(methodName))
                {
                    RulesDetail ws = new RulesDetail();
                    ws.setId(Long.parseLong(request.getParameter("id")));
                    ws.setRulesid(Long.parseLong(request.getParameter("rulesid")));
                    ws.setCondition(request.getParameter("condition"));
                    ws.setRelationship(request.getParameter("relationship"));
                    ws.setValue(request.getParameter("value"));
                    ws.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    ws.setCreatePerson(ui.getId().toString());
                    rulesDetailService.SaveRulesDetail(ws);
                    String strRulesDetail = rulesDetailService.getRulesDetailInfo(ws);
                    out.print(strRulesDetail);
                }
                
                //删除
                else if("delRow".equals(methodName))
                {
                    rulesDetailService.DelRulesDetail(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                    
                }
                else if ("init".equalsIgnoreCase(methodName))
                {
                    RulesDetail ws = (RulesDetail)rulesDetailService.GetRulesDetailById(Long.parseLong(request.getParameter("id")));
                    String strRulesDetail = rulesDetailService.getRulesDetailInfo(ws);
                    out.print(strRulesDetail);
                }
                else if ("getRulesDetail".equals(methodName))
                {
                    List<RulesDetail> ls = rulesDetailService.GetRulesDetailById(Long.parseLong(request.getParameter("id")));
                    String strJson = "";

                    for (RulesDetail item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"RulesId\":\"" + item.getRulesid() + "\"," + "\"Condition\":\"" 
                                + item.getCondition() + "\",\"Relationship\":\"" + item.getRelationship() + "\",\"Value\":\"" 
                                + item.getValue() + "\"," + "\"CreatePerson\":\"" + item.getCreatePerson() + "\",\"CreateTime\":\""
                                + item.getCreateTime() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"rulesDetailList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
            } catch (Exception e)
            {
                out.print("false");
            }
            finally
            {
                out.flush();
                out.close();
            }
        }
    }
}
