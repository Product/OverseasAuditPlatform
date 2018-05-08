package com.tonik;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tonik.model.SpringQTZ;
import com.tonik.service.MismatchProductService;
import com.tonik.service.ProductService;
import com.tonik.service.SpringQTZService;
import com.tonik.util.DateUtil;

/**
 * @desc 定时器类，定时执行product 分析
 * @spring.bean id="ProductQuartzTask" 
 * @spring.property name="productService" ref="ProductService"
 * @spring.property name="mismatchProductService" ref="MismatchProductService"
 * @spring.property name="springQTZService" ref="SpringQTZService"
 */
public class ProductQuartzTask
{
    private ProductService productService;
    private SpringQTZService springQTZService;
    private MismatchProductService mismatchProductService;
    
    //1: 任务正在执行   0：任务ready
    private final int TASK_STATUS_READY=0;
    private final int TASK_STATUS_RUNNING=1;
    
    //0: product 任务      1：rule 执行
    private final Long TASK_ID_SYNC_PRODUCT=1L;
    private final Long TASK_ID_SCAN_RULE=2L;
    
    private Lock lock = new ReentrantLock();
    
    public MismatchProductService getMismatchProductService()
    {
        return mismatchProductService;
    }


    public void setMismatchProductService(MismatchProductService mismatchProductService)
    {
        this.mismatchProductService = mismatchProductService;
    }


    public ProductService getProductService()
    {
        return productService;
    }


    public void setProductService(ProductService productService)
    {
        this.productService = productService;
    }

    
    public SpringQTZService getSpringQTZService()
    {
        return springQTZService;
    }


    public void setSpringQTZService(SpringQTZService springQTZService)
    {
        this.springQTZService = springQTZService;
    }


    public void executeProduct(){
        
        System.out.println("spring timer worked product start!");
        lock.lock();
        long startTime = System.currentTimeMillis();
        
        List<SpringQTZ> listqtz = springQTZService.getSpringQZTById(TASK_ID_SYNC_PRODUCT);
        
        if ( listqtz != null && listqtz.size() > 0) {
            SpringQTZ qtz=listqtz.get(0);
            if (qtz != null  ) {
                if ( qtz.getStatus() == TASK_STATUS_READY ){
                    System.out.println("do product!");
                    //productService.setProductDefinition("2016-5-13 11:00:00");
                    springQTZService.updateTaskStatus(TASK_ID_SYNC_PRODUCT, TASK_STATUS_RUNNING, DateUtil.formatDate(qtz.getRuntime()));
        
                    productService.execSyncProductsTask(DateUtil.formatDate(qtz.getRuntime()));
                    
                    springQTZService.updateTaskStatusAndTime(TASK_ID_SYNC_PRODUCT, TASK_STATUS_READY, DateUtil.formatDate(new Date()));
                }
            }
        }
        lock.unlock();
        
        long endTime = System.currentTimeMillis();
        System.out.println("spring timer worked product end spend times="+(endTime-startTime)/1000+"s");
    }
    
    public void executeRule() throws Exception{
        //TODO null 判断
        Date stopDate; //执行rule最后一个商品的createTime
        System.out.println("spring timer worked rule start!");
        lock.lock();        
        List<SpringQTZ> listProduct = springQTZService.getSpringQZTById(TASK_ID_SYNC_PRODUCT);
        List<SpringQTZ> listRule = springQTZService.getSpringQZTById(TASK_ID_SCAN_RULE);
        
        if (listProduct != null && listRule != null && listProduct.size()>0 && listRule.size() > 0 ) {
            SpringQTZ productQtz=listProduct.get(0);
            SpringQTZ ruleQtz=listRule.get(0);
            if (productQtz != null && ruleQtz != null ) {
//                if ( ruleQtz.getStatus() == TASK_STATUS_READY && ruleQtz.getRuntime().getTime() < productQtz.getRuntime().getTime() ){
                if ( ruleQtz.getStatus() == TASK_STATUS_READY ){

                    System.out.println("do rule!");
                    
                    springQTZService.updateTaskStatus(TASK_ID_SCAN_RULE, TASK_STATUS_RUNNING, DateUtil.formatDate(ruleQtz.getRuntime()));
                    
                    stopDate= mismatchProductService.updateMismatchProducts(ruleQtz.getRuntime());
                                        
                    springQTZService.updateTaskStatusAndTime(TASK_ID_SCAN_RULE, TASK_STATUS_READY, DateUtil.formatDate(stopDate));
                }
            }
        }
        lock.unlock();
        System.out.println("spring timer worked rule end");
    }    
    

    
}
