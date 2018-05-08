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

import com.tonik.model.Article;
import com.tonik.model.Match;
import com.tonik.service.ArticleService;
import com.tonik.service.MatchService;

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
 * @web.servlet name="matchServlet"
 * @web.servlet-mapping url-pattern="/servlet/MatchServlet"
 */
public class MatchServlet extends BaseServlet
{
    private MatchService matchService;
    private ArticleService articleService;
    
    
    public ArticleService getArticleService()
    {
        return articleService;
    }

    public void setArticleService(ArticleService articleService)
    {
        this.articleService = articleService;
    }

    public MatchService getMatchService()
    {
        return matchService;
    }

    public void setMatchService(MatchService matchService)
    {
        this.matchService = matchService;
    }
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        matchService = (MatchService) ctx.getBean("MatchService");
        articleService = (ArticleService) ctx.getBean("ArticleService");
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
                        String strTotal = matchService.MatchTotal(strQuery, strStraTime, strEndTime);
            
                        String strJson = "";
                        List<Match> ls = matchService.MatchPaging(Integer.parseInt(strPageIndex),
                                Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                        for (Match item : ls)
                        {
                            strJson += matchService.getMatchInfo(item) + ",";
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
                    else if ("del".equalsIgnoreCase(methodName))
                    {
                        matchService.DelMatch(Long.parseLong(request.getParameter("id")));
                        out.print("true");
                    }
                    else if("detail".equalsIgnoreCase(methodName)){
                        String res="";
                        String strId = request.getParameter("id");
                        Article pt = articleService.GetArticleById(Long.parseLong(strId));
                    
                        res = articleService.getFullArticleInfo(pt);
                        //res = res.substring(0, res.length() - 1);
                        String strJson = "{\"Article\":"+res+", \"Keys\":"+matchService.getMatchKeyWords(Long.parseLong(strId))+"}";
                        out.print(strJson);
                    }
                } catch (Exception e)
                {
                    out.println("false");
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
