package com.thinvent.rules.business.drools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.tonik.rule.RuleException;
import com.tonik.standard.rules.Entity;

public class DroolsProcessor implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private final String filePath = this.getClass().getClassLoader().getResource("/").getPath() + "rule.drl";


    private String loadRules() throws IOException
    {        
//        File inputfile = new File(filePath);
//        FileReader fin = new FileReader(inputfile);
//        BufferedReader br = new BufferedReader(fin);
//        
//        String content = "";
//        String templine = null;
//        
//        while((templine = br.readLine()) != null) {
//            content += templine;
//        }
//        
//        br.close();
//        fin.close();
//        return content;
        return FileUtils.readFileToString(new File(filePath), "GBK");
    }

    public void runRule(List<Entity> inserts) throws RuleException
    {
        try {
            byte[] ruleString = loadRules().getBytes();
            KieServices ks = KieServices.Factory.get();
            KieFileSystem kfs = ks.newKieFileSystem();
            kfs.write("src/main/resources/simple.drl", ruleString); // ruleString是规则字符串
            KieBuilder kb = ks.newKieBuilder(kfs);
            kb.buildAll();
    
            // 以下几行代码可以看到载入错误
            Results results = kb.getResults();
            if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
                System.out.println(results.getMessages());
                throw new IllegalStateException("### errors ###");
            }
    
            KieContainer kContainer = ks.newKieContainer(kb.getKieModule().getReleaseId());
            KieSession kSession = kContainer.newKieSession();       
            
            
            for(Entity en : inserts){
                 kSession.insert(en);
            }
            
            kSession.fireAllRules();
        } catch (Exception e ) {
            throw new RuleException(e);
        }

    }

}
