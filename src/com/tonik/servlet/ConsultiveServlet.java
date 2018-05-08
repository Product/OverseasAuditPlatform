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

import com.tonik.Constant;
import com.tonik.model.Consultive;
import com.tonik.model.UserInfo;
import com.tonik.service.ConsultiveService;

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
 * @web.servlet name="consultiveServlet"
 * @web.servlet-mapping url-pattern="/servlet/ConsultiveServlet"
 */
public class ConsultiveServlet extends BaseServlet
{
    private ConsultiveService ConsultiveService;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ConsultiveService = (ConsultiveService) ctx.getBean("ConsultiveService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
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
                if("QueryList".equals(methodName))
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
                        SimpleDateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式
                        strEndTime = df.format(new Date());
                    }
                    
                    String strTotal = ConsultiveService.ConsultiveTotal(strQuery, strStraTime, strEndTime);
                    List<Consultive> ls = ConsultiveService.ConsultivePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";    
                    for (Consultive item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Title\":\"" + item.getTitle()+"\",\"Type\":\"" + item.getType()
                                + "\",\"Content\":\"" + item.getContent() + "\",\"ReturnTime\":\"" + item.getFormatReturnTime()
                                + "\",\"ReturnContent\":\"" + item.getReturnContent() + "\",\"Consultant\":\""
                                + item.getConsultant() + "\",\"ConsultiveTime\":\"" + item.getFormatConsultiveTime()
                                + "\",\"CreatePerson\":\"" + item.getCreatePerson() + "\",\"CreateTime\":\"" + item.getFormatCreateTime()
                                + "\"},";
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
                else if("My_QueryList".equals(methodName))
                {
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    String strTotal = ConsultiveService.MyConsultiveTotal(((UserInfo) request.getSession().getAttribute("userInfo")).getId());
                    List<Consultive> ls = ConsultiveService.MyConsultivePaging(Integer.parseInt(strPageIndex),Integer.parseInt(strPageCount),
                            ((UserInfo) request.getSession().getAttribute("userInfo")).getId());
                    String strJson = "";    
                    for (Consultive item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Title\":\"" + item.getTitle()+"\",\"Type\":\"" + item.getType()
                                + "\",\"Content\":\"" + item.getContent() + "\",\"ReturnTime\":\"" + item.getFormatReturnTime()
                                + "\",\"ReturnContent\":\"" + item.getReturnContent() + "\",\"Consultant\":\""
                                + item.getConsultant() + "\",\"ConsultiveTime\":\"" + item.getFormatConsultiveTime()
                                + "\",\"CreatePerson\":\"" + item.getCreatePerson() + "\",\"CreateTime\":\"" + item.getFormatCreateTime()
                                + "\"},";
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
                else if("Complain_QueryList".equals(methodName))
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
                    String strTotal = ConsultiveService.ComplainConsultiveTotal(strQuery, strStraTime, strEndTime);
                    List<Consultive> ls =ConsultiveService.ComplainConsultivePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = ""; 
                    for (Consultive item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Title\":\"" + item.getTitle()+"\",\"Type\":\"" + item.getType()
                                + "\",\"Content\":\"" + item.getContent() + "\",\"ReturnTime\":\"" + item.getFormatReturnTime()
                                + "\",\"ReturnContent\":\"" + item.getReturnContent() + "\",\"Consultant\":\""
                                + item.getConsultant() + "\",\"ConsultiveTime\":\"" + item.getFormatConsultiveTime()
                                + "\",\"CreatePerson\":\"" + item.getCreatePerson() + "\",\"CreateTime\":\"" + item.getFormatCreateTime()
                                + "\"},";
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
                else if("Detail".equals(methodName))
                {
                    String id=request.getParameter("id");
                    //String strTotal=ConsultiveService.AnswerTotal(Long.parseLong(id));
                    List<Consultive> ls=ConsultiveService.FindAnswerDetailById(Long.parseLong(id));
                    String strJson = "";
                    for (Consultive item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Title\":\"" + item.getTitle()+"\",\"Type\":\"" + item.getType()
                                + "\",\"Content\":\"" + item.getContent() + "\",\"ReturnTime\":\"" + item.getFormatReturnTime()
                                + "\",\"ReturnContent\":\"" + item.getReturnContent() + "\",\"Consultant\":\""
                                + item.getConsultant() + "\",\"ConsultiveTime\":\"" + item.getFormatConsultiveTime()
                                + "\",\"CreatePerson\":\"" + item.getCreatePerson() + "\",\"CreateTime\":\"" + item.getFormatCreateTime()
                                +"\",\"ParentNod\":\""+item.getParentNod()+"\",\"RootNod\":\""+item.getRootNod()
                                + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
                else if("add".equals(methodName))
                {
                    Consultive cl=new Consultive();
                    cl.setTitle(URLDecoder.decode(request.getParameter("title"),"UTF-8"));
                    cl.setType(Integer.parseInt(URLDecoder.decode(request.getParameter("type"),"UTF-8")));
                    cl.setContent(URLDecoder.decode(request.getParameter("content"),"UTF-8"));
                    cl.setConsultant(((UserInfo) request.getSession().getAttribute("userInfo")).getId());
                    cl.setConsultiveTime(new Date());
                    cl.setParentNod(0l);
                    ConsultiveService.SaveConsultive(cl);
                    cl.setRootNod(cl.getId());
                    ConsultiveService.SaveConsultive(cl);
                    out.print("{\"Id\":\"" + cl.getId() + "\",\"Title\":\"" + cl.getTitle()+"\",\"Type\":\"" + cl.getType()
                            + "\",\"Content\":\"" + cl.getContent() + "\",\"ReturnTime\":\"" + cl.getFormatReturnTime()
                            + "\",\"ReturnContent\":\"" + cl.getReturnContent() + "\",\"Consultant\":\""
                            + cl.getConsultant() + "\",\"ConsultiveTime\":\"" + cl.getFormatConsultiveTime()
                            + "\",\"CreatePerson\":\"" + cl.getCreatePerson() + "\",\"CreateTime\":\"" + cl.getFormatCreateTime()
                            + "\"}");
                }
                else if("Ask".equals(methodName))
                {
                    String id=request.getParameter("id");
                    Consultive cs=ConsultiveService.getConsultiveById(Long.parseLong(id));
                    Consultive cu=new Consultive();
                    cu.setTitle(cs.getTitle());
                    cu.setContent(URLDecoder.decode(request.getParameter("question"),"UTF-8"));
                    cu.setConsultant(((UserInfo) request.getSession().getAttribute("userInfo")).getId());
                    cu.setConsultiveTime(new Date());
                    cu.setParentNod(Long.parseLong(id));
                    cu.setRootNod(cs.getRootNod());
                    cu.setType(cs.getType());
                    ConsultiveService.SaveConsultive(cu);
                    out.print("true");
                }
                else if("Answer".equals(methodName))
                {
                    String id=request.getParameter("id");
                    Consultive cs=ConsultiveService.getConsultiveById(Long.parseLong(id));
                    cs.setReturnContent(URLDecoder.decode(request.getParameter("answer"),"UTF-8"));
                    cs.setReturnTime(new Date());
                    cs.setCreatePerson(((UserInfo) request.getSession().getAttribute("userInfo")).getId().toString());
                    ConsultiveService.SaveConsultive(cs);
                    out.print("{\"CreateTime\":\"" + cs.getFormatReturnTime()+ "\"}");
                }
                else if("ComplainHanding".equals(methodName))
                {
                    String id=request.getParameter("id");
                    Consultive cs=ConsultiveService.getConsultiveById(Long.parseLong(id));
                    out.print("{\"Id\":\"" + cs.getId() + "\",\"Title\":\"" + cs.getTitle()+"\",\"Type\":\"" + cs.getType()
                    + "\",\"Content\":\"" + cs.getContent() + "\",\"ReturnTime\":\"" + cs.getFormatReturnTime()
                    + "\",\"ReturnContent\":\"" + cs.getReturnContent() + "\",\"Consultant\":\""
                    + cs.getConsultant() + "\",\"ConsultiveTime\":\"" + cs.getFormatConsultiveTime()
                    + "\",\"CreatePerson\":\"" + cs.getCreatePerson() + "\",\"CreateTime\":\"" + cs.getFormatCreateTime()
                    + "\"}");
                }
                else if("Handing".equals(methodName))
                {
                    String id=request.getParameter("id");
                    Consultive cs=ConsultiveService.getConsultiveById(Long.parseLong(id));
                    cs.setReturnContent(URLDecoder.decode(request.getParameter("Handing"),"UTF-8"));
                    cs.setReturnTime(new Date());
                    cs.setCreatePerson(((UserInfo) request.getSession().getAttribute("userInfo")).getId().toString());
                    ConsultiveService.SaveConsultive(cs);
                    out.print("{\"Id\":\"" + cs.getId() + "\",\"Title\":\"" + cs.getTitle()+"\",\"Type\":\"" + cs.getType()
                            + "\",\"Content\":\"" + cs.getContent() + "\",\"ReturnTime\":\"" + cs.getFormatReturnTime()
                            + "\",\"ReturnContent\":\"" + cs.getReturnContent() + "\",\"Consultant\":\""
                            + cs.getConsultant() + "\",\"ConsultiveTime\":\"" + cs.getFormatConsultiveTime()
                            + "\",\"CreatePerson\":\"" + cs.getCreatePerson() + "\",\"CreateTime\":\"" + cs.getFormatCreateTime()
                            + "\"}");
                }
            } catch (Exception e)
            {
                out.print("false");
            }
        }

        out.flush();
        out.close();

    }
}
