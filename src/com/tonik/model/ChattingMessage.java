package com.tonik.model;

import java.util.Date;

/**
 * @hibernate.class table="ChattingMessage"
 */
public class ChattingMessage
{
    private Long id;
    
    private Long userId;
    
    private String answer;
    
    private String question;
    
    private Date answerTime;
    
    private Date questionTime;


    /**
     * @hibernate.id column="MessageId" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    public ChattingMessage setId(Long id)
    {
        this.id = id;
        return this;
    }
    
    /**
     * @hibernate.property column="UserId" type="long" not-null="true"
     * @return Returns the userId.
     */
    public Long getUserId()
    {
        return userId;
    }

    public ChattingMessage setUserId(Long userId)
    {
        this.userId = userId;
        return this;
    }

    /**
     * @hibernate.property column="Answer" type="string" length="200" not-null="false"
     * @return Returns the answer.
     */
    public String getAnswer()
    {
        return answer;
    }

    public ChattingMessage setAnswer(String answer)
    {
        this.answer = answer;
        return this;
    }

    /**
     * @hibernate.property column="Question" type="string" length="200" not-null="false"
     * @return Returns the question.
     */
    public String getQuestion()
    {
        return question;
    }

    public ChattingMessage setQuestion(String question)
    {
        this.question = question;
        return this;
    }

    /**
     * @hibernate.property column="AnswerTime" sql-type="Date" not-null="true" 
     * @return Returns the answerTime.
     */
    public Date getAnswerTime()
    {
        return answerTime;
    }

    public ChattingMessage setAnswerTime(Date answerTime)
    {
        this.answerTime = answerTime;
        return this;
    }

    /**
     * @hibernate.property column="QuestionTime" sql-type="Date" not-null="true" 
     * @return Returns the questionTime.
     */
    public Date getQuestionTime()
    {
        return questionTime;
    }

    public ChattingMessage setQuestionTime(Date questionTime)
    {
        this.questionTime = questionTime;
        return this;
    }
}
