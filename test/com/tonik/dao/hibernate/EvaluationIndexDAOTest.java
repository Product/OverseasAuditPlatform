package com.tonik.dao.hibernate;

import java.text.SimpleDateFormat;

import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IEvaluationIndexDAO;
import com.tonik.model.EvaluationIndex;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing EvaluationIndexDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="evaluationIndexDAOTest"
 * @spring.property name="evaluationIndexDAO" ref="EvaluationIndexDAO"
 */
public class EvaluationIndexDAOTest extends BaseDaoTestCase
{
    private IEvaluationIndexDAO evaluationIndexDAO;

    public void setEvaluationIndexDAO(IEvaluationIndexDAO evaluationIndexDAO)
    {
        this.evaluationIndexDAO = evaluationIndexDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(EvaluationIndexDAOTest.class);
    }

    public void testGetEvaluationIndices() throws Exception
    {
        String str = GsonUtil.bean2Json(evaluationIndexDAO.getEvaluationIndices(""));
        System.out.println(str);
        String str1 = GsonUtil.bean2Json(evaluationIndexDAO.getEvaluationIndices(""));
        System.out.println(str1);
    }
    
//    public void testAddAndRemoveevaluationIndex() throws Exception
//    {
//        EvaluationIndex evaluationIndex = new EvaluationIndex();
//        evaluationIndex.setName("fd");
//        evaluationIndex.setEvaluationStyle(null);
//        evaluationIndex.setWeight(1);
//        evaluationIndex.setRemark("remark");
//        evaluationIndex.setCreatePerson(null);
//        evaluationIndex.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-03-06 11:27:50"));
//        evaluationIndexDAO.saveEvaluationIndex(evaluationIndex);
//        setComplete();
//        endTransaction();
//
//        startNewTransaction();
//        evaluationIndex = evaluationIndexDAO.getEvaluationIndex(1l);
//        assertNotNull(evaluationIndex.getName());
//        assertNotNull(evaluationIndex.getEvaluationStyle());
//        assertNotNull(evaluationIndex.getWeight());
//        assertNotNull(evaluationIndex.getRemark());
//        assertNotNull(evaluationIndex.getCreatePerson());
//        assertNotNull(evaluationIndex.getCreateTime());
//
//        evaluationIndexDAO.removeEvaluationIndex(evaluationIndex);
//        setComplete();
//        endTransaction();
//
//        evaluationIndex = evaluationIndexDAO.getEvaluationIndex(1l);
//        assertNull(evaluationIndex);
//    }
}

