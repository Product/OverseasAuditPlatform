package com.tonik.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import com.thinvent.utils.WebUtil;

/**
 * 读写规则文件 Servlet
 * @web.servlet name="rulesFileServlet"
 * @web.servlet-mapping url-pattern="/servlet/RulesFileServlet"
 */
public class RulesFileServlet extends BaseServlet
{
    private static final long serialVersionUID = 1L;

    private String mutex = "";

    // 比较关系
    private static final String[] conditions = { "", "大于", "小于", "间于", "外于" };

    // 规则类别
    private static final String[] ruleTypes = { "配方", "分类", "父类", "禁止商品" };

    // 影响类别列表
    private static final String[] entities = { "婴儿配方食品", "乳基婴儿配方食品", "豆基婴儿配方食品", "较大婴儿和幼儿配方食品", "婴幼儿谷类辅助食品",
            "婴幼儿高蛋白谷物辅助食品", "婴幼儿生制类谷物辅助食品", "婴幼儿饼干或其他婴幼儿谷物辅助食品", "婴幼儿罐装辅助食品" };

    private static Logger logger = Logger.getLogger(RulesFileServlet.class);


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String fileName = "rule.drl";
        // drl文件目录路径
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes") + "/" + fileName;

        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("ruleList".equalsIgnoreCase(methodName))
                {
                    Long standard = Long.parseLong(request.getParameter("standard"));
                    String content = getFileContent(filePath);
                    List<Map<String, Object>> list = getRuleList(content, standard);
                    WebUtil.writeJSON(response, list);
                }
                else if ("delRule".equalsIgnoreCase(methodName))
                {
                    String ruleName = URLDecoder.decode(request.getParameter("ruleName"), "UTF-8");
                    Long standard = Long.parseLong(request.getParameter("standard"));
                    if (null == ruleName || ruleName.isEmpty() || standard == null)
                        throw new Exception("参数不正确");
                    synchronized (mutex)
                    {
                        String content = getFileContent(filePath);
                        content = delRule(content, ruleName, standard);
                        writeRule(filePath, content);
                    }
                    response.getWriter().write("true");
                }
                else if ("addRule".equalsIgnoreCase(methodName))
                {
                    String ruleName = URLDecoder.decode(request.getParameter("ruleName"), "UTF-8");
                    String ruleType = URLDecoder.decode(request.getParameter("ruleType"), "UTF-8");
                    String entity = URLDecoder.decode(request.getParameter("entity"), "UTF-8");
                    String composition = URLDecoder.decode(request.getParameter("composition"), "UTF-8");
                    String unit = URLDecoder.decode(request.getParameter("unit"), "UTF-8");
                    String condition = URLDecoder.decode(request.getParameter("condition"), "UTF-8");
                    String num1 = URLDecoder.decode(request.getParameter("num1"), "UTF-8");
                    String num2 = URLDecoder.decode(request.getParameter("num2"), "UTF-8");
                    String fatherClass = URLDecoder.decode(request.getParameter("fatherClass"), "UTF-8");
                    String childClass = URLDecoder.decode(request.getParameter("childClass"), "UTF-8");
                    Long standard = Long.parseLong(request.getParameter("standard"));
                    synchronized (mutex)
                    {
                        String content = getFileContent(filePath);
                        StringBuffer sb = new StringBuffer(content);
                        sb.append(addRule(ruleName, ruleType, entity, composition, unit, condition, num1, num2,
                                fatherClass, childClass, standard));
                        content = sb.toString();
                        writeRule(filePath, content);
                    }
                    response.getWriter().write("true");
                }
                else if ("entityList".equalsIgnoreCase(methodName))
                {
                    WebUtil.writeJSON(response, entities);
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }

    /*
     * 新增规则
     */
    private StringBuffer addRule(String ruleName, String ruleType, String entity, String composition, String unit,
            String condition, String num1, String num2, String fatherClass, String childClass, Long standard)
    {
        StringBuffer sb = new StringBuffer();
        // 规则名称
        sb.append("rule \"" + ruleName + "\"\n");
        // 规则类型
        sb.append("/* ruleType:" + ruleTypes[Integer.parseInt(ruleType)] + "\n");
        // 规则集ID
        sb.append("   standard:" + standard + "l */\n");
        sb.append("    when\n");
        // 父类
        if ("2".equals(ruleType))
        {
            sb.append("        $m : Entity(types contains \"" + childClass + "\", types not contains \"" + fatherClass
                    + "\")\n");
            sb.append("    then\n        $m.addType( \"" + fatherClass + "\" );\n        $m.addStandardId(" + standard
                    + "l);\n        update( $m );\nend\n\n");
        }
        // 分类
        else if ("1".equals(ruleType))
        {
            List<String> contain = Lists.newArrayList(num1.split(",|，"));
            List<String> notContain = Lists.newArrayList(num2.split(",|，"));
            sb.append("        $m : Entity(types contains \"商品\"");
            for (String item : contain)
            {
                if (!item.isEmpty())
                    sb.append(", literal matches \".*" + item + ".*\"");
            }
            for (String item : notContain)
            {
                if (!item.isEmpty())

                    sb.append(", literal not matches \".*" + item + ".*\"");
            }
            sb.append(", types not contains \"" + entity + "\")\n");
            sb.append("    then\n        $m.addType( \"" + entity + "\" );\n        $m.addStandardId(" + standard
                    + "l);\n        update( $m );\nend\n\n");
        }
        // 禁止商品
        else if ("3".equals(ruleType))
        {
            // List<String> contain = Lists.newArrayList(num1.split(",|，"));
            // List<String> notContain = Lists.newArrayList(num2.split(",|，"));
            // sb.append(" $m : Entity(types contains \"商品\"");
            // if (contain.size() > 0 && !contain.get(0).isEmpty())
            // {
            // sb.append(", types contains \"" + contain.get(0) + "\"");
            // contain.remove(0);
            // }
            // for (String item : contain)
            // {
            // if (!item.isEmpty())
            // sb.append(" || types contains \"" + item + "\"");
            // }
            // for (String item : notContain)
            // {
            // if (!item.isEmpty())
            // sb.append(", types not contains \"" + item + "\"");
            // }
            // sb.append(", firedRules not contains \"" + ruleName + "\")\n");
            // sb.append(" then\n $m.addRiskLevel(100.0);\n $m.setRisky(true);\n $m.addStandardId(" + standard
            // + "l);\n $m.addFiredRule(\"" + ruleName + "\");\nend\n\n");

            StringBuilder sb2 = new StringBuilder("");
            List<String> Scontain = Lists.newArrayList(num1.split(",|，"));
            List<String> SnotContain = Lists.newArrayList(num2.split(",|，"));
            List<String> contain = new ArrayList<>(Scontain);
            List<String> notContain = new ArrayList<>(SnotContain);
            sb.append("        $m : Entity(types contains \"商品\"");
            if (contain.size() > 0 && !contain.get(0).isEmpty())
            {
                String tx = contain.get(0).contains("|") ? ".*(" + contain.get(0) + ").*"
                        : ".*" + contain.get(0) + ".*";
                sb.append(", (literal matches \"" + tx + "\"");

                sb2.append(") || (");
                if (contain.get(0).contains("|"))
                {
                    String[] cds = contain.get(0).split("\\|");
                    for (int i = 0; i < cds.length; i++)
                    {
                        if (i == 0)
                        {
                            sb2.append("(types contains \"" + cds[i] + "\"");
                        }
                        else
                        {
                            sb2.append(" || types contains \"" + cds[i] + "\"");
                        }
                    }
                    sb2.append(")");
                }
                else
                {
                    sb2.append("types contains \"" + contain.get(0) + "\"");
                }
                contain.remove(0);
            }
            for (String item : contain)
            {
                if (!item.isEmpty())
                {
                    String tx = item.contains("|") ? ".*(" + item + ").*" : ".*" + item + ".*";
                    sb.append(" && literal matches \"" + tx + "\"");
                }

                if (item.contains("|"))
                {
                    String[] cds = item.split("\\|");
                    for (int i = 0; i < cds.length; i++)
                    {
                        if (i == 0)
                        {
                            sb2.append(" && (types contains \"" + cds[i] + "\"");
                        }
                        else
                        {
                            sb2.append(" || types contains \"" + cds[i] + "\"");
                        }

                    }
                    sb2.append(")");
                }
                else
                {
                    sb2.append(" && types contains \"" + item + "\"");
                }

            }
            for (String item : notContain)
            {
                if (!item.isEmpty())
                {
                    sb.append(" && literal not matches \".*" + item + ".*\"");
                    sb2.append(" && types not contains \"" + item + "\"");
                }
            }
            if (contain.size() > 0 && !contain.get(0).isEmpty())
            {
                sb2.append(")");
            }
            sb.append(sb2);
            sb.append(", firedRules not contains \"" + ruleName + "\")\n");
            sb.append("    then\n        $m.addRiskLevel(100.0);\n        $m.setRisky(true);\n        $m.addStandardId("
                    + standard + "l);\n        $m.addFiredRule(\"" + ruleName + "\");\nend\n\n");
        }
        // 配方
        else if ("0".equals(ruleType))
        {
            sb.append("        $m : Entity(types contains \"" + entity + "\", firedRules not contains \"" + ruleName
                    + "\")\n");
            sb.append("        $e : Entity(literal == \"" + composition + "\"");
            // + ", unit == \"" + unit + "\"");
            // 大于
            if ("1".equals(condition))
            {
                sb.append(", value (<" + num1 + ")) from $m.properties\n");
                sb.append("    then\n        $m.updateRiskyInfo(\"" + ruleName + "：" + composition + " "
                        + conditions[Integer.parseInt(condition)] + " " + num1 + " " + unit + "\", $e.getValue());\n");
            }
            // 小于
            else if ("2".equals(condition))
            {
                sb.append(", value (>" + num1 + ")) from $m.properties\n");
                sb.append("    then\n        $m.updateRiskyInfo(\"" + ruleName + "：" + composition + " "
                        + conditions[Integer.parseInt(condition)] + " " + num1 + " " + unit + "\", $e.getValue());\n");
            }
            // 间于
            else if ("3".equals(condition))
            {
                sb.append(", value (<" + num1 + " || >" + num2 + ")) from $m.properties\n");
                sb.append("    then\n        $m.updateRiskyInfo(\"" + ruleName + "：" + composition + " "
                        + conditions[Integer.parseInt(condition)] + " " + num1 + "-" + num2 + " " + unit
                        + "\", $e.getValue());\n");
            }
            // 外于
            else if ("4".equals(condition))
            {
                sb.append(", value (>" + num1 + " && <" + num2 + ")) from $m.properties\n");
                sb.append("    then\n        $m.updateRiskyInfo(\"" + ruleName + "：" + composition + " "
                        + conditions[Integer.parseInt(condition)] + " " + num1 + "-" + num2 + " " + unit
                        + "\", $e.getValue());\n");
            }
            sb.append("        $m.addRiskLevel(1.0);\n        $m.setRisky(true);\n        $m.addStandardId(" + standard
                    + "l);\n        $m.addFiredRule(\"" + ruleName + "\");\nend\n\n");
        }
        return sb;
    }

    /*
     * 获取文件内容
     */
    private String getFileContent(String filePath) throws IOException
    {
        return FileUtils.readFileToString(new File(filePath), "GBK");
    }

    /*
     * 获取规则集下规则列表
     */
    private List<Map<String, Object>> getRuleList(String content, Long standard)
    {
        List<Map<String, Object>> ruleList = Lists.newArrayList();
        // 遍历文件所有规则
        String regex = "rule .*?end";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find())
        {
            String rule = matcher.group();
            // 匹配规则集
            if (rule.contains("standard:" + standard + "l"))
            {
                Map<String, Object> ruleMap = Maps.newHashMap();
                ruleMap.put("name", rule.split("\"")[1]);
                ruleMap.put("content", rule);
                ruleList.add(ruleMap);
            }
        }
        return ruleList;
    }

    /*
     * 删除该规则集下所有同名规则
     */
    private String delRule(String content, String ruleName, Long standard)
    {
        String regex = "rule \"" + ruleName + "\".*?end";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            String rule = matcher.group();
            // 匹配规则集
            if (rule.contains("standard:" + standard + "l"))
            {
                matcher.appendReplacement(sb, "");
            }
        }
        matcher.appendTail(sb);
        content = sb.toString();
        return content;
    }

    /*
     * 将内容写入文件
     */
    private void writeRule(String filePath, String content) throws IOException
    {
        FileUtils.writeStringToFile(new File(filePath), content, "GBK");
    }
}
