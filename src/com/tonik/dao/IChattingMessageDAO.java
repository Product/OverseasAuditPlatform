package com.tonik.dao;

import java.util.Date;
import java.util.List;

import com.tonik.model.ChattingMessage;

public interface IChattingMessageDAO
{

    List<ChattingMessage> getChattingMessagePaging(int pageIndex, int pageSize, String strQuery, Date startTime);

    Integer getChattingMessageTotal(String strQuery, Date startTime);

    void saveChattingMessage(ChattingMessage chattingMessage);

    void removeChattingMessage(Long id);

}
