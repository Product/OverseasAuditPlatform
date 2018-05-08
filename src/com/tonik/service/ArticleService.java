package com.tonik.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tonik.Constant;
import com.tonik.dao.IArticleDAO;
import com.tonik.dao.IArticleEventDAO;
import com.tonik.dao.IPointOfTimeDAO;
import com.tonik.model.Article;

/**
 * @spring.bean id="ArticleService"
 * @spring.property name="articleDAO" ref="ArticleDAO"
 * @spring.property name="pointoftimeDAO" ref="PointOfTimeDAO"
 * @spring.property name="articleEventDAO" ref="ArticleEventDAO"
 */
public class ArticleService
{
    private IArticleDAO articleDAO;
    private IPointOfTimeDAO pointoftimeDAO;
    private IArticleEventDAO articleEventDAO;

    public IArticleDAO getArticleDAO()
    {
        return articleDAO;
    }

    public void setArticleDAO(IArticleDAO aritcleDAO)
    {
        this.articleDAO = aritcleDAO;
    }

    public IPointOfTimeDAO getPointoftimeDAO()
    {
        return pointoftimeDAO;
    }

    public void setPointoftimeDAO(IPointOfTimeDAO pointoftimeDAO)
    {
        this.pointoftimeDAO = pointoftimeDAO;
    }
    
    public IArticleEventDAO getArticleEventDAO()
    {
        return articleEventDAO;
    }

    public void setArticleEventDAO(IArticleEventDAO articleEventDAO)
    {
        this.articleEventDAO = articleEventDAO;
    }

    public List<Article> ArticlePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime, String strEventId)
    {
        List<Object[]> ls = new ArrayList<Object[]>();
        List<Article> ats = new ArrayList<Article>();
        if("-1".equals(strEventId)){
            ls= articleDAO.getArticlePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
            
        }else{
            List<Object[]> objs = articleEventDAO.getArticlesByEvent(strEventId);
            Set<Article> arts = new HashSet<Article>();
            for(Object[] obj:objs){
                arts.add((Article)obj[1]);
            }
            Long[] artsId = new Long[arts.size()];
            int i = 0;
            for(Article a:arts){
                artsId[i++] = a.getId();
            }
            ls= articleDAO.getArticleByEventPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime, artsId);
        }
        if(ls!=null)
        {
            for(Object[] obj:ls){
                Article at = (Article)obj[0];
                ats.add(at);
            }
        }
        return ats;
    }
    
    public String ArticleTotal(String strQuery, String strStraTime, String strEndTime, String strEventId)
    {
        String res = "0";
        if("-1".equals(strEventId))
            res = Integer.toString(articleDAO.getArticleTotal(strQuery, strStraTime, strEndTime));
        else
            res = Integer.toString(articleDAO.getArticleTotalByEvent(strQuery, strStraTime, strEndTime, Long.parseLong(strEventId)));
        return res;
    }
    
    public void SaveArticle(Article at)
    {   
        articleDAO.saveArticle(at);
    }
    
    public void DelArticle(Long id)
    {
        articleDAO.removeObject(Article.class, id);
    }
    
    public Article GetArticleById(Long id)
    {
        Article at = articleDAO.getArticle(id);
        return at;
    }
    
    public String getArticleInfo(Article pt){
        String res = "{\"Id\":\"" + pt.getId() + "\",\"Name\":\"" + pt.getTitle()
                + "\",\"Remark\":\"" + pt.getRemark()
                + "\",\"Location\":\"" + pt.getLocation()
                + "\",\"Validity\":\"" + pt.getValidity()
                + "\",\"CreateTime\":\"" + pt.getCreateTime()
                + "\",\"GatherTime\":\"" + Constant.getFormatTime(pt.getGatherTime())
                + "\",\"CreatePerson\":\"" + pt.getCreatePersonName()+"\"}";
        return res;
    }
    
    public String getFullArticleInfo(Article pt){
        String res = "{\"Id\":\"" + pt.getId() + "\",\"Name\":\"" + pt.getTitle()
                + "\",\"Remark\":\"" + pt.getRemark()
                + "\",\"Location\":\"" + pt.getLocation()
                + "\",\"Content\":\"" + pt.getContent()
                + "\",\"Validity\":\"" + pt.getValidity()
                + "\",\"CreateTime\":\"" + pt.getCreateTime()
                + "\",\"GatherTime\":\"" + Constant.getFormatTime(pt.getGatherTime())
                + "\",\"CreatePerson\":\"" + pt.getCreatePersonName()+"\"}";
        return res;
    }
    
    public List<Article> MatchAllByBrand(String name_cn)
    {
        return articleDAO.matchAllByBrand(name_cn);
    }
    
    public List<Article> MatchAllByPT(String name)
    {
        return articleDAO.matchAllByPT(name);
    }
    
    public List<Article> getArticle()
    {
        return articleDAO.getArticle();
    }
    
    public List<Article> getArticleRecent()
    {
        return articleDAO.getArticleRecent(pointoftimeDAO.getPointOfTime().getExtractTimeFormat());
    }
    
    public List<Article> getArticleByPage(int pageIndex, int pageSize)
    {
        return articleDAO.getArticleByPage(pageIndex, pageSize);
    }
    public int getArticleCount()
    {
        return articleDAO.getArticleCount();
    }
}
