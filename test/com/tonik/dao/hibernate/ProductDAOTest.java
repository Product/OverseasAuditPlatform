package com.tonik.dao.hibernate;

import java.util.Date;
import java.util.List;

import com.tonik.dao.IProductDAO;
import com.tonik.model.Product;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing ProductDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="productDAOTest"
 * @spring.property name="productDAO" ref="ProductDAO"
 */
public class ProductDAOTest extends BaseDaoTestCase
{
    private IProductDAO productDAO;


    public void setProductDAO(IProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ProductDAOTest.class);
    }

    public void testGetProducts() throws Exception
    {
        //List<Object[]> ls = productDAO.getProductPaging1(1, 10, "德国", "2016-01-01 00:00:00", "2017-01-01 00:00:00");
        System.out.println(productDAO.getProductTotal1("德国", "2016-01-01 00:00:00", "2017-01-01 00:00:00"));
    }
//    public void testAddAndRemoveproduct() throws Exception
//    {
//        Product product = new Product();
//        product.setName("MZC");
//        product.setBrand("Enfamil");
//        product.setPrice("167.00yuan");
//        product.setFreight("60");
//        product.setCustomsDuty("5");
//        product.setPurchaseTime(new Date());
//       
//        product.setLocation("http://www.ymatou.com/");
//        product.setPicture("picture1");
//        product.setProducingArea("USA");
//        product.setSuitableAge("16years old");
//        product.setStandard("D");
//        product.setExpirationDate("2018-6-29");
//        product.setRemark("good");
//        product.setCreatePerson("LiFeiFei");
//        product.setCreateTime(new Date());
//        productDAO.saveProduct(product);
//        setComplete();
//        endTransaction();
//
//        startNewTransaction();
//        product = productDAO.getProduct(1l);
//        assertNotNull(product.getName());
//        assertNotNull(product.getBrand());
//        assertNotNull(product.getPrice());
//        assertNotNull(product.getFreight());
//        assertNotNull(product.getCustomsDuty());
//        assertNotNull(product.getPurchaseTime());
//        assertNotNull(product.getProductType());
//       //assertNotNull(product.getProductTypeName());
//        assertNotNull(product.getLocation());
//        assertNotNull(product.getPicture());
//        assertNotNull(product.getProducingArea());
//        assertNotNull(product.getSuitableAge());
//        assertNotNull(product.getStandard());
//        assertNotNull(product.getExpirationDate());
//        assertNotNull(product.getRemark());
//        assertNotNull(product.getCreatePerson());
//        assertNotNull(product.getCreateTime());
//
//        productDAO.removeProduct(product);
//        setComplete();
//        endTransaction();
//
//        product = productDAO.getProduct(1l);
//        assertNull(product);
//    }
}

