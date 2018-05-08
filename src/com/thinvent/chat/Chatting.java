package com.thinvent.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinvent.utils.DateUtil;
import com.thinvent.utils.JsonUtil;
import com.thinvent.utils.SpringContextUtil;
import com.tonik.model.UserInfo;
import com.tonik.service.ChattingMessageService;

/**
 * 文件名：Chatting.java 创建人：叶楷 日期：2017-03-03 描述：Chatting类,基于Websocket的聊天功能 版本号：1.0 更新履历：2017-03-03 序号 日期 更新者 更新原因
 */

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class Chatting
{

    private static Logger logger = Logger.getLogger(Chatting.class);

    private static final String CLIENT_PREFIX = "Client";

    // concurrent包的线程安全Set，用来存放每个客户端对应的AlarmAnnotation对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static final CopyOnWriteArraySet<Chatting> connections = new CopyOnWriteArraySet<Chatting>();

    private static final AtomicInteger connectionIds = new AtomicInteger(0);

    private final String clientName;

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private HttpSession httpSession;

    private ChattingMessageService chattingMessageService;


    public Chatting()
    {
        clientName = CLIENT_PREFIX + connectionIds.getAndIncrement();
        this.chattingMessageService = (ChattingMessageService) SpringContextUtil.getBean("ChattingMessageService");

    }

    /**
     * 连接建立成功调用的方法
     * @param session session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config)
    {
        this.session = session;
        session.setMaxIdleTimeout(43200000);
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        connections.add(this);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose()
    {
        connections.remove(this);
    }

    @OnMessage
    public void onMessage(String message) throws Exception
    {
        Map<String, Object> map = JsonUtil.jsonToMap(message);
        this.sendMessage(chattingMessageService.getReply(map.get("question").toString(),
                DateUtil.parseDate(DateUtil.DEFAULT_FORMAT, map.get("questionTime").toString()),
                ((UserInfo) this.httpSession.getAttribute("userInfo")).getId()));
    }

    @OnError
    public void error(Throwable t) throws Throwable
    {
        logger.error("Chatting Error: " + t.toString(), t);
    }

    public static String byteBufferToString(ByteBuffer buffer)
    {
        CharBuffer charBuffer = null;
        try
        {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 给所有客户端推送报警消息
     * @param result
     */
    public static void broadcast(String message)
    {
        for (Chatting client : connections)
        {
            try
            {
                synchronized (client)
                {
                    client.session.getBasicRemote().sendText(message);
                }
            } catch (IOException e)
            {
                logger.debug("Chatting Error: Failed to send message to client", e);
                connections.remove(client);
                try
                {
                    client.session.close();
                } catch (IOException e1)
                {
                    // Ignore
                }
                System.out.println(client.clientName + "has been disconnected." + connections.size());
            }
        }
    }

    /**
     * 返回消息内容
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws Exception
    {
        this.session.getBasicRemote().sendText(message);
    }
}