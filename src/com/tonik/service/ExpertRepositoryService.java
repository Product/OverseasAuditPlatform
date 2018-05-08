package com.tonik.service;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.lob.SerializableClob;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tonik.dao.IProductTypeDAO;
import com.thinvent.utils.DateUtil;
import com.tonik.dao.IExpertRepositoryDAO;
import com.tonik.model.ExpertRepository;
import com.tonik.model.ProductType;

/**
 * @desc: 专家知识库服务层
 * @author fuzhi
 * @spring.bean id="ExpertRepositoryService"
 * @spring.property name="expertRepositoryDAO" ref="ExpertRepositoryDAO"
 * @spring.property name="productTypeDAO" ref="ProductTypeDAO"
 */
public class ExpertRepositoryService
{
    private IExpertRepositoryDAO expertRepositoryDAO;
    private IProductTypeDAO productTypeDAO;

    public IExpertRepositoryDAO getExpertRepositoryDAO()
    {
        return expertRepositoryDAO;
    }

    public void setExpertRepositoryDAO(IExpertRepositoryDAO expertRepositoryDAO)
    {
        this.expertRepositoryDAO = expertRepositoryDAO;
    }

    public IProductTypeDAO getProductTypeDAO()
    {
        return productTypeDAO;
    }

    public void setProductTypeDAO(IProductTypeDAO productTypeDAO)
    {
        this.productTypeDAO = productTypeDAO;
    }

    public String initList(String index, String size) throws JSONException
    {
        int pageIndex = 1;
        int pageSize = 10;
        if(null != index && !"".equals(index) && null != size && !"".equals(size))
        {
            pageIndex = Integer.valueOf(index).intValue();
            pageSize = Integer.valueOf(size).intValue();
        }
        List<ExpertRepository> list = expertRepositoryDAO.initList(pageIndex, pageSize);
        int count = expertRepositoryDAO.initCount();
        JSONObject jsonOutWrite = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (ExpertRepository expertRepository : list)
        {
            JSONObject jsonObject = new JSONObject();
            if(null == expertRepository.getId()){
                jsonObject.put("id", "");
            }else{
                jsonObject.put("id", expertRepository.getId());
            }
            if(null != expertRepository.getTitle()){
                jsonObject.put("title", expertRepository.getTitle());
            }else{
                jsonObject.put("title", "");
            }
            if(null != expertRepository.getAuthor()){
                jsonObject.put("author", expertRepository.getAuthor());
            }else{
                jsonObject.put("author", "");
            }
            if(null != expertRepository.getCreatetime()){
                jsonObject.put("createtime", expertRepository.getCreatetime());
            }else{
                jsonObject.put("createtime", "");
            }
            if(null != expertRepository.getContent()){
                jsonObject.put("content", expertRepository.getContent());
            }else{
                jsonObject.put("content", "");
            }
            if(null != expertRepository.getProductType()){
                jsonObject.put("typeId", expertRepository.getProductType().getId());
            }else{
                jsonObject.put("typeId", "");
            }
            jsonArray.put(jsonObject);
        }
        jsonOutWrite.put("count", count);
        jsonOutWrite.put("repositoryList", jsonArray);
        return jsonOutWrite.toString();
    }
    
    public String queryById(String id) throws JSONException
    {
        Long longId = Long.valueOf(id);
        ExpertRepository expertRepository = expertRepositoryDAO.queryById(longId);
        JSONObject jsonObject = new JSONObject();
        if(null == expertRepository.getId()){
            jsonObject.put("id", "");
        }else{
            jsonObject.put("id", expertRepository.getId());
        }
        if(null != expertRepository.getTitle()){
            jsonObject.put("title", expertRepository.getTitle());
        }else{
            jsonObject.put("title", "");
        }
        if(null != expertRepository.getAuthor()){
            jsonObject.put("author", expertRepository.getAuthor());
        }else{
            jsonObject.put("author", "");
        }
        if(null != expertRepository.getCreatetime()){
            jsonObject.put("createtime", expertRepository.getCreatetime());
        }else{
            jsonObject.put("createtime", "");
        }
        if(null != expertRepository.getContent()){
            jsonObject.put("content", expertRepository.getContent());
        }else{
            jsonObject.put("content", "");
        }
        if(null != expertRepository.getProductType()){
            jsonObject.put("typeId", expertRepository.getProductType().getId());
        }else{
            jsonObject.put("typeId", "");
        }
        return jsonObject.toString();
    }
    
    public String queryListByKey(String index, String size, String keyword, String typeId) throws JSONException
    {
        int pageIndex = 1;
        int pageSize = 10;
        if(null != index && !"".equals(index) && null != size && !"".equals(size))
        {
            pageIndex = Integer.valueOf(index).intValue();
            pageSize = Integer.valueOf(size).intValue();
        }
        Long lo = Long.valueOf(typeId);
        List<Object[]> list = expertRepositoryDAO.queryListByKey(pageIndex, pageSize, keyword, lo);
        int count = expertRepositoryDAO.queryCountByKey(keyword, lo);
        JSONObject jsonOutWrite = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Object[] object : list)
        {
            JSONObject jsonObject = new JSONObject();
            if(null == object[1]){
                jsonObject.put("id", "");
            }else{
                jsonObject.put("id", object[1]);
            }
            if(null != object[2]){
                jsonObject.put("title", object[2]);
            }else{
                jsonObject.put("title", "");
            }
            if(null != object[3]){
                jsonObject.put("createtime", DateUtil.formatDate(((Timestamp)object[3])));
            }else{
                jsonObject.put("createtime", "");
            }
            if(null != object[4]){
                jsonObject.put("author", object[4]);
            }else{
                jsonObject.put("author", "");
            }
            if(null != object[5]){
                String contentInner = getClob((SerializableClob)object[5]);
                if(contentInner.length() > 30){                    
                    jsonObject.put("content", contentInner.substring(0, 30)+"...");
                }else{
                    jsonObject.put("content", contentInner);
                }
            }else{
                jsonObject.put("content", "");
            }
            if(null != object[6]){
                jsonObject.put("typeId", object[6]);
            }else{
                jsonObject.put("typeId", "");
            }
            jsonArray.put(jsonObject);
        }
        jsonOutWrite.put("count", count);
        jsonOutWrite.put("repositoryList", jsonArray);
        return jsonOutWrite.toString();
    }
    public String update(String id, String title,  String createtime,  String author,  String content, String typeId) throws ParseException, JSONException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date ct = sdf.parse(createtime);
        ProductType productType = productTypeDAO.getProductType(Long.valueOf(typeId));
        ExpertRepository expertRepository = new ExpertRepository();
        if(null != id && !"".equals(id)){
            expertRepository.setId(Long.valueOf(id));
        }
        expertRepository.setTitle(title);
        expertRepository.setCreatetime(ct);
        expertRepository.setAuthor(author);
        expertRepository.setContent(content);
        expertRepository.setProductType(productType);
        expertRepositoryDAO.save(expertRepository);
        JSONObject jsonObject = new JSONObject();
        if(null != expertRepository.getId()){
            jsonObject.put("id", expertRepository.getId());
        }else{
            jsonObject.put("id", "");
        }
        if(null != expertRepository.getTitle()){
            jsonObject.put("title", expertRepository.getTitle());
        }else{
            jsonObject.put("title", "");
        }
        if(null != expertRepository.getAuthor()){
            jsonObject.put("author", expertRepository.getAuthor());
        }else{
            jsonObject.put("author", "");
        }
        if(null != expertRepository.getCreatetime()){
            jsonObject.put("createtime", expertRepository.getCreatetime());
        }else{
            jsonObject.put("createtime", "");
        }
        if(null != expertRepository.getContent()){
            jsonObject.put("content", expertRepository.getContent());
        }else{
            jsonObject.put("content", "");
        }
        if(null != expertRepository.getProductType()){
            jsonObject.put("typeId", expertRepository.getProductType().getId());
        }else{
            jsonObject.put("typeId", "");
        }
        return jsonObject.toString();
    }

    public String del(String id)
    {
        Long longId = Long.valueOf(id);
        ExpertRepository expertRepository = expertRepositoryDAO.queryById(longId);
        expertRepositoryDAO.del(expertRepository);
        return "true";
    }
    public String getClob(SerializableClob c){
        Reader reader;
        StringBuffer sb = new StringBuffer();
        try {
         reader = c.getCharacterStream();
         BufferedReader br = new BufferedReader(reader);
         String temp = null;
         while ((temp=br.readLine()) != null) {
          sb.append(temp);
         }
        } catch (Exception e) {
         e.printStackTrace();
        }  
        return sb.toString();
       }
}
