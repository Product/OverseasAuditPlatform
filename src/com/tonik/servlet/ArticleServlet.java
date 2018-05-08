package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Article;
import com.tonik.model.UserInfo;
import com.tonik.service.ArticleService;

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
 * @web.servlet name="articleServlet"
 * @web.servlet-mapping url-pattern="/servlet/ArticleServlet"
 */
public class ArticleServlet extends BaseServlet
{

    private ArticleService articleService;


    public ArticleService getArticleService()
    {
        return articleService;
    }

    public void setArticleService(ArticleService articleService)
    {
        this.articleService = articleService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        articleService = (ArticleService) ctx.getBean("ArticleService");
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
        PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equalsIgnoreCase(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    String strEventId = request.getParameter("strEventId");

                    if ("".equals(strStraTime))
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    if ("".equals(strPageIndex))
                    {
                        strPageIndex = "1";
                    }
                    if ("".equals(strPageCount))
                    {
                        strPageCount = "100";
                    }
                    String strTotal = "", strJson = "";
                    List<Article> ls = new ArrayList<Article>();

                    strTotal = articleService.ArticleTotal(strQuery, strStraTime, strEndTime, strEventId);
                    ls = articleService.ArticlePaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount),
                            strQuery, strStraTime, strEndTime, strEventId);

                    for (Article item : ls)
                    {
                        strJson += articleService.getArticleInfo(item) + ",";
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
                if ("QueryListInit".equalsIgnoreCase(methodName))
                {
                    String strQuery = "";
                    String strStraTime = "1980-01-01 00:00:01";
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    String strEndTime = df.format(new Date());
                    String strPageIndex = "1";
                    String strPageCount = "100";
                    String strEventId = request.getParameter("strEventId");

                    String strTotal = "", strJson = "";
                    List<Article> ls = new ArrayList<Article>();

                    strTotal = articleService.ArticleTotal(strQuery, strStraTime, strEndTime, strEventId);
                    ls = articleService.ArticlePaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount),
                            strQuery, strStraTime, strEndTime, strEventId);

                    for (Article item : ls)
                    {
                        strJson += articleService.getArticleInfo(item) + ",";
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
                    Article pt = articleService.GetArticleById(Long.parseLong(request.getParameter("id")));

                    pt.setValidity(Boolean.valueOf(request.getParameter("vadility")));
                    pt.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));
                    pt.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    articleService.SaveArticle(pt);
                    String res = articleService.getArticleInfo(pt);

                    out.print(res);
                }
                else if ("del".equalsIgnoreCase(methodName))
                {
                    articleService.DelArticle(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                else if ("init".equalsIgnoreCase(methodName))
                {
                    String res = "";
                    String strId = request.getParameter("id");
                    Article pt = articleService.GetArticleById(Long.parseLong(strId));

                    res = articleService.getArticleInfo(pt);

                    out.print(res);
                }
                else if ("detail".equalsIgnoreCase(methodName))
                {
                    String res = "";
                    String strId = request.getParameter("id");
                    Article pt = articleService.GetArticleById(Long.parseLong(strId));

                    res = articleService.getFullArticleInfo(pt);

                    out.print(res);
                }
                // 獲取第三方信息分頁列表：
                else if ("context".equalsIgnoreCase(methodName))
                {
                    String pageIndex = request.getParameter("pageIndex");
                    String pageSize = request.getParameter("pageSize");
                    List<Article> list = articleService.getArticleByPage(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
                    int articleCount = articleService.getArticleCount();
                    JSONObject jsonObjectTotle = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    for (Article article : list)
                    {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("title", article.getTitle());
                        jsonObject.put("url", article.getLocation());
                        jsonObject.put("createTime", article.getGatherTime());
                        jsonObject.put("webSite", "暂无");
                        jsonObject.put("type", "召回");
                        jsonArray.put(jsonObject);
                    }
                    jsonObjectTotle.put("articleData", jsonArray);
                    jsonObjectTotle.put("articleCount", articleCount);
                    out.print(jsonObjectTotle);
                }
            } catch (Exception e)
            {
                out.println("false");
            }
            finally
            {
                out.flush();
                out.close();
            }
        }
    }
}
