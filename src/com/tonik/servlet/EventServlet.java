package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.Constant;
import com.tonik.model.DetectingSuggest;
import com.tonik.model.Epidemic;
import com.tonik.model.Event;
import com.tonik.model.EventAffectedArea;
import com.tonik.model.EventAffectedBrand;
import com.tonik.model.EventAffectedMaterial;
import com.tonik.model.EventAffectedProductType;
import com.tonik.model.EventAffectedWebsite;
import com.tonik.model.Product;
import com.tonik.model.ProductDefinition;
import com.tonik.service.DetectingSuggestService;
import com.tonik.service.EpidemicService;
import com.tonik.service.EventAffectedAreaService;
import com.tonik.service.EventAffectedBrandService;
import com.tonik.service.EventAffectedMaterialService;
import com.tonik.service.EventAffectedProductTypeService;
import com.tonik.service.EventAffectedWebsiteService;
import com.tonik.service.EventService;
import com.tonik.service.ProductDefinitionService;
import com.tonik.service.ProductService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件概览 servlet
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * @web.servlet name="eventServlet"
 * @web.servlet-mapping url-pattern="/servlet/EventServlet"
 */
public class EventServlet extends BaseServlet
{
    private EventService eventService;
    private EpidemicService EpidemicService;
    private EventAffectedAreaService eventAffectedAreaService;
    private EventAffectedBrandService eventAffectedBrandService;
    private EventAffectedMaterialService eventAffectedMaterialService;
    private EventAffectedProductTypeService eventAffectedProductTypeService;
    private EventAffectedWebsiteService eventAffectedWebsiteService;
    private ProductService ProductService;
    private DetectingSuggestService DetectingSuggestService;
    private ProductDefinitionService ProductDefinitionService;
    private SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式


    public EventService getEventService()
    {
        return eventService;
    }

    public void setEventService(EventService eventService)
    {
        this.eventService = eventService;
    }

    public EpidemicService getEpidemicService()
    {
        return EpidemicService;
    }

    public void setEpidemicService(EpidemicService epidemicService)
    {
        EpidemicService = epidemicService;
    }

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        eventService = (EventService) ctx.getBean("eventService");
        EpidemicService = (EpidemicService) ctx.getBean("EpidemicService");
        eventAffectedAreaService = (EventAffectedAreaService) ctx.getBean("eventAffectedAreaService");
        eventAffectedBrandService = (EventAffectedBrandService) ctx.getBean("eventAffectedBrandService");
        eventAffectedMaterialService =(EventAffectedMaterialService) ctx.getBean("eventAffectedMaterialService");
        eventAffectedProductTypeService = (EventAffectedProductTypeService) ctx
                .getBean("eventAffectedProductTypeService");
        eventAffectedWebsiteService = (EventAffectedWebsiteService) ctx.getBean("eventAffectedWebsiteService");
        ProductService = (ProductService) ctx.getBean("ProductService");
        DetectingSuggestService = (DetectingSuggestService) ctx.getBean("DetectingSuggestService");
        ProductDefinitionService = (ProductDefinitionService) ctx.getBean("ProductDefinitionService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if (this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                    String strStartTime = request.getParameter("strStartTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    Date date = new Date();

                    if ("".equals(strEndTime))
                    {
                        strEndTime = dateFormater.format(date);
                    }
                    if ("".equals(strStartTime))
                    {
                        strStartTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strPageIndex))
                    {
                        strPageIndex = "1";
                    }
                    if ("".equals(strPageCount))
                    {
                        strPageCount = "100";
                    }

                    String strTotal = eventService.EventTotal(strQuery, strStartTime, strEndTime);
                    List<Event> ls = eventService.EventPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStartTime, strEndTime);
                    String strJson = "";

                    for (Event item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                                + "\",\"TypeName\":\"" + item.getTypeName() + "\",\"BeginDate\":\""
                                + dateFormater.format(item.getBeginDate()) + "\",\"EndDate\":\""
                                + dateFormater.format(item.getEndDate()) + "\",\"Remark\":\"" + item.getRemark()
                                + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"eventList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                if ("QueryListInit".equals(methodName))
                {
                    String strQuery = "";
                    String strStartTime = "1980-01-01 00:00:01";
                    Date date = new Date();
                    String strEndTime = dateFormater.format(date);
                    String strPageIndex = "1";
                    String strPageCount = "100";

                    String strTotal = eventService.EventTotal(strQuery, strStartTime, strEndTime);
                    List<Event> ls = eventService.EventPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStartTime, strEndTime);
                    String strJson = "";

                    for (Event item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                                + "\",\"TypeName\":\"" + item.getTypeName() + "\",\"BeginDate\":\""
                                + dateFormater.format(item.getBeginDate()) + "\",\"EndDate\":\""
                                + dateFormater.format(item.getEndDate()) + "\",\"Remark\":\"" + item.getRemark()
                                + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"eventList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("edit".equals(methodName)) // 编辑事件
                {
                    Event e = eventService.GetEventById(Long.parseLong(request.getParameter("id")));

                    e.setName(URLDecoder.decode(request.getParameter("eventName"), "UTF-8"));
                    e.setTypeId(Long.parseLong(request.getParameter("eventTypeId")));
                    e.setTypeName(URLDecoder.decode(request.getParameter("eventTypeName"), "UTF-8"));
                    e.setBeginDate(dateFormater.parse(request.getParameter("eventBeginDate")));// 将字符串格式成Date
                    e.setEndDate(dateFormater.parse(request.getParameter("eventEndDate")));
                    e.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));
                    if (Long.parseLong(request.getParameter("eventTypeId")) == 1)
                    {
                        e.setEpidemic(
                                EpidemicService.GetEpidemicById(Long.parseLong(request.getParameter("epidemicId"))));
                    }
                    eventService.SaveEvent(e);// 保存
                    response.getWriter().write("true");
                }
                else if ("editEventName".equals(methodName)) // 编辑事件
                {
                    Event evnet = eventService.GetEventById(Long.parseLong(request.getParameter("id")));
                    evnet.setName(URLDecoder.decode(request.getParameter("eventName"), "UTF-8"));
                    eventService.SaveEvent(evnet);// 保存
                    response.getWriter().write("success");
                }
                else if ("add".equals(methodName)) // 添加事件
                {
                    Event e = new Event();

                    e.setName(URLDecoder.decode(request.getParameter("eventName"), "UTF-8"));
                    e.setTypeId(Long.parseLong(request.getParameter("eventTypeId")));
                    e.setTypeName(URLDecoder.decode(request.getParameter("eventTypeName"), "UTF-8"));
                    e.setBeginDate(dateFormater.parse(request.getParameter("eventBeginDate")));// 将字符串格式成Date
                    e.setEndDate(dateFormater.parse(request.getParameter("eventEndDate")));
                    e.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));
                    if (Long.parseLong(request.getParameter("eventTypeId")) == 1)
                    {
                        e.setEpidemic(
                        EpidemicService.GetEpidemicById(Long.parseLong(request.getParameter("epidemicId"))));
                    }
                    eventService.SaveEvent(e);
                    response.getWriter().write("true");
                }else if ("addEvent".equals(methodName)) // 添加事件
                {
                    Event e = new Event();

                    e.setName(URLDecoder.decode(request.getParameter("eventName"), "UTF-8"));
                    e.setTypeId(Long.parseLong(request.getParameter("eventTypeId")));
                    e.setTypeName(URLDecoder.decode(request.getParameter("eventTypeName"), "UTF-8"));
                    e.setBeginDate(dateFormater.parse(request.getParameter("eventBeginDate")));// 将字符串格式成Date
                    e.setEndDate(dateFormater.parse(request.getParameter("eventEndDate")));
                    e.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));
                    if (Long.parseLong(request.getParameter("eventTypeId")) == 1)
                    {
                        e.setEpidemic(
                        EpidemicService.GetEpidemicById(Long.parseLong(request.getParameter("epidemicId"))));
                    }
                    eventService.SaveEvent(e);
                    Long keyId = Long.parseLong(URLDecoder.decode(request.getParameter("keywordId"), "UTF-8"));
                    eventService.SaveEventBind(e, keyId);
                    
                    response.getWriter().write(eventService.getEventInfo(e));
                }
                else if ("del".equals(methodName)) // 删除事件
                {
                    eventService.DelEvent(eventService.GetEventById(Long.parseLong(request.getParameter("id"))));
                    response.getWriter().write("true");
                }
                else if ("init".equals(methodName)) // 初始化事件
                {
                    Event e = eventService.GetEventById(Long.parseLong(request.getParameter("id")));
                    String str = "{\"Id\":\"" + e.getId() + "\",\"Name\":\"" + e.getName() + "\",\"TypeId\":\""
                            + e.getTypeId() + "\",\"BeginDate\":\"" + dateFormater.format(e.getBeginDate())
                            + "\",\"EndDate\":\"" + dateFormater.format(e.getEndDate()) + "\",\"Remark\":\""
                            + e.getRemark() + "\",\"TypeName\":\"" + e.getTypeName();
                    if (e.getTypeId() == 1)
                    {
                        Epidemic ep = EpidemicService.GetEpidemicById(e.getEpidemic().getId());
                        str += "\",\"EpidemicId\":\"" + ep.getId() + "\",\"EpidemicName\":\"" + ep.getTitle();
                    }
                    str += "\"}";
                    response.getWriter().write(str);
                }else if("initEvent".equals(methodName)){
                    Event e = eventService.GetEventById(Long.parseLong(request.getParameter("id")));
                    String ea = eventService.getEventAffectedDetailInfo(e);
                    String str = "{\"event\":"+eventService.getEventInfo(e)+", "+ea+"}";
                    response.getWriter().write(str);
                }
                else if ("Extract".equals(methodName))
                {
                    String eventId = request.getParameter("Id");
                    Event event = eventService.GetEventById(Long.parseLong(eventId));
                    DetectingSuggestService.delFromDetectingSuggest(event.getId());
                    if (event.getTypeId() == 1) // 疫情事件
                    {
                        List<EventAffectedArea> eventAffectedAreas = eventAffectedAreaService
                                .getEventAffectedAreas(event.getId());
                        List<EventAffectedProductType> eventAffectedProductTypes = eventAffectedProductTypeService
                                .getEventAffectedProductTypes(event.getId());
                        int length = eventAffectedProductTypes.size();
                        Long[] pt = new Long[length];
                        int i = 0;
                        for (EventAffectedProductType eventAffectedProductType : eventAffectedProductTypes)
                        {
                            if (eventAffectedProductType.getProductThirdTypeId() != null)
                                pt[i++] = eventAffectedProductType.getProductThirdTypeId();
                            else
                            {
                                if (eventAffectedProductType.getProductSecondTypeId() != null)
                                    pt[i++] = eventAffectedProductType.getProductSecondTypeId();
                                else
                                {
                                    pt[i++] = eventAffectedProductType.getProductFirstTypeId();
                                }
                            }
                        }
                        for (EventAffectedArea eventAffectedArea : eventAffectedAreas)
                        {
                            List<Product> products = ProductService
                                    .getProductListByTypeIdsAndCountryId(eventAffectedArea.getCountryId(), pt);
                            if (eventAffectedArea.getCountryId() != null)
                            {
                                for (Product product : products)
                                {
                                    if (product.getCountry() != null)
                                    {
                                        if (product.getCountry().getId().equals(eventAffectedArea.getCountryId()))
                                        {
                                            DetectingSuggest detectingSuggest = new DetectingSuggest();
                                            detectingSuggest.setEvent(event);
                                            detectingSuggest.setProduct(product);
                                            detectingSuggest.setSourceType(Constant.SOURCE_TYPE_INFO);
                                            detectingSuggest.setCreateTime(new Date());
                                            DetectingSuggestService.saveDetectingSuggest(detectingSuggest);
                                        }
                                    }
                                }
                            }
                            else
                            {
                                for (Product product : products)
                                {
                                    DetectingSuggest detectingSuggest = new DetectingSuggest();
                                    detectingSuggest.setEvent(event);
                                    detectingSuggest.setProduct(product);
                                    detectingSuggest.setSourceType(Constant.SOURCE_TYPE_INFO);
                                    detectingSuggest.setCreateTime(new Date());
                                    DetectingSuggestService.saveDetectingSuggest(detectingSuggest);
                                }
                            }
                        }
                    }
                    else if (event.getTypeId() == 2) // 网站事件
                    {
                        DetectingSuggestService.delFromDetectingSuggest(event.getId());
                        List<EventAffectedWebsite> eventAffectedWebsites = eventAffectedWebsiteService
                                .getEventAffectedWebsites(event.getId());
                        for (EventAffectedWebsite eventAffectedWebsite : eventAffectedWebsites)
                        {
                            List<Product> products = ProductService
                                    .getProductByEventWebsite(eventAffectedWebsite.getWebsiteId());
                            for (Product product : products)
                            {
                                DetectingSuggest detectingSuggest = new DetectingSuggest();
                                detectingSuggest.setEvent(event);
                                detectingSuggest.setProduct(product);
                                detectingSuggest.setSourceType(Constant.SOURCE_TYPE_INFO);
                                detectingSuggest.setCreateTime(new Date());
                                DetectingSuggestService.saveDetectingSuggest(detectingSuggest);
                            }
                        }
                    }
                    else if (event.getTypeId() == 3) // 品牌事件
                    {
                        DetectingSuggestService.delFromDetectingSuggest(event.getId());
                        List<EventAffectedBrand> eventAffectedBrands = eventAffectedBrandService
                                .getEventAffectedBrandsByEvent(event.getId());
                        for (EventAffectedBrand eventAffectedBrand : eventAffectedBrands)
                        {
                            List<ProductDefinition> productDefinitions = ProductDefinitionService
                                    .getProductDefinitionByBrand(eventAffectedBrand.getBrandId());
                            for (ProductDefinition productDefinition : productDefinitions)
                            {
                                List<Product> products = ProductService
                                        .getProductByProductDefinitionId(productDefinition.getId());
                                for (Product product : products)
                                {
                                    DetectingSuggest detectingSuggest = new DetectingSuggest();
                                    detectingSuggest.setEvent(event);
                                    detectingSuggest.setProduct(product);
                                    detectingSuggest.setSourceType(Constant.SOURCE_TYPE_INFO);
                                    detectingSuggest.setCreateTime(new Date());
                                    DetectingSuggestService.saveDetectingSuggest(detectingSuggest);
                                }
                            }
                        }
                    }
                    else if (event.getTypeId() == 4) // 地域事件
                    {
                        DetectingSuggestService.delFromDetectingSuggest(event.getId());
                        List<EventAffectedArea> eventAffectedAreas = eventAffectedAreaService
                                .getEventAffectedAreas(event.getId());
                        List<EventAffectedProductType> eventAffectedProductTypes = eventAffectedProductTypeService
                                .getEventAffectedProductTypes(event.getId());
                        // List<Product> products = ProductService.getProductList();
                        int length = eventAffectedProductTypes.size();
                        Long[] pt = new Long[length];
                        int i = 0;
                        for (EventAffectedProductType eventAffectedProductType : eventAffectedProductTypes)
                        {
                            if (eventAffectedProductType.getProductThirdTypeId() != null)
                                pt[i++] = eventAffectedProductType.getProductThirdTypeId();
                            else
                            {
                                if (eventAffectedProductType.getProductSecondTypeId() != null)
                                    pt[i++] = eventAffectedProductType.getProductSecondTypeId();
                                else
                                {
                                    pt[i++] = eventAffectedProductType.getProductFirstTypeId();
                                }
                            }
                           
                        }
                        for (EventAffectedArea eventAffectedArea : eventAffectedAreas)
                        {
                            List<Product> products = ProductService
                                    .getProductListByTypeIdsAndCountryId(eventAffectedArea.getCountryId(), pt);
                            if (eventAffectedArea.getCountryId() != null)
                            {
                                for (Product product : products)
                                {
                                    if (product.getCountry() != null)
                                    {
                                        if (product.getCountry().getId().equals(eventAffectedArea.getCountryId()))
                                        {
                                            DetectingSuggest detectingSuggest = new DetectingSuggest();
                                            detectingSuggest.setEvent(event);
                                            detectingSuggest.setProduct(product);
                                            detectingSuggest.setSourceType(Constant.SOURCE_TYPE_INFO);
                                            detectingSuggest.setCreateTime(new Date());
                                            DetectingSuggestService.saveDetectingSuggest(detectingSuggest);
                                        }
                                    }
                                }
                            }
                            else
                            {
                                for (Product product : products)
                                {
                                    DetectingSuggest detectingSuggest = new DetectingSuggest();
                                    detectingSuggest.setEvent(event);
                                    detectingSuggest.setProduct(product);
                                    detectingSuggest.setSourceType(Constant.SOURCE_TYPE_INFO);
                                    detectingSuggest.setCreateTime(new Date());
                                    DetectingSuggestService.saveDetectingSuggest(detectingSuggest);
                                }
                            }
                        }
                    }
                    else if (event.getTypeId() == 5) // 产品事件
                    {
                        DetectingSuggestService.delFromDetectingSuggest(event.getId());
                        List<EventAffectedProductType> eventAffectedProductTypes = eventAffectedProductTypeService
                                .getEventAffectedProductTypes(event.getId());
                        int length = eventAffectedProductTypes.size();
                        Long[] pt = new Long[length];
                        int i = 0;
                        for (EventAffectedProductType eventAffectedProductType : eventAffectedProductTypes)
                        {
                            if (eventAffectedProductType.getProductThirdTypeId() != null)
                                pt[i++] = eventAffectedProductType.getProductThirdTypeId();
                            else
                            {
                                if (eventAffectedProductType.getProductSecondTypeId() != null)
                                    pt[i++] = eventAffectedProductType.getProductSecondTypeId();
                                else
                                {
                                    pt[i++] = eventAffectedProductType.getProductFirstTypeId();
                                }
                            }
                           
                        }
                        List<Product> products = ProductService.getProductListByTypesIds(pt);

                        for (Product product : products)
                        {
                            DetectingSuggest detectingSuggest = new DetectingSuggest();
                            detectingSuggest.setEvent(event);
                            detectingSuggest.setProduct(product);
                            detectingSuggest.setSourceType(Constant.SOURCE_TYPE_INFO);
                            detectingSuggest.setCreateTime(new Date());
                            DetectingSuggestService.saveDetectingSuggest(detectingSuggest);
                        }
                    }
                    else if (event.getTypeId() == 6) // 配方原料事件
                    {
                        DetectingSuggestService.delFromDetectingSuggest(event.getId());
                        List<EventAffectedMaterial> eventAffectedMaterials = eventAffectedMaterialService
                                .getEventAffectedMaterials(event.getId());
                        int length = eventAffectedMaterials.size();
                        Long[] pt = new Long[length];
                        int i = 0;
                        for (EventAffectedMaterial material : eventAffectedMaterials)
                        {
                            if (material.getMaterialId() != null)
                                pt[i++] = material.getMaterialId();
                        }
                        List<Product> products = ProductService.getProductListByMaterialIds(pt);

                        for (Product product : products)
                        {
                            DetectingSuggest detectingSuggest = new DetectingSuggest();
                            detectingSuggest.setEvent(event);
                            detectingSuggest.setProduct(product);
                            detectingSuggest.setSourceType(Constant.SOURCE_TYPE_INFO);
                            detectingSuggest.setCreateTime(new Date());
                            DetectingSuggestService.saveDetectingSuggest(detectingSuggest);
                        }
                    }
                    response.getWriter().write("true");
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
