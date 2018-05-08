package com.tonik.dao;

import java.util.List;

import com.tonik.model.Article;
import com.tonik.model.KeyWord;
import com.tonik.model.Match;

public interface IMatchDAO extends IDAO
{
    public List<Match> Match();

    public Match getMatch(Long matchId);

    public void saveMatch(Match match);

    public void removeMatch(Match match);
    
    public List<Match> getArticleMatches(Long articleId); 
    
    public List<Article> getArticlePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getMatchTotal(String strQuery, String strStraTime, String strEndTime);
    
    public void DelMatch();

    public List<KeyWord> getArticleKeyWords(Long articleId);
}
