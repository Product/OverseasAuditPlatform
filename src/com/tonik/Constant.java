package com.tonik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant
{
    // For extractService
    public static int SCROLL_HEIGHT = 3000;
    public static int SCROLL_MAX_COUNT = 100;
    public static int MAX_LOCATION_LENGTH = 400;

    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static String getFormatTime(Date time)
    {
        if (time == null)
            return null;
        else
        {
            String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(time);
            return date;
        }
    }

    public static String val(Object obj)
    {
        if (obj != null)
        {
            return obj.toString();
        }
        else
        {
            return "";
        }
    }


    public static double productGradeWebisteScale = 0.5;

    public static double productGradeProductDefinitionScale = 0.5;

    public static String SOURCE_TYPE_ALL = "0";

    public static String SOURCE_TYPE_INFO = "1";

    public static String SOURCE_TYPE_CRED = "2";


    public static String matchDateString(String dateStr)
    {
        try
        {
            List matches = null;
            Pattern p = Pattern.compile(
                    "(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
            Matcher matcher = p.matcher(dateStr);
            if (matcher.find() && matcher.groupCount() >= 1)
            {
                matches = new ArrayList();
                for (int i = 1; i <= matcher.groupCount(); i++)
                {
                    String temp = matcher.group(i);
                    matches.add(temp);
                }
            }
            else
            {
                matches = Collections.EMPTY_LIST;
            }
            if (matches.size() > 0)
            {
                return ((String) matches.get(0)).trim();
            }
            else
            {
            }
        } catch (Exception e)
        {
            return "";
        }

        return dateStr;
    }


    // 风险建议
    public static enum EvaluationSuggestion
    {
        WARN(0.1, "警告并向社会公示"), REPORT(0.03, "报告政府部门"), EXAMINE(0.01, "第三方审查"), NOTICE(0.001, "通知平台"), NONE(0.0,
                "暂不处理");

        private Double value;

        private String suggestion;


        private EvaluationSuggestion(Double value, String suggestion)
        {
            this.value = value;
            this.suggestion = suggestion;
        }

        public Double getValue()
        {
            return value;
        }

        public void setValue(Double value)
        {
            this.value = value;
        }

        public String getSuggestion()
        {
            return suggestion;
        }

        public void setSuggestion(String suggestion)
        {
            this.suggestion = suggestion;
        }

        public static String getSuggestion(Double value)
        {
            for (EvaluationSuggestion item : EvaluationSuggestion.values())
            {
                if(value > item.getValue())
                    return item.getSuggestion();
            }
            return "风险值错误";
        }
    }
}
