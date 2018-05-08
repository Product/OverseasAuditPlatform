package com.tonik.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Area;
import com.tonik.model.Article;
import com.tonik.model.Brand;
import com.tonik.model.Country;
import com.tonik.model.KeyWord;
import com.tonik.model.Match;
import com.tonik.model.Material;
import com.tonik.model.PointOfTime;
import com.tonik.model.ProductType;
import com.tonik.service.AreasService;
import com.tonik.service.ArticleService;
import com.tonik.service.BrandService;
import com.tonik.service.CountryService;
import com.tonik.service.KeyWordService;
import com.tonik.service.MatchService;
import com.tonik.service.MaterialsService;
import com.tonik.service.NewsExtractService;
import com.tonik.service.PointOfTimeService;
import com.tonik.service.ProductTypeService;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @web.servlet name="newsExtractServlet"
 * @web.servlet-mapping url-pattern="/servlet/NewsExtractServlet"
 */
@SuppressWarnings("serial")
public class NewsExtractServlet extends BaseServlet
{
    private NewsExtractService NewsExtractService;
    private BrandService BrandService;
    private ArticleService ArticleService;
    private MatchService MatchService;
    private ProductTypeService ProductTypeService;
    private MaterialsService MaterialsService;
    private KeyWordService KeyWordService;
    private AreasService AreasService;
    private CountryService CountryService;
    private PointOfTimeService PointOfTimeService;
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        NewsExtractService = (NewsExtractService)ctx.getBean("NewsExtractService");
        BrandService = (BrandService)ctx.getBean("BrandService");
        ArticleService = (ArticleService)ctx.getBean("ArticleService");
        MatchService = (MatchService)ctx.getBean("MatchService");
        ProductTypeService = (ProductTypeService)ctx.getBean("ProductTypeService");
        MaterialsService = (MaterialsService)ctx.getBean("MaterialsService");
        KeyWordService = (KeyWordService)ctx.getBean("KeyWordService");
        CountryService = (CountryService)ctx.getBean("CountryService");
        AreasService = (AreasService)ctx.getBean("AreasService");
        PointOfTimeService = (PointOfTimeService)ctx.getBean("PointOfTimeService");
    }
    
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");  
        if(this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if("MatchAll".equals(methodName))
                {
                    MatchService.DelMatch();
                    KeyWordService.DelKeyWord();
                    List<Article> articless= ArticleService.getArticle();
                    for(Article article:articless)
                    {
                        String content = article.getContent();
                        for(String item:request.getParameter("strValue").split("_"))
                        {
                            if("1".equals(item))   //匹配商品种类
                            {
                                List<ProductType> productTypes = ProductTypeService.getProductTypeList();
                                for(ProductType productType:productTypes)
                                {
                                    if(content.contains(productType.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(productType.getName(),1)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(productType.getName(),1);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else
                                        {
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(productType.getName());
                                            keyword.setCategory(1);
                                            keyword.setCategoryId(productType.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                    }
                                }
                            }
                            else if("2".equals(item))   //匹配品牌
                            {
                                List<Brand> brands = BrandService.getBrandList();
                                for(Brand brand:brands)
                                {
                                    if(content.contains(brand.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(brand.getName(),2)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(brand.getName(),2);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else
                                        {
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(brand.getName_cn());
                                            keyword.setCategory(2);
                                            keyword.setCategoryId(brand.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        
                                    }
                                }
                            }
                            else if("3".equals(item))    //匹配国家/地区
                            {
                                List<Country> countries = CountryService.getCountryList();
                                for(Country country:countries)
                                {
                                    
                                    if(content.contains(country.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(country.getName(),3)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(country.getName(),3);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else{
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(country.getName());
                                            keyword.setCategory(3);
                                            keyword.setCategoryId(country.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                    }
                                }
                                List<Area> areas = AreasService.getArea();
                                for(Area area:areas)
                                {
                                    if(content.contains(area.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(area.getName(),5)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(area.getName(),5);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else{
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(area.getName());
                                            keyword.setCategory(5);
                                            keyword.setCategoryId(area.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);   
                                        }
                                    }
                                }
                            }
                            else if("4".equals(item))    // 匹配原料
                            {
                                List<Material> materials = MaterialsService.getMaterial();
                                for(Material material:materials)
                                {
                                    if(content.contains(material.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(material.getName(),4)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(material.getName(),4);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else{
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(material.getName());
                                            keyword.setCategory(4);
                                            keyword.setCategoryId(material.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    PointOfTime pointoftime = PointOfTimeService.getPointOfTime();
                    pointoftime.setExtractTime(new Date());
                    PointOfTimeService.UpdateTime(pointoftime);
                    response.getWriter().write("true");
                }
                
                else if("MatchPart".equals(methodName))
                {
                    List<Article> articless= ArticleService.getArticleRecent();
                    for(Article article:articless)
                    {
                        String content = article.getContent();
                        for(String item:request.getParameter("strValue").split("_"))
                        {
                            if("1".equals(item))   //匹配商品种类
                            {
                                List<ProductType> productTypes = ProductTypeService.getProductTypeList();
                                for(ProductType productType:productTypes)
                                {
                                    if(content.contains(productType.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(productType.getName(),1)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(productType.getName(),1);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else
                                        {
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(productType.getName());
                                            keyword.setCategory(1);
                                            keyword.setCategoryId(productType.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                    }
                                }
                            }
                            else if("2".equals(item))   //匹配品牌
                            {
                                List<Brand> brands = BrandService.getBrandList();
                                for(Brand brand:brands)
                                {
                                    if(content.contains(brand.getName()))   //brand.getName_cn().matches(content)
                                    {
                                        if(NewsExtractService.NewsExtractTotal(brand.getName(),2)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(brand.getName(),2);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else
                                        {
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(brand.getName());
                                            keyword.setCategory(2);
                                            keyword.setCategoryId(brand.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                    }
                                }
                            }
                            else if("3".equals(item))    //匹配国家/地区
                            {
                                List<Country> countries = CountryService.getCountryList();
                                for(Country country:countries)
                                {
                                    if(content.contains(country.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(country.getName(),3)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(country.getName(),3);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else{
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(country.getName());
                                            keyword.setCategory(3);
                                            keyword.setCategoryId(country.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                    }
                                }
                                List<Area> areas = AreasService.getArea();
                                for(Area area:areas)
                                {
                                    if(content.contains(area.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(area.getName(),5)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(area.getName(),5);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else{
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(area.getName());
                                            keyword.setCategory(5);
                                            keyword.setCategoryId(area.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                    }
                                }
                            }
                            else if("4".equals(item))    // 匹配原料
                            {
                                List<Material> materials = MaterialsService.getMaterial();
                                for(Material material:materials)
                                {
                                    if(content.contains(material.getName()))
                                    {
                                        if(NewsExtractService.NewsExtractTotal(material.getName(),4)>0)
                                        {
                                            KeyWord keyword = NewsExtractService.NewsExtract(material.getName(),4);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                        else{
                                            KeyWord keyword= new KeyWord();
                                            keyword.setName(material.getName());
                                            keyword.setCategory(4);
                                            keyword.setCategoryId(material.getId());
                                            NewsExtractService.SaveKeyWord(keyword);
                                            Match match= new Match();
                                            match.setKeyWord(keyword);
                                            match.setArticle(article);
                                            MatchService.addMatch(match);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    PointOfTime pointoftime = PointOfTimeService.getPointOfTime();
                    pointoftime.setExtractTime(new Date());
                    PointOfTimeService.UpdateTime(pointoftime);
                    response.getWriter().write("true");
                }
                
            }catch(Exception e){
                response.getWriter().write("false");
                }
            }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
