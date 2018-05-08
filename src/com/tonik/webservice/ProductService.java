package com.tonik.webservice;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import com.thinvent.utils.GsonUtil;
import com.thinvent.utils.SpringContextUtil;
import com.tonik.service.WebsiteService.WebsitePagingBean;

@WebService(endpointInterface = "com.tonik.webservice.IProductService")
public class ProductService implements IProductService
{
    @Override
    public String listProducts()
    {
        com.tonik.service.ProductService productService = SpringContextUtil.getBean("ProductService");
        String result = productService.getRandTopProducts();
        return result;
    }
}