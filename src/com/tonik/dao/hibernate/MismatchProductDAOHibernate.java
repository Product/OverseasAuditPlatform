package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IMismatchProductDAO;
import com.tonik.model.MismatchProduct;
import com.tonik.model.Product;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IMismatchProductDAO.
 * </p>
 * @since Apr 19, 2016
 * @version 1.0
 * @author liuyu
 * @spring.bean id="MismatchProductDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class MismatchProductDAOHibernate extends BaseDaoHibernate implements IMismatchProductDAO
{

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 通过不符合标准的商品的Id获得不符合标准的商品
     * @param MismatchProductId 不符合标准的商品的Id
     * @return MismatchProduct 不符合标准的商品对象
     */
    public MismatchProduct getMismatchProduct(Long MismatchProductId)
    {
        return (MismatchProduct) getHibernateTemplate().get(MismatchProduct.class, MismatchProductId);
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 根据商品对象保存不符合标准的商品
     * @param MismatchProduct 商品对象
     * @return List<MismatchProduct> 商品的分页列表
     */
    public void saveMismatchProduct(MismatchProduct MismatchProduct)
    {
        getHibernateTemplate().saveOrUpdate(MismatchProduct);
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 根据商品对象删除不符合标准的商品
     * @param MismatchProduct 商品对象
     */
    public void removeMismatchProduct(MismatchProduct MismatchProduct)
    {
        getHibernateTemplate().delete(MismatchProduct);
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 获取不符合标准的商品分页列表
     * @param pageIndex 当前页数
     * @param pageSize 每页记录数
     * @param ptl 商品类别数组
     * @param standardId 标准id
     * @return List<MismatchProduct> 商品的分页列表
     */
    public List<Object[]> getMismatchProductPaging(int pageIndex, int pageSize, Long[] ptl, Long standardId,
            String orderBy, String orderType)
    {
        Session session = getSession();
        try
        {
            // 商品名称，网站名称，网站url,不匹配内容
            String sql = "select mp.ID,p.PRODUCT_NAME,p.WEBSITE_NAME,p.PRODUCT_LOCATION, mp.MISMATCH_CONTENT,mp.CREATE_TIME,s.NAME,mp.MISMATCH_NUM "
                    + "from MISMATCH_PRODUCT mp left join PRODUCT p on p.PRODUCT_ID = mp.PRODUCT_ID left join STANDARD s on s.ID = mp.STANDARD_ID "
                    + "where 1=1 ";
            if (standardId > 0)
            {
                sql += "and mp.STANDARD_ID = :standardId ";
            }
            if (ptl.length > 0)
            {
                sql += "and (p.FIRST_TYPE in (:ptl) or p.SECOND_TYPE in (:ptl) or p.THIRD_TYPE in (:ptl)) ";
            }
            if (orderBy.length() != 0)
            {
                sql += "ORDER BY mp." + orderBy + " ";
                if (orderType.equals("1"))
                {
                    sql += "ASC";
                }
                else if (orderType.equals("2"))
                {
                    sql += "DESC";
                }
            }

            Query query = session.createSQLQuery(sql);
            if (standardId > 0)
            {
                query.setParameter("standardId", standardId);
            }
            if (ptl.length > 0)
            {
                query.setParameterList("ptl", ptl);
            }
            query.setFirstResult((pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
            List<Object[]> result = query.list();
            return result;
        } catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 获取不符合标准的商品分页列表
     * @param pageIndex 当前页数
     * @param pageSize 每页记录数
     * @param ptl 商品类别数组
     * @param standardIdList 标准id数组
     * @return List<MismatchProduct> 商品的分页列表
     */
    public List<Object[]> getMismatchProductPaging(int pageIndex, int pageSize, Long[] ptl, Long[] standardIdList,
            String orderBy, String orderType)
    {
        Session session = getSession();
        try
        {
            // 商品名称，网站名称，网站url,不匹配内容
            String sql = "select mp.ID,p.PRODUCT_NAME,p.WEBSITE_NAME,p.PRODUCT_LOCATION, mp.MISMATCH_CONTENT,mp.CREATE_TIME,s.NAME, mp.MISMATCH_NUM "
                    + "from MISMATCH_PRODUCT mp left join PRODUCT p on p.PRODUCT_ID = mp.PRODUCT_ID left join STANDARD s on s.ID = mp.STANDARD_ID "
                    + "where 1=1 ";
            if (standardIdList.length > 0)
            {
                sql += "and mp.STANDARD_ID in (:standardId) ";
            }
            if (ptl.length > 0)
            {
                sql += "and (p.FIRST_TYPE in (:ptl) or p.SECOND_TYPE in (:ptl) or p.THIRD_TYPE in (:ptl)) ";
            }
            if (orderBy.length() != 0)
            {
                sql += "ORDER BY mp." + orderBy + " ";
                if (orderType.equals("1"))
                {
                    sql += "ASC";
                }
                else if (orderType.equals("2"))
                {
                    sql += "DESC";
                }
            }

            Query query = session.createSQLQuery(sql);

            if (standardIdList.length > 0)
            {
                query.setParameterList("standardId", standardIdList);
            }
            if (ptl.length > 0)
            {
                query.setParameterList("ptl", ptl);
            }
            query.setFirstResult((pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);

            List<Object[]> result = query.list();

            return result;
        } catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 获取不符合标准的商品总数
     * @param ptl 商品类别数组
     * @param standardId 标准id
     * @return int
     */
    public int getMismatchProductTotal(Long[] ptl, Long standardId)
    {
        Session session = getSession();
        try
        {
            String sql = "select mp.ID "
                    + "from MISMATCH_PRODUCT mp left join PRODUCT p on p.PRODUCT_ID = mp.PRODUCT_ID " + "where 1=1 ";
            if (standardId > 0)
            {
                sql += "and mp.STANDARD_ID = :standardId ";
            }
            if (ptl.length > 0)
            {
                sql += "and (p.FIRST_TYPE in (:ptl) or p.SECOND_TYPE in (:ptl) or p.THIRD_TYPE in (:ptl)) ";
            }

            Query query = session.createSQLQuery(sql);
            if (standardId > 0)
            {
                query.setParameter("standardId", standardId);
            }
            if (ptl.length > 0)
            {
                query.setParameterList("ptl", ptl);
            }

            List<Object[]> result = query.list();

            return result.size();
        } catch (Exception e)
        {
            return 0;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 获取不符合标准的商品总数
     * @param ptl 商品类别数组
     * @param standardIdList 标准id数组
     * @return int
     */
    public int getMismatchProductTotal(Long[] ptl, Long[] standardIdList)
    {
        Session session = getSession();
        try
        {
            String sql = "select mp.ID "
                    + "from MISMATCH_PRODUCT mp left join PRODUCT p on p.PRODUCT_ID = mp.PRODUCT_ID " + "where 1=1 ";
            if (standardIdList.length > 0)
            {
                sql += "and mp.STANDARD_ID in (:standardId) ";
            }
            if (ptl.length > 0)
            {
                sql += "and (p.FIRST_TYPE in (:ptl) or p.SECOND_TYPE in (:ptl) or p.THIRD_TYPE in (:ptl)) ";
            }

            Query query = session.createSQLQuery(sql);

            if (standardIdList.length > 0)
            {
                query.setParameterList("standardId", standardIdList);
            }
            if (ptl.length > 0)
            {
                query.setParameterList("ptl", ptl);
            }

            List<Object[]> result = query.list();
            return result.size();
        } catch (Exception e)
        {
            return 0;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 删除不符合标准的商品
     * @param id 不匹配的记录id
     */
    public void removeMismatchProductById(String id)
    {
        Session session = getSession();
        try
        {
            String sql = "DELETE FROM MISMATCH_PRODUCT WHERE MISMATCH_PRODUCT.ID = :id";
            Query query = session.createSQLQuery(sql);
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception e)
        {
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 删除不符合标准的商品
     * @param id 不匹配的记录id数组
     */
    public void removeMultiMismatchProductById(String[] idList)
    {
        Session session = getSession();
        try
        {
            String sql = "DELETE FROM MISMATCH_PRODUCT WHERE MISMATCH_PRODUCT.ID in (:ids)";
            Query query = session.createSQLQuery(sql);
            query.setParameterList("ids", idList);
            query.executeUpdate();
        } catch (Exception e)
        {
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @Override
     * @desc: 查找不匹配的配料信息
     * @param id 不匹配的记录的id
     * @return 不匹配的配料信息
     */
    public String getMismatchContentById(Long id)
    {
        String sql = "SELECT MISMATCH_PRODUCT.MISMATCH_CONTENT FROM MISMATCH_PRODUCT WHERE MISMATCH_PRODUCT.ID = :id";
        Session session = getSession();

        try
        {

            Query query = session.createSQLQuery(sql);
            query.setParameter("id", id);
            String result = query.list().get(0).toString();
            return result;
        } catch (Exception e)
        {
            return "";
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IMismatchProductDAO#getSimilarProductPaging(int, int, java.lang.Long)
     * @Override
     * @desc: 查找与某商品类似的不匹配记录的分页列表
     * @param pageIndex 页码值
     * @param pageSize 每页记录数
     * @param id 不匹配的记录的id
     * @return 不匹配记录的分页列表
     */
    public List<Object[]> getSimilarProductPaging(int pageIndex, int pageSize, Long id)
    {
        Session session = getSession();
        try
        {
            String sql = "select mp1.ID,p1.PRODUCT_NAME,p1.WEBSITE_NAME,p1.PRODUCT_LOCATION, mp1.MISMATCH_CONTENT "
                    + "from MISMATCH_PRODUCT mp1 left join PRODUCT p1 on p1.PRODUCT_ID = mp1.PRODUCT_ID, "
                    + "MISMATCH_PRODUCT mp2 left join PRODUCT p2 on p2.PRODUCT_ID = mp2.PRODUCT_ID "
                    + "where p1.PRODEFINITION_ID = p2.PRODEFINITION_ID "
                    + "and mp1.STANDARD_ID = mp2.STANDARD_ID and mp2.ID = :id";

            Query query = session.createSQLQuery(sql);

            query.setParameter("id", id);
            query.setFirstResult((pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);

            List<Object[]> result = query.list();

            return result;
        } catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IMismatchProductDAO#getSimilarProductTotal(java.lang.Long)
     * @Override
     * @desc: 查找与某商品类似的不匹配记录的总数
     * @param id 不匹配的记录的id
     * @return 不匹配记录的总数
     */
    public int getSimilarProductTotal(Long id)
    {
        Session session = getSession();
        try
        {

            String sql = "select mp1.ID "
                    + "from MISMATCH_PRODUCT mp1 left join PRODUCT p1 on p1.PRODUCT_ID = mp1.PRODUCT_ID, "
                    + "MISMATCH_PRODUCT mp2 left join PRODUCT p2 on p2.PRODUCT_ID = mp2.PRODUCT_ID "
                    + "where p1.PRODEFINITION_ID = p2.PRODEFINITION_ID "
                    + "and mp1.STANDARD_ID = mp2.STANDARD_ID and mp2.ID = :id";
            Query query = session.createSQLQuery(sql);
            query.setParameter("id", id);
            List<Object[]> result = query.list();

            return result.size();
        } catch (Exception e)
        {
            return 0;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IMismatchProductDAO#loadingMismatchProductMaterial()
     * @Override
     * @desc: 将不符合规则的商品记录加载到MISMATCH_PRODUCT表中，演示用例，可能更改
     */
    public List<Object[]> loadingMismatchProductMaterial(Long id)
    {
        Session session = getSession();
        try
        {
            String sql = "select m.MATERIAL_NAME,rpm.MATERIAL_CONTENT,rpm.UNIT from REL_PRODUCT_MATERIAL rpm left join MATERIAL m on m.MATERIAL_ID = rpm.MATERIAL_ID  where rpm.PRODUCT_ID = :id";

            Query query = session.createSQLQuery(sql);
            query.setParameter("id", id);
            List<Object[]> result = query.list();

            return result;
        } catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IMismatchProductDAO#delMismatchProductByProductIdAndStandardId(java.lang.Long, java.lang.Long)
     * @Override
     */
    public void delMismatchProductByProductIdAndStandardId(Long productId, Long standardId)
    {
        Session session = getSession();
        try
        {
            String sql = "DELETE from MISMATCH_PRODUCT where MISMATCH_PRODUCT.PRODUCT_ID = :pid and MISMATCH_PRODUCT.STANDARD_ID = :sid";
            Query query = session.createSQLQuery(sql);
            query.setParameter("pid", productId);
            query.setParameter("sid", standardId);
            query.executeUpdate();
        } catch (Exception e)
        {
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IMismatchProductDAO#getStandardTotal()
     * @Override
     */
    public int getStandardTotal()
    {
        String hql = "select count(distinct standardId) from MismatchProduct";
        Long res = (Long) this.getHibernateTemplate().find(hql).get(0);
        return res != null ? res.intValue() : 0;
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IMismatchProductDAO#getMismatchProductTotal()
     * @Override
     */
    public int getMismatchProductTotal()
    {
        String hql = "select count(*) from MismatchProduct";
        Long res = (Long) this.getHibernateTemplate().find(hql).get(0);
        return res != null ? res.intValue() : 0;
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IMismatchProductDAO#getMismatchMap()
     * @Override
     * @desc 获得风险产品的地区分布情况
     * @return 风险产品分布国家和数量
     */
    public List<Object[]> getMismatchMap()
    {
        Session session = getSession();
        try
        {
            String sql = "select c.COUNTRY_NAME,count(distinct mp.PRODUCT_ID) "
                    + "from MISMATCH_PRODUCT mp left join PRODUCT p on p.PRODUCT_ID = mp.PRODUCT_ID left join COUNTRY c on c.COUNTRY_ID = p.COUNTRY "
                    + "where c.COUNTRY_NAME is not null group by c.COUNTRY_NAME";
            Query query = session.createSQLQuery(sql);
            List<Object[]> result = query.list();
            return result;
        } catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    @Override
    public int getWebsiteTotal()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * (non-Javadoc)
     * @see
     * @Override
     * @desc 风险报表
     * @return
     */
    public List<Object[]> getMismatchFormByCountry(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "WITH TOPX AS (" + "SELECT TOP(:topSize) C.COUNTRY_ID  AS ID, "
                    + "C.COUNTRY_NAME AS COUNTRY, " + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN COUNTRY AS C ON C.COUNTRY_ID = P.COUNTRY "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime " + "GROUP BY C.COUNTRY_NAME,C.COUNTRY_ID "
                    + "ORDER BY RESULT DESC) " + "SELECT CONVERT(varchar(12) , MP.CREATE_TIME, 102 ) AS DATE, "
                    + "T.COUNTRY AS COUNTRY, " + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN TOPX AS T ON T.ID = P.COUNTRY "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY CONVERT(varchar(12) , MP.CREATE_TIME, 102 ),  T.COUNTRY " + "ORDER BY DATE ASC";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getMismatchFormByWebsite(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "WITH TOPX AS (" + "SELECT TOP(:topSize) W.WEBSITE_ID  AS ID, "
                    + "W.WEBSITE_NAME AS WEBSITE, " + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN WEBSITE AS W ON W.WEBSITE_ID = P.WEBSITE "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime " + "GROUP BY W.WEBSITE_NAME, W.WEBSITE_ID "
                    + "ORDER BY RESULT DESC) " + "SELECT CONVERT(varchar(12) , MP.CREATE_TIME, 102 ) AS DATE, "
                    + "T.WEBSITE AS WEBSITE, " + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN TOPX AS T ON T.ID = P.WEBSITE "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY CONVERT(varchar(12) , MP.CREATE_TIME, 102 ),  T.WEBSITE " + "ORDER BY DATE ASC";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getMismatchFormByType(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "WITH TOPX AS (" + "SELECT TOP(:topSize)  PT.PRODUCTTYPE_ID AS ID, "
                    + "PT.PRODUCTTYPE_NAME AS PRODUCTTYPE, " + "COUNT(MP.ID) AS RESULT "
                    + "FROM MISMATCH_PRODUCT AS MP " + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN PRODUCTTYPE AS PT ON PT.PRODUCTTYPE_ID = P.SECOND_TYPE "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY PT.PRODUCTTYPE_NAME, PT.PRODUCTTYPE_ID " + "ORDER BY RESULT DESC) "
                    + "SELECT CONVERT(varchar(12) , MP.CREATE_TIME, 102 ) AS DATE, " + "T.PRODUCTTYPE AS PRODUCTTYPE, "
                    + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN TOPX AS T ON T.ID = P.SECOND_TYPE "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY CONVERT(varchar(12) , MP.CREATE_TIME, 102 ),  T.PRODUCTTYPE " + "ORDER BY DATE ASC";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getMismatchFormByBrand(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "WITH TOPX AS (" + "SELECT TOP(:topSize) B.BRAND_ID AS ID, "
                    + "B.BRAND_NAMEEN AS BRAND, " + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN PRODUCTDEFINITION AS PF ON P.PRODEFINITION_ID = PF.PRODUCTDEFINITION_ID "
                    + "INNER JOIN BRAND AS B ON B.BRAND_ID = PF.BRAND "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime " + "GROUP BY B.BRAND_NAMEEN, B.BRAND_ID "
                    + "ORDER BY RESULT DESC) " + "SELECT CONVERT(varchar(12) , MP.CREATE_TIME, 102 ) AS DATE, "
                    + "T.BRAND AS BRAND, " + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN PRODUCTDEFINITION AS PF ON P.PRODEFINITION_ID = PF.PRODUCTDEFINITION_ID "
                    + "INNER JOIN TOPX AS T ON T.ID = PF.BRAND "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY CONVERT(varchar(12) , MP.CREATE_TIME, 102 ),  T.BRAND " + "ORDER BY DATE ASC";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getTopCountry(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "SELECT TOP(:topSize) C.COUNTRY_ID  AS ID, " + "C.COUNTRY_NAME AS COUNTRY, "
                    + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN COUNTRY AS C ON C.COUNTRY_ID = P.COUNTRY "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime " + "GROUP BY C.COUNTRY_NAME,C.COUNTRY_ID "
                    + "ORDER BY RESULT DESC";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getTopWebsite(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "SELECT TOP(:topSize) W.WEBSITE_ID  AS ID, " + "W.WEBSITE_NAME AS WEBSITE, "
                    + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN WEBSITE AS W ON W.WEBSITE_ID = P.WEBSITE "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime " + "GROUP BY W.WEBSITE_NAME, W.WEBSITE_ID "
                    + "ORDER BY RESULT DESC";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getTopType(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "SELECT TOP(:topSize) PT.PRODUCTTYPE_ID AS ID, " + "PT.PRODUCTTYPE_NAME AS PRODUCTTYPE, "
                    + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN PRODUCTTYPE AS PT ON PT.PRODUCTTYPE_ID = P.SECOND_TYPE "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY PT.PRODUCTTYPE_NAME, PT.PRODUCTTYPE_ID " + "ORDER BY RESULT DESC";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getTopBrand(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "SELECT TOP(:topSize) B.BRAND_ID AS ID, " + "B.BRAND_NAMEEN AS BRAND, "
                    + "COUNT(MP.ID) AS RESULT " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN PRODUCTDEFINITION AS PF ON P.PRODEFINITION_ID = PF.PRODUCTDEFINITION_ID "
                    + "INNER JOIN BRAND AS B ON B.BRAND_ID = PF.BRAND "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime " + "GROUP BY B.BRAND_NAMEEN, B.BRAND_ID "
                    + "ORDER BY RESULT DESC";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getPieChartByCountry(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "WITH TopItems AS " + "(SELECT C.COUNTRY_ID AS COUNTRY_ID, "
                    + "C.COUNTRY_NAME AS COUNTRY_NAME, " + "Count(MP.ID) AS RESULT, "
                    + "ROW_NUMBER() OVER( ORDER BY Count(MP.ID) DESC ) AS NUM " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN COUNTRY AS C ON C.COUNTRY_ID = P.COUNTRY "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY C.COUNTRY_NAME, C.COUNTRY_ID ) " + "Select COUNTRY_NAME AS item, " + "RESULT AS result "
                    + "From TopItems " + "Where Num <= :topSize " + "Union ALL " + "Select '其它', "
                    + "ISNULL(Sum(RESULT),0) " + "From TopItems " + "Where Num > :topSize " + "Union ALL "
                    + "Select '总计', Sum(RESULT) " + "From TopItems ";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getPieChartByWebsite(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "WITH TopItems AS " + "(SELECT W.WEBSITE_ID AS WEBSITE_ID, "
                    + "W.WEBSITE_NAME AS WEBSITE_NAME, " + "Count(MP.ID) AS RESULT, "
                    + "ROW_NUMBER() OVER( ORDER BY Count(MP.ID) DESC ) AS NUM " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN WEBSITE AS W ON W.WEBSITE_ID = P.WEBSITE "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY W.WEBSITE_NAME, W.WEBSITE_ID ) " + "Select WEBSITE_NAME AS item, " + "RESULT AS result "
                    + "From TopItems " + "Where Num <= :topSize " + "Union ALL " + "Select '其它', "
                    + "ISNULL(Sum(RESULT),0) " + "From TopItems " + "Where Num > :topSize " + "Union ALL "
                    + "Select '总计', Sum(RESULT) " + "From TopItems ";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getPieChartByProductType(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "WITH TopItems AS " + "(SELECT PT.PRODUCTTYPE_ID AS PRODUCTTYPE_ID, "
                    + "PT.PRODUCTTYPE_NAME AS PRODUCTTYPE_NAME, " + "Count(MP.ID) AS RESULT, "
                    + "ROW_NUMBER() OVER( ORDER BY Count(MP.ID) DESC ) AS NUM " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN PRODUCTTYPE AS PT ON PT.PRODUCTTYPE_ID = P.SECOND_TYPE "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime "
                    + "GROUP BY PT.PRODUCTTYPE_NAME, PT.PRODUCTTYPE_ID ) " + "Select PRODUCTTYPE_NAME AS item, "
                    + "RESULT AS result " + "From TopItems " + "Where Num <= :topSize " + "Union ALL " + "Select '其它', "
                    + "ISNULL(Sum(RESULT),0) " + "From TopItems " + "Where Num > :topSize " + "Union ALL "
                    + "Select '总计', Sum(RESULT) " + "From TopItems ";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Object[]> getPieChartByBrand(Integer topSize, Date startTime, Date endTime)
    {
        try
        {
            final String sql = "WITH TopItems AS " + "(SELECT B.BRAND_ID AS BRAND_ID, "
                    + "B.BRAND_NAMEEN AS BRAND_NAME, " + "Count(MP.ID) AS RESULT, "
                    + "ROW_NUMBER() OVER( ORDER BY Count(MP.ID) DESC ) AS NUM " + "FROM MISMATCH_PRODUCT AS MP "
                    + "INNER JOIN PRODUCT AS P ON P.PRODUCT_ID = MP.PRODUCT_ID "
                    + "INNER JOIN PRODUCTDEFINITION AS PF ON P.PRODEFINITION_ID = PF.PRODUCTDEFINITION_ID "
                    + "INNER JOIN BRAND AS B ON B.BRAND_ID = PF.BRAND "
                    + "WHERE MP.CREATE_TIME BETWEEN :startTime AND :endTime " + "GROUP BY B.BRAND_NAMEEN, B.BRAND_ID ) "
                    + "Select BRAND_NAME AS item, " + "RESULT AS result " + "From TopItems " + "Where Num <= :topSize "
                    + "Union ALL " + "Select '其它', " + "ISNULL(Sum(RESULT),0) " + "From TopItems "
                    + "Where Num > :topSize " + "Union ALL " + "Select '总计', Sum(RESULT) " + "From TopItems ";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("topSize", topSize);
                    query.setParameter("startTime", startTime);
                    query.setParameter("endTime", endTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public List<Object[]> getEvaluationByWebsite(int pageIndex, int pageSize)
    {
        try
        {
            final String sql = "SELECT W.WEBSITE_ID, W.WEBSITE_NAME, CAST(COUNT(*) AS FLOAT) /(SELECT COUNT(*) FROM PRODUCT WHERE PRODUCT.WEBSITE = W.WEBSITE_ID) AS RESULT FROM MISMATCH_PRODUCT AS MP, WEBSITE AS W, PRODUCT AS P WHERE P.PRODUCT_ID = MP.PRODUCT_ID AND P.WEBSITE = W.WEBSITE_ID GROUP BY W.WEBSITE_ID, W.WEBSITE_NAME";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setMaxResults(pageSize);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public Integer getEvaluationTotalByWebsite()
    {
        try
        {
            final String sql = "SELECT COUNT(DISTINCT W.WEBSITE_ID) FROM WEBSITE AS W, PRODUCT AS P, MISMATCH_PRODUCT AS MP WHERE MP.PRODUCT_ID = P.PRODUCT_ID AND W.WEBSITE_ID = P.WEBSITE";
            Object obj = getHibernateTemplate().execute(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    int total = (int) query.uniqueResult();
                    return total;
                }
            });
            return (Integer) obj;
        } catch (Exception e)
        {
            return null;
        }
    }
}
