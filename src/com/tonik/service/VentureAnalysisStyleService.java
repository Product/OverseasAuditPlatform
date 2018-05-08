package com.tonik.service;

import java.util.List;

import com.tonik.dao.IVentureAnalysisStyleDAO;
import com.tonik.model.VentureAnalysisStyle;

/**                 
 * @spring.bean id="VentureAnalysisStyleService"
 * @spring.property name="ventureAnalysisStyleDAO" ref="VentureAnalysisStyleDAO"
 */
public class VentureAnalysisStyleService
{
    private IVentureAnalysisStyleDAO ventureAnalysisStyleDAO;

    public IVentureAnalysisStyleDAO getVentureAnalysisStyleDAO()
    {
        return ventureAnalysisStyleDAO;
    }

    public void setVentureAnalysisStyleDAO(IVentureAnalysisStyleDAO ventureAnalysisStyleDAO)
    {
        this.ventureAnalysisStyleDAO = ventureAnalysisStyleDAO;
    }
    
    public void DelVentureAnalysisStyle(Long Id)
    {
        ventureAnalysisStyleDAO.removeVentureAnalysisStyle(Id);
    }
    
    public void SaveVentureAnalysisStyle(VentureAnalysisStyle rules)
    {
        ventureAnalysisStyleDAO.saveVentureAnalysisStyle(rules);
    }


    public List<VentureAnalysisStyle> VentureAnalysisStylePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        List<VentureAnalysisStyle> ls=ventureAnalysisStyleDAO.getVentureAnalysisStylePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return ls;
    }

    public String VentureAnalysisStyleTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(ventureAnalysisStyleDAO.getVentureAnalysisStyleTotal(strQuery, strStraTime, strEndTime));
    }

    
     public VentureAnalysisStyle GetVentureAnalysisStyleById(Long Id)
     {
      return ventureAnalysisStyleDAO.getVentureAnalysisStyle(Id);
     }
     
     //code by yk
     public List<VentureAnalysisStyle> GetVentureAnalysisStyles()
     {
         return ventureAnalysisStyleDAO.getVentureAnalysisStyle();
     }
     
     //返回单条分析类别信息
     public String getVentureAnalysisStyleInfo(VentureAnalysisStyle vs){
         String vas="";
         vas = "{\"Id\":\"" + vs.getId() + "\",\"Name\":\"" + vs.getName() + "\",\"Remark\":\""
                 + vs.getRemark() + "\",\"CreatePerson\":\"" + vs.getCreatePerson()
                 + "\",\"CreateTime\":\"" + vs.getCreateTime() + "\"}";
         return vas;
     }
}
