package com.tonik.util;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.tonik.dao.IAreaDAO;
import com.tonik.dao.ICountryDAO;
import com.tonik.dao.IProductDefinitionDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.dao.hibernate.ProductDAOHibernate;
import com.tonik.model.Product;
import com.tonik.model.ProductStyle;
import com.tonik.model.ProductType;
import com.tonik.model.Website;

/**
 * @spring.bean id="CollectionCommodity"
 * @author fuzhi
 * @spring.property name="countryDAO" ref="CountryDAO"
 * @spring.property name="areaDAO" ref="AreaDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="productDefinitionDAO" ref="ProductDefinitionDAO"
 * @spring.property name="productDAO" ref="ProductDAO"
 */

public class CollectionCommodity implements Runnable
{
    //商品类型 
    private ProductDAOHibernate productDAO;
    //product参数=======================================================
    //采集任务id
    private long id;

    //商品类型 
    private ProductStyle productStyle;

    //所属一级商品类别
    private ProductType firstlevelType;
    //所属二级商品类别
    private ProductType secondlevelType;
    //所属三级商品类别
    private ProductType thirdlevelType;

    //所属产品评分 ？
    private double productDefineGrade;
    //所属网站评分 ？
    private double productWebisteGrade;
    //最终评分 ？
    private String powerValue;

    //采集参数========================================================
    //采集地址
    private String url;  
    //List内容
    private String allSign;  
    //标题标志
    private String titleSign;
    //价格标志
    private String priceSign;
    //销量标志
    private String saleMountSign;
    //下一页标志
    private String nextSign;
    //总页数
    private int totlePage;
    
    //品牌
    private String brandSign;
    //产地
    private String producingAreaSign;
    //适用年龄
    private String suitableAgeSign;
    //规格
    private String standardSign;
    //保质期
    private String expirationDateSign;
    //国家
    private String countrySign;
    //产品类型
    private String productTypeSign;

    //bean属性
    private ICountryDAO countryDAO;
    private IAreaDAO areaDAO;
    private IWebsiteDAO websiteDAO;
    private IProductDefinitionDAO productDefinitionDAO;
    
    
    public ICountryDAO getCountryDAO()
    {
        return countryDAO;
    }
    public void setCountryDAO(ICountryDAO countryDAO)
    {
        this.countryDAO = countryDAO;
    }
    
    public IAreaDAO getAreaDAO()
    {
        return areaDAO;
    }
    public void setAreaDAO(IAreaDAO areaDAO)
    {
        this.areaDAO = areaDAO;
    }
    
    public IWebsiteDAO getWebsiteDAO()
    {
        return websiteDAO;
    }
    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        this.websiteDAO = websiteDAO;
    }
    
    public IProductDefinitionDAO getProductDefinitionDAO()
    {
        return productDefinitionDAO;
    }
    public void setProductDefinitionDAO(IProductDefinitionDAO productDefinitionDAO)
    {
        this.productDefinitionDAO = productDefinitionDAO;
    }
    @Override
    public void run()
    {
        getCommodityInfo();
    }
    // 商品采集
    private void getCommodityInfo()
    {
        Pattern patternWebsite = Pattern.compile("\\w+\\.(\\w+)\\.\\w+");
        //Pattern patternBrand = Pattern.compile("\\w*");
        //Pattern patternProductType = Pattern.compile("(.*) (.*)");
        //System.getProperties().setProperty("webdriver.chrome.driver", "D:\\tools\\chromedriver.exe");
        WebDriver webDriver = null;
        //WebDriver webSubDriver = null;
        try
        {
            webDriver = new ChromeDriver();
            //webSubDriver = new ChromeDriver();      
            webDriver.get(url);
            List<WebElement> resultsAll = null;

            WebElement resultsTitle = null;
            WebElement resultsPrice = null;
            WebElement saleMount = null;
            
            //WebElement brand = null;
            //WebElement producingArea = null;
            //WebElement suitableAge = null;
            //WebElement standard = null;
            //WebElement expirationDate = null;
            //WebElement country = null;
            //WebElement productType = null;
            
            WebElement nextPage = null;
            String currentUrl = "";
            for (int i = 0; i < totlePage; i++)
            {
                // 查找数据
                try {
                    resultsAll = webDriver.findElements(By.cssSelector(allSign));
                    Thread.sleep(3000);
                    if(!currentUrl.equals(webDriver.getCurrentUrl())){
                        currentUrl = webDriver.getCurrentUrl();
                    } else {
                        System.out.println("退出：没有翻页");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("找不到list,继续下一页。");
                    continue;
                }
                // 过滤数据
                for (WebElement webElement : resultsAll) {
                    Product product = new Product();
                    product.setTid(id);
                    product.setCreateTime(new Date());
                    product.setProductType(productStyle);
                    product.setFirstlevelType(firstlevelType);
                    product.setSecondlevelType(secondlevelType);
                    product.setThirdlevelType(thirdlevelType);
                    Matcher matcherWebsite = patternWebsite.matcher(url);
                    if(matcherWebsite.find()){
                        List<Website> websiteList = websiteDAO.getWebsiteByLocation(matcherWebsite.group(1));
                        if(null != websiteList && websiteList.size() != 0){
                            for(Website website: websiteList){
                                if(null != website && website.getName() != null && !"".equals(website.getName().trim())){
                                    product.setWebsite(website);                            
                                    product.setWebsiteName(website.getName());
                                    break;
                                }
                            }
                            if(null == product.getWebsite()){            
                                product.setWebsiteName("对应网站名为空");
                            }
                        } else {
                            product.setWebsiteName("暂无对应网站");
                        }
                    }
                    try {
                        resultsTitle = webElement.findElement(By.cssSelector(titleSign));
                        product.setName(resultsTitle.getText());
                        product.setRemark(resultsTitle.getText());
                    } catch (Exception e) {
                        System.out.println("标题找不到,本条不抓取,继续下一条.");
                        continue;
                    }
                    try {
                        resultsPrice = webElement.findElement(By.cssSelector(priceSign));
                        product.setPrice(resultsPrice.getText());
                    } catch (Exception e) {
                        System.out.println("价格找不到");
                    }
                    try {
                        saleMount = webElement.findElement(By.cssSelector(saleMountSign));
                        if(null != saleMount.getText() && !"".equals(saleMount.getText())){
                            //product.setSales(Integer.valueOf(saleMount.getText() ));
                            product.setLocation(saleMount.getAttribute("href"));
                        }
                    } catch (Exception e) {
                        System.out.println("链接找不到,本条不抓取,继续下一条.");
                        continue;
                    }
                    /*
                    if(null != product.getLocation() && !"".equals(product.getLocation())){
                        try {
                            webSubDriver.get(product.getLocation());
                            try {
                                brand = webSubDriver.findElement(By.cssSelector(brandSign));
                                product.setBrand(brand.getText());
                            } catch (Exception e) {
                                System.out.println("品牌找不到");
                            }
                            try {
                                producingArea = webSubDriver.findElement(By.cssSelector(producingAreaSign));
                                product.setProducingArea(producingArea.getText());
                            } catch (Exception e) {
                                System.out.println("产地找不到");
                            }
                            try {
                                suitableAge = webSubDriver.findElement(By.cssSelector(suitableAgeSign));
                                product.setSuitableAge(suitableAge.getText());
                            } catch (Exception e) {
                                System.out.println("适用年龄找不到");
                            }
                            try {
                                standard = webSubDriver.findElement(By.cssSelector(standardSign));
                                product.setStandard(standard.getText());
                            } catch (Exception e) {
                                System.out.println("规格找不到");
                            }
                            try {
                                expirationDate = webSubDriver.findElement(By.cssSelector(expirationDateSign));
                                product.setExpirationDate(expirationDate.getText());
                            } catch (Exception e) {
                                System.out.println("保质期找不到");
                            }
                            try {
                                country = webSubDriver.findElement(By.cssSelector(countrySign));
                                product.setCountry(countryDAO.getCountryByName(country.getText()));
                                product.setArea(areaDAO.getAreaByCountryId(countryDAO.getCountryByName(country.getText()).getId()));
                            } catch (Exception e) {
                                System.out.println("国家找不到");
                            }
                            try {
                                productType = webSubDriver.findElement(By.cssSelector(productTypeSign));
                                if(productType.getText().contains(" ")){
                                    Matcher matcherProductType = patternProductType.matcher(productType.getText());
                                    if(null == brand){
                                        product.setPdid(productDefinitionDAO.getProductDefinitionByFeature(matcherProductType.group(2), "").getId());
                                    }else{
                                        Matcher matcherBrand = patternBrand.matcher(brand.getText());
                                        if("".equals(matcherBrand.group(0))){
                                            product.setPdid(productDefinitionDAO.getProductDefinitionByFeature(matcherProductType.group(2), brand.getText()).getId());
                                        }else{
                                            product.setPdid(productDefinitionDAO.getProductDefinitionByFeature(matcherProductType.group(2), matcherBrand.group(0)).getId());
                                        }
                                    }
                                }else{
                                    if(null == brand){
                                        product.setPdid(productDefinitionDAO.getProductDefinitionByFeature(productType.getText(), "").getId());
                                    }else{
                                        Matcher matcherBrand = patternBrand.matcher(brand.getText());
                                        if("".equals(matcherBrand.group(0))){
                                            product.setPdid(productDefinitionDAO.getProductDefinitionByFeature(productType.getText(), brand.getText()).getId());
                                        }else{
                                            product.setPdid(productDefinitionDAO.getProductDefinitionByFeature(productType.getText(), matcherBrand.group(0)).getId());
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("商品类型找不到");
                            }
                        } catch (Exception e) {
                            System.out.println("网址无效");
                        }
                    }*/
                    try{
                        productDAO.saveProduct(product);
                    }catch (Exception e) {
                        System.out.println("数据异常，不能保存。");
                    }
                }
                // 跳转翻页
                if(totlePage > 1){
                    try {
                        nextPage = webDriver.findElement(By.cssSelector(nextSign));
                        Random random = new Random();
                        Thread.sleep(1000 + random.nextInt(1000));
                        nextPage.click();
                    } catch (Exception e) {
                        System.out.println("退出：找不到翻页按钮");
                        break;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("查询中途退出");
        }
        finally
        {
            //webSubDriver.quit();
            webDriver.quit();
        }
    }
    

    //get、set方法
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    public ProductStyle getProductStyle()
    {
        return productStyle;
    }
    public void setProductStyle(ProductStyle productStyle)
    {
        this.productStyle = productStyle;
    }
    public ProductType getFirstlevelType()
    {
        return firstlevelType;
    }
    public void setFirstlevelType(ProductType firstlevelType)
    {
        this.firstlevelType = firstlevelType;
    }
    public ProductType getSecondlevelType()
    {
        return secondlevelType;
    }
    public void setSecondlevelType(ProductType secondlevelType)
    {
        this.secondlevelType = secondlevelType;
    }
    public ProductType getThirdlevelType()
    {
        return thirdlevelType;
    }
    public void setThirdlevelType(ProductType thirdlevelType)
    {
        this.thirdlevelType = thirdlevelType;
    }
    public double getProductDefineGrade()
    {
        return productDefineGrade;
    }
    public void setProductDefineGrade(double productDefineGrade)
    {
        this.productDefineGrade = productDefineGrade;
    }
    public double getProductWebisteGrade()
    {
        return productWebisteGrade;
    }
    public void setProductWebisteGrade(double productWebisteGrade)
    {
        this.productWebisteGrade = productWebisteGrade;
    }
    public String getPowerValue()
    {
        return powerValue;
    }
    public void setPowerValue(String powerValue)
    {
        this.powerValue = powerValue;
    }
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public String getAllSign()
    {
        return allSign;
    }
    public void setAllSign(String allSign)
    {
        this.allSign = allSign;
    }
    public String getTitleSign()
    {
        return titleSign;
    }
    public void setTitleSign(String titleSign)
    {
        this.titleSign = titleSign;
    }
    public String getPriceSign()
    {
        return priceSign;
    }
    public void setPriceSign(String priceSign)
    {
        this.priceSign = priceSign;
    }
    public String getSaleMountSign()
    {
        return saleMountSign;
    }
    public void setSaleMountSign(String saleMountSign)
    {
        this.saleMountSign = saleMountSign;
    }
    public String getNextSign()
    {
        return nextSign;
    }
    public void setNextSign(String nextSign)
    {
        this.nextSign = nextSign;
    }
    public int getTotlePage()
    {
        return totlePage;
    }
    public void setTotlePage(int totlePage)
    {
        this.totlePage = totlePage;
    }
    public String getBrandSign()
    {
        return brandSign;
    }
    public void setBrandSign(String brandSign)
    {
        this.brandSign = brandSign;
    }
    public String getProducingAreaSign()
    {
        return producingAreaSign;
    }
    public void setProducingAreaSign(String producingAreaSign)
    {
        this.producingAreaSign = producingAreaSign;
    }
    public String getSuitableAgeSign()
    {
        return suitableAgeSign;
    }
    public void setSuitableAgeSign(String suitableAgeSign)
    {
        this.suitableAgeSign = suitableAgeSign;
    }
    public String getStandardSign()
    {
        return standardSign;
    }
    public void setStandardSign(String standardSign)
    {
        this.standardSign = standardSign;
    }
    public String getExpirationDateSign()
    {
        return expirationDateSign;
    }
    public void setExpirationDateSign(String expirationDateSign)
    {
        this.expirationDateSign = expirationDateSign;
    }
    public String getCountrySign()
    {
        return countrySign;
    }
    public void setCountrySign(String countrySign)
    {
        this.countrySign = countrySign;
    }
    public String getProductTypeSign()
    {
        return productTypeSign;
    }
    public void setProductTypeSign(String productTypeSign)
    {
        this.productTypeSign = productTypeSign;
    }
    public ProductDAOHibernate getProductDAO()
    {
        return productDAO;
    }
    public void setProductDAO(ProductDAOHibernate productDAO)
    {
        this.productDAO = productDAO;
    }
    
}