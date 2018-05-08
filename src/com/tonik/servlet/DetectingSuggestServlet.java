package com.tonik.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.Constant;
import com.tonik.model.ProductType;
import com.tonik.service.DetectingSuggestService;
import com.tonik.service.ProductTypeService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author yekai
 * @web.servlet name="detectingSuggestServlet"
 * @web.servlet-mapping url-pattern="/servlet/DetectingSuggestServlet"
 */
public class DetectingSuggestServlet extends BaseServlet
{
    private DetectingSuggestService DetectingSuggestService;
    private ProductTypeService ProductTypeService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        DetectingSuggestService = (DetectingSuggestService) ctx.getBean("DetectingSuggestService");
        ProductTypeService = (ProductTypeService) ctx.getBean("ProductTypeService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        /*
         * if (this.sessionCheck(request, response)) { response.getWriter().write("sessionOut"); return; }
         */
        //PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("MapList".equalsIgnoreCase(methodName))
                {
                    String sourceTypeId = request.getParameter("sourceTypeId");
                    String keyWord = URLDecoder.decode(request.getParameter("EventKeyWord"),"UTF-8");
                    String rangeLeft = request.getParameter("EvaluationRangeMin");
                    String rangeRight = request.getParameter("EvaluationRangeMax");
                    int rangeMin = "".equals(rangeLeft) ? 0 : Integer.parseInt(rangeLeft);
                    int rangeMax = "".equals(rangeRight) ? 0 : Integer.parseInt(rangeRight);
                    if (rangeMin > rangeMax)
                    {
                        int tmp = rangeMin;
                        rangeMin = rangeMax;
                        rangeMax = tmp;
                    }

                    String strSql = "";

                    String strSourceType = "";
                    if (!"0".equals(sourceTypeId) && !"".equals(sourceTypeId))
                    {
                        strSourceType = " and a.SOURCETYPE='" + sourceTypeId + "'";
                    }
                    if (request.getParameter("ProductTypeChoices") != "")
                    {
                        for (String item : (request.getParameter("ProductTypeChoices")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");

                            Long pt1 = Long.parseLong(itemArray[0]);
                            Long pt2 = Long.parseLong(itemArray[1]);
                            Long pt3 = Long.parseLong(itemArray[2]);
                            if (pt1 != 0 && pt2 == 0 && pt3 == 0)
                            {
                                strSql += " or (b.FIRST_TYPE=" + pt1 + strSourceType + ")";
                            }
                            if (pt1 != 0 && pt2 != 0 && pt3 == 0)
                            {
                                strSql += " or (b.SECOND_TYPE=" + pt2 + strSourceType + ")";
                            }
                            if (pt1 != 0 && pt2 != 0 && pt3 != 0)
                            {
                                strSql += " or (b.THIRD_TYPE=" + pt3 + strSourceType + ")";
                            }
                        }
                    }
                    /**
                     * sourceTypeId: 0：全部 1：舆情 2：诚信
                     */
                    if (!"0".equals(sourceTypeId) && !"".equals(sourceTypeId))
                    {
                        strSql += " and a.SOURCETYPE='" + sourceTypeId + "'";
                    }
                    int srctype = Integer.parseInt(sourceTypeId);
                    if ((srctype == 0 || srctype == 2) && rangeMax > 0)
                    {
                        strSql += " and b.WEBSITEGRADE * " + Constant.productGradeWebisteScale
                                + " + b.PRODUCTDEFINEGRADE * " + Constant.productGradeProductDefinitionScale
                                + " between " + rangeMin + " and " + rangeMax;
                    }
                    if ((srctype == 0 || srctype == 1) && !("".equals(keyWord.replaceAll(" ", ""))))
                        strSql += " and i.EVENT_NAME like '%" + keyWord + "%'";
                    int suggestNum = Integer.parseInt(
                            request.getParameter("suggestNum") == "" ? "1000" : request.getParameter("suggestNum"));
                    String jdj = DetectingSuggestService.getDashboardJson(strSql.replaceFirst("or", "and"), suggestNum,
                            keyWord);

                    response.getWriter().print(jdj);
                }
                else if("EvaluationList".equals(methodName)){
                    String rangeLeft = request.getParameter("EvaluationRangeMin");
                    String rangeRight = request.getParameter("EvaluationRangeMax");
                    int rangeMin = "".equals(rangeLeft) ? 0 : Integer.parseInt(rangeLeft);
                    int rangeMax = "".equals(rangeRight) ? 0 : Integer.parseInt(rangeRight);
                    if (rangeMin > rangeMax)
                    {
                        int tmp = rangeMin;
                        rangeMin = rangeMax;
                        rangeMax = tmp;
                    }
                    int suggestNum = Integer.parseInt(
                            request.getParameter("suggestNum") == "" ? "1000" : request.getParameter("suggestNum"));
                    String strSql= "";
                    if("".equals(rangeRight) && rangeMax > 0)
                        strSql += " and b.WEBSITEGRADE * " + Constant.productGradeWebisteScale
                            + " + b.PRODUCTDEFINEGRADE * " + Constant.productGradeProductDefinitionScale
                            + " between " + rangeMin + " and " + rangeMax;
                    if (request.getParameter("ProductTypeChoices") != "")
                    {
                        for (String item : (request.getParameter("ProductTypeChoices")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");

                            Long pt1 = Long.parseLong(itemArray[0]);
                            Long pt2 = Long.parseLong(itemArray[1]);
                            Long pt3 = Long.parseLong(itemArray[2]);
                            if (pt1 != 0 && pt2 == 0 && pt3 == 0)
                            {
                                strSql += " or (b.FIRST_TYPE=" + pt1 + ")";
                            }
                            if (pt1 != 0 && pt2 != 0 && pt3 == 0)
                            {
                                strSql += " or (b.SECOND_TYPE=" + pt2 + ")";
                            }
                            if (pt1 != 0 && pt2 != 0 && pt3 != 0)
                            {
                                strSql += " or (b.THIRD_TYPE=" + pt3 + ")";
                            }
                        }
                    }
                    String jdj = DetectingSuggestService.getDashEvaluationJson(strSql.replaceFirst("or", "and"), suggestNum);
                    response.getWriter().print(jdj);
                }
                else if ("productTypeInit".equals(methodName))
                {
                    String parentId = request.getParameter("parentId");
                    List<ProductType> ls = null;
                    if (parentId.isEmpty())
                    {
                        ls = ProductTypeService.getProductTypeByLevel(1);
                    }
                    else
                    {
                        ls = ProductTypeService.getProductTypeByParentId(Long.parseLong(parentId));
                    }
                    String strJson = "";
                    for (ProductType item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strJson != "")
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                    }
                    response.getWriter().print("{\"StyleList\":[" + strJson + "]}");
                }
                else if ("exportExcel".equals(methodName))
                {
                    String sourceTypeId = request.getParameter("sourceTypeId");
                    String keyWord = URLDecoder.decode(request.getParameter("EventKeyWord"),"UTF-8");
                    String rangeLeft = request.getParameter("EvaluationRangeMin");
                    String rangeRight = request.getParameter("EvaluationRangeMax");
                    int rangeMin = "".equals(rangeLeft) ? 0 : Integer.parseInt(rangeLeft);
                    int rangeMax = "".equals(rangeRight) ? 0 : Integer.parseInt(rangeRight);
                    if (rangeMin > rangeMax)
                    {
                        int tmp = rangeMin;
                        rangeMin = rangeMax;
                        rangeMax = tmp;
                    }

                    String strSql = "";

                    String strSourceType = "";
                    if (!"0".equals(sourceTypeId) && !"".equals(sourceTypeId))
                    {
                        strSourceType = " and a.SOURCETYPE='" + sourceTypeId + "'";
                    }
                    if (request.getParameter("ProductTypeChoices") != "")
                    {
                        for (String item : (request.getParameter("ProductTypeChoices")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");

                            Long pt1 = Long.parseLong(itemArray[0]);
                            Long pt2 = Long.parseLong(itemArray[1]);
                            Long pt3 = Long.parseLong(itemArray[2]);
                            if (pt1 != 0 && pt2 == 0 && pt3 == 0)
                            {
                                strSql += " or (b.FIRST_TYPE=" + pt1 + strSourceType + ")";
                            }
                            if (pt1 != 0 && pt2 != 0 && pt3 == 0)
                            {
                                strSql += " or (b.SECOND_TYPE=" + pt2 + strSourceType + ")";
                            }
                            if (pt1 != 0 && pt2 != 0 && pt3 != 0)
                            {
                                strSql += " or (b.THIRD_TYPE=" + pt3 + strSourceType + ")";
                            }
                        }
                    }
                    if (!"0".equals(sourceTypeId) && !"".equals(sourceTypeId))
                    {
                        strSql += " and a.SOURCETYPE='" + sourceTypeId + "'";
                    }
                    int srctype = Integer.parseInt(sourceTypeId);
                    if ((srctype == 0) && rangeMax > 0)
                    {
                        strSql += " and b.WEBSITEGRADE * " + Constant.productGradeWebisteScale
                                + " + b.PRODUCTDEFINEGRADE * " + Constant.productGradeProductDefinitionScale
                                + " between " + rangeMin + " and " + rangeMax;
                    }
                    if ((srctype == 0 || srctype == 1) && !("".equals(keyWord.replaceAll(" ", ""))))
                        strSql += " and i.EVENT_NAME like '%" + keyWord + "%'";
                    int suggestNum = Integer.parseInt(
                            request.getParameter("suggestNum") == "" ? "1000" : request.getParameter("suggestNum"));
                    int form = Integer.parseInt(request.getParameter("viewForm"));
                    //excel形式输出显示
                    if(form == 1){
                        response.setHeader("Content-Disposition",
                                "inline;filename=" + new String("样品检测建议清单".getBytes("gb2312"), "ISO8859-1") + ".xls");
                        response.setContentType("application/octet-stream");
                        OutputStream os = response.getOutputStream();
                        exportExcel("样品检测建议清单", DetectingSuggestService.getDetectingSuggestList(strSql.replaceFirst("or", "and"), suggestNum), os, 1);
                        os.close();
                    }
                    //表格形式显示
                    else if(form == 2){
                        String str = "";
                        List<Object[]> dss = DetectingSuggestService.getDetectingSuggestList(strSql.replaceFirst("or", "and"), suggestNum);
                        for(Object[] ds:dss){
                            str += DetectingSuggestService.getDetectingSuggestJsonInfo(ds, 1)+",";
                        }
                        if (str.length() > 0){
                            str = str.substring(0, str.length() - 1);
                        }
                        response.getWriter().print("{\"webList\":[" + str + "]}");
                    }
                }
                else if("exportEvaluationExcel".equals(methodName)){
                    String rangeLeft = request.getParameter("EvaluationRangeMin");
                    String rangeRight = request.getParameter("EvaluationRangeMax");
                    int rangeMin = "".equals(rangeLeft) ? 0 : Integer.parseInt(rangeLeft);
                    int rangeMax = "".equals(rangeRight) ? 0 : Integer.parseInt(rangeRight);
                    if (rangeMin > rangeMax)
                    {
                        int tmp = rangeMin;
                        rangeMin = rangeMax;
                        rangeMax = tmp;
                    }
                    int suggestNum = Integer.parseInt(
                            request.getParameter("suggestNum") == "" ? "1000" : request.getParameter("suggestNum"));
                    String strSql= "";
                    if("".equals(rangeRight) && rangeMax > 0)
                        strSql += " and b.WEBSITEGRADE * " + Constant.productGradeWebisteScale
                            + " + b.PRODUCTDEFINEGRADE * " + Constant.productGradeProductDefinitionScale
                            + " between " + rangeMin + " and " + rangeMax;
                    if (request.getParameter("ProductTypeChoices") != "")
                    {
                        for (String item : (request.getParameter("ProductTypeChoices")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");

                            Long pt1 = Long.parseLong(itemArray[0]);
                            Long pt2 = Long.parseLong(itemArray[1]);
                            Long pt3 = Long.parseLong(itemArray[2]);
                            if (pt1 != 0 && pt2 == 0 && pt3 == 0)
                            {
                                strSql += " or (b.FIRST_TYPE=" + pt1 + ")";
                            }
                            if (pt1 != 0 && pt2 != 0 && pt3 == 0)
                            {
                                strSql += " or (b.SECOND_TYPE=" + pt2 + ")";
                            }
                            if (pt1 != 0 && pt2 != 0 && pt3 != 0)
                            {
                                strSql += " or (b.THIRD_TYPE=" + pt3 + ")";
                            }
                        }
                    }
                    int form = Integer.parseInt(request.getParameter("viewForm"));
                    //excel形式输出显示
                    if(form == 1){
                        response.setHeader("Content-Disposition",
                                "inline;filename=" + new String("样品检测建议清单".getBytes("gb2312"), "ISO8859-1") + ".xls");
                        response.setContentType("application/octet-stream");
                        
                        OutputStream os = response.getOutputStream();
                        exportExcel("样品检测建议清单", DetectingSuggestService.getEvaluationList(strSql.replaceFirst("or", "and"), suggestNum), os, 2);
                        os.close();
                    }
                    //表格形式显示
                    else if(form == 2){
                        String str = "";
                        List<Object[]> dss = DetectingSuggestService.getEvaluationList(strSql.replaceFirst("or", "and"), suggestNum);
                        for(Object[] ds:dss){
                            str += DetectingSuggestService.getDetectingSuggestJsonInfo(ds, 2)+",";
                        }
                        if (str.length() > 0){
                            str = str.substring(0, str.length() - 1);
                        }
                        response.getWriter().print("{\"webList\":[" + str + "]}");
                    }
                }
            } catch (Exception e)
            {
                response.getWriter().print("false");
            }
        }
    }

    protected void exportExcel(String title, List mapList, OutputStream out, int type)
    {
        // 声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字符
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式，用来设置标题样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式,用于设置内容样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        String []headers;
        if(type == 2)
            headers = new String[] { "商品名", "网站名称", "网站路径", "来源类型", "商品品牌", "商品产地" };
        else
            headers = new String[] { "商品名", "网站名称", "网站路径", "来源类型", "来源内容", "商品品牌", "商品产地" };
        for (int i = 0; i < headers.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        int i = 0;
        if(type == 2){
            for (Iterator iterator = mapList.iterator(); iterator.hasNext();)
            {
                row = sheet.createRow(i + 1);
                int j = 0;
                Object[] obj = (Object[]) iterator.next();
                row.createCell(j++).setCellValue(val(obj[0]));
                row.createCell(j++).setCellValue(val(obj[1]));
                row.createCell(j++).setCellValue(val(obj[2]));
                row.createCell(j++).setCellValue("诚信评价");
                row.createCell(j++).setCellValue(val(obj[3]));
                row.createCell(j++).setCellValue(val(obj[4]));
                i++;
            }
        }else{
            for (Iterator iterator = mapList.iterator(); iterator.hasNext();)
            {
                row = sheet.createRow(i + 1);
                int j = 0;
                Object[] obj = (Object[]) iterator.next();
                row.createCell(j++).setCellValue(val(obj[0]));
                row.createCell(j++).setCellValue(val(obj[1]));
                row.createCell(j++).setCellValue(val(obj[2]));
                row.createCell(j++).setCellValue("舆情关注");
                row.createCell(j++).setCellValue(val(obj[4]));
                row.createCell(j++).setCellValue(val(obj[5]));
                row.createCell(j++).setCellValue(val(obj[6]));
                i++;
            }
        }
        
        // for (int i = 0; i < mapList.size(); i++)
        // {
        // DetectingSuggest map = mapList.get(i);
        // row = sheet.createRow(i + 1);
        // int j = 0;
        //
        // row.createCell(j++).setCellValue(map.getProduct().getName().toString());
        // row.createCell(j++).setCellValue(map.getProduct().getWebsitename().toString());
        // row.createCell(j++).setCellValue(map.getProduct().getLocation().toString());
        // row.createCell(j++).setCellValue(map.getSourceContent());
        // row.createCell(j++).setCellValue(map.getProduct().getBrand());
        // row.createCell(j++).setCellValue(map.getProduct().getArea().getName());
        // }
        try
        {
            workbook.write(out);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    String val(Object obj){
        if(obj != null){
            return obj.toString();
        }else{
            return "";
        }
    }
}
