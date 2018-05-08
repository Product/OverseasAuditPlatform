package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IArticleDAO;
import com.tonik.dao.IArticleEventDAO;
import com.tonik.dao.IEventDAO;
import com.tonik.model.Article;
import com.tonik.model.ArticleEvent;
import com.tonik.model.Event;

/**
 * @spring.bean id="ArticleEventService"
 * @spring.property name="articleDAO" ref="ArticleDAO"
 * @spring.property name="eventDAO" ref="EventDAO"
 * @spring.property name="articleEventDAO" ref="ArticleEventDAO"
 */
public class ArticleEventService
{
    private IArticleEventDAO articleEventDAO;
    private IArticleDAO articleDAO;
    private IEventDAO eventDAO;
    
    /**
     * setter and getter
     */
    public IArticleEventDAO getArticleEventDAO()
    {
        return articleEventDAO;
    }
    public void setArticleEventDAO(IArticleEventDAO articleEventDAO)
    {
        this.articleEventDAO = articleEventDAO;
    }
    public IArticleDAO getArticleDAO()
    {
        return articleDAO;
    }
    public void setArticleDAO(IArticleDAO articleDAO)
    {
        this.articleDAO = articleDAO;
    }
    public IEventDAO getEventDAO()
    {
        return eventDAO;
    }
    public void setEventDAO(IEventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }
    
    /**
     * 保存ArticleEvent
     * @param: ae ArticleEvent
     */
    public void saveArticleEvent(ArticleEvent ae){
        articleEventDAO.saveArticleEvent(ae);
    }
    
    /**
     * 删除相应id的ArticleEvent
     * @param: articleEventId Long
     */
    public void removeArticleEvent(Long articleEventId){
        ArticleEvent ae = articleEventDAO.getArticleEvent(articleEventId);
        articleEventDAO.removeArticleEvent(ae);
    }
    
    /**
     * 获取相应id的article关联event
     * @param: articleId Long
     * @return: List<Event>
     */
    public List<Event> getEventsByArticle(Long articleId){
        Article article = articleDAO.getArticle(articleId);
        List<Object[]> articleEvents = articleEventDAO.getEventsByArticle(article);
        List<Event> events = new ArrayList<Event>();
        for(Object[] obj:articleEvents){
            Event event = (Event)obj[2];
            events.add(event);
        }
        return events;
    }
    
    /**
     * 根据事件id和文章id创建ArticleEvent
     * @param: eid Long
     * @param: aid Long
     * @return: 返回关联的事件信息: Event
     */
    public Event saveArticleEvent(Long eid, Long aid)
    {
        Article article = articleDAO.getArticle(aid);
        Event event = eventDAO.getEvent(eid);
        ArticleEvent ae = new ArticleEvent();
        ae.setArticle(article);
        ae.setEvent(event);
        articleEventDAO.saveArticleEvent(ae);
        return event;
    }
    
    /**
     * 根据事件id和文章id删除相应的ArticleEvent
     * @param: eventId Long
     * @param: articleId Long
     * @return: void
     */
    public void removeArticleEvent(Long articleId, Long eventId){
        ArticleEvent ae = articleEventDAO.getArticleEventByArticleAndEvent(articleId, eventId);
        articleEventDAO.removeArticleEvent(ae);
    }
    
    /**
     * 分页查找位于该文章关联的所有的事件
     * @param: aet List<Event>
     * @param: pageIndex int
     * @param: pageSize int
     * @param: strQuery String
     * @param: strStartTime String
     * @param: strEndTime String
     * @return: List<Event>
     */
    public List<Event> getUnrelatedEvents(List<Event> aet, int pageIndex, int pageSize, String strQuery, String strStartTime,
            String strEndTime){
        return eventDAO.getUnrelatedEventPaging(aet, pageIndex, pageSize, strQuery, strStartTime, strEndTime);
    }
    
    /**
     * 查找位于该文章关联的所有的事件的总数目
     * @param: events List<Event>
     * @param: strQuery String
     * @param: strStartTime String
     * @param: strEndTime String
     * @return: void
     */
    public String getUnrelatedEventsTotal(List<Event> events, String strQuery, String strStartTime, String strEndTime)
    {
        return Integer.toString(eventDAO.getUnrelatedEventsTotal(events, strQuery, strStartTime, strEndTime));
    }
}
