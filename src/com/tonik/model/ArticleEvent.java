package com.tonik.model;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * 
 * @hibernate.class table="ARTICLEEVENT"
 */
public class ArticleEvent
{
    private Long id;
    private Article article;
    private Event event;
    /**
     * @hibernate.id column="ARTICLEEVENT_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.many-to-one column="ARTICLEEVENT_ARTICLE" not-null="false" lazy="false" class="com.tonik.model.Article"
     * @return Returns the article.
     */
    public Article getArticle()
    {
        return article;
    }
    /**
     * @param article The article to set.
     */
    public void setArticle(Article article)
    {
        this.article = article;
    }
    
    /**
     * @hibernate.many-to-one column="ARTICLEEVENT_EVENT" not-null="false" lazy="false" class="com.tonik.model.Event"
     * @return Returns the event.
     */
    public Event getEvent()
    {
        return event;
    }
    /**
     * @param event The event to set.
     */
    public void setEvent(Event event)
    {
        this.event = event;
    }
   
}
