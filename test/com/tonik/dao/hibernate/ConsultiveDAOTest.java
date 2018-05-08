package com.tonik.dao.hibernate;

import java.util.Date;

import com.tonik.dao.IConsultiveDAO;
import com.tonik.model.Consultive;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing ConsultiveDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="consultiveDAOTest"
 * @spring.property name="consultiveDAO" ref="ConsultiveDAO"
 */
public class ConsultiveDAOTest extends BaseDaoTestCase
{
    private IConsultiveDAO consultiveDAO;


    public void setconsultiveDAO(IConsultiveDAO consultiveDAO)
    {
        this.consultiveDAO = consultiveDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ConsultiveDAOTest.class);
    }

    public void testAddAndRemoveconsultive() throws Exception
    {
        Consultive consultive = new Consultive();
        consultive.setType(1);
        consultive.setTitle("How to buy on sb's behalf goods?");
        consultive.setContent("As title, please introduce process!");
        consultive.setReturnTime(new Date());
        consultive.setReturnContent("As title, please introduce process!");
        consultive.setConsultant(1L);
        consultive.setConsultiveTime(new Date());
        consultive.setCreatePerson("LiFeiFei");
        consultive.setCreateTime(new Date());
        consultiveDAO.saveConsultive(consultive);
        setComplete();
        endTransaction();

        startNewTransaction();
        consultive = consultiveDAO.getConsultive(1l);
        assertNotNull(consultive.getType());
        assertNotNull(consultive.getTitle());
        assertNotNull(consultive.getContent());
        assertNotNull(consultive.getReturnTime());
        assertNotNull(consultive.getReturnContent());
        assertNotNull(consultive.getConsultant());
        assertNotNull(consultive.getConsultiveTime());
        assertNotNull(consultive.getCreatePerson());
        assertNotNull(consultive.getCreateTime());

        consultiveDAO.removeConsultive(consultive);
        setComplete();
        endTransaction();

        consultive = consultiveDAO.getConsultive(1l);
        assertNull(consultive);
    }
}
