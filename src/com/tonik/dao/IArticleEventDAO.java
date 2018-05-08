package com.tonik.dao;

import java.util.List;

import com.tonik.model.Article;
import com.tonik.model.ArticleEvent;

public interface IArticleEventDAO extends IDAO
{
    public ArticleEvent getArticleEvent(Long articleEventId);
    
    public List<Object[]> getEventsByArticle(Article article);
    
    public void saveArticleEvent(ArticleEvent articleEvent);
    
    public void removeArticleEvent(ArticleEvent articleEvent);

    public ArticleEvent getArticleEventByArticleAndEvent(Long articleId, Long eventId);

    public List<Object[]> getArticlesByEvent(String strEventId);
}
