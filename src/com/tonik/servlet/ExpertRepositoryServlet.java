package com.tonik.servlet;

import java.io.IOException;
import java.io.Writer;
import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.service.ExpertRepositoryService;

/**
 * @desc: 专家数据库servlet层
 * @author fuzhi
 * @web.servlet name="ExpertRepository"
 * @web.servlet-mapping url-pattern="/servlet/repositoryServlet"
 */
@SuppressWarnings("serial")
public class ExpertRepositoryServlet extends BaseServlet
{
    private ExpertRepositoryService expertRepositoryService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        expertRepositoryService = (ExpertRepositoryService) wac.getBean("ExpertRepositoryService");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        Writer write = response.getWriter();
        if(sessionCheck(request, response)){
            write.write("sessionOut");
            return;
        }
        String methodName = request.getParameter("methodName");
        if (null != methodName && !"".equals(methodName)){
            String str = "false";
            if (methodName.equals("init")){
                String index = request.getParameter("strPageIndex");
                String size = request.getParameter("strPageCount");
                try{
                    str = expertRepositoryService.initList(index, size);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                write.write(str);
            }else if(methodName.equals("queryById")){
                String id = request.getParameter("id");
                try
                {
                    str = expertRepositoryService.queryById(id);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                write.write(str);
            }
            else if (methodName.equals("getList")){
                String index = request.getParameter("strPageIndex");
                String size = request.getParameter("strPageCount");
                String queryStr = URLDecoder.decode(request.getParameter("queryStr"), "utf-8");
                String typeId = URLDecoder.decode(request.getParameter("typeId"), "utf-8");
                try{
                    str = expertRepositoryService.queryListByKey(index, size, queryStr, typeId);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                write.write(str);
            }
            else if (methodName.equals("save")){
                String id = URLDecoder.decode(request.getParameter("id"), "utf-8");
                String title = URLDecoder.decode(request.getParameter("title"), "utf-8");
                String createtime = URLDecoder.decode(request.getParameter("createtime"), "utf-8");
                String author = URLDecoder.decode(request.getParameter("author"), "utf-8");
                String content = URLDecoder.decode(request.getParameter("content"), "utf-8");
                String typeId = URLDecoder.decode(request.getParameter("typeId"), "utf-8");
                try{
                    str = expertRepositoryService.update(id, title, createtime, author, content, typeId);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                write.write(str);
            }
            else if (methodName.equals("del")){
                String id = URLDecoder.decode(request.getParameter("id"), "utf-8");
                try
                {                    
                    str = expertRepositoryService.del(id);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                write.write(str);
            }
            write.flush();
            write.close();
        }
    }
}
