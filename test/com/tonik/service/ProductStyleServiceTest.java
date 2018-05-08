package com.tonik.service;

import com.tonik.dao.hibernate.BaseDaoTestCase;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing StateDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * spring.bean id="ProductStyleServiceTest"
 * spring.property name="consultiveService" ref="ProductStyleService"
 */
public class ProductStyleServiceTest extends BaseDaoTestCase
{
    private ProductStyleService ProductStyleService;

    

    public ProductStyleService getProductStyleService()
    {
        return ProductStyleService;
    }

    public void setProductStyleService(ProductStyleService ProductStyleService)
    {
        this.ProductStyleService = ProductStyleService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ProductStyleServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(ProductStyleService);
    }

}
