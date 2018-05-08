package com.tonik.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hankcs.hanlp.seg.common.Term;
import com.thinvent.translate.IdentifyEnglish;
import com.thinvent.translate.Translation;
import com.thinvent.utils.PropertiesUtil;
import com.thinvent.wordparser.HanlapOperation;
import com.thinvent.wordparser.checkFilepath;
import com.tonik.dao.IArticleDAO;
import com.tonik.model.Article;
/**
 * @spring.bean id="ArticleManagementService"
 * @spring.property name="articleDAO" ref="ArticleDAO"
 */

public class ArticleManagementService
{
    private HanlapOperation hanlapOperation = null;
    private IArticleDAO articleDAO;

    private String ip;
    private String from;
    private String to;

    public IArticleDAO getArticleDAO()
    {
        return articleDAO;
    }

    public void setArticleDAO(IArticleDAO articleDAO)
    {
        this.articleDAO = articleDAO;
    }

    public boolean initTranslationConnection()
    {
        try
        {

            PropertiesUtil.readProperties("translation.properties");
            this.ip = PropertiesUtil.getProperty("tranlationServerIp");
            this.from = PropertiesUtil.getProperty("from");
            this.to = PropertiesUtil.getProperty("to");

        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String doTranslation(String input_text)
    {
        initTranslationConnection();
        Translation translation = new Translation(this.ip, this.from, this.to);
        String output = translation.doJob(input_text);
        return output;

    }

    public Object getArticle(Long id)
    {
        Article article = articleDAO.getArticle(id);
        String translatedtitle = "";
        String translatedContent = "";
        articalVo aVo = new articalVo();
        String title = article.getTitle();
        String content = article.getContent();
        if (!IdentifyEnglish.isChinese(title))
        {
            translatedtitle = doTranslation(title);
            translatedContent = doTranslation(content);
        }
        
        aVo.setArticletitle(title);
        aVo.setArticleContent(content);
        aVo.setArticleCreateTime(article.getCreateTime());
        aVo.setArticlesourceUrl(article.getLocation());
        aVo.setTranslatedtitle(translatedtitle);
        aVo.setTranslatedContent(translatedContent);
        
        if(new checkFilepath().checkExists())
        {
            hanlapOperation = new HanlapOperation();
        if(!translatedtitle.equals("")){
            
            aVo.setKeywords( hanlapOperation.keyWordExtractOperation(translatedtitle, 4));
            aVo.setClassification(getClassication(translatedtitle));
        }else{
            aVo.setKeywords(hanlapOperation.keyWordExtractOperation(title, 4));
            aVo.setClassification(getClassication(title));
        }
        }
        return aVo;
    }
    public HashMap<String, Object> getArticleManagementPagelist(int pageIndex, int pageSize)
    {
        List<Object[]> ls = new ArrayList<Object[]>();
        List<articalVo> artvo = new ArrayList<articalVo>();
        ls = articleDAO.getArticleManageByPage(pageIndex, pageSize);
        for (Object[] article : ls)
        {
            String translatedtitle = "";
            //String translatedContent = "";
            articalVo aVo = new articalVo();
            String title = (String)article[1];
            //String content = article.getContent();
            if (!IdentifyEnglish.isChinese(title))
            {
                translatedtitle = doTranslation(title);
                //translatedContent = doTranslation(content);
            }
            hanlapOperation = new HanlapOperation();
            aVo.setArticleId((Long)article[0]);
            aVo.setArticletitle(title);
            //aVo.setArticleContent(content);
            aVo.setArticleCreateTime((String)article[2]);
            aVo.setArticlesourceUrl((String)article[3]);
            aVo.setTranslatedtitle(translatedtitle);
            //aVo.setTranslatedContent(translatedContent);
            if(new checkFilepath().checkExists())
            {
            if(!translatedtitle.equals("")){
                aVo.setKeywords( hanlapOperation.keyWordExtractOperation(translatedtitle, 4));
                aVo.setClassification(getClassication(translatedtitle));
            }else{
                aVo.setKeywords(hanlapOperation.keyWordExtractOperation(title, 4));
                aVo.setClassification(getClassication(title));
            }
            }
            artvo.add(aVo);
        }

        int articleManagementCount = articleDAO.getArticleCount();

        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("articleManagementList", artvo);
        res.put("articleManagementCount", articleManagementCount);
        return res;
    }

    public String getClassication(String input)
    {
        List<Term> wordlist = hanlapOperation.getwordList(input);
        String ClassType = null;
        for (Term term : wordlist)
        {
            if (term.nature.toString().equalsIgnoreCase("nf"))
            {
                ClassType = "食品";
                break;

            }
            else if (term.nature.toString().equalsIgnoreCase("nh") || term.nature.toString().equalsIgnoreCase("nhm")
                    || term.nature.toString().equalsIgnoreCase("nhd"))
            {
                ClassType = "医药疾病";
                break;
            }
            else if (term.nature.toString().equalsIgnoreCase("nab") || term.nature.toString().equalsIgnoreCase("nbc")
                    || term.nature.toString().equalsIgnoreCase("nbc"))
            {
                ClassType = "动物植物";
                break;
            }
            else
            {
                ClassType = "未知";

            }

        }
        return ClassType;
    }


    public class articalVo
    {
        private Long articleId; 
        
        private String articletitle;
        
        private String articleContent;

        private String translatedtitle;
        
        private String translatedContent;

        private List<String> keywords;

        private String classification;

        private String articlesourceUrl;

        private String articleCreateTime;


        public Long getArticleId()
        {
            return articleId;
        }

        public void setArticleId(Long articleId)
        {
            this.articleId = articleId;
        }

        public String getArticletitle()
        {
            return articletitle;
        }

        public void setArticletitle(String articletitle)
        {
            this.articletitle = articletitle;
        }

        public String getArticleContent()
        {
            return articleContent;
        }

        public void setArticleContent(String articleContent)
        {
            this.articleContent = articleContent;
        }

        public String getTranslatedtitle()
        {
            return translatedtitle;
        }

        public void setTranslatedtitle(String translatedtitle)
        {
            this.translatedtitle = translatedtitle;
        }

        public String getTranslatedContent()
        {
            return translatedContent;
        }

        public void setTranslatedContent(String translatedContent)
        {
            this.translatedContent = translatedContent;
        }

        public List<String> getKeywords()
        {
            return keywords;
        }

        public void setKeywords(List<String> keywords)
        {
            this.keywords = keywords;
        }

        public String getClassification()
        {
            return classification;
        }

        public void setClassification(String classification)
        {
            this.classification = classification;
        }

        public String getArticlesourceUrl()
        {
            return articlesourceUrl;
        }

        public void setArticlesourceUrl(String articlesourceUrl)
        {
            this.articlesourceUrl = articlesourceUrl;
        }

        public String getArticleCreateTime()
        {
            return articleCreateTime;
        }

        public void setArticleCreateTime(String articleCreateTime)
        {
            this.articleCreateTime = articleCreateTime;
        }
    }
}
