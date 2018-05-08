package com.tonik.service;

import java.util.List;

import com.tonik.dao.hibernate.BaseDaoTestCase;
import com.tonik.model.Product;

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
 * spring.bean id="ProductServiceTest"
 * spring.property name="consultiveService" ref="ProductService"
 */
public class ProductServiceTest extends BaseDaoTestCase
{
    private ProductService ProductService;

    

    public ProductService getProductService()
    {
        return ProductService;
    }

    public void setProductService(ProductService ProductService)
    {
        this.ProductService = ProductService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ProductServiceTest.class);
    }

  public void testGetProduct() throws Exception
  {
      List<Object[]> ls = ProductService.ProductPaging1(1, 100, "德国", "2016-01-01 00:00:00", "2017-01-01 00:00:00");
      String strJson = "";

      for (Object[] item : ls)
      {
          strJson +=  "{\"Id\":\"" + item[0] + "\",\"Name\":\"" + item[1] + "\",\"Price\":\"" + item[2]
                  + "\",\"Location\":\"" + item[3] + "\",\"WebsiteName\":\"" + item[4]
                  + "\",\"Remark\":\"" + item[5] + "\",\"CreatePerson\":\"" + item[6] + "\",\"CreateTime\":\""
                  + item[7] + "\"},";
      }
      System.out.println(strJson);
  }

    
//    public void testAddAndRemoveState() throws Exception
//    {
//        assertNotNull(ProductService);
//    }

}
