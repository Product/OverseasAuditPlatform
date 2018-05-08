package com.tonik.dao.hibernate;

import java.util.Date;

import com.tonik.dao.IProductStyleDAO;
import com.tonik.model.ProductStyle;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing ProductStyleDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="productStyleDAOTest"
 * @spring.property name="productStyleDAO" ref="ProductStyleDAO"
 */
public class ProductStyleDAOTest extends BaseDaoTestCase
{
    private IProductStyleDAO productStyleDAO;


    public void setProductStyleDAO(IProductStyleDAO productStyleDAO)
    {
        this.productStyleDAO = productStyleDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ProductStyleDAOTest.class);
    }

    public void testAddAndRemoveproductStyle() throws Exception
    {
        ProductStyle productStyle = new ProductStyle();
        productStyle.setName("name");
        productStyle.setRemark("remark");
        productStyle.setCreatePerson("Person");
        productStyle.setCreateTime(new Date());
        productStyleDAO.saveProductStyle(productStyle);
        setComplete();
        endTransaction();

        startNewTransaction();
        productStyle = productStyleDAO.getProductStyle(1l);
        assertNotNull(productStyle.getName());
        assertNotNull(productStyle.getRemark());
        assertNotNull(productStyle.getCreatePerson());
        assertNotNull(productStyle.getCreateTime());

        productStyleDAO.removeProductStyle(productStyle);
        setComplete();
        endTransaction();

        productStyle = productStyleDAO.getProductStyle(1l);
        assertNull(productStyle);
    }
}

