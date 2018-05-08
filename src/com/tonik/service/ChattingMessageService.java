package com.tonik.service;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinvent.QuestionAnswer.QAoperation;
import com.thinvent.utils.DateUtil;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IChattingMessageDAO;
import com.tonik.model.ChattingMessage;

/**
 * @spring.bean id="ChattingMessageService"
 * @spring.property name="chattingMessageDAO" ref="ChattingMessageDAO"
 * @spring.property name="qAoperation" ref="QAoperation"
 */
public class ChattingMessageService
{
    private IChattingMessageDAO chattingMessageDAO;
    private QAoperation qAoperation;


    public IChattingMessageDAO getChattingMessageDAO()
    {
        return chattingMessageDAO;
    }

    public void setChattingMessageDAO(IChattingMessageDAO chattingMessageDAO)
    {
        this.chattingMessageDAO = chattingMessageDAO;
    }

    public QAoperation getqAoperation()
    {
        return qAoperation;
    }

    public void setqAoperation(QAoperation qAoperation)
    {
        this.qAoperation = qAoperation;
    }

    public String getReply(String question, Date questionTime, Long userId)
    {
        String answer = qAoperation.getAnswer(question);
        ChattingMessage cm = new ChattingMessage().setAnswer(answer).setAnswerTime(new Date()).setQuestion(question)
                .setQuestionTime(questionTime).setUserId(userId);
        chattingMessageDAO.saveChattingMessage(cm);
        Map<String, String> result = Maps.newHashMap();
        result.put("answer", answer);
        result.put("answerTime", DateUtil.formatDate(DateUtil.DEFAULT_FORMAT, cm.getAnswerTime()));
        return GsonUtil.bean2Json(result);
    }

    public String getChattingMessages(int pageIndex, int pageSize, String strQuery, Date startTime)
    {
        List<ChattingMessage> lCMs = chattingMessageDAO.getChattingMessagePaging(pageIndex, pageSize, strQuery,
                startTime);
        Integer count = chattingMessageDAO.getChattingMessageTotal(strQuery, startTime);
        List<Map<String, String>> list = Lists.newArrayList();
        for (ChattingMessage item : lCMs)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", item.getId().toString());
            map.put("question", item.getQuestion());
            map.put("answer", item.getAnswer());
            map.put("questionTime", DateUtil.formatDate(DateUtil.DEFAULT_FORMAT, item.getQuestionTime()));
            map.put("answerTime", DateUtil.formatDate(DateUtil.DEFAULT_FORMAT, item.getAnswerTime()));
            list.add(map);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", count);
        result.put("list", list);
        return GsonUtil.bean2Json(result);
    }
}
