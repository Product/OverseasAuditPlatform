package com.tonik.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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

import com.tonik.model.Extraction;
import com.tonik.service.ExtractionService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nmf
 * @web.servlet name="excelExportServlet"
 * @web.servlet-mapping url-pattern="/servlet/ExcelExportServlet"
 */

public class ExcelExportServlet extends HttpServlet
{
    private ExtractionService ExtractionService;
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ExtractionService = (ExtractionService)ctx.getBean("ExtractionService");
    }
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       // response.setContentType("octets/stream");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
       // response.setContentType("text/html;charset=UTF-8");
        String strQuery = request.getParameter("strQuery");
        String strStraTime = request.getParameter("strStraTime");
        String strEndTime = request.getParameter("strEndTime");
        String excelName = "按规则抽取数据";
        response.setHeader("Content-Disposition", "inline;filename="+new String(excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");
        response.setContentType("application/octet-stream");
        String[] headers = new String[]{"商品名","所用规则","商品品牌","商品价格"};
        try {  
            //OutputStream out = new FileOutputStream("E://"+excelName+".xls");
            OutputStream out = response.getOutputStream();  
            exportExcel(excelName,headers, getList(strQuery,strStraTime,strEndTime), out,"yyyy-MM-dd");
            out.close();
        }catch (FileNotFoundException e) {  
                e.printStackTrace();  
        }catch (IOException e) {  
                e.printStackTrace();  
        }
    }
    public List<Map<String,Object>> getList(String strQuery, String strStraTime,String strEndTime){  
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();  
        List<Extraction> ls=new ArrayList<Extraction>();
        if(strQuery=="")
        {
            ls=ExtractionService.getExtractionList();
        }
        else
        {
            if ("".equals(strStraTime))
            {
                strStraTime = "1980-01-01 00:00:01";
            }
            if ("".equals(strEndTime))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                strEndTime = df.format(new Date());
            }
            ls=ExtractionService.ExtractionSpecial(strQuery, strStraTime, strEndTime);
        }
            for(Extraction item:ls)
            {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("ProductName", item.getProduct().getName());
                map.put("Rules", item.getRules().getName());
                map.put("ProductBrand", item.getProduct().getBrand());
                map.put("ProductPrice", item.getProduct().getPrice());
                list.add(map); 
            } 
        return list;  
    }
    @SuppressWarnings("unchecked")
    protected void exportExcel(String title,String[] headers,List mapList,OutputStream out,String pattern)
    {
      //声明一个工作簿  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        //生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        //设置表格默认列宽度为15个字符  
        sheet.setDefaultColumnWidth(20);  
        //生成一个样式，用来设置标题样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        //设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        //把字体应用到当前的样式  
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
        //产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for(int i = 0; i<headers.length;i++){  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
        for (int i=0;i<mapList.size();i++) {
            Map<String,Object> map = (Map<String, Object>) mapList.get(i);  
            row = sheet.createRow(i+1);  
            int j = 0;  
            //Object value = null;
            row.createCell(j++).setCellValue(map.get("ProductName").toString());
            row.createCell(j++).setCellValue(map.get("Rules").toString());
            row.createCell(j++).setCellValue(map.get("ProductBrand").toString());
//            value=map.get("ProductPrice");  
//            if(value instanceof Integer){  
//                row.createCell(j++).setCellValue(String.valueOf(value));  
//            } 
            row.createCell(j++).setCellValue(map.get("ProductPrice").toString());
        }  
        try {  
            workbook.write(out);
        } catch (IOException e) {  
            e.printStackTrace();  
        }
    }
}
