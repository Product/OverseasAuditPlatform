package com.tonik.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description:检测评价模块 model层
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @hibernate.class table="DETECTINGEVALUATION"
 */
    public class DetectingEvaluation implements Serializable//检测评价
    {
        /**
     * 
     */
    private static final long serialVersionUID = -7373723547451794877L;

        private Long id;
        
        private Website website;//网站对象
        
        private int productNum;//抽查的商品数
        
        private int complaints;//投诉引起的抽查数
        
        private int overallRating;//总体合格率
        
        private String remark;//备注
        
        private Date detectingEndTime;//检测结束时间
        
        private Long createPersonId;//创建人ID
        
        private String createPersonName;//创建人名称
        
        private Date createTime;//创建时间
        
        /**
         * @param id The id to set.
         */
        public void setId(Long id)
        {
            this.id = id;
        }

        /**
         * @hibernate.id column="DETECTINGEVALUATION_ID" type="long" unsaved-value="null" generator-class="identity"
         * @return Returns the id.
         */
        public Long getId()
        {
            return id;
        }
        
        /**
         * @hibernate.many-to-one column="DETECTINGEVALUATION_WEBSITE" not-null="false" lazy="false" class="com.tonik.model.Website"
         * @return Returns the website.
         */
        public Website getWebsite()
        {
            return website;
        }
        
        /**
         * @param website The website to set.
         */
        public void setWebsite(Website website)
        {
            this.website = website;
        }
        
        /**
         * @hibernate.property column="DETECTINGEVALUATION_PRODUCTNUM" type="int" not-null="false"
         * @return Returns the productNum.
         */
        public int getProductNum()
        {
            return productNum;
        }
        
        /**
         * 
         * @param productNum The productNum to set.
         */
        public void setProductNum(int productNum)
        {
            this.productNum = productNum;
        }
        
        /**
         * @hibernate.property column="DETECTINGEVALUATION_COMPLAINTS" type="int" not-null="false"
         * @return Returns the complaints.
         */
        public int getComplaints()
        {
            return complaints;
        }
        
        /**
         * 
         * @param complaints The complaints to set.
         */
        public void setComplaints(int complaints)
        {
            this.complaints = complaints;
        }

        /**
         * @hibernate.property column="DETECTINGEVALUATION_OVERALLRATING" type="int" not-null="false"
         * @return Returns the overallRating.
         */
        public int getOverallRating()
        {
            return overallRating;
        }

        /**
         * 
         * @param OverallRating The OverallRating to set.
         */
        public void setOverallRating(int overallRating)
        {
            this.overallRating = overallRating;
        }
        
        /**
         * @hibernate.property column="DETECTINGEVALUATION_REMARK" type="string" length="2000" not-null="false"
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
         * @hibernate.property column="DETECTINGEVALUATION_CREATEPERSONID" type="long" unsaved-value="null"
         * @return Returns the createPersonId.
         */
        public Long getCreatePersonId()
        {
            return createPersonId;
        }

        /**
         * @param createPersonId The createPersonId to set.
         */
        public void setCreatePersonId(Long createPersonId)
        {
            this.createPersonId = createPersonId;
        }

        /**
         * @hibernate.property column="DETECTINGEVALUATION_CREATEPERSONNAME" type="string" length="50" not-null="false"
         * @return Returns the createPersonName.
         */
        public String getCreatePersonName()
        {
            return createPersonName;
        }

        /**
         * @param createPersonName The createPersonName to set.
         */
        public void setCreatePersonName(String createPersonName)
        {
            this.createPersonName = createPersonName;
        }

        /**
         * @param createTime The createTime to set.
         */
        public void setCreateTime(Date createTime)
        {
            this.createTime = createTime;
        }
        
        /**
         * @hibernate.property column="DETECTINGEVALUATION_CREATETIME" sql-type="Date" not-null="false"
         * @return Returns the CreateTime.
         */
        public Date getCreateTime()
        {
            return createTime;
        }

        /**
         * @hibernate.property column="DETECTINGEVALUATION_ENDTIME" sql-type="Date" not-null="false"
         * @return Returns the detectingEndTime.
         */
        public Date getDetectingEndTime()
        {
            return detectingEndTime;
        }
        
        /**
         * 
         * @param detectingEndTime The detectingEndTime to set.
         */
        public void setDetectingEndTime(Date detectingEndTime)
        {
            this.detectingEndTime = detectingEndTime;
        }

}
