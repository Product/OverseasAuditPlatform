package com.tonik.standard.rules;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.testng.collections.Maps;

import com.tonik.servlet.MismatchProductServlet;

public class Entity
{
    private Long productId; // 商品ID
    private List<Long> standardId = new ArrayList<Long>(); // 商品不符合某个国标，这个国标具体的ID
    private List<String> mismatchContent = new ArrayList<String>(); // 商品不符合某个国标的具体描述信息
    private List<StringBuffer> tempmismatchContent =  new ArrayList<StringBuffer>();
    private List<Integer> mismatchNum = new ArrayList<Integer>();//商品不符合国标的规则数量
    private Integer tempMismatchNum;//临时
    private double heat = 1;// 热量

    private String literal;
    private Set<String> types = new HashSet<String>();
    private double value;
    private String unit;
    private List<Entity> properties = new ArrayList<Entity>();
    private List<String> firedRules = new ArrayList<String>();//已经触发过的规则列表
    private double riskLevel; // 实体的风险等级
    private boolean risky;
    
    public static Map<String, Float> score = initScore();//词作为关键词的优先度 WordSimilarity.loadGlossary()

    private static Map initScore() {
        String filePath = Entity.class.getClassLoader().getResource("/").getPath()
                + "wordrankscore.data";
        ObjectInputStream objr = null;
        try
        {
            objr = new ObjectInputStream(new FileInputStream(filePath));
            return (Map<String, Float>) objr.readObject();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(objr);
        }
        return Maps.newHashMap();
    }
    /**
     * desc: 检测某商品配料指标是否在某个范围。
     * @param indexName，配料或指标名称
     * @param indexValue，配料含量或指标值
     * @param standard 标准id
     * @return，比较结果，默认找不到对应的商品信息时返回false。
     */
    public boolean checkIndexInRange(String indexName, String unitName, Long standard , double indexValue1, double indexValue2)
    {
        if (this.properties != null && this.properties.size() > 0)
        {
            for (Entity entity : this.properties)
            {
                if (entity.literal != null && entity.literal.equalsIgnoreCase(indexName))
                {
                    double entityValue = entity.value;
                    // 解析商品标注的单位,把配料含量换算成国标单位下的含量。
                    entityValue = calculateUnit(entity.unit, unitName, entityValue);
                    if (entityValue > indexValue1 && entityValue < indexValue2)
                    {
                        if(!this.standardId.isEmpty()){
                            for (int i = 0; i < this.standardId.size(); i++)
                            {
                                if (this.standardId.get(i) == standard)
                                {
                                    this.tempmismatchContent.get(i).append(indexName);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append("大于等于");
                                    this.tempmismatchContent.get(i).append(indexValue2);
                                    this.tempmismatchContent.get(i).append("， 小于等于");
                                    this.tempmismatchContent.get(i).append(indexValue1);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append(entityValue);
                                    this.tempmismatchContent.get(i).append("||");
                                    tempMismatchNum = this.mismatchNum.get(i);
                                    tempMismatchNum++;
                                    return true;
                                }
                            }
                        }
                        this.standardId.add(standard);
                        StringBuffer content = new StringBuffer();
                        content.append(indexName);
                        content.append("|");
                        content.append("大于等于");
                        content.append(indexValue2);
                        content.append("， 小于等于");
                        content.append(indexValue1);
                        content.append("|");
                        content.append(entityValue);
                        content.append("||");
                        this.tempmismatchContent.add(content);
                        this.mismatchNum.add(new Integer(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * desc: 检测某商品配料指标是否在某个范围之外。
     * @param indexName，配料或指标名称
     * @param indexValue，配料含量或指标值
     * @param standard 标准id
     * @return，比较结果，默认找不到对应的商品信息时返回false。
     */
    public boolean checkIndexOutRange(String indexName, String unitName, Long standard, double indexValue1, double indexValue2)
    {
        if (this.properties != null && this.properties.size() > 0)
        {
            for (Entity entity : this.properties)
            {
                if (entity.literal != null && entity.literal.equalsIgnoreCase(indexName))
                {
                    double entityValue = entity.value;
                    // 解析商品标注的单位,把配料含量换算成国标的单位。
                    entityValue = calculateUnit(entity.unit, unitName, entityValue);
                    if (entityValue < indexValue1 || entityValue > indexValue2)
                    {
                        if(!this.standardId.isEmpty()){
                            for (int i = 0; i < this.standardId.size(); i++)
                            {
                                if (this.standardId.get(i).equals(standard))
                                {
                                    this.tempmismatchContent.get(i).append(indexName);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append("大于等于");
                                    this.tempmismatchContent.get(i).append(indexValue1);
                                    this.tempmismatchContent.get(i).append("，小于等于");
                                    this.tempmismatchContent.get(i).append(indexValue2);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append(entityValue);
                                    this.tempmismatchContent.get(i).append("||");
                                    tempMismatchNum = this.mismatchNum.get(i);
                                    tempMismatchNum++;
                                    return true;
                                }
                            }
                        }
                        this.standardId.add(standard);
                        StringBuffer content = new StringBuffer();
                        content.append(indexName);
                        content.append("|");
                        content.append("大于等于");
                        content.append(indexValue1);
                        content.append("，小于等于");
                        content.append(indexValue2);
                        content.append("|");
                        content.append(entityValue);
                        content.append("||");
                        this.tempmismatchContent.add(content);
                        this.mismatchNum.add(new Integer(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * desc: 检测某商品配料指标是否在大于某个值。
     * @param indexName，配料或指标名称
     * @param indexValue，配料含量或指标值 
     * @param standard 标准id
     * @return，比较结果，默认找不到对应的商品信息时返回false。
     */
    public boolean checkIndexGreaterThan(String indexName, String unitName, Long standard, double indexValue)
    {
        if (this.properties != null && this.properties.size() > 0)
        {
            for (Entity entity : this.properties)
            {
                if (entity.literal != null && entity.literal.equalsIgnoreCase(indexName))
                {
                    double entityValue = entity.value;
                    // 解析商品标注的单位,把配料含量换算成国标的单位。
                    entityValue = calculateUnit(entity.unit, unitName, entityValue);
                    if (entityValue > indexValue)
                    {
                        if (!this.standardId.isEmpty())
                        {
                            for (int i = 0; i < this.standardId.size(); i++)
                            {
                                if (this.standardId.get(i).equals(standard))
                                {
                                    this.tempmismatchContent.get(i).append(indexName);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append("小于等于");
                                    this.tempmismatchContent.get(i).append(indexValue);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append(entityValue);
                                    this.tempmismatchContent.get(i).append("||");
                                    tempMismatchNum = this.mismatchNum.get(i);
                                    tempMismatchNum++;
                                    return true;

                                }
                            }
                        }
                        this.standardId.add(standard);
                        StringBuffer content = new StringBuffer();
                        content.append(indexName);
                        content.append("|");
                        content.append("小于等于");
                        content.append(indexValue);
                        content.append("|");
                        content.append(entityValue);
                        content.append("||");
                        this.tempmismatchContent.add(content);
                        this.mismatchNum.add(new Integer(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * desc: 检测某商品配料指标是否在大等于某个值。检查上限，大则视为风险
     * @param indexName，配料或指标名称
     * @param indexValue，配料含量或指标值
     * @param standard 标准id
     * @return，比较结果，默认找不到对应的商品信息时返回false。
     */
    public boolean checkIndexGreaterEqualThan(String indexName, String unitName, Long standard, double indexValue)
    {
        if (this.properties != null && this.properties.size() > 0)
        {
            for (Entity entity : this.properties)
            {
                if (entity.literal != null && entity.literal.equalsIgnoreCase(indexName))
                {
                    double entityValue = entity.value;
                    // 解析商品标注的单位,把配料含量换算成国标的单位。
                    entityValue = calculateUnit(entity.unit, unitName, entityValue);
                    if (entityValue >= indexValue)
                    {
                        if (!this.standardId.isEmpty())
                        {
                            for (int i = 0; i < this.standardId.size(); i++)
                            {
                                if (this.standardId.get(i).equals(standard))
                                {
                                     //添加规则信息
                                    this.tempmismatchContent.get(i).append(indexName);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append("小于");
                                    this.tempmismatchContent.get(i).append(indexValue);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append(entityValue);
                                    this.tempmismatchContent.get(i).append("||");
                                    tempMismatchNum = this.mismatchNum.get(i);
                                    tempMismatchNum++;
                                    return true;

                                }
                            }
                        }
                        this.standardId.add(standard);
                        StringBuffer content = new StringBuffer();
                        content.append(indexName);
                        content.append("|");
                        content.append("小于");
                        content.append(indexValue);
                        content.append("|");
                        content.append(entityValue);
                        content.append("||");
                        this.tempmismatchContent.add(content);
                        this.mismatchNum.add(new Integer(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * desc: 检测某商品配料指标是否在小于某个值。
     * @param indexName，配料或指标名称
     * @param indexValue，配料含量或指标值
     * @param standard 标准id
     * @return，比较结果，默认找不到对应的商品信息时返回false。
     */
    public boolean checkIndexLessThan(String indexName, String unitName, Long standard, double indexValue)
    {
        if (this.properties != null && this.properties.size() > 0)
        {
            for (Entity entity : this.properties)
            {
                if (entity.literal != null && entity.literal.equalsIgnoreCase(indexName))
                {
                    double entityValue = entity.value;
                    // 解析商品标注的单位,把配料含量换算成国标的单位。
                    entityValue = calculateUnit(entity.unit, unitName, entityValue);
                    if (entityValue < indexValue)
                    {
                        if (!this.standardId.isEmpty())
                        {
                            for (int i = 0; i < this.standardId.size(); i++)
                            {
                                if (this.standardId.get(i).equals(standard))
                                {
                                    this.tempmismatchContent.get(i).append(indexName);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append("大于等于");
                                    this.tempmismatchContent.get(i).append(indexValue);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append(entityValue);
                                    this.tempmismatchContent.get(i).append("||");
                                    tempMismatchNum = this.mismatchNum.get(i);
                                    tempMismatchNum++;
                                    return true;

                                }
                            }
                        }
                        
                        //如果该STANDARD没有出现，
                        this.standardId.add(standard);
                        StringBuffer content = new StringBuffer();
                        content.append(indexName);
                        content.append("|");
                        content.append("大于等于");
                        content.append(indexValue);
                        content.append("|");
                        content.append(entityValue);
                        content.append("||");
                        this.tempmismatchContent.add(content);
                        this.mismatchNum.add(new Integer(1));    
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * desc: 检测某商品配料指标是否在小等于某个值。
     * @param indexName，配料或指标名称
     * @param indexValue，配料含量或指标值
     * @param standard 标准id
     * @return，比较结果，默认找不到对应的商品信息时返回false。
     */
    public boolean checkIndexLessEqualThan(String indexName, String unitName, Long standard, double indexValue)
    {
        if (this.properties != null && this.properties.size() > 0)
        {
            for (Entity entity : this.properties)
            {
                if (entity.literal != null && entity.literal.equalsIgnoreCase(indexName))
                {
                    double entityValue = entity.value;
                    // 解析商品标注的单位,把配料含量换算成国标的单位。
                    //entityValue = calculateUnit(entity.unit, unitName, entityValue);
                    if (entityValue <= indexValue)
                    {
                        if (!this.standardId.isEmpty())
                        {
                            for (int i = 0; i < this.standardId.size(); i++)
                            {
                                if (this.standardId.get(i).equals(standard))
                                {
                                    this.tempmismatchContent.get(i).append(indexName);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append("大于");
                                    this.tempmismatchContent.get(i).append(indexValue);
                                    this.tempmismatchContent.get(i).append("|");
                                    this.tempmismatchContent.get(i).append(entityValue);
                                    this.tempmismatchContent.get(i).append("||");
                                    tempMismatchNum = this.mismatchNum.get(i);
                                    tempMismatchNum++;
                                    return true;

                                }
                            }
                        }
                        this.standardId.add(standard);
                        StringBuffer content = new StringBuffer();
                        content.append(indexName);
                        content.append("|");
                        content.append("大于");
                        content.append(indexValue);
                        content.append("|");
                        content.append(entityValue);
                        content.append("||");
                        this.tempmismatchContent.add(content);
                        this.mismatchNum.add(new Integer(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**TODO:
     * desc: 检测某商品是否属于 幼儿配方食品。
     * @return
     */
    public boolean isInfantMilk()
    {
        if (this.literal != null && this.literal.contains("奶粉")){
            if (Pattern.matches(".*((1|一)(段|号)|(S|s)(1)).*",this.literal)){
                return false;
            }

            //特殊处理有pre段的话 就是婴儿奶粉
            if (Pattern.matches(".*([Pp][Rr][Ee]段).*",literal)){
                return false;
            }
            
            //先从字串中找出英文单词， 然后再匹配
            String s = "\\d+.\\d+|\\w+";
            Pattern  pattern=Pattern.compile(s);  
            Matcher  ma=pattern.matcher(this.literal);  
       
            while(ma.find()){  
                //System.out.println(ma.group());
                String temp= splitNotNumber(ma.group());
                if ( Pattern.matches("\\b[Pp][Rr][Ee]\\b", temp )) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    /**TODO:
     * desc: 检测某商品是否属于婴儿配方食品。
     * @return
     */
    public boolean isKidMilk()
    {
        if (this.literal != null && this.literal.contains("奶粉") ) {
            if (Pattern.matches(".*((1|一)(段|号)|(S|s)(1)).*",this.literal)){
                return true;
            }
            
            //特殊处理有pre段的话 就是婴儿奶粉
            if (Pattern.matches(".*([Pp][Rr][Ee]段).*",literal)){
                return true;
            }
            
            //先从字串中找出英文单词， 然后再匹配
            String s = "\\d+.\\d+|\\w+";
            Pattern  pattern=Pattern.compile(s);  
            Matcher  ma=pattern.matcher(this.literal);  
       
            while(ma.find()){  
                //System.out.println(ma.group());
                String temp= splitNotNumber(ma.group());
                if ( Pattern.matches("\\b[Pp][Rr][Ee]\\b", temp )) {
                    return true;
                }
            }       
        }
        return false;
    }

    /**TODO:
     * desc: 去掉字串中的数字
     * @return
     */
    public String splitNotNumber(String content) {  
        Pattern pattern = Pattern.compile("\\D+");  
        Matcher matcher = pattern.matcher(content);  
        while (matcher.find()) {  
            return matcher.group(0);  
        }  
        return "";  
    }  
    
    //Entity.java - Modified by SongBo 170614
    /**TODO:
     * desc: 设置某商品的风险信息。
     * @return
     */
    public void updateRiskyInfo(String description, double value)
    {
        this.setRisky(true);
        String content = description+" | 本品（" + this.getLiteral() + "）：" + value;
        this.mismatchContent.add(content);
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getLiteral()
    {
        return literal;
    }

    public void setLiteral(String literal) throws Exception
    {
        this.literal = literal;
        if(this.types.contains("商品")){
            categorizebyLiteral_merge(literal);
        }
    }

    public Set<String> getTypes()
    {
        return types;
    }

    public void setTypes(Set<String> types)
    {
        this.types = types;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public List<Entity> getProperties()
    {
        return properties;
    }

    public void setProperties(List<Entity> properties)
    {
        this.properties = properties;
    }

    public boolean isRisky()
    {
        return risky;
    }

    public void setRisky(boolean risky)
    {
        this.risky = risky;
    }

    public double getHeat()
    {
        return heat;
    }

    public void setHeat(double heat)
    {
        this.heat = heat;
    }

    /**
     * 将配料值转换为标准值
     * @param contentUnit 配料单位
     * @param baseUnit 标准单位
     * @param contentValue 配料的值
     * @return 单位转换后的配料值
     */
    private double calculateUnit(String contentUnit, String baseUnit, double contentValue)
    {
        double times = 1;// 倍數
        switch (baseUnit)
        {
            case "毫克":
                times = 1000;
                break;
            case "微克":
                times = 1000000;
                break;
        }
        if (contentUnit.indexOf("毫克") >= 0 || contentUnit.indexOf("mg") >= 0)
        {
            times = times * 0.001;
        }
        else if (contentUnit.indexOf("微克") >= 0 || contentUnit.indexOf("ug") >= 0 || contentUnit.indexOf("μg") >= 0)
        {
            times = times * 0.000001;
        }
        BigDecimal bg = new BigDecimal(contentValue * times).setScale(4, RoundingMode.UP);
        return bg.doubleValue();
    }

    public List<Long> getStandardId()
    {
        return standardId;
    }
    
    public void addStandardId(Long standardId)
    {
        if(!this.standardId.contains(standardId))
            this.standardId.add(standardId);
    }

    public List<String> getMismatchContent()
    {
        return mismatchContent;
    }

    private void setMismatchContent(List<String> mismatchContent)
    {
        this.mismatchContent = mismatchContent;
    }
    
    void categorizebyLiteral_merge(String literal) throws Exception{//如果一个概念包含在另一个概念中，则采用更长的那个概念
        List<String> temp = new ArrayList<String>();
        for(String str : WordSimilarity.ALLWORDS.keySet()){
            if(literal.contains(str)){
                temp.add(str);
            }
        }
        for(int i=0; i<temp.size(); i++){
            for(int j=0; j<temp.size(); j++){
                if(i!=j && temp.get(j)!="-" && temp.get(j).contains(temp.get(i))){
                    temp.set(i, "-");
                }
            }
        }
        List<String> segs = new ArrayList<String>();
        for(String str : temp){
            if(!str.equals("-")){
                segs.add(str);
            }
        }
        Map<String, Float> stl = new HashMap<String, Float>();
        for (String seg : segs){
            stl.put(seg, score.get(seg) == null ? 0 : score.get(seg));//rank with only 1 score
        }
        List<Map.Entry<String, Float>> map2list = new ArrayList<Map.Entry<String, Float>>(stl.entrySet());
        Collections.sort(map2list, new Comparator<Map.Entry<String, Float>>(){
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2){
                return (-1) * o1.getValue().compareTo(o2.getValue());
            }
        });
        int count = 0;
        for(int k=0; k<map2list.size(); k++){
            String wd = map2list.get(k).getKey();
            if(wd.length()==1 && literal.substring(literal.indexOf(wd)+wd.length()).matches("[\\u4e00-\\u9fa5]+.*")){
                continue;
            }
            List<Word> variations = WordSimilarity.ALLWORDS.get(wd);
            for(Word w : variations){
                if(w.getType().equals("N")){//Disambiguation operations to be added here
                    this.types.add(wd);
                    count++;
                    String father = w.getFirstPrimitive();
                    this.types.add(father);
                    if(WordSimilarity.ALLWORDS.containsKey(father)){
                        List<Word> fvariations = WordSimilarity.ALLWORDS.get(father);
                        for(Word fw : fvariations){
                            if(fw.getType().equals("N")){
                                this.types.add(fw.getFirstPrimitive());//fathers' fathers
                                //System.out.println("Chosen:\t"+wd+"\t"+w.getFirstPrimitive()+"\t"+fw.getFirstPrimitive());//
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            if(count>2)
                break;
        }
    }
    
    public void addType(String type){
        this.types.add(type);
    }

    public double getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(double risklevel) {
        this.riskLevel = risklevel;
    }
    
    public void addRiskLevel(double addedvalue){
        this.riskLevel += addedvalue;   
    }
    
    public void addFiredRule(String firedrule){
        this.firedRules.add(firedrule);
    }
    
    
    public List<String> getFiredRules(){
        return firedRules;
    }

    public boolean isBird()
    {
        if (this.literal != null  ) {
            return true;
        }
        return false;
    }
    
    
    
    public List<Integer> getMismatchNum()
    {
        return mismatchNum;
    }

    public void setMismatchNum(List<Integer> mismatchNum)
    {
        this.mismatchNum = mismatchNum;
    }

    public boolean checkProductType(String rule, Long startdid,String bird)
    {
        //types contains "商品", types contains "禽", firedRules not contains "负面清单禽类风险"
        if (this.literal != null  ) {
            Entity en = properties.get(0);
            Set<String> types = en.getTypes();
            if (types.contains(bird) ) {
                this.standardId.add(startdid);
                StringBuffer sb=new StringBuffer();
                sb.append(bird);
                sb.append("|");
                sb.append(rule);
                sb.append("|");
                sb.append(bird);
                sb.append("||");
                this.tempmismatchContent.add(sb);
                //this.addFiredRule(rule);
                return true;
            }
        }
        return false;
    }
    
    public void addProperty(Entity property){
        this.properties.add(property);
    }
    
}
