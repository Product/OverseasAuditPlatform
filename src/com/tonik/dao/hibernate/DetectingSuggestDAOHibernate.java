package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IDetectingSuggestDAO;
import com.tonik.model.DetectingSuggest;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IDetectingRecordDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="DetectingSuggestDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class DetectingSuggestDAOHibernate extends BaseDaoHibernate implements IDetectingSuggestDAO
{

    @Override
    public List<DetectingSuggest> getDetectingSuggest()
    {
        return getHibernateTemplate().find("from DetectingSuggest");
    }

    @Override
    public DetectingSuggest getDetectingSuggest(Long detectingSuggestId)
    {
        return (DetectingSuggest) getHibernateTemplate().get(DetectingSuggest.class, detectingSuggestId);
    }

    @Override
    public void saveDetectingSuggest(DetectingSuggest detectingSuggest)
    {
        getHibernateTemplate().saveOrUpdate(detectingSuggest);
    }

    @Override
    public void removeDetectingSuggest(DetectingSuggest detectingSuggest)
    {
        getHibernateTemplate().delete(detectingSuggest);
    }

    @Override
    public List<Object[]> getDetectingSuggestPaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        final String hql = "from DetectingSuggest where respondents=:strQuery and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
        List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setParameter("strQuery", strQuery);
                query.setParameter("strStraTime", strStraTime);
                query.setParameter("strEndTime", strEndTime);
                query.setFirstResult((pageIndex - 1) * pageSize);
                query.setMaxResults(pageSize);
                List list = query.list();
                return list;
            }
        });
        return listTable;
    }

    @Override
    public int getDetectingSuggestTotal(final String strQuery, final String strStraTime, final String strEndTime)
    {
        return getHibernateTemplate().find("from DetectingSuggest where respondents=" + strQuery + " and createTime>="
                + strStraTime + " and createTime<=" + strEndTime + " order by createTime desc").size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getDetectingSuggest(final String strSql, final int reRowCount)
    {
        Session session = getSession();
        List ls = session.createSQLQuery("select top(" + reRowCount
                + ")b.PRODUCT_REMARK, g.WEBSITE_NAME,b.PRODUCT_LOCATION,"
                + "a.SOURCETYPE,a.SOURCECONTENT,b.PRODUCT_BRAND,f.COUNTRY_NAME from DETECTINGSUGGEST a "
                + "left join PRODUCT b on a.PRODUCT=b.PRODUCT_ID "
                + "left join PRODUCTTYPE c on b.FIRST_TYPE=c.PRODUCTTYPE_ID "
                + "left join PRODUCTTYPE d on b.SECOND_TYPE=d.PRODUCTTYPE_ID "
                + "left join PRODUCTTYPE e on b.THIRD_TYPE=e.PRODUCTTYPE_ID left join COUNTRY f on b.COUNTRY=f.COUNTRY_ID "
                + "left join WEBSITE g on b.WEBSITE=g.WEBSITE_ID "
                + "left join EVENT i on a.EVENT=i.EVENT_ID where 1=1" + strSql + " order by a.CREATETIME desc")
                .list();
        releaseSession(session);
        return ls;
    }

    @Override
    public String getDashboardJson(final String strSql, final int reRowCount, final String strKeyWord)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("if exists(select * from tempdb..sysobjects where id=object_id('tempdb..#DETECTINGSUGGEST'))");
        builder.append(" begin ");
        builder.append(" drop table #DETECTINGSUGGEST");
        builder.append(" end ");
        builder.append("create table #DETECTINGSUGGEST");
        builder.append("(");
        builder.append("    country varchar(50),");
        builder.append("    brand varchar(50),");
        builder.append("    brandname varchar(50),");
        builder.append("    num int");
        builder.append(") ");
        builder.append("insert into #DETECTINGSUGGEST(country,brand,brandname,num) ");
        builder.append("select f.COUNTRY_NAME,b.PRODUCT_BRAND,b.PRODUCT_BRAND, COUNT(*) as Num from (select top(" + reRowCount
                + ")a1.* from DETECTINGSUGGEST a1 left join PRODUCT b1 on a1.PRODUCT=b1.PRODUCT_ID where b1.PRODEFINITION_ID >0 order by a1.CREATETIME desc) a ");
        builder.append("left join PRODUCT b on a.PRODUCT=b.PRODUCT_ID ");
        builder.append("left join PRODUCTTYPE c on b.FIRST_TYPE=c.PRODUCTTYPE_ID ");
        builder.append("left join PRODUCTTYPE d on b.SECOND_TYPE=d.PRODUCTTYPE_ID ");
        builder.append("left join PRODUCTTYPE e on b.THIRD_TYPE=e.PRODUCTTYPE_ID ");
        builder.append("left join COUNTRY f on b.COUNTRY=f.COUNTRY_ID ");
        builder.append("left join WEBSITE g on b.WEBSITE=g.WEBSITE_ID ");
        if(!"".equals(strKeyWord.replaceAll(" ", ""))){
            builder.append("left join EVENT i on a.EVENT=i.EVENT_ID ");
        }
        builder.append("where 1=1 and b.COUNTRY is not null and b.PRODUCT_BRAND is not null " + strSql);
        builder.append(
                " group by b.COUNTRY,f.COUNTRY_NAME,b.PRODUCT_BRAND order by Num desc");

        Session session = getSession();
        List ls = session.createSQLQuery(
                builder.toString() + " select country,SUM(isnull(num,0)) num from #DETECTINGSUGGEST group by country")
                .list();
        String str = "";
        for (Iterator iterator = ls.iterator(); iterator.hasNext();)
        {
            Object[] obj = (Object[]) iterator.next();
            if(obj[0] == null)
                continue;

            str += "{\"name\":\"" + obj[0] + "\",\"value\":\"" + obj[1] + "\"},";// 国家与对应的推荐商品数量
        }

        List ls2 = session
                .createSQLQuery(builder.toString()
                        + " select country,brandname,num from #DETECTINGSUGGEST order by country asc,brand asc,num desc")
                .list(); 
        String str2 = "";
        String temp = "";
        String oldCountry = "";
        int i = 0;
        for (Iterator iterator = ls2.iterator(); iterator.hasNext();)
        {
            
            Object[] obj = (Object[]) iterator.next();
            if(obj[0] == null)
                continue;
            if (i >= 3 && oldCountry == obj[0].toString().trim())
            {
                continue;
            }
            else if (oldCountry != obj[0].toString().trim())
            {
                if (!temp.isEmpty())//新行
                {
                    temp = temp.substring(0, temp.length() - 1);
                    str2 += "{\"name\":\"" + oldCountry + "\",\"title\":\"" + temp + "\"},";
                    temp = "";
                }
                if (!iterator.hasNext())//最后一条数据处理
                {
                    oldCountry = obj[0].toString().trim();
                    temp += obj[1].toString().trim() + ":" + obj[2].toString().trim() + ",";
                    temp = temp.substring(0, temp.length() - 1);
                    str2 += "{\"name\":\"" + oldCountry + "\",\"title\":\"" + temp + "\"},";
                    break;
                }
                i = 0;
                oldCountry = obj[0].toString().trim();
                temp += obj[1].toString().trim() + ":" + obj[2].toString().trim() + ",";// 同一国家相同品牌出现的前三名
            }
            if (oldCountry.isEmpty())
            {
                oldCountry = obj[0].toString().trim();//第一条数据
            }
            i++;
        }
        if (str != "")
        {
            str = str.substring(0, str.length() - 1);
        }
        if (str2 != "")
        {
            str2 = str2.substring(0, str2.length() - 1);
        }
        releaseSession(session);
        return ("{\"strTitle\":[" + str2 + "],\"strCountryNum\":[" + str + "]}");
    }
    
    public void delFromDetectingSuggest(Long eventId)
    {
        Query query = getSession().createSQLQuery("delete DETECTINGSUGGEST where EVENT = "+eventId);
        query.executeUpdate();
    }

    @Override
    public List getEvaluationRecord(String strSql, int reRowCount)
    {
        Session session = getSession();
        List ls = session.createSQLQuery("select top(" + reRowCount
                + ")b.PRODUCT_REMARK, g.WEBSITE_NAME,b.PRODUCT_LOCATION,"
                + "b.PRODUCT_BRAND,f.COUNTRY_NAME from PRODUCT b "
                + "left join PRODUCTTYPE c on b.FIRST_TYPE=c.PRODUCTTYPE_ID "
                + "left join PRODUCTTYPE d on b.SECOND_TYPE=d.PRODUCTTYPE_ID "
                + "left join PRODUCTTYPE e on b.THIRD_TYPE=e.PRODUCTTYPE_ID "
                + "left join COUNTRY f on b.COUNTRY=f.COUNTRY_ID "
                + "left join WEBSITE g on b.WEBSITE=g.WEBSITE_ID "
                + "where 1=1 " + strSql 
                + " order by b.CREATETIME desc")
                .list();
        releaseSession(session);
        return ls;
    }

    @Override
    public String getEvaluationSuggest(String strSql, int reRowCount)
    {
        Session session = getSession();
        List ls = session.createSQLQuery("select COUNTRY_NAME,COUNT(*) num from PRODUCT b "
                + "left join PRODUCTTYPE c on b.FIRST_TYPE=c.PRODUCTTYPE_ID "
                + "left join PRODUCTTYPE d on b.SECOND_TYPE=d.PRODUCTTYPE_ID "
                + "left join PRODUCTTYPE e on b.THIRD_TYPE=e.PRODUCTTYPE_ID "
                + "left join COUNTRY f on b.COUNTRY=f.COUNTRY_ID "
                +"where 1=1 "+strSql
                + " group by b.COUNTRY,f.COUNTRY_NAME,b.PRODUCT_BRAND order by Num desc")
                .list();
        String str = "";
        for (Iterator iterator = ls.iterator(); iterator.hasNext();)
        {
            Object[] obj = (Object[]) iterator.next();
            if(obj[0] == null)
                continue;

            str += "{\"name\":\"" + obj[0] + "\",\"value\":\"" + obj[1] + "\"},";// 国家与对应的推荐商品数量
        }

        List ls2 = session
                .createSQLQuery("select COUNTRY_NAME,PRODUCT_BRAND,COUNT(*) num from PRODUCT b "
                        + "left join PRODUCTTYPE c on b.FIRST_TYPE=c.PRODUCTTYPE_ID "
                        + "left join PRODUCTTYPE d on b.SECOND_TYPE=d.PRODUCTTYPE_ID "
                        + "left join PRODUCTTYPE e on b.THIRD_TYPE=e.PRODUCTTYPE_ID "
                        + "left join COUNTRY f on b.COUNTRY=f.COUNTRY_ID "
                        +"where 1=1 " + strSql
                        + " group by b.COUNTRY,f.COUNTRY_NAME,b.PRODUCT_BRAND order by Num desc")
                .list();
        String str2 = "";
        String temp = "";
        String oldCountry = "";
        int i = 0;
        for (Iterator iterator = ls2.iterator(); iterator.hasNext();)
        {
            
            Object[] obj = (Object[]) iterator.next();
            if(obj[0] == null)
                continue;
            if (i >= 3 && oldCountry == obj[0].toString().trim())
            {
                continue;
            }
            else if (oldCountry != obj[0].toString().trim())
            {
                if (!temp.isEmpty())//新行
                {
                    temp = temp.substring(0, temp.length() - 1);
                    str2 += "{\"name\":\"" + oldCountry + "\",\"title\":\"" + temp + "\"},";
                    temp = "";
                }
                if (!iterator.hasNext())//最后一条数据处理
                {
                    oldCountry = obj[0].toString().trim();
                    temp += obj[1].toString().trim() + ":" + obj[2].toString().trim() + ",";
                    temp = temp.substring(0, temp.length() - 1);
                    str2 += "{\"name\":\"" + oldCountry + "\",\"title\":\"" + temp + "\"},";
                    break;
                }
                i = 0;
                oldCountry = obj[0].toString().trim();
                temp += obj[1].toString().trim() + ":" + obj[2].toString().trim() + ",";// 同一国家相同品牌出现的前三名
            }
            if (oldCountry.isEmpty())
            {
                oldCountry = obj[0].toString().trim();//第一条数据
            }
            i++;
        }
        if (str != "")
        {
            str = str.substring(0, str.length() - 1);
        }
        if (str2 != "")
        {
            str2 = str2.substring(0, str2.length() - 1);
        }
        releaseSession(session);
        return ("{\"strTitle\":[" + str2 + "],\"strCountryNum\":[" + str + "]}");
    }
}
