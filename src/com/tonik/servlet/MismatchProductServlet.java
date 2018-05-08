package com.tonik.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thinvent.utils.DateUtil;
import com.thinvent.utils.JsonUtil;
import com.thinvent.utils.WebUtil;
import com.tonik.Constant;
import com.tonik.model.DRLFile;
import com.tonik.service.MismatchProductService;
import com.tonik.service.ProductService;
import com.tonik.service.WebsiteService;
import com.tonik.standard.rules.Entity;
import com.tonik.standard.rules.WordSimilarity;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 不匹配标准的商品 Servlet
 * </p>
 * @since Apr 19, 2016
 * @version 1.0
 * @author liuyu
 * @update 巩文华 20170328 请求方法QueryList中 添加风险商品请求返回数据的字段RiskLevel，RiskSuggest
 * @web.servlet name="mismatchProductServlet"
 * @web.servlet-mapping url-pattern="/servlet/MismatchProductServlet"
 */
public class MismatchProductServlet extends BaseServlet
{
    private static final long serialVersionUID = 1L;
    private MismatchProductService mismatchProductService;
    private ProductService productService;
    private WebsiteService websiteService;
    private SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式


    /**
     * @Project OverseaSensePlatform
     * @ClassName RISK_LEVEL
     * @Desc 产品风险等级
     * @Author Gong WenHua
     * @Date 2017年3月28日 下午3:54:07
     */
    private static enum RISK_LEVEL
    {
        LOW(1, 5, "抽检"), MID(2, 10, "进一步核实"), HIG(3, 20, "下架");

        private final int level;
        private final String suggest;
        private final int rightVal;


        /**
         * <p>
         * <b>Constructor: </b>
         * </p>
         * RISK_LEVEL
         * @param rightVal 最大值
         * @param suggest 建议
         */
        private RISK_LEVEL(int level, int rightVal, String suggest)
        {

            this.level = level;
            this.rightVal = rightVal;
            this.suggest = suggest;
        }

        public int getLevel()
        {
            return this.level;
        }

        public String getSuggest()
        {
            return this.suggest;
        }

        public int getRigthVal()
        {
            return this.rightVal;
        }

    }


    /**
     * @Desc 获取商品风险等级
     * @param mismatchNum
     * @param materialNum
     * @return
     */
    private static RISK_LEVEL getRiskLevel(int mismatchNum, int materialNum)
    {
        if (mismatchNum <= RISK_LEVEL.LOW.getRigthVal())
            return RISK_LEVEL.LOW;
        if (mismatchNum <= RISK_LEVEL.MID.getRigthVal())
            return RISK_LEVEL.MID;
        if (mismatchNum <= RISK_LEVEL.HIG.getRigthVal())
            return RISK_LEVEL.HIG;
        return RISK_LEVEL.HIG;
    }


    WebApplicationContext ctx;


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        mismatchProductService = (MismatchProductService) ctx.getBean("MismatchProductService");
        productService = (ProductService) ctx.getBean("ProductService");
        websiteService = (WebsiteService) ctx.getBean("WebsiteService");
        System.setProperty("jxl.encoding", "UTF-8");
        WordSimilarity.loadGlossary();
    }

    /**
     * @Desc
     * @update 巩文华 20170328 请求方法QueryList中 添加风险商品请求返回数据的字段RiskLevel，RiskSuggest
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))
                {
                    // 加载不匹配商品分页列表
                    String module = URLDecoder.decode(request.getParameter("module"), "UTF-8");
                    int pageSize = Integer.parseInt(URLDecoder.decode(request.getParameter("pageSize"), "UTF-8"));
                    int pageIndex = Integer.parseInt(URLDecoder.decode(request.getParameter("pageIndex"), "UTF-8"));
                    int choiceLength = Integer
                            .parseInt(URLDecoder.decode(request.getParameter("choiceLength"), "UTF-8"));
                    String choice = URLDecoder.decode(request.getParameter("choices"), "UTF-8");
                    String orderBy = request.getParameter("orderBy");
                    String orderType = request.getParameter("orderType");

                    Long[] ptl = new Long[choiceLength];

                    if (choiceLength > 0)
                    {
                        int i = 0;
                        for (String item : choice.split("<>"))
                        {
                            String[] itemArray = item.split("\\|");

                            Long pt1 = Long.parseLong(itemArray[0]);
                            Long pt2 = Long.parseLong(itemArray[1]);
                            Long pt3 = Long.parseLong(itemArray[2]);

                            if (pt3 != 0)
                            {
                                ptl[i++] = pt3;
                            }
                            else if (pt2 != 0)
                            {
                                ptl[i++] = pt2;
                            }
                            else if (pt1 != 0)
                            {
                                ptl[i++] = pt1;
                            }
                        }
                    }
                    List<Object[]> mismatchProduct = new ArrayList<Object[]>();
                    int mismatchProductTotal = 0;

                    if (module.equals("All"))
                    {
                        mismatchProduct = mismatchProductService.getMismatchProductPaging(pageIndex, pageSize, ptl, -1l,
                                orderBy, orderType);
                        mismatchProductTotal = mismatchProductService.getMismatchProductTotal(ptl, -1l);
                    }
                    else if (module.equals("ByStandardId"))
                    {
                        String standardIds = URLDecoder.decode(request.getParameter("standardId"), "UTF-8");
                        String[] standardList = standardIds.split("\\|");
                        Long[] standardIdList = new Long[standardList.length];
                        for (int i = 0; i < standardList.length; i++)
                        {
                            standardIdList[i] = Long.parseLong(standardList[i].toString());
                        }
                        mismatchProduct = mismatchProductService.getMismatchProductPaging(pageIndex, pageSize, ptl,
                                standardIdList, orderBy, orderType);
                        mismatchProductTotal = mismatchProductService.getMismatchProductTotal(ptl, standardIdList);
                    }

                    String dataList = "";
                    for (Object[] obj : mismatchProduct)
                    {
                        String mismatchContent = (String) obj[4];
                        if (mismatchContent.endsWith("||"))
                        {
                            mismatchContent = mismatchContent.substring(0, mismatchContent.length() - 2);
                        }
                        String date = dateFormater.format(obj[5]);
                        RISK_LEVEL riskLevel = getRiskLevel(((Integer) obj[7]).intValue(), 0);

                        dataList += "{\"Id\":\"" + obj[0] + "\",\"ProductName\":\"" + obj[1] + "\",\"WebName\":\""
                                + obj[2] + "\",\"Url\":\"" + obj[3] + "\",\"MismatchContent\":\"" + mismatchContent
                                + "\",\"CreateTime\":\"" + date + "\",\"StandardName\":\"" + obj[6]
                                + "\",\"RiskLevel\":\"" + riskLevel.getLevel() + "\",\"RiskSuggest\":\""
                                + riskLevel.getSuggest() + "\"},";
                    }

                    if (dataList != "")
                    {
                        dataList = dataList.substring(0, dataList.length() - 1);
                    }

                    response.getWriter()
                            .write("{\"Total\":\"" + mismatchProductTotal + "\",\"DataList\":[" + dataList + "]}");
                }
                else if ("Del".equals(methodName))
                {// 删除某记录
                    String id = request.getParameter("id");
                    mismatchProductService.delMismatchProduct(id);
                    response.getWriter().write("true");
                }
                else if ("DelMul".equals(methodName))
                {// 删除多条记录
                    String id = request.getParameter("idList");
                    String[] idList = id.split("\\|");
                    mismatchProductService.delMultiMismatchProduct(idList);
                    response.getWriter().write("true");
                }
                else if ("InitContent".equals(methodName))
                {// 查找记录
                    Long id = Long.parseLong(request.getParameter("id"));
                    String mismatchContent = mismatchProductService.getMismatchContentById(id);
                    if (mismatchContent.endsWith("||"))
                    {
                        mismatchContent = mismatchContent.substring(0, mismatchContent.length() - 2);
                    }
                    response.getWriter().write("{\"MismatchContent\":\"" + mismatchContent + "\"}");
                }
                else if ("SimilarStandard".equals(methodName))
                {// 查找相似产品
                    Long id = Long.parseLong(request.getParameter("id"));
                    int pageSize = Integer.parseInt(request.getParameter("pageSize"));
                    int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
                    List<Object[]> mismatchProduct = mismatchProductService.getSimilarProductPaging(pageIndex, pageSize,
                            id);
                    int mismatchProductTotal = mismatchProductService.getSimilarProductTotal(id);

                    String dataList = "";
                    for (Object[] obj : mismatchProduct)
                    {
                        String mismatchContent = (String) obj[4];
                        if (mismatchContent.endsWith("||"))
                        {
                            mismatchContent = mismatchContent.substring(0, mismatchContent.length() - 2);
                        }
                        dataList += "{\"Id\":\"" + obj[0] + "\",\"ProductName\":\"" + obj[1] + "\",\"WebName\":\""
                                + obj[2] + "\",\"Url\":\"" + obj[3] + "\",\"MismatchContent\":\"" + mismatchContent
                                + "\"},";
                    }

                    if (dataList != "")
                    {
                        dataList = dataList.substring(0, dataList.length() - 1);
                    }
                    response.getWriter()
                            .write("{\"Total\":\"" + mismatchProductTotal + "\",\"DataList\":[" + dataList + "]}");
                }
                else if ("Loading".equals(methodName))
                {// 将不符合规则的商品加载到MISMATCH_PRODUCT表中
                    mismatchProductService.loadingMismatchProducts(ctx);
                }
                else if ("StandardGrade".equals(methodName))
                {// 获得实时检测的数量统计
                    String websiteTotal = websiteService.getWebsiteTotal(); // 演示用例
                    String productTotal = productService.getProductTotal();
                    int standardTotal = mismatchProductService.getStandardTotal();
                    int misMatchProductTotal = mismatchProductService.getMismatchProductTotal();
                    String strReturn = "{\"WebsiteTotal\":\"" + websiteTotal + "\",\"ProductTotal\":\"" + productTotal
                            + "\",\"StandardTotal\":\"" + standardTotal + "\",\"MisMatchProductTotal\":\""
                            + misMatchProductTotal + "\"}";
                    response.getWriter().write(strReturn);
                }
                else if ("GetCountry".equals(methodName))
                {
                    List<Object[]> mismatchMap = mismatchProductService.getMismatchMap();
                    String dataList = "";
                    for (Object[] object : mismatchMap)
                    {
                        dataList += "{\"Name\":\"" + object[0] + "\",\"Value\":\"" + object[1] + "\"},";
                    }
                    if (dataList.length() > 0)
                    {
                        dataList = dataList.substring(0, dataList.length() - 1);
                    }
                    response.getWriter().write("{\"DataList\":[" + dataList + "]}");
                }
                else if ("EditRule".equals(methodName))
                {// 编辑规则修改DRL文件
                    String ruleName = URLDecoder.decode(request.getParameter("ruleName"), "UTF-8");
                    String ruleType = URLDecoder.decode(request.getParameter("ruleType"), "UTF-8");
                    ruleName = ruleName + "__" + ruleType;
                    String entity = URLDecoder.decode(request.getParameter("entity"), "UTF-8");// 目前编辑不允许修改规则名
                    // String entity = "";
                    String composition = URLDecoder.decode(request.getParameter("composition"), "UTF-8");
                    String unit = URLDecoder.decode(request.getParameter("unit"), "UTF-8");
                    String standard = URLDecoder.decode(request.getParameter("standard"), "UTF-8");
                    String condition = URLDecoder.decode(request.getParameter("condition"), "UTF-8");
                    String num1 = URLDecoder.decode(request.getParameter("num1"), "UTF-8");
                    String num2 = URLDecoder.decode(request.getParameter("num2"), "UTF-8");
                    String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes");// drl文件目录路径
                    String fileName = "products-standard_val-1.drl";
                    Long id = 1l;// 例
                    // mismatchProductService.getDRLFile(filePath, fileName, id);//从DB中取出文件，并创建在缓存文件夹中
                    String drlContent = mismatchProductService.read(filePath + "/" + fileName, ruleName, "edit",
                            ruleType, entity, composition, unit, standard, condition, num1, num2);
                    mismatchProductService.write(filePath + "/" + fileName, drlContent);
                    mismatchProductService.saveDRLFile(filePath + "/" + fileName, id);
                    mismatchProductService.reDeployRules(ctx, drlContent);
                    response.getWriter().write("true");
                }
                else if ("DelRule".equals(methodName))
                {// 删除规则修改DRL文件
                    String ruleName = URLDecoder.decode(request.getParameter("ruleName"), "UTF-8");
                    String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes");// drl文件目录路径
                    String fileName = "products-standard_val-1.drl";
                    Long id = 1l;// 例
                    // mismatchProductService.getDRLFile(filePath, fileName, id);//从DB中取出文件，并创建在缓存文件夹中
                    String drlContent = mismatchProductService.read(filePath + "/" + fileName, ruleName, "del",
                            "ruleType", "entity", "composition", "unit", "standard", "condition", "num1", "num2");
                    mismatchProductService.write(filePath + "/" + fileName, drlContent);
                    mismatchProductService.saveDRLFile(filePath + "/" + fileName, id);
                    mismatchProductService.reDeployRules(ctx, drlContent);
                    response.getWriter().write("true");
                }
                else if ("AddRule".equals(methodName))
                {// 新增规则
                    String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes");// drl文件目录路径
                    String ruleName = URLDecoder.decode(request.getParameter("ruleName"), "UTF-8");
                    String ruleType = URLDecoder.decode(request.getParameter("ruleType"), "UTF-8");
                    ruleName = ruleName + "__" + ruleType;
                    String entity = URLDecoder.decode(request.getParameter("entity"), "UTF-8");
                    String composition = URLDecoder.decode(request.getParameter("composition"), "UTF-8");
                    String unit = URLDecoder.decode(request.getParameter("unit"), "UTF-8");
                    String standard = URLDecoder.decode(request.getParameter("standard"), "UTF-8");
                    String condition = URLDecoder.decode(request.getParameter("condition"), "UTF-8");
                    String num1 = URLDecoder.decode(request.getParameter("num1"), "UTF-8");
                    String num2 = URLDecoder.decode(request.getParameter("num2"), "UTF-8");
                    String fatherClass = URLDecoder.decode(request.getParameter("fatherClass"), "UTF-8");
                    String fileName = "products-standard_val-1.drl";
                    Long id = 1l;// 例
                    // 父类规则
                    if (Integer.parseInt(ruleType) == 2)
                    {
                        List<String> str = mismatchProductService.getRule(filePath + "/" + fileName, fatherClass);
                        entity = str.get(1);
                        composition = str.get(2);
                        unit = str.get(3);
                        standard = str.get(4);
                        condition = str.get(5);
                        num1 = str.get(6);
                        num2 = str.get(7);
                    }
                    // mismatchProductService.getDRLFile(filePath, fileName, id);//从DB中取出文件，并创建在缓存文件夹中
                    String drlContent = mismatchProductService.addRule(filePath + "/" + fileName, ruleName, "add",
                            ruleType, entity, composition, unit, standard, condition, num1, num2);
                    mismatchProductService.write(filePath + "/" + fileName, drlContent);
                    mismatchProductService.saveDRLFile(filePath + "/" + fileName, id);
                    mismatchProductService.reDeployRules(ctx, drlContent);
                    response.getWriter().write("true");
                }
                else if ("ViewRule".equals(methodName))
                {// 查看规则
                    String ruleName = URLDecoder.decode(request.getParameter("ruleName"), "UTF-8");
                    String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes");// drl文件目录路径
                    String fileName = "products-standard_val-1.drl";
                    String ruleType = null;
                    Long id = 1l;// 例
                    // mismatchProductService.getDRLFile(filePath, fileName, id);//从DB中取出文件，并创建在缓存文件夹中
                    List<String> str = mismatchProductService.getRule(filePath + "/" + fileName, ruleName);
                    String dataList = "";
                    try
                    {
                        if (ruleName.split("__")[1] == null || !ruleName.split("__")[1].isEmpty())
                        {
                            ruleType = ruleName.split("__")[1];
                        }
                    } catch (Exception e)
                    {
                        ruleType = "0";
                    }
                    dataList += "{\"Name\":\"" + ruleName + "\",\"RuleType\":\"" + ruleType + "\",\"Entity\":\""
                            + str.get(1) + "\",\"Composition\":\"" + str.get(2) + "\",\"Unit\":\"" + str.get(3)
                            + "\",\"Standard\":\"" + str.get(4) + "\",\"Condition\":\"" + str.get(5) + "\",\"Num1\":\""
                            + str.get(6) + "\",\"Num2\":\"" + str.get(7) + "\"},";
                    // for (String[] item : str)
                    // {
                    // dataList += "{\"Name\":\"" + str[0] + "\",\"Value\":\"" + str[1] + "\"},";
                    // }
                    if (dataList.length() > 0)
                    {
                        dataList = dataList.substring(0, dataList.length() - 1);
                    }
                    response.getWriter().write("{\"DataList\":[" + dataList + "]}");
                    // str.toString();
                }
                else if ("Download".equals(methodName))
                {// 下载DRL文件
                    getDRLFile(request, response);
                }
                else if ("RuleList".equals(methodName))
                {// 查看规则名列表
                    String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes");// drl文件目录路径
                    Long standard = Long.parseLong(request.getParameter("standard"));
                    String fileName = "products-standard_val-1.drl";
                    List<String> ruleList = mismatchProductService.getRuleList(filePath + "/" + fileName, standard);
                    String dataList = "";
                    for (String item : ruleList)
                    {
                        String[] str = item.split("__");
                        dataList += "{\"Name\":\"" + str[0] + "\"},";
                    }
                    if (dataList.length() > 0)
                    {
                        dataList = dataList.substring(0, dataList.length() - 1);
                    }
                    response.getWriter().write("{\"DataList\":[" + dataList + "]}");
                }
                else if ("EntityList".equals(methodName))
                {// 查看Entity列表
                    String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes");// drl文件目录路径
                    String fileName = "products-standard_val-1.dsl";
                    List<String> ruleList = mismatchProductService.getEntityList(filePath + "/" + fileName);
                    String dataList = "";
                    for (String item : ruleList)
                    {
                        dataList += "{\"Name\":\"" + item + "\"},";
                    }
                    if (dataList.length() > 0)
                    {
                        dataList = dataList.substring(0, dataList.length() - 1);
                    }
                    response.getWriter().write("{\"DataList\":[" + dataList + "]}");
                }
                else if ("Test".equals(methodName))
                {// 验证规则
                    String productName = URLDecoder.decode(request.getParameter("productName"), "UTF-8");
                    String material = URLDecoder.decode(request.getParameter("material"), "UTF-8");
                    List<Object[]> objs = new ArrayList<Object[]>();
                    for (String item : material.split("<>"))
                    {
                        String[] itemArray = item.split("\\|");
                        Object[] obj = { itemArray[0], itemArray[1], itemArray[2] };
                        objs.add(obj);
                    }
                    String testResult = mismatchProductService.getTestResult(ctx, productName, objs);
                    response.getWriter().write(testResult);
                }
                // 风险智能化报表
                else if ("MismatchForm".equals(methodName))
                {
                    Integer dataSource = Integer.parseInt(request.getParameter("dataSource"));
                    Integer chartType = Integer.parseInt(request.getParameter("chartType"));
                    Integer number = Integer.parseInt(request.getParameter("number"));
                    Date startTime = DateUtil.parseDate(DateUtil.STANDARD_FORMAT, request.getParameter("startTime"));
                    Date endTime = DateUtil.parseDate(DateUtil.STANDARD_FORMAT, request.getParameter("endTime"));

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(endTime);
                    cal.set(Calendar.HOUR, 23);
                    cal.set(Calendar.MINUTE, 59);
                    cal.set(Calendar.SECOND, 59);
                    endTime = cal.getTime();

                    String strReturn = mismatchProductService.getMismatchForm(dataSource, chartType, number, startTime,
                            endTime);
                    response.getWriter().write(strReturn);
                }
                else if ("workFlowPic".equals(methodName))
                {
                    String workFlowPicUrl = getWorkFlowPicUrl();
                    HashMap<String, String> res = new HashMap<String, String>();
                    res.put("Url", workFlowPicUrl);
                    WebUtil.writeJSON(response, res);
                }
                else if ("websiteEvaluation".equals(methodName))
                {
                    String resultl = mismatchProductService.listWebsiteEvaluation(
                            Integer.parseInt(request.getParameter("pageIndex")),
                            Integer.parseInt(request.getParameter("pageSize")));
                    response.getWriter().write(resultl);
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }

    /**
     * @desc 文件下载
     * @param request
     * @param response
     * @throws Exception
     */
    private void getDRLFile(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Long fileId = 1l;
        DRLFile drlFile = mismatchProductService.getDRLFile(fileId);

        response.reset();
        // 设置文件MIME类型
        // response.setContentType(getServletContext().getMimeType(document.getFilename()));
        response.setContentType(new MimetypesFileTypeMap().getContentType(new File(drlFile.getFilename())));
        // 设置Content-Disposition
        response.setHeader("Content-Disposition",
                "attachment;filename=\"" + new String(drlFile.getFilename().getBytes(), "iso-8859-1") + "\"");
        // 通过response将目标文件写到客户端
        OutputStream out = response.getOutputStream();
        // 获取文件的二进制数据
        byte[] buffer = drlFile.getContent();
        out.write(buffer);
        out.close();
    }

    private String getWorkFlowPicUrl()
    {
        int mismatchProductNum = mismatchProductService.getMismatchProductTotal();
        String fileName = "";
        String url = "";
        if (mismatchProductNum <= 500)
        {
            fileName = "risk_workflow.png";
        }
        else if (mismatchProductNum > 500 && mismatchProductNum <= 1000)
        {
            fileName = "risk_workflow_500.png";
        }
        else if (mismatchProductNum > 1000 && mismatchProductNum <= 2000)
        {
            fileName = "risk_workflow_1000.png";
        }
        else
        {
            fileName = "risk_workflow_2000.png";
        }
        url = "images/" + fileName;
        return url;

    }
}
