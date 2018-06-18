package il.co.appschool.firebasechatapp.Ilan;

/**
 * Created by ailan on 03/04/2018.
 */

public class Message {
    private long messageId;                      ///ID
    private String msgSaveDay;                      ///Fname
    private String msgSaveHour;                       ///Lname
    private String message;                           ///city
    private String fromWhichChat;                     //waste

    public Message(long messageId, String msgSaveDay, String msgSaveHour,
                   String message, String fromWhichChat) /// building new sms
    {
        this.messageId = messageId;
        this.msgSaveDay = msgSaveDay;
        this.msgSaveHour = msgSaveHour;
        this.message = message;
        this.fromWhichChat = fromWhichChat;
    }
    public Message(String msgSaveDay , String msgSaveHour , String message , String fromWhichChat) /// building new sms
    {
        this.msgSaveDay = msgSaveDay;
        this.msgSaveHour = msgSaveHour;
        this.message = message;
        this.fromWhichChat = fromWhichChat;
    }
    public long getmessageId()
    {
        return messageId;
    }
    public void setmessageId(long messageId)
    {
        this.messageId = messageId;
    }
    public String getMsgSaveDay() {
        return msgSaveDay;
    }
    public void setMsgSaveDay(String msgSaveDay)
    {
        this.msgSaveDay = msgSaveDay;
    }
    public String getMsgSaveHour() {
        return msgSaveHour;
    }
    public void setMsgSaveHour(String msgSaveHour)
    {
        this.msgSaveHour = msgSaveHour;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String msg)
    {
        this.message = msg;
    }
    public String getFromWhichChat() {
        return fromWhichChat;
    }
    public void setFromWhichChat(String chat)
    {
        this.fromWhichChat = chat;
    }

}
