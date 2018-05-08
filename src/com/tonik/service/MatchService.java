package com.tonik.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tonik.Constant;
import com.tonik.dao.IArticleDAO;
import com.tonik.dao.IMatchDAO;
import com.tonik.model.Article;
import com.tonik.model.KeyWord;
import com.tonik.model.Match;

/**
 * @spring.bean id="MatchService"
 * @spring.property name="matchDAO" ref="MatchDAO"
 * @spring.property name="articleDAO" ref="ArticleDAO"
 */
public class MatchService
{
    private IMatchDAO matchDAO;
    private IArticleDAO articleDAO;
    
    public IMatchDAO getMatchDAO()
    {
        return matchDAO;
    }

    public void setMatchDAO(IMatchDAO matchDAO)
    {
        this.matchDAO = matchDAO;
    }
    
    public IArticleDAO getArticleDAO()
    {
        return articleDAO;
    }

    public void setArticleDAO(IArticleDAO articleDAO)
    {
        this.articleDAO = articleDAO;
    }
	
    /**
     *获取文章相关的关键字
     *param: articleId 文章id
     */
    public String getMatchKeyWords(Long articleId){
        String key = "";
        String keys="";
        String keysName="";
        String keyIds="";
        String keyId = "";
        List<KeyWord> mks = matchDAO.getArticleKeyWords(articleId);
        Set<KeyWord> mkset = new HashSet<KeyWord>();
        mkset.addAll(mks);
        int pre = 0;
        for(KeyWord kw:mkset){
            if(kw.getCategory() != pre){
                keysName += "\""+kw.getCategoryStr()+"\",";
                if(pre != 0){
                    if(key.length() > 0){
                        key = key.substring(0, key.length()-1);
                        keyId = keyId.substring(0, keyId.length()-1);
                    }
                    keys += "["+key+"],";
                    key="";
                    keyIds += "["+keyId+"],";
                    keyId=""; 
                }
                pre=kw.getCategory();
            }
            key += "\""+kw.getName()+"\",";
            keyId += "\""+kw.getId()+"\",";
        }
        if(key.length() > 0){
            key = key.substring(0, key.length()-1);
            keys += "["+key+"],";
            keyId = keyId.substring(0, keyId.length()-1);
            keyIds += "["+keyId+"],";
        }
        if (keysName.length() > 0)
        {
            keysName = keysName.substring(0, keysName.length() - 1);
            keys = keys.substring(0, keys.length()-1);
            keyIds = keyIds.substring(0, keyIds.length()-1);
        }
        return "{\"KeysName\":["+keysName+"], \"KeysContent\":["+keys+"], \"KeysId\":["+keyIds+"]}";
    }
    
    public List<Match> MatchPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime)
    {
        List<Article> ls= matchDAO.getArticlePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        Set<Article> arts = new HashSet<Article>();
        arts.addAll(ls);
        List<Match> mts = new ArrayList<Match>();
        
        for(Article obj:arts){
            //String keys= "";
            //List<Object[]> mks = matchDAO.getArticleMatches(obj.getId());
            //for(Object[] mk:mks){
            //    KeyWord kw = (KeyWord)mk[1];
            //    keys += kw.getName()+" ";
            //}
            Article art = articleDAO.getArticle(obj.getId());
            Match match = new Match();
            match.setArticle(art);
            //match.setKeyWordsName(keys);
            mts.add(match);
        }
        return mts;
    }
    
    public String MatchTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(matchDAO.getMatchTotal(strQuery, strStraTime, strEndTime));
    }
    
    public void addMatch(Match match)
    {   
        matchDAO.saveMatch(match);
    }
    
    public void DelMatch(Long id)
    {
        List<Match> mks = matchDAO.getArticleMatches(id);
        for(Match mt:mks){
            matchDAO.removeMatch(mt);
        }
    }
    
    public Match GetMatchById(Long id)
    {
        Match at = matchDAO.getMatch(id);
        return at;
    }
    
    public String getMatchInfo(Match pt){
        String res = "{\"Id\":\"" + pt.getArticle().getId() + "\",\"Name\":\"" + pt.getArticle().getTitle()
                + "\",\"Remark\":\"" + pt.getArticle().getRemark()
                + "\",\"Location\":\"" + pt.getArticle().getLocation()
                + "\",\"Validity\":\"" + pt.getArticle().getValidity()
                + "\",\"CreateTime\":\"" + pt.getArticle().getCreateTime()
                + "\",\"GatherTime\":\"" + Constant.getFormatTime(pt.getArticle().getGatherTime())
                + "\",\"CreatePerson\":\"" + pt.getArticle().getCreatePersonName()
                + "\",\"KeyWords\":\"" + pt.getKeyWordsName() + "\"}";
        return res;
    }
    
    public void DelMatch()
    {
        matchDAO.DelMatch();
    }
}
