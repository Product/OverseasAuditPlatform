package com.tonik.dao.hibernate;

import java.text.SimpleDateFormat;

import com.tonik.dao.IEvaluationDAO;
import com.tonik.model.Evaluation;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing EvaluationDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="evaluationDAOTest"
 * @spring.property name="evaluationDAO" ref="EvaluationDAO"
 */
public class EvaluationDAOTest extends BaseDaoTestCase
{
    private IEvaluationDAO evaluationDAO;


    public void setEvaluationDAO(IEvaluationDAO evaluationDAO)
    {
        this.evaluationDAO = evaluationDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(EvaluationDAOTest.class);
    }

    public void testAddAndRemoveevaluation() throws Exception
    {
        Evaluation evaluation = new Evaluation();
        //evaluation.setName("ymatou");
        //evaluation.setLocation("http://www.ymatou.com/");
        //evaluation.setWebSiteStyle(1);
        evaluation.setScore(20);
        evaluation.setEvaluationStatus(2);
        evaluation.setRemark("hao");
        //evaluation.setCreatePerson("LiFeiFei");
        evaluation.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-03-06 11:27:50"));
        evaluationDAO.saveEvaluation(evaluation);
        setComplete();
        endTransaction();

        startNewTransaction();
        evaluation = evaluationDAO.getEvaluation(1l);
        //assertNotNull(evaluation.getName());
        //assertNotNull(evaluation.getLocation());
        //assertNotNull(evaluation.getWebSiteStyle());
        assertNotNull(evaluation.getScore());
        assertNotNull(evaluation.getEvaluationStatus());
        assertNotNull(evaluation.getRemark());
        assertNotNull(evaluation.getCreatePerson());
        assertNotNull(evaluation.getCreateTime());

        evaluationDAO.removeEvaluation(evaluation);
        setComplete();
        endTransaction();

        evaluation = evaluationDAO.getEvaluation(1l);
        assertNull(evaluation);
    }
}

