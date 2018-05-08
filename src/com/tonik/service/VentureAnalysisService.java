package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IVentureAnalysisDAO;
import com.tonik.dao.IVentureAnalysisStyleDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.dao.IWebsiteStyleDAO;
import com.tonik.model.VentureAnalysis;
import com.tonik.model.VentureAnalysisStyle;
import com.tonik.model.Website;
import com.tonik.model.WebsiteStyle;

/**
 * @spring.bean id="VentureAnalysisService"
 * @spring.property name="ventureAnalysisDAO" ref="VentureAnalysisDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="websiteStyleDAO" ref="WebsiteStyleDAO"
 * @spring.property name="ventureAnalysisStyleDAO" ref="VentureAnalysisStyleDAO"
 */
public class VentureAnalysisService
{
    private IVentureAnalysisDAO ventureAnalysisDAO;
    private IVentureAnalysisStyleDAO ventureAnalysisStyleDAO;
    private IWebsiteDAO websiteDAO;
    private IWebsiteStyleDAO websiteStyleDAO;


    public IVentureAnalysisStyleDAO getVentureAnalysisStyleDAO()
    {
        return ventureAnalysisStyleDAO;
    }

    public void setVentureAnalysisStyleDAO(IVentureAnalysisStyleDAO ventureAnalysisStyleDAO)
    {
        this.ventureAnalysisStyleDAO = ventureAnalysisStyleDAO;
    }

    public IVentureAnalysisDAO getVentureAnalysisDAO()
    {
        return ventureAnalysisDAO;
    }

    public void setVentureAnalysisDAO(IVentureAnalysisDAO ventureAnalysisDAO)
    {
        this.ventureAnalysisDAO = ventureAnalysisDAO;
    }

     public void DelVentureAnalysis(Long Id)
     {
     ventureAnalysisDAO.removeVentureAnalysis(Id);
     }
    
     public void SaveVentureAnalysis(VentureAnalysis ventureAnalysis)
     {
     ventureAnalysisDAO.saveVentureAnalysis(ventureAnalysis);
     }
    
     public VentureAnalysis GetVentureAnalysisById(Long Id)
     {
     return ventureAnalysisDAO.getVentureAnalysis(Id);
     }
     
     public IWebsiteDAO getWebsiteDAO()
     {
         return websiteDAO;
     }

     public void setWebsiteDAO(IWebsiteDAO websiteDAO)
     {
         this.websiteDAO = websiteDAO;
     }
     
     public IWebsiteStyleDAO getWebsiteStyleDAO()
     {
         return websiteStyleDAO;
     }

     public void setWebsiteStyleDAO(IWebsiteStyleDAO websiteStyleDAO)
     {
         this.websiteStyleDAO = websiteStyleDAO;
     }
      
     public String VentureAnalysisTotal(String strQuery, String strStraTime, String strEndTime)
     {
     return Integer.toString(ventureAnalysisDAO.getVentureAnalysisTotal(strQuery, strStraTime, strEndTime));
     }

    public List<VentureAnalysisStyle> getVentureAnalysisStyle()
    {
        return ventureAnalysisStyleDAO.getVentureAnalysisStyle();
    }

    public List<Website> getWebsite(Long webId)
    {
        return websiteDAO.getWebsite();
    }

    public List<WebsiteStyle> getWebsiteStyle()
    {
        return websiteStyleDAO.getWebsiteStyle();
    }

    public List<VentureAnalysis> VentureAnalysisPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,String strEndTime)
    {
        List<Object[]> ls=ventureAnalysisDAO.getVentureAnalysisPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<VentureAnalysis> es = new ArrayList<VentureAnalysis>();
        for(Object[] obj:ls)
        {
            VentureAnalysis e = (VentureAnalysis)obj[0];
            es.add(e);
        }
        return es;
    }
    
    public WebsiteStyle getWebsiteStyleById(Long Id)
    {
        return websiteStyleDAO.getWebsiteStyle(Id);
    }
    
    public VentureAnalysisStyle getVentureAnalysisStyleById(Long Id)
    {
        return ventureAnalysisStyleDAO.getVentureAnalysisStyle(Id);
    }
    public Website getWebsiteById(Long Id)
    {
        return websiteDAO.getWebsite(Id);
    }
}
