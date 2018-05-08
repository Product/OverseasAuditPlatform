package com.tonik.webservice;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import com.thinvent.utils.GsonUtil;
import com.thinvent.utils.SpringContextUtil;
import com.tonik.service.WebsiteService.WebsitePagingBean;

@WebService(endpointInterface = "com.tonik.webservice.IWebsiteService")
public class WebsiteService implements IWebsiteService
{
    @Override
    public String listWebsites(String strStraTime, String strEndTime, String webType, String strPageIndex,
            String strPageCount)
    {
        com.tonik.service.WebsiteService websiteService = SpringContextUtil.getBean("WebsiteService");
        String strTotal = websiteService.NewWebsiteTotal("", strStraTime, strEndTime, webType);
        List<WebsitePagingBean> ls = websiteService.NewWebsitePaging(Integer.parseInt(strPageIndex),
                Integer.parseInt(strPageCount), "", strStraTime, strEndTime, webType);
        String strJson = "";

        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("total", strTotal);
        res.put("websiteList", ls);
        return GsonUtil.bean2Json(res);
    }
}