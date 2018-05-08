package com.tonik.dao;

import java.util.List;

import com.tonik.model.Article;

public interface IArticleDAO extends IDAO
{
    public List<Article> getArticle();
    
    public List<Article> getArticleRecent(String RecentTime);

    public Article getArticle(Long articleId);

    public void saveArticle(Article article);

    public void removeArticle(Article article);
    
    public List<Object[]> getArticlePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getArticleTotal(String strQuery, String strStraTime, String strEndTime);
    
    public List<Article> matchAllByBrand(String name_cn);
    
    public List<Article> matchAllByPT(String name); 	public Article getArticleByLocation(String location);

    /**
     * 通过事件id获取相关的舆情文章总数
     * @param strQuery
     * @param strStraTime
     * @param strEndTime
     * @param parseLong
     * @return
     */
    public int getArticleTotalByEvent(String strQuery, String strStraTime, String strEndTime, long parseLong);

    /**
     * 通过事件id分页获取相关的舆情文章
     * @param pageIndex
     * @param pageSize
     * @param strQuery
     * @param strStraTime
     * @param strEndTime
     * @param artsId
     * @return
     */
    public List<Object[]> getArticleByEventPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime, Long[] artsId);
    
    public List<Article> getArticleByPage(int pageIndex, int pageSize);

    public int getArticleCount();

    public List<Object[]> getArticleManageByPage(int pageIndex, int pageSize);
}
