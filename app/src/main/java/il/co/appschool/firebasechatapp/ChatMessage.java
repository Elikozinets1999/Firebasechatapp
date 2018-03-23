package il.co.appschool.firebasechatapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Eli on 3/8/2018.
 */

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private Date messageTime;
    private String formattedMessageTime;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E hh:mm a",Locale.ENGLISH);
        formattedMessageTime = simpleDateFormat.format(messageTime);
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public String getFormattedMessageTime() {
        return formattedMessageTime;
    }
}
