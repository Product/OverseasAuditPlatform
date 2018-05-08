package com.tonik.event;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.tonik.model.SpringQTZ;
import com.tonik.service.MismatchProductService;
import com.tonik.service.ProductService;
import com.tonik.service.SpringQTZService;
import com.tonik.util.DateUtil;

/**
 * @desc 定时器类，定时执行product 分析
 * @spring.bean id="ProductEventListener"
 * @spring.property name="productService" ref="ProductService"
 * @spring.property name="mismatchProductService" ref="MismatchProductService"
 * @spring.property name="springQTZService" ref="SpringQTZService"
 */
public class ProductEventListener implements ApplicationListener
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


    public MismatchProductService getMismatchProductService()
    {
        return mismatchProductService;
    }


    public void setMismatchProductService(MismatchProductService mismatchProductService)
    {
        this.mismatchProductService = mismatchProductService;
    }


    public void  onApplicationEvent(ApplicationEvent event) {
        Date stopDate = null; //执行rule最后一个商品的createTime
        Date updateDate=new Date();
        
        if(event instanceof  ProductEvent) {
            ProductEvent msgEvt =  (ProductEvent)event;
             
//            lock.lock();
            
//            long startTime = System.currentTimeMillis();
//             
//            List<SpringQTZ> listqtz = springQTZService.getSpringQZTById(TASK_ID_SYNC_PRODUCT);
//             
//            if ( listqtz != null && listqtz.size() > 0) {
//                SpringQTZ qtz=listqtz.get(0);
//                if (qtz != null  ) {
//                    //if ( qtz.getStatus() == TASK_STATUS_READY ){
//                        System.out.println("do product!");
//                        //productService.setProductDefinition("2016-5-13 11:00:00");
//                        //springQTZService.updateTaskStatus(TASK_ID_SYNC_PRODUCT, TASK_STATUS_RUNNING, DateUtil.formatDate(qtz.getRuntime()));
//             
//                        productService.execSyncProductsTask(DateUtil.formatDate(qtz.getRuntime()));
//                        
//                        updateDate = new Date();
//                        springQTZService.updateTaskStatusAndTime(TASK_ID_SYNC_PRODUCT, TASK_STATUS_READY, DateUtil.formatDate(updateDate));
//                    //}
//                }
//            }
             
            //List<SpringQTZ> listProduct = springQTZService.getSpringQZTById(TASK_ID_SYNC_PRODUCT);
            List<SpringQTZ> listRule = springQTZService.getSpringQZTById(TASK_ID_SCAN_RULE);
             
            if ( listRule != null  && listRule.size() > 0 ) {
                SpringQTZ ruleQtz=listRule.get(0);
                if ( ruleQtz != null ) {
                    if ( /*ruleQtz.getStatus() == TASK_STATUS_READY &&*/ ruleQtz.getRuntime().getTime() < updateDate.getTime() ){
                        System.out.println("do rule!"+this);
                         
                        //springQTZService.updateTaskStatus(TASK_ID_SCAN_RULE, TASK_STATUS_RUNNING, DateUtil.formatDate(ruleQtz.getRuntime()));
                         
                        try
                        {
                            stopDate= mismatchProductService.updateMismatchProducts(ruleQtz.getRuntime());
                        } catch (Exception e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                         
                        springQTZService.updateTaskStatusAndTime(TASK_ID_SCAN_RULE, TASK_STATUS_READY, DateUtil.formatDate(stopDate));
                     }
                 }
             }
             
  //           lock.unlock();
             
         }
    }    

}
