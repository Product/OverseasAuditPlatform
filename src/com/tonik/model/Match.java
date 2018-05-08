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
 * @hibernate.class table="MATCH"
 */
public class Match
{
    private Long id;
    private Article article;
    private KeyWord keyWord;
    private String keyWordsName;
    
    /**
     * @hibernate.id column="MATCH_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.many-to-one column="MATCH_ARTICLE" not-null="false" lazy="false" class="com.tonik.model.Article"
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
     * @hibernate.many-to-one column="MATCH_KEYWORD" not-null="false" lazy="false" class="com.tonik.model.KeyWord"
     * @return Returns the keyWord.
     */
    public KeyWord getKeyWord()
    {
        return keyWord;
    }
    /**
     * @param keyWord The keyWord to set.
     */
    public void setKeyWord(KeyWord keyword)
    {
        this.keyWord = keyword;
    }
    
    public String getKeyWordsName(){
        return keyWordsName;
    }
    public void setKeyWordsName(String keyWordsName)
    {
        this.keyWordsName = keyWordsName;
    }
}
