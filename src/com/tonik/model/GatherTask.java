package com.tonik.model;

import java.util.Date;
import java.util.Set;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @hibernate.class table="GATHERTASK"
 */
public class GatherTask
{
    //TODO: fixme， hardcoding solution。
    


    private Long id; // 子任务id
    private Long gid; // 所属的采集任务id
    private ProductStyle ptype; // 子任务中商品类型
    
    private ProductType firstType; //采集所属一级类别
    private ProductType secondType; //采集所属二级类别
    private ProductType thirdType;//采集所属三级类别

    private String url; // 访问地址
    private String requestType;
    private String pagingType; // 分页方式
    private String pagingParams; // 分页参数
    private String pagingStep;//分页step
    private String nextPageType;//下一页定位方式
    private String nextPagePath;//下一页定位路径
    private String nextPageStr;//下一页
    private String lastPageType;
    private String lastPageStr;
    private String lastPageUrl;
    private Set<GatherTaskItemPath> itemPath;
    private Set<GatherTaskParams> params;
    private String name; // 任务名称
    private Long productnum; // 子任务中的商品数量
    private int state; // 任务状态
    //0：空闲
    //1：正在执行
    //2：异常
    private String productPath;
    private String pathType;
    private String remark; // 备注
    private String createPerson; // 创建人
    private Date createTime; // 创建日期


    /**
     * @param id The id to set.
     */
    public void setId(Long id)

    {
        this.id = id;
    }

    /**
     * @hibernate.id column="TASK_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
   
    public Long getId()
    {
        return id;
    }
   
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @hibernate.property column="TASK_NAME" type="string" length="500" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @hibernate.property column="GATHER_ID" type="long" not-null="false"
     * @return Returns the gid.
     */
    public Long getGid()
    {
        return gid;
    }

    public void setGid(Long gid)
    {
        this.gid = gid;
    }
    /**
     * @hibernate.many-to-one column="FIRST_TYPE" not-null="false" lazy="false" class="com.tonik.model.ProductType"
     * @return Returns the firstType.
     */
    public ProductType getFirstType()
    {
        return firstType;
    }
    /**
     * @param firstType The firstType to set.
     */
    public void setFirstType(ProductType firstType)
    {
        this.firstType = firstType;
    }
    /**
     * @hibernate.many-to-one column="SECOND_TYPE" not-null="false" lazy="false" class="com.tonik.model.ProductType"
     * @return Returns the secondType.
     */
    public ProductType getSecondType()
    {
        return secondType;
    }
    /**
     * @param secondType The secondType to set.
     */
    public void setSecondType(ProductType secondType)
    {
        this.secondType = secondType;
    }
    /**
     * @hibernate.many-to-one column="THIRD_TYPE" not-null="false" lazy="false" class="com.tonik.model.ProductType"
     * @return Returns the thirdType.
     */
    public ProductType getThirdType()
    {
        return thirdType;
    }
    /**
     * @param thirdType The thirdType to set.
     */
    public void setThirdType(ProductType thirdType)
    {
        this.thirdType = thirdType;
    }

    /**
     * @hibernate.property column="REQUEST_TYPE" type="string" length="500" not-null = "false"
     * @return Returns the requestType.
     */

    public String getRequestType()
    {
        return requestType;
    }
    /**
     * @param requestType The requestType to set.
     */
    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }
    /**
     * @hibernate.property column="PAGING_TYPE" type="string" length="500" not-null = "false"
     * @return Returns the pagingType.
     */
    public String getPagingType()
    {
        return pagingType;
    }
    /**
     * @param pagingType The pagingType to set.
     */
    public void setPagingType(String pagingType)
    {
        this.pagingType = pagingType;
    }
    /**
     * @hibernate.property column="PAGING_PARAMS" type="string" length="500" not-null = "false"
     * @return Returns the pagingParams.
     */
    public String getPagingParams()
    {
        return pagingParams;
    }
    /**
     * @param pagingParams The pagingParams to set.
     */

    public void setPagingParams(String pagingParams)
    {
        this.pagingParams = pagingParams;
    }
    /**
     * @hibernate.property column="PAGING_STEP" type="string" length="500" not-null = "false"
     * @return Returns the pagingStep.
     */
    public String getPagingStep()
    {
        return pagingStep;
    }
    /**
     * @param pagingStep The pagingStep to set.
     */
    public void setPagingStep(String pagingStep)
    {
        this.pagingStep = pagingStep;
    }
    /**
     * @hibernate.property column="NEXTPAGE_TYPE" type="string" length="500" not-null = "false"
     * @return Returns the nextPageType.
     */
    public String getNextPageType()
    {
        return nextPageType;
    }
    /**
     * @param nextPageType The nextPageType to set.
     */
    public void setNextPageType(String nextPageType)
    {
        this.nextPageType = nextPageType;
    }
    /**
     * @hibernate.property column="NEXTPAGE_PATH" type="string" length="500" not-null = "false"
     * @return Returns the nextPagePath.
     */
    public String getNextPagePath()
    {
        return nextPagePath;
    }
    /**
     * @param nextPagePath The nextPagePath to set.
     */
    public void setNextPagePath(String nextPagePath)
    {
        this.nextPagePath = nextPagePath;
    }
    /**
     * @hibernate.property column="NEXTPAGE_STR" type="string" length="500" not-null = "false"
     * @return Returns the nextPageStr.
     */
    public String getNextPageStr()
    {
        return nextPageStr;
    }
    /**
     * @param nextPageStr The nextPageStr to set.
     */
    public void setNextPageStr(String nextPageStr)
    {
        this.nextPageStr = nextPageStr;
    }
    /**
     * @hibernate.property column="LASTPAGE_TYPE" type="string" length="500" not-null = "false"
     * @return Returns the lastPageType.
     */
    public String getLastPageType()
    {
        return lastPageType;
    }
    /**
     * @param lastPageType The lastPageType to set.
     */
    public void setLastPageType(String lastPageType)
    {
        this.lastPageType = lastPageType;
    }
    /**
     * @hibernate.property column="LASTPAGE_STR" type="string" length="500" not-null = "false"
     * @return Returns the lastPageStr.
     */
    public String getLastPageStr()
    {
        return lastPageStr;
    }
    /**
     * @param lastPageStr The lastPageStr to set.
     */
    public void setLastPageStr(String lastPageStr)
    {
        this.lastPageStr = lastPageStr;
    }
    /**
     * @hibernate.property column="URL" type="string" length="500" not-null = "false"
     * @return Returns the url.
     */
    public String getUrl()
    {
        return url;
    }
    /**
     * @param url The url to set.
     */
    public void setUrl(String url)
    {
        this.url = url;
    }
   
    public Set<GatherTaskItemPath> getItemPath()
    {
        return itemPath;
    }

    public void setItemPath(Set<GatherTaskItemPath> itemPath)
    {
        this.itemPath = itemPath;
    }

    public Set<GatherTaskParams> getParams()
    { 
        return params;
    }

    public void setParams(Set<GatherTaskParams> params)
    {
        this.params = params;
    }

    /**
     * @hibernate.property column="LASTPAGEURL" type="string" length="500" not-null = "false"
     * @return Returns the lastPageUrl.
     */
    public String getLastPageUrl()
    {
        return lastPageUrl;
    }

    public void setLastPageUrl(String lastPageUrl)
    {
        this.lastPageUrl = lastPageUrl;
    }

    /**
     * @hibernate.many-to-one column="TASK_PTYPE" not-null="false" lazy="false" class="com.tonik.model.ProductStyle"
     * @return Returns the ptype.
     */
    public ProductStyle getPtype()
    {
        return ptype;
    }
    public String getPtypeName()
    {
    return ptype.getName();
    }

    /**
     * @param ptype The ptype to set.
     */
    public void setPtype(ProductStyle ptype)
    {
        this.ptype = ptype;
    }


    /**
     * @hibernate.property column="TPRODUCT_NUM" type="long" not-null="false"
     * @return Returns the productnum.
     */
    public Long getProductnum()
    {
        return productnum;
    }

    /**
     * @param n The productnum to set.
     */
    public void setProductnum(Long n)
    {
        this.productnum = n;
    }

    /**
     * @hibernate.property column="TASK_STATE" type="integer"  not-null="false"
     * @return Returns the state.
     */
    public int getState()
    {
        return state;
    }

    /**
     * @param state The state to set.
     */
    public void setState(int state)
    {
        this.state = state;
    }
    /**
     * @hibernate.property column="PRODUCT_PATH" type="string" length="500" not-null="false"
     * @return Returns the productPath.
     */
    public String getProductPath()
    {
        return productPath;
    }

    /**
     * @param productPath The productPath to set.
     */
    public void setProductPath(String productPath)
    {
        this.productPath = productPath;
    }
    /**
     * @hibernate.property column="PATH_TYPE" type="string" length="500" not-null="false"
     * @return Returns the pathType.
     */
    public String getPathType()
    {
        return pathType;
    }

    /**
     * @param pathType The pathType to set.
     */
    public void setPathType(String pathType)
    {
        this.pathType = pathType;
    }

    /**
     * @hibernate.property column="TASK_REMARK" type="string" length="5000" not-null="false"
     * @return Returns the remark.
     */
    public String getRemark()
    {
        return remark;
    }

    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    /**
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(String createPerson)
    {
        this.createPerson = createPerson;
    }

    /**
     * @hibernate.property column="CREATEPERSON" type="string" length="500" not-null="false"
     * @return Returns the createPerson.
     */
    public String getCreatePerson()
    {
        return createPerson;
    }

    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    /**
     * @hibernate.property column="CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    public String getStrState()
    {
        String strState = "";
        switch(state){
            case 0:
                strState = "空闲";break;
            case 1:
                strState = "执行中";break;
            case 2:
                strState = "异常";break;
        }
        return strState;
    }

}
