package com.tonik.service;

import java.text.DecimalFormat;
import java.util.List;

import com.google.common.collect.Lists;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IEvaluationManagementDAO;
import com.tonik.model.EvaluationIndex;
import com.tonik.model.EvaluationManagement;

/**
 * @spring.bean id="EvaluationManagementService"
 * @spring.property name="evaluationManagementDAO" ref="EvaluationManagementDAO"
 */
public class EvaluationManagementService
{
    private IEvaluationManagementDAO evaluationManagementDAO;


    public IEvaluationManagementDAO getEvaluationManagementDAO()
    {
        return evaluationManagementDAO;
    }

    public void setEvaluationManagementDAO(IEvaluationManagementDAO evaluationManagementDAO)
    {
        this.evaluationManagementDAO = evaluationManagementDAO;
    }

    public String add(Integer type, String productType, String weights)
    {
        try
        {
            evaluationManagementDAO.delEvaluationManagements(type, productType);
            for (String item : weights.split("#"))
            {
                String[] item2 = item.split(",");
                Long evaluationIndexId = Long.parseLong(item2[0]);
                Float weight = Float.parseFloat(item2[1]);
                EvaluationManagement em = new EvaluationManagement();
                em.setEvaluationType(type);
                if (type == 3)
                    em.setEvaluationObjectId(Long.parseLong(productType));
                em.setEvaluationIndex(new EvaluationIndex().setId(evaluationIndexId));
                em.setEvaluationIndexWeight(weight);
                evaluationManagementDAO.saveObject(em);
            }
            return "true";
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String init(Integer type, String productType, String strQuery) throws Exception
    {
        List<EvaluationManagementVO> emVOs = Lists.newArrayList();
        List<Object[]> list = evaluationManagementDAO.initEvaluationManagement(type, productType, strQuery);
        for (Object[] item : list)
        {
            EvaluationManagementVO emVO = new EvaluationManagementVO(item[0].toString(), item[1].toString(),
                    (item[2] == null) ? "" : new DecimalFormat("##0.##").format(item[2]),
                    (item[2] == null) ? false : true, (item[3] == null) ? "" : item[3].toString());
            emVOs.add(emVO);
        }
        return GsonUtil.bean2Json(emVOs);
    }

    public String del(Long id) throws Exception
    {
        evaluationManagementDAO.removeObject(EvaluationManagement.class, id);
        return "true";
    }


    public class EvaluationManagementVO
    {

        private String id = "";

        private String name = "";

        private String weight = "";

        private Boolean isChecked; // 是否选中

        private String evaluationManagemenId = "";


        public EvaluationManagementVO(String id, String name, String weight, Boolean isChecked,
                String evaluationManagemenId)
        {
            this.id = id;
            this.name = name;
            this.weight = weight;
            this.isChecked = isChecked;
            this.evaluationManagemenId = evaluationManagemenId;
        }
    }
}
