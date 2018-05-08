package com.tonik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thinvent.utils.GsonUtil;
import com.thinvent.utils.HibernateProxyTypeAdapter;
import com.tonik.dao.IEvaluationIndexDAO;
import com.tonik.dao.IEvaluationStyleDAO;
import com.tonik.dao.IUserDAO;
import com.tonik.model.EvaluationIndex;
import com.tonik.model.EvaluationStyle;

/**
 * @spring.bean id="EvaluationIndexService"
 * @spring.property name="evaluationIndexDAO" ref="EvaluationIndexDAO"
 * @spring.property name="evaluationStyleDAO" ref="EvaluationStyleDAO"
 * @spring.property name="userDAO" ref="UserDAO"
 */
public class EvaluationIndexService  
{
    private IEvaluationIndexDAO EvaluationIndexDAO;
    private IEvaluationStyleDAO EvaluationStyleDAO;
    private IUserDAO UserDAO;
    public void setEvaluationIndexDAO(IEvaluationIndexDAO EvaluationIndexDAO)
    {
        this.EvaluationIndexDAO = EvaluationIndexDAO;
    }
    
    public IEvaluationStyleDAO getEvaluationStyleDAO()
    {
        return EvaluationStyleDAO;
    }

    public void setEvaluationStyleDAO(IEvaluationStyleDAO evaluationStyleDAO)
    {
        EvaluationStyleDAO = evaluationStyleDAO;
    }

    public IEvaluationIndexDAO getEvaluationIndexDAO()
    {
        return EvaluationIndexDAO;
    }
    
    public IUserDAO getUserDAO()
    {
        return UserDAO;
    }

    public void setUserDAO(IUserDAO userDAO)
    {
        UserDAO = userDAO;
    }

    public List<EvaluationIndex> EvaluationIndexPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        List<Object[]> ls= EvaluationIndexDAO.getEvaluationIndexPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<EvaluationIndex> eis = new ArrayList<EvaluationIndex>();
        for(Object[] obj:ls){
            EvaluationIndex ei = (EvaluationIndex)obj[0];
            eis.add(ei);
        }
        return eis;
    }
    
    public String EvaluationIndexTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(EvaluationIndexDAO.getEvaluationIndexTotal(strQuery, strStraTime, strEndTime));
    }
    
    public void SaveEvaluationIndex(EvaluationIndex evaluationIndex, String style)
    {
        EvaluationStyle es = EvaluationStyleDAO.getEvaluationStyle(Long.parseLong(style));

        evaluationIndex.setEvaluationStyle(es);
        EvaluationIndexDAO.saveEvaluationIndex(evaluationIndex);
    }
    
    public void DelEvaluationStyle(Long Id)
    {
        EvaluationIndexDAO.removeObject(EvaluationIndex.class, Id);
    }
    
    public EvaluationIndex GetEvaluationStyleById(Long Id)
    {
        return EvaluationIndexDAO.getEvaluationIndex(Id);
    }
    
    public String getEvaluationIndexInfo(EvaluationIndex ei){
        String res = "";
        res += "{\"Id\":\"" + ei.getId() + "\",\"Name\":\"" + ei.getName() + "\",\"Weight\":\""
                + ei.getWeight() + "\",\"CreatePerson\":\"" + ei.getCreatePersonName()
                + "\",\"StyleName\":\"" + ei.getEvaluationStyleName()
                + "\",\"Style\":\"" + ei.getEvaluationStyle().getId() + "\",\"Remark\":\"" + ei.getRemark()
                + "\",\"Type\":\"" + ei.getType() + "\",\"CreateTime\":\"" + ei.getFormatCreateTime()
                + "\",\"ScoreNo\":\"" + ei.getScore_no() + "\",\"ScoreYes\":\"" + ei.getScore_yes()
                + "\",\"NoneMin\":\"" + ei.getNone_min() + "\",\"NoneMax\":\"" + ei.getNone_max()
                + "\",\"OneMin\":\"" + ei.getOne_min() + "\",\"OneMax\":\"" + ei.getOne_max()
                + "\",\"TwoMin\":\"" + ei.getTwo_min() + "\",\"TwoMax\":\"" + ei.getTwo_max()
                + "\",\"ThreeMin\":\"" + ei.getThree_min() + "\",\"ThreeMax\":\"" + ei.getThree_max() + "\"}";
        return res;
    }

    public String getAllIndices()
    {
        List<IndicesVO> lVOs = Lists.newArrayList();
        List<EvaluationStyle> lES = EvaluationStyleDAO.getEvaluationStyle();
        for(EvaluationStyle item : lES)
        {
            List<EvaluationIndex> lEI = EvaluationIndexDAO.getEvaluationIndexByStyle(item.getId());
            List<Map<String, String>> indices = Lists.newArrayList();
            for(EvaluationIndex item2 : lEI)
            {
                Map<String, String> index = Maps.newHashMap();
                index.put("name", item2.getName());
                indices.add(index);
            }
            lVOs.add(new IndicesVO(item.getName(), indices));
        }
        return GsonUtil.bean2Json(lVOs);
    }
    
    public String getEvaluationIndex(Long id)
    {
        EvaluationIndex ei = (EvaluationIndex) EvaluationIndexDAO.loadObject(EvaluationIndex.class, id);
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
        return gson.toJson(ei);
    }
    
public class IndicesVO {
        
        private String styleName = "";
        
        private List<Map<String, String>> indices;
    
        public IndicesVO(String styleName, List<Map<String, String>> indices) {
            this.styleName = styleName;
            this.indices = indices;
        }
    }
}
