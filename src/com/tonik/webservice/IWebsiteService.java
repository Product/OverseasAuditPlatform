package com.tonik.webservice;

import javax.jws.WebService;

@WebService
public interface IWebsiteService
{
    String listWebsites(String strStraTime, String strEndTime, String webType, String strPageIndex,
            String strPageCount);
}
