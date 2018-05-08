package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductDAO;
import com.tonik.model.Country;
import com.tonik.model.Product;
import com.tonik.model.ProductStyle;
import com.tonik.model.Rules;
import com.tonik.model.WebsiteStyle;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IProductDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="ProductDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ProductDAOHibernate extends BaseDaoHibernate implements IProductDAO
{
    @Override
    public List<Product> getProduct()
    {
        return getHibernateTemplate().find("from Product fetch all properties");
    }

    @Override
    public Product getProduct(Long productId)
    {
        try
        {
            final String hql = "from Product p left join p.website left join p.productType left join p.country left join p.area "
                    + "left join p.firstlevelType left join p.secondlevelType left join p.thirdlevelType where p.id="
                    + productId;
            //
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Product) listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveProduct(Product product)
    {

        getHibernateTemplate().saveOrUpdate(product);
    }

    @Override
    public void removeProduct(Product product)
    {
        getHibernateTemplate().delete(product);
    }

    public void removeProduct(Long productId)
    {
        getHibernateTemplate().delete(getProduct(productId));
    }

    @SuppressWarnings("unchecked")
    // nimengfei
    public List<Product> getProductByRules(Rules rules, List<String> rulesvalue)
    {
        try
        {
            String hql = "from Product t left join t.productType left join t.website left join t.country left join t.area "
                    + "left join p.firstlevelType left join p.secondlevelType left join p.thirdlevelType where (";

            for (ProductStyle item : rules.getProductStyles())
            {
                hql += "t.productType.id =" + item.getId() + " or ";
            }
            hql = hql.substring(0, hql.length() - 4);
            hql += ") and (";
            for (WebsiteStyle item : rules.getWebsiteStyles())
            {
                hql += " t.website.webStyle.id=" + item.getId() + " or ";
            }
            hql = hql.substring(0, hql.length() - 4);
            hql += ")";
            for (String item : rulesvalue)
            {
                hql += " and t.remark like '%" + item + "%'";
            }
            // hql=hql.substring(0, hql.length()-5);
            final String a = hql;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(a);
                    List list = query.list();
                    return list;
                }
            });
            List<Product> products = new ArrayList<Product>();
            for (Object[] item : listTable)
            {
                Product pt = (Product) item[0];
                products.add(pt);
            }
            return products;

        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Product> getProductPaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {

            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from Product p  where p.name like :strQuery or p.brand like :strQuery "
                    + " or p.remark like :strQuery " + "and createTime>=:strStraTime and createTime<=:strEndTime";
            List<Product> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStraTime", StraTime);
                    query.setParameter("strEndTime", EndTime);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
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

    @SuppressWarnings("unchecked")
    public List<Object[]> getProductPaging1(final int pageIndex, final int pageSize, final String strQuery,
            String strStraTime, String strEndTime)
    {
        try
        {
            final String sql;
            if(strStraTime.isEmpty() && strEndTime.isEmpty() && strQuery.isEmpty())
                sql = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_LOCATION, WEBSITE_NAME, PRODUCT_REMARK, CREATEPERSON, CREATETIME FROM PRODUCT P order by P.CREATETIME desc";
            else if(strEndTime.isEmpty() && strQuery.isEmpty())
                sql = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_LOCATION, WEBSITE_NAME, PRODUCT_REMARK, CREATEPERSON, CREATETIME FROM PRODUCT P WHERE P.CREATETIME > '" + strStraTime + "'";
            else if(strStraTime.isEmpty() && strQuery.isEmpty())
                sql = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_LOCATION, WEBSITE_NAME, PRODUCT_REMARK, CREATEPERSON, CREATETIME FROM PRODUCT P WHERE P.CREATETIME < '" + strEndTime + "'";
            else if(strQuery.isEmpty())
                sql = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_LOCATION, WEBSITE_NAME, PRODUCT_REMARK, CREATEPERSON, CREATETIME FROM PRODUCT P WHERE P.CREATETIME BETWEEN '" + strStraTime + "' AND '" + strEndTime +"'";
            else
            {
                if(strStraTime.isEmpty())
                    strStraTime = "1980-01-01 00:00:01";
                if(strEndTime.isEmpty())
                {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    strEndTime = df.format(new Date());
                }
                sql = "select * from F_GetProduct('" + strQuery + "', '" + strStraTime + "', '" + strEndTime + "')";
            }
            
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    // query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
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
    public List<Object[]> getProductPaging(int pageIndex, int pageSize, String orderBy, String orderType)
    {
        Session session = getSession();
        try
        {
            // 商品名称，网站名称
            String sql = "select p.PRODUCT_NAME,p.WEBSITE_NAME " + "from PRODUCT p where p.PRODUCT_NAME !=''";

            if (orderBy.length() != 0)
            {
                sql += "ORDER BY p." + orderBy + " ";
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

    @Override
    public int getProductTotal1(final String strQuery, String strStartTime, String strEndTime)
    {
        try
        {
            final String sql;
            if(strStartTime.isEmpty() && strEndTime.isEmpty() && strQuery.isEmpty())
                sql = "SELECT COUNT(*) FROM PRODUCT P ";
            else if(strEndTime.isEmpty() && strQuery.isEmpty())
                sql = "SELECT COUNT(*) FROM PRODUCT P WHERE P.CREATETIME > '" + strStartTime + "'";
            else if(strStartTime.isEmpty() && strQuery.isEmpty())
                sql = "SELECT COUNT(*) FROM PRODUCT P WHERE P.CREATETIME < '" + strEndTime + "'";
            else if(strQuery.isEmpty())
                sql = "SELECT COUNT(*) FROM PRODUCT P WHERE P.CREATETIME BETWEEN '" + strStartTime + "' AND '" + strEndTime +"'";
            else
            {
                if(strStartTime.isEmpty())
                    strStartTime = "1980-01-01 00:00:01";
                if(strEndTime.isEmpty())
                {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    strEndTime = df.format(new Date());
                }
                sql = "select count(*) from F_GetProduct('" + strQuery + "', '" + strStartTime + "', '" + strEndTime + "')";
            }
            Object obj = getHibernateTemplate().execute(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    Object obj = query.uniqueResult();
                    return obj;
                }
            });
            return (int) obj;
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public int getProductTotal(final String strQuery, final String strStartTime, final String strEndTime)
    {
        try
        {
            final Date StartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStartTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "select count(*) from Product p  where p.name like :strQuery or p.brand like :strQuery "
                    + "or p.remark like :strQuery " + "and createTime>=:strStartTime and createTime<=:strEndTime";
            String[] params = { "strQuery", "strStartTime", "strEndTime" };
            Object[] args = { "%" + strQuery + "%", StartTime, EndTime };
            Long count = (Long) getHibernateTemplate().findByNamedParam(hql, params, args).listIterator().next();
            // Long count = (Long)getHibernateTemplate().find(hql).listIterator().next();
            return count.intValue();
        } catch (Exception e)
        {
            return 0;
        }

        // final String sql = "select count(*) from PRODUCT p where ( p.PRODUCT_NAME like '%奶粉%' or p.PRODUCT_BRAND like '%奶粉%' or p.PRODUCT_REMARK
        // like '%奶粉%')";
        // //final String sql = "select count(*)"
        // // + " from Fn_GetChannelForDpID("+ dataType + "," + type+ ",'"+ strStartDate+ "','"+ strEndDate+"')";
        //
        // List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
        // {
        // public Object doInHibernate(Session session) throws HibernateException, SQLException
        // {
        // SQLQuery query = session.createSQLQuery(sql);
        // List list = query.list();
        // return list;
        // }
        // });
        // int count = (int)listTable.listIterator().next();
        // return count;
        //
    }

    public Product getProductByLocation(final String location)
    {
        try
        {
            final String hql = "from Product p left join p.website left join p.productType left join p.country left join p.area where p.location ='"
                    + location + "'";
            //
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Product) listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }

    }

    @Override
    public int getProductTotalByTypeIds(final Long[] ptl)
    {
        try
        {
            String hql1 = "";
            if (ptl.length == 0)
            {
                return getHibernateTemplate().find("from Product").size();
            }

            else
            {
                hql1 = "from Product p left join p.firstlevelType left join p.secondlevelType left join p.thirdlevelType "
                        + "where p.firstlevelType.id in (:PTL) or p.secondlevelType.id in (:PTL) or p.thirdlevelType.id in (:PTL) "
                        + " order by p.sales";
            }

            final String hql = hql1;

            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    if (ptl.length > 0)
                    {
                        query.setParameterList("PTL", ptl);

                    }
                    List list = query.list();
                    return list;
                }
            });
            return listTable.size();

        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<Product> getProductListByTypeIds(final Long[] ptl)
    {
        try
        {
            String hql1 = "";
            if (ptl.length == 0)
            {
                return getHibernateTemplate().find(
                        "select p from Product p left join p.firstlevelType left join p.secondlevelType left join p.thirdlevelType order by p.sales desc ");
            }
            else
            {
                hql1 = "select p from Product p left join p.firstlevelType left join p.secondlevelType left join p.thirdlevelType "
                        + " where (p.firstlevelType.id in (:PTL) or p.secondlevelType.id in (:PTL) or p.thirdlevelType.id in (:PTL) )"
                        + " order by p.sales desc";

            }

            final String hql = hql1;

            List<Product> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    if (ptl.length > 0)
                    {
                        query.setParameterList("PTL", ptl);

                    }
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
    public int getProductTotalByTypeIdsAndCountryId(final Long countryid, final Long[] ptl)
    {
        try
        {
            String hql1 = "";
            if (ptl.length == 0)
            {
                hql1 = "from Product p left join p.country where p.country.id = " + countryid
                        + " order by p.sales desc";
            }
            else
            {
                hql1 = "from Product p left join p.country left join p.firstlevelType " + "where p.country.id = "
                        + countryid + " and ( p.firstlevelType.id in (:PTL) "
                        + "or p.secondlevelType.id in (:PTL) or p.thirdlevelType.id in (:PTL) )"
                        + " order by p.sales desc";

            }

            final String hql = hql1;

            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    if (ptl.length > 0)
                    {
                        query.setParameterList("PTL", ptl);

                    }
                    List list = query.list();
                    return list;
                }
            });
            return listTable.size();

        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<Object[]> getProductListByTypeIdsAndCountryId(final Long countryid, final Long[] ptl)
    {
        try
        {
            String hql1 = "";
            if (ptl.length == 0)
            {
                hql1 = "select p from Product p left join p.firstlevelType left join p.secondlevelType left join p.thirdlevelType left join p.country left join p.area "
                        + "left join p.website where p.country.id = " + countryid + " order by p.sales desc";
            }
            else
            {
                hql1 = "from Product p left join p.firstlevelType left join p.secondlevelType left join p.thirdlevelType left join p.country "
                        + "where p.country.id = " + countryid
                        + " and ( p.firstlevelType.id in (:PTL) or p.secondlevelType.id in (:PTL) or p.thirdlevelType.id in (:PTL) )"
                        + " order by p.sales desc";

            }

            final String hql = hql1;

            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    if (ptl.length > 0)
                    {
                        query.setParameterList("PTL", ptl);
                    }
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
        /*
         * String sql=""; if(ptl.length==0){ sql="select * from Product p where p.country = :country order by p.sales desc"; }else{
         * sql="select * from Product p where p.country = :country and ( p.FIRST_TYPE in (:PTL) or p.SECOND_TYPE in (:PTL) or p.THIRD_TYPE in (:PTL) ) order by p.sales desc"
         * ; } Query query=getSession().createSQLQuery(sql).addEntity(Product.class); query.setParameter("country", countryid); if(ptl.length>0){
         * query.setParameterList("PTL", ptl); } return query.list().get(0);
         */
    }

    // 获得各个国家的商品总数
    @Override
    public List<Object[]> getWorldMapProductTotal(String ptl)
    {
        try
        {
            String hql1 = "";
            if (ptl.length() == 0)
            {
                hql1 = "select country, country_name, count(PRODUCT_ID) from PRODUCT left join COUNTRY on COUNTRY = COUNTRY_ID where country is not null group by country,country_name ";
            }
            else
            {
                hql1 = "select country, country_name, count(PRODUCT_ID) from PRODUCT left join COUNTRY on COUNTRY = COUNTRY_ID"
                        + " where country is not null and ( first_type in (" + ptl + ") or second_type in (" + ptl
                        + ") or third_type in (" + ptl + ")) group by country,country_name";

            }
            String sql = hql1;

            Session session = getSession();
            List ls = session.createSQLQuery(sql).list();
            releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }
    }

    // 获得世界地图中各个国家中的商品名字列表
    @Override
    public List<Object[]> getWorldMapProductNameList(String ptl)
    {
        try
        {

            String sql1 = "";
            if (ptl.length() == 0)
            {
                sql1 = "select country_name, product_name from PRODUCT a left join country on COUNTRY = COUNTRY_ID"
                        + " where country is not null and country_name is not null and (select count(*) from product b where a.COUNTRY = b.COUNTRY and a.SALES < b.SALES) < 10 order by COUNTRY";
            }
            else
            {
                sql1 = "select country_name, product_name from PRODUCT a left join COUNTRY on COUNTRY = COUNTRY_ID where country is not null and country_name is not null and "
                        + " (first_type in (" + ptl + ") or second_type in (" + ptl + ") or third_type in (" + ptl
                        + ")) and"
                        + " (select count(*) from PRODUCT b where a.COUNTRY = b.COUNTRY and a.SALES < b.SALES and (first_type in ("
                        + ptl + ") or second_type in (" + ptl + ") or third_type in (" + ptl + "))) < 10";

            }

            final String sql = sql1;

            Session session = getSession();
            List ls = getSession().createSQLQuery(sql).list();
            releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public Long getProductTotal()
    {
        Session session = getSession();

        try
        {
            String hql = "select count(*) from Product ";

            Integer res = (Integer) session.createSQLQuery(hql).list().get(0);
            return res != null ? res.longValue() : (Long) 0L;

            // Long res = (Long)getSession().createSQLQuery(hql).list().get(0);
            // return res != null ? res : (Long)0L;

        } catch (Exception e)
        {
            return 0L;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

    }

    public List<Product> getProductByEventWebsite(Long websiteId)
    {
        try
        {
            final String hql = "from Product p left join fetch p.website where p.website.id=" + websiteId;
            //
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
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

    public List<Product> getProductByEventBrand(String brandName)
    {
        try
        {
            final String hql = "from Product where brand like :brandName";
            //
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("brandName", brandName);
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

    public List<Product> getProductByProductDefinitionId(Long ProductDefinitionId)
    {
        try
        {
            final String hql = "from Product where pdid=" + ProductDefinitionId;
            //
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
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

    public boolean MatchingProductDefinition(List<Object[]> pdf)
    {
        try
        {
            for (Object[] item : pdf)
            {
                Long pdId = Long.parseLong(item[0].toString());
                String feature_one = item[1].toString();
                String feature_two = item[2].toString();
                String feature_three = item[3].toString();
                String sql = "UPDATE PRODUCT SET PRODEFINITION_ID = " + pdId + " WHERE  (PRODUCT_REMARK like '%"
                        + feature_one + "%' " + "AND PRODUCT_REMARK like '%" + feature_two
                        + "%' AND PRODUCT_REMARK like '%" + feature_three + "%')";
                Session session = getSession();
                SQLQuery query = session.createSQLQuery(sql);
                int result = query.executeUpdate();
                releaseSession(session);

            }
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IProductDAO#getProductListByMaterialIds(java.lang.Long[])
     * @Override
     * @desc 获得含有某些配方原料的商品列表
     * @param ptl 配方原料id数组
     * @return List<Product> 商品列表
     */
    public List<Product> getProductListByMaterialIds(Long[] ptl)
    {
        final String sql = "select * from PRODUCT p where p.PRODEFINITION_ID "
                + "in(select distinct PRODUCTDEFINITION_ID from PRODUCTDEFMATERIALS where MATERIAL_ID in(:PTL))";
        Session session = getSession();
        Query query = session.createSQLQuery(sql).addEntity(Product.class);
        query.setParameterList("PTL", ptl);
        List list = query.list();
        releaseSession(session);
        return list;
    }

    @Override
    public Integer getProductTotalByStyle(String ptl)
    {
        String sql = " select count(*) from product where FIRST_TYPE in (" + ptl + ") or SECOND_TYPE in (" + ptl
                + ") or THIRD_TYPE in (" + ptl + ")";
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer) ls.get(0);
    }

    @Override
    public Integer getProductTotalByStyleAndCountry(String ptl, Long id)
    {
        String sql = " select count(*) from product where country = " + id + " and (FIRST_TYPE in (" + ptl
                + ") or SECOND_TYPE in (" + ptl + ") or THIRD_TYPE in (" + ptl + "))";
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer) ls.get(0);
    }

    @Override
    public List<Object[]> getProductLists(Country c, String start, String len, String strOrder, String dir)
    {
        try
        {
            String hql1 = "";
            if (c != null)
            {
                hql1 = "select top(" + len
                        + ")isnull(PRODUCT_NAME, PRODUCT_REMARK), WEBSITE.WEBSITE_NAME, PRODUCT_LOCATION from PRODUCT left join WEBSITE on WEBSITE = WEBSITE_ID"
                        + " where product.country = " + c.getId() + " and " + " PRODUCT_ID not in(select top(" + start
                        + ")PRODUCT_ID from PRODUCT where country = " + c.getId() + " order by " + strOrder + " " + dir
                        + ") order by " + strOrder + " " + dir;
            }
            else
            {
                hql1 = "select top(" + len
                        + ")isnull(PRODUCT_NAME, PRODUCT_REMARK), WEBSITE.WEBSITE_NAME, PRODUCT_LOCATION from PRODUCT left join WEBSITE on WEBSITE = WEBSITE_ID"
                        + " where PRODUCT_ID not in(select top(" + start + ")PRODUCT_ID from PRODUCT order by "
                        + strOrder + " " + dir + ") order by " + strOrder + " " + dir;
            }
            String sql = hql1;
            Session session = getSession();
            List ls = session.createSQLQuery(sql).list();
            releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public List<Object[]> getProductListsByStyle(String ptl, Country c, String start, String len, String strOrder,
            String dir)
    {
        try
        {
            String hql1 = "";
            if (c != null)
            {
                hql1 = "select top(" + len
                        + ")isnull(PRODUCT_NAME, PRODUCT_REMARK), WEBSITE.WEBSITE_NAME, PRODUCT_LOCATION from PRODUCT left join WEBSITE on WEBSITE = WEBSITE_ID"
                        + " where product.country = " + c.getId() + " and (FIRST_TYPE in(" + ptl
                        + ") or SECOND_TYPE in (" + ptl + ") or THIRD_TYPE in (" + ptl + ")) and"
                        + " PRODUCT_ID not in(select top(" + start + ")PRODUCT_ID from PRODUCT where country = "
                        + c.getId() + " and (FIRST_TYPE in(" + ptl + ") or SECOND_TYPE in (" + ptl
                        + ") or THIRD_TYPE in (" + ptl + "))" + " order by " + strOrder + " " + dir + ") order by "
                        + strOrder + " " + dir;
            }
            else
            {
                hql1 = "select top(" + len
                        + ")isnull(PRODUCT_NAME, PRODUCT_REMARK), WEBSITE.WEBSITE_NAME, PRODUCT_LOCATION from PRODUCT left join WEBSITE on WEBSITE = WEBSITE_ID"
                        + " where (FIRST_TYPE in(" + ptl + ") or SECOND_TYPE in (" + ptl + ") or THIRD_TYPE in (" + ptl
                        + ")) and" + " PRODUCT_ID not in(select top(" + start
                        + ")PRODUCT_ID from PRODUCT where (FIRST_TYPE in(" + ptl + ") or SECOND_TYPE in (" + ptl
                        + ") or THIRD_TYPE in (" + ptl + "))" + " order by " + strOrder + " " + dir + ") order by "
                        + strOrder + " " + dir;
            }
            String sql = hql1;
            Session session = getSession();
            List ls = session.createSQLQuery(sql).list();
            releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void execSyncProductsTask(String dateFrom)
    {

        Session session = getSession();
        try
        {
            String sql = "exec Proc_SetProductDefinition '" + dateFrom + "'";
            session.createSQLQuery(sql).executeUpdate();
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

    @Override
    public void execSyncProductsTaskById(Long id, String remark)
    {

        Session session = getSession();
        try
        {
            String sql = "exec Proc_SetDefinitionIDByRemark '" + id + "','" + remark.replace("'", "") + "'";
            session.createSQLQuery(sql).executeUpdate();
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

    @Override
    public List<Object[]> getContrabandProduct(int type)
    {
        try
        {
            String sql = "";
            List ls = getSession().createSQLQuery(sql).list();
            return ls;
        } catch (Exception e)
        {
            return null;
        }

    }

    @Override
    public List<Object[]> getContrabandProductWebsite()
    {
        try
        {
            String sql = "";
            List ls = getSession().createSQLQuery(sql).list();
            return ls;
        } catch (Exception e)
        {
            return null;
        }

    }

    @Override
    public List<Object[]> getContrabandProductBrand()
    {
        try
        {
            String sql = "";
            List ls = getSession().createSQLQuery(sql).list();
            return ls;
        } catch (Exception e)
        {
            return null;
        }

    }

    @Override
    public List<Object[]> getContrabandProductOrigin()
    {
        try
        {
            String sql = "";
            List ls = getSession().createSQLQuery(sql).list();
            return ls;
        } catch (Exception e)
        {
            return null;
        }

    }

    @Override
    public List<Object[]> getContrabandProductMaterial()
    {
        try
        {
            String sql = "";
            List ls = getSession().createSQLQuery(sql).list();
            return ls;
        } catch (Exception e)
        {
            return null;
        }

    }

    @Override
    public List<Object[]> getContrabandProductSentiment()
    {
        try
        {
            String sql = "";
            List ls = getSession().createSQLQuery(sql).list();
            return ls;
        } catch (Exception e)
        {
            return null;
        }

    }

    @Override
    public List<Product> getProductByDate(Date fromDate)
    {

        try
        {
            // final Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fromDate);
            final String hql = "from Product p where p.createTime >=:fromDate order by p.createTime asc";

            List<Product> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("fromDate", fromDate);
                    query.setFirstResult(1);
                    query.setMaxResults(500);
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

    public List<Object[]> getMatchProductPaging(final int pageIndex, final int pageSize, final String strQuery,
            final Long productType, final Boolean isMatched)
    {
        try
        {
            StringBuilder sb = new StringBuilder(
                    "select * from V_MatchProduct as mp where mp.PRODUCT_NAME like :strQuery and  mp.PRODUCT_NAME is not null and  mp.PRODUCT_NAME != ''");
            if (productType != null)
                sb.append(" and mp.PRODUCTTYPE_ID = " + productType);
            if (isMatched == null)
                ;
            else if (isMatched.booleanValue() == true)
                sb.append(" and mp.PRODUCTDEFINITION_ID is not null");
            else if (isMatched.booleanValue() == false)
                sb.append(" and mp.PRODUCTDEFINITION_ID is null");
            sb.append(" order by mp.CREATETIME desc");
            final String sql = sb.toString();
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            throw e;
        }
    }

    public Integer getMatchProductTotal(final String strQuery, final Long productType, final Boolean isMatched)
    {
        try
        {
            StringBuilder sb = new StringBuilder(
                    "select count(*) from V_MatchProduct as mp where mp.PRODUCT_NAME like :strQuery and mp.PRODUCT_NAME is not null and  mp.PRODUCT_NAME != ''");
            if (productType != null)
                sb.append(" and mp.PRODUCTTYPE_ID = " + productType);
            if (isMatched == null)
                ;
            else if (isMatched.booleanValue() == true)
                sb.append(" and mp.PRODUCTDEFINITION_ID is not null");
            else if (isMatched.booleanValue() == false)
                sb.append(" and mp.PRODUCTDEFINITION_ID is null");

            final String sql = sb.toString();
            Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    Object obj = query.uniqueResult();
                    return obj;
                }
            });
            return count;
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public List<Product> getRandTopProducts()
    {
        try
        {
            final String sql = "select top 20 P.* from PRODUCT AS P order by newid()";

            List<Product> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql).addEntity(Product.class);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            throw e;
        }
    }

}
