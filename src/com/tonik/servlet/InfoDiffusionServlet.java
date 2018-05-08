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

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.InfoDiffusion;
import com.tonik.service.InfoDiffusionService;

/**
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * @author fuzhi
 * @web.servlet name="InfoDiffusionServlet"
 * @web.servlet-mapping url-pattern="/servlet/getInfoDiffusion"
 */
@SuppressWarnings("serial")
public class InfoDiffusionServlet extends BaseServlet
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfSimple = new SimpleDateFormat("yyyy-MM-dd");
    InfoDiffusionService infoDiffusionService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        // TODO Auto-generated method stub
        super.init(config);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getWebApplicationContext(config.getServletContext());
        infoDiffusionService = (InfoDiffusionService) webApplicationContext.getBean("InfoDiffusionService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        if (this.sessionCheck(request, response))
        {
            out.write("sessionOut");
            return;
        }

        try
        {
            String method = request.getParameter("method");
            if (method != null && !"".equals(method))
            {
                if (method.equals("list"))
                {
                    String fPageIndex = request.getParameter("pageIndex");
                    String fpageSize = request.getParameter("pageSize");
                    int pageIndex = 1;
                    int pageSize = 10;
                    if (fPageIndex != null && fPageIndex.trim().length() > 0 && fpageSize != null
                            && fpageSize.trim().length() > 0)
                    {
                        pageIndex = Integer.valueOf(fPageIndex).intValue();
                        pageSize = Integer.valueOf(fpageSize).intValue();
                    }
                    List<InfoDiffusion> list = infoDiffusionService.getInfoDiffusion(pageIndex, pageSize);
                    int infoDiffusionTotal = infoDiffusionService.getInfoDiffusionTotal();
                    JSONObject jsonTotal = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    for (InfoDiffusion infoDiffusion : list)
                    {
                        JSONObject jsonObject = new JSONObject();
                        if (null == infoDiffusion.getId())
                        {
                            jsonObject.put("id", "");
                        }
                        else
                        {
                            jsonObject.put("id", infoDiffusion.getId());
                        }
                        if (null == infoDiffusion.getTitle())
                        {
                            jsonObject.put("title", "");
                        }
                        else
                        {
                            jsonObject.put("title", infoDiffusion.getTitle());
                        }
                        if (null == infoDiffusion.getAuthor())
                        {
                            jsonObject.put("author", "");
                        }
                        else
                        {
                            jsonObject.put("author", infoDiffusion.getAuthor());
                        }
                        if (null == infoDiffusion.getDate())
                        {
                            jsonObject.put("creatTime", "");
                        }
                        else
                        {
                            jsonObject.put("creatTime", infoDiffusion.getDate());
                        }
                        if (null == infoDiffusion.getKeyword())
                        {
                            jsonObject.put("keyword", "");
                        }
                        else
                        {
                            jsonObject.put("keyword", infoDiffusion.getKeyword());
                        }
                        jsonObject.put("type", infoDiffusion.getType());
                        jsonArray.put(jsonObject);
                    }
                    jsonTotal.put("total", infoDiffusionTotal);
                    jsonTotal.put("infoList", jsonArray);
                    out.write(jsonTotal.toString());
                }
                else if (method.equals("queryById"))
                {
                    int id = Integer.valueOf(request.getParameter("id")).intValue();
                    InfoDiffusion infoDiffusion = infoDiffusionService.getInfoDiffusionById(id);
                    JSONObject jsonObject = new JSONObject();
                    if (null == infoDiffusion.getId())
                    {
                        jsonObject.put("id", "");
                    }
                    else
                    {
                        jsonObject.put("id", infoDiffusion.getId());
                    }
                    if (null == infoDiffusion.getTitle())
                    {
                        jsonObject.put("title", "");
                    }
                    else
                    {
                        jsonObject.put("title", infoDiffusion.getTitle());
                    }
                    if (null == infoDiffusion.getAuthor())
                    {
                        jsonObject.put("author", "");
                    }
                    else
                    {
                        jsonObject.put("author", infoDiffusion.getAuthor());
                    }
                    if (null == infoDiffusion.getDate())
                    {
                        jsonObject.put("creatTime", "");
                    }
                    else
                    {
                        jsonObject.put("creatTime", infoDiffusion.getDate());
                    }
                    if (null == infoDiffusion.getKeyword())
                    {
                        jsonObject.put("keyword", "");
                    }
                    else
                    {
                        jsonObject.put("keyword", infoDiffusion.getKeyword());
                    }
                    jsonObject.put("type", infoDiffusion.getType());
                    if (null == infoDiffusion.getContext())
                    {
                        jsonObject.put("content", "");
                    }
                    else
                    {
                        jsonObject.put("content", infoDiffusion.getContext());
                    }
                    out.write(jsonObject.toString());
                }
                else if (method.equals("queryByKey"))
                {
                    String keyword = URLDecoder.decode(request.getParameter("keyword"), "utf-8");
                    String fPageIndex = request.getParameter("pageIndex");
                    String fpageSize = request.getParameter("pageSize");
                    int pageIndex = 1;
                    int pageSize = 10;
                    if (fPageIndex != null && fPageIndex.trim().length() > 0 && fpageSize != null
                            && fpageSize.trim().length() > 0)
                    {
                        pageIndex = Integer.valueOf(fPageIndex).intValue();
                        pageSize = Integer.valueOf(fpageSize).intValue();
                    }
                    int infoDiffusionTotalByKey = infoDiffusionService.getInfoDiffusionTotalByKey(keyword);
                    List<InfoDiffusion> list = infoDiffusionService.getInfoDiffusionByKey(keyword, pageIndex, pageSize);
                    JSONObject jsonTotal = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    for (InfoDiffusion infoDiffusion : list)
                    {
                        JSONObject jsonObject = new JSONObject();
                        if (null == infoDiffusion.getId())
                        {
                            jsonObject.put("id", "");
                        }
                        else
                        {
                            jsonObject.put("id", infoDiffusion.getId());
                        }
                        if (null == infoDiffusion.getTitle())
                        {
                            jsonObject.put("title", "");
                        }
                        else
                        {
                            jsonObject.put("title", infoDiffusion.getTitle());
                        }
                        if (null == infoDiffusion.getAuthor())
                        {
                            jsonObject.put("author", "");
                        }
                        else
                        {
                            jsonObject.put("author", infoDiffusion.getAuthor());
                        }
                        if (null == infoDiffusion.getDate())
                        {
                            jsonObject.put("creatTime", "");
                        }
                        else
                        {
                            jsonObject.put("creatTime", infoDiffusion.getDate());
                        }
                        if (null == infoDiffusion.getKeyword())
                        {
                            jsonObject.put("keyword", "");
                        }
                        else
                        {
                            jsonObject.put("keyword", infoDiffusion.getKeyword());
                        }
                        jsonObject.put("type", infoDiffusion.getType());
                        jsonArray.put(jsonObject);
                    }
                    jsonTotal.put("total", infoDiffusionTotalByKey);
                    jsonTotal.put("infoList", jsonArray);
                    out.write(jsonTotal.toString());
                }
                else if (method.equals("add"))
                {
                    InfoDiffusion infoDiffusion = new InfoDiffusion();
                    infoDiffusion.setTitle(URLDecoder.decode(request.getParameter("title"), "utf-8"));
                    infoDiffusion.setAuthor(URLDecoder.decode(request.getParameter("author"), "utf-8"));
                    infoDiffusion.setType(Integer.valueOf(URLDecoder.decode(request.getParameter("type"), "utf-8")));
                    infoDiffusion.setDate(sdf.parse(URLDecoder.decode(request.getParameter("createTime"), "utf-8")));
                    infoDiffusion.setKeyword(URLDecoder.decode(request.getParameter("keyword"), "utf-8"));
                    infoDiffusion.setContext(URLDecoder.decode(request.getParameter("content"), "utf-8"));
                    infoDiffusion.setTarget(Long.valueOf(URLDecoder.decode(request.getParameter("target"), "utf-8")));
                    infoDiffusionService.saveInfoDiffusion(infoDiffusion);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", infoDiffusion.getId());
                    jsonObject.put("title", infoDiffusion.getTitle());
                    jsonObject.put("author", infoDiffusion.getAuthor());
                    jsonObject.put("type", infoDiffusion.getType());
                    jsonObject.put("createTime", sdf.format(infoDiffusion.getDate()));
                    jsonObject.put("keyword", infoDiffusion.getKeyword());
                    jsonObject.put("content", infoDiffusion.getContext());
                    jsonObject.put("target", infoDiffusion.getTarget());
                    out.write(jsonObject.toString());
                }
                else if (method.equals("addByProduct"))
                {
                    String Title = URLDecoder.decode(request.getParameter("webName"), "utf-8")
                            + URLDecoder.decode(request.getParameter("title"), "utf-8");
                    String Content = "依照国标：" + URLDecoder.decode(request.getParameter("standardMessage"), "utf-8")
                            + "标准，海淘风险分析平台检出" + Title + "为不合格产品，不合格属性有："
                            + URLDecoder.decode(request.getParameter("content"), "utf-8");
                    InfoDiffusion infoDiffusion = new InfoDiffusion();
                    infoDiffusion.setTitle(Title + "不合格");
                    infoDiffusion.setDate(new Date());
                    infoDiffusion.setContext(Content);
                    infoDiffusionService.saveInfoDiffusion(infoDiffusion);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", infoDiffusion.getId());
                    jsonObject.put("title", infoDiffusion.getTitle());
                    jsonObject.put("author", infoDiffusion.getAuthor());
                    jsonObject.put("type", infoDiffusion.getType());
                    jsonObject.put("createTime", sdf.format(infoDiffusion.getDate()));
                    jsonObject.put("keyword", infoDiffusion.getKeyword());
                    jsonObject.put("content", infoDiffusion.getContext());
                    jsonObject.put("target", infoDiffusion.getTarget());
                    out.write(jsonObject.toString());
                }
            }
            else
            {
                out.write("方法名为空");
            }

        } catch (Exception e)
        {
            out.write("false");
        }
        finally
        {
            out.flush();
            out.close();
        }
    }
}
