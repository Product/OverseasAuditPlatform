package com.tonik.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.ibm.wsdl.Constants;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.thinvent.common.rules.def.IPortletRulesProcessor;
import com.thinvent.common.rules.def.RulesException;
import com.thinvent.common.util.rule.DroolsDeployer;
import com.thinvent.rules.business.drools.DroolsProcessor;
import com.thinvent.utils.DateUtil;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IDRLFileDAO;
import com.tonik.dao.IMismatchProductDAO;
import com.tonik.dao.IProductDAO;
import com.tonik.model.DRLFile;
import com.tonik.model.MismatchProduct;
import com.tonik.model.MismatchProductForm;
import com.tonik.model.Product;
import com.tonik.rule.RuleException;
import com.tonik.standard.rules.Entity;
import com.tonik.standard.rules.WordSimilarity;

/**
 * @spring.bean id="MismatchProductService"
 * @spring.property name="mismatchProductDAO" ref="MismatchProductDAO"
 * @spring.property name="rulesengine" ref="RulesProcessorLookup"
 * @spring.property name="productDAO" ref="ProductDAO"
 * @spring.property name="productService" ref="ProductService"
 * @spring.property name="drlFileDAO" ref="DRLFileDAO"
 */

public class MismatchProductService
{
    private IMismatchProductDAO MismatchProductDAO;
    private IPortletRulesProcessor rulesengine;
    private IProductDAO ProductDAO;
    private ProductService productService;
    private IDRLFileDAO drlFileDAO;


    public ProductService getProductService()
    {
        return productService;
    }

    public void setProductService(ProductService productService)
    {
        this.productService = productService;
    }

    public IMismatchProductDAO getMismatchProductDAO()
    {
        return MismatchProductDAO;
    }

    public void setMismatchProductDAO(IMismatchProductDAO mismatchProductDAO)
    {
        MismatchProductDAO = mismatchProductDAO;
    }

    public List<Object[]> getMismatchProductPaging(int pageIndex, int pageSize, Long[] ptl, Long standardId,
            String orderBy, String orderType)
    {
        return MismatchProductDAO.getMismatchProductPaging(pageIndex, pageSize, ptl, standardId, orderBy, orderType);
    }

    public int getMismatchProductTotal(Long[] ptl, Long standardId)
    {
        return MismatchProductDAO.getMismatchProductTotal(ptl, standardId);
    }

    public void setRulesengine(IPortletRulesProcessor rulesengine)
    {
        this.rulesengine = rulesengine;
    }

    public IProductDAO getProductDAO()
    {
        return ProductDAO;
    }

    public void setProductDAO(IProductDAO productDAO)
    {
        ProductDAO = productDAO;
    }

    public IDRLFileDAO getDrlFileDAO()
    {
        return drlFileDAO;
    }

    public void setDrlFileDAO(IDRLFileDAO drlFileDAO)
    {
        this.drlFileDAO = drlFileDAO;
    }

    /**
     * desc:根据id删除不匹配的商品记录
     * @param id 匹配记录的id
     */
    public void delMismatchProduct(String id)
    {
        MismatchProductDAO.removeMismatchProductById(id);
    }

    /**
     * desc:删除多个不匹配的商品记录
     * @param idList 匹配记录的id列表
     */
    public void delMultiMismatchProduct(String[] idList)
    {
        MismatchProductDAO.removeMultiMismatchProductById(idList);
    }

    /**
     * desc: 查找不匹配的记录的不匹配配料信息
     * @param id 不匹配的记录id
     * @return 不匹配配料信息
     */
    public String getMismatchContentById(Long id)
    {
        return MismatchProductDAO.getMismatchContentById(id);
    }

    /**
     * desc: 查找与某不匹配商品类似的不匹配商品
     * @param pageIndex 页码值
     * @param pageSize 每页记录数
     * @param id 不匹配商品记录id
     * @return
     */
    public List<Object[]> getSimilarProductPaging(int pageIndex, int pageSize, Long id)
    {
        return MismatchProductDAO.getSimilarProductPaging(pageIndex, pageSize, id);
    }

    /**
     * desc: 查找与某不匹配商品类似的不匹配商品总数
     * @param id 不匹配商品记录id
     * @return 不匹配商品总数
     */
    public int getSimilarProductTotal(Long id)
    {
        return MismatchProductDAO.getSimilarProductTotal(id);
    }


    public class DataFileFilter implements FilenameFilter
    {

        String extension;


        DataFileFilter(String extension)
        {
            this.extension = extension;
        }

        public boolean accept(File directory, String filname)
        {
            // TODO Auto-generated method stub
            return filname.endsWith(extension);
        }

    }


    private String getFileContents(String file)
    {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream is = null;
        InputStreamReader isr = null;
        StringBuffer contents = new StringBuffer();
        try
        {
            is = cl.getResourceAsStream(file);
            isr = new InputStreamReader(is, "UTF-8");
            int ch = -1;
            ch = isr.read();
            while (ch != -1)
            {
                char c = (char) ch;
                contents.append(c);
                ch = isr.read();
            }
        } catch (Exception e)
        {
            e.getMessage();
        }
        finally
        {
            if (isr != null)
            {
                try
                {
                    isr.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return contents.toString();
    }

    // 只为测试用, 司机运行时 ，新增规则后在生成drl文件后， 这个函数读取的还是老的文件。 有待研究
    public void reDeployRules(WebApplicationContext ctx)
    {
        // ruleDeployer.deployRule("*", "products", "standard_val", 1, "products-standard_val-1.drl", "products-standard_val-1.dsl");
        String path = null;
        DataFileFilter filter = null;
        DroolsDeployer droolsDeployer = (DroolsDeployer) ctx.getBean("DroolsDeployer");

        try
        {
            path = WordSimilarity.class.getClassLoader().getResource("").toURI().getPath();
            File folder = new File(path);
            filter = new DataFileFilter("drl");
            String[] filenamesDrl = folder.list(filter);

            filter = new DataFileFilter("dsl");
            String[] filenamesDsl = folder.list(filter);

            for (int i = 0; i < filenamesDrl.length; i++)
            {
                String drl = getFileContents(filenamesDrl[i]);
                String dsl = getFileContents(filenamesDsl[i]);
                droolsDeployer.deployRule("*", "products", "standard_val", i + 1, drl, dsl);
            }
            droolsDeployer.flushRulesCache();

        } catch (Exception e)// URISyntaxException | IOException | RuleDeployFailedException | RulesException e
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void reDeployRules(WebApplicationContext ctx, String drl)
    {
        // ruleDeployer.deployRule("*", "products", "standard_val", 1, "products-standard_val-1.drl", "products-standard_val-1.dsl");
        String path = null;
        DataFileFilter filter = null;
        DroolsDeployer droolsDeployer = (DroolsDeployer) ctx.getBean("DroolsDeployer");

        try
        {
            path = WordSimilarity.class.getClassLoader().getResource("").toURI().getPath();
            File folder = new File(path);
            // filter = new DataFileFilter("drl");
            // String[] filenamesDrl = folder.list(filter);

            filter = new DataFileFilter("dsl");
            String[] filenamesDsl = folder.list(filter);

            for (int i = 0; i < filenamesDsl.length; i++)
            {
                // String drl = getFileContents(filenamesDrl[i]);
                String dsl = getFileContents(filenamesDsl[i]);

                droolsDeployer.deployRule("*", "products", "standard_val", i + 1, drl, dsl);
            }
            droolsDeployer.flushRulesCache();

        } catch (Exception e)// URISyntaxException | IOException | RuleDeployFailedException | RulesException e
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public long buildProductForTest()
    {

        Product product = new Product();
        String line = "";
        InputStream fis = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try
        {
            fis = WordSimilarity.class.getClassLoader().getResourceAsStream("facts.txt");
            inputStreamReader = new InputStreamReader(fis);
            reader = new BufferedReader(inputStreamReader);
            line = reader.readLine();

            product.setLocation("http://www.jd.com/123item.html");
            product.setName("母鸡");
            product.setWebsiteName("京东全球购");
            product.setTid(498L);
            product.setCreateTime(new Date());

            if (line != null)
                product.setRemark(line);

            Product tempProduct = ProductDAO.getProductByLocation(product.getLocation());
            if (tempProduct != null)
            {
                product.setId(tempProduct.getId());

            }
            product.setRemark(product.getRemark().replaceAll("\\s", ""));
            product.setRemark(product.getRemark().replaceAll("\"", "“"));
            product.setRemark(product.getRemark().replaceAll("\"", "”"));

            ProductDAO.saveProduct(product);

            if (product.getRemark() != null)
            {
                ProductDAO.execSyncProductsTaskById(product.getId(), product.getRemark());
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null)
            {
                try
                {
                    inputStreamReader.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return product.getId();

    }

    /**
     * @throws Exception
     * @desc: 将不符合规则的商品记录加载到MISMATCH_PRODUCT表中，演示用例，可能更改
     */
    public void loadingMismatchProducts(WebApplicationContext ctx) throws Exception
    {
        // loading hownet 分词系统
        // WordSimilarity.loadGlossary();
        // ruleDeployer.deployRule("cn", portlet, mode, sequence, drl, dsl);
        Map<String, Object> globals = new HashMap<String, Object>();
        reDeployRules(ctx);
        Long i = buildProductForTest();
        Entity entity = new Entity();
        entity.setProductId(i);
        entity.addType("商品");
        Product product = ProductDAO.getProduct(i);
        if (product != null)
        {
            String productName = product.getRemark();// 获取商品备注
            entity.setLiteral(productName);// 添加商品名称
            List<Object[]> ingredientlist = MismatchProductDAO.loadingMismatchProductMaterial(i);
            if (ingredientlist == null || ingredientlist.size() == 0)
            {
                Entity cmdty = new Entity();
                // cmdty.addType("商品");// 空配方时，唯一的商品属性只需要设置literal
                cmdty.setLiteral(productName);// ***
                entity.getProperties().add(cmdty);

                List<Object> inserts = new ArrayList<Object>();
                inserts.add(entity);

                // 执行业务，改变entity的值
                try
                {
                    this.rulesengine.runRules("cn", "products", "standard_val", inserts, globals,
                            this.getClass().getClassLoader());
                } catch (RulesException e)
                {
                    e.printStackTrace();
                }

                if (entity.isRisky()) // 若为true，代表不匹配，需要保存
                {
                    MismatchProduct mismatchProduct = new MismatchProduct();
                    mismatchProduct.setProductId(entity.getProductId());
                    mismatchProduct.setStandardId(entity.getStandardId().get(0));
                    mismatchProduct.setMismatchContent(entity.getMismatchContent().get(0));
                    mismatchProduct.setCreateTime(new Date());
                    mismatchProduct.setMismatchNum(entity.getMismatchNum().get(0));

                    MismatchProductDAO.delMismatchProductByProductIdAndStandardId(entity.getProductId(),
                            entity.getStandardId().get(0));
                    MismatchProductDAO.saveMismatchProduct(mismatchProduct);
                }

            }
            else
            {
                for (Object[] object : ingredientlist)
                {
                    String literal = object[0].toString();// 配料说明
                    if (literal.indexOf("热量") != -1 || literal.indexOf("熱量") != -1)
                    {// 存在热量时才能与标准进行比对
                        entity.setHeat(Double.parseDouble(object[1].toString()));
                        for (Object[] obj : ingredientlist)
                        {
                            Entity property = new Entity();
                            property.setLiteral(obj[0].toString());// 配料说明
                            double content = Double.parseDouble(obj[1].toString()) / entity.getHeat() * 100;// 将配料含量转化为g/100kJ
                            BigDecimal bigDecimal = new BigDecimal(content);
                            double value = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留4位小数
                            property.setValue(value);// 配料值
                            property.setUnit(obj[2].toString());// 配料单位
                            entity.getProperties().add(property);// 添加配料
                        }

                        List<Object> inserts = new ArrayList<Object>();
                        inserts.add(entity);

                        // 执行业务，改变entity的值
                        try
                        {
                            this.rulesengine.runRules("cn", "products", "standard_val", inserts, globals,
                                    this.getClass().getClassLoader());
                        } catch (RulesException e)
                        {
                            e.printStackTrace();
                        }

                        if (entity.isRisky()) // 若为true，代表不匹配，需要保存
                        {
                            List<Long> standards = entity.getStandardId();
                            for (int j = 0; j < standards.size(); j++)
                            {
                                MismatchProduct mismatchProduct = new MismatchProduct();
                                mismatchProduct.setProductId(entity.getProductId());
                                mismatchProduct.setStandardId(standards.get(j));
                                mismatchProduct.setMismatchContent(entity.getMismatchContent().get(j));
                                mismatchProduct.setCreateTime(new Date());
                                mismatchProduct.setMismatchNum(entity.getMismatchNum().get(0));
                                MismatchProductDAO.delMismatchProductByProductIdAndStandardId(entity.getProductId(),
                                        standards.get(j));
                                MismatchProductDAO.saveMismatchProduct(mismatchProduct);
                            }
                        }
                    }
                }
            }

        }
        // System.out.println("将不符合规则的商品记录加载到MISMATCH_PRODUCT表中，运行完毕");
    }

    /**
     * @throws Exception
     * @desc: 该方法仅用于测试 TODO：
     */
    public void testRuleEngine() throws Exception
    {

        Entity entity = new Entity();
        entity.addType("商品");
        entity.setLiteral("美赞臣婴儿一段奶粉");

        Entity property = new Entity();
        property.setLiteral("能量");// 配料说明
        property.setValue(275);// 配料值
        property.setUnit("kJ/100ml");// 配料单位
        entity.getProperties().add(property);// 添加配料

        Entity property2 = new Entity();
        property2.setLiteral("脂肪");// 配料说明
        property2.setValue(0.24);// 配料值
        property2.setUnit("g/100kJ");// 配料单位
        entity.getProperties().add(property2);// 添加配料

        Entity property3 = new Entity();
        property3.setLiteral("蛋白质");// 配料说明
        property3.setValue(1.47);// 配料值
        property3.setUnit("g/100kJ");// 配料单位
        entity.getProperties().add(property3);// 添加配料

        Entity property4 = new Entity();
        property4.setLiteral("碳水化合物");// 配料说明
        property4.setValue(0.65);// 配料值
        property4.setUnit("g/100kJ");// 配料单位
        entity.getProperties().add(property4);// 添加配料

        Entity property5 = new Entity();
        property5.setLiteral("乳清蛋白比值");// 配料说明
        property5.setValue(-0.1);// 配料值
        // property.setUnit("g/100kJ");// 配料单位
        entity.getProperties().add(property5);// 添加配料

        Map<String, Object> globals = new HashMap<String, Object>();

        List<Object> inserts = new ArrayList<Object>();
        inserts.add(entity);

        // 执行业务，改变entity的值
        try
        {
            this.rulesengine.runRules("cn", "products", "standard_val", inserts, globals,
                    this.getClass().getClassLoader());
        } catch (RulesException e)
        {
            e.printStackTrace();
        }

        System.out.println(entity.isRisky());

        // System.out.println("将不符合规则的商品记录加载到MISMATCH_PRODUCT表中，运行完毕");
    }

    private Integer ruleProducts(List<Product> listProduct) throws Exception
    {
        Integer saveCount = 0;
        List<Entity> inserts = new ArrayList<Entity>();
        for (Product product : listProduct)
        {
            Entity entity = new Entity();
            entity.addType("product");
            entity.addType("商品");
            entity.setProductId(product.getId());

            // Product product = ProductDAO.getProduct(i);
            if (product != null)
            {
                String productName = product.getRemark();// 获取商品备注
                entity.setLiteral(productName);// 添加商品名称
                List<Object[]> ingredientlist = MismatchProductDAO.loadingMismatchProductMaterial(product.getId());

                if (ingredientlist == null || ingredientlist.size() == 0)
                {
                    inserts.add(entity);

                }
                else
                {
                    for (Object[] object : ingredientlist)
                    {
                        String literal = object[0].toString();// 配料说明
                        if (literal.indexOf("热量") != -1 || literal.indexOf("熱量") != -1)
                        {// 存在热量时才能与标准进行比对
                            entity.setHeat(Double.parseDouble(object[1].toString()));
                            for (Object[] obj : ingredientlist)
                            {
                                Entity property = new Entity();
                                property.setLiteral(obj[0].toString());// 配料说明
                                double content = Double.parseDouble(obj[1].toString()) / entity.getHeat() * 100;// 将配料含量转化为g/100kJ
                                BigDecimal bigDecimal = new BigDecimal(content);
                                double value = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留4位小数
                                property.setValue(value);// 配料值
                                property.setUnit(obj[2].toString());// 配料单位
                                entity.getProperties().add(property);// 添加配料
                            }

                            System.out.println("ingredientlist:" + ingredientlist.size());
                            inserts.add(entity);
                        }
                    }
                }
            }
        }

        try
        {
            DroolsProcessor drools = new DroolsProcessor();

            drools.runRule(inserts);
        } catch (RuleException e)
        {
            e.printStackTrace();
        }

        return checkRisk(inserts);

    }

    private Integer checkRisk(List<Entity> entities)
    {
        Integer saveCount = 0;
        for (Entity entity : entities)
        {
            if (entity.isRisky()) // 若为true，代表不匹配，需要保存
            {
                String content = "";
                // 无不匹配内容
                List<Long> standards = entity.getStandardId();
                List<String> contents = entity.getMismatchContent();
                if (contents == null || contents.isEmpty())
                    content = "禁止商品";
                else
                {
                    for (int k = 0; k < contents.size(); k++)
                    {
                        content += (entity.getMismatchContent().get(k) + "||");
                    }
                    content = content.substring(0, content.length() - 2);
                }
                for (int j = 0; j < standards.size(); j++)
                {
                    MismatchProduct mismatchProduct = new MismatchProduct();
                    mismatchProduct.setProductId(entity.getProductId());
                    mismatchProduct.setStandardId(standards.get(j));
                    System.out.println(standards.size() + "@@@@@@@" + j + "@@@@@@@" + entity.getMismatchContent().size());
                    // // 无不匹配内容
                    // if(entity.getMismatchContent() == null || entity.getMismatchContent().isEmpty())
                    // mismatchProduct.setMismatchContent("禁止商品");
                    // else
                    // mismatchProduct.setMismatchContent(entity.getMismatchContent().get(j));
                    mismatchProduct.setMismatchContent(content);
                    mismatchProduct.setCreateTime(new Date());
                    // 无不匹配内容
                    if (entity.getMismatchNum() == null || entity.getMismatchNum().isEmpty())
                        mismatchProduct.setMismatchNum(1);
                    else
                        mismatchProduct.setMismatchNum(entity.getMismatchNum().get(0));
                    // MismatchProductDAO.delMismatchProductByProductIdAndStandardId(entity.getProductId(),
                    // standards.get(j));
                    MismatchProductDAO.saveMismatchProduct(mismatchProduct);
                    saveCount++;
                }
            }
        }

        return saveCount;
    }

    public Date updateMismatchProducts(Date fromDate) throws Exception
    {
        Integer saveCount = 0;
        Date stopDate = null;
        Map<String, Object> globals = new HashMap<String, Object>();
        Date start = new Date();
        List<Product> listProduct = productService.getProductByDate(fromDate);
        saveCount = ruleProducts(listProduct);
        System.out.println("time:" + (new Date().getTime() - start.getTime()) + "ms");

        /*
         * for (Product product : listProduct) { // for(Long i=322171L;i<=322252L;i++){ Entity entity = new Entity(); entity.addType("product");
         * entity.addType("商品"); entity.setProductId(product.getId()); // Product product = ProductDAO.getProduct(i); if (product != null) { String
         * productName = product.getRemark();// 获取商品备注 entity.setLiteral(productName);// 添加商品名称 List<Object[]> ingredientlist =
         * MismatchProductDAO.loadingMismatchProductMaterial(product.getId()); if (ingredientlist == null || ingredientlist.size() == 0) { // Entity
         * cmdty = new Entity(); // //cmdty.addType("商品");// 空配方时，唯一的商品属性只需要设置literal // cmdty.setLiteral(productName);// *** //
         * entity.getProperties().add(cmdty); List<Entity> inserts = new ArrayList<Entity>(); inserts.add(entity); listInserts.add(entity) //
         * 执行业务，改变entity的值 // try // { // DroolsProcessor drools = new DroolsProcessor(); // // drools.runRule(inserts); // } catch (RuleException e)
         * // { // e.printStackTrace(); // } if (entity.isRisky()) // 若为true，代表不匹配，需要保存 { List<Long> standards = entity.getStandardId(); for (int j =
         * 0; j < standards.size(); j++) { System.out.println(standards.size() + "@@@@@@@" + j + "@@@@@@@" + entity.getMismatchContent().size());
         * MismatchProduct mismatchProduct = new MismatchProduct(); mismatchProduct.setProductId(entity.getProductId());
         * mismatchProduct.setStandardId(standards.get(j)); mismatchProduct.setCreateTime(new Date()); // 无不匹配内容 if(entity.getMismatchContent() ==
         * null || entity.getMismatchContent().isEmpty()) mismatchProduct.setMismatchContent("禁止商品"); else
         * mismatchProduct.setMismatchContent(entity.getMismatchContent().get(j)); // 无不匹配内容 if(entity.getMismatchNum() == null ||
         * entity.getMismatchNum().isEmpty()) mismatchProduct.setMismatchNum(1); else mismatchProduct.setMismatchNum(entity.getMismatchNum().get(0));
         * MismatchProductDAO.delMismatchProductByProductIdAndStandardId(entity.getProductId(), standards.get(j));
         * MismatchProductDAO.saveMismatchProduct(mismatchProduct); saveCount++; } } } else { for (Object[] object : ingredientlist) { String literal
         * = object[0].toString();// 配料说明 if (literal.indexOf("热量") != -1 || literal.indexOf("熱量") != -1) {// 存在热量时才能与标准进行比对
         * entity.setHeat(Double.parseDouble(object[1].toString())); for (Object[] obj : ingredientlist) { Entity property = new Entity();
         * property.setLiteral(obj[0].toString());// 配料说明 double content = Double.parseDouble(obj[1].toString()) / entity.getHeat() * 100;//
         * 将配料含量转化为g/100kJ BigDecimal bigDecimal = new BigDecimal(content); double value = bigDecimal.setScale(4,
         * BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留4位小数 property.setValue(value);// 配料值 property.setUnit(obj[2].toString());// 配料单位
         * entity.getProperties().add(property);// 添加配料 } List<Entity> inserts = new ArrayList<Entity>(); inserts.add(entity); // 执行业务，改变entity的值 //
         * try // { // DroolsProcessor drools = new DroolsProcessor(); // // drools.runRule(inserts); // } catch (RuleException e) // { //
         * e.printStackTrace(); // } if (entity.isRisky()) // 若为true，代表不匹配，需要保存 { List<Long> standards = entity.getStandardId(); for (int j = 0; j <
         * standards.size(); j++) { MismatchProduct mismatchProduct = new MismatchProduct(); mismatchProduct.setProductId(entity.getProductId());
         * mismatchProduct.setStandardId(standards.get(j)); System.out.println(standards.size() + "@@@@@@@" + j + "@@@@@@@" +
         * entity.getMismatchContent().size()); // 无不匹配内容 if(entity.getMismatchContent() == null || entity.getMismatchContent().isEmpty())
         * mismatchProduct.setMismatchContent("禁止商品"); else mismatchProduct.setMismatchContent(entity.getMismatchContent().get(j));
         * mismatchProduct.setCreateTime(new Date()); // 无不匹配内容 if(entity.getMismatchNum() == null || entity.getMismatchNum().isEmpty())
         * mismatchProduct.setMismatchNum(1); else mismatchProduct.setMismatchNum(entity.getMismatchNum().get(0));
         * MismatchProductDAO.delMismatchProductByProductIdAndStandardId(entity.getProductId(), standards.get(j));
         * MismatchProductDAO.saveMismatchProduct(mismatchProduct); saveCount++; } } } } } } }
         */

        System.out.println("本次规则应用于商品数：" + listProduct.size());
        System.out.println("本次规则风险商品数：" + saveCount);
        if (listProduct != null && listProduct.size() > 0)
            stopDate = listProduct.get(listProduct.size() - 1).getCreateTime();
        else
            stopDate = new Date();

        return stopDate;
        // System.out.println("将不符合规则的商品记录加载到MISMATCH_PRODUCT表中，运行完毕");
    }

    /**
     * @desc:获得对应的网站总数
     * @return 网站数
     */
    public int getWebsiteTotal()
    {
        return MismatchProductDAO.getWebsiteTotal();
    }

    /**
     * @desc: 获得对应的规则总数
     * @return 规则数
     */
    public int getStandardTotal()
    {
        return MismatchProductDAO.getStandardTotal();
    }

    /**
     * @desc: 获得不匹配的商品数
     * @return 商品数
     */
    public int getMismatchProductTotal()
    {
        return MismatchProductDAO.getMismatchProductTotal();
    }

    /**
     * @desc 获取存在风险商品的国家以及对应商品数
     * @return 存在风险商品的国家以及对应商品数
     */
    public List<Object[]> getMismatchMap()
    {
        return MismatchProductDAO.getMismatchMap();
    }

    public List<Object[]> getMismatchProductPaging(int pageIndex, int pageSize, Long[] ptl, Long[] standardIdList,
            String orderBy, String orderType)
    {
        return MismatchProductDAO.getMismatchProductPaging(pageIndex, pageSize, ptl, standardIdList, orderBy,
                orderType);
    }

    public int getMismatchProductTotal(Long[] ptl, Long[] standardIdList)
    {
        return MismatchProductDAO.getMismatchProductTotal(ptl, standardIdList);
    }

    public void getDRLFile(String filePath, String fileName, Long id)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            // 从数据库读drl
            DRLFile drl = (DRLFile) drlFileDAO.getObject(DRLFile.class, id);
            // DRLFile drl = new DRLFile();
            File dir = new File(filePath);
            if (!dir.exists() && !dir.isDirectory())
            {
                System.out.println("文件缓存目录不存在");
                dir.mkdir();
            }
            file = new File(filePath, fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte[] buffer = drl.getContent();
            bos.write(buffer);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文件内容,并返回增加规则后的字符串
     * @param num22
     */
    public String addRule(String filePath, String ruleName, String operation, String ruleType, String entity,
            String composition, String unit, String standard, String condition, String num1, String num2)
    {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        String line = null;
        StringBuffer buf = new StringBuffer();
        try
        {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            br = new BufferedReader(inputStreamReader);
            while ((line = br.readLine()) != null)
            {
                buf.append(line);
                buf.append(System.getProperty("line.separator"));
            }
            buf.append(System.getProperty("line.separator"));
            buf.append("rule " + ruleName);
            buf.append(System.getProperty("line.separator"));
            buf.append("        when");
            buf.append(System.getProperty("line.separator"));
            buf.append("                Entity present");
            buf.append(System.getProperty("line.separator"));
            buf.append("                is " + entity);
            // buf.append(System.getProperty("line.separator"));
            // buf.append(" \"维生素C\" unit \"毫克\" standard 1l is less than 1.8");
            buf.append(System.getProperty("line.separator"));
            if ("-1".equals(condition))
            {
                condition = "are not in range";
                buf.append("                \"" + composition + "\" unit \"" + unit + "\" standard " + standard + "l "
                        + condition + " " + num1 + "," + num2);
            }
            else if ("0".equals(condition))
            {
                condition = "are in range";
                buf.append("                \"" + composition + "\" unit \"" + unit + "\" standard " + standard + "l "
                        + condition + " " + num1 + "," + num2);
            }
            else if ("1".equals(condition))
            {
                condition = "is greater than";
                buf.append("                \"" + composition + "\" unit \"" + unit + "\" standard " + standard + "l "
                        + condition + " " + num1);
            }

            else if ("2".equals(condition))
            {
                condition = "is less than";
                buf.append("                \"" + composition + " unit \"" + unit + "\" standard " + standard + "l "
                        + condition + " " + num1);
            }
            else if ("3".equals(condition))
            {
                condition = "is contains";
                buf.append("                \"" + ruleName + "\" standard " + standard + "l " + condition + " \""
                        + composition + "\"");
            }
            buf.append(System.getProperty("line.separator"));
            buf.append("        then");
            buf.append(System.getProperty("line.separator"));
            buf.append("                Update risky info");
            buf.append(System.getProperty("line.separator"));
            buf.append("end");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                } catch (IOException e)
                {
                    fileInputStream = null;
                }
            }
            if (inputStreamReader != null)
            {
                try
                {
                    inputStreamReader.close();
                } catch (IOException e)
                {
                    inputStreamReader = null;
                }
            }
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    br = null;
                }
            }
        }
        return buf.toString();
    }

    /**
     * 读取文件内容,并返回修改后的字符串
     */
    public String read(String filePath, String ruleName, String operation, String ruleType, String entity,
            String composition, String unit, String standard, String condition, String num1, String num2)
    {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        String line = null;
        StringBuffer buf = new StringBuffer();
        try
        {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            br = new BufferedReader(inputStreamReader);
            // BufferedReader br=new BufferedReader(new InputStreamReader(newFileInputStream(fileName),"UTF-8"));
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null)
            {
                // 此处根据实际需要修改某些行的内容
                if (line.contains(ruleName))
                {
                    if ("edit".equals(operation))
                    {
                        // 规则名
                        buf.append(line);
                        buf.append(System.getProperty("line.separator"));
                        // when
                        buf.append(br.readLine());
                        buf.append(System.getProperty("line.separator"));
                        // Entity present
                        buf.append(br.readLine());
                        buf.append(System.getProperty("line.separator"));
                        // 修改内容
                        br.readLine();
                        String str = "        is " + entity;
                        buf.append(str);
                        // buf.append(br.readLine());
                        // String str = "";
                        buf.append(System.getProperty("line.separator"));

                        br.readLine();
                        if ("-1".equals(condition))
                        {
                            condition = "are not in range";
                            str = "                \"" + composition + "\" unit \"" + unit + "\" standard " + standard
                                    + "l " + condition + " " + num1 + "," + num2;
                        }
                        else if ("0".equals(condition))
                        {
                            condition = "are in range";
                            str = "                \"" + composition + "\" unit \"" + unit + "\" standard " + standard
                                    + "l " + condition + " " + num1 + "," + num2;
                        }
                        else if ("1".equals(condition))
                        {
                            condition = "is greater than";
                            str = "                \"" + composition + "\" unit \"" + unit + "\" standard " + standard
                                    + "l " + condition + " " + num1;
                        }
                        else if ("2".equals(condition))
                        {
                            condition = "is less than";
                            str = "                \"" + composition + "\" unit \"" + unit + "\" standard " + standard
                                    + "l " + condition + " " + num1;
                        }
                        else if ("3".equals(condition))
                        {
                            condition = "is contains";
                            str = "                \"" + composition + "\" standard " + standard + "l " + condition
                                    + " \"" + num1 + "\"";
                        }
                        buf.append(str);
                    }
                    else if ("del".equals(operation))
                    {
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        br.readLine();
                    }
                }
                // 如果不用修改, 则按原来的内容回写
                else
                {
                    buf.append(line);
                }
                buf.append(System.getProperty("line.separator"));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                } catch (IOException e)
                {
                    fileInputStream = null;
                }
            }
            if (inputStreamReader != null)
            {
                try
                {
                    inputStreamReader.close();
                } catch (IOException e)
                {
                    inputStreamReader = null;
                }
            }
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    br = null;
                }
            }
        }
        return buf.toString();
    }

    /**
     * 将内容回写到文件中，并存入数据库
     */
    public void write(String filePath, String content)
    {
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bw = null;
        try
        {
            fileOutputStream = new FileOutputStream(filePath);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            // 根据文件路径创建缓冲输出流
            bw = new BufferedWriter(outputStreamWriter);
            // bw = new BufferedWriter(new FileWriter(filePath,"UTF-8"));
            // 将内容写入文件中
            bw.write(content);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            if (bw != null)
            {
                try
                {
                    bw.close();
                } catch (IOException e)
                {
                    bw = null;
                }
            }
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (outputStreamWriter != null)
            {
                try
                {
                    outputStreamWriter.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveDRLFile(String filePath, Long id)
    {
        File file = new File(filePath);
        FileInputStream fis = null;
        try
        {
            // 从数据库读drl
            fis = new FileInputStream(file);
            DRLFile drl = (DRLFile) drlFileDAO.getObject(DRLFile.class, id);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int n;
            byte[] buffer = null;
            byte[] b = new byte[1024];
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            drl.setFilename("products-standard_val-1.drl");
            drl.setContent(buffer);
            drl.setUpdateTime(new Date());
            drlFileDAO.saveObject(drl);
            // saveDRLFile();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public DRLFile getDRLFile(Long id)
    {
        DRLFile drl = (DRLFile) drlFileDAO.getObject(DRLFile.class, id);
        return drl;
    }

    public List<String> getRule(String filePath, String ruleName)
    {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        String line = null;
        List<String> rule = new ArrayList<String>();
        String str = null;
        try
        {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            br = new BufferedReader(inputStreamReader);
            while ((line = br.readLine()) != null)
            {
                // 此处根据实际需要修改某些行的内容
                if (line.contains(ruleName))
                {
                    rule.add(ruleName);// 规则名
                    br.readLine();
                    br.readLine();
                    line = br.readLine();
                    str = line.substring(line.indexOf("is ") + 3);// Entity present
                    rule.add(str);
                    line = br.readLine();
                    // line = br.readLine();
                    // str = line.substring(line.indexOf("\"")+1,line.indexOf("\" unit"));//成分
                    // rule.add(str);
                    // str = line.substring(line.indexOf("unit \"")+6,line.indexOf("\" standard"));//单位
                    // rule.add(str);
                    if (line.contains("are in range"))
                    {
                        str = line.substring(line.indexOf("\"") + 1, line.indexOf("\" unit"));// 成分
                        rule.add(str);
                        str = line.substring(line.indexOf("unit \"") + 6, line.indexOf("\" standard"));// 单位
                        rule.add(str);
                        str = line.substring(line.indexOf("standard ") + 9, line.indexOf(" are"));
                        rule.add(str);
                        rule.add("0");// standard
                        str = line.substring(line.indexOf("range ") + 6, line.indexOf(","));// 范围
                        rule.add(str);
                        str = line.substring(line.indexOf(",") + 1);// 范围
                        rule.add(str);
                    }
                    else if (line.contains("are not in range"))
                    {
                        str = line.substring(line.indexOf("\"") + 1, line.indexOf("\" unit"));// 成分
                        rule.add(str);
                        str = line.substring(line.indexOf("unit \"") + 6, line.indexOf("\" standard"));// 单位
                        rule.add(str);
                        str = line.substring(line.indexOf("standard ") + 9, line.indexOf(" are"));
                        rule.add(str);
                        rule.add("-1");// standard
                        str = line.substring(line.indexOf("range ") + 6, line.indexOf(","));// 范围
                        rule.add(str);
                        str = line.substring(line.indexOf(",") + 1);// 范围
                        rule.add(str);
                    }
                    else if (line.contains("greater"))
                    {
                        str = line.substring(line.indexOf("\"") + 1, line.indexOf("\" unit"));// 成分
                        rule.add(str);
                        str = line.substring(line.indexOf("unit \"") + 6, line.indexOf("\" standard"));// 单位
                        rule.add(str);
                        str = line.substring(line.indexOf("standard ") + 9, line.indexOf(" is"));
                        rule.add(str);
                        rule.add("1");// standard
                        str = line.substring(line.indexOf("than ") + 5);// 范围
                        rule.add(str);
                        rule.add("");
                    }
                    else if (line.contains("less"))
                    {
                        str = line.substring(line.indexOf("\"") + 1, line.indexOf("\" unit"));// 成分
                        rule.add(str);
                        str = line.substring(line.indexOf("unit \"") + 6, line.indexOf("\" standard"));// 单位
                        rule.add(str);
                        str = line.substring(line.indexOf("standard ") + 9, line.indexOf(" is"));
                        rule.add(str);
                        rule.add("2");// standard
                        str = line.substring(line.indexOf("than ") + 5);// 范围
                        rule.add(str);
                        rule.add("");
                    }
                    else if (line.contains("contains"))
                    {
                        str = line.substring(line.indexOf("contains \"") + 10, line.length() - 1);
                        rule.add(str);
                        rule.add("");
                        str = line.substring(line.indexOf("standard ") + 9, line.indexOf(" is"));
                        rule.add(str);
                        rule.add("3");// standard
                        rule.add("");
                        rule.add("");
                    }
                    break;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                } catch (IOException e)
                {
                    fileInputStream = null;
                }
            }
            if (inputStreamReader != null)
            {
                try
                {
                    inputStreamReader.close();
                } catch (IOException e)
                {
                    inputStreamReader = null;
                }
            }
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    br = null;
                }
            }
        }
        return rule;
    }

    public List<String> getRuleList(String filePath, Long standard)
    {
        BufferedReader br = null;
        String line = null;
        List<String> ruleList = new ArrayList<String>();
        String str = null;
        try
        {
            // 根据文件路径创建缓冲输入流
            // br = new BufferedReader(new FileReader(filePath));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            // BufferedReader br=new BufferedReader(new InputStreamReader(newFileInputStream(fileName),"UTF-8"));
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null)
            {
                // 此处根据实际需要修改某些行的内容
                if (line.contains("rule "))
                {
                    br.readLine();
                    br.readLine();
                    br.readLine();
                    if (br.readLine().contains(String.valueOf(standard) + "l"))
                    {
                        str = line.substring(line.indexOf("rule ") + 5);// ruleName
                        ruleList.add(str);
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    br = null;
                }
            }
        }
        return ruleList;
    }

    public List<String> getEntityList(String filePath)
    {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        String line = null;
        List<String> entityList = new ArrayList<String>();
        String str = null;
        try
        {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            br = new BufferedReader(inputStreamReader);
            while ((line = br.readLine()) != null)
            {
                // 此处根据实际需要修改某些行的内容
                if (line.contains("entity.is"))
                {
                    str = line.substring(9, line.indexOf("=eval($entity.is"));// entity
                    entityList.add(str);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                } catch (IOException e)
                {
                    fileInputStream = null;
                }
            }
            if (inputStreamReader != null)
            {
                try
                {
                    inputStreamReader.close();
                } catch (IOException e)
                {
                    inputStreamReader = null;
                }
            }
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    br = null;
                }
            }
        }
        return entityList;
    }

    public String getTestResult(WebApplicationContext ctx, String productName, List<Object[]> objs) throws Exception
    {
        String testResult = "";
        Map<String, Object> globals = new HashMap<String, Object>();
        reDeployRules(ctx);
        Entity entity = new Entity();
        entity.addType("商品");
        Product product = new Product();
        product.setName(productName);
        if (product != null)
        {
            entity.setLiteral(productName);// 添加商品名称
            // 商品原料信息
            List<Object[]> ingredientlist = objs;
            // TODO 无配料信息情况（未修改）
            if (ingredientlist == null || ingredientlist.size() == 0)
            {
                Entity cmdty = new Entity();
                // cmdty.addType("商品");// 空配方时，唯一的商品属性只需要设置literal
                cmdty.setLiteral(productName);// ***
                entity.getProperties().add(cmdty);

                List<Object> inserts = new ArrayList<Object>();
                inserts.add(entity);

                // 执行业务，改变entity的值
                try
                {
                    this.rulesengine.runRules("cn", "products", "standard_val", inserts, globals,
                            this.getClass().getClassLoader());
                } catch (RulesException e)
                {
                    e.printStackTrace();
                }

                if (entity.isRisky()) // 若为true，代表不匹配，需要保存
                {
                    // MismatchProduct mismatchProduct = new MismatchProduct();
                    // mismatchProduct.setStandardId(entity.getStandardId().get(0));
                    // mismatchProduct.setMismatchContent(entity.getMismatchContent().get(0));
                    // mismatchProduct.setCreateTime(new Date());
                    testResult = testResult + entity.getMismatchContent().get(0);
                }
            }
            else
            {// 有配料信息
                for (Object[] object : ingredientlist)
                {
                    String literal = object[0].toString();// 配料说明
                    if (literal.indexOf("热量") != -1 || literal.indexOf("熱量") != -1)
                    {// 存在热量时才能与标准进行比对
                        entity.setHeat(Double.parseDouble(object[1].toString()));
                        for (Object[] obj : ingredientlist)
                        {
                            Entity property = new Entity();
                            property.setLiteral(obj[0].toString());// 配料说明
                            double content = Double.parseDouble(obj[1].toString()) / entity.getHeat() * 100;// 将配料含量转化为g/100kJ
                            BigDecimal bigDecimal = new BigDecimal(content);
                            double value = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留4位小数
                            property.setValue(value);// 配料值
                            property.setUnit(obj[2].toString());// 配料单位
                            entity.getProperties().add(property);// 添加配料
                        }

                        List<Object> inserts = new ArrayList<Object>();
                        inserts.add(entity);

                        // 执行业务，改变entity的值
                        try
                        {
                            this.rulesengine.runRules("cn", "products", "standard_val", inserts, globals,
                                    this.getClass().getClassLoader());
                        } catch (RulesException e)
                        {
                            e.printStackTrace();
                        }

                        if (entity.isRisky()) // 若为true，代表不匹配，需要保存
                        {
                            List<Long> standards = entity.getStandardId();
                            for (int j = 0; j < standards.size(); j++)
                            {
                                // MismatchProduct mismatchProduct = new MismatchProduct();
                                // mismatchProduct.setProductId(entity.getProductId());
                                // mismatchProduct.setStandardId(standards.get(j));
                                // mismatchProduct.setMismatchContent(entity.getMismatchContent().get(j));
                                // mismatchProduct.setCreateTime(new Date());
                                testResult = testResult + entity.getMismatchContent().get(j);
                            }
                        }
                    }
                }
            }

        }
        return testResult;
    }

    public String getMismatchForm(Integer dataSource, Integer chartType, Integer number, Date startTime, Date endTime)
            throws ParseException
    {
        if (chartType == 2)
        {
            List<Object[]> ls = Lists.newArrayList();
            if (dataSource == 1)
            {
                ls = MismatchProductDAO.getPieChartByCountry(number, startTime, endTime);
            }
            else if (dataSource == 2)
            {
                ls = MismatchProductDAO.getPieChartByWebsite(number, startTime, endTime);
            }
            else if (dataSource == 3)
            {
                ls = MismatchProductDAO.getPieChartByProductType(number, startTime, endTime);
            }
            else if (dataSource == 4)
            {
                ls = MismatchProductDAO.getPieChartByBrand(number, startTime, endTime);
            }
            return getPieChart(ls, number, startTime, endTime);
        }
        else if (chartType == 1)
        {
            if (dataSource == 1)
            {
                return getMismatchFormByCountry(number, startTime, endTime);
            }
            else if (dataSource == 2)
            {
                return getMismatchFormByWebsite(number, startTime, endTime);
            }
            else if (dataSource == 3)
            {
                return getMismatchFormByType(number, startTime, endTime);
            }
            else if (dataSource == 4)
            {
                return getMismatchFormByBrand(number, startTime, endTime);
            }
        }

        return null;
    }

    private String getPieChart(List<Object[]> ls, Integer number, Date startTime, Date endTime)
    {
        List<Map<String, Object>> lMaps = Lists.newArrayList();
        for (Object[] item : ls)
        {
            Map<String, Object> map = Maps.newHashMap();
            map.put("name", item[0].toString());
            map.put("value", (int) item[1]);
            lMaps.add(map);
        }
        return GsonUtil.bean2Json(lMaps);
    }

    public String getMismatchFormByCountry(Integer number, Date startTime, Date endTime) throws ParseException
    {
        List<Date> lDate = DateUtil.findDates(startTime, endTime);

        List<Object[]> topSize = MismatchProductDAO.getTopCountry(number, startTime, endTime);

        List<MismatchProductForm> lMPF = Lists.newArrayList();
        List<Object[]> lMF = MismatchProductDAO.getMismatchFormByCountry(number, startTime, endTime);
        for (Date item : lDate)
        {
            List<Map<String, Object>> lMap = Lists.newArrayList();
            for (Object[] item3 : topSize)
            {
                Map<String, Object> map = Maps.newHashMap();
                map.put("item", item3[1].toString());
                map.put("result", 0);
                lMap.add(map);
            }

            for (Object[] item2 : lMF)
            {
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(item);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(DateUtil.parseDate(DateUtil.YYMMDD_FORMAT, item2[0].toString()));
                if (!DateUtils.isSameDay(cal1, cal2) && cal1.before(cal2))
                    break;
                else if (DateUtils.isSameDay(item, DateUtil.parseDate(DateUtil.YYMMDD_FORMAT, item2[0].toString())))
                {
                    for (Map<String, Object> item4 : lMap)
                    {
                        if (item4.containsValue(item2[1]))
                        {
                            item4.replace("result", (Integer) item2[2]);
                        }
                    }
                }
            }
            MismatchProductForm result = new MismatchProductForm(item, lMap);
            lMPF.add(result);

        }
        return GsonUtil.bean2Json(lMPF);
    }

    public String getMismatchFormByWebsite(Integer number, Date startTime, Date endTime) throws ParseException
    {
        List<Date> lDate = DateUtil.findDates(startTime, endTime);

        List<Object[]> topSize = MismatchProductDAO.getTopWebsite(number, startTime, endTime);

        List<MismatchProductForm> lMPF = Lists.newArrayList();
        List<Object[]> lMF = MismatchProductDAO.getMismatchFormByWebsite(number, startTime, endTime);
        for (Date item : lDate)
        {
            List<Map<String, Object>> lMap = Lists.newArrayList();
            for (Object[] item3 : topSize)
            {
                Map<String, Object> map = Maps.newHashMap();
                map.put("item", item3[1].toString());
                map.put("result", 0);
                lMap.add(map);
            }

            for (Object[] item2 : lMF)
            {
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(item);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(DateUtil.parseDate(DateUtil.YYMMDD_FORMAT, item2[0].toString()));
                if (!DateUtils.isSameDay(cal1, cal2) && cal1.before(cal2))
                    break;
                else if (DateUtils.isSameDay(item, DateUtil.parseDate(DateUtil.YYMMDD_FORMAT, item2[0].toString())))
                {
                    for (Map<String, Object> item4 : lMap)
                    {
                        if (item4.containsValue(item2[1]))
                        {
                            item4.replace("result", (Integer) item2[2]);
                        }
                    }
                }
            }
            MismatchProductForm result = new MismatchProductForm(item, lMap);
            lMPF.add(result);

        }
        return GsonUtil.bean2Json(lMPF);
    }

    public String getMismatchFormByType(Integer number, Date startTime, Date endTime) throws ParseException
    {
        List<Date> lDate = DateUtil.findDates(startTime, endTime);

        List<Object[]> topSize = MismatchProductDAO.getTopType(number, startTime, endTime);

        List<MismatchProductForm> lMPF = Lists.newArrayList();
        List<Object[]> lMF = MismatchProductDAO.getMismatchFormByType(number, startTime, endTime);
        for (Date item : lDate)
        {
            List<Map<String, Object>> lMap = Lists.newArrayList();
            for (Object[] item3 : topSize)
            {
                Map<String, Object> map = Maps.newHashMap();
                map.put("item", item3[1].toString());
                map.put("result", 0);
                lMap.add(map);
            }

            for (Object[] item2 : lMF)
            {
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(item);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(DateUtil.parseDate(DateUtil.YYMMDD_FORMAT, item2[0].toString()));
                if (!DateUtils.isSameDay(cal1, cal2) && cal1.before(cal2))
                    break;
                else if (DateUtils.isSameDay(item, DateUtil.parseDate(DateUtil.YYMMDD_FORMAT, item2[0].toString())))
                {
                    for (Map<String, Object> item4 : lMap)
                    {
                        if (item4.containsValue(item2[1]))
                        {
                            item4.replace("result", (Integer) item2[2]);
                        }
                    }
                }
            }
            MismatchProductForm result = new MismatchProductForm(item, lMap);
            lMPF.add(result);

        }
        return GsonUtil.bean2Json(lMPF);
    }

    public String getMismatchFormByBrand(Integer number, Date startTime, Date endTime) throws ParseException
    {
        List<Date> lDate = DateUtil.findDates(startTime, endTime);

        List<Object[]> topSize = MismatchProductDAO.getTopBrand(number, startTime, endTime);

        List<MismatchProductForm> lMPF = Lists.newArrayList();
        List<Object[]> lMF = MismatchProductDAO.getMismatchFormByType(number, startTime, endTime);
        for (Date item : lDate)
        {
            List<Map<String, Object>> lMap = Lists.newArrayList();
            for (Object[] item3 : topSize)
            {
                Map<String, Object> map = Maps.newHashMap();
                map.put("item", item3[1].toString());
                map.put("result", 0);
                lMap.add(map);
            }

            for (Object[] item2 : lMF)
            {
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(item);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(DateUtil.parseDate(DateUtil.YYMMDD_FORMAT, item2[0].toString()));
                if (!DateUtils.isSameDay(cal1, cal2) && cal1.before(cal2))
                    break;
                else if (DateUtils.isSameDay(item, DateUtil.parseDate(DateUtil.YYMMDD_FORMAT, item2[0].toString())))
                {
                    for (Map<String, Object> item4 : lMap)
                    {
                        if (item4.containsValue(item2[1]))
                        {
                            item4.replace("result", (Integer) item2[2]);
                        }
                    }
                }
            }
            MismatchProductForm result = new MismatchProductForm(item, lMap);
            lMPF.add(result);

        }
        return GsonUtil.bean2Json(lMPF);
    }

    public String listWebsiteEvaluation(int pageIndex, int pageSize)
    {
        List<Object[]> ls = MismatchProductDAO.getEvaluationByWebsite(pageIndex, pageSize);
        int total = MismatchProductDAO.getEvaluationTotalByWebsite();
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", total);
        List<Map<String, String>> list = Lists.newArrayList();
        for (Object[] item : ls)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("websiteId", item[0].toString());
            map.put("websiteName", item[1].toString());
            map.put("result", new DecimalFormat("##0.#").format(5 - ((Double) item[2] * 5)));
            map.put("suggestion", com.tonik.Constant.EvaluationSuggestion.getSuggestion((Double) item[2]));
            list.add(map);
        }
        result.put("list", list);
        return GsonUtil.bean2Json(result);

    }
}
