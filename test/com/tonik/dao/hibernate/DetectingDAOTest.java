package com.tonik.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tonik.dao.IDetectingDAO;
import com.tonik.model.DetectingEvent;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing DetectingDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="detectingDAOTest"
 * @spring.property name="detectingDAO" ref="DetectingDAO"
 */
public class DetectingDAOTest extends BaseDaoTestCase
{
    private IDetectingDAO detectingDAO;


    public void setDetectingDAO(IDetectingDAO detectingDAO)
    {
        this.detectingDAO = detectingDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(DetectingDAOTest.class);
    }

    public void testAddAndRemovedetecting() throws Exception
    {
        DetectingEvent detecting = new DetectingEvent();
   //     detecting.setWebId(1L);
    //    detecting.setWebName("yangmatou");
        //detecting.setEventType("1");
   //     detecting.setRespondents("Aptamil 1200g");
    //    detecting.setRandomOrganization(1);
        detecting.setRemark("ok");
        detecting.setDetectingDate(new Date());
     //   detecting.setCreatePerson("LiFeiFei");
        detecting.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-03-06 11:27:50"));
        detectingDAO.saveDetecting(detecting);
        setComplete();
        endTransaction();

        startNewTransaction();
        detecting = detectingDAO.getDetecting(1l);
   //     assertNotNull(detecting.getWebId());
   //     assertNotNull(detecting.getWebName());
        assertNotNull(detecting.getEventType());
    //    assertNotNull(detecting.getRespondents());
     //   assertNotNull(detecting.getRandomOrganization());
        assertNotNull(detecting.getRemark());
        assertNotNull(detecting.getDetectingDate());
       // assertNotNull(detecting.getCreatePerson());
        assertNotNull(detecting.getCreateTime());

        detectingDAO.removeDetecting(detecting);
        setComplete();
        endTransaction();

        detecting = detectingDAO.getDetecting(1l);
        assertNull(detecting);
    }
}

