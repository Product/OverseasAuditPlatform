package com.tonik.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tonik.dao.ICollectorCfgCommodityDAO;
import com.tonik.dao.IProductStyleDAO;
import com.tonik.dao.IProductTypeDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.CollectorCfgCommodity;
import com.tonik.util.CollectionCommodity;

/**
 * @spring.bean id="CollectorCfgCommodityService"
 * @author fuzhi
 * @spring.property name="collectorCfgCommodityDAO" ref="CollectorCfgCommodityDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="productStyleDAO" ref="ProductStyleDAO"
 * @spring.property name="collectionCommodity" ref="CollectionCommodity"
 * @spring.property name="productTypeDAO" ref="ProductTypeDAO"
 */
public class CollectorCfgCommodityService
{
    private ICollectorCfgCommodityDAO collectorCfgCommodityDAO;
    private IWebsiteDAO websiteDAO;
    private IProductStyleDAO productStyleDAO;
    private CollectionCommodity collectionCommodity;
    private IProductTypeDAO productTypeDAO;


    public ICollectorCfgCommodityDAO getCollectorCfgCommodityDAO()
    {
        return collectorCfgCommodityDAO;
    }

    public void setCollectorCfgCommodityDAO(ICollectorCfgCommodityDAO collectorCfgCommodityDAO)
    {
        this.collectorCfgCommodityDAO = collectorCfgCommodityDAO;
    }

    public IWebsiteDAO getWebsiteDAO()
    {
        return websiteDAO;
    }

    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        this.websiteDAO = websiteDAO;
    }

    public IProductStyleDAO getProductStyleDAO()
    {
        return productStyleDAO;
    }

    public void setProductStyleDAO(IProductStyleDAO productStyleDAO)
    {
        this.productStyleDAO = productStyleDAO;
    }

    public CollectionCommodity getCollectionCommodity()
    {
        return collectionCommodity;
    }

    public void setCollectionCommodity(CollectionCommodity collectionCommodity)
    {
        this.collectionCommodity = collectionCommodity;
    }

    public IProductTypeDAO getProductTypeDAO()
    {
        return productTypeDAO;
    }

    public void setProductTypeDAO(IProductTypeDAO productTypeDAO)
    {
        this.productTypeDAO = productTypeDAO;
    }

    public String findById(String id) throws JSONException
    {
        Long longid = Long.valueOf(id);
        CollectorCfgCommodity CollectorCfgCommodity = collectorCfgCommodityDAO.findById(longid);
        JSONObject jsonObject = new JSONObject();
        
        //基本属性
        jsonObject.put("id", CollectorCfgCommodity.getId());
        if (null != CollectorCfgCommodity.getName() && !CollectorCfgCommodity.getName().equals("")) {
            jsonObject.put("name", CollectorCfgCommodity.getName());
        } else {
            jsonObject.put("name", "");
        }
        if (null != CollectorCfgCommodity.getCreateTime() && !CollectorCfgCommodity.getCreateTime().equals("")) {
            jsonObject.put("createTime", CollectorCfgCommodity.getCreateTime());
        } else {
            jsonObject.put("createTime", "");
        }
        jsonObject.put("rate", CollectorCfgCommodity.getRate());
        
        //主采集页面
        if (null != CollectorCfgCommodity.getUrl() && !CollectorCfgCommodity.getUrl().equals("")) {
            jsonObject.put("url", CollectorCfgCommodity.getUrl());
        } else {
            jsonObject.put("url", "");
        }
        if (null != CollectorCfgCommodity.getContentSign() && !CollectorCfgCommodity.getContentSign().equals("")) {
            jsonObject.put("contentSign", CollectorCfgCommodity.getContentSign());
        } else {
            jsonObject.put("contentSign", "");
        }
        if (null != CollectorCfgCommodity.getTitleSign() && !CollectorCfgCommodity.getTitleSign().equals("")) {
            jsonObject.put("titleSign", CollectorCfgCommodity.getTitleSign());
        } else {
            jsonObject.put("titleSign", "");
        }
        if (null != CollectorCfgCommodity.getPriceSign() && !CollectorCfgCommodity.getPriceSign().equals("")) {
            jsonObject.put("getPriceSign", CollectorCfgCommodity.getPriceSign());
        } else {
            jsonObject.put("getPriceSign", "");
        }
        if (null != CollectorCfgCommodity.getNextSign() && !CollectorCfgCommodity.getNextSign().equals("")) {
            jsonObject.put("nextSign", CollectorCfgCommodity.getNextSign());
        } else {
            jsonObject.put("nextSign", "");
        }
        if (null != CollectorCfgCommodity.getSaleMountSign() && !CollectorCfgCommodity.getSaleMountSign().equals("")) {
            jsonObject.put("saleMountSign", CollectorCfgCommodity.getSaleMountSign());
        } else {
            jsonObject.put("saleMountSign", "");
        }
        jsonObject.put("pageTotal", CollectorCfgCommodity.getPageTotal());
        
        //副采集页面
        if (null != CollectorCfgCommodity.getBrandSign()) {
            jsonObject.put("brandSign", CollectorCfgCommodity.getBrandSign());
        } else {
            jsonObject.put("brandSign", "");
        }
        if (null != CollectorCfgCommodity.getProducingAreaSign()) {
            jsonObject.put("producingAreaSign", CollectorCfgCommodity.getProducingAreaSign());
        } else {
            jsonObject.put("producingAreaSign", "");
        }
        if (null != CollectorCfgCommodity.getSuitableAgeSign()) {
            jsonObject.put("suitableAgeSign", CollectorCfgCommodity.getSuitableAgeSign());
        } else {
            jsonObject.put("suitableAgeSign", "");
        }
        if (null != CollectorCfgCommodity.getStandardSign()) {
            jsonObject.put("standardSign", CollectorCfgCommodity.getStandardSign());
        } else {
            jsonObject.put("standardSign", "");
        }
        if (null != CollectorCfgCommodity.getExpirationDateSign()) {
            jsonObject.put("expirationDateSign", CollectorCfgCommodity.getExpirationDateSign());
        } else {
            jsonObject.put("expirationDateSign", "");
        }
        if (null != CollectorCfgCommodity.getCountrySign()) {
            jsonObject.put("countrySign", CollectorCfgCommodity.getCountrySign());
        } else {
            jsonObject.put("countrySign", "");
        }
        if (null != CollectorCfgCommodity.getProductTypeSign()) {
            jsonObject.put("productTypeSign", CollectorCfgCommodity.getProductTypeSign());
        } else {
            jsonObject.put("productTypeSign", "");
        }

        //外键属性
        if (null != CollectorCfgCommodity.getWebsite()) {
            jsonObject.put("websiteId", CollectorCfgCommodity.getWebsite().getId());
        } else {
            jsonObject.put("websiteId", "");
        }
        if (null != CollectorCfgCommodity.getProductStyle()) {
            jsonObject.put("productStyleId", CollectorCfgCommodity.getProductStyle().getId());
        } else {
            jsonObject.put("productStyleId", "");
        }
        if (null != CollectorCfgCommodity.getFirstlevelType()) {
            jsonObject.put("firstlevelType", CollectorCfgCommodity.getFirstlevelType().getId());
        } else {
            jsonObject.put("firstlevelType", "");
        }
        if (null != CollectorCfgCommodity.getSecondlevelType()) {
            jsonObject.put("secondlevelType", CollectorCfgCommodity.getSecondlevelType().getId());
        } else {
            jsonObject.put("secondlevelType", "");
        }
        if (null != CollectorCfgCommodity.getThirdlevelType()) {
            jsonObject.put("thirdlevelType", CollectorCfgCommodity.getThirdlevelType().getId());
        } else {
            jsonObject.put("thirdlevelType", "");
        }
        return jsonObject.toString();
    }

    public String findList(String index, String size) throws JSONException
    {
        int pageIndex = 1;
        int pageSize = 10;
        if (null != index && !"".equals(index) && null != size && !"".equals(size))
        {
            pageIndex = Integer.valueOf(index).intValue();
            pageSize = Integer.valueOf(size).intValue();
        }
        List<Object[]> list = collectorCfgCommodityDAO.findList(pageIndex, pageSize);
        int count = collectorCfgCommodityDAO.findCount();
        JSONObject jsonTotal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Object[] object : list)
        {
            CollectorCfgCommodity CollectorCfgCommodity = (CollectorCfgCommodity) object[0];
            JSONObject jsonObject = new JSONObject();
            //基本属性
            jsonObject.put("id", CollectorCfgCommodity.getId());
            if (null != CollectorCfgCommodity.getName() && !CollectorCfgCommodity.getName().equals("")) {
                jsonObject.put("name", CollectorCfgCommodity.getName());
            } else {
                jsonObject.put("name", "");
            }
            if (null != CollectorCfgCommodity.getCreateTime() && !CollectorCfgCommodity.getCreateTime().equals("")) {
                jsonObject.put("createTime", CollectorCfgCommodity.getCreateTime());
            } else {
                jsonObject.put("createTime", "");
            }
            jsonObject.put("rate", CollectorCfgCommodity.getRate());
            
            //主采集页面
            if (null != CollectorCfgCommodity.getUrl() && !CollectorCfgCommodity.getUrl().equals("")) {
                jsonObject.put("url", CollectorCfgCommodity.getUrl());
            } else {
                jsonObject.put("url", "");
            }
            if (null != CollectorCfgCommodity.getContentSign() && !CollectorCfgCommodity.getContentSign().equals("")) {
                jsonObject.put("contentSign", CollectorCfgCommodity.getContentSign());
            } else {
                jsonObject.put("contentSign", "");
            }
            if (null != CollectorCfgCommodity.getTitleSign() && !CollectorCfgCommodity.getTitleSign().equals("")) {
                jsonObject.put("titleSign", CollectorCfgCommodity.getTitleSign());
            } else {
                jsonObject.put("titleSign", "");
            }
            if (null != CollectorCfgCommodity.getPriceSign() && !CollectorCfgCommodity.getPriceSign().equals("")) {
                jsonObject.put("getPriceSign", CollectorCfgCommodity.getPriceSign());
            } else {
                jsonObject.put("getPriceSign", "");
            }
            if (null != CollectorCfgCommodity.getNextSign() && !CollectorCfgCommodity.getNextSign().equals("")) {
                jsonObject.put("nextSign", CollectorCfgCommodity.getNextSign());
            } else {
                jsonObject.put("nextSign", "");
            }
            if (null != CollectorCfgCommodity.getSaleMountSign() && !CollectorCfgCommodity.getSaleMountSign().equals("")) {
                jsonObject.put("saleMountSign", CollectorCfgCommodity.getSaleMountSign());
            } else {
                jsonObject.put("saleMountSign", "");
            }
            jsonObject.put("pageTotal", CollectorCfgCommodity.getPageTotal());
            
            //副采集页面
            if (null != CollectorCfgCommodity.getBrandSign()) {
                jsonObject.put("brandSign", CollectorCfgCommodity.getBrandSign());
            } else {
                jsonObject.put("brandSign", "");
            }
            if (null != CollectorCfgCommodity.getProducingAreaSign()) {
                jsonObject.put("producingAreaSign", CollectorCfgCommodity.getProducingAreaSign());
            } else {
                jsonObject.put("producingAreaSign", "");
            }
            if (null != CollectorCfgCommodity.getSuitableAgeSign()) {
                jsonObject.put("suitableAgeSign", CollectorCfgCommodity.getSuitableAgeSign());
            } else {
                jsonObject.put("suitableAgeSign", "");
            }
            if (null != CollectorCfgCommodity.getStandardSign()) {
                jsonObject.put("standardSign", CollectorCfgCommodity.getStandardSign());
            } else {
                jsonObject.put("standardSign", "");
            }
            if (null != CollectorCfgCommodity.getExpirationDateSign()) {
                jsonObject.put("expirationDateSign", CollectorCfgCommodity.getExpirationDateSign());
            } else {
                jsonObject.put("expirationDateSign", "");
            }
            if (null != CollectorCfgCommodity.getCountrySign()) {
                jsonObject.put("countrySign", CollectorCfgCommodity.getCountrySign());
            } else {
                jsonObject.put("countrySign", "");
            }
            if (null != CollectorCfgCommodity.getProductTypeSign()) {
                jsonObject.put("productTypeSign", CollectorCfgCommodity.getProductTypeSign());
            } else {
                jsonObject.put("productTypeSign", "");
            }
            
            //外键属性
            if (null != CollectorCfgCommodity.getWebsite()) {
                jsonObject.put("websiteId", CollectorCfgCommodity.getWebsite().getId());
            } else {
                jsonObject.put("websiteId", "");
            }
            if (null != CollectorCfgCommodity.getWebsite()) {
                jsonObject.put("websiteName", CollectorCfgCommodity.getWebsite().getName());
            } else {
                jsonObject.put("websiteName", "");
            }
            if (null != CollectorCfgCommodity.getProductStyle()) {
                jsonObject.put("productStyleId", CollectorCfgCommodity.getProductStyle().getId());
            } else {
                jsonObject.put("productStyleId", "");
            }
            if (null != CollectorCfgCommodity.getProductStyle()) {
                jsonObject.put("productStyleName", CollectorCfgCommodity.getProductStyle().getName());
            } else {
                jsonObject.put("productStyleName", "");
            }
            if (null != CollectorCfgCommodity.getFirstlevelType()) {
                jsonObject.put("firstlevelType", CollectorCfgCommodity.getFirstlevelType().getId());
            } else {
                jsonObject.put("firstlevelType", "");
            }
            if (null != CollectorCfgCommodity.getFirstlevelType()) {
                jsonObject.put("firstlevelTypeName", CollectorCfgCommodity.getFirstlevelType().getName());
            } else {
                jsonObject.put("firstlevelTypeName", "");
            }
            if (null != CollectorCfgCommodity.getSecondlevelType()) {
                jsonObject.put("secondlevelType", CollectorCfgCommodity.getSecondlevelType().getId());
            } else {
                jsonObject.put("secondlevelType", "");
            }
            if (null != CollectorCfgCommodity.getSecondlevelType()) {
                jsonObject.put("secondlevelTypeName", CollectorCfgCommodity.getSecondlevelType().getName());
            } else {
                jsonObject.put("secondlevelTypeName", "");
            }
            if (null != CollectorCfgCommodity.getThirdlevelType()) {
                jsonObject.put("thirdlevelType", CollectorCfgCommodity.getThirdlevelType().getId());
            } else {
                jsonObject.put("thirdlevelType", "");
            }
            if (null != CollectorCfgCommodity.getThirdlevelType()) {
                jsonObject.put("thirdlevelTypeName", CollectorCfgCommodity.getThirdlevelType().getName());
            } else {
                jsonObject.put("thirdlevelTypeName", "");
            }
            jsonArray.put(jsonObject);
        }
        jsonTotal.put("count", count);
        jsonTotal.put("infoList", jsonArray);
        return jsonTotal.toString();
    }

    public String addOrUpdate(String id, String name, String rate, String url, String contentSign, String titleSign,
            String priceSign, String saleMountSign, String nextSign, String pageTotal, String brandSign, String producingAreaSign,
            String suitableAgeSign, String standardSign, String expirationDateSign, String countrySign, String productTypeSign, String websiteId,
            String productStyleId, String firstlevelType, String secondlevelType, String thirdlevelType) throws JSONException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CollectorCfgCommodity collectorCfgCommodity = new CollectorCfgCommodity();
        
        if (null != id && !"".equals(id)) {
            collectorCfgCommodity.setId(Long.valueOf(id));
        }
        collectorCfgCommodity.setName(name);
        collectorCfgCommodity.setCreateTime(sdf.format(new Date()));
        if (null != rate && !"".equals(rate)) {
            collectorCfgCommodity.setRate(Integer.valueOf(rate).intValue());
        }
        
        collectorCfgCommodity.setUrl(url);
        collectorCfgCommodity.setContentSign(contentSign);
        collectorCfgCommodity.setTitleSign(titleSign);
        collectorCfgCommodity.setPriceSign(priceSign);
        collectorCfgCommodity.setSaleMountSign(saleMountSign);
        collectorCfgCommodity.setNextSign(nextSign);
        if (null != pageTotal && !"".equals(pageTotal)) {
            collectorCfgCommodity.setPageTotal(Integer.valueOf(pageTotal).intValue());
        }
        
        collectorCfgCommodity.setBrandSign(brandSign);
        collectorCfgCommodity.setProducingAreaSign(producingAreaSign);
        collectorCfgCommodity.setSuitableAgeSign(suitableAgeSign);
        collectorCfgCommodity.setStandardSign(standardSign);
        collectorCfgCommodity.setExpirationDateSign(expirationDateSign);
        collectorCfgCommodity.setCountrySign(countrySign);
        collectorCfgCommodity.setProductTypeSign(productTypeSign);
        
        if (null != websiteId && !"".equals(websiteId)) {
            collectorCfgCommodity.setWebsite(websiteDAO.getWebsite(Long.valueOf(websiteId)));
        }
        if (null != productStyleId && !"".equals(productStyleId)) {
            collectorCfgCommodity.setProductStyle(productStyleDAO.getProductStyle(Long.valueOf(productStyleId)));
        }
        if (null != firstlevelType && !"".equals(firstlevelType)) {
            collectorCfgCommodity.setFirstlevelType(productTypeDAO.getProductType(Long.valueOf(firstlevelType)));
        }
        if (null != secondlevelType && !"".equals(secondlevelType)) {
            collectorCfgCommodity.setSecondlevelType(productTypeDAO.getProductType(Long.valueOf(secondlevelType)));
        }
        if (null != thirdlevelType && !"".equals(thirdlevelType)) {
            collectorCfgCommodity.setThirdlevelType(productTypeDAO.getProductType(Long.valueOf(thirdlevelType)));
        }
        collectorCfgCommodityDAO.addOrUpdate(collectorCfgCommodity);
        JSONObject jsonObject = new JSONObject();
        //基本属性
        jsonObject.put("id", collectorCfgCommodity.getId());
        if (null != collectorCfgCommodity.getName() && !collectorCfgCommodity.getName().equals("")) {
            jsonObject.put("name", collectorCfgCommodity.getName());
        } else {
            jsonObject.put("name", "");
        }
        if (null != collectorCfgCommodity.getCreateTime() && !collectorCfgCommodity.getCreateTime().equals("")) {
            jsonObject.put("createTime", collectorCfgCommodity.getCreateTime());
        } else {
            jsonObject.put("createTime", "");
        }
        jsonObject.put("rate", collectorCfgCommodity.getRate());
        
        //主采集页面
        if (null != collectorCfgCommodity.getUrl() && !collectorCfgCommodity.getUrl().equals("")) {
            jsonObject.put("url", collectorCfgCommodity.getUrl());
        } else {
            jsonObject.put("url", "");
        }
        if (null != collectorCfgCommodity.getContentSign() && !collectorCfgCommodity.getContentSign().equals("")) {
            jsonObject.put("contentSign", collectorCfgCommodity.getContentSign());
        } else {
            jsonObject.put("contentSign", "");
        }
        if (null != collectorCfgCommodity.getTitleSign() && !collectorCfgCommodity.getTitleSign().equals("")) {
            jsonObject.put("titleSign", collectorCfgCommodity.getTitleSign());
        } else {
            jsonObject.put("titleSign", "");
        }
        if (null != collectorCfgCommodity.getPriceSign() && !collectorCfgCommodity.getPriceSign().equals("")) {
            jsonObject.put("getPriceSign", collectorCfgCommodity.getPriceSign());
        } else {
            jsonObject.put("getPriceSign", "");
        }
        if (null != collectorCfgCommodity.getNextSign() && !collectorCfgCommodity.getNextSign().equals("")) {
            jsonObject.put("nextSign", collectorCfgCommodity.getNextSign());
        } else {
            jsonObject.put("nextSign", "");
        }
        if (null != collectorCfgCommodity.getSaleMountSign() && !collectorCfgCommodity.getSaleMountSign().equals("")) {
            jsonObject.put("saleMountSign", collectorCfgCommodity.getSaleMountSign());
        } else {
            jsonObject.put("saleMountSign", "");
        }
        jsonObject.put("pageTotal", collectorCfgCommodity.getPageTotal());
        
        //副采集页面
        if (null != collectorCfgCommodity.getBrandSign()) {
            jsonObject.put("brandSign", collectorCfgCommodity.getBrandSign());
        } else {
            jsonObject.put("brandSign", "");
        }
        if (null != collectorCfgCommodity.getProducingAreaSign()) {
            jsonObject.put("producingAreaSign", collectorCfgCommodity.getProducingAreaSign());
        } else {
            jsonObject.put("producingAreaSign", "");
        }
        if (null != collectorCfgCommodity.getSuitableAgeSign()) {
            jsonObject.put("suitableAgeSign", collectorCfgCommodity.getSuitableAgeSign());
        } else {
            jsonObject.put("suitableAgeSign", "");
        }
        if (null != collectorCfgCommodity.getStandardSign()) {
            jsonObject.put("standardSign", collectorCfgCommodity.getStandardSign());
        } else {
            jsonObject.put("standardSign", "");
        }
        if (null != collectorCfgCommodity.getExpirationDateSign()) {
            jsonObject.put("expirationDateSign", collectorCfgCommodity.getExpirationDateSign());
        } else {
            jsonObject.put("expirationDateSign", "");
        }
        if (null != collectorCfgCommodity.getCountrySign()) {
            jsonObject.put("countrySign", collectorCfgCommodity.getCountrySign());
        } else {
            jsonObject.put("countrySign", "");
        }
        if (null != collectorCfgCommodity.getProductTypeSign()) {
            jsonObject.put("productTypeSign", collectorCfgCommodity.getProductTypeSign());
        } else {
            jsonObject.put("productTypeSign", "");
        }

        //外键属性
        if (null != collectorCfgCommodity.getWebsite()) {
            jsonObject.put("websiteId", collectorCfgCommodity.getWebsite().getId());
        } else {
            jsonObject.put("websiteId", "");
        }
        if (null != collectorCfgCommodity.getProductStyle()) {
            jsonObject.put("productStyleId", collectorCfgCommodity.getProductStyle().getId());
        } else {
            jsonObject.put("productStyleId", "");
        }
        if (null != collectorCfgCommodity.getFirstlevelType()) {
            jsonObject.put("firstlevelType", collectorCfgCommodity.getFirstlevelType().getId());
        } else {
            jsonObject.put("firstlevelType", "");
        }
        if (null != collectorCfgCommodity.getSecondlevelType()) {
            jsonObject.put("secondlevelType", collectorCfgCommodity.getSecondlevelType().getId());
        } else {
            jsonObject.put("secondlevelType", "");
        }
        if (null != collectorCfgCommodity.getThirdlevelType()) {
            jsonObject.put("thirdlevelType", collectorCfgCommodity.getThirdlevelType().getId());
        } else {
            jsonObject.put("thirdlevelType", "");
        }
        return jsonObject.toString();
    }

    public void delete(String id)
    {
        CollectorCfgCommodity collectorCfgCommodity = collectorCfgCommodityDAO.findById(Long.valueOf(id));
        collectorCfgCommodityDAO.delete(collectorCfgCommodity);
    }

    public void excute(Long id)
    {
        CollectorCfgCommodity collectorCfgCommodity = collectorCfgCommodityDAO.findById(id);
        
        collectionCommodity.setId(id);
        collectionCommodity.setProductStyle(collectorCfgCommodity.getProductStyle());
        collectionCommodity.setFirstlevelType(collectorCfgCommodity.getFirstlevelType());
        collectionCommodity.setSecondlevelType(collectorCfgCommodity.getSecondlevelType());
        collectionCommodity.setThirdlevelType(collectorCfgCommodity.getThirdlevelType());
        
        collectionCommodity.setUrl(collectorCfgCommodity.getUrl());
        collectionCommodity.setAllSign(collectorCfgCommodity.getContentSign());
        collectionCommodity.setTitleSign(collectorCfgCommodity.getTitleSign());
        collectionCommodity.setPriceSign(collectorCfgCommodity.getPriceSign());
        collectionCommodity.setSaleMountSign(collectorCfgCommodity.getSaleMountSign());
        collectionCommodity.setNextSign(collectorCfgCommodity.getNextSign());
        collectionCommodity.setTotlePage(collectorCfgCommodity.getPageTotal());
        
        collectionCommodity.setBrandSign(collectorCfgCommodity.getBrandSign());
        collectionCommodity.setProducingAreaSign(collectorCfgCommodity.getProducingAreaSign());
        collectionCommodity.setSuitableAgeSign(collectorCfgCommodity.getSuitableAgeSign());
        collectionCommodity.setStandardSign(collectorCfgCommodity.getStandardSign());
        collectionCommodity.setExpirationDateSign(collectorCfgCommodity.getExpirationDateSign());
        collectionCommodity.setCountrySign(collectorCfgCommodity.getCountrySign());
        collectionCommodity.setProductTypeSign(collectorCfgCommodity.getProductTypeSign());
        
        new Thread(collectionCommodity).start();
    }
}
