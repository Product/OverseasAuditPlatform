/**
 * @Title QAoperation.java
 * @Package com.thinvent.QuestionAnswer
 * @Desc
 * @Author GongWenhua
 * @Date 2017年4月12日 下午5:20:09
 * @Version 1.0
 */
package com.thinvent.QuestionAnswer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.openqa.selenium.server.browserlaunchers.Sleeper;
import org.springframework.context.support.StaticApplicationContext;

import com.thinvent.utils.SpringContextUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * @spring.bean id="QAoperation"
 */
public class QAoperation
{
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();


    public QAoperation() throws InterruptedException
    {
        map = readQuestionAnswerfromExcel(QAoperation.class.getResource("/").getPath() + "奶粉信息采集.xls");

    }

    public String getAnswer(String question)
    {

        double maxscore = 0;

        String outputanswer = "";
        double threshold = 0.6;

        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet())
        {

            double score = 0;
            i++;
            if (i >= 20)
            {
                break;
            }
            score = new TextSimilarityCalulate().calulateSimilarity(entry.getKey(), question);

            if (score >= maxscore)
            {
                maxscore = score;

                outputanswer = entry.getValue();
            }
        }

        if (maxscore > threshold)
        {
            return outputanswer;
        }
        else
        {
            outputanswer = "对不起没有找到你要得答案";
            return outputanswer;
        }

    }

    public LinkedHashMap<String, String> readQuestionAnswerfromExcel(String inputfileName)
    {
        jxl.Workbook readwb = null;
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        try

        {

            // 构建Workbook对象, 只读Workbook对象

            // 直接从本地文件创建Workbook

            InputStream instream = new FileInputStream(inputfileName);

            readwb = Workbook.getWorkbook(instream);

            // Sheet的下标是从0开始

            // 获取第一张Sheet表

            Sheet readsheet = readwb.getSheet(0);

            // 获取Sheet表中所包含的总列数

            int rsColumns = readsheet.getColumns();

            // 获取Sheet表中所包含的总行数

            int rsRows = readsheet.getRows();

            // 获取指定单元格的对象引用

            for (int i = 1; i < rsRows; i++)
            { // 第０行为标题头：从第１行读起
                Cell cell = readsheet.getCell(0, i);// 这是读取的问题的ｃｅｌｌ

                Cell cell1 = readsheet.getCell(1, i);// 这是读取的答案的ｃｅｌｌ
                map.put(cell.getContents(), cell1.getContents());

            }

        } catch (Exception e)
        {

            // e.printStackTrace();
            // logger.error("Excel Operation Error: " + e.toString(), e);

        }
        finally
        {

            readwb.close();

        }
        return map;
    }

}
