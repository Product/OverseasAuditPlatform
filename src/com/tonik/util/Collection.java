package com.tonik.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thinvent.utils.DateUtil;
import com.tonik.dao.IArticleDAO;
import com.tonik.dao.IArticleEventDAO;
import com.tonik.dao.ICountryDAO;
import com.tonik.dao.IEventDAO;
import com.tonik.dao.IExpertRepositoryDAO;
import com.tonik.dao.IThirdReportDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.dao.IWebsiteStyleDAO;
import com.tonik.model.Article;
import com.tonik.model.ArticleEvent;
import com.tonik.model.ExpertRepository;
import com.tonik.model.ThirdReport;
import com.tonik.model.Website;

/**
 * @spring.bean id="Collection"
 * @author fuzhi
 * @spring.property name="websiteStyleDAO" ref="WebsiteStyleDAO"
 * @spring.property name="countryDAO" ref="CountryDAO"
 * @spring.property name="thirdReportDAO" ref="ThirdReportDAO"
 * @spring.property name="articleDAO" ref="ArticleDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="expertRepositoryDAO" ref="ExpertRepositoryDAO"
 * @spring.property name="articleEventDAO" ref="ArticleEventDAO"
 * @spring.property name="eventDAO" ref="EventDAO"
 */

public class Collection implements Runnable
{
    // property
    private String url;
    private String blockSign;
    private String titleSign;
    private String createTimeSign;
    private String nextSign;
    private int pageTotal;
    private String siteName;
    private int type;
    private String contentSign;

    // bean
    private IWebsiteStyleDAO websiteStyleDAO;
    private ICountryDAO countryDAO;
    private IThirdReportDAO thirdReportDAO;
    private IArticleDAO articleDAO;
    private IWebsiteDAO websiteDAO;
    private IExpertRepositoryDAO expertRepositoryDAO;
    private IArticleEventDAO articleEventDAO;
    private IEventDAO eventDAO;

    @Override
    public void run()
    {
        if(type == 5){
            getExpertRepositoryInfo();
        }else if (type == 2) {
            getAbroadInfo();
        }else if (type == 3) {
            getPublicOpinionInfo();
        }else{            
            getInfomation();
        }
    }

    // 国外第三方信息采集
    public void getAbroadInfo()
    {
        WebDriver webDriver = null;
        WebDriver webSubDriver = null;
        try
        {
            String title = "";
            String titleTrans = "";
            String cont = "";
            String conTrans = "";
            webDriver = new ChromeDriver();
            webSubDriver = new ChromeDriver();
            webDriver.get(url);
            List<WebElement> resultsAll = null;
            WebElement resultsTitle = null;
            WebElement resultsTime = null;
            WebElement nextPage = null;
            WebElement content = null;
            NiuTrans niuTrans = NiuTrans.getNiuTrans();
            for (int i = 0; i < pageTotal; i++)
            {
                // 查找数据
                try
                {
                    resultsAll = webDriver.findElements(By.cssSelector(blockSign));
                } catch (Exception e)
                {
                    System.out.println("退出：没有块数据");
                }
                // 过滤数据
                for (WebElement webElement : resultsAll)
                {
                    try
                    {
                        resultsTitle = webElement.findElement(By.cssSelector(titleSign));
                        resultsTime = webElement.findElement(By.cssSelector(createTimeSign));
                    } catch (Exception e)
                    {
                        System.out.println("继续：块内数据不完整");
                        continue;
                    }
                    if (null != resultsTitle.getText() && !"".equals(resultsTitle.getText())
                            && null != resultsTitle.getAttribute("href")
                            && !"".equals(resultsTitle.getAttribute("href")) && null != resultsTime.getText()
                            && !"".equals(resultsTime.getText()))
                    {
                        title = resultsTitle.getText();
                        titleTrans = niuTrans.translate(title);
                        ThirdReport thirdReport = new ThirdReport();
                        thirdReport.setUrl(resultsTitle.getAttribute("href"));
                        thirdReport.setTitle(resultsTitle.getText());
                        thirdReport.setTitleTrans(titleTrans);
                        thirdReport.setCreateTime(resultsTime.getText());
                        thirdReport.setGether(DateUtil.formatDate(new Date()));
                        thirdReport.setBrand(resultsTitle.getText());
                        thirdReport.setCountry("美国");
                        thirdReport.setSiteName(siteName);
                        thirdReport.setAddressType(Long.valueOf(1));
                        try{
                            webSubDriver.get(resultsTitle.getAttribute("href"));
                            content = webSubDriver.findElement(By.cssSelector(contentSign));
                            cont = content.getText();
                            conTrans = niuTrans.translate(cont);
                        }catch (Exception e) {
                            System.out.println("文章数据不存在");
                        }
                        thirdReport.setContent(cont);
                        thirdReport.setContentTrans(conTrans);
                        thirdReportDAO.save(thirdReport);
                    }
                }
                // 跳转翻页
                if (pageTotal > 1)
                {
                    try
                    {
                        nextPage = webDriver.findElement(By.cssSelector(nextSign));
                        nextPage.click();
                        Random random = new Random();
                        Thread.sleep(1000 + random.nextInt(1000));
                    } catch (Exception e)
                    {
                        System.out.println("退出：找不到翻页按钮");
                        break;
                    }
                }
            }
        } catch (Exception e)
        {
            System.out.println("专家知识库爬虫爬取异常");
            e.printStackTrace();
        }
        finally
        {
            webDriver.quit();
            webSubDriver.quit();
        }
    }


    // 专家知识库爬虫爬取
    public void getExpertRepositoryInfo()
    {
        WebDriver webDriver = null;
        try
        {
            webDriver = new ChromeDriver();
            webDriver.get(url);
            List<WebElement> resultsAll = null;
            WebElement resultsTitle = null;
            WebElement resultsTime = null;
            WebElement nextPage = null;
            for (int i = 0; i < pageTotal; i++)
            {
                // 查找数据
                try
                {
                    resultsAll = webDriver.findElements(By.cssSelector(blockSign));
                } catch (Exception e)
                {
                    System.out.println("退出：没有块数据");
                }
                // 过滤数据
                for (WebElement webElement : resultsAll)
                {
                    try
                    {
                        resultsTitle = webElement.findElement(By.cssSelector(titleSign));
                        resultsTime = webElement.findElement(By.cssSelector(createTimeSign));
                    } catch (Exception e)
                    {
                        System.out.println("继续：快内数据不完整");
                        continue;
                    }
                    if (null != resultsTitle.getText() && !"".equals(resultsTitle.getText())
                            && null != resultsTitle.getAttribute("href")
                            && !"".equals(resultsTitle.getAttribute("href")) && null != resultsTime.getText()
                            && !"".equals(resultsTime.getText()))
                    {
                        ExpertRepository expertRepository = new ExpertRepository();
                        expertRepository.setTitle(resultsTitle.getText());
                        expertRepository.setAuthor("网络爬取");
                        expertRepository.setContent(resultsTime.getText());
                        expertRepository.setUrl(resultsTitle.getAttribute("href"));
                        expertRepository.setCreatetime(new Date());
                        expertRepositoryDAO.save(expertRepository);
                    }
                }
                // 跳转翻页
                if (pageTotal > 1)
                {
                    try
                    {
                        nextPage = webDriver.findElement(By.cssSelector(nextSign));
                        nextPage.click();
                        Random random = new Random();
                        Thread.sleep(1000 + random.nextInt(1000));
                    } catch (Exception e)
                    {
                        System.out.println("退出：找不到翻页按钮");
                        break;
                    }
                }
            }
        } catch (Exception e)
        {
            System.out.println("专家知识库爬虫爬取异常");
            e.printStackTrace();
        }
        finally
        {
            webDriver.close();
        }
    }


    // 舆情文章爬虫爬取
    public void getPublicOpinionInfo()
    {
        Pattern pattern = Pattern.compile("(\\d{4}|\\d{2})(\\-|\\/|\\.|年)(\\d{1,2})(\\-|\\/|\\.|月)(\\d{1,2})");
        WebDriver webDriver = null;
        try
        {
            webDriver = new ChromeDriver();
            webDriver.get(url);
            List<WebElement> resultsAll = null;
            WebElement resultsTitle = null;
            WebElement resultsTime = null;
            WebElement nextPage = null;
            for (int i = 0; i < pageTotal; i++)
            {
                // 查找数据
                try
                {
                    resultsAll = webDriver.findElements(By.cssSelector(blockSign));
                } catch (Exception e)
                {
                    System.out.println("退出：没有块数据");
                }
                // 过滤数据
                for (WebElement webElement : resultsAll)
                {
                    try
                    {
                        resultsTitle = webElement.findElement(By.cssSelector(titleSign));
                        resultsTime = webElement.findElement(By.cssSelector(createTimeSign));
                    } catch (Exception e)
                    {
                        System.out.println("继续：快内数据不完整");
                        continue;
                    }
                    if (null != resultsTitle.getText() && !"".equals(resultsTitle.getText())
                            && null != resultsTitle.getAttribute("href")
                            && !"".equals(resultsTitle.getAttribute("href")) && null != resultsTime.getText()
                            && !"".equals(resultsTime.getText()))
                    {

                        Article article = new Article();
                        article.setTitle(resultsTitle.getText());
                        article.setLocation(resultsTitle.getAttribute("href"));
                        article.setCreateTime(resultsTime.getText());
                        Matcher matcher = pattern.matcher(resultsTime.getText());
                        if (matcher.find()) {
                            article.setCreateTime(matcher.group(1) + "-" + matcher.group(3) + "-" + matcher.group(5));
                        } else {
                            article.setCreateTime("发表于" + resultsTime.getText());
                        }
                        article.setGatherTime(new Date());
                        articleDAO.saveArticle(article);
                        if(null != contentSign && !"".equals(contentSign)){
                            ArticleEvent articleEvent = new ArticleEvent();
                            articleEvent.setArticle(article);
                            articleEvent.setEvent(eventDAO.getEvent(Long.valueOf(contentSign)));
                            articleEventDAO.saveArticleEvent(articleEvent);
                        }
                    }
                }
                // 跳转翻页
                if (pageTotal > 1)
                {
                    try
                    {
                        nextPage = webDriver.findElement(By.cssSelector(nextSign));
                        nextPage.click();
                        Random random = new Random();
                        Thread.sleep(1000 + random.nextInt(1000));
                    } catch (Exception e)
                    {
                        System.out.println("退出：找不到翻页按钮");
                        break;
                    }
                }
            }
        } catch (Exception e)
        {
            System.out.println("舆情文章爬虫爬取异常");
            e.printStackTrace();
        }
        finally
        {
            webDriver.close();
        }
    }
    
    
    // 第三方国内、网站 采集
    public void getInfomation()
    {
        // System.getProperties().setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver.exe");
        Pattern pattern = Pattern.compile("(\\d{4}|\\d{2})(\\-|\\/|\\.|年)(\\d{1,2})(\\-|\\/|\\.|月)(\\d{1,2})");
        WebDriver webDriver = null;
        try
        {
            webDriver = new ChromeDriver();
            webDriver.get(url);

            List<WebElement> resultsTitle = null;
            List<WebElement> resultsTime = null;
            WebElement nextPage = null;

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sdf.format(date);

            for (int i = 0; i < pageTotal; i++)
            {
                // 查找数据
                try
                {
                    resultsTitle = webDriver.findElements(By.cssSelector(titleSign));
                    resultsTime = webDriver.findElements(By.cssSelector(createTimeSign));
                } catch (Exception e)
                {
                    System.out.println("退出：找不到数据");
                    break;
                }
                // 过滤数据
                if (resultsTitle.size() == resultsTime.size())
                {
                    for (int j = 0; j < resultsTitle.size(); j++)
                    {
                        if (null != resultsTitle.get(j).getText() && !"".equals(resultsTitle.get(j).getText())
                                && null != resultsTitle.get(j).getAttribute("href")
                                && !"".equals(resultsTitle.get(j).getAttribute("href"))
                                && null != resultsTime.get(j).getText() && !"".equals(resultsTime.get(j).getText()))
                        {
                            //国内第三方报告爬虫
                            if (type == 1)
                            {
                                ThirdReport thirdReport = new ThirdReport();
                                thirdReport.setUrl(resultsTitle.get(j).getAttribute("href"));
                                thirdReport.setTitle(resultsTitle.get(j).getText());
                                Matcher matcher = pattern.matcher(resultsTime.get(j).getText());
                                if (matcher.find()) {
                                    thirdReport.setCreateTime(matcher.group(1) + "-" + matcher.group(3) + "-" + matcher.group(5));
                                } else {
                                    thirdReport.setCreateTime(resultsTime.get(j).getText());
                                }
                                thirdReport.setGether(strDate);
                                thirdReport.setInfoType("召回");
                                thirdReport.setSiteName(siteName);
                                thirdReport.setAddressType(Long.valueOf(0));
                                thirdReportDAO.save(thirdReport);
                            }
                            //网站爬虫
                            else if (type == 4)
                            {
                                Website website = new Website();
                                website.setName(resultsTitle.get(j).getText());
                                website.setLocation(resultsTitle.get(j).getAttribute("href"));
                                website.setWebStyle(websiteStyleDAO.getWebsiteStyle(Long.valueOf(2)));
                                website.setRemark(resultsTime.get(j).getText());
                                website.setCreateTime(date);
                                website.setCountry(countryDAO.getCountryById(Long.valueOf(751)));
                                website.setRegulatory(true);
                                website.setIntegrityDegree(0);
                                website.setComprehensiveScore(0);
                                website.setWebsiteType(2);
                                websiteDAO.saveWebsite(website);
                            }
                        }
                    }
                }

                // 跳转翻页
                if (pageTotal > 1)
                {
                    try
                    {
                        nextPage = webDriver.findElement(By.cssSelector(nextSign));
                        nextPage.click();
                        Random random = new Random();
                        Thread.sleep(1000 + random.nextInt(1000));
                    } catch (Exception e)
                    {
                        System.out.println("退出：找不到翻页按钮");
                        break;
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("未捕获异常");
        }
        finally
        {
            if (webDriver != null)
            {
                webDriver.quit();
            }
        }
    }


    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getBlockSign()
    {
        return blockSign;
    }

    public void setBlockSign(String blockSign)
    {
        this.blockSign = blockSign;
    }

    public String getTitleSign()
    {
        return titleSign;
    }

    public void setTitleSign(String titleSign)
    {
        this.titleSign = titleSign;
    }

    public String getCreateTimeSign()
    {
        return createTimeSign;
    }

    public void setCreateTimeSign(String createTimeSign)
    {
        this.createTimeSign = createTimeSign;
    }

    public String getNextSign()
    {
        return nextSign;
    }

    public void setNextSign(String nextSign)
    {
        this.nextSign = nextSign;
    }

    public int getPageTotal()
    {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal)
    {
        this.pageTotal = pageTotal;
    }

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
    public String getContentSign()
    {
        return contentSign;
    }

    public void setContentSign(String contentSign)
    {
        this.contentSign = contentSign;
    }

    
    
    public IWebsiteStyleDAO getWebsiteStyleDAO()
    {
        return websiteStyleDAO;
    }

    public void setWebsiteStyleDAO(IWebsiteStyleDAO websiteStyleDAO)
    {
        this.websiteStyleDAO = websiteStyleDAO;
    }

    public ICountryDAO getCountryDAO()
    {
        return countryDAO;
    }

    public void setCountryDAO(ICountryDAO countryDAO)
    {
        this.countryDAO = countryDAO;
    }

    public IArticleDAO getArticleDAO()
    {
        return articleDAO;
    }

    public void setArticleDAO(IArticleDAO articleDAO)
    {
        this.articleDAO = articleDAO;
    }

    public IThirdReportDAO getThirdReportDAO()
    {
        return thirdReportDAO;
    }

    public void setThirdReportDAO(IThirdReportDAO thirdReportDAO)
    {
        this.thirdReportDAO = thirdReportDAO;
    }

    public IWebsiteDAO getWebsiteDAO()
    {
        return websiteDAO;
    }

    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        this.websiteDAO = websiteDAO;
    }

    public IExpertRepositoryDAO getExpertRepositoryDAO()
    {
        return expertRepositoryDAO;
    }

    public void setExpertRepositoryDAO(IExpertRepositoryDAO expertRepositoryDAO)
    {
        this.expertRepositoryDAO = expertRepositoryDAO;
    }

    public IArticleEventDAO getArticleEventDAO()
    {
        return articleEventDAO;
    }

    public void setArticleEventDAO(IArticleEventDAO articleEventDAO)
    {
        this.articleEventDAO = articleEventDAO;
    }

    public IEventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(IEventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }
    
}