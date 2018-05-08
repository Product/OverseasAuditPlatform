package com.tonik.dao.hibernate;


import java.util.Date;

import com.tonik.dao.IDetectingRecordDAO;
import com.tonik.model.DetectingRecord;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing DetectingRecordDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="detectingRecordDAOTest"
 * @spring.property name="detectingRecordDAO" ref="DetectingRecordDAO"
 */
public class DetectingRecordDAOTest extends BaseDaoTestCase
{
    private IDetectingRecordDAO detectingRecordDAO;


    public void setDetectingRecordDAO(IDetectingRecordDAO detectingRecordDAO)
    {
        this.detectingRecordDAO = detectingRecordDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(DetectingRecordDAOTest.class);
    }

    public void testAddAndRemovedetectingRecord() throws Exception
    {
        DetectingRecord detectingRecord = new DetectingRecord();
  //      detectingRecord.setRespondents("NaiFenLei");
  //      detectingRecord.setDetectingId(2L);
        detectingRecord.setSample(2);
  //      detectingRecord.setRandomOrganization(1);
        detectingRecord.setQualified(3);
        detectingRecord.setRemark("Good");
       //detectingRecord.setCreatePerson("LiFeiFei");
        detectingRecord.setCreateTime(new Date());
        detectingRecordDAO.saveDetectingRecord(detectingRecord);
        setComplete();
        endTransaction();

        startNewTransaction();
        detectingRecord = detectingRecordDAO.getDetectingRecord(1l);
        //assertNotNull(detectingRecord.getRespondents());
 //       assertNotNull(detectingRecord.getDetectingId());
        assertNotNull(detectingRecord.getSample());
        //assertNotNull(detectingRecord.getOrganization());
        assertNotNull(detectingRecord.getQualified());
        assertNotNull(detectingRecord.getRemark());
       // assertNotNull(detectingRecord.getCreatePerson());
        assertNotNull(detectingRecord.getCreateTime());

        detectingRecordDAO.removeDetectingRecord(detectingRecord);
        setComplete();
        endTransaction();

        detectingRecord = detectingRecordDAO.getDetectingRecord(1l);
        assertNull(detectingRecord);
    }
}

